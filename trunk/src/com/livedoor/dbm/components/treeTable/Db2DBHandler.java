/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.connection.DB2ConnectionInfo;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.db.DBMConnectionManager;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;

/**
 * <p>
 * Title: 执行计划
 * </p>
 * <p>
 * Description: DbManager sql 语句执行计划
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author YuanBaoKun
 * @version 1.0
 */
public class Db2DBHandler {

	private List list;

	private ConnectionInfo connectionInfo;

	private DBSession dBSession;

	private DBMSqlExecuter dBMSqlExecuter;

	private String[] tabName = new String[]{"EXPLAIN_INSTANCE", "ADVISE_INDEX", "ADVISE_WORKLOAD", "EXPLAIN_STATEMENT", "EXPLAIN_ARGUMENT",
			"EXPLAIN_OBJECT", "EXPLAIN_OPERATOR", "EXPLAIN_PREDICATE", "EXPLAIN_STREAM"};

	/**
	 * [功 能] 根据给定的参数，生成查询计划结果集
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param conInfo
	 *            ConnectionInfo
	 * @param dBSession
	 *            DBSession
	 *            <p>
	 */
	public Db2DBHandler(ConnectionInfo connectionInfo, DBSession dBSession) {

		this.connectionInfo = connectionInfo;
		this.dBSession = dBSession;

		dBMSqlExecuter = DBMSqlExecuterFactory.createExecuter(connectionInfo, dBSession);
	}

	/**
	 * [功 能] 创建执行计划所需的表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void createTables() throws DBMException {
		DBMDataMetaInfo dBMDataMetaInfo = new DBMDataMetaInfo();

		dBMDataMetaInfo.setSchemaName(connectionInfo.getUserName().toUpperCase());
		dBMDataMetaInfo.setCatalog(null);
		dBMDataMetaInfo.setTableName(null);
		dBMDataMetaInfo.setTypes(null);

		List list = dBMSqlExecuter.getTableNames(dBMDataMetaInfo);

		for (int i = 0; i < tabName.length; i++) {
			if (list.contains(tabName[i]) == false) {
				createTable(i);
			}
		}
	}

	/**
	 * [功 能] 执行查询计划
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param String
	 *            sql语句
	 * 
	 * @return list 返回查询计划的详细信息
	 *         <p>
	 */
	public List executePlan(String stSql) throws DBMException {

		String stTime = ((Long) System.currentTimeMillis()).toString();

		String stPlanSql = "EXPLAIN PLAN SET QUERYTAG = '" + stTime + "' FOR " + stSql;

		int iRet = dBMSqlExecuter.executeUpdate(stPlanSql);

		if (iRet > -1) {
			list = getDataList(stTime);

			Db2DataRecord dataRecord = (Db2DataRecord) list.get(0);
			BigDecimal nTotalCost = dataRecord.getSubCost();

			calculateNodeCost(list, nTotalCost, 0);
			deleteRecord(stTime);
		}
		return list;
	}

	/**
	 * [功 能] 计算Node Cost
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param List
	 *            查询计划所有的信息
	 * @param BigDecimal
	 *            总的Node Cost
	 * @param int
	 *            纪录顺序
	 * 
	 * @return void
	 *         <p>
	 */
	private void calculateNodeCost(List list, BigDecimal nTotalCost, int index) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(7);
		BigDecimal zero = new BigDecimal("0");
		Db2DataRecord dataRecord = (Db2DataRecord) list.get(index);
		BigDecimal nSubCost = dataRecord.getSubCost();

		int iOperatorId = Integer.parseInt(dataRecord.getOperatorId());

		BigDecimal nTemCost = new BigDecimal("0");

		for (int i = 0; i < list.size(); i++) {
			Db2DataRecord temDR = (Db2DataRecord) list.get(i);

			String str = temDR.getTargetId();

			if (str == null || str.trim().length() == 0 || str.equals("(null)")) {
				continue;
			} else if (Integer.parseInt(str) == iOperatorId) {
				nTemCost = nTemCost.add(temDR.getSubCost());
			}
		}

		BigDecimal nodeCost = nSubCost.subtract(nTemCost);
		String stNodeCost = null;
		if (nodeCost.abs().compareTo(zero) == 0) {
			nodeCost = zero;
			stNodeCost = "0";
		} else {
			nodeCost = nodeCost.setScale(7, BigDecimal.ROUND_HALF_UP);
			stNodeCost = nf.format(nodeCost);
		}

		BigDecimal bNodeCost = nodeCost.multiply(new BigDecimal("100")).divide(nTotalCost, 2, BigDecimal.ROUND_HALF_UP);

		dataRecord.setNodeCost(stNodeCost + "(" + bNodeCost + "%)");

		if (index < list.size() - 1) {
			calculateNodeCost(list, nTotalCost, index + 1);
		}
	}

	/**
	 * [功 能] 获取查询计划纪录信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param statementId
	 *            查询计划标识
	 * @return List 查询计划纪录信息
	 */
	private List getDataList(String statementId) throws DBMException {

		List listData = new ArrayList();

		String stSql = replaceSql(PlanTabSql.DB2_PLANTAB_SELECT, statementId);

		ResultSet rs = null;
		Statement st = null;

		try {
			rs = dBMSqlExecuter.executeQueryRes(stSql);
			st = rs.getStatement();

			while (rs.next()) {

				/*
				 * String stOperation = filterNull(rs.getString("OPERATION"));
				 * String stOptions = filterNull(rs.getString("OPTIONS"));
				 * 
				 * if (stOptions != null && stOptions.length() > 0) {
				 * stOperation = stOperation + " (" + stOptions + ")"; }
				 */

				Db2DataRecord db2Record = new Db2DataRecord();
				db2Record.setBuffers(filterNull(rs.getString("BUFFERS")));
				db2Record.setColumnCount(filterNull(rs.getString("COLUMN_COUNT")));
				db2Record.setColumnNames(filterNull(rs.getObject("COLUMN_NAMES")));
				db2Record.setComCost(filterNull(rs.getString("COMM_COST")));
				db2Record.setCpuCost(filterNull(rs.getString("CPU_COST")));
				db2Record.setExplainTime(rs.getTimestamp("EXPLAIN_TIME").toString());
				db2Record.setExplianLevel(filterNull(rs.getString("EXPLAIN_LEVEL")));
				db2Record.setExpRequester(filterNull(rs.getString("EXPLAIN_REQUESTER")));
				db2Record.setFirstComCost(filterNull(rs.getString("FIRST_COMM_COST")));
				db2Record.setFirstRowCost(filterNull(rs.getString("FIRST_ROW_COST")));
				db2Record.setIoCost(filterNull(rs.getString("IO_COST")));

				db2Record.setNodeCost("");

				db2Record.setObjName(filterNull(rs.getString("OBJECT_NAME")));
				db2Record.setObjSchema(filterNull(rs.getString("OBJECT_SCHEMA")));

				String stOperation = rs.getString("OPERATOR_TYPE");
				db2Record.setOperation(filterNull(convertOperation(stOperation)));
				db2Record.setOperatorType(filterNull(stOperation));
				db2Record.setPartitionColumns(filterNull(rs.getString("PARTITION_COLUMNS")));
				db2Record.setPmid(filterNull(rs.getString("PMID")));
				db2Record.setReCpuCost(filterNull(rs.getString("RE_CPU_COST")));
				db2Record.setReIOCost(filterNull(rs.getString("RE_IO_COST")));
				db2Record.setRemoteComCost(filterNull(rs.getString("REMOTE_COMM_COST")));

				db2Record.setRemoteTotalCost(filterNull(rs.getString("REMOTE_TOTAL_COST")));

				db2Record.setReTotalCost(filterNull(rs.getString("RE_TOTAL_COST")));
				db2Record.setSectionNumber(filterNull(rs.getString("SECTNO")));
				db2Record.setSingleNode(filterNull(rs.getString("SINGLE_NODE")));
				db2Record.setSourceName(filterNull(rs.getString("SOURCE_NAME")));
				db2Record.setSourceSchema(filterNull(rs.getString("SOURCE_SCHEMA")));
				db2Record.setSourceType(filterNull(rs.getString("SOURCE_TYPE")));
				db2Record.setStreamCount(filterNull(rs.getString("STREAM_COUNT")));

				db2Record.setStreamId(filterNull(rs.getString("STREAM_ID")));

				java.math.BigDecimal dec = rs.getBigDecimal("TOTAL_COST");
				db2Record.setSubCost(dec.setScale(7, BigDecimal.ROUND_HALF_UP));

				db2Record.setTargetType(filterNull(rs.getString("TARGET_TYPE")));

				db2Record.setOperatorId(filterNull(rs.getString("OPERATOR_ID")));
				db2Record.setSourceId(filterNull(rs.getString("SOURCE_ID")));
				db2Record.setTargetId(filterNull(rs.getString("TARGET_ID")));

				listData.add(db2Record);

			}
		} catch (SQLException e) {
			throw new DBMException(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (st != null)
				{
					st.close();
					st = null;
				}
			} catch (SQLException e) {
			}

		}

		return listData;
	}

	/**
	 * [功 能] 删除查询计划纪录
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param statementId
	 *            查询计划标识
	 * @return int 删除记录条数
	 */
	private int deleteRecord(String statementId) throws DBMException {
		String stSql = PlanTabSql.DB2_EXPLAIN_DELETE + statementId + "')";

		return dBMSqlExecuter.executeUpdate(stSql);
	}

	/**
	 * [功 能] 创建查询计划表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param tableIndex
	 *            表的顺序
	 * @return void
	 */
	private void createTable(int tableIndex) throws DBMException {

		String stSql = null;
		switch (tableIndex) {
			case 0 :
				stSql = PlanTabSql.DB2_EXPLAIN_INSTANCE;
				dBMSqlExecuter.executeUpdate(stSql);
				break;
			case 1 :
				stSql = PlanTabSql.DB2_ADVISE_INDEX;
				dBMSqlExecuter.executeUpdate(stSql);
				break;
			case 2 :
				stSql = PlanTabSql.DB2_ADVISE_WORKLOAD;
				dBMSqlExecuter.executeUpdate(stSql);
				break;
			case 3 :
				stSql = PlanTabSql.DB2_EXPLAIN_STATEMENT;
				dBMSqlExecuter.executeUpdate(stSql);
				break;
			case 4 :
				stSql = PlanTabSql.DB2_EXPLAIN_ARGUMENT;
				dBMSqlExecuter.executeUpdate(stSql);
				break;
			case 5 :
				stSql = PlanTabSql.DB2_EXPLAIN_OBJECT;
				dBMSqlExecuter.executeUpdate(stSql);
				break;
			case 6 :
				stSql = PlanTabSql.DB2_EXPLAIN_OPERATOR;
				dBMSqlExecuter.executeUpdate(stSql);
				break;
			case 7 :
				stSql = PlanTabSql.DB2_EXPLAIN_PREDICATE;
				dBMSqlExecuter.executeUpdate(stSql);
				break;
			case 8 :
				stSql = PlanTabSql.DB2_EXPLAIN_STREAM;
				dBMSqlExecuter.executeUpdate(stSql);
				break;
		}
	}

	/**
	 * [功 能] 过滤空值
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param Object
	 *            给定的数据
	 * @return String 过滤后的数据
	 */
	private String filterNull(Object obj) {
		String stRet = "(null)";
		if (obj == null) {
			return stRet;
		} else {
			return obj.toString();
		}
	}

	/**
	 * [功 能] 生成sql语句
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param stSql
	 *            sql 语句
	 * @param strParam
	 *            参数
	 * 
	 * @return String sql 语句
	 */
	private String replaceSql(String stSql, String strParam) {
		/*
		 * String[] objs = new String[1]; objs[0] = strParam;
		 * 
		 * return replaceSql(stSql, objs);
		 */
		return MessageFormat.format(stSql, strParam);
	}

	/**
	 * [功 能] 生成sql语句
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param stSql
	 *            sql 语句
	 * @param param
	 *            参数
	 * 
	 * @return String sql 语句
	 */
	/*private String replaceSql(String stSql, String[] param) {
		String strRet = MessageFormat.format(stSql, param);
		return strRet;
	}*/

	/**
	 * [功 能] 过滤结果中表示数据
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param str
	 *            给定的数据
	 * 
	 * @return String 返回的数据
	 */
	private String convertOperation(String str) {
		String sRet = null;

		String[] inValues = new String[]{"RETURN", "HSJOIN", "TBSCAN", "MSJOIN", "FILTER", "NLJOIN", "DELETE", "TEMP", "IXSCAN", "FETCH",
				"SORT"};
		String[] outValues = new String[]{"Result", "Hash Join", "Table Scan", "Merge Scan Join", "Filter rows", "Nested loop Join",
				"Delete", "Temporary Table Construction", "Index Scan", "Fetch", "Sort"};

		if (str == null || str.length() == 0) {
			sRet = str;
		} else {
			sRet = str;
			for (int i = 0; i < inValues.length; i++) {
				if (str.trim().equals(inValues[i]) == true) {
					sRet = outValues[i];
					break;
				}
			}
		}

		return sRet;
	}

	public static void main(String[] args) throws Exception {

		DB2ConnectionInfo ora = new DB2ConnectionInfo();
		ora.setDbType(DBServerType.DB2);
		ora.setHost("10.5.6.53");
		ora.setUserName("db2admin");
		ora.setPassword("db2admin");
		ora.setDatabase("mydb2");
		ora.setPort("50000");
		ora.setConnectionName("db2");

		DBSession dbSession = new DBSession();

		Db2DBHandler oo = new Db2DBHandler(ora, dbSession);

		oo.createTables();

		String stSql = "SELECT STMT.QUERYNO, QUERYTAG, OPERATOR_TYPE, STMT.EXPLAIN_REQUESTER, STMT.EXPLAIN_TIME, O.SOURCE_NAME, O.SOURCE_SCHEMA, O.EXPLAIN_LEVEL, O.STMTNO, O.SECTNO, S.SOURCE_TYPE, S.TARGET_TYPE, OBJ.OBJECT_SCHEMA, OBJ.OBJECT_NAME, S.STREAM_COUNT, S.COLUMN_COUNT, S.PREDICATE_ID, S.COLUMN_NAMES, S.PMID, S.SINGLE_NODE, S.PARTITION_COLUMNS, OPERATOR_ID, O.TOTAL_COST, IO_COST, CPU_COST, FIRST_ROW_COST, RE_TOTAL_COST, RE_IO_COST, RE_CPU_COST, COMM_COST, FIRST_COMM_COST, BUFFERS,  REMOTE_COMM_COST, S.SOURCE_ID, S.TARGET_ID FROM ((( EXPLAIN_OPERATOR O JOIN EXPLAIN_STATEMENT STMT ON O.STMTNO = STMT.STMTNO AND O.EXPLAIN_REQUESTER = STMT.EXPLAIN_REQUESTER AND O.EXPLAIN_TIME = STMT.EXPLAIN_TIME AND O.SOURCE_NAME = STMT.SOURCE_NAME AND O.SOURCE_SCHEMA = STMT.SOURCE_SCHEMA AND O.EXPLAIN_LEVEL = STMT.EXPLAIN_LEVEL AND O.STMTNO = STMT.STMTNO AND O.SECTNO = STMT.SECTNO AND STMT.EXPLAIN_LEVEL = 'P' AND STMT.QUERYTAG = '55555') LEFT JOIN EXPLAIN_STREAM S ON O.OPERATOR_ID = S.SOURCE_ID AND O.STMTNO = S.STMTNO AND O.EXPLAIN_REQUESTER = S.EXPLAIN_REQUESTER AND O.EXPLAIN_TIME = S.EXPLAIN_TIME AND O.SOURCE_NAME = S.SOURCE_NAME AND O.SOURCE_SCHEMA = S.SOURCE_SCHEMA AND O.EXPLAIN_LEVEL = S.EXPLAIN_LEVEL AND O.STMTNO = S.STMTNO AND O.SECTNO = S.SECTNO ) LEFT JOIN EXPLAIN_STREAM OBJ ON S.SOURCE_ID = OBJ.TARGET_ID AND OBJ.SOURCE_ID = -1 AND S.EXPLAIN_REQUESTER = OBJ.EXPLAIN_REQUESTER AND S.EXPLAIN_TIME = OBJ.EXPLAIN_TIME AND S.SOURCE_NAME = OBJ.SOURCE_NAME AND S.SOURCE_SCHEMA = OBJ.SOURCE_SCHEMA AND S.EXPLAIN_LEVEL = OBJ.EXPLAIN_LEVEL AND S.STMTNO = OBJ.STMTNO AND S.SECTNO = OBJ.SECTNO )";

		List ll = oo.executePlan(stSql);

		for (int i = 0; i < ll.size(); i++) {
			Db2DataRecord d = (Db2DataRecord) ll.get(i);

			System.out.println(d.getOperation() + "-------" + d.getSubCost() + "-------" + d.getCpuCost() + "-------" + d.getObjName()
					+ "-------" + d.getExplianLevel());
		}

		DBMConnectionManager.commit(ora, dbSession);

		DBMConnectionManager.closeConnection(ora);
	}
}

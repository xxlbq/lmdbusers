/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.connection.OraConnectionInfo;
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
public class OracleDBHandler {

	private List list;

	private ConnectionInfo connectionInfo;

	private DBSession dBSession;

	private DBMSqlExecuter dBMSqlExecuter;

	private String[] tabName = new String[] { "PLAN_TABLE" };

	/**
	 * [功 能] 根据给定的参数，生成查询计划结果集 
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * @param conInfo
	 *            ConnectionInfo
	 * @param dBSession
	 *            DBSession
	 *            <p>
	 */
	public OracleDBHandler(ConnectionInfo connectionInfo, DBSession dBSession) {

		this.connectionInfo = connectionInfo;
		this.dBSession = dBSession;

		dBMSqlExecuter = DBMSqlExecuterFactory.createExecuter(connectionInfo,
				dBSession);
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

		dBMDataMetaInfo.setSchemaName(connectionInfo.getUserName()
				.toUpperCase());
		dBMDataMetaInfo.setCatalog(null);
		dBMDataMetaInfo.setTableName(null);
		dBMDataMetaInfo.setTypes(null);

		List list = dBMSqlExecuter.getTableNames(dBMDataMetaInfo);

		if (list.contains(tabName[0]) == false) {
			createTable(tabName[0]);
		}
	}

	/**
	 * [功 能] 执行查询计划
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param String sql语句
	 * 
	 * @return list 返回查询计划的详细信息
	 *         <p>
	 */
	public List executePlan(String stSql) throws DBMException {

		String stTime = ((Long) System.currentTimeMillis()).toString();

		String stPlanSql = "explain plan " + "set statement_id = '" + stTime
				+ "' for " + stSql;

		int iRet = dBMSqlExecuter.executeUpdate(stPlanSql);

		if (iRet > -1) {
			list = getDataList(stTime);
			deleteRecord(stTime);
		}

		return list;
	}
	
	/**
	 * [功 能] 获取查询计划纪录信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param statementId 查询计划标识
	 * @return List 查询计划纪录信息
	 */
	private List getDataList(String statementId) throws DBMException {
		List listData = new ArrayList();

		String stSql = PlanTabSql.ORACLE_PLANTAB_SELECT + statementId
				+ "' ORDER BY ID ASC";

		ResultSet rs = null;
		Statement st = null;

		try {
			rs = dBMSqlExecuter.executeQueryRes(stSql);
			st = rs.getStatement();

			while (rs.next()) {

				String stOperation = filterNull(rs.getString("OPERATION"));
				String stOptions = filterNull(rs.getString("OPTIONS"));

				if (stOptions != null && stOptions.length() > 0) {
					stOperation = stOperation + " (" + stOptions + ")";
				}

				OraDataRecord oraRecord = new OraDataRecord();
				oraRecord.setBytes(filterNull(rs.getString("BYTES")));
				oraRecord
						.setCardinality(filterNull(rs.getString("CARDINALITY")));
				oraRecord.setCost(filterNull(rs.getString("COST")));
				oraRecord.setCpuCost(filterNull(rs.getString("CPU_COST")));
				oraRecord.setDistribution(filterNull(rs
						.getString("DISTRIBUTION")));
				oraRecord.setIoCost(filterNull(rs.getString("IO_COST")));
				oraRecord.setObjInstance(filterNull(rs
						.getString("OBJECT_INSTANCE")));
				oraRecord.setObjName(filterNull(rs.getString("OBJECT_NAME")));
				oraRecord.setObjNode(filterNull(rs.getString("OBJECT_NODE")));
				oraRecord.setObjOwner(filterNull(rs.getString("OBJECT_OWNER")));
				oraRecord.setObjType(filterNull(rs.getString("OBJECT_TYPE")));
				oraRecord.setOperation(stOperation);
				oraRecord.setOptimizer(filterNull(rs.getString("OPTIMIZER")));
				oraRecord.setOptions(stOptions);

				oraRecord.setOther("");

				/*
				 * byte[] binary = rs.getBytes("OTHER"); if(binary.length == 0) {
				 * oraRecord.setOther(""); } else { oraRecord.setOther(new
				 * String(binary)); }
				 */
				oraRecord.setOtherTag(filterNull(rs.getString("OTHER_TAG")));
				oraRecord.setPartitionId(filterNull(rs
						.getString("PARTITION_ID")));
				oraRecord.setPartitionStart(filterNull(rs
						.getString("PARTITION_START")));
				oraRecord.setPartitionStop(filterNull(rs
						.getString("PARTITION_STOP")));
				oraRecord.setPosition(filterNull(rs.getString("POSITION")));
				oraRecord.setRemarks(filterNull(rs.getString("REMARKS")));
				oraRecord.setSearchColumns(filterNull(rs
						.getString("SEARCH_COLUMNS")));
				oraRecord.setTempSpace(filterNull(rs.getString("TEMP_SPACE")));
				oraRecord.setId(filterNull(rs.getString("ID")));
				oraRecord.setParent(filterNull(rs.getString("PARENT_ID")));

				listData.add(oraRecord);

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
	 * @param statementId 查询计划标识
	 * @return int 删除记录条数
	 */
	private int deleteRecord(String statementId) throws DBMException {
		String stSql = PlanTabSql.ORACLE_PLANTAB_DELETE + statementId + "'";
		return dBMSqlExecuter.executeUpdate(stSql);
	}

	/**
	 * [功 能] 创建查询计划表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param tableIndex 表的顺序
	 * @return void
	 */
	private int createTable(String table) throws DBMException {
		int iRet = 0;

		if (table.equals(tabName[0]) == true) {
			iRet = dBMSqlExecuter
					.executeUpdate(PlanTabSql.ORACLE_PLANTAB_CREATE);
		}

		return iRet;
	}
	
	/**
	 * [功 能] 过滤空值
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param Object 给定的数据
	 * @return String 过滤后的数据
	 */
	private String filterNull(String str) {
		String stRet = "";
		if (str == null) {
			return stRet;
		} else {
			return str;
		}
	}

	public static void main(String[] args) throws Exception {

		OraConnectionInfo ora = new OraConnectionInfo();
		ora.setDbType(DBServerType.ORACLE9i);
		ora.setHost("10.5.6.53");
		ora.setUserName("sys");
		ora.setPassword("sys");
		ora.setSid("orcl");
		ora.setPort("1521");
		ora.setConnectionName("ora");
		ora.setRole("sysdba");

		DBSession dbSession = new DBSession();

		OracleDBHandler oo = new OracleDBHandler(ora, dbSession);

		oo.createTables();

		String stSql = "select * from tab";

		List ll = oo.executePlan(stSql);

		for (int i = 0; i < ll.size(); i++) {
			OraDataRecord d = (OraDataRecord) ll.get(i);

			System.out.println(d.getOperation() + "-------" + d.getCost()
					+ "-------" + d.getObjName() + "-------" + d.getPosition()
					+ "-------" + d.getId() + "-------" + d.getParent());
		}

		DBMConnectionManager.commit(ora, dbSession);

		DBMConnectionManager.closeConnection(ora);
	}

}

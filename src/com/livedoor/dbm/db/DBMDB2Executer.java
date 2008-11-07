/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.livedoor.dbm.connection.DB2ConnectionInfo;
import com.livedoor.dbm.constants.DBColumnType;
import com.livedoor.dbm.constants.DBResultType;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.exception.DBMException;

/**
 * <p>
 * Title: 数据库操作
 * </p>
 * <p>
 * Description: DbManager 数据库操作，执行各种sql语句
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
public class DBMDB2Executer extends DBMSqlExecuter {

	/*
	 * list for return databases
	 */
	private List list;

	private String SQL_SELECT_VIEW = "SELECT VIEWNAME FROM SYSCAT.VIEWS";
	// WHERE VIEWSCHEMA = 'SYSIBM'

	private String SQL_SELECT_TAB = "SELECT TABNAME FROM SYSCAT.TABLES";

	private String SQL_SELECT_IDEBTITY = "SELECT COLNAME, IDENTITY, GENERATED FROM SYSCAT.COLUMNS";
	// WHERE TABSCHEMA = 'SYSIBM'

	private String DB2_CHAR = "CHAR";

	private String DB2_CHARACTER = "CHARACTER";

	/**
	 * [功 能] 获取数据库列表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return list 数据库列表
	 *         <p>
	 */
	public List getDatabase() {
		list = new ArrayList();
		String stDatabase = ((DB2ConnectionInfo) this.getConnectionInfo()).getDatabase();
		list.add(stDatabase);
		return list;
	}

	/**
	 * [功 能] 获取视图列表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return list 视图列表
	 *         <p>
	 */
	public List getViews(DBMDataMetaInfo dbMetaInfo) throws DBMException {
		String schemaName = dbMetaInfo.getSchemaName();
		String stSql = null;
		list = new ArrayList();

		if (schemaName != null) {
			stSql = SQL_SELECT_VIEW + " WHERE VIEWSCHEMA = '" + schemaName + "'";
		} else {
			stSql = SQL_SELECT_VIEW;
		}

		DBMDataResult dBMDataResult = executeQuery(stSql);

		List listDatabase = dBMDataResult.getData();

		for (int i = 0; i < listDatabase.size(); i++) {
			String stViewName = (String) ((Map) listDatabase.get(i)).get("VIEWNAME");
			list.add(stViewName);
		}

		return list;
	}

	/**
	 * [功 能] 获取Table列表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return list Table列表
	 *         <p>
	 */
	public List getTableNames(DBMDataMetaInfo dbMetaInfo) throws DBMException {
		String schema = dbMetaInfo.getSchemaName();
		String stSql = null;
		list = new ArrayList();

		if (schema != null) {
			stSql = SQL_SELECT_TAB + " WHERE TABSCHEMA = '" + schema + "' AND TYPE = 'T'";
		} else {
			stSql = SQL_SELECT_TAB + " WHERE TYPE = 'T'";
		}

		DBMDataResult dBMDataResult = executeQuery(stSql);

		List listDatabase = dBMDataResult.getData();

		for (int i = 0; i < listDatabase.size(); i++) {
			String stTabName = (String) ((Map) listDatabase.get(i)).get("TABNAME");
			list.add(stTabName);
		}

		return list;

	}

	/**
	 * [功 能] 获取表中所有自增型列列表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return list 自增型列列表
	 *         <p>
	 */
	public List getAutoIncrementColumns(DBMDataMetaInfo dbMetaInfo) throws DBMException {
		String schema = dbMetaInfo.getSchemaName();
		String tabname = dbMetaInfo.getTableName();
		String stSql = null;
		list = new ArrayList();

		// SELECT COLNAME, IDENTITY, GENERATED FROM SYSCAT.COLUMNS WHERE
		// TABSCHEMA = 'DB2ADMIN' AND TABNAME = 'OKOK' AND IDENTITY = 'Y'

		if (schema != null) {
			stSql = SQL_SELECT_IDEBTITY + " WHERE TABSCHEMA = '" + schema + "' AND TABNAME = '" + tabname + "' AND IDENTITY = 'Y'";
		} else {
			stSql = SQL_SELECT_IDEBTITY + " WHERE TABNAME = '" + tabname + "' AND IDENTITY = 'Y'";
		}

		DBMDataResult dBMDataResult = executeQuery(stSql);

		List listDatabase = dBMDataResult.getData();

		for (int i = 0; i < listDatabase.size(); i++) {
			String stTabName = (String) ((Map) listDatabase.get(i)).get("COLNAME");
			list.add(stTabName);
		}

		return list;

	}

	/**
	 * [功 能] 改变数据库
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbName
	 *            数据库名称
	 *            <p>
	 * @return 无
	 *         <p>
	 */
	public void changeDatabase(String dbName) {

	}

	/**
	 * [功 能] 根据给定的参数，返回满足条件的表信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return DBMDataResult table结果集
	 *         <p>
	 */
	public DBMDataResult getTables(DBMDataMetaInfo dbMetaInfo) throws DBMException {
		return getTables(dbMetaInfo.getCatalog(), dbMetaInfo.getSchemaName(), dbMetaInfo.getTableName(), dbMetaInfo.getTypes());

	}

	/**
	 * [功 能] 根据给定的参数，返回满足条件的列信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return DBMDataResult 列结果集
	 *         <p>
	 */
	public DBMDataResult getColumns(DBMDataMetaInfo dbMetaInfo) throws DBMException {
		DBMDataResult dBMDataResult = getColumns(dbMetaInfo.getCatalog(), dbMetaInfo.getSchemaName(), dbMetaInfo.getTableName(), dbMetaInfo
				.getColumnName());

		List list = dBMDataResult.getData();

		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);

			String stTypeName = (String) m.get(DBColumnType.TYPE_NAME);
			if (stTypeName.equals(DB2_CHAR)) {
				m.put(DBColumnType.TYPE_NAME, DB2_CHARACTER);
			}
		}

		return dBMDataResult;
	}

	/**
	 * [功 能] 执行多条sql语句
	 * <p>
	 * [说 明] 用来执行多条sql语句
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param sqls
	 *            sql语句数组
	 * @param iCount
	 *            返回的最大记录数量
	 *            <p>
	 * 
	 * @return DBMSqlResult[] 结果集数组
	 *         <p>
	 */
	public DBMSqlResult[] execute(String[] sqls, int iCount) throws DBMException {
		int iLen = sqls.length;
		ResultSet rs = null;
		Statement stmt = null;
		DBMSqlResult[] dBMSqlResults = new DBMSqlResult[iLen];
		Connection conn = null;

		conn = DBMConnectionManager.getConnection(conInfo, dBSession);

		/*
		 * try { conn = DBMConnectionManager.getConnection(conInfo, dBSession); }
		 * catch (Exception e) { throw new DBMException(e.getMessage()); }
		 */

		for (int i = 0; i < iLen; i++) {

			dBMSqlResults[i] = new DBMSqlResult();

			if (conn == null) {
				conn = DBMConnectionManager.getConnection(conInfo, dBSession);
			}

			try {

				stmt = conn.createStatement();
				boolean brs = stmt.execute(sqls[i]);

				if (brs == true) {
					rs = stmt.getResultSet();

					DBMDataResult dBMDataResult = new DBMDataResult();
					dBMDataResult.dealResultSet(rs, iCount);
					dBMSqlResults[i].setDBMDataResult(dBMDataResult);
					dBMSqlResults[i].setType(DBResultType.MUTI_ROWS);
					if (((stmt.getMoreResults() == false) && (stmt.getUpdateCount() == -1)) == false) {
						int iRow = stmt.getUpdateCount();
						dBMSqlResults[i].setMessage(iRow + stUpdateMessage);
						dBMSqlResults[i].setType(DBResultType.MUTI_RES);
					}
				} else {
					int iRow = stmt.getUpdateCount();

					dBMSqlResults[i].setMessage(iRow + stUpdateMessage);
					dBMSqlResults[i].setType(DBResultType.ZERO_ROWS);
					if (((stmt.getMoreResults() == false) && (stmt.getUpdateCount() == -1)) == false) {
						rs = stmt.getResultSet();

						DBMDataResult dBMDataResult = new DBMDataResult();
						dBMDataResult.dealResultSet(rs, iCount);
						dBMSqlResults[i].setDBMDataResult(dBMDataResult);
						dBMSqlResults[i].setType(DBResultType.MUTI_RES);
					}
				}
			} catch (Exception e) {
				String stMessage = e.getMessage();
				dBMSqlResults[i].setMessage(stMessage);
				dBMSqlResults[i].setType(DBResultType.ERROR);
				try {
					DBMConnectionManager.closeConnection(conInfo, dBSession);
					conn = null;
				} catch (Exception ex) {

				}
				continue;
			} finally {
				try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
					if (stmt != null) {
						stmt.close();
						stmt = null;
					}
				} catch (Exception e) {
				}
			}
		}

		/*
		 * try { if (stmt != null) { stmt.close(); stmt = null; } } catch
		 * (SQLException e) { }
		 */

		return dBMSqlResults;
	}

	/**
	 * [功 能] 执行sql语句
	 * <p>
	 * [说 明] 用来执行sql语句
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param sqls
	 *            sql语句
	 * @param iCount
	 *            返回的最大记录数量
	 *            <p>
	 * 
	 * @return DBMSqlResult 结果集
	 *         <p>
	 */
	public DBMSqlResult execute(String sqls, int iCount) throws DBMException {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;

		DBMSqlResult dBMSqlResult = new DBMSqlResult();
		dBMSqlResult.setType(DBResultType.SUCCESS);

		conn = DBMConnectionManager.getConnection(conInfo, dBSession);

		/*
		 * try { conn = DBMConnectionManager.getConnection(conInfo, dBSession); }
		 * catch (Exception e) { throw new DBMException(e.getMessage()); }
		 */

		/*
		 * if (conn == null) { conn =
		 * DBMConnectionManager.getConnection(conInfo, dBSession); }
		 */

		try {

			stmt = conn.createStatement();
			_currentStatement = stmt;
			setExecuting(true);
			boolean brs = stmt.execute(sqls);

			if (brs == true) {
				rs = stmt.getResultSet();

				DBMDataResult dBMDataResult = new DBMDataResult();
				dBMDataResult.dealResultSet(rs, iCount);
				dBMSqlResult.setDBMDataResult(dBMDataResult);
				dBMSqlResult.setType(DBResultType.MUTI_ROWS);
				if (((stmt.getMoreResults() == false) && (stmt.getUpdateCount() == -1)) == false) {
					int iRow = stmt.getUpdateCount();
					dBMSqlResult.setMessage(iRow + stUpdateMessage);
					dBMSqlResult.setType(DBResultType.MUTI_RES);
				}
			} else {
				int iRow = stmt.getUpdateCount();

				dBMSqlResult.setMessage(iRow + stUpdateMessage);
				dBMSqlResult.setType(DBResultType.ZERO_ROWS);
				if (((stmt.getMoreResults() == false) && (stmt.getUpdateCount() == -1)) == false) {
					rs = stmt.getResultSet();

					DBMDataResult dBMDataResult = new DBMDataResult();
					dBMDataResult.dealResultSet(rs, iCount);
					dBMSqlResult.setDBMDataResult(dBMDataResult);
					dBMSqlResult.setType(DBResultType.MUTI_RES);
				}
			}
		} catch (Exception e) {
			setExecuting(false);
			String stMessage = e.getMessage();
			dBMSqlResult.setMessage(stMessage);
			dBMSqlResult.setType(DBResultType.ERROR);
			try {
				DBMConnectionManager.closeConnection(conInfo, dBSession);
				conn = null;
			} catch (Exception ex) {

			}
		} finally {
			setExecuting(false);
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (Exception e) {
			}
		}
		setExecuting(false);

		/*
		 * try { if (stmt != null) { stmt.close(); stmt = null; } } catch
		 * (SQLException e) { }
		 */

		return dBMSqlResult;
	}

	/**
	 * [功 能] 执行sql语句
	 * <p>
	 * [说 明] 用来执行sql语句，包括insert, update or delete 等操作
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param stSql
	 *            sql语句
	 *            <p>
	 * 
	 * @return int 返回执行语句后，影响的记录数量
	 *         <p>
	 */
	public int executeUpdate(String stSql) throws DBMException {
		int iRow = 0;
		Statement statement = null;
		Connection conn = null;
		try {
			conn = DBMConnectionManager.getConnection(conInfo, dBSession);
			statement = conn.createStatement();

			iRow = statement.executeUpdate(stSql);

		} catch (SQLException e) {
			try {
				DBMConnectionManager.closeConnection(conInfo, dBSession);
				conn = null;
			} catch (Exception ex) {

			}
			throw new DBMException(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
					statement = null;
				} catch (SQLException e) {
				}
			}
		}

		return iRow;
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

		DBSession dBSession = new DBSession();
		DBMSqlExecuter db = DBMSqlExecuterFactory.createExecuter(ora, dBSession);
		DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
		dbMetaInfo.setSchemaName("DB2ADMIN");
		// dbMetaInfo.setTableName("OKOK");
		dbMetaInfo.setTableName("EXPLAIN_STATEMENT");
		// dbMetaInfo.setColumnName("COL1");

		/*
		 * List list = db.getAutoIncrementColumns(dbMetaInfo);
		 * 
		 * for (int i = 0; i < list.size(); i++) {
		 * System.out.println(list.get(i)); }
		 */

		DBMDataResult d = db.getForeignKeys(dbMetaInfo);

		List ll = d.getColumns();
		List l2 = d.getData();

		for (int i = 0; i < ll.size(); i++) {
			String stName = ((DBColumn) ll.get(i)).getColumnName();
			System.out.println(stName);
		}
		for (int i = 0; i < l2.size(); i++) {
			
			System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh ::::: " + i);
			Map m = (Map) l2.get(i);
			for (int j = 0; j < ll.size(); j++) {
				String stName = ((DBColumn) ll.get(j)).getColumnName();
				System.out.println(stName);
				System.out.println(m.get(stName));
			}
			System.out.println("");
		}

		System.out.println(System.currentTimeMillis());

		DBMConnectionManager.releaseConnection(ora, dBSession);
		System.out.println("hello, world!");

	}

}

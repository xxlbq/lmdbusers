/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.livedoor.dbm.connection.MySqlConnectionInfo;
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
public class DBMMysqlExecuter extends DBMSqlExecuter {
	/*
	 * list for return databases
	 */
	private List list;

	private String SQL_SELECT_DB = "show databases";

	private String SQL_SELECT_VIEW = "select table_name from information_schema.views";// where

	// table_schema"

	
	
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
	/*public DBMSqlResult execute(String sqls, int iCount) throws DBMException {

		String stSql = sqls + " limit " + iCount;
		return super.execute(stSql, iCount);
	}*/
	
	
	/**
	 * [功 能] 获取数据库列表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return list 数据库列表
	 *         <p>
	 */
	public List getDatabase() throws DBMException {
		list = new ArrayList();

		DBMDataResult dBMDataResult = executeQuery(SQL_SELECT_DB);

		List listDatabase = dBMDataResult.getData();

		for (int i = 0; i < listDatabase.size(); i++) {
			String stDatabaseName = (String) ((Map) listDatabase.get(i)).get("Database");
			list.add(stDatabaseName);
		}

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
		
		String[] types = new String[]{"VIEW"};
		dbMetaInfo.setTypes(types);
		
		/*
		list = new ArrayList();
		String stSql = null;

		if (schemaName != null) {
			stSql = SQL_SELECT_VIEW + " where table_schema = '" + schemaName + "'";
		} else {
			stSql = SQL_SELECT_VIEW;
		}

		DBMDataResult dBMDataResult = executeQuery(stSql);

		List listDatabase = dBMDataResult.getData();

		for (int i = 0; i < listDatabase.size(); i++) {
			String stViewName = (String) ((Map) listDatabase.get(i)).get("table_name");
			list.add(stViewName);
			
			return list;
		}*/
		
		return getTableNames(dbMetaInfo);
		
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
		list = new ArrayList();
		DBMDataResult dBMDataResult = getTables(dbMetaInfo);

		List listDatabase = dBMDataResult.getData();

		for (int i = 0; i < listDatabase.size(); i++) {
			String stTabName = (String) ((Map) listDatabase.get(i)).get("TABLE_NAME");
			list.add(stTabName);
		}

		return list;
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
		DBMDataResult dBMDataResult = super.getTables(dbMetaInfo.getDatabaseName(), null, dbMetaInfo.getTableName(), dbMetaInfo.getTypes());

		return dBMDataResult;
	}

	/**
	 * [功 能] 根据给定的参数，返回满足条件的表主键信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return DBMDataResult 主键结果集
	 *         <p>
	 */
	public DBMDataResult getPrimaryKeys(DBMDataMetaInfo dbMetaInfo) throws DBMException {
		DBMDataResult dBMDataResult = getPrimaryKeys(dbMetaInfo.getDatabaseName(), null, dbMetaInfo.getTableName());

		return dBMDataResult;
	}
	
	/**
	 * [功 能] 根据给定的参数，返回满足条件的表外键信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return DBMDataResult 外键结果集
	 *         <p>
	 */
	public DBMDataResult getForeignKeys(DBMDataMetaInfo dbMetaInfo) throws DBMException {
		DBMDataResult dBMDataResult = getForeignKeys(dbMetaInfo.getDatabaseName(), null, dbMetaInfo.getTableName());

		return dBMDataResult;
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
		DBMDataResult dBMDataResult = getColumns(dbMetaInfo.getDatabaseName(), null, dbMetaInfo.getTableName(), null);

		return dBMDataResult;
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
		String sql = "USE " + dbName;
		try {
			executeUpdate(sql);
		} catch (DBMException e) {
			e.printStackTrace();
		}
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
		//dbMetaInfo.setSchemaName(dbMetaInfo.getDatabaseName());
		//		return super.getAutoIncrementColumns(dbMetaInfo);
		String stSql = "SELECT * FROM " + dbMetaInfo.getDatabaseName() + "." + dbMetaInfo.getTableName() + " WHERE 1 = 2";

		return getAutoIncrementColumns(stSql);
	}

	public static void main(String[] args) throws Exception {

		System.out.println(System.currentTimeMillis());
		MySqlConnectionInfo ora = new MySqlConnectionInfo();
		ora.setDbType(DBServerType.MYSQL);
		ora.setHost("10.5.1.111");
		ora.setUserName("root");
		ora.setPassword("root");
		// ora.setDatabase("mysql");
		ora.setPort("3306");
		ora.setCharacterEncoding("UTF-8");
		ora.setConnectionName("mysql");

		DBSession dBSession = new DBSession();
		DBMSqlExecuter db = DBMSqlExecuterFactory.createExecuter(ora, dBSession);
		
		String st = "call he('hello')";
		
		DBMSqlResult dd = db.execute(st, 100);
		
		DBMDataResult[] dm = dd.getDBMDataResults();
		String[] ds = dd.getArrayMessage();
		
		System.out.println(dm.length);
		System.out.println(ds.length);

		/*DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
		dbMetaInfo.setDatabaseName("mydbtest");
		dbMetaInfo.setTableName("AAA");

		List list = db.getAutoIncrementColumns(dbMetaInfo);

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}*/

		/*
		 * DBMDataResult d = db.getColumns(null, "info", "data_applog", null);
		 * 
		 * List ll = d.getColumns(); List l2 = d.getData();
		 * 
		 * for (int i = 0; i < ll.size(); i++) { System.out.println(((DBColumn)
		 * ll.get(i)).getColumnName()); }
		 * 
		 * for (int i = 0; i < l2.size(); i++) { Map m = (Map) l2.get(i); for
		 * (int j = 0; j < ll.size(); j++) {
		 * System.out.println(m.get(((DBColumn) ll.get(j)).getColumnName())); }
		 * System.out.println(""); }
		 */

		/*
		 * for(int i = 0; i < list.size(); i++) {
		 * System.out.println(list.get(i)); }
		 */

		/*
		 * ResultSetMetaData rsmt = rs.getMetaData(); int is =
		 * rsmt.getColumnCount(); System.out.println(is);
		 */

		// for(int i = 0; i < is; i++) {
		// System.out.println(rsmt.getColumnName(i)); }
		DBMConnectionManager.releaseConnection(ora, dBSession);
		System.out.println(System.currentTimeMillis());
		System.out.println("hello, world!");

	}

}

/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.livedoor.dbm.connection.OraConnectionInfo;
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
public class DBMOracleExecuter extends DBMSqlExecuter {

	/*
	 * list for return databases
	 */
	private List list;
	private String SQL_SELECT_VIEW = "SELECT VIEW_NAME FROM ALL_VIEWS"; // WHERE
	// OWNER
	// = ''
	// ORDER
	// BY
	// VIEW_NAME

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
		String stDatabase = ((OraConnectionInfo) this.getConnectionInfo()).getSid();
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
			stSql = SQL_SELECT_VIEW + " WHERE OWNER = '" + schemaName + "'";
		} else {
			stSql = SQL_SELECT_VIEW;
		}

		DBMDataResult dBMDataResult = executeQuery(stSql);

		List listDatabase = dBMDataResult.getData();

		for (int i = 0; i < listDatabase.size(); i++) {
			String stViewName = (String) ((Map) listDatabase.get(i)).get("VIEW_NAME");
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
		// TODO Auto-generated method stub

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
		return getColumns(dbMetaInfo.getCatalog(), dbMetaInfo.getSchemaName(), dbMetaInfo.getTableName(), dbMetaInfo.getColumnName());
	}

}

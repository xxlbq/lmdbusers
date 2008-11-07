/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.db;

/**
 * <p>
 * Description:
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英极软件开发（大连）有限公司
 * 
 * @author <a href="mailto:lijian@livedoor.cn">Jian Li </a>
 * @version 1.0
 */
public final class DBMDataMetaInfo {
	// public ConnectionInfo connInfo;
	private String schemaName;
	private String databaseName;
	private String catalog;
	private String columnName;
	private String tableName;
	private String[] types;
	private Object object; // 其它未知信息

	/**
	 * [功 能] 执行数据库操作时，传递的参数信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public DBMDataMetaInfo() {
		// connInfo = null;
		schemaName = null;
		databaseName = null;
		catalog = null;
		tableName = null;
		object = null;
		columnName = null;
		types = null;
	}

	/**
	 * [功 能] 获取Catalog名称
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public String getCatalog() {
		return catalog;
	}

	/**
	 * [功 能] 设定Catalog
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	/**
	 * [功 能] 获取Column名称
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * [功 能] 设定Column名称
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * [功 能] 获取数据库名称
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * [功 能] 设定数据库名称
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * [功 能] 获取信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * [功 能] 设定信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * [功 能] 获取Schema名称
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * [功 能] 设定Schema名称
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	/**
	 * [功 能] 获取Table名称
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * [功 能] 设定Table名称
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * [功 能] 获取Types
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public String[] getTypes() {
		return types;
	}

	/**
	 * [功 能] 设定Types
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public void setTypes(String[] types) {
		this.types = types;
	}
}

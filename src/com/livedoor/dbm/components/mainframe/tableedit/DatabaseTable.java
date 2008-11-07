package com.livedoor.dbm.components.mainframe.tableedit;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.DBColumnType;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.db.DBMConnectionManager;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.scripts.SqlBuilderFactory;
import com.livedoor.dbm.util.Convertor;

/**
 * <p>
 * Title: 数据表编辑数据库操作
 * <p>
 * Description: 数据表编辑后对应的数据库操作
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class DatabaseTable {

	private List<List> datas; // 保存从数据库里取得的原始数据

	private List<List> columnsNameAndTypeName; // 保存数据库名的所有字段名和类型名

	private List columnNames; // 保存数据库表的所有字段名

	private int[] columnTypes; // 保存数据库表的所有字段类型

	private ConnectionInfo connInfo;

	private String tableName; //

	private List primarykeys; // 用户选择的数据表中主键的名字

	private DBMSqlExecuter sqlExecuter;

	private int databaseRowCount; // 保存初始加载到JTable里的数据行数.这些行是数据库表中已经存在的行.

	private HashMap deletedRows; // 保存用户删除的行号,只有当用户选择删除的行号小于databaseRowCount时才将行号记录到这个集合里

	private HashMap updatedRows; // 保存用户对JTable中每个cell的最后一次更新的值.

	private DBSession dbSession;

	private String schemaName;

	private String dbName;
	
	private String dbType;

	private int rowsLimit;
	
	private String whereStr;

	/**
	 * Function: 构造一个新的数据库表操作引用
	 * <p>
	 * Description: 构造一个新的数据库表操作引用
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 * 
	 * @param connInfo
	 *            数据库连接新息
	 * @param schemaName
	 *            默认被选择的schema
	 * @param dbName
	 *            默认被选择的database
	 * @param tableName
	 *            默认被选择的table
	 * @param dbSession
	 *            数据库连接session
	 * @throws DBMException
	 */
	public DatabaseTable(ConnectionInfo connInfo, String schemaName, String dbName, String tableName, DBSession dbSession)
			throws DBMException {

		this.connInfo = connInfo;
		this.schemaName = schemaName;
		this.dbName = dbName;
		this.tableName = tableName;
		this.dbSession = dbSession;
		this.rowsLimit = 1000;
		this.whereStr = "";
		this.dbType = connInfo.getDbType();
		this.refresh();

	}

	/**
	 * Function: 获取sql语句
	 * <p>
	 * Description: 获取sql语句
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 * 
	 * @return 一个列表，包涵所有需要执行的sql语句
	 * @throws DBMException
	 */
	@SuppressWarnings("unchecked")
	public List getSql() throws DBMException {

		String sqlStr;
		List allSql = new ArrayList();
		List sql;

		DBMDataMetaInfo dateMetaInfo = new DBMDataMetaInfo();
		dateMetaInfo.setDatabaseName(dbName);
		dateMetaInfo.setSchemaName(schemaName);
		dateMetaInfo.setTableName(tableName);

		List columns = new ArrayList();
		List values;
		for (int i = this.databaseRowCount; i < this.datas.size(); i++) {
			sql = new ArrayList();
			values = datas.get(i);
			columns.clear();
			for (int j = 0; j < values.size(); j++) {
				if (values.get(j) != null) {
					columns.add(this.columnNames.get(j));
					sql.add(values.get(j));
				}
			}
			sqlStr = SqlBuilderFactory.createSqlBuilder(connInfo, dbSession, dateMetaInfo).insertDynamic(columns.toArray());
			sql.add(0, new String(sqlStr));
			allSql.add(sql);
		}

		// 如果原始数据库表没有定义主键,检查用户是否在主键选择页上设置了主键.如果都没有就抛出一个异常
		List userPrimaryKeys = this.primarykeys;
		List primarykey;
		if (userPrimaryKeys.size() < 1) {
			userPrimaryKeys = new ArrayList();
			for (int i = 0; i < this.columnsNameAndTypeName.size(); i++) {
				primarykey = this.columnsNameAndTypeName.get(i);
				if (Boolean.TRUE.equals(primarykey.get(2))) {
					userPrimaryKeys.add(primarykey.get(0));
				}

			}
			if (userPrimaryKeys.size() < 1)
				throw new DBMException("the table haven't primary key,operation failed!!!");
		}

		String[] pksName = new String[userPrimaryKeys.size()];
		int[] colIds = new int[userPrimaryKeys.size()];
		for (int i = 0; i < userPrimaryKeys.size(); i++) {
			pksName[i] = (String) userPrimaryKeys.get(i);
			colIds[i] = columnNames.indexOf(userPrimaryKeys.get(i));// 取得主键对应的列ID
		}

		sqlStr = SqlBuilderFactory.createSqlBuilder(connInfo, dbSession, dateMetaInfo).deleteDynamic(pksName);

		Iterator rowIds;
		rowIds = deletedRows.values().iterator();
		List row;
		int rowId;

		while (rowIds.hasNext()) {
			sql = new ArrayList();
			sql.add(new String(sqlStr));
			rowId = ((Integer) rowIds.next()).intValue();
			row = this.datas.get(rowId);
			for (int i = 0; i < colIds.length; i++) {
				sql.add(row.get(colIds[i]));
			}
			allSql.add(sql);
		}

		sqlStr = "update " + this.tableName + " set ";
		HashMap updateFields;
		Object[] updatedRowId = this.updatedRows.keySet().toArray();
		Object[] updatedFieldsId;
		Object[] updatedValue;

		for (int i = 0; i < updatedRowId.length; i++) {
			if (deletedRows.containsKey(updatedRowId[i]))
				continue;// 如果更新的行已经标注为删除,则不再更新这行
			else {
				sql = new ArrayList();

				updateFields = (HashMap) updatedRows.get(updatedRowId[i]);
				updatedFieldsId = updateFields.keySet().toArray();
				updatedValue = updateFields.values().toArray();
				List columnsAndpks = new ArrayList();
				String[] columnsName = new String[updatedFieldsId.length];

				for (int j = 0; j < updatedFieldsId.length; j++) {

					columnsName[j] = (String) columnNames.get(((Integer) updatedFieldsId[j]).intValue());
					sql.add(updatedValue[j]);
				}

				columnsAndpks.add(pksName);
				columnsAndpks.add(columnsName);
				sqlStr = SqlBuilderFactory.createSqlBuilder(connInfo, dbSession, dateMetaInfo).updateDynamic(columnsAndpks);
				sql.add(0, new String(sqlStr));
				row = this.datas.get(((Integer) updatedRowId[i]).intValue());
				for (int k = 0; k < colIds.length; k++) {
					sql.add(row.get(colIds[k]));
				}
				allSql.add(sql);

			}
		}

		return allSql;
	}

	/**
	 * Function: 执行sql
	 * <p>
	 * Description: 执行sql将数据写入到对应数据库
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 * 
	 * @return sql语句执行结果
	 */
	public String save() {

		List allSql;
		List sql;
		String errorMessage = "";
		String sqlStr = "";
		Object[] params = null;
		try {

			DBMConnectionManager.setAutoCommit(connInfo, dbSession, false);
			allSql = this.getSql();
			for (int i = 0; i < allSql.size(); i++) {
				sql = (List) allSql.get(i);
				sqlStr = (String) sql.remove(0);
				if (sqlStr != null && !"".equals(sqlStr)) {
					params = sql.toArray();
					sqlExecuter.executeUpdate(sqlStr, params);
				}
			}
			DBMConnectionManager.commit(connInfo, dbSession);

		} catch (DBMException e) {

			if (params != null) {
				for (int i = 0; i < params.length; i++)
					if (params[i] == null)
						sqlStr = sqlStr.replaceFirst("\\?", "");
					else
						sqlStr = sqlStr.replaceFirst("\\?", "'" + params[i].toString() + "'");
				errorMessage = sqlStr;
			}
			try {
				DBMConnectionManager.rollback(connInfo, dbSession);
			} catch (DBMException e1) {
				e1.printStackTrace();
			}
			errorMessage = errorMessage + "\n" + e.getMessage();
		}finally{
			try {
				DBMConnectionManager.setAutoCommit(connInfo, dbSession, true);
			} catch (DBMException e) {
				e.printStackTrace();
			}
		}
		return errorMessage;
	}
	/**
	 * Function: 执行sql
	 * <p>
	 * Description: 执行sql将数据写入到对应数据库
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 * 
	 * @return sql语句执行结果
	 */
	@SuppressWarnings("unchecked")
	public void refresh() throws DBMException {

		this.datas = new ArrayList(10);
		this.columnsNameAndTypeName = new ArrayList();
		this.updatedRows = new HashMap();
		this.deletedRows = new HashMap();
		this.columnTypes = null;
		this.columnNames = new ArrayList();
		this.primarykeys = new ArrayList();
		Convertor convertor = new Convertor();

		List dbResult;
		HashMap resultRow;
		sqlExecuter = DBMSqlExecuterFactory.createExecuter(connInfo, dbSession);
		sqlExecuter.changeDatabase(dbName);
		DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
		dbMetaInfo.setDatabaseName(dbName);
		dbMetaInfo.setSchemaName(schemaName);
		dbMetaInfo.setTableName(tableName);
		// 取得所有主键名
		dbResult = sqlExecuter.getPrimaryKeys(dbMetaInfo).getData();
		for (int i = 0; i < dbResult.size(); i++) {
			resultRow = (HashMap) dbResult.get(i);
			this.primarykeys.add(resultRow.get(DBColumnType.COLUMN_NAME));
		}

		// 取得所有列名和列类型
		dbResult = sqlExecuter.getColumns(dbMetaInfo).getData();
		this.columnTypes = new int[dbResult.size()];

		for (int i = 0; i < dbResult.size(); i++) {
			resultRow = (HashMap) dbResult.get(i);
			this.columnNames.add(resultRow.get(DBColumnType.COLUMN_NAME));
			String type = resultRow.get(DBColumnType.DATA_TYPE).toString();
			this.columnTypes[i] = Integer.valueOf(type);
			//把oracle的Data类型转换为jdbc的timestamp类型处理
			if (this.dbType.equals(DBServerType.ORACLE9i) == true && this.columnTypes[i] == Types.DATE)
				this.columnTypes[i] =Types.TIMESTAMP;

			// 用户编辑主键用的
			List row = new ArrayList();
			row.add(resultRow.get(DBColumnType.COLUMN_NAME));
			row.add(resultRow.get(DBColumnType.TYPE_NAME));
			if (primarykeys.contains(row.get(0)))
				row.add(Boolean.valueOf(true));
			else
				row.add(Boolean.valueOf(false));
			this.columnsNameAndTypeName.add(row);
		}

		DBMDataMetaInfo dataMetaInfo = new DBMDataMetaInfo();
		dataMetaInfo.setSchemaName(schemaName);
		dataMetaInfo.setTableName(tableName);
		String sqlStr = SqlBuilderFactory.createSqlBuilder(connInfo, dbSession, dataMetaInfo).selectAll();
		if(whereStr != null && !"".equals(this.whereStr.trim()))
			sqlStr += " where " + whereStr;
		if (rowsLimit < 0)
			dbResult = sqlExecuter.executeQuery(sqlStr).getData();
		else
			dbResult = sqlExecuter.executeQuery(sqlStr, this.rowsLimit).getData();

		this.databaseRowCount = dbResult.size();
		List row;

		for (int i = 0; i < this.databaseRowCount; i++) {
			row = new ArrayList();
			Object[] columns = this.columnNames.toArray();
			resultRow = (HashMap) dbResult.get(i);

			for (int j = 0; j < columns.length; j++) {
				row.add(convertor.object2ConvertObject(resultRow.get(columns[j]), this.columnTypes[j]));
			}
			datas.add(row);
		}
	}

	public List getColumnNames() {
		return columnNames;
	}

	public List<List> getColumnsNameAndTypeName() {
		return columnsNameAndTypeName;
	}

	public int[] getColumnTypes() {
		return columnTypes;
	}

	public List<List> getDatas() {
		return datas;
	}

	public HashMap getDeletedRows() {
		return deletedRows;
	}

	public List getPrimarykeys() {
		return primarykeys;
	}

	public HashMap getUpdatedRows() {
		return updatedRows;
	}

	public void setRowLimit(String rowLimit) {
		if(rowLimit==null || "".equals(rowLimit)){
			this.rowsLimit = -1;
			return;
		}
		if (GenericValidator.isInt(rowLimit))
			this.rowsLimit = Integer.parseInt(rowLimit);
//		if (this.rowsLimit < 0)
//			this.rowsLimit = 0;
	}
	
	public void setWhereStr(String whereStr){
		this.whereStr = whereStr;
	}

}

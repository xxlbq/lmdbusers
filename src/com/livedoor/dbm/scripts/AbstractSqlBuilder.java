package com.livedoor.dbm.scripts;

import java.util.List;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBSession;
/**
 * <p>
 * Description: AbstractSqlBuilder
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
public abstract class AbstractSqlBuilder {

	protected DBMDataMetaInfo dateMetaInfo;
	/**
	 * [機 能] Abstract Sql Builder
	 * <p>
	 * [解 説] Abstract Sql Builder 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @param dateMetaInfo
	 */
	public AbstractSqlBuilder(DBMDataMetaInfo dateMetaInfo) {
		this.dateMetaInfo = dateMetaInfo;

	}

	/**
	 * [解 説] ConnectionInfo class object
	 */
	protected ConnectionInfo conInfo;

	/**
	 * [解 説] DBSession class object
	 */
	protected DBSession dBSession;
	/**
	 * [解 説] mysql:ALTER COLUMN CHARACTER_SET_NAME [解 説]db2:ALTER COLUMN ID SET
	 * DATA TYPE <DATATYPE> [解 説]oracle: MODIFY id <DATATYPE>
	 */
	public abstract String getAlterColumnValues();
	/**
	 * [解 説]mysql:COLUMN CHARACTER_SET_NAME [解 説]oracle:( id ) CASCADE
	 * CONSTRAINT
	 */
	public abstract String getDropColumnValues();
	/**
	 * [解 説]db2:COLUMN ID CHARACTER(10) [解 説]oracle:( id VARCHAR2(25) NOT NULL )
	 * [解 説]mysql:COLUMN CHARACTER_SET_NAME varchar(64) NOT NULL
	 */
	public abstract String getCreateColumnValues();

	public abstract String getSelectColumns();

	public abstract String getTableName();

	public abstract String getCreateTableValues();

	public abstract String getInsertColumnNames();

	public abstract String[] getInsertColumnValues();

	public abstract String getUpdateValues();

	public abstract String getCreateDynamic(List list);

	public abstract String getAlterAdd(List list);

	public abstract String getAlterDrop(List list);

	public abstract String getAlterModify(List list);

	public abstract String getAlterPrimary(List list);
	/**
	 * [解 説]生成createColumn的SQL
	 * 
	 * @return sql create Column
	 */
	public String createColumn() {

		return "ALTER TABLE " + getTableName() + " ADD COLUMN " + getCreateColumnValues();

	}
	/**
	 * [解 説]生成alterColumn的SQL
	 * 
	 * @return sql alter Column
	 */
	public String alterColumn() {

		return "ALTER TABLE " + getTableName() + getAlterColumnValues();

	}
	/**
	 * [解 説]生成dropColumn的SQL
	 * 
	 * @return sql drop Column
	 */
	public String dropColumn() {

		return "ALTER TABLE " + getTableName() + " DROP COLUMN " + getDropColumnValues();

	}
	/**
	 * [解 説]生成select的SQL
	 * 
	 * @return sql select
	 */
	public String select() {

		return "SELECT " + getSelectColumns() + " FROM " + getTableName();

	}
	/**
	 * [解 説]生成selectAll的SQL
	 * 
	 * @return sql selectAll
	 */
	public String selectAll() {

		return "SELECT * " + " FROM " + getTableName();

	}
	/**
	 * [解 説]生成update的SQL
	 * 
	 * @return sql update
	 */
	public String update() {

		return "UPDATE " + getTableName() + " SET " + getUpdateValues() + " WHERE ";

	}
	/**
	 * [解 説]生成delete的SQL
	 * 
	 * @return sql delete
	 */
	public String delete() {

		return "DELETE FROM " + getTableName() + " WHERE ";

	}
	/**
	 * [解 説]生成createTable的SQL
	 * 
	 * @return sql create Table
	 */
	public String createTable() {

		return "CREATE TABLE " + getTableName() + " (" + getCreateTableValues() + ")";

	}
	/**
	 * [解 説]生成alterTable的SQL
	 * 
	 * @return sql alter Table
	 */
	public String alterTable() {

		return "ALTER TABLE " + getTableName() + getAltetTableValue();

	}
	public abstract String getAltetTableValue();
	/**
	 * [解 説]生成dropTable的SQL
	 * 
	 * @return sql drop Table
	 */
	public String dropTable() {

		return "DROP TABLE " + getTableName();

	}
	/**
	 * [解 説]生成insert的SQL
	 * 
	 * @return sql insert
	 */
	public String insert() {

		String sql = "";

		String sqlColumnValues = "(";

		String columnValues[] = getInsertColumnValues();

		for (int i = 0; i < columnValues.length; i++) {

			sqlColumnValues += columnValues[i] + ")";

			if (i < columnValues.length - 1)

				sqlColumnValues += ", (";
		}
		sql = "INSERT INTO " + getTableName() + " (" + getInsertColumnNames() + " )" + " VALUES " + sqlColumnValues;

		return sql;

	}
	/**
	 * [解 説]生成insertDynamic的SQL
	 * 
	 * @return sql insertDynamic
	 */
	public String insertDynamic() {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO " + getTableName() + "(" + getInsertColumnNames() + ") VALUES (");

		String[] columnName = getInsertColumnNames().split(",");
		for (int i = 0; i < columnName.length; i++) {
			sb.append("?");
			if (i < columnName.length - 1)
				sb.append(", ");
		}
		sb.append(")");
		return sb.toString();
	}
	/**
	 * [解 説]生成insertDynamic的SQL
	 * 
	 * @return sql insertDynamic
	 */
	public String insertDynamic(Object[] columns) {

		if (columns == null)
			return "";
		if (columns.length < 1)
			return "";
		StringBuffer sb = new StringBuffer();
		String valueString;
		sb.append("INSERT INTO ");
		sb.append(getTableName());
		sb.append("(");
		valueString = "?";
		sb.append("\"");
		sb.append(columns[0]);
		sb.append("\"");
		for (int i = 1; i < columns.length; i++) {
			sb.append(",");
			sb.append("\"");
			sb.append(columns[i]);
			sb.append("\"");// + getTableName() + "(" +
			// getInsertColumnNames() + ") VALUES (");
			valueString = valueString + ",?";
		}
		sb.append(") values (");
		sb.append(valueString);
		sb.append(")");
		return sb.toString();
	}

	/**
	 * [解 説]生成updateDynamic的SQL 通过传入的list构造update SQL. list[0]:where 后面的字段数组
	 * list[1]:set 后面的字段数组
	 * 
	 * @return sql update Dynamic
	 */
	public String updateDynamic(List list) {

		String[] arrayPk = (String[]) list.get(0);
		String[] arrayColumnName = (String[]) list.get(1);

		StringBuffer pk = new StringBuffer();
		StringBuffer columnName = new StringBuffer();

		if (arrayPk != null && arrayColumnName != null) {

			for (int i = 0; i < arrayPk.length; i++) {
				pk.append("\"" + arrayPk[i] + "\" = ? ");
				if (i < arrayPk.length - 1)
					pk.append(" AND ");
			}

			for (int i = 0; i < arrayColumnName.length; i++) {
				columnName.append("\"" + arrayColumnName[i] + "\" = ? ");
				if (i < arrayColumnName.length - 1)
					columnName.append(", ");
			}
		}

		return "UPDATE " + getTableName() + " SET " + columnName + " WHERE " + pk;

	}
	/**
	 * [解 説]生成createDynamic的SQL
	 * 
	 * @return sql create Dynamic
	 */
	public String createDynamic(List list) {

		return "CREATE TABLE  ( " + getCreateDynamic(list) + " ) ";

	}
	/**
	 * [解 説]生成deleteDynamic的SQL 通过传入的where 后面的字段数组构造delete SQL.
	 * 
	 * @return sql delete Dynamic
	 */
	public String deleteDynamic(String[] arrayPk) {

		StringBuffer pk = new StringBuffer();

		if (arrayPk != null) {

			for (int i = 0; i < arrayPk.length; i++) {
				pk.append("\"" + arrayPk[i] + "\" = ? ");
				if (i < arrayPk.length - 1)
					pk.append(" AND ");
			}
		}
		return "DELETE FROM " + getTableName() + " WHERE " + pk;
	}
	/**
	 * [機 能] setting ConnectionInfo
	 * <p>
	 * [解 説] setting ConnectionInfo class object。
	 * <p>
	 * [備 考] なし
	 * <p>
	 * [作成日付] 2006/09/16
	 * <p>
	 * [更新日付]
	 * <p>
	 * 
	 * @param conInfo
	 *            ConnectionInfo object
	 *            <p>
	 * 
	 * @return void
	 *         <p>
	 */
	public void setConnectionInfo(ConnectionInfo conInfo) {
		this.conInfo = conInfo;
	}
	/**
	 * [機 能] getting ConnectionInfo
	 * <p>
	 * [解 説] getting ConnectionInfo class object。
	 * <p>
	 * [備 考] なし
	 * <p>
	 * [作成日付] 2006/09/16
	 * <p>
	 * [更新日付]
	 * <p>
	 * 
	 * @return conInfo ConnectionInfo object
	 *         <p>
	 */
	public ConnectionInfo getConnectionInfo() {
		return this.conInfo;
	}
	/**
	 * [機 能] setting DBSession
	 * <p>
	 * [解 説] setting DBSession class object。
	 * <p>
	 * [備 考] なし
	 * <p>
	 * [作成日付] 2006/09/16
	 * <p>
	 * [更新日付]
	 * <p>
	 * 
	 * @param dBSession
	 *            DBSession object
	 *            <p>
	 * 
	 * @return void
	 *         <p>
	 */
	public void setDBSession(DBSession dBSession) {
		this.dBSession = dBSession;
	}

	/**
	 * [機 能] getting DBSession
	 * <p>
	 * [解 説] getting DBSession class object。
	 * <p>
	 * [備 考] なし
	 * <p>
	 * [作成日付] 2006/09/16
	 * <p>
	 * [更新日付]
	 * <p>
	 * 
	 * @return dBSession DBSession object
	 *         <p>
	 */
	public DBSession getDBSession() {
		return this.dBSession;
	}
	/**
	 * [解 説]生成alterAdd的SQL
	 * 
	 * @return sql alter Add
	 */
	public String alterAdd(List list) {
		return getAlterAdd(list);
	}
	/**
	 * [解 説]生成alterDrop的SQL
	 * 
	 * @return sql alter Drop
	 */
	public String alterDrop(List list) {
		return getAlterDrop(list);
	}
	/**
	 * [解 説]生成alterModify的SQL
	 * 
	 * @return sql alter Modify
	 */
	public String alterModify(List list) {
		return getAlterModify(list);
	}
	/**
	 * [解 説]生成alterPrimary的SQL
	 * 
	 * @return sql alter Primary
	 */
	public String alterPrimary(List list) {
		return getAlterPrimary(list);
	}

}

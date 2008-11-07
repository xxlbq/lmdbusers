package com.livedoor.dbm.scripts;

import java.util.List;
import java.util.Map;

import com.livedoor.dbm.constants.ColumnProperty;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBMDataResult;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.util.Convertor;

/**
 * <p>
 * Description: MysqlBuilder
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
public class MysqlBuilder extends AbstractSqlBuilder {

	public MysqlBuilder(DBMDataMetaInfo dateMetaInfo) {
		super(dateMetaInfo);
	}

	@Override
	public String getAlterColumnValues() {
		String s = dateMetaInfo.getColumnName().substring(0, dateMetaInfo.getColumnName().indexOf("("));
		System.out.println(s.trim());
		return " MODIFY COLUMN `" + s.trim() + "`";

	}

	@Override
	public String getCreateColumnValues() {

		String rs = "";
		String value = "";
		String t = "";
		String h = "";
		String columnName = "";

		try {
			DBMSqlExecuter dde = DBMSqlExecuterFactory.createExecuter(getConnectionInfo(), getDBSession());
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();

			dbMetaInfo.setTableName(dateMetaInfo.getTableName());
			dbMetaInfo.setDatabaseName(dateMetaInfo.getDatabaseName());
			DBMDataResult ddr = dde.getColumns(dbMetaInfo);

			List s = ddr.getData();
			List pk = dde.getPrimaryKeys(dbMetaInfo).getData();

			String s1 = dateMetaInfo.getColumnName().substring(0, dateMetaInfo.getColumnName().indexOf("("));
			s1 = s1.trim();

			for (int i = 0; i < s.size(); i++) {
				Map map = (Map) s.get(i);
				columnName = map.get("COLUMN_NAME").toString().trim();
				String typeName = map.get("TYPE_NAME").toString().trim();
				if (s1.equals(columnName)) {
					if (typeName.equals("bigint") || typeName.equals("char") || typeName.equals("int") || typeName.equals("integer")
							|| typeName.equals("smallint") || typeName.equals("tinyint") || typeName.equals("varchar")) {
						value = "(" + map.get("CHAR_OCTET_LENGTH") + ")";
					} else if (typeName.equals("decimal") || typeName.equals("numeric")) {
						value = "(" + map.get("COLUMN_SIZE") + "," + map.get("DECIMAL_DIGITS") + ")";
					} else
						value = " ";

					if (map.get("IS_NULLABLE").equals("YES")) {

						rs += "`" + columnName + "` " + map.get("TYPE_NAME") + value + ",";
					} else
						rs += "`" + columnName + "` " + map.get("TYPE_NAME") + value + " NOT NULL,";

					if (pk.size() == 1 && columnName.equals(((Map) pk.get(0)).get("COLUMN_NAME"))) {
						t = "`" + columnName + "`";
						h = ",PRIMARY KEY (" + t + ") ";
					}
				}

			}

		} catch (DBMException e) {

		}
		if (!"".equals(rs))
			rs = rs.substring(0, rs.length() - 1);

		return rs + h;

	}

	@Override
	public String getCreateTableValues() {
		return null;
	}

	@Override
	public String getDropColumnValues() {
		String s = dateMetaInfo.getColumnName().substring(0, dateMetaInfo.getColumnName().indexOf("("));

		return "`" + s.trim() + "`";
	}
	public String dropColumn() {

		return "ALTER TABLE " + getTableName() + " DROP COLUMN " + getDropColumnValues();

	}
	public String select() {

		return "SELECT " + getSelectColumns() + " FROM " + getTableName();

	}

	@Override
	public String getInsertColumnNames() {

		return getAllColumnNames();
	}

	@Override
	public String[] getInsertColumnValues() {

		String valueresult = "";
		String def = "";

		try {
			DBMSqlExecuter dde = DBMSqlExecuterFactory.createExecuter(getConnectionInfo(), getDBSession());
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
			dbMetaInfo.setDatabaseName(dateMetaInfo.getDatabaseName());
			dbMetaInfo.setTableName(dateMetaInfo.getTableName());
			DBMDataResult ddr = dde.getColumns(dbMetaInfo);
			List s = ddr.getData();

			for (int i = 0; i < s.size(); i++) {

				Map map = (Map) s.get(i);

				String value = (String) map.get("TYPE_NAME");
				def = (String) map.get("COLUMN_DEF");
				if (def != null && !def.equals("")) {
					if (value.equalsIgnoreCase("VARCHAR") || value.equalsIgnoreCase("TINYTEXT") || value.equalsIgnoreCase("TIMESTAMP")
							|| value.equalsIgnoreCase("TEXT") || value.equalsIgnoreCase("MEDIUMTEXT") || value.equalsIgnoreCase("LONGTEXT")
							|| value.equalsIgnoreCase("DATETIME") || value.equalsIgnoreCase("DATE") || value.equalsIgnoreCase("CHAR")
							|| value.equalsIgnoreCase("TIME")) {

						if (value.equalsIgnoreCase("DATETIME") || value.equalsIgnoreCase("DATE")) {
							Convertor convertor = new Convertor();

							Object o1 = convertor.string2Date(def);
							convertor.setDatetimeFormat("yyyy/MM/dd");
							Object o2 = convertor.string2Date(def);

							if (o1 == null && o2 == null) {
								valueresult += def;
							} else {
								valueresult += "'" + def + "'";
							}
						} else if (value.equalsIgnoreCase("TIME")) {
							Convertor convertor = new Convertor();

							Object o1 = convertor.string2Time(def);

							if (o1 == null) {
								valueresult += def;
							} else {
								valueresult += "'" + def + "'";
							}
						} else if (value.equalsIgnoreCase("TIMESTAMP")) {

							Convertor convertor = new Convertor();

							Object o1 = convertor.string2Datetime(def);
							convertor.setDatetimeFormat("yyyy/MM/dd HH:mm:ss");
							Object o2 = convertor.string2Datetime(def);
							if (o1 == null && o2 == null) {
								valueresult += def;
							} else {
								valueresult += "'" + def + "'";
							}

						} else {

							valueresult += "'" + def + "'";
						}
					} else
						valueresult += def;
				} else {

					if (value.equalsIgnoreCase("VARCHAR"))
						valueresult += "''";
					else if (value.equalsIgnoreCase("TINYTEXT"))
						valueresult += "''";
					else if (value.equalsIgnoreCase("TINYINT"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("TINYBLOB"))
						valueresult += "NULL";
					else if (value.equalsIgnoreCase("TIMESTAMP")) {
						valueresult += "CURRENT_TIMESTAMP";
					} else if (value.equalsIgnoreCase("TIME")) {
						valueresult += "CURRENT_TIME";
					} else if (value.equalsIgnoreCase("TEXT"))
						valueresult += "''";

					else if (value.equalsIgnoreCase("SMALLINT"))
						valueresult += "0";
					/*
					 * else if (value.equalsIgnoreCase("SET")) valueresult +=
					 * "''";
					 */
					else if (value.equalsIgnoreCase("REAL"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("NUMERIC"))
						valueresult += "0";

					else if (value.equalsIgnoreCase("MEDIUMTEXT"))
						valueresult += "''";

					else if (value.equalsIgnoreCase("MEDIUMINT"))
						valueresult += "0";

					else if (value.equalsIgnoreCase("MEDIUMBLOB"))
						valueresult += "NULL";

					else if (value.equalsIgnoreCase("LONGTEXT"))
						valueresult += "''";

					else if (value.equalsIgnoreCase("LONGBLOB"))
						valueresult += "NULL";

					else if (value.equalsIgnoreCase("INTEGER"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("INT"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("FLOAT"))
						valueresult += "0";
					/*
					 * else if (value.equalsIgnoreCase("ENUM")) valueresult +=
					 * "''";
					 */
					else if (value.equalsIgnoreCase("DOUBLE"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("DECIMAL"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("DATETIME")) {
						valueresult += "CURRENT_TIMESTAMP";
					} else if (value.equalsIgnoreCase("DATE")) {
						valueresult += "CURRENT_DATE";

					} else if (value.equalsIgnoreCase("CHAR"))
						valueresult += "''";
					else if (value.equalsIgnoreCase("BLOB"))
						valueresult += "NULL";
					else if (value.equalsIgnoreCase("BIGINT"))
						valueresult += "0";
				}
				valueresult += ",";
			}

		} catch (DBMException e) {

		}
		if (!"".equals(valueresult))
			valueresult = valueresult.substring(0, valueresult.length() - 1);

		String[] retString = {valueresult};
		return retString;

	}

	@Override
	public String getSelectColumns() {
		return getAllColumnNames();
	}

	@Override
	public String getTableName() {
		return "`" + dateMetaInfo.getTableName() + "`";
	}
	public String alterColumn() {

		return "ALTER TABLE " + getTableName() + getAlterColumnValues();

	}

	@Override
	public String getUpdateValues() {

		String rs = "";
		String valueresult = "";
		String def = "";

		try {
			DBMSqlExecuter dde = DBMSqlExecuterFactory.createExecuter(getConnectionInfo(), getDBSession());
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
			dbMetaInfo.setDatabaseName(dateMetaInfo.getDatabaseName());
			dbMetaInfo.setTableName(dateMetaInfo.getTableName());
			DBMDataResult ddr = dde.getColumns(dbMetaInfo);
			List s = ddr.getData();

			for (int i = 0; i < s.size(); i++) {
				valueresult = "";
				Map map = (Map) s.get(i);
				String value = (String) map.get("TYPE_NAME");
				def = (String) map.get("COLUMN_DEF");
				if (def != null && !def.equals("")) {
					if (value.equalsIgnoreCase("VARCHAR") || value.equalsIgnoreCase("TINYTEXT") || value.equalsIgnoreCase("TIMESTAMP")
							|| value.equalsIgnoreCase("TEXT") || value.equalsIgnoreCase("MEDIUMTEXT") || value.equalsIgnoreCase("LONGTEXT")
							|| value.equalsIgnoreCase("DATETIME") || value.equalsIgnoreCase("DATE") || value.equalsIgnoreCase("CHAR")
							|| value.equalsIgnoreCase("TIME")) {

						if (value.equalsIgnoreCase("DATETIME") || value.equalsIgnoreCase("DATE")) {
							Convertor convertor = new Convertor();

							Object o1 = convertor.string2Date(def);
							convertor.setDatetimeFormat("yyyy/MM/dd");
							Object o2 = convertor.string2Date(def);

							if (o1 == null && o2 == null) {
								valueresult += def;
							} else {
								valueresult += "'" + def + "'";
							}
						} else if (value.equalsIgnoreCase("TIME")) {
							Convertor convertor = new Convertor();

							Object o1 = convertor.string2Time(def);

							if (o1 == null) {
								valueresult += def;
							} else {
								valueresult += "'" + def + "'";
							}
						} else if (value.equalsIgnoreCase("TIMESTAMP")) {

							Convertor convertor = new Convertor();

							Object o1 = convertor.string2Datetime(def);
							convertor.setDatetimeFormat("yyyy/MM/dd HH:mm:ss");
							Object o2 = convertor.string2Datetime(def);
							if (o1 == null && o2 == null) {
								valueresult += def;
							} else {
								valueresult += "'" + def + "'";
							}

						} else {

							valueresult += "'" + def + "'";
						}
					} else
						valueresult += def;
				} else

				if (value.equalsIgnoreCase("VARCHAR"))
					valueresult = "''";
				else if (value.equalsIgnoreCase("TINYTEXT"))
					valueresult = "''";
				else if (value.equalsIgnoreCase("TINYINT"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("TINYBLOB"))
					valueresult = "NULL";
				else if (value.equalsIgnoreCase("TIMESTAMP")) {
					valueresult += "CURRENT_TIMESTAMP";
				} else if (value.equalsIgnoreCase("TIME")) {
					valueresult += "CURRENT_TIME";
				} else if (value.equalsIgnoreCase("TEXT"))
					valueresult = "''";

				else if (value.equalsIgnoreCase("SMALLINT"))
					valueresult = "0";
				/*
				 * else if (value.equalsIgnoreCase("SET")) valueresult += "''";
				 */
				else if (value.equalsIgnoreCase("REAL"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("NUMERIC"))
					valueresult = "0";

				else if (value.equalsIgnoreCase("MEDIUMTEXT"))
					valueresult = "''";

				else if (value.equalsIgnoreCase("MEDIUMINT"))
					valueresult = "0";

				else if (value.equalsIgnoreCase("MEDIUMBLOB"))
					valueresult = "NULL";

				else if (value.equalsIgnoreCase("LONGTEXT"))
					valueresult = "''";

				else if (value.equalsIgnoreCase("LONGBLOB"))
					valueresult = "NULL";

				else if (value.equalsIgnoreCase("INTEGER"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("INT"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("FLOAT"))
					valueresult = "0";
				/*
				 * else if (value.equalsIgnoreCase("ENUM")) valueresult += "''";
				 */
				else if (value.equalsIgnoreCase("DOUBLE"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("DECIMAL"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("DATETIME")) {
					valueresult += "CURRENT_TIMESTAMP";
				} else if (value.equalsIgnoreCase("DATE")) {
					valueresult += "CURRENT_DATE";

				} else if (value.equalsIgnoreCase("CHAR"))
					valueresult = "''";
				else if (value.equalsIgnoreCase("BLOB"))
					valueresult = "NULL";
				else if (value.equalsIgnoreCase("BIGINT"))
					valueresult = "0";

				rs += "`" + map.get("COLUMN_NAME") + "`=" + valueresult + ",";

			}

		} catch (DBMException e) {

		}
		if (!"".equals(rs))
			rs = rs.substring(0, rs.length() - 1);

		return rs;

	}

	@Override
	public String createTable() {
		String result = "";
		String sql = "SHOW CREATE TABLE " + dateMetaInfo.getTableName();

		try {
			DBMDataResult data = DBMSqlExecuterFactory.createExecuter(conInfo, dBSession).executeQuery(sql);
			List dataList = data.getData();
			if (dataList.size() > 0) {
				result = (String) ((Map) dataList.get(0)).get("Create Table");
			}
		} catch (DBMException e) {

		}
		return result;

	}

	private String getAllColumnNames() {

		String result = "";

		try {
			DBMSqlExecuter dde = DBMSqlExecuterFactory.createExecuter(getConnectionInfo(), getDBSession());
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
			dbMetaInfo.setDatabaseName(dateMetaInfo.getDatabaseName());
			dbMetaInfo.setTableName(dateMetaInfo.getTableName());
			DBMDataResult ddr = dde.getColumns(dbMetaInfo);
			List s = ddr.getData();

			for (int i = 0; i < s.size(); i++) {
				Map map = (Map) s.get(i);

				result += "`" + map.get("COLUMN_NAME") + "`,";

			}
		} catch (DBMException e) {

		}
		if (!"".equals(result))
			result = result.substring(0, result.length() - 1);

		return result;
	}

	@Override
	public String getCreateDynamic(List list) {

		String columnname = "";
		String a = "";
		String b = "";
		String c = "";
		String d = "";
		String e = "";
		String f = "";
		String h = "";
		String t = "";
		String result = "";
		String table = "`" + dateMetaInfo.getTableName() + "`";

		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			if (map.get("Column Name") != "") {
				columnname = "`" + map.get("Column Name") + "` ";
				if (map.get(ColumnProperty.TYPE) != "" || map.get(ColumnProperty.TYPE) != null) {
					f = " " + map.get(ColumnProperty.TYPE).toString();
				} else
					f = "";

				if (map.get(ColumnProperty.LENGTH) == "") {
					a = "";

				} else
					a = "( " + map.get(ColumnProperty.LENGTH).toString() + " ) ";

				if (map.get(ColumnProperty.NULLABLE).equals(Boolean.TRUE)) {
					b = " NULL ";
				} else
					b = " NOT NULL ";
				if (map.get("Default Value").equals("")) {
					c = "";

				} else {
					String def = (String) map.get("Default Value");
					if ("DATETIME".equalsIgnoreCase((String) map.get(ColumnProperty.TYPE))
							|| "DATE".equalsIgnoreCase((String) map.get(ColumnProperty.TYPE))) {
						Convertor convertor = new Convertor();

						Object o1 = convertor.string2Date(def);
						convertor.setDatetimeFormat("yyyy/MM/dd");
						Object o2 = convertor.string2Date(def);

						if (o1 == null && o2 == null) {
							c = " DEFAULT " + def;
						} else {
							c = " DEFAULT '" + def + "'";
						}
					} else if ("TIME".equalsIgnoreCase((String) map.get(ColumnProperty.TYPE))) {
						Convertor convertor = new Convertor();

						Object o1 = convertor.string2Time(def);

						if (o1 == null) {
							c = " DEFAULT " + def;
						} else {
							c = " DEFAULT '" + def + "'";
						}
					} else if ("TIMESTAMP".equalsIgnoreCase((String) map.get(ColumnProperty.TYPE))) {

						Convertor convertor = new Convertor();

						Object o1 = convertor.string2Datetime(def);
						convertor.setDatetimeFormat("yyyy/MM/dd HH:mm:ss");
						Object o2 = convertor.string2Datetime(def);
						if (o1 == null && o2 == null) {
							c = " DEFAULT " + def;
						} else {
							c = " DEFAULT '" + def + "'";
						}

					} else

						c = " DEFAULT '" + map.get("Default Value") + "'";

				}
				if (map.get(ColumnProperty.PRECISION).equals("")) {
					d = "";
				} else
					d = "(" + map.get(ColumnProperty.PRECISION) + "," + map.get(ColumnProperty.SCALE) + ") ";
				if (map.get(ColumnProperty.IDENTITY).toString().equals(ColumnProperty.IS_IDENTITY)) {
					e = " AUTO_INCREMENT ";
				} else
					e = "";
				if (map.get("Primary Key").equals(Boolean.TRUE)) {
					t += map.get("Column Name").toString().trim() + ",";
					h = ", PRIMARY KEY (" + t.substring(0, t.length() - 1) + ")";
				}

				result += columnname + f + a + d + e + c + b + " ,";

			}

		}

		if (!"".equals(result))
			result = result.substring(0, result.length() - 1);

		return table + " ( " + result + h + " ) ";

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
		sb.append("`");
		sb.append(columns[0]);
		sb.append("`");
		for (int i = 1; i < columns.length; i++) {
			sb.append(",");
			sb.append("`");
			sb.append(columns[i]);
			sb.append("`");
			valueString = valueString + ",?";
		}
		sb.append(") values (");
		sb.append(valueString);
		sb.append(")");
		return sb.toString();
	}
	public String updateDynamic(List list) {

		String[] arrayPk = (String[]) list.get(0);
		String[] arrayColumnName = (String[]) list.get(1);

		StringBuffer pk = new StringBuffer();
		StringBuffer columnName = new StringBuffer();

		if (arrayPk != null && arrayColumnName != null) {

			for (int i = 0; i < arrayPk.length; i++) {
				pk.append("`" + arrayPk[i] + "` = ? ");
				if (i < arrayPk.length - 1)
					pk.append(" AND ");
			}

			for (int i = 0; i < arrayColumnName.length; i++) {
				columnName.append("`" + arrayColumnName[i] + "` = ? ");
				if (i < arrayColumnName.length - 1)
					columnName.append(", ");
			}
		}

		return "UPDATE " + getTableName() + " SET " + columnName + " WHERE " + pk;

	}
	public String deleteDynamic(String[] arrayPk) {

		StringBuffer pk = new StringBuffer();

		if (arrayPk != null) {

			for (int i = 0; i < arrayPk.length; i++) {
				pk.append("`" + arrayPk[i] + "` = ? ");
				if (i < arrayPk.length - 1)
					pk.append(" AND ");
			}
		}
		return "DELETE FROM " + getTableName() + " WHERE " + pk;
	}

	@Override
	public String getAlterAdd(List list) {

		String columnname = "";
		String a = "";
		String b = "";
		String c = "";
		String d = "";
		String e = "";
		String f = "";

		String result = "";
		String table = dateMetaInfo.getTableName();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			if (map.get("Column Name") != "") {
				columnname = "`" + map.get("Column Name") + "` ";
				if (map.get(ColumnProperty.TYPE) != "" || map.get(ColumnProperty.TYPE) != null) {
					f = map.get(ColumnProperty.TYPE).toString();
				} else
					f = "";

				if (map.get(ColumnProperty.LENGTH) == "") {
					a = "";

				} else
					a = "( " + map.get(ColumnProperty.LENGTH).toString() + " ) ";

				if (map.get(ColumnProperty.NULLABLE).equals(Boolean.TRUE)) {
					b = " NULL ";
				} else
					b = " NOT NULL ";
				if (map.get("Default Value").equals("")) {
					c = "";

				} else{

					String def = (String) map.get("Default Value");
					if ("DATETIME".equalsIgnoreCase((String) map.get(ColumnProperty.TYPE))
							|| "DATE".equalsIgnoreCase((String) map.get(ColumnProperty.TYPE))) {
						Convertor convertor = new Convertor();

						Object o1 = convertor.string2Date(def);
						convertor.setDatetimeFormat("yyyy/MM/dd");
						Object o2 = convertor.string2Date(def);

						if (o1 == null && o2 == null) {
							c = " DEFAULT " + def;
						} else {
							c = " DEFAULT '" + def + "'";
						}
					} else if ("TIME".equalsIgnoreCase((String) map.get(ColumnProperty.TYPE))) {
						Convertor convertor = new Convertor();

						Object o1 = convertor.string2Time(def);

						if (o1 == null) {
							c = " DEFAULT " + def;
						} else {
							c = " DEFAULT '" + def + "'";
						}
					} else if ("TIMESTAMP".equalsIgnoreCase((String) map.get(ColumnProperty.TYPE))) {

						Convertor convertor = new Convertor();

						Object o1 = convertor.string2Datetime(def);
						convertor.setDatetimeFormat("yyyy/MM/dd HH:mm:ss");
						Object o2 = convertor.string2Datetime(def);
						if (o1 == null && o2 == null) {
							c = " DEFAULT " + def;
						} else {
							c = " DEFAULT '" + def + "'";
						}

					} else

						c = " DEFAULT '" + map.get("Default Value") + "'";

				
				}
				if (map.get(ColumnProperty.PRECISION).equals("")) {
					d = "";
				} else
					d = "(" + map.get(ColumnProperty.PRECISION) + "," + map.get(ColumnProperty.SCALE) + ") ";
				if (map.get(ColumnProperty.IDENTITY).toString().equals(ColumnProperty.IS_IDENTITY)) {
					e = " AUTO_INCREMENT ";
				} else
					e = "";

				result += "ALTER TABLE " + table + " ADD COLUMN " + columnname + f + a + d + e + c + b + "\n";

			}

		}
		return result;

	}

	@Override
	public String getAlterDrop(List list) {
		String result = "";
		String table = dateMetaInfo.getTableName();
		for (int i = 0; i < list.size(); i++) {
			String columnName = "`" + list.get(i) + "`";
			result += "ALTER TABLE " + table + " DROP COLUMN " + columnName + "\n";
		}
		return result;
	}

	@Override
	public String getAlterModify(List list) {

		// String columnname = "";
		String a = "";
		String b = "";
		String c = "";
		String d = "";
		String e = "";
		String f = "";
		String h = "";

		String result = "";
		String table = "`" + dateMetaInfo.getTableName() + "`";
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);

			// columnname = "`" + map.get(ColumnProperty.NAME) + "` ";
			if (map.get(ColumnProperty.TYPE) != "" || map.get(ColumnProperty.TYPE) != null) {
				f = " " + map.get(ColumnProperty.TYPE);
			} else
				f = "";

			if (map.get(ColumnProperty.LENGTH) == "") {
				a = "";

			} else
				a = "( " + map.get(ColumnProperty.LENGTH).toString() + " ) ";

			if ("".equals(map.get(ColumnProperty.DEFAULT)) || map.get(ColumnProperty.DEFAULT) == null) {
				c = "";

			} else {


				String def = (String) map.get("Default Value");
				if ("DATETIME".equalsIgnoreCase((String) map.get(ColumnProperty.TYPE))
						|| "DATE".equalsIgnoreCase((String) map.get(ColumnProperty.TYPE))) {
					Convertor convertor = new Convertor();

					Object o1 = convertor.string2Date(def);
					convertor.setDatetimeFormat("yyyy/MM/dd");
					Object o2 = convertor.string2Date(def);

					if (o1 == null && o2 == null) {
						c = " DEFAULT " + def;
					} else {
						c = " DEFAULT '" + def + "'";
					}
				} else if ("TIME".equalsIgnoreCase((String) map.get(ColumnProperty.TYPE))) {
					Convertor convertor = new Convertor();

					Object o1 = convertor.string2Time(def);

					if (o1 == null) {
						c = " DEFAULT " + def;
					} else {
						c = " DEFAULT '" + def + "'";
					}
				} else if ("TIMESTAMP".equalsIgnoreCase((String) map.get(ColumnProperty.TYPE))) {

					Convertor convertor = new Convertor();

					Object o1 = convertor.string2Datetime(def);
					convertor.setDatetimeFormat("yyyy/MM/dd HH:mm:ss");
					Object o2 = convertor.string2Datetime(def);
					if (o1 == null && o2 == null) {
						c = " DEFAULT " + def;
					} else {
						c = " DEFAULT '" + def + "'";
					}

				} else

					c = " DEFAULT '" + map.get("Default Value") + "'";

			}
			if (map.get(ColumnProperty.PRECISION).equals("")) {
				d = "";
			} else
				d = "(" + map.get(ColumnProperty.PRECISION) + "," + map.get(ColumnProperty.SCALE) + ") ";
			if (map.get(ColumnProperty.ALLOW_NULL) != map.get(ColumnProperty.NULLABLE)) {
				if (map.get("Allow Nulls").equals(Boolean.TRUE)) {
					b = " NULL ";
				} else
					b = " NOT NULL ";
			} else
				b = "";
			if (map.get(ColumnProperty.IDENTITY).toString().equals(ColumnProperty.IS_IDENTITY)) {
				e = " AUTO_INCREMENT ";
			} else
				e = "";

			h = "ALTER TABLE " + table + " CHANGE COLUMN `" + map.get(ColumnProperty.OLD_COLUMN_NAME) + "` `"
					+ map.get(ColumnProperty.NAME) + "`" + f + a + d + e + c + b + "\n";

			result += h;

		}
		return result;

	}

	@Override
	public String getAlterPrimary(List list) {

		String result = "";
		String t = "";
		String h = "";
		String sql = "";
		String table = dateMetaInfo.getTableName();

		for (int i = 0; i < list.size(); i++) {
			String pkColumnName = "`" + list.get(i).toString().trim() + "`";
			t += pkColumnName + ",";
		}
		if (!"".equals(t))
			h = " PRIMARY KEY (" + t.substring(0, t.length() - 1) + ")";
		try {
			DBMDataResult data = DBMSqlExecuterFactory.createExecuter(conInfo, dBSession).getPrimaryKeys(dateMetaInfo);
			List dataList = data.getData();
			if (dataList.size() > 0) {
				result = "ALTER TABLE " + table + " DROP PRIMARY KEY\n";
			}

			if (!"".equals(h))
				sql = "ALTER TABLE " + table + " ADD  CONSTRAINT " + h;
		} catch (DBMException e) {

		}
		return result + sql + "\n";

	}
	public String createDynamic(List list) {

		return "CREATE TABLE " + getCreateDynamic(list);

	}

	@Override
	public String getAltetTableValue() {
		return " ADD COLUMN " + "<COLUMN_NAME> <DATA_TYPE>";
	}
	public String alterTable() {

		return "ALTER TABLE " + getTableName() + getAltetTableValue();

	}

}

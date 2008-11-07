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
 * Description: Db2Builder
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
public class Db2Builder extends AbstractSqlBuilder {
	/**
	 * [機 能] Db2 Builder
	 * <p>
	 * [解 説] Db2 Builder 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @param dateMetaInfo
	 */
	public Db2Builder(DBMDataMetaInfo dateMetaInfo) {
		super(dateMetaInfo);
	}
	/**
	 * [機 能] get AlterColumnValues
	 * <p>
	 * [解 説] get AlterColumnValues 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @return getAlterColumnValues sql(String)
	 */
	@Override
	public String getAlterColumnValues() {
		String rs = "";

		rs = dateMetaInfo.getColumnName().substring(0, dateMetaInfo.getColumnName().indexOf("("));
		return " ALTER COLUMN \"" + rs.trim() + "\" SET DATA TYPE <DATA TYPE>";

	}
	/**
	 * [機 能] get CreateColumnValues
	 * <p>
	 * [解 説] get CreateColumnValues 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @return getCreateColumnValues sql(String)
	 */
	@Override
	public String getCreateColumnValues() {

		String rs = "";
		String value = "";
		String t = "";
		String h = "";

		try {
			DBMSqlExecuter dde = DBMSqlExecuterFactory.createExecuter(getConnectionInfo(), getDBSession());
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();

			dbMetaInfo.setSchemaName(dateMetaInfo.getSchemaName());
			dbMetaInfo.setTableName(dateMetaInfo.getTableName());
			DBMDataResult ddr = dde.getColumns(dbMetaInfo);

			List s = ddr.getData();
			List pk = dde.getPrimaryKeys(dbMetaInfo).getData();
			String s1 = dateMetaInfo.getColumnName().substring(0, dateMetaInfo.getColumnName().indexOf("("));
			s1 = s1.trim();
			for (int i = 0; i < s.size(); i++) {
				Map map = (Map) s.get(i);
				String columnName = map.get("COLUMN_NAME").toString();
				String typeName = map.get("TYPE_NAME").toString();
				if (s1.equals(columnName)) {

					if (typeName.equals("BLOB") || typeName.equals("CHARACTER") || typeName.equals("CLOB") || typeName.equals("VARCHAR")) {
						value = "(" + map.get("CHAR_OCTET_LENGTH") + ")";
					} else if (typeName.equals("DECIMAL")) {
						value = "(" + map.get("COLUMN_SIZE") + "," + map.get("DECIMAL_DIGITS") + ")";
					} else
						value = " ";

					if (map.get("IS_NULLABLE").equals("YES")) {

						rs += "\"" + map.get("COLUMN_NAME") + "\"" + " " + map.get("TYPE_NAME") + value + ",";
					} else
						rs += "\"" + map.get("COLUMN_NAME") + "\"" + " " + map.get("TYPE_NAME") + value + " NOT NULL,";
					if (pk.size() == 1 && columnName.equals(((Map) pk.get(0)).get("COLUMN_NAME"))) {
						t = "\"" + columnName + "\"";
						h = ",PRIMARY KEY (" + t + ") ";
					}
					if (!"".equals(rs))
						rs = rs.substring(0, rs.length() - 1);

				}
			}

		} catch (DBMException e) {

		}

		return rs + h;

	}
	/**
	 * [機 能] get CreateTableValues
	 * <p>
	 * [解 説] get CreateTableValues 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @return getCreateTableValues sql(String)
	 */
	@Override
	public String getCreateTableValues() {

		String rs = "";
		String value = "";
		String t = "";
		String h = "";
		String def = "";
		

		try {
			DBMSqlExecuter dde = DBMSqlExecuterFactory.createExecuter(getConnectionInfo(), getDBSession());
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
			dbMetaInfo.setSchemaName(dateMetaInfo.getSchemaName());
			dbMetaInfo.setTableName(dateMetaInfo.getTableName());
			DBMDataResult ddr = dde.getColumns(dbMetaInfo);
			List s = ddr.getData();
			List pk = dde.getPrimaryKeys(dbMetaInfo).getData();

			for (int i = 0; i < s.size(); i++) {
				Map map = (Map) s.get(i);
				String valueresult = "";
				String typeName = map.get("TYPE_NAME").toString();
				def = (String) map.get("COLUMN_DEF");
				if (def != null&& !def.equals("")) {
					
						valueresult = "DEFAULT " + def.trim();

				}

				if (typeName.equals("BLOB") || typeName.equals("CHARACTER") || typeName.equals("CLOB") || typeName.equals("VARCHAR")) {
					value = "(" + map.get("CHAR_OCTET_LENGTH") + ")";
				} else if (typeName.equals("DECIMAL")) {
					value = "(" + map.get("COLUMN_SIZE") + "," + map.get("DECIMAL_DIGITS") + ")";
				} else
					value = " ";

				if (map.get("IS_NULLABLE").equals("YES")) {

					rs += "\"" + map.get("COLUMN_NAME") + "\"" + " " + map.get("TYPE_NAME") + value + valueresult + ",";
				} else
					rs += "\"" + map.get("COLUMN_NAME") + "\"" + " " + map.get("TYPE_NAME") + value + valueresult + " NOT NULL,";

			}
			for (int i = 0; i < pk.size(); i++) {
				Map map = (Map) pk.get(i);
				t += "\"" + map.get("COLUMN_NAME") + "\"" + ",";
				if (!"".equals(t))
				h = ",PRIMARY KEY (" + t.substring(0, t.length() - 1) + ")";

			}
			if (!"".equals(rs))
				rs = rs.substring(0, rs.length() - 1);

		} catch (DBMException e) {

		}

		return rs + h;
	}
	/**
	 * [機 能] get DropColumnValues
	 * <p>
	 * [解 説] get DropColumnValues 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @return getDropColumnValues sql(String)
	 */
	@Override
	public String getDropColumnValues() {
		String s = dateMetaInfo.getColumnName().substring(0, dateMetaInfo.getColumnName().indexOf("("));

		return "\"" + s.trim() + "\"";
	}
	public String dropColumn() {

		return "";

	}
	/**
	 * [機 能] get InsertColumnNames
	 * <p>
	 * [解 説] get InsertColumnNames 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @return getInsertColumnNames sql(String)
	 */
	@Override
	public String getInsertColumnNames() {
		return getAllColumnNames();
	}
	/**
	 * [機 能] get InsertColumnValues
	 * <p>
	 * [解 説] get InsertColumnValues 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @return getInsertColumnValues sql(String)
	 */
	@Override
	public String[] getInsertColumnValues() {
		String valueresult = "";
		String def = "";

		try {
			DBMSqlExecuter dde = DBMSqlExecuterFactory.createExecuter(getConnectionInfo(), getDBSession());
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
			dbMetaInfo.setSchemaName(dateMetaInfo.getSchemaName());
			dbMetaInfo.setTableName(dateMetaInfo.getTableName());
			DBMDataResult ddr = dde.getColumns(dbMetaInfo);
			List s = ddr.getData();

			for (int i = 0; i < s.size(); i++) {

				Map map = (Map) s.get(i);

				String value = (String) map.get("TYPE_NAME");
				def = (String) map.get("COLUMN_DEF");

				if (def != null&& !def.equals("")) {
					valueresult += def;

				} else {

					if (value.equalsIgnoreCase("VARCHAR"))
						valueresult += "''";
					else if (value.equalsIgnoreCase("TIMESTAMP")) {
						

						valueresult += "CURRENT_TIMESTAMP";

					} else if (value.equalsIgnoreCase("TIME")) {
						

						valueresult += "CURRENT_TIME";

					} else if (value.equalsIgnoreCase("SMALLINT"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("REAL"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("LONG VARCHAR"))
						valueresult += "NULL";

					else if (value.equalsIgnoreCase("INTEGER"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("DOUBLE"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("DECIMAL"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("DATE")) {
						valueresult += "CURRENT_DATE";
					} else if (value.equalsIgnoreCase("CLOB"))
						valueresult += "''";
					else if (value.equalsIgnoreCase("CHARACTER"))
						valueresult += "''";
					else if (value.equalsIgnoreCase("BLOB"))
						valueresult += "NULL";

					else if (value.equalsIgnoreCase("BIGINT"))
						valueresult += "NULL";

					else
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
	/**
	 * [機 能] get SelectColumns
	 * <p>
	 * [解 説] get SelectColumns 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @return getSelectColumns sql(String)
	 */
	@Override
	public String getSelectColumns() {
		return getAllColumnNames();
	}
	/**
	 * [機 能] get TableName
	 * <p>
	 * [解 説] get TableName 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @return getTableName sql(String)
	 */
	@Override
	public String getTableName() {
		return "\"" + dateMetaInfo.getSchemaName() + "\".\"" + dateMetaInfo.getTableName() + "\"";
	}
	/**
	 * [機 能] get UpdateValues
	 * <p>
	 * [解 説] get UpdateValues 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @return getUpdateValues sql(String)
	 */
	@Override
	public String getUpdateValues() {

		String rs = "";
		String valueresult = "";
		String def = "";

		try {
			DBMSqlExecuter dde = DBMSqlExecuterFactory.createExecuter(getConnectionInfo(), getDBSession());
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
			dbMetaInfo.setSchemaName(dateMetaInfo.getSchemaName());
			dbMetaInfo.setTableName(dateMetaInfo.getTableName());
			DBMDataResult ddr = dde.getColumns(dbMetaInfo);
			List s = ddr.getData();

			for (int i = 0; i < s.size(); i++) {
				Map map = (Map) s.get(i);
				String value = (String) map.get("TYPE_NAME");
				def = (String) map.get("COLUMN_DEF");
				if (def != null&& !def.equals("")) {
					valueresult = def;

				} else

				if (value.equalsIgnoreCase("VARCHAR"))
					valueresult = "''";
				else if (value.equalsIgnoreCase("TIMESTAMP")) {
					

					valueresult = "CURRENT_TIMESTAMP";

				} else if (value.equalsIgnoreCase("TIME")) {
					valueresult = "CURRENT_TIME";

				} else if (value.equalsIgnoreCase("SMALLINT"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("REAL"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("LONG VARCHAR"))
					valueresult = "NULL";

				else if (value.equalsIgnoreCase("INTEGER"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("DOUBLE"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("DECIMAL"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("DATE")) {
					valueresult = "CURRENT_DATE";

				} else if (value.equalsIgnoreCase("CLOB"))
					valueresult = "''";
				else if (value.equalsIgnoreCase("CHARACTER"))
					valueresult = "''";
				else if (value.equalsIgnoreCase("BLOB"))
					valueresult = "NULL";

				else if (value.equalsIgnoreCase("BIGINT"))
					valueresult = "NULL";

				else
					valueresult = "0";

				rs += "\"" + map.get("COLUMN_NAME") + "\"=" + valueresult + ",";

			}

		} catch (DBMException e) {

		}

		return rs.substring(0, rs.length() - 1);
	}
	/**
	 * [機 能] get AllColumnNames
	 * <p>
	 * [解 説] get AllColumnNames 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @return rs
	 */
	private String getAllColumnNames() {
		String rs = "";

		try {
			DBMSqlExecuter dde = DBMSqlExecuterFactory.createExecuter(getConnectionInfo(), getDBSession());
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
			dbMetaInfo.setSchemaName(dateMetaInfo.getSchemaName());
			dbMetaInfo.setTableName(dateMetaInfo.getTableName());
			DBMDataResult ddr = dde.getColumns(dbMetaInfo);
			List s = ddr.getData();

			for (int i = 0; i < s.size(); i++) {
				Map map = (Map) s.get(i);

				rs += "\"" + map.get("COLUMN_NAME") + "\"" + ",";

			}

		} catch (DBMException e) {

		}
		if (!"".equals(rs))
			rs = rs.substring(0, rs.length() - 1);

		return rs;
	}
	/**
	 * [機 能] get CreateDynamic
	 * <p>
	 * [解 説] get CreateDynamic 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @return getCreateDynamic sql(String)
	 */
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
		String schema = dateMetaInfo.getSchemaName();
		String table = dateMetaInfo.getTableName();

		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			if (map.get("Column Name") != "") {
				columnname = "\"" + map.get("Column Name") + "\" ";
				Object columnType = map.get("Data Type");
				if (columnType != "" || columnType != null) {
					f = columnType.toString();
				} else
					f = "";

				if (map.get("Length") == "") {
					a = "";

				} else
					a = "( " + map.get("Length").toString() + " ) ";

				if (map.get("Allow Nulls").equals(Boolean.TRUE)) {
					b = "";
				} else
					b = " NOT NULL";
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

					} else if (columnType.equals("CHARACTER") || columnType.equals("LONG VARCHAR")
							|| columnType.equals("VARCHAR") || columnType.equals("BLOB")
							|| columnType.equals("CLOB")) {
						c = " DEFAULT '" + map.get("Default Value") + "'";

					} else

						c = " DEFAULT " + map.get("Default Value");

				}

				if (map.get("Precision").equals("")) {
					d = "";
				} else
					d = " (" + map.get("Precision") + "," + map.get("Scale") + ")";
				if (map.get("Identity").toString().equals("YES")) {
					e = " GENERATED ALWAYS AS IDENTITY ";
				} else
					e = "";
				if (map.get("Primary Key").equals(Boolean.TRUE)) {
					t += "\"" + map.get("Column Name") + "\"" + ",";
					/*if (!"".equals(t))
						t = t.substring(0, t.length() - 1);*/
					h = ",PRIMARY KEY (" + t.substring(0, t.length() - 1) + ")";
				}

				result += columnname + f + a + d + e + c + b + ",";

			}

		}
		if (!"".equals(result))
			result = result.substring(0, result.length() - 1);

		return "\"" + schema + "\"." + "\"" + table + "\" (" + result + h;

	}
	/**
	 * [機 能] get CreateDynamic
	 * <p>
	 * [解 説] get CreateDynamic 。
	 * <p>
	 * [備 考] なし
	 * 
	 * @return getCreateDynamic sql(String)
	 */
	public String createDynamic(List list) {

		return "CREATE TABLE " + getCreateDynamic(list) + " ) ";

	}
	/**
	 * @param list
	 *            [機 能] get AlterAdd
	 *            <p>
	 *            [解 説] get AlterAdd 。
	 *            <p>
	 *            [備 考] なし
	 * @return getCreateDynamic sql(String)
	 */
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
		String schema = dateMetaInfo.getSchemaName();
		String table = dateMetaInfo.getTableName();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			if (map.get("Column Name") != "") {
				columnname = "\"" + map.get("Column Name") + "\"" + " ";
				if (map.get("Data Type") != "" || map.get("Data Type") != null) {
					f = " " + map.get("Data Type").toString();
				} else
					f = "";

				if (map.get("Length") == "") {
					a = "";

				} else
					a = "( " + map.get("Length").toString() + " ) ";

				if (map.get("Allow Nulls").equals(Boolean.TRUE)) {
					b = "";
				} else
					b = " NOT NULL ";
				if (map.get("Default Value").equals("")) {
					c = "";

				} else if (map.get("Data Type").equals("CHARACTER") || map.get("Data Type").equals("LONG VARCHAR")
						|| map.get("Data Type").equals("VARCHAR") || map.get("Data Type").equals("BLOB")
						|| map.get("Data Type").equals("CLOB"))
					c = " DEFAULT '" + map.get("Default Value") + "' ";
				else{

					String def = (String) map.get("Default Value");
					Object columnType = map.get("Data Type");
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

					} else if (columnType.equals("CHARACTER") || columnType.equals("LONG VARCHAR")
							|| columnType.equals("VARCHAR") || columnType.equals("BLOB")
							|| columnType.equals("CLOB")) {
						c = " DEFAULT '" + map.get("Default Value") + "'";

					} else

						c = " DEFAULT " + map.get("Default Value");

				
				}

				if (map.get("Precision").equals("")) {
					d = "";
				} else
					d = "(" + map.get("Precision") + "," + map.get("Scale") + ") ";
				if (map.get("Identity").toString().equals("YES")) {
					e = " GENERATED ALWAYS AS IDENTITY ";
				} else
					e = "";

				result += "ALTER TABLE " + "\"" + schema + "\"." + "\"" + table + "\"" + " ADD COLUMN " + columnname + f + a + d + e + c
						+ b + "\n";
			}
		}
		return result;
	}
	@Override
	public String getAlterDrop(List list) {
		return null;
	}
	/**
	 * @param list
	 *            [機 能] get AlterModify
	 *            <p>
	 *            [解 説] get AlterModify 。
	 *            <p>
	 *            [備 考] なし
	 * @return getAlterModify sql(String)
	 */
	@Override
	public String getAlterModify(List list) {
		String columnname = "";
		String a = "";
		String b = "";
		String c = "";
		String d = "";
		String e = "";
		String f = "";
		String result = "";
		String schema = dateMetaInfo.getSchemaName();
		String table = dateMetaInfo.getTableName();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			columnname = "\"" + map.get("Column Name") + "\"" + " ";
			if (map.get("Data Type") != "" || map.get("Data Type") != null) {
				f = " " + map.get("Data Type").toString();
			} else
				f = "";
			if (map.get("Length") == "") {
				a = "";
			} else
				a = "( " + map.get("Length").toString() + " ) ";
			if (map.get(ColumnProperty.ALLOW_NULL) != map.get(ColumnProperty.NULLABLE)) {
				if (map.get("Allow Nulls").equals(Boolean.TRUE)) {
					b = " NULL ";
				} else
					b = " NOT NULL ";
			} else
				b = "";
			if ("".equals(map.get("Default Value")) || map.get("Default Value") == null) {
				c = "";

			} else if (map.get("Data Type").equals("CHARACTER") || map.get("Data Type").equals("LONG VARCHAR")
					|| map.get("Data Type").equals("VARCHAR") || map.get("Data Type").equals("BLOB") || map.get("Data Type").equals("CLOB"))
				c = " DEFAULT '" + map.get("Default Value") + "' ";
			else
				c = " DEFAULT " + map.get("Default Value") + " ";

			if (map.get("Precision").equals("")) {
				d = "";
			} else
				d = "(" + map.get("Precision") + "," + map.get("Scale") + ") ";
			if (map.get("Identity").toString().equals("YES")) {
				e = " GENERATED ALWAYS AS IDENTITY ";
			} else
				e = "";
			result += "ALTER TABLE " + "\"" + schema + "\"." + "\"" + table + "\"" + " ALTER " + columnname + " SET DATA TYPE " + f + a + d
					+ e + c + b + "\n";
		}
		return result;
	}
	/**
	 * @param list
	 *            [機 能] get AlterPrimary
	 *            <p>
	 *            [解 説] get AlterPrimary 。
	 *            <p>
	 *            [備 考] なし
	 * @return getAlterPrimary sql(String)
	 */
	@Override
	public String getAlterPrimary(List list) {
		String result = "";
		String t = "";
		String h = "";
		String sql = "";
		String schema = dateMetaInfo.getSchemaName();
		String table = dateMetaInfo.getTableName();
		for (int i = 0; i < list.size(); i++) {
			String pkColumnName = (String) list.get(i);
			t += "\"" + pkColumnName + "\"" + ",";
		}
		if (!"".equals(t))
			h = " PRIMARY KEY (" + t.substring(0, t.length() - 1) + ")";
		try {
			DBMDataResult data = DBMSqlExecuterFactory.createExecuter(conInfo, dBSession).getPrimaryKeys(dateMetaInfo);
			List dataList = data.getData();
			Object constraintName = "";
			if (dataList.size() > 0) {
				constraintName = ((Map) dataList.get(0)).get("PK_NAME");
				result = "ALTER TABLE" + "\"" + schema + "\"." + "\"" + table + "\"" + " DROP CONSTRAINT " + (String) constraintName;
			} else
				constraintName = "pk1" + System.currentTimeMillis();
			if (!"".equals(h))
				sql = "ALTER TABLE " + "\"" + schema + "\"." + "\"" + table + "\"" + " ADD CONSTRAINT " + (String) constraintName + h;
		} catch (DBMException e) {

		}
		return result + "\n" + sql + "\n";
	}
	@Override
	public String getAltetTableValue() {

		return " ADD COLUMN " + "<COLUMN_NAME> <DATA_TYPE>";
	}

}

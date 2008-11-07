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
 * Description: OracleBuilder
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
public class OracleBuilder extends AbstractSqlBuilder {

	public OracleBuilder(DBMDataMetaInfo dateMetaInfo) {
		super(dateMetaInfo);
	}

	@Override
	public String getAlterColumnValues() {
		String s= dateMetaInfo.getColumnName().substring(0, dateMetaInfo.getColumnName().indexOf("("));
		return " MODIFY \"" + s.trim() + "\" <DATA TYPE>";
	}

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
			if (!"".equals(s1))
				s1 = s1.trim();
			for (int i = 0; i < s.size(); i++) {
				Map map = (Map) s.get(i);
				String columnName = map.get("COLUMN_NAME").toString();
				String typeName = map.get("TYPE_NAME").toString();
				if (s1.equals(columnName)) {

					if (typeName.equals("CHAR") || typeName.equals("NCHAR") || typeName.equals("NVARCHAR2") || typeName.equals("RAW")
							|| typeName.equals("VARCHAR") || typeName.equals("VARCHAR2")) {
						value = "(" + map.get("CHAR_OCTET_LENGTH") + ") ";
					} else if (typeName.equals("NUMBER")) {
						if(map.get("DECIMAL_DIGITS")==null)
							value = "(" + map.get("COLUMN_SIZE") + ",0) ";
						else value = "(" + map.get("COLUMN_SIZE") + "," + map.get("DECIMAL_DIGITS") + ") ";
					} else
						value = " ";

					if (map.get("IS_NULLABLE").equals("YES")) {

						rs += "\"" + map.get("COLUMN_NAME") + "\"" + " " + map.get("TYPE_NAME") + value + ",";
					} else
						rs += "\"" + map.get("COLUMN_NAME") + "\"" + " " + map.get("TYPE_NAME") + value + "NOT NULL,";
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

	@Override
	public String getCreateTableValues() {
		String rs = "";
		String value = "";
		String t = "";
		String h = "";
		String def="";
		

		try {
			DBMSqlExecuter dde = DBMSqlExecuterFactory.createExecuter(getConnectionInfo(), getDBSession());
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();

			dbMetaInfo.setSchemaName(dateMetaInfo.getSchemaName());
			dbMetaInfo.setTableName(dateMetaInfo.getTableName());
			DBMDataResult ddr = dde.getColumns(dbMetaInfo);

			List s = ddr.getData();
			List pk = dde.getPrimaryKeys(dbMetaInfo).getData();
			for (int i = 0; i < s.size(); i++) {
				String valueresult="";
				Map map = (Map) s.get(i);
				String typeName = map.get("TYPE_NAME").toString();
				def = (String)map.get("COLUMN_DEF");
				if (def != null&& !def.equals("")) {
						valueresult = "DEFAULT "+def.trim();
				} 
				if (typeName.equals("CHAR") || typeName.equals("NCHAR") || typeName.equals("NVARCHAR2") || typeName.equals("RAW")
						|| typeName.equals("VARCHAR") || typeName.equals("VARCHAR2")) {
					value = "(" + map.get("CHAR_OCTET_LENGTH") + ") ";
				} else if (typeName.equals("NUMBER")) {
					if(map.get("DECIMAL_DIGITS")==null)
						value = "(" + map.get("COLUMN_SIZE") + ",0) ";
					else value = "(" + map.get("COLUMN_SIZE") + "," + map.get("DECIMAL_DIGITS") + ") ";
				} else
					value = " ";

				if (map.get("IS_NULLABLE").equals("YES")) {

					rs += "\"" + map.get("COLUMN_NAME") + "\"" + " " + map.get("TYPE_NAME") + value+valueresult + ",";
				} else
					rs += "\"" + map.get("COLUMN_NAME") + "\"" + " " + map.get("TYPE_NAME") + value +valueresult+ "NOT NULL,";

			}
			for (int i = 0; i < pk.size(); i++) {
				Map map = (Map) pk.get(i);
				t += "\"" + map.get("COLUMN_NAME").toString().trim() + "\"" + ",";
				if (!"".equals(t))
				h = ",PRIMARY KEY (" + t.substring(0, t.length() - 1) + ") ";

			}
			if (!"".equals(rs))
				rs = rs.substring(0, rs.length() - 1);

		} catch (DBMException e) {

		}

		return rs + h;
	}

	@Override
	public String getDropColumnValues() {
		String s=dateMetaInfo.getColumnName().substring(0, dateMetaInfo.getColumnName().indexOf("("));

		return " DROP (\"" + s.trim()
				+ "\") CASCADE CONSTRAINT";
	}

	@Override
	public String getInsertColumnNames() {
		String result = "";

		try {
			DBMSqlExecuter dde = DBMSqlExecuterFactory.createExecuter(getConnectionInfo(), getDBSession());
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
			dbMetaInfo.setSchemaName(dateMetaInfo.getSchemaName());
			dbMetaInfo.setTableName(dateMetaInfo.getTableName());
			DBMDataResult ddr = dde.getColumns(dbMetaInfo);
			List s = ddr.getData();

			for (int i = 0; i < s.size(); i++) {
				Map map = (Map) s.get(i);

				result += "\"" + map.get("COLUMN_NAME") + "\"" + ",";

			}
		} catch (DBMException e) {

		}
		if (!"".equals(result))
			result = result.substring(0, result.length() - 1);

		return result;

	}

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
				def = (String)map.get("COLUMN_DEF");
				if (def != null&& !def.equals("")) {
						valueresult += def.trim();
				}else 
					if (value.equalsIgnoreCase("VARCHAR2"))
						valueresult += "''";
					else if (value.equalsIgnoreCase("ROWID"))
						valueresult += "''";
					else if (value.equalsIgnoreCase("RAW"))
						valueresult += "''";
					else if (value.equalsIgnoreCase("NVARCHAR2"))
						valueresult += "N''";

					else if (value.equalsIgnoreCase("NUMBER"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("NCLOB"))
						valueresult += "N''";
					else if (value.equalsIgnoreCase("NCHAR"))
						valueresult += "N''";

					else if (value.equalsIgnoreCase("LONG"))
						valueresult += "NULL";

					else if (value.equalsIgnoreCase("FLOAT"))
						valueresult += "0";
					else if (value.equalsIgnoreCase("DATE"))
						valueresult += "CURRENT_DATE";
					else if (value.equalsIgnoreCase("CLOB"))
						valueresult += "''";
					else if (value.equalsIgnoreCase("CHAR"))
						valueresult += "''";
					else if (value.equalsIgnoreCase("BLOB"))
						valueresult += "NULL";
					else if (value.equalsIgnoreCase("BFILE"))
						valueresult += "NULL";
					else if (value.equalsIgnoreCase("DATETIME")) {
						valueresult += "CURRENT_TIMESTAMP";
					}
				valueresult +=  ",";
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
		return "\"" + dateMetaInfo.getSchemaName() + "\".\"" + dateMetaInfo.getTableName() + "\"";
	}

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
				def = (String)map.get("COLUMN_DEF");
				if (def != null&& !def.equals("")) {
						valueresult = def.trim();
				} else if (value.equalsIgnoreCase("VARCHAR2"))
					valueresult = "''";
				else if (value.equalsIgnoreCase("ROWID"))
					valueresult = "''";
				else if (value.equalsIgnoreCase("RAW"))
					valueresult = "''";
				else if (value.equalsIgnoreCase("NVARCHAR2"))
					valueresult = "N''";

				else if (value.equalsIgnoreCase("NUMBER"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("NCLOB"))
					valueresult = "N''";
				else if (value.equalsIgnoreCase("NCHAR"))
					valueresult = "N''";

				else if (value.equalsIgnoreCase("LONG"))
					valueresult = "NULL";

				else if (value.equalsIgnoreCase("FLOAT"))
					valueresult = "0";
				else if (value.equalsIgnoreCase("DATE"))
					valueresult = "CURRENT_DATE";
				else if (value.equalsIgnoreCase("CLOB"))
					valueresult = "''";
				else if (value.equalsIgnoreCase("CHAR"))
					valueresult = "''";
				else if (value.equalsIgnoreCase("BLOB"))
					valueresult = "NULL";
				else if (value.equalsIgnoreCase("BFILE"))
					valueresult = "NULL";
				else if (value.equalsIgnoreCase("DATETIME")) {
					valueresult = "CURRENT_TIMESTAMP";
				}
				valueresult=valueresult.trim();

				rs += "\"" + map.get("COLUMN_NAME") + "\"" + "=" + valueresult + ",";

			}

		} catch (DBMException e) {

		}
		if (!"".equals(rs))
			rs = rs.substring(0, rs.length() - 1);

		return rs;
	}

	private String getAllColumnNames() {

		String result = "";

		try {
			DBMSqlExecuter dde = DBMSqlExecuterFactory.createExecuter(getConnectionInfo(), getDBSession());
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
			dbMetaInfo.setSchemaName(dateMetaInfo.getSchemaName());
			dbMetaInfo.setTableName(dateMetaInfo.getTableName());
			DBMDataResult ddr = dde.getColumns(dbMetaInfo);
			List s = ddr.getData();

			for (int i = 0; i < s.size(); i++) {
				Map map = (Map) s.get(i);

				result += "\"" + map.get("COLUMN_NAME") + "\"" + ",";

			}
		} catch (DBMException e) {

		}
		if (!"".equals(result))
			result = result.substring(0, result.length() - 1);

		return result;
	}

	public String createColumn() {

		return "ALTER TABLE " + getTableName() + " ADD  " + getCreateColumnValues();

	}

	public String dropColumn() {

		return "ALTER TABLE " + getTableName() + getDropColumnValues();

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
					b = " NULL ";
				} else
					b = " NOT NULL ";
				if (map.get("Default Value").equals("")) {
					c = "";

				} else {
					String def = (String) map.get("Default Value");
					String columnType = (String) map.get(ColumnProperty.TYPE);
					if ("DATETIME".equalsIgnoreCase(columnType)
							|| "DATE".equalsIgnoreCase(columnType)) {
						Convertor convertor = new Convertor();

						Object o1 = convertor.string2Date(def);
						convertor.setDatetimeFormat("yyyy/MM/dd");
						Object o2 = convertor.string2Date(def);

						if (o1 == null && o2 == null) {
							c = " DEFAULT " + def;
						} else {
							c = " DEFAULT '" + def + "'";
						}
					} else if ("TIME".equalsIgnoreCase(columnType)) {
						Convertor convertor = new Convertor();

						Object o1 = convertor.string2Time(def);

						if (o1 == null) {
							c = " DEFAULT " + def;
						} else {
							c = " DEFAULT '" + def + "'";
						}
					} else if ("TIMESTAMP".equalsIgnoreCase(columnType)) {

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
						if (columnType.equalsIgnoreCase("VARCHAR2") || columnType.equalsIgnoreCase("ROWID") || columnType.equalsIgnoreCase("RAW")
								|| columnType.equalsIgnoreCase("NVARCHAR") || columnType.equalsIgnoreCase("CLOB") 
								|| columnType.equalsIgnoreCase("BLOB")|| columnType.equalsIgnoreCase("NCLOB")|| columnType.equalsIgnoreCase("NCHAR")
								|| columnType.equalsIgnoreCase("VARCHAR") || columnType.equalsIgnoreCase("NVARCHAR2")){
							c = " DEFAULT '" + map.get("Default Value") + "'";
							
						}
						else

						c = " DEFAULT " + map.get("Default Value");

				} 
				
				if (map.get("Precision").equals("")) {
					d = "";
				} else
					d = "(" + map.get("Precision") + "," + map.get("Scale") + ") ";
				e = "";
				if (map.get("Primary Key").equals(Boolean.TRUE)) {
					t += "\"" + map.get("Column Name") + "\"" + ",";
					if (!"".equals(t))
					h = ", PRIMARY KEY (" + t.substring(0, t.length() - 1) + ")";
				}

				result += columnname + f + a + d + e + c + b + " ,";

			}

		}
		if (!"".equals(result))
			result = result.substring(0, result.length() - 1);

		return "\"" + schema + "\"." + "\"" + table + "\" (" + result + h;

	}
	public String createDynamic(List list) {

		return "CREATE TABLE " + getCreateDynamic(list) + " ) ";

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
					b = " NULL ";
				} else
					b = " NOT NULL ";
				if (map.get("Default Value").equals("")) {
					c = "";

				} else{

					String def = (String) map.get("Default Value");
					String columnType = (String) map.get(ColumnProperty.TYPE);
					if ("DATETIME".equalsIgnoreCase(columnType)
							|| "DATE".equalsIgnoreCase(columnType)) {
						Convertor convertor = new Convertor();

						Object o1 = convertor.string2Date(def);
						convertor.setDatetimeFormat("yyyy/MM/dd");
						Object o2 = convertor.string2Date(def);

						if (o1 == null && o2 == null) {
							c = " DEFAULT " + def;
						} else {
							c = " DEFAULT '" + def + "'";
						}
					} else if ("TIME".equalsIgnoreCase(columnType)) {
						Convertor convertor = new Convertor();

						Object o1 = convertor.string2Time(def);

						if (o1 == null) {
							c = " DEFAULT " + def;
						} else {
							c = " DEFAULT '" + def + "'";
						}
					} else if ("TIMESTAMP".equalsIgnoreCase(columnType)) {

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
						if (columnType.equalsIgnoreCase("VARCHAR2") || columnType.equalsIgnoreCase("ROWID") || columnType.equalsIgnoreCase("RAW")
								|| columnType.equalsIgnoreCase("NVARCHAR") || columnType.equalsIgnoreCase("CLOB") 
								|| columnType.equalsIgnoreCase("BLOB")|| columnType.equalsIgnoreCase("NCLOB")|| columnType.equalsIgnoreCase("NCHAR")
								|| columnType.equalsIgnoreCase("VARCHAR") || columnType.equalsIgnoreCase("NVARCHAR2")){
							c = " DEFAULT '" + map.get("Default Value") + "'";
							
						}
						else

						c = " DEFAULT " + map.get("Default Value");

				
				}
					//c = " DEFAULT '" + map.get("Default Value") + "' ";
				if (map.get("Precision").equals("")) {
					d = "";
				} else
					d = "(" + map.get("Precision") + "," + map.get("Scale") + ") ";
				e = "";

				result += "ALTER TABLE " + "\"" + schema + "\"." + "\"" + table + "\"" + " ADD (" + columnname + f + a + d + e + c + b
						+ " ) " + "\n";

			}

		}
		return result;
	}

	@Override
	public String getAlterDrop(List list) {
		String result = "";
		String schema = dateMetaInfo.getSchemaName();
		String table = dateMetaInfo.getTableName();
		for (int i = 0; i < list.size(); i++) {
			String columnName = (String) list.get(i);
			result += "ALTER TABLE " + "\"" + schema + "\"." + "\"" + table + "\"" + " DROP (" + "\"" + columnName + "\""
					+ " ) CASCADE CONSTRAINT" + "\n";
		}
		return result;
	}

	@Override
	public String getAlterModify(List list) {
		String columnname = "";
		String a = "";
		String b = "";
		String c = "";
		String d = "";
		String e = "";
		String f = "";
		String h = "";

		String result = "";
		String schema = dateMetaInfo.getSchemaName();
		String table = dateMetaInfo.getTableName();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			if (map.get("Column Name") != map.get("Old Column Name")) {
				h = "ALTER TABLE " + "\"" + schema + "\"." + "\"" + table + "\"" + " RENAME COLUMN " + "\"" + map.get("Old Column Name")
						+ "\"" + " " + "TO" + "\"" + map.get("Column Name") + "\"" + " ";
			}
			columnname = "\"" + map.get("Old Column Name") + "\"" + " ";
			if (map.get("Data Type") != "" || map.get("Data Type") != null) {
				f = " " + map.get("Data Type").toString();
			} else
				f = "";

			if (map.get("Length") == "") {
				a = "";

			} else
				a = "( " + map.get("Length").toString() + " ) ";

			if ("".equals(map.get("Default Value")) || map.get("Default Value") == null) {
				if (map.get("Data Type").equals("FLOAT")||map.get("Data Type").equals("NUMBER")||map.get("Data Type").equals("LONG")){
					c=" DEFAULT 0";
				}
				else
				if (map.get("Data Type").equals("DATE")||map.get("Data Type").equals("DATETIME")){
					c=" DEFAULT NULL";
				}
				else c = " DEFAULT ''";

			} else{


				String def = (String) map.get("Default Value");
				String columnType = (String) map.get(ColumnProperty.TYPE);
				if ("DATETIME".equalsIgnoreCase(columnType)
						|| "DATE".equalsIgnoreCase(columnType)) {
					Convertor convertor = new Convertor();

					Object o1 = convertor.string2Date(def);
					convertor.setDatetimeFormat("yyyy/MM/dd");
					Object o2 = convertor.string2Date(def);

					if (o1 == null && o2 == null) {
						c = " DEFAULT " + def;
					} else {
						c = " DEFAULT '" + def + "'";
					}
				} else if ("TIME".equalsIgnoreCase(columnType)) {
					Convertor convertor = new Convertor();

					Object o1 = convertor.string2Time(def);

					if (o1 == null) {
						c = " DEFAULT " + def;
					} else {
						c = " DEFAULT '" + def + "'";
					}
				} else if ("TIMESTAMP".equalsIgnoreCase(columnType)) {

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
					if (columnType.equalsIgnoreCase("VARCHAR2") || columnType.equalsIgnoreCase("ROWID") || columnType.equalsIgnoreCase("RAW")
							|| columnType.equalsIgnoreCase("NVARCHAR") || columnType.equalsIgnoreCase("CLOB") 
							|| columnType.equalsIgnoreCase("BLOB")|| columnType.equalsIgnoreCase("NCLOB")|| columnType.equalsIgnoreCase("NCHAR")
							|| columnType.equalsIgnoreCase("VARCHAR") || columnType.equalsIgnoreCase("NVARCHAR2")){
						c = " DEFAULT '" + map.get("Default Value") + "'";
						
					}
					else

					c = " DEFAULT " + map.get("Default Value");
			
			
			}

			if (map.get(ColumnProperty.ALLOW_NULL) != map.get(ColumnProperty.NULLABLE)) {
				if (map.get("Allow Nulls").equals(Boolean.TRUE)) {
					b = " NULL ";
				} else
					b = " NOT NULL ";
			} else
				b = "";

			if (map.get("Precision").equals("")) {
				d = "";
			} else
				d = "(" + map.get("Precision") + "," + map.get("Scale") + ") ";
			e = "";

			result += "ALTER TABLE " + "\"" + schema + "\"." + "\"" + table + "\"" + " MODIFY (" + columnname + f + a + d + e + c + b
					+ " ) " + "\n" + h + "\n";

		}
		return result;
	}

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
			t += "\""+pkColumnName + "\",";
		}
		if (!"".equals(t))
			h = " PRIMARY KEY ( "+ t.substring(0, t.length() - 1) +" )";

		try {
			DBMDataResult data = DBMSqlExecuterFactory.createExecuter(conInfo, dBSession).getPrimaryKeys(dateMetaInfo);
			List dataList = data.getData();
			String pkName;
			if (dataList.size() > 0) {
				pkName = (String) ((Map) dataList.get(0)).get("PK_NAME");
				result += "ALTER TABLE " + "\"" + schema + "\"." + "\"" + table + "\"" + " DROP CONSTRAINT " + pkName;
			} else {
				pkName = "pk" + System.currentTimeMillis();
			}
			if (!"".equals(h))
				sql = "ALTER TABLE " + "\"" + schema + "\"." + "\"" + table + "\"" + " ADD  CONSTRAINT " + pkName + h + " INITIALLY  ";
		} catch (DBMException e) {

		}
		return result + "\n" + sql + "\n";
	}

	@Override
	public String getAltetTableValue() {
		return " ADD " + "<COLUMN_NAME> <DATA_TYPE>";
	}

}

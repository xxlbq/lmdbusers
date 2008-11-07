/*
 * 
*/
package com.livedoor.dbm.util;

import com.livedoor.dbm.action.IActionHandler;
import com.livedoor.dbm.components.tree.DBMRootNode;
import com.livedoor.dbm.components.tree.DBMTree;
import com.livedoor.dbm.components.tree.DBMTreeModel;

/**
 * @author <a href="mailto:lijian@livedoor.cn">Jian Li </a>
 * @version 1.0
 * <p> Description:</p>
 */
public class DBMSchemaTreeInit {
	/**
	 * 返回当前的树对象
	 * @param actionHandler
	 * @return
	 */
	public static DBMTree getTree(IActionHandler actionHandler){
		DBMRootNode rootNode = new DBMRootNode(actionHandler.getFrame(),DBMConnectionUtil.getConnDir());
		DBMTreeModel treeModel = new DBMTreeModel(rootNode);
		DBMTree dbmTree = new DBMTree(treeModel,actionHandler.getFrame());
		
		return dbmTree;
	}
	
	/**
	 * [機 能] 得到列的数据类型.
	 * 
	 * @param s
	 *            result结果.
	 */
	public static String getMySQLDataType(String s) {
		int i = s.indexOf("(");
		if (i > 0)
			return s.substring(0, i);
		else
			return s;
	}
	/**
	 * [機 能] 得到列的长度.
	 * 
	 * @param s
	 *            result结果.
	 */
	public static String getMySQLLength(String s) {
		String s1 = getMySQLDataType(s);
		if (s1.equalsIgnoreCase("ENUM") || s1.equalsIgnoreCase("SET"))
			return "";
		if (!s1.equalsIgnoreCase("VARCHAR") && !s1.equalsIgnoreCase("CHAR") && !s1.equalsIgnoreCase("TIMESTAMP")
				&& !s1.equalsIgnoreCase("TEXT") && !s1.equalsIgnoreCase("MEDIUMTEXT") && !s1.equalsIgnoreCase("LONGTEXT")
				&& !s1.equalsIgnoreCase("TINYTEXT"))
			return "";
		int i = s.indexOf("(");
		if (i > 0) {
			int j = s.indexOf(")");
			String s2 = s.substring(i + 1, j);
			i = s2.indexOf(",");
			if (i >= 0) {
				if (i == 0)
					return "";
				if (i == s2.length() - 1)
					return s2.substring(0, i);
				else
					return s2.substring(0, i);
			} else {
				return s2;
			}
		} else {
			return "";
		}
	}
	/**
	 * [機 能] 得到列的精确度.
	 * 
	 * @param s
	 *            result结果.
	 */
	public static String getMySQLPrecision(String s) {
		String s1 = getMySQLDataType(s);
		if (s1.equalsIgnoreCase("ENUM") || s1.equalsIgnoreCase("SET"))
			return "";
		if (s1.equalsIgnoreCase("VARCHAR") || s1.equalsIgnoreCase("CHAR") || s1.equalsIgnoreCase("TIMESTAMP")
				|| s1.equalsIgnoreCase("TEXT") || s1.equalsIgnoreCase("MEDIUMTEXT") || s1.equalsIgnoreCase("LONGTEXT")
				|| s1.equalsIgnoreCase("TINYTEXT"))
			return "";
		int i = s.indexOf("(");
		if (i > 0) {
			int j = s.indexOf(")");
			String s2 = s.substring(i + 1, j);
			i = s2.indexOf(",");
			if (i >= 0) {
				if (i == 0)
					return "";
				if (i == s2.length() - 1)
					return s2.substring(0, i);
				else
					return s2.substring(0, i);
			} else {
				return s2;
			}
		} else {
			return "";
		}
	}
	/**
	 * [機 能] 得到列的数值范围.
	 * 
	 * @param s
	 *            result结果.
	 */
	public static String getMySQLScale(String s) {
		String s1 = getMySQLDataType(s);
		if (s1.equalsIgnoreCase("ENUM") || s1.equalsIgnoreCase("SET"))
			return "";
		int i = s.indexOf("(");
		if (i > 0) {
			int j = s.indexOf(")");
			String s2 = s.substring(i + 1, j);
			i = s2.indexOf(",");
			if (i >= 0) {
				if (i == s2.length() - 1)
					return "";
				else
					return s2.substring(i + 1, s2.length());
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	/**
	 * [機 能] 得到要显示的列.
	 * 
	 * @param s,s1,s2,s3
	 *            result结果.
	 */
	public static String getMysqlDataTypeDefinitionString(String s, String s1, String s2, String s3) {
		String s6 = "";

		if (s.equalsIgnoreCase("INT")) {
			if (s2.length() > 0)
				s6 = "(" + s2 + ")";
		} else if (s.equalsIgnoreCase("TINYINT")) {
			if (s2.length() > 0)
				s6 = "(" + s2 + ")";
		} else if (s.equalsIgnoreCase("SMALLINT")) {
			if (s2.length() > 0)
				s6 = "(" + s2 + ")";
		} else if (s.equalsIgnoreCase("MEDIUMINT")) {
			if (s2.length() > 0)
				s6 = "(" + s2 + ")";
		} else if (s.equalsIgnoreCase("INTEGER")) {
			if (s2.length() > 0)
				s6 = "(" + s2 + ")";
		} else if (s.equalsIgnoreCase("BIGINT")) {
			if (s2.length() > 0)
				s6 = "(" + s2 + ")";
		} else if (!s.equalsIgnoreCase("REAL"))
			if (s.equalsIgnoreCase("DOUBLE")) {
				if (s2.length() > 0 && s3.length() > 0)
					s6 = "(" + s2 + "," + s3 + ")";
				else if (s2.length() > 0 && s3.length() == 0)
					s6 = "(" + s2 + ")";
				else if (s2.length() == 0 && s3.length() > 0)
					s6 = "(10," + s3 + ")";
			} else if (s.equalsIgnoreCase("FLOAT")) {
				if (s2.length() > 0 && s3.length() > 0)
					s6 = "(" + s2 + "," + s3 + ")";
				else if (s2.length() > 0 && s3.length() == 0)
					s6 = "(" + s2 + ")";
				else if (s2.length() == 0 && s3.length() > 0)
					s6 = "(10," + s3 + ")";
			} else if (s.equalsIgnoreCase("DECIMAL")) {
				if (s2.length() > 0 && s3.length() > 0)
					s6 = "(" + s2 + "," + s3 + ")";
				else if (s2.length() > 0 && s3.length() == 0)
					s6 = "(" + s2 + ")";
				else if (s2.length() == 0 && s3.length() > 0)
					s6 = "(10," + s3 + ")";
			} else if (s.equalsIgnoreCase("NUMERIC")) {
				if (s2.length() > 0 && s3.length() > 0)
					s6 = "(" + s2 + "," + s3 + ")";
				else if (s2.length() > 0 && s3.length() == 0)
					s6 = "(" + s2 + ")";
				else if (s2.length() == 0 && s3.length() > 0)
					s6 = "(10," + s3 + ")";
			} else if (s.equalsIgnoreCase("CHAR"))
				s6 = "(" + s1 + ")";
			else if (s.equalsIgnoreCase("VARCHAR"))
				s6 = "(" + s1 + ")";
			else if (s.equalsIgnoreCase("TINYTEXT") || s.equalsIgnoreCase("DATE") || s.equalsIgnoreCase("TIME")
					|| s.equalsIgnoreCase("TIMESTAMP") || s.equalsIgnoreCase("DATETIME") || s.equalsIgnoreCase("TINYBLOB")
					|| s.equalsIgnoreCase("BLOB") || s.equalsIgnoreCase("MEDIUMBLOB") || s.equalsIgnoreCase("LONGBLOB")
					|| s.equalsIgnoreCase("TEXT") || s.equalsIgnoreCase("MEDIUMTEXT") || s.equalsIgnoreCase("LONGTEXT")
					|| s.equalsIgnoreCase("ENUM") || !s.equalsIgnoreCase("SET"))
				;

		return s6;
	}
	
	/**
	 * [機 能] 得到要显示的列.
	 * 
	 * @param s,s1,s2,s3 result结果.
	 */
	public static String getOracleDataTypeDefinitionString(String s, String s1, String s2, String s3) {
		String s6 = "";

		if (!s.equalsIgnoreCase("BFILE") && !s.equalsIgnoreCase("BLOB"))
			if (s.equalsIgnoreCase("CHAR"))
				s6 = "(" + s1 + ")";
			else if (!s.equalsIgnoreCase("CLOB") && !s.equalsIgnoreCase("NCLOB") && !s.equalsIgnoreCase("DATE")
					&& !s.equalsIgnoreCase("FLOAT") && !s.equalsIgnoreCase("LONG"))
				if (s.equalsIgnoreCase("NCHAR"))
					s6 = "(" + s1 + ")";
				else if (s.equalsIgnoreCase("NUMBER"))
					s6 = "(" + s2 + "," + s3 + ")";
				else if (s.equalsIgnoreCase("NVARCHAR2"))
					s6 = "(" + s1 + ")";
				else if (s.equalsIgnoreCase("RAW"))
					s6 = "(" + s1 + ")";
				else if (!s.equalsIgnoreCase("ROWID"))
					if (s.equalsIgnoreCase("VARCHAR"))
						s6 = "(" + s1 + ")";
					else if (s.equalsIgnoreCase("VARCHAR2"))
						s6 = "(" + s1 + ")";

		return s6;
	}
	
	/**
	 * [機 能] 得到要显示的列.
	 * 
	 * @param s,s1,s2,s3
	 *            result结果.
	 */
	public static String getDb2DataTypeDefinitionString(String s, String s1, String s2, String s3) {
		String s6 = "";

		if (!s.equalsIgnoreCase("BIGINT"))
			if (s.equalsIgnoreCase("DECIMAL"))
				s6 = "(" + s2 + "," + s3 + ")";
			else if (!s.equalsIgnoreCase("DOUBLE") && !s.equalsIgnoreCase("INTEGER") && !s.equalsIgnoreCase("REAL")
					&& !s.equalsIgnoreCase("SMALLINT"))
				if (s.equalsIgnoreCase("CHARACTER"))
					s6 = "(" + s1 + ")";
				else if (s.equalsIgnoreCase("VARCHAR"))
					s6 = "(" + s1 + ")";
				else if (!s.equalsIgnoreCase("DATE") && !s.equalsIgnoreCase("TIME") && !s.equalsIgnoreCase("TIMESTAMP"))
					if (s.equalsIgnoreCase("CLOB"))
						s6 = "(" + s1 + ")";
					else if (s.equalsIgnoreCase("BLOB"))
						s6 = "(" + s1 + ")";
					else if (!s.equalsIgnoreCase("LONG VARCHAR"))
						;

		return s6;
	}

}


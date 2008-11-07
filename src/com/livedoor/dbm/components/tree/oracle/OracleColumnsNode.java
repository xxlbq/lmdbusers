package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;
import java.sql.ResultSet;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.DBMSchemaTreeInit;
/**
 * <p>
 * Description: OracleColumnsNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleColumnsNode extends DBMResultsetList {
	/**
	 * [解 説]返回获取Oracle所有数据库的sql脚本
	 * 
	 * @return s SQL
	 */
	public String getSql() {
		String s = "select COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, DATA_SCALE, NULLABLE, DATA_DEFAULT from SYS.ALL_TAB_COLUMNS WHERE TABLE_NAME = '"
				+ ((DBMTreeNode) getParent()).getId()
				+ "' AND OWNER = '"
				+ ((DBMTreeNode) (getParent().getParent().getParent())).getId()
				+ "' ORDER BY COLUMN_ID";
		return s;
	}
	/**
	 * [解 説]生成当前节点的子节点 及OracleColumnNode
	 * 
	 * @param rs
	 * @return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {

		DBMTreeNode treeNode = null;
		String s = "";
		String s1 = "";
		String s2 = "";
		String s3 = "";
		String s4 = "";
		try {
			s = rs.getString(1);
			s1 = rs.getString(2);
			s2 = rs.getString(3);
			s3 = rs.getString(4);
			s4 = rs.getString(5);
		} catch (Exception exception) {
			s = null;
			s1 = null;
			s2 = null;
		}
		if (s1 != null && s1.equalsIgnoreCase("NUMBER")) {
			if (s3 == null)
				s3 = "";
			if (s4 == null)
				s4 = "";
			if (s3.length() == 0)
				s3 = s2;
			if (s4.length() == 0)
				s4 = "0";
		}
		if (s == null) {
			return null;
		} else {

			String s5 = DBMSchemaTreeInit.getOracleDataTypeDefinitionString(s1, s2, s3, s4);

			treeNode = new OracleColumnNode(s + " (" + s1 + s5 + ")");
		}

		return treeNode;
	}
	/**
	 * [機 能] OracleColumnsNode [解 説] OracleColumnsNode 。
	 * 
	 * @param connInfo
	 */
	public OracleColumnsNode(ConnectionInfo connInfo) {
		super(connInfo, "Columns", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");

	}
	

}

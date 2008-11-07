package com.livedoor.dbm.components.tree.mysql;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;

import java.awt.Color;
import java.sql.ResultSet;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.DBMSchemaTreeInit;
/**
 * <p>
 * Description: MySqlColumnsNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlColumnsNode extends DBMResultsetList {
	/**
	 * [解 説]返回获取MySql所有数据库的sql脚本
	 * 
	 * @return s SQL
	 */
	public String getSql() {
		char c = '`';
		return "SHOW COLUMNS FROM " + c + ((DBMTreeNode) getParent()).getId() + c + " FROM " + c
				+ ((DBMTreeNode) (getParent().getParent().getParent())).getId() + c;
	}
	/**
	 * [解 説]生成当前节点的子节点 及MySqlColumnNode
	 * 
	 * @param rs
	 * @return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {
		DBMTreeNode treeNode = null;

		String s = "";
		String s1 = "";
		String s3 = "";
		String s4 = "";
		String s5 = "";
		String s6 = "";
		try {
			s = rs.getString("Field");
			String s2 = rs.getString("Type");
			s3 = DBMSchemaTreeInit.getMySQLDataType(s2);
			s4 = DBMSchemaTreeInit.getMySQLLength(s2);
			s5 = DBMSchemaTreeInit.getMySQLPrecision(s2);
			s6 = DBMSchemaTreeInit.getMySQLScale(s2);
		} catch (Exception exception) {
			s = null;
		}
		if (s == null) {
			return null;
		} else {
			String s7 = DBMSchemaTreeInit.getMysqlDataTypeDefinitionString(s3, s4, s5, s6);
			treeNode = new MySqlColumnNode(s + " (" + s3 + s7 + ")");
		}

		return treeNode;
	}
	/**
	 * [機 能] MySqlColumnsNode [解 説] MySqlColumnsNode 。
	 * 
	 * @param connInfo
	 */
	public MySqlColumnsNode(ConnectionInfo connInfo) {
		super(connInfo, "Columns", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");

	}



}

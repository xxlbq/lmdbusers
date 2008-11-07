package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;
/**
 * <p>
 * Description: OracleProceduresNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleProceduresNode extends DBMResultsetList {
	/**
	 * [機 能] OracleProceduresNode 
	 * [解 説] OracleProceduresNode 。
	 * 
	 * @param connInfo
	 */
	public OracleProceduresNode(ConnectionInfo connInfo) {
		super(connInfo, "Procedures", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");

	}
	/**
	 * [解 説]返回获取Oracle所有数据库的sql脚本
	 * 
	 * @return s SQL
	 */
	public String getSql() {
		return "SELECT OBJECT_NAME FROM ALL_OBJECTS WHERE OBJECT_TYPE = 'PROCEDURE' AND owner = '" + ((DBMTreeNode) getParent()).getId()
				+ "' ORDER BY OBJECT_NAME ";
	}
	/**
	 * [解 説]生成当前节点的子节点 及OracleProcedureNode
	 * 
	 * @param rs
	 * @return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {
		DBMTreeNode treeNode = null;
		String s;
		try {
			s = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			s = null;
		}

		if (StringUtil.isNotEmpty(s)) {
			treeNode = new OracleProcedureNode(s);
		}

		return treeNode;
	}

}

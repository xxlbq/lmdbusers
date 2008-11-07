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
 * Description: OracleFunctionsNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleFunctionsNode extends DBMResultsetList {
	/**
	 * [解 説]返回获取Oracle所有数据库的sql脚本
	 * 
	 * @return s SQL
	 */
	public String getSql() {
		return "SELECT OBJECT_NAME FROM ALL_OBJECTS WHERE OBJECT_TYPE = 'FUNCTION' AND owner = '" + ((DBMTreeNode) getParent()).getId()
				+ "' ORDER BY OBJECT_NAME ";
	}
	/**
	 * [機 能] OracleFunctionsNode 
	 * [解 説] OracleFunctionsNode 。
	 * 
	 * @param connInfo
	 */
	public OracleFunctionsNode(ConnectionInfo connInfo) {
		super(connInfo, "Functions", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");

	}
	/**
	 * [解 説]生成当前节点的子节点 及OracleFunctionNode
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
			treeNode = new OracleFunctionNode(s);
		}

		return treeNode;
	}

}

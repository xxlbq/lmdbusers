package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.ResultSet;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Description: OracleProcessInfosNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleProcessInfosNode extends DBMResultsetList {
	/**
	 * [機 能] OracleProcessInfosNode 
	 * [解 説] OracleProcessInfosNode 。
	 * 
	 * @param connInfo
	 */
	public OracleProcessInfosNode(ConnectionInfo connectionInfo) {
		super(connectionInfo, "Session Info", null, Color.black, "TREE_CONN_PROCESSINFO", "TREE_CONN_PROCESSINFO");
	}

	/**
	 * [解 説]返回获取Oracle所有数据库的sql脚本
	 * 
	 * @return s SQL
	 */
	public String getSql() {
		return "select SID, USERNAME, MACHINE from v$session";
	}

	/**
	 * [解 説]生成当前节点的子节点 及OracleProcessInfoNode
	 * 
	 * @param rs
	 * @return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {

		BigDecimal bigdecimal = null;
		String s = null;
		String s1 = null;
		try {
			bigdecimal = (BigDecimal) rs.getObject(1);
			s = (String) rs.getObject(2);
			s1 = (String) rs.getObject(3);
		} catch (Exception exception) {
			bigdecimal = null;
			s = null;
		}
		if (bigdecimal == null)
			return null;
		if (s == null)
			s = "Background";
		if (s1 == null)
			s1 = "Unknown";
		return new OracleProcessInfoNode(bigdecimal.toString() + " - " + s + " (" + s1 + ")");

	}

}

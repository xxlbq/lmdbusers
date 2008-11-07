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
 * Description: OracleTriggersNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleTriggersNode extends DBMResultsetList {
	/**
	 * [解 説]返回获取Oracle所有数据库的sql脚本
	 * 
	 * @return s SQL
	 */
	public String getSql() {
		return "SELECT OWNER, TRIGGER_NAME, TABLE_OWNER, TABLE_NAME FROM ALL_TRIGGERS WHERE OWNER = '" + ((DBMTreeNode) getParent()).getId() + "' ORDER BY TRIGGER_NAME";
	}
	/**
	 * [解 説]生成当前节点的子节点 及OracleTriggerNode
	 * 
	 * @param rs
	 * @return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {
		DBMTreeNode treeNode = null;
		String s;
		try {

			s = (String) rs.getObject(2) + "[" + (String) rs.getObject(4) + "]";

		} catch (SQLException e) {
			e.printStackTrace();
			s = null;
		}

		if (StringUtil.isNotEmpty(s)) {
			treeNode = new OracleTriggerNode(s);
		}

		return treeNode;
	}
	/**
	 * [機 能] OracleTriggersNode 
	 * [解 説] OracleTriggersNode 。
	 * 
	 * @param connInfo
	 */
	public OracleTriggersNode(ConnectionInfo connInfo) {
		super(connInfo, "Triggers", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");

	}

}

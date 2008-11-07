package com.livedoor.dbm.components.tree.mysql;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;
/**
 * <p>
 * Description: MySqlTriggersNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlTriggersNode extends DBMResultsetList {
	/**
	 *[解 説]返回获取MySql所有数据库的sql脚本
	 *@return s SQL
	 */
	public String getSql() {
		return "select  trigger_name, event_object_table from information_schema.triggers where trigger_schema = '"
				+ ((DBMTreeNode) getParent()).getId() + "'";

	}
	/**
	 *[解 説]生成当前节点的子节点 及MySqlTriggerNode
	 *@param rs
	 *@return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {
		DBMTreeNode treeNode = null;
		String s;
		try {
			s = (String) rs.getObject(1);
			s += "[" + (String) rs.getObject(2) + "]";
		} catch (SQLException e) {
			e.printStackTrace();
			s = null;
		}

		if (StringUtil.isNotEmpty(s)) {
			treeNode = new MySqlTriggerNode(s);
		}

		return treeNode;
	}
	/**
	 * [機 能] MySqlTriggersNode 
	 * [解 説] MySqlTriggersNode 。
	 * 
	 * @param connInfo
	 */
	public MySqlTriggersNode(ConnectionInfo connInfo) {
		super(connInfo, "Triggers", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");

	}

}

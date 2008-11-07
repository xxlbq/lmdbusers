package com.livedoor.dbm.components.tree.mysql;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;
/**
 * <p>
 * Description: MySqlLocksNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlLocksNode extends DBMResultsetList {
	/**
	 * [機 能] MySqlLocksNode 
	 * [解 説] MySqlLocksNode 。
	 * 
	 * @param connInfo
	 */
	public MySqlLocksNode(ConnectionInfo connectionInfo) {
		super(connectionInfo, "Locks / Object", null, Color.black, "TREE_CONN_LOCKOBJECTFOLDER", "TREE_CONN_LOCKOBJECTFOLDER");
	}
	/**
	 *[解 説]返回获取MySql所有数据库的sql脚本
	 *@return s SQL
	 */
	public String getSql() {
		return "SHOW PROCESSLIST";
	}

	/**
	 *[解 説]生成当前节点的子节点 及MySqlLockNode
	 *@param rs
	 *@return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {

		DBMTreeNode treeNode = null;
		String s = null;
		try {
			if ("Locked".equals(rs.getString("State"))) {

				s = rs.getString("Id") + "-" + rs.getString("User") + "(" + rs.getString("Host") + ")" + "-" + rs.getString("db") + " "
						+ rs.getString("Info");
			}
		} catch (SQLException e) {
			s = null;
		}

		if (StringUtil.isNotEmpty(s)) {
			treeNode = new MySqlLockNode(s);
		}

		return treeNode;

	}

}

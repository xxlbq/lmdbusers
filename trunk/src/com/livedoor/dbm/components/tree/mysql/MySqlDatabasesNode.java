package com.livedoor.dbm.components.tree.mysql;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.util.StringUtil;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * <p>
 * Description: MySqlDatabasesNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlDatabasesNode extends DBMResultsetList {
	/**
	 * [機 能] MySqlDatabasesNode 
	 * [解 説] MySqlDatabasesNode 。
	 * 
	 * @param connInfo
	 */
	public MySqlDatabasesNode(ConnectionInfo connectionInfo) {
		super(connectionInfo, "Databases", null, Color.black, "TREE_CONN_DATABASES", "TREE_CONN_DATABASES");
	}

	/**
	 *[解 説]返回获取MySql所有数据库的sql脚本
	 *@return s SQL
	 */
	public String getSql() {
		return "SHOW DATABASES";
	}

	/**
	 *[解 説]生成当前节点的子节点 及MySqlDatabaseNode
	 *@param rs
	 *@return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {

		DBMTreeNode treeNode = null;
		String s;
		try {
			s = (String) rs.getObject(1);
		} catch (SQLException e) {
			e.printStackTrace();
			s = null;
		}

		if (StringUtil.isNotEmpty(s)) {
			treeNode = new MySqlDatabaseNode(connectionInfo, s);
		}

		return treeNode;

	}

}

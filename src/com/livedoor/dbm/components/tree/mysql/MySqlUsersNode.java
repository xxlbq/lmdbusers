package com.livedoor.dbm.components.tree.mysql;

import java.awt.Color;
import java.sql.ResultSet;
import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Description: MySqlUsersNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlUsersNode extends DBMResultsetList {
	/**
	 * [機 能] MySqlUsersNode 
	 * [解 説] MySqlUsersNode 。
	 * 
	 * @param connInfo
	 */
	public MySqlUsersNode(ConnectionInfo connectionInfo) {
		super(connectionInfo, "Users", null, Color.black, "TREE_CONN_CLOSEDFOLDER", "TREE_CONN_CLOSEDFOLDER");
		// TODO 自动生成构造函数存根
	}
	/**
	 *[解 説]生成当前节点的子节点 及MySqlUserNode
	 *@param rs
	 *@return treeNode
	 */
	@Override
	public DBMTreeNode createNode(ResultSet resultset) {
		String s = null;
		String s1 = null;
		try {
			s = resultset.getString(1);
			s1 = resultset.getString(2);
		} catch (Exception exception) {
			s = null;
		}
		if (s == null)
			return null;
		else
			return new MySqlUserNode(s, s1);
	}
	/**
	 *[解 説]返回获取MySql所有数据库的sql脚本
	 *@return s SQL
	 */
	@Override
	public String getSql() {
		return "SELECT USER, HOST FROM mysql.user where length(USER) > 0";
	}

}

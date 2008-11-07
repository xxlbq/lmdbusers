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
 * Description: MySqlProcessInfosNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlProcessInfosNode extends DBMResultsetList {
	/**
	 * [機 能] MySqlProcessInfosNode 
	 * [解 説] MySqlProcessInfosNode 。
	 * 
	 * @param connInfo
	 */
	public MySqlProcessInfosNode(ConnectionInfo connectionInfo) {
		super(connectionInfo, "Process Info", null, Color.black, "TREE_CONN_PROCESSINFO", "TREE_CONN_PROCESSINFO");
	}

	/**
	 *[解 説]返回获取MySql所有数据库的sql脚本
	 *@return s SQL
	 */
	public String getSql() {
		return "SHOW PROCESSLIST";
	}

	/**
	 *[解 説]生成当前节点的子节点 及MySqlProcessInfoNode
	 *@param rs
	 *@return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {

		DBMTreeNode treeNode = null;
		String s;
		try {
			s = rs.getObject(1).toString() + "-" + (String) rs.getObject(2) + "(" + (String) rs.getObject(3) + ")" + "-"
					+ (String) rs.getObject(5);
		} catch (SQLException e) {
			e.printStackTrace();
			s = null;
		}

		if (StringUtil.isNotEmpty(s)) {
			treeNode = new MySqlProcessInfoNode(s);
		}

		return treeNode;

	}

}
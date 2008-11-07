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
 * Description: MySqlProceduresNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlProceduresNode extends DBMResultsetList {
	/**
	 * [機 能] MySqlProceduresNode 
	 * [解 説] MySqlProceduresNode 。
	 * 
	 * @param connInfo
	 */
	public MySqlProceduresNode(ConnectionInfo connInfo) {
		super(connInfo, "Procedures", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");

	}
	/**
	 *[解 説]返回获取MySql所有数据库的sql脚本
	 *@return s SQL
	 */
	public String getSql() {
		return "select db, name from mysql.proc where db = '" + ((DBMTreeNode) getParent()).getId() + "' and type = 'PROCEDURE'";
	}
	/**
	 *[解 説]生成当前节点的子节点 及MySqlProcedureNode
	 *@param rs
	 *@return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {
		DBMTreeNode treeNode = null;
		String s;
		try {
			s = rs.getString(2);
		} catch (SQLException e) {
			e.printStackTrace();
			s = null;
		}

		if (StringUtil.isNotEmpty(s)) {
			treeNode = new MySqlProcedureNode(s);
		}

		return treeNode;
	}

}

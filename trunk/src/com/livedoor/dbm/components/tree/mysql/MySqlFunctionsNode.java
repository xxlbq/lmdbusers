package com.livedoor.dbm.components.tree.mysql;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * <p>
 * Description: MySqlFunctionsNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlFunctionsNode extends DBMResultsetList {

	/**
	 *[解 説]返回获取MySql所有数据库的sql脚本
	 *@return s SQL
	 */
	public String getSql() {
		return "select db, name from mysql.proc where db = '" + ((DBMTreeNode) getParent()).getId() + "' and type = 'FUNCTION'";
	}
	/**
	 * [機 能] MySqlFunctionsNode 
	 * [解 説] MySqlFunctionsNode 。
	 * 
	 * @param connInfo
	 */
	public MySqlFunctionsNode(ConnectionInfo connInfo) {
		super(connInfo, "Functions", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");

	}
	/**
	 *[解 説]生成当前节点的子节点 及MySqlFunctionNode
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
			treeNode = new MySqlFunctionNode(s);
		}

		return treeNode;
	}

}

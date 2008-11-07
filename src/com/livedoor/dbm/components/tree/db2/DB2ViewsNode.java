package com.livedoor.dbm.components.tree.db2;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;

/**
 * <p>
 * Description: DB2ViewsNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2ViewsNode extends DBMResultsetList {
	/**
	 *[解 説]返回获取DB2所有数据库的sql脚本
	 *@return s SQL
	 */
	public String getSql() {
		return "select NAME, CREATOR, TYPE from sysibm.systables where type = 'V' and creator = '" + ((DBMTreeNode) getParent()).getId()
				+ "' ORDER BY NAME";
	}
	/**
	 *[解 説]生成当前节点的子节点 及DB2ViewNode
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
			treeNode = new DB2ViewNode(connectionInfo, s);
		}

		return treeNode;
	}
	/**
	 * [機 能] DB2ViewsNode 
	 * [解 説] DB2ViewsNode 。
	 * 
	 * @param connInfo
	 */
	public DB2ViewsNode(ConnectionInfo connInfo) {
		super(connInfo, "Views", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");

	}

}

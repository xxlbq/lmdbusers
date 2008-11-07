package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;
import java.sql.ResultSet;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;
/**
 * <p>
 * Description: DB2TablesNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2UsersNode extends DBMResultsetList {
	/**
	 * [機 能] DB2UsersNode 
	 * [解 説] DB2UsersNode 。
	 * 
	 * @param connInfo
	 */
	public DB2UsersNode(ConnectionInfo connectionInfo) {
		super(connectionInfo, "Users", null, Color.black, "TREE_CONN_CLOSEDFOLDER", "TREE_CONN_CLOSEDFOLDER");
	}
	/**
	 *[解 説]生成当前节点的子节点 及DB2UserNode
	 *@param rs
	 *@return treeNode
	 */
	@Override
	public DBMTreeNode createNode(ResultSet rs) {
		DB2UserNode userNode = null;
		String s;
		try {
			s = (String) rs.getObject(1);
			if (s != null)
				s = s.trim();
		} catch (Exception exception) {
			s = null;
		}
		if (StringUtil.isNotEmpty(s)) {
			userNode = new DB2UserNode(s);
		}
		return userNode;
	}
	/**
	 *[解 説]返回获取DB2所有数据库的sql脚本
	 *@return s SQL
	 */
	@Override
	public String getSql() {
		return "select GRANTEE from syscat.dbauth where granteetype = 'U'";
	}

}

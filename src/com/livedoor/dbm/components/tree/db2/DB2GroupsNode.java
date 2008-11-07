package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;
import java.sql.ResultSet;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;
/**
 * <p>
 * Description: DB2GroupsNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2GroupsNode extends DBMResultsetList {
	/**
	 * [機 能] DB2GroupsNode
	 * <p>
	 * [解 説] DB2GroupsNode 。
	 * <p>
	 * 
	 * @param connectionInfo
	 */

	public DB2GroupsNode(ConnectionInfo connectionInfo) {
		super(connectionInfo, "Groups", null, Color.black, "TREE_CONN_CLOSEDFOLDER", "TREE_CONN_CLOSEDFOLDER");
		// TODO 自动生成构造函数存根
	}

	@Override
	public DBMTreeNode createNode(ResultSet rs) {
		DB2GroupNode groupNode = null;
		String s;
		try {
			s = (String) rs.getObject(1);
			if (s != null)
				s = s.trim();
		} catch (Exception exception) {
			s = null;
		}
		if (StringUtil.isNotEmpty(s)) {
			groupNode = new DB2GroupNode(s);
		}
		return groupNode;
	}
	/**
	 * [機 能] getSql
	 * <p>
	 * [解 説] getSql 。
	 * <p>
	 */
	@Override
	public String getSql() {
		return "select GRANTEE from syscat.dbauth where granteetype = 'G'";
	}

}

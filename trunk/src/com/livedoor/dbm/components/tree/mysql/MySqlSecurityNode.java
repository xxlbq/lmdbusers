package com.livedoor.dbm.components.tree.mysql;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Description: MySqlSecurityNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlSecurityNode extends DBMTreeNodeList {

	private ConnectionInfo connectionInfo;
	/**
	 * [機 能] MySqlSecurityNode 
	 * [解 説] MySqlSecurityNode 。
	 * 
	 * @param s
	 */
	public MySqlSecurityNode(ConnectionInfo connInfo) {
		super("Security", null, Color.black, "TREE_CONN_SECURITY", "TREE_CONN_SECURITY");
		this.connectionInfo = connInfo;
	}

	/**
	 * [機 能] get Children List 
	 * [解 説] get Children List 。
	 * 
	 * @return childrenList
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		childrenList.add(new MySqlUsersNode(connectionInfo));

		return childrenList;
	}

}

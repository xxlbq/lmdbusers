package com.livedoor.dbm.components.tree.mysql;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * <p>
 * Description: MySqlUserNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */

@SuppressWarnings("serial")
public class MySqlUserNode extends DBMTreeNode {
	/**
	 * [機 能] MySqlUserNode 
	 * [解 説] MySqlUserNode 。
	 * 
	 * @param s
	 */
	public MySqlUserNode(String s, String s1) {
		super(s + "@" + s1, null, Color.black, "TREE_CONN_USERS", "TREE_CONN_USERS");
	}

}

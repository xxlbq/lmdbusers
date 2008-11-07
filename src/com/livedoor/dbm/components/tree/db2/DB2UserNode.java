package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * <p>
 * Description: DB2UserNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */

@SuppressWarnings("serial")
public class DB2UserNode extends DBMTreeNode {
	/**
	 * [機 能] DB2UserNode 
	 * [解 説] DB2UserNode 。
	 * 
	 * @param s
	 */
	public DB2UserNode(String s) {
		super(s, null, Color.black, "TREE_CONN_USERS", "TREE_CONN_USERS");
	}

}

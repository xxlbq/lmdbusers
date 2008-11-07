package com.livedoor.dbm.components.tree.mysql;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * <p>
 * Description: MySqlLockNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlLockNode extends DBMTreeNode {
	/**
	 * [機 能] MySqlLockNode 
	 * [解 説] MySqlLockNode 。
	 * 
	 * @param s
	 */
	public MySqlLockNode(String s) {
		super(s,  null, Color.black, "TREE_CONN_LOCKSOBJECTS", "TREE_CONN_LOCKSOBJECTS");
	}

}

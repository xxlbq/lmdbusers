package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * <p>
 * Description: DB2LockNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2LockNode extends DBMTreeNode {
	/**
	 * [機 能] DB2LockNode
	 * [解 説] DB2LockNode 。
	 * 
	 * @param s
	 */
	public DB2LockNode(String s) {
		super(s, null, Color.black, "TREE_CONN_LOCKSOBJECTS", "TREE_CONN_LOCKSOBJECTS");
	}

}
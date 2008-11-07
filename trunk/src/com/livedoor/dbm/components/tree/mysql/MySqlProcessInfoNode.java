package com.livedoor.dbm.components.tree.mysql;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * <p>
 * Description: MySqlProcessInfoNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlProcessInfoNode extends DBMTreeNode {
	/**
	 * [機 能] MySqlProcessInfoNode 
	 * [解 説] MySqlProcessInfoNode 。
	 * 
	 * @param s
	 */
	public MySqlProcessInfoNode(String s) {
		super(s, null, Color.black, "TREE_CONN_PROCESS", "TREE_CONN_PROCESS");
	}

}

package com.livedoor.dbm.components.tree.mysql;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * <p>
 * Description: MySqlTriggerNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlTriggerNode extends DBMTreeNode {
	/**
	 * [機 能] MySqlTriggerNode [解 説] MySqlTriggerNode 。
	 * 
	 * @param s
	 */
	public MySqlTriggerNode(String s) {
		super(s, null, Color.black, "TREE_CONN_TRIGGERS", "TREE_CONN_TRIGGERS");
	}

}

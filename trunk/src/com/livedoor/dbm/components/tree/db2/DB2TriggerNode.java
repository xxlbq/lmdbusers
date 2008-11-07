package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * <p>
 * Description: DB2TriggerNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2TriggerNode extends DBMTreeNode {
	/**
	 * [機 能] DB2TriggerNode [解 説] DB2TriggerNode 。
	 * 
	 * @param s
	 */
	public DB2TriggerNode(String s) {
		super(s, null, Color.black, "TREE_CONN_TRIGGERS", "TREE_CONN_TRIGGERS");
	}
}

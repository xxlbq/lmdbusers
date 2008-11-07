package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * Description: OracleTriggerNode Copyright: Copyright (c) 2006 Company:
 * 英極軟件開發（大連）有限公司
 * 
 * @author 车鹏
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleTriggerNode extends DBMTreeNode {
	/**
	 * [機 能] OracleTriggerNode [解 説] OracleTriggerNode 。
	 * 
	 * @param s
	 */
	public OracleTriggerNode(String s) {
		super(s, null, Color.black, "TREE_CONN_TRIGGERS", "TREE_CONN_TRIGGERS");
	}

}

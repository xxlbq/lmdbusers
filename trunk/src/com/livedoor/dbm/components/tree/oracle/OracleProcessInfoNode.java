package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * Description: OracleProcessInfoNode
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司 
 * @author 车鹏
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleProcessInfoNode extends DBMTreeNode {
	/**
	 * [機 能] OracleProcessInfoNode 
	 * [解 説] OracleProcessInfoNode 。
	 * 
	 * @param s
	 */
	public OracleProcessInfoNode(String s) {
		super(s, null, Color.black, "TREE_CONN_SESSION", "TREE_CONN_SESSION");
	}

}

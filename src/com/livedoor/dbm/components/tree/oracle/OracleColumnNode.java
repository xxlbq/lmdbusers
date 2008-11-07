package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;

/**
 * Description: OracleColumnNode
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司 
 * @author 车鹏
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleColumnNode extends DBMTreeNode {
	/**
	 * [機 能] OracleColumnNode 
	 * [解 説] OracleColumnNode 。
	 * 
	 * @param s
	 */
	public OracleColumnNode(String s) {
		super(s, null, Color.black, "TREE_CONN_COLUMN", "TREE_CONN_COLUMN");
	}

}

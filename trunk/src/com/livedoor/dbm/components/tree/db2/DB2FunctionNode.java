package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * <p>
 * Description: DB2FunctionNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2FunctionNode extends DBMTreeNode {
	/**
	 * [機 能] 创建 DB2FunctionNode
	 * <p>
	 * [解 説] 创建 DB2FunctionNode 。
	 * <p>
	 * 
	 * @param s
	 */
	public DB2FunctionNode(String s) {
		super(s, null, Color.black, "TREE_CONN_UDFS", "TREE_CONN_UDFS");
	}
}

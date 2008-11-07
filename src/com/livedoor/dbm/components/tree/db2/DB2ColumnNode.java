package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * <p>
 * Description: DB2ColumnNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2ColumnNode extends DBMTreeNode {
	/**
	 * 
	 * @param s
	 *            列名
	 */
	public DB2ColumnNode(String s) {
		super(s, null, Color.black, "TREE_CONN_COLUMN", "TREE_CONN_COLUMN");
	}

}

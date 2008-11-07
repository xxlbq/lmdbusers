package com.livedoor.dbm.components.tree.mysql;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * <p>
 * Description: MySqlProcedureNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlProcedureNode extends DBMTreeNode {
	/**
	 * [機 能] MySqlProcedureNode [解 説] MySqlProcedureNode 。
	 * 
	 * @param s
	 */
	public MySqlProcedureNode(String s) {
		super(s, null, Color.black, "TREE_CONN_STOREDPROCEDURES", "TREE_CONN_STOREDPROCEDURES");
	}

}

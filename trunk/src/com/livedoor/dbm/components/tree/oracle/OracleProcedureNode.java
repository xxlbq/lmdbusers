package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;

import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * Description: OracleProcedureNode Copyright: Copyright (c) 2006 Company:
 * 英極軟件開發（大連）有限公司
 * 
 * @author 车鹏
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleProcedureNode extends DBMTreeNode {
	/**
	 * [機 能] OracleProcedureNode [解 説] OracleProcedureNode 。
	 * 
	 * @param s
	 */
	public OracleProcedureNode(String s) {
		super(s, null, Color.black, "TREE_CONN_UDFS", "TREE_CONN_UDFS");
	}
}

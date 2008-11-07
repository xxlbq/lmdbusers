package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;
import com.livedoor.dbm.components.tree.DBMTreeNode;
/**
 * Description: OracleUserNode Copyright: Copyright (c) 2006 Company:
 * 英極軟件開發（大連）有限公司
 * 
 * @author 车鹏
 * @version 1.0
 */

@SuppressWarnings("serial")
public class OracleUserNode extends DBMTreeNode {
	/**
	 * [機 能] OracleUserNode [解 説] OracleUserNode 。
	 * 
	 * @param s
	 */
	public OracleUserNode(String s) {
		super(s, null, Color.black, "TREE_CONN_USERS", "TREE_CONN_USERS");
	}

}

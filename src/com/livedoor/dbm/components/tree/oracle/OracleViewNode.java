package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * Description: OracleViewNode Copyright: Copyright (c) 2006 Company:
 * 英極軟件開發（大連）有限公司
 * 
 * @author 车鹏
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleViewNode extends DBMTreeNodeList {

	private ConnectionInfo connectionInfo;
	/**
	 * [機 能] OracleViewNode [解 説] OracleViewNode 。
	 * 
	 * @param s
	 */
	public OracleViewNode(ConnectionInfo connInfo, String tblName) {
		super(tblName, null, Color.black, "TREE_CONN_VIEWS", "TREE_CONN_VIEWS");
		this.connectionInfo = connInfo;
	}

	/**
	 * [機 能] get Children List [解 説] get Children List 。
	 * 
	 * @return childrenList
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		childrenList.add(new OracleColumnsNode(connectionInfo));

		return childrenList;
	}

}

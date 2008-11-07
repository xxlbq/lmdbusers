package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Description: DB2LocksNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2ManagementNode extends DBMTreeNodeList {

	private ConnectionInfo connectionInfo;
	/**
	 * [機 能] DB2ManagementNode 
	 * [解 説] DB2ManagementNode 。
	 * 
	 * @param connInfo
	 */
	public DB2ManagementNode(ConnectionInfo connInfo) {
		super("Management", null, Color.black, "TREE_CONN_MAINTENANCE", "TREE_CONN_MAINTENANCE");
		this.connectionInfo = connInfo;
	}
	/**
	 * [機 能] getChildrenList 
	 * [解 説] getChildrenList
	 * 
	 * @return childrenList
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		childrenList.add(new DB2CurrentActivityNode(connectionInfo));
		return childrenList;
	}

}

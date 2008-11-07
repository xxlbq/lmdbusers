package com.livedoor.dbm.components.tree.mysql;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Description: MySqlCurrentActivityNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlCurrentActivityNode extends DBMTreeNodeList {

	private ConnectionInfo connectionInfo;
	/**
	 * [機 能] MySqlCurrentActivityNode 
	 * [解 説] MySqlCurrentActivityNode 。
	 * 
	 * @param s
	 */
	public MySqlCurrentActivityNode(ConnectionInfo connInfo) {
		super("Current Activity", null, Color.black, "TREE_CONN_CURRENTACTIVITY", "TREE_CONN_CURRENTACTIVITY");
		this.connectionInfo = connInfo;
	}

	/**
	 * [機 能] get Children List 
	 * [解 説] get Children List 。
	 * 
	 * @return childrenList
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		childrenList.add(new MySqlProcessInfosNode(connectionInfo));
		childrenList.add(new MySqlLocksNode(connectionInfo));
		return childrenList;
	}

}

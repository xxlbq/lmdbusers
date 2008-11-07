package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Description: DB2CurrentActivityNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2CurrentActivityNode extends DBMTreeNodeList {

	private ConnectionInfo connectionInfo;
	/**
	 * [機 能] 创建 DB2CurrentActivityNode
	 * <p>
	 * [解 説] 创建 DB2CurrentActivityNode 。
	 * <p>
	 * 
	 * @param connInfo
	 */
	public DB2CurrentActivityNode(ConnectionInfo connInfo) {
		super("Current Activity", null, Color.black, "TREE_CONN_CURRENTACTIVITY", "TREE_CONN_CURRENTACTIVITY");
		this.connectionInfo = connInfo;
	}

	/**
	 * [機 能] 获取ChildrenList
	 * <p>
	 * [解 説] 获取ChildrenList 。
	 * <p>
	 * 
	 * @return childrenList [備 考] なし
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		childrenList.add(new DB2ProcessInfosNode(connectionInfo));
		childrenList.add(new DB2LocksNode(connectionInfo));
		return childrenList;
	}

}

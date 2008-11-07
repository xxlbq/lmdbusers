package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Description: DB2ViewNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2ViewNode extends DBMTreeNodeList {

	private ConnectionInfo connectionInfo;
	/**
	 * [機 能] DB2ViewNode 
	 * [解 説] DB2ViewNode 。
	 * 
	 * @param s
	 */
	public DB2ViewNode(ConnectionInfo connInfo,  String tblName) {
		super(tblName, null, Color.black, "TREE_CONN_VIEWS", "TREE_CONN_VIEWS");
		this.connectionInfo = connInfo;
	}

	/**
	 * [機 能] getChildrenList 
	 * [解 説] getChildrenList 。
	 * @return childrenList
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		childrenList.add(new DB2ColumnsNode(connectionInfo));
		
		return childrenList;
	}
	 
}

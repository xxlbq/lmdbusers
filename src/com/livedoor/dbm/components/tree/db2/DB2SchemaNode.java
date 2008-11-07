package com.livedoor.dbm.components.tree.db2;

import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.tree.SchemaBaseNode;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Description: DB2SchemaNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2SchemaNode extends SchemaBaseNode {
	/**
	 * [機 能] getChildrenList 
	 * [解 説] getChildrenList 。
	 * 
	 * @return childrenList
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		childrenList.add(new DB2TablesNode(connectionInfo));
		childrenList.add(new DB2ViewsNode(connectionInfo));
		childrenList.add(new DB2FunctionsNode(connectionInfo));
		childrenList.add(new DB2TriggersNode(connectionInfo));
		childrenList.add(new DB2ProceduresNode(connectionInfo));

		return childrenList;
	}
	/**
	 * [機 能] DB2SchemaNode 
	 * [解 説] DB2SchemaNode 。
	 * 
	 * @param s
	 */
	public DB2SchemaNode(ConnectionInfo connInfo, String dbName) {
		super(connInfo, dbName);
	}

}

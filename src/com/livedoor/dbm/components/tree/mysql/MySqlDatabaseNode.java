package com.livedoor.dbm.components.tree.mysql;

import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.tree.DatabaseBaseNode;
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
public class MySqlDatabaseNode extends DatabaseBaseNode {

	/**
	 * [機 能] get Children List 
	 * [解 説] get Children List 。
	 * 
	 * @return childrenList
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();
		childrenList.add(new MySqlTablesNode(connectionInfo));
		childrenList.add(new MySqlViewsNode(connectionInfo));
		childrenList.add(new MySqlFunctionsNode(connectionInfo));
		childrenList.add(new MySqlTriggersNode(connectionInfo));
		childrenList.add(new MySqlProceduresNode(connectionInfo));

		return childrenList;
	}
	/**
	 * [機 能] MySqlCurrentActivityNode 
	 * [解 説] MySqlCurrentActivityNode 。
	 * 
	 * @param s
	 */
	public MySqlDatabaseNode(ConnectionInfo connInfo, String dbName) {
		super(connInfo, dbName);

	}

}

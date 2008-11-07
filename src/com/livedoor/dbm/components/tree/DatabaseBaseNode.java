/*
 * 
*/
package com.livedoor.dbm.components.tree;

import java.awt.Color;

import com.livedoor.dbm.connection.ConnectionInfo;

/**
 * <p>Description:DatabaseBaseNode    </p>
 * Copyright: Copyright (c) 2006 
 * Company: 英極軟件開發（大連）有限公司
 * @author chepeng
 * @version 1.0
 */
public abstract class DatabaseBaseNode extends DBMTreeNodeList {
	protected ConnectionInfo connectionInfo;
	/**
	 * [機 能] 构造 DatabaseBaseNode 节点
	 * @param connInfo
	 * @param dbName
	 */	
	public DatabaseBaseNode(ConnectionInfo connInfo, String dbName) {
		super(dbName,null,Color.black,"TREE_CONN_DATABASE","TREE_CONN_DATABASE");
		this.connectionInfo = connInfo;
	}

}


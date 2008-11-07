/*
 * 
*/
package com.livedoor.dbm.components.tree;

import java.awt.Color;

import com.livedoor.dbm.connection.ConnectionInfo;

/**
 * <p>
 * Description: 定义抽象类SchemaBaseNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */

public abstract class SchemaBaseNode extends DBMTreeNodeList {
	protected  ConnectionInfo connectionInfo;
	/**
	 * [機 能] 创建 SchemaBaseNode
	 * [解 説] 创建 SchemaBaseNode
	 * @param connInfo
	 * @param dbName
	 */
	public SchemaBaseNode(ConnectionInfo connInfo, String dbName) {
		super(dbName,null,Color.black,"TREE_CONN_SCHEMA","TREE_CONN_SCHEMA");
		this.connectionInfo = connInfo;
	}

}


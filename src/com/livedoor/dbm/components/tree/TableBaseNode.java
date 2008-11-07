package com.livedoor.dbm.components.tree;

import java.awt.Color;

import com.livedoor.dbm.connection.ConnectionInfo;

/**
 * <p>
 * Description: 定义抽象类TableBaseNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
public abstract class TableBaseNode extends DBMTreeNodeList {
	protected ConnectionInfo connectionInfo;
	/**
	 * [機 能] 创建 TableBaseNode
	 * [解 説] 创建 TableBaseNode
	 * @param connInfo
	 * @param tblName
	 */
	public TableBaseNode(ConnectionInfo connInfo,  String tblName) {
		super(tblName, null, Color.black, "TREE_CONN_TABLES", "TREE_CONN_TABLES");
		this.connectionInfo = connInfo;
	}

}

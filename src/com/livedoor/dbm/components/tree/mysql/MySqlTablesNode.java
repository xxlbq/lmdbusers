package com.livedoor.dbm.components.tree.mysql;

import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreeNodeList;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.exception.DBMException;
/**
 * <p>
 * Description: MySqlTablesNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MySqlTablesNode extends DBMTreeNodeList {

	private ConnectionInfo connectionInfo;
	/**
	 * [機 能] MySqlViewNode 
	 * [解 説] MySqlViewNode 。
	 * 
	 * @param s
	 */
	public MySqlTablesNode(ConnectionInfo connInfo) {
		super("Tables", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");
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
		DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
		dbMetaInfo.setDatabaseName(((DBMTreeNode) getParent()).getId());
		List list = null;
		try {
			list = DBMSqlExecuterFactory.createExecuter(connectionInfo, getDBSession()).getTableNames(dbMetaInfo);
			
		} catch (DBMException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	    
		if(list != null){
	    	for(int i = 0 ;i< list.size();i++)
			childrenList.add(new MySqlTableNode(connectionInfo, (String)list.get(i)));
		    }
		return childrenList;
	}


}

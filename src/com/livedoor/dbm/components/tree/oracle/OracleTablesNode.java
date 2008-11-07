package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;
/**
 * <p>
 * Description: OracleTablesNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleTablesNode extends DBMResultsetList {
	/**
	 * [機 能] OracleTablesNode 
	 * [解 説] OracleTablesNode 。
	 * 
	 * @param connInfo
	 */
	public  OracleTablesNode(ConnectionInfo connInfo) {
		 super(connInfo,"Tables", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");
		  
	}
	/**
	 * [解 説]返回获取Oracle所有数据库的sql脚本
	 * 
	 * @return s SQL
	 */
	public String getSql() {
		return "SELECT TABLE_NAME FROM ALL_TABLES WHERE OWNER = '"+((DBMTreeNode)getParent()).getId()+"' ORDER BY TABLE_NAME";
	}
	/**
	 * [解 説]生成当前节点的子节点 及OracleTableNode
	 * 
	 * @param rs
	 * @return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {
		DBMTreeNode treeNode = null;
		String   s;
        try {
			   s = (String)rs.getObject(1);
		} catch (SQLException e) {
			e.printStackTrace();
			s = null;
		}
		
		if(StringUtil.isNotEmpty(s)){
			treeNode = new OracleTableNode(connectionInfo,s);
		}
		
		return treeNode;
	}
	 

	 
}

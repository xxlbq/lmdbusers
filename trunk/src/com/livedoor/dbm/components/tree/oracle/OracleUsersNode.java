package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;
import java.sql.ResultSet;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;
/**
 * <p>
 * Description: OracleUsersNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleUsersNode extends DBMResultsetList{
	/**
	 * [機 能] OracleUsersNode 
	 * [解 説] OracleUsersNode 。
	 * 
	 * @param connInfo
	 */
	public OracleUsersNode(ConnectionInfo connectionInfo) {
		super(connectionInfo, "Users", null,Color.black,"TREE_CONN_CLOSEDFOLDER","TREE_CONN_CLOSEDFOLDER");
		// TODO 自动生成构造函数存根
	}
	/**
	 * [解 説]生成当前节点的子节点 及OracleUserNode
	 * 
	 * @param rs
	 * @return treeNode
	 */
	@Override
	public DBMTreeNode createNode(ResultSet rs) {
		OracleUserNode userNode =null;
		  String s;
	        try
	        {
	            s = (String)rs.getObject(1);
	            if(s != null)
	                s = s.trim();
	        }
	        catch(Exception exception)
	        {
	            s = null;
	        }
	        if(StringUtil.isNotEmpty(s)){
	        	userNode = new OracleUserNode(s);
	        }
	        return userNode;
	}
	/**
	 * [解 説]返回获取Oracle所有数据库的sql脚本
	 * 
	 * @return s SQL
	 */
	@Override
	public String getSql() {
		return "select USERNAME from SYS.DBA_USERS ORDER BY USERNAME";
	}

}

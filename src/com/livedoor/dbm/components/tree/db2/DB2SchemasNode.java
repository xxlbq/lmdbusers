package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;
import java.sql.ResultSet;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;
/**
 * <p>
 * Description: DB2ProcessInfosNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2SchemasNode extends DBMResultsetList{
	/**
	 * [機 能] DB2SchemasNode 
	 * [解 説] DB2SchemasNode 。
	 * 
	 * @param connInfo
	 */
	public DB2SchemasNode(ConnectionInfo connectionInfo) {
		super(connectionInfo, "Schema", null,Color.black,"TREE_CONN_SCHEMAS","TREE_CONN_SCHEMAS");
	}
	/**
	 *[解 説]生成当前节点的子节点
	 *@param rs
	 *@return treeNode
	 */
	@Override
	public DBMTreeNode createNode(ResultSet rs) {
		DB2SchemaNode schemaNode =null;
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
	        	schemaNode = new DB2SchemaNode(connectionInfo,s);
	        }
	        return schemaNode;
	}
	/**
	 *[解 説]返回获取DB2所有数据库的sql脚本
	 *@return s SQL
	 */
	@Override
	public String getSql() {
		return "SELECT NAME FROM sysibm.sysschemata ORDER BY NAME ";
	}

}

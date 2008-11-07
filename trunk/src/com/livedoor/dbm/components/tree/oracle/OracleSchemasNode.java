package com.livedoor.dbm.components.tree.oracle;

import java.awt.Color;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBMConnectionManager;
/**
 * <p>
 * Description: OracleSchemasNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OracleSchemasNode extends DBMTreeNodeList {
	ConnectionInfo connectionInfo;
	/**
	 * [機 能] OracleSchemasNode 
	 * [解 説] OracleSchemasNode 。
	 * 
	 * @param connInfo
	 */
	public OracleSchemasNode(ConnectionInfo connectionInfo) {
		super("Schema", null, Color.black, "TREE_CONN_SCHEMAS", "TREE_CONN_SCHEMAS");
		this.connectionInfo = connectionInfo;
	}
	/**
	 * [機 能] get Children List 
	 * [解 説] get Children List 。
	 * 
	 * @return childrenList
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getChildrenList() {
		List childrenList = new ArrayList();

		try {
			DatabaseMetaData databasemetadata = DBMConnectionManager.getConnection(connectionInfo, getDBSession()).getMetaData();
			ResultSet resultset = databasemetadata.getSchemas();
			do {
				if (!resultset.next())
					break;
				childrenList.add(new OracleSchemaNode(connectionInfo, resultset.getString(1)));
			} while (true);
			resultset.close();
		} catch (Exception e) {
			DBMMessageDialog.showErrorMessageDialog(e.getMessage());
		}
		return childrenList;
	}
	/**
	 * [解 説]生成当前节点的子节点
	 * 
	 * @param rs
	 * @return null
	 */
	public DBMTreeNode createNode(ResultSet rs) {
		return null;
	}
	/**
	 * [解 説]返回获取Oracle所有数据库的sql脚本
	 * 
	 * @return s SQL
	 */
	public String getSql() {
		return null;
	}

}

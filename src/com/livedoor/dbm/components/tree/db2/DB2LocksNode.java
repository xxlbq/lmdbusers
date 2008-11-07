package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;
/**
 * <p>
 * Description: DB2LocksNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2LocksNode extends DBMResultsetList {
	/**
	 * [機 能] DB2LockNode
	 * [解 説] DB2LockNode 。
	 * @param connectionInfo
	 */
	public DB2LocksNode(ConnectionInfo connectionInfo) {
		super(connectionInfo, "Locks / Object", null, Color.black, "TREE_CONN_LOCKOBJECTFOLDER", "TREE_CONN_LOCKOBJECTFOLDER");
	}

	/**
	 *[解 説]返回获取DB2所有数据库的sql脚本
	 *@return s SQL
	 */
	public String getSql() {
		String s = "SELECT DISTINCT TABLE_NAME, TABLE_SCHEMA, TABLESPACE_NAME FROM TABLE(SNAPSHOT_LOCK('" + connectionInfo.getDatabase()
				+ "', 0)) AS S";
		return s;
	}

	/**
	 *[解 説]生成当前节点的子节点及 DB2LockNode
	 *@param rs
	 *@return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {

		DBMTreeNode treeNode = null;
		String s;
		try {
			s = (String) rs.getObject(1);
		} catch (SQLException e) {
			e.printStackTrace();
			s = null;
		}

		if (StringUtil.isNotEmpty(s)) {
			treeNode = new DB2LockNode(s);
		}

		return treeNode;

	}

}

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
 * Description: DB2ProcessInfosNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2ProcessInfosNode extends DBMResultsetList {
	/**
	 * [機 能] DB2ProcessInfosNode 
	 * [解 説] DB2ProcessInfosNode 。
	 * 
	 * @param connInfo
	 */
	public DB2ProcessInfosNode(ConnectionInfo connectionInfo) {
		super(connectionInfo, "Connection Info", null, Color.black, "TREE_CONN_PROCESSINFO", "TREE_CONN_PROCESSINFO");
	}

	/**
	 *[解 説]返回获取Mysql所有数据库的sql脚本
	 *@return s SQL
	 */
	public String getSql() {
		return "SELECT S.AGENT_ID,S.INBOUND_COMM_ADDRESS,AI.AUTH_ID,AI.DB_NAME FROM TABLE(SNAPSHOT_APPL('" 
				+connectionInfo.getDatabase() + "', 0)) AS S,TABLE(SNAPSHOT_APPL_INFO('mydb2', 0)) AS AI WHERE S.AGENT_ID = AI.AGENT_ID ORDER BY S.AGENT_ID";

	}

	/**
	 *[解 説]生成当前节点的子节点 及DB2ProcessInfoNode
	 *@param rs
	 *@return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {

		DBMTreeNode treeNode = null;
		String s;
		try {
			s = rs.getObject(1).toString() + "-" + (String) rs.getObject(3) + "(" + (String) rs.getObject(2) + ")" + "-" + (String) rs.getObject(4);
		} catch (SQLException e) {
			e.printStackTrace();
			s = null;
		}

		if (StringUtil.isNotEmpty(s)) {
			treeNode = new DB2ProcessInfoNode(s);
		}

		return treeNode;

	}

}
package com.livedoor.dbm.components.tree.db2;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.util.StringUtil;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * <p>
 * Description: DB2ProceduresNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2ProceduresNode extends DBMResultsetList {
	/**
	 * [機 能] DB2ProceduresNode 
	 * [解 説] DB2ProceduresNode 。
	 * 
	 * @param connInfo
	 */
	public DB2ProceduresNode(ConnectionInfo connInfo) {
		super(connInfo, "Procedures", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");

	}
	/**
	 *[解 説]返回获取DB2所有数据库的sql脚本
	 *@return s SQL
	 */
	public String getSql() {
		return "select procschema, procname, SPECIFICNAME, PARM_COUNT from syscat.procedures where procschema = '"
				+ ((DBMTreeNode) getParent()).getId() + "' ORDER BY PROCNAME";
	}
	/**
	 *[解 説]生成当前节点的子节点
	 *@param rs
	 *@return treeNode
	 */
	public DBMTreeNode createNode(ResultSet rs) {
		DBMTreeNode treeNode = null;
		String s;
		try {
			s = (String) rs.getObject(2);
		} catch (SQLException e) {
			e.printStackTrace();
			s = null;
		}

		if (StringUtil.isNotEmpty(s)) {
			treeNode = new DB2ProcedureNode(s);
		}

		return treeNode;
	}

}

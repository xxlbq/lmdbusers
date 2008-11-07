package com.livedoor.dbm.components.tree.db2;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.StringUtil;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * <p>
 * Description: DB2FunctionsNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2FunctionsNode extends DBMResultsetList {

	/**
	 * [機 能] 获取SQL
	 * <p>
	 * [解 説] 获取SQL 。
	 * <p>
	 * [備 考] なし
	 */
	public String getSql() {
		String s = "select funcschema, funcname, specificname, PARM_COUNT from syscat.functions where funcschema = '"
				+ ((DBMTreeNode) getParent()).getId() + "' ";
		s = s + "AND (ORIGIN = 'E' OR ORIGIN = 'Q' OR ORIGIN = 'U') ";
		s = s + "ORDER BY FUNCNAME";
		return s;
	}
	/**
	 * [機 能] 创建DB2FunctionsNode
	 * <p>
	 * [解 説] 创建DB2FunctionsNode 。
	 * <p> 
	 * @param connInfo
	 */

	public DB2FunctionsNode(ConnectionInfo connInfo) {
		super(connInfo, "Functions", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");

	}
	/**
	 * [機 能] 构造节点
	 * <p>
	 * [解 説] 根据结果集ResultSet 构造节点 。
	 * <p>
	 * @param rs 结果集
	 * @return treeNode
	 * [備 考] なし
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
			treeNode = new DB2FunctionNode(s);
		}

		return treeNode;
	}

}

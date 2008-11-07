package com.livedoor.dbm.components.tree.db2;

import java.awt.Color;
import java.sql.ResultSet;

import com.livedoor.dbm.components.tree.DBMResultsetList;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.DBMSchemaTreeInit;
/**
 * <p>
 * Description: DB2ColumnsNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DB2ColumnsNode extends DBMResultsetList {
	/**
	 * [機 能] 获取SQL
	 * <p>
	 * [解 説] 获取SQL 。
	 * <p>
	 * [備 考] なし
	 */
	public String getSql() {
		String s = "SELECT COLNAME, TYPENAME, LENGTH, SCALE ";
		s = s + "from SYSCAT.COLUMNS ";
		s = s + "where TABNAME = '" + ((DBMTreeNode) getParent()).getId() + "' AND TABSCHEMA = '"
				+ ((DBMTreeNode) (getParent().getParent().getParent())).getId() + "' ";
		s = s + "order by COLNO";
		return s;
	}

	/**
	 * [機 能] 创建节点
	 * <p>
	 * [解 説] 创建节点 。
	 * <p>
	 * 
	 * @param rs
	 * @return treeNode [備 考] なし
	 */
	public DBMTreeNode createNode(ResultSet rs) {
		DBMTreeNode treeNode = null;

		String s = "";
		String s1 = "";
		String s2 = "";
		String s3 = "";
		String s4 = "";
		try {
			s = rs.getString(1);
			s1 = rs.getString(2);
			s2 = rs.getString(3);
			s4 = rs.getString(4);
		} catch (Exception exception) {
			s = null;
		}
		if (s == null)
			return null;
		if (s1.equalsIgnoreCase("DECIMAL")) {
			s3 = s2;
			s2 = "";
		}
		String s5 = DBMSchemaTreeInit.getDb2DataTypeDefinitionString(s1, s2, s3, s4);

		treeNode = new DB2ColumnNode(s + " (" + s1 + s5 + ")");
		return treeNode;
	}
	/**
	 * [機 能] 创建DB2ColumnsNode
	 * <p>
	 * [解 説] 创建DB2ColumnsNode 。
	 * 
	 * @param connInfo
	 */
	public DB2ColumnsNode(ConnectionInfo connInfo) {
		super(connInfo, "Columns", null, Color.black, "TREE_CONN_OPENFOLDER", "TREE_CONN_CLOSEDFOLDER");

	}

	

}

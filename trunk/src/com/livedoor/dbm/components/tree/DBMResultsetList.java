package com.livedoor.dbm.components.tree;

import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.connection.ConnectionInfo;

import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.exception.DBMException;

import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 * Description:DatabaseBaseNode
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */

public abstract class DBMResultsetList extends DBMTreeNodeList {

	protected ConnectionInfo connectionInfo;

	/**
	 * [機 能] 构造 动态子节点对象 [解 説] 动态子节点对象，需要根据指定的SQL语句 获取其子节点
	 * 
	 * @param connectionInfo
	 * @param name
	 * @param font
	 * @param color
	 * @param expandedKey
	 *            expandedIcon的索引值
	 * @param collapseKey
	 *            collapseIcon的索引值
	 */
	public DBMResultsetList(ConnectionInfo connectionInfo, String name, Font font, Color color, String expandedKey, String collapseKey) {
		super(name, font, color, expandedKey, collapseKey);
		this.connectionInfo = connectionInfo;
	}

	/**
	 * [機 能] 构造 动态子节点对象
	 * <p>
	 * [解 説] * 1.调用getSql()得到sql语句 2。调用工具类执行SQL语句，得到ResultSet 3。根据resultSet依次循环
	 * 调用createNodes(ResultSet)得到子节点集合； 4。关闭resultSet,返回子节点集合
	 * <p>
	 * [備 考] なし
	 */
	@SuppressWarnings("unchecked")
	public List getChildrenList() {
		List childrenList = new ArrayList();

		ResultSet resultset = null;
		Statement statement = null;
		try {
			resultset = DBMSqlExecuterFactory.createExecuter(connectionInfo, getDBSession()).executeQueryRes(getSql());
			statement = resultset.getStatement();

			if (resultset != null) {
				try {
					do {

						if (!resultset.next())
							break;
						DBMTreeNode treeNode = createNode(resultset);
						if (treeNode != null)
							childrenList.add(treeNode);
					} while (true);

				} catch (SQLException e) {
					/**
					 * [機 能] 自动生成 catch 块 [備 考] なし
					 */
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			DBMMessageDialog.showErrorMessageDialog(e1.getMessage());
		} finally {
			try {
				if(statement != null)
					statement.close();
				if(resultset != null)
					resultset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return childrenList;

	}

	/**
	 * [機 能] 抽象方法，得到获取子节点的sql语句 [備 考] なし
	 */
	public abstract String getSql();

	/**
	 * [機 能] 抽象方法，根据resultSet生成具体的子节点 [備 考] なし
	 */
	public abstract DBMTreeNode createNode(ResultSet rs);

	public ConnectionInfo getConnectionInfo() {
		return connectionInfo;
	}

}

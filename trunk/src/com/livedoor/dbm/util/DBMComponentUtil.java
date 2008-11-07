package com.livedoor.dbm.util;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Properties;
import javax.swing.JComboBox;
import com.livedoor.dbm.components.tree.DBMConnectionNode;
import com.livedoor.dbm.components.tree.DBMRootNode;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DatabaseBaseNode;
import com.livedoor.dbm.components.tree.SchemaBaseNode;
import com.livedoor.dbm.components.tree.TableBaseNode;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Description: Component工具类
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author lijian
 * @version 1.0
 */
public class DBMComponentUtil {

	public static DBMConnectionNode getDBMConnectionNode(DBMTreeNode node) {

		while (!DBMConnectionNode.class.isInstance(node)) {
			node = (DBMTreeNode) node.getParent();
		}
		return (DBMConnectionNode) node;

	}

	public static ConnectionInfo getConnectionInfo(DBMTreeNode node) {

		ConnectionInfo conn = null;

		if (!DBMRootNode.class.isInstance(node)) {
			while (!DBMConnectionNode.class.isInstance(node)) {
				node = (DBMTreeNode) node.getParent();
			}

			conn = ((DBMConnectionNode) node).getConnectionInfo();
		}

		return conn;

	}

	/**
	 * [功 能] 返回某个节点的所选数据库名称，如果该节点无数据库信息，则从当前的连接信息中返回缺省数据库名称
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param connInfo
	 *            连接信息
	 * @param node
	 *            结点
	 * 
	 * <p>
	 * 
	 * @return String
	 *         <p>
	 */
	public static String getDatabaseName(ConnectionInfo connInfo, DBMTreeNode node) {
		String dbName = "";
		if (connInfo == null)
			return dbName;
		do {
			if (node == null || node instanceof DBMRootNode) {
				// 如果为跟节点或NULL，取connInfo信息直接返回
				dbName = connInfo.getDatabase();
				break;
			}
			if (node instanceof DatabaseBaseNode) {
				// 如果为数据库节点，返回当前节点的数据库名称信息
				dbName = node.getId();
				break;
			}
			// 其它节点，取其上级节点重新遍历
			node = (DBMTreeNode) node.getParent();

		} while (true);

		return dbName;
	}

	/**
	 * [功 能] 返回某个节点的所选数据表名称，如果该节点无表信息，则返回空
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param connInfo
	 *            连接信息
	 * @param node
	 *            结点
	 * 
	 * <p>
	 * 
	 * @return String
	 *         <p>
	 */
	public static String getTableName(ConnectionInfo connInfo, DBMTreeNode node) {
		String tblName = "";
		if (connInfo == null)
			return tblName;
		do {
			if (node == null || node instanceof DBMRootNode) {
				// 如果为跟节点或NULL，直接返回
				tblName = "";
				break;
			}
			if (node instanceof TableBaseNode) {
				// 如果为数据表节点，返回当前节点的表名称称信息
				tblName = node.getId();
				break;
			}
			// 其它节点，取其上级节点重新遍历
			node = (DBMTreeNode) node.getParent();

		} while (true);

		return tblName;
	}

	/**
	 * [功 能] 返回某个节点的所选数据模式(schema)名称，如果该节点无schema信息，则从当前的连接信息中返回当前用户名称
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param connInfo
	 *            连接信息
	 * @param node
	 *            结点
	 * 
	 * <p>
	 * 
	 * @return String
	 *         <p>
	 */
	public static String getSchemaName(ConnectionInfo connInfo, DBMTreeNode node) {
		String schemaName = "";
		if (connInfo == null)
			return schemaName;
		do {
			if (node == null || node instanceof DBMRootNode) {
				// 如果为跟节点或NULL，取connInfo用户信息直接返回
				schemaName = connInfo.getUserName();
				break;
			}
			if (node instanceof SchemaBaseNode) {
				// 如果为数据模式(schema)节点，返回当前节点的schema名称信息
				schemaName = node.getId();
				break;
			}
			// 其它节点，取其上级节点重新遍历
			node = (DBMTreeNode) node.getParent();

		} while (true);
        return schemaName;
	//	return schemaName.toUpperCase();
	}

	/**
	 * [功 能] 右键触发判断，须判断多平台
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param mouseevent
	 *            鼠标事件
	 * 
	 * 
	 * <p>
	 * 
	 * @return boolean
	 *         <p>
	 */
	public static boolean isPopupTrigger(MouseEvent mouseevent) {
		if (mouseevent.isPopupTrigger())
			return true;
		if (mouseevent.isMetaDown())
			return true;
		return isOSX() && mouseevent.isControlDown();
	}

	/**
	 * [功 能] 其它系统判断
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 * 
	 * 
	 * <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static boolean isOSX() {
		Properties properties = System.getProperties();
		String s = properties.getProperty("os.name", "Windows");
		s = s.toUpperCase();
		return s.indexOf("WINDOWS") == -1;
	}

	/**
	 * [功 能] JComboBox中添加数据
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param comboBox
	 *            JComboBox
	 * @param list
	 *            数据
	 * 
	 * <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static void addItems(JComboBox comboBox, List list) {

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				comboBox.addItem(list.get(i));
			}
		}
	}
}

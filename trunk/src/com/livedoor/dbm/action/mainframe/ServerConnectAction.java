/*
 * 创建时间 2006-09-29
 */
package com.livedoor.dbm.action.mainframe;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.tree.TreePath;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.tree.DBMConnectionNode;
import com.livedoor.dbm.components.tree.DBMTree;
import com.livedoor.dbm.components.tree.DBMTreeModel;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBMConnectionManager;
import com.livedoor.dbm.exception.DBMException;

public class ServerConnectAction extends DBMBaseAction {

	public ServerConnectAction() {
		super("SERVER_CONNECT");
	}

	/**
	 * <p>
	 * Title: Action类
	 * </p>
	 * <p>
	 * Description: 数据库连接.
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2006
	 * </p>
	 * <p>
	 * Company: 英极软件开发（大连）有限公司
	 * </p>
	 * 
	 * @author zhangys
	 * @version 1.0
	 */
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();
		DBMTreeNode node = (DBMTreeNode) treeView.getDBMTree().getSelectionPath().getLastPathComponent();
		if (node instanceof DBMConnectionNode) {
			frame.setCursor(new Cursor(3));
			ConnectionInfo connectionInfo = ((DBMConnectionNode) node).getConnectionInfo();
			// 连接数据库
			try {
				DBMConnectionManager.getConnection(connectionInfo, node.getDBSession());
			} catch (DBMException e) {
				DBMMessageDialog.showErrorMessageDialog(e.getMessage());
				return;
			}
			// 打开子节点
			((DBMConnectionNode) node).updateList();
			DBMTree dbmTree = treeView.getDBMTree();
			DBMTreeModel model = ((DBMConnectionNode) node).getTreeModel();
			dbmTree.expandPath(new TreePath(model.getPathToRoot(node)));
			frame.setCursor(new Cursor(0));
		} else {
			DBMMessageDialog.showErrorMessageDialog("REGISTER_SERVER_CONNECT");
			return;
		}
	}

}

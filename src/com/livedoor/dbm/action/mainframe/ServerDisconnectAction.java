/*
 * 创建时间 2006-09-29
 */
package com.livedoor.dbm.action.mainframe;

import java.awt.event.ActionEvent;
import javax.swing.tree.TreeNode;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.tree.DBMConnectionNode;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.db.DBMConnectionManager;
import com.livedoor.dbm.util.DBMComponentUtil;

/**
 * <p>
 * Title: Action类
 * </p>
 * <p>
 * Description: 断开数据库连接.
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
public class ServerDisconnectAction extends DBMBaseAction {

	public ServerDisconnectAction() {
		super("SERVER_DISCONNECT");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();
		TreeNode node = (TreeNode) treeView.getDBMTree().getSelectionPath().getLastPathComponent();
		if (node instanceof DBMConnectionNode) {
			//((DBMConnectionNode) node).disconnection();
			//删除查询分析器中对应的tabbed组件
			frame.removeQueryPanelByConnAliasName(((DBMConnectionNode) node).getConnectionInfo().getAliasName());
			// 关闭所有该类型数据库的连接
			((DBMConnectionNode) node).removeAllChildren();
			 DBMConnectionManager.closeConnection(((DBMConnectionNode) node).getConnectionInfo());
		} else {
			DBMMessageDialog.showErrorMessageDialog("REGISTER_SERVER_DISCONNECT");
			return;
		}
	}
}

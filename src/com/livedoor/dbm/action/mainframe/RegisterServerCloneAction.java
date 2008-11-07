/*
 * 创建时间 2006-09-29
 */
package com.livedoor.dbm.action.mainframe;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.mainframe.RegisterServerDialog;
import com.livedoor.dbm.components.tree.DBMConnectionNode;
import com.livedoor.dbm.components.tree.DBMRootNode;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.DBMComponentUtil;
import com.livedoor.dbm.util.DBMConnectionUtil;
/**
 * <p>
 * Title: Action类
 * </p>
 * <p>
 * Description: 服务克隆.
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
public class RegisterServerCloneAction extends DBMBaseAction {
	public RegisterServerCloneAction() {
		super("REGISTER_SERVER_CLONE");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {

		JPanel treeView = frame.getTreePanel();
		if (treeView instanceof DBMTreePaneView) {
			// 得到当前节点
			DBMTreeNode node = (DBMTreeNode) ((DBMTreePaneView) treeView).getDBMTree().getLastSelectedPathComponent();
			// 得到它的CONNECTION节点
			DBMConnectionNode connectionNode = DBMComponentUtil.getDBMConnectionNode(node);
			ConnectionInfo conn = DBMConnectionUtil.readConnectionInfo(connectionNode.getId());

			RegisterServerDialog registerServerDialog = new RegisterServerDialog(frame, conn, node.getDBSession(), false);

			registerServerDialog.show();
			if (registerServerDialog.isOk()) {
				// 写信息到文件中
				DBMConnectionUtil.writeConnectionFile(registerServerDialog.getConnInfo());
				DBMRootNode root = (DBMRootNode) ((DBMTreePaneView) treeView).getDBMTree().getModel().getRoot();
				// 构造新的子节点，然后更新数模型
				DBMConnectionNode newConnectionNode = new DBMConnectionNode(registerServerDialog.getConnInfo().getConnectionName());
				root.addElement(newConnectionNode);

			} else {
				;
			}
			registerServerDialog.dispose();

		}
	}
}

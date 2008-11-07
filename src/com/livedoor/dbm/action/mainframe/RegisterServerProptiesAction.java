/*
 * 创建时间 2006-09-29
 */
package com.livedoor.dbm.action.mainframe;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.common.DBMMessageDialog;
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
 * Description: 服务属性.
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
public class RegisterServerProptiesAction extends DBMBaseAction {

	public RegisterServerProptiesAction() {
		super("REGISTER_SERVER_PROPERTIES");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {

		JPanel treeView = frame.getTreePanel();
		if (treeView instanceof DBMTreePaneView) {

			DBMTreeNode node = (DBMTreeNode) ((DBMTreePaneView) treeView).getDBMTree().getLastSelectedPathComponent();

			if (node instanceof DBMRootNode) {
				DBMMessageDialog.showErrorMessageDialog("REGISTER_SERVER_NOT_EXIST");
				return;
			}

			DBMConnectionNode connectionNode = DBMComponentUtil.getDBMConnectionNode(node);
			ConnectionInfo conn = DBMConnectionUtil.readConnectionInfo(connectionNode.getId());

			RegisterServerDialog registerServerDialog = new RegisterServerDialog(frame, conn, node.getDBSession(), true);

			registerServerDialog.show();
			if (registerServerDialog.isOk()) {
				DBMConnectionUtil.writeConnectionFile(registerServerDialog.getConnInfo());
				connectionNode.cloneConnectionInfo(registerServerDialog.getConnInfo());
			} else {
				;
			}
			registerServerDialog.dispose();

		}
	}

}

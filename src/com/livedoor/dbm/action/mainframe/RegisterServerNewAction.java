/*
 * 创建时间 2006-09-29
 */
package com.livedoor.dbm.action.mainframe;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.RegisterServerDialog;
import com.livedoor.dbm.util.DBMConnectionUtil;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.tree.DBMConnectionNode;
import com.livedoor.dbm.components.tree.DBMRootNode;
import com.livedoor.dbm.components.tree.DBMTreePaneView;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

/**
 * <p>
 * Title: Action类
 * </p>
 * <p>
 * Description: 注册新的服务.
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
public class RegisterServerNewAction extends DBMBaseAction {

	private RegisterServerDialog registerServerDialog;

	private DBMConnectionUtil dBMConnectionUtil;

	public RegisterServerNewAction() {
		super("REGISTER_SERVER_NEW");
	}

	/**
	 * 1。调用RegisterServerDialog显示注册对话筐 2。收集注册信息，然后保存到文件中 3。更新树模型
	 */
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		JPanel treeView = frame.getTreePanel();
		DBMRootNode root = (DBMRootNode) ((DBMTreePaneView) treeView).getDBMTree().getModel().getRoot();
		RegisterServerDialog registerServerDialog = new RegisterServerDialog(frame, root.getDBSession());
		registerServerDialog.show();

		if (registerServerDialog.isOk()) {

			DBMConnectionUtil.writeConnectionFile(registerServerDialog.getConnInfo());
			DBMConnectionNode connectionNode = new DBMConnectionNode(registerServerDialog.getConnInfo().getConnectionName());
			root.addElement(connectionNode);

		} else {
			;
		}
		registerServerDialog.dispose();
	}

}

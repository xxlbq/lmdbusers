/*
 * 创建时间 2006-09-29
 */
package com.livedoor.dbm.action.mainframe;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.ChooseServerDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.mainframe.er.ErGeneratorDialog;
import com.livedoor.dbm.components.tree.DBMRootNode;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.util.DBMComponentUtil;
/**
 * <p>
 * Title: Action类
 * </p>
 * <p>
 * Description: 生成ER图.
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
public class ToolsErDiagramGeneratorAction extends DBMBaseAction {

	public ToolsErDiagramGeneratorAction() {
		super("TOOLS_ERDIAGRAMGENERATOR");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {

		JPanel treeView = frame.getTreePanel();

		DBMTreeNode node = (DBMTreeNode) ((DBMTreePaneView) treeView).getDBMTree().getLastSelectedPathComponent();
		ConnectionInfo connInfo = null;

		if (node instanceof DBMRootNode) {
			// POPUP DIALOG
			ChooseServerDialog dialog = new ChooseServerDialog(frame);
			dialog.setVisible(true);

			if (dialog.isOk()) {
				connInfo = dialog.getConnectionInfo();
			} else
				return;

		} else {
			connInfo = DBMComponentUtil.getConnectionInfo(node);
		}

		DBSession dbSession = node.getDBSession();

		ErGeneratorDialog dialog = new ErGeneratorDialog(frame, connInfo, dbSession);
		dialog.show();

	}

}

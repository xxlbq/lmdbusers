/*
 * 创建时间 2006-09-29
 */
package com.livedoor.dbm.action.mainframe;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.ChooseServerDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.mainframe.importexport.ExportFrame;

import com.livedoor.dbm.components.tree.DBMConnectionNode;
import com.livedoor.dbm.components.tree.DBMRootNode;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.DBMComponentUtil;
/**
 * <p>
 * Title: Action类
 * </p>
 * <p>
 * Description: 导出.
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
public class ToolsExportAction extends DBMBaseAction {

	public ToolsExportAction() {
		super("TOOLS_EXPORT");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();

		TreeNode node = (TreeNode) treeView.getDBMTree().getSelectionPath().getLastPathComponent();
		DBMTreeNode currentNode = (DBMTreeNode) node;
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
			while (!DBMConnectionNode.class.isInstance(node)) {
				node = node.getParent();
			}
			DBMConnectionNode connNode = (DBMConnectionNode) node;
			connInfo = connNode.getConnectionInfo();
		}

		String databaseName = DBMComponentUtil.getDatabaseName(connInfo, currentNode);
		String schemaName = DBMComponentUtil.getSchemaName(connInfo, currentNode);
		String tableName = DBMComponentUtil.getTableName(connInfo, currentNode);
		ExportFrame exframe = new ExportFrame(connInfo, currentNode.getDBSession(), databaseName, schemaName, tableName);
		exframe.setVisible(true);

	}

}

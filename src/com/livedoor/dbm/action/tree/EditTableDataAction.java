package com.livedoor.dbm.action.tree;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.mainframe.tableedit.TableEditJFrame;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.DBMComponentUtil;

public class EditTableDataAction extends DBMBaseAction {

	public EditTableDataAction() {
		super("EDIT_TABLE_DATA");
		// TODO 自动生成构造函数存根
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();
		DBMTreeNode node = (DBMTreeNode) treeView.getDBMTree()
				.getSelectionPath().getLastPathComponent();
		ConnectionInfo connInfo = DBMComponentUtil.getConnectionInfo(node);
		node.getDBSession();
		TableEditJFrame editTableDataFrame;
		try {
			editTableDataFrame = new TableEditJFrame(connInfo,DBMComponentUtil.getSchemaName(connInfo,node),DBMComponentUtil.getDatabaseName(connInfo,node),DBMComponentUtil.getTableName(connInfo,node),node.getDBSession());
			editTableDataFrame.setVisible(true);
		} catch (Exception e) {
			DBMMessageDialog.showErrorMessageDialog(e.getMessage());
			e.printStackTrace();
		}
	}

}

package com.livedoor.dbm.action.tree;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.mainframe.createdt.OperationTableFrame;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.DBMComponentUtil;

public class SchemaTableAlterAction extends DBMBaseAction {

	public SchemaTableAlterAction() {
		super("SCHEMA_TABLE_ALTER");
		// TODO 自动生成构造函数存根
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		// DBMMessageDialog.showConfirmDialog("SCHEMA_TABLE_ALTER");
		DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();
		DBMTreeNode node = (DBMTreeNode) treeView.getDBMTree().getSelectionPath().getLastPathComponent();
		ConnectionInfo connInfo = DBMComponentUtil.getConnectionInfo(node);
		OperationTableFrame createTableFrame;
		try {
			createTableFrame = new OperationTableFrame(frame, connInfo, DBMComponentUtil.getSchemaName(connInfo, node), DBMComponentUtil
					.getDatabaseName(connInfo, node), node.getDBSession(), OperationTableFrame.OPENATION_ALTER, node.toString());
			createTableFrame.setVisible(true);
			if (createTableFrame.isOk())
				((DBMTreeNodeList) node).updateList();//			刷新树模型
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

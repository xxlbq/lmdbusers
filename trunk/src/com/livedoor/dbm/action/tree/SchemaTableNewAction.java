package com.livedoor.dbm.action.tree;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.mainframe.createdt.OperationTableFrame;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.components.tree.TableBaseNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.DBMComponentUtil;

public class SchemaTableNewAction  extends DBMBaseAction {

	public SchemaTableNewAction() {
		super("SCHEMA_TABLE_NEW");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
	//	DBMMessageDialog.showConfirmDialog("SCHEMA_TABLE_NEW");
		DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();
		DBMTreeNode node = (DBMTreeNode) treeView.getDBMTree()
				.getSelectionPath().getLastPathComponent();
		ConnectionInfo connInfo = DBMComponentUtil.getConnectionInfo(node);
		OperationTableFrame createTableFrame;
		try {
			createTableFrame = new OperationTableFrame(frame,connInfo,DBMComponentUtil.getSchemaName(connInfo,node),DBMComponentUtil.getDatabaseName(connInfo,node),node.getDBSession(),OperationTableFrame.OPERATION_CREATE,"");
			createTableFrame.setVisible(true);
			DBMTreeNodeList tmpNode = (DBMTreeNodeList) node;
			if (createTableFrame.isOk()){
				if(node instanceof TableBaseNode ){
					tmpNode = (DBMTreeNodeList)node.getParent();
				}
				tmpNode.updateList();//刷新树模型
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

}


package com.livedoor.dbm.action.tree;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.mainframe.createdt.CreateDatabaseDialog;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.components.tree.DatabaseBaseNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.DBMComponentUtil;

public class SchemaDatabaseNewAction extends DBMBaseAction {

	public SchemaDatabaseNewAction() {
		super("SCHEMA_DATABASE_NEW");
		// TODO 自动生成构造函数存根
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
//		DBMMessageDialog.showConfirmDialog("SCHEMA_DATABASE_NEW");
		DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();

		DBMTreeNode node = (DBMTreeNode) treeView.getDBMTree().getSelectionPath().getLastPathComponent();
        ConnectionInfo connInfo = DBMComponentUtil.getConnectionInfo(node);
        String databaseName = DBMComponentUtil.getDatabaseName(connInfo, node);
        String schemaName = DBMComponentUtil.getSchemaName(connInfo, node);
        String tableName = DBMComponentUtil.getTableName(connInfo, node);
        CreateDatabaseDialog createDatabaseDialog = new CreateDatabaseDialog(frame, true, connInfo, node.getDBSession(), databaseName, schemaName, tableName);
        createDatabaseDialog.show(true);
        if (createDatabaseDialog.isOk())  {
        	DBMTreeNodeList tmpNode = (DBMTreeNodeList) node;
			if(node instanceof DatabaseBaseNode ){
				tmpNode = (DBMTreeNodeList)node.getParent();
			}
        	((DBMTreeNodeList)tmpNode).updateList();
        }
	}
}

package com.livedoor.dbm.action.tree;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.util.DBMComponentUtil;

public class SchemaDatabaseDropAction extends DBMBaseAction {

	public SchemaDatabaseDropAction() {
		super("SCHEMA_DATABASE_DROP");
		// TODO 自动生成构造函数存根
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		if (DBMMessageDialog.showConfirmDialog("SCHEMA_DATABASE_DROP") == JOptionPane.YES_OPTION) {
			// 得到当前的节点对象
			DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();
			DBMTreeNode node = (DBMTreeNode) treeView.getDBMTree().getSelectionPath().getLastPathComponent();
			ConnectionInfo connInfo = DBMComponentUtil.getConnectionInfo(node);
			String dbName = DBMComponentUtil.getDatabaseName(connInfo, node);
			String scriptSql = "DROP DATABASE " + dbName;
			try {
				DBMSqlExecuterFactory.createExecuter(connInfo, node.getDBSession()).executeUpdate(scriptSql);

				// 刷新树模型并且选中父结点
				DBMTreeNode parentNode = (DBMTreeNodeList) node.getParent();
				((DBMTreeNodeList) node.getParent()).updateList();
				treeView.getDBMTree().setSelectionPath(new TreePath(parentNode.getPath()));

			} catch (DBMException dbme) {
				DBMMessageDialog.showErrorMessageDialog(dbme.getMessage());
			}
		}
	}

}

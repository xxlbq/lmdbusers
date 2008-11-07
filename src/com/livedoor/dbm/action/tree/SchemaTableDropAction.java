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
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.scripts.SqlBuilderFactory;
import com.livedoor.dbm.util.DBMComponentUtil;

public class SchemaTableDropAction extends DBMBaseAction {

	public SchemaTableDropAction() {
		super("SCHEMA_TABLE_DROP");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		if(DBMMessageDialog.showConfirmDialog("SCHEMA_TABLE_DROP")==JOptionPane.YES_OPTION){
			//得到当前的节点对象
			DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();
			DBMTreeNode currentNode = (DBMTreeNode) treeView.getDBMTree().getSelectionPath().getLastPathComponent();
			ConnectionInfo connInfo = DBMComponentUtil.getConnectionInfo(currentNode);
			DBMDataMetaInfo dateMetaInfo=new DBMDataMetaInfo();
			dateMetaInfo.setSchemaName(DBMComponentUtil.getSchemaName(connInfo, currentNode));
			dateMetaInfo.setTableName(DBMComponentUtil.getTableName(connInfo, currentNode));
			dateMetaInfo.setDatabaseName(DBMComponentUtil.getDatabaseName(connInfo, currentNode));
			//得到脚本语句
			String scriptSql = SqlBuilderFactory.createSqlBuilder(connInfo, currentNode.getDBSession(),dateMetaInfo).dropTable();
			try{
				DBMSqlExecuter sqlExecuter = DBMSqlExecuterFactory.createExecuter(connInfo, currentNode.getDBSession());
				//改变数据库连接
				sqlExecuter.changeDatabase(DBMComponentUtil.getDatabaseName(connInfo, currentNode));
				sqlExecuter.executeUpdate(scriptSql);
				//刷新树模型
				DBMTreeNode parentNode = (DBMTreeNodeList) currentNode.getParent();
				((DBMTreeNodeList) currentNode.getParent()).updateList();
				treeView.getDBMTree().setSelectionPath(new TreePath(parentNode.getPath()));
				
			}catch(DBMException dbme){
				DBMMessageDialog.showErrorMessageDialog(dbme.getMessage());
			}
		}

	}


}

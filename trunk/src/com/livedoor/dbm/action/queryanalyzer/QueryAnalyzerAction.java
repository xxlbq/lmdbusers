/**
 * $Id: QueryAnalyzerAction.java,v 1.2 2006/12/04 02:55:13 lijc Exp $
 * 查询分析器工具栏按钮对应
 */
package com.livedoor.dbm.action.queryanalyzer;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.ChooseServerDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.queryanalyzer.QueryAnalyzerPanel;
import com.livedoor.dbm.components.tree.DBMConnectionNode;
import com.livedoor.dbm.components.tree.DBMRootNode;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.util.DBMComponentUtil;
import com.livedoor.dbm.util.QueryPanelUtil;

/**
 * <p>Title: 打开查询分析器窗口</p> 
 * <p>Description: 打开查询分析器窗口</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 *  
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class QueryAnalyzerAction extends DBMBaseAction {

	public QueryAnalyzerAction() {
		super("QUERY_ANALYZER");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();
		TreeNode node = (TreeNode) treeView.getDBMTree().getSelectionPath().getLastPathComponent();
		DBMTreeNode currentNode = (DBMTreeNode)node;
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
		QueryAnalyzerPanel panel = QueryPanelUtil.createNewQueryPanel(frame, connInfo, actionHandler);
		//设定当前的数据库名称
		panel.setCurrentDatabase(DBMComponentUtil.getDatabaseName(connInfo,currentNode));
		//设定当前的模式名称
		panel.setCurrentSchema(DBMComponentUtil.getSchemaName(connInfo,currentNode));
		frame.addQueryPanel(panel);
	}

}

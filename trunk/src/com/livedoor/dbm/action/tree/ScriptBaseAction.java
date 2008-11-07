package com.livedoor.dbm.action.tree;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.queryanalyzer.QueryAnalyzerPanel;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.util.DBMComponentUtil;
import com.livedoor.dbm.util.QueryPanelUtil;

/**
 * <p>Description:  所有Script操作的基类Action  </p>
 * Copyright: Copyright (c) 2006 
 * Company: 英極軟件開發（大連）有限公司
 * @author <a href="mailto:lijian@livedoor.cn">Jian Li </a>
 * @version 1.0
 */
public abstract class ScriptBaseAction extends DBMBaseAction {
	protected String schemaName;
	public ScriptBaseAction(String actionName) {
		super(actionName);
		
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {

		//得到当前的节点对象
		DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();
		DBMTreeNode node = (DBMTreeNode) treeView.getDBMTree().getSelectionPath().getLastPathComponent();
		ConnectionInfo connInfo = DBMComponentUtil.getConnectionInfo(node);
		//获取当前使用中的QueryAnaylizerPanel对象
		QueryAnalyzerPanel queryPanel = QueryPanelUtil.getQueryAnaylizerPanel(frame, connInfo, actionHandler);
		//改变数据库
		queryPanel.setCurrentDatabase(DBMComponentUtil.getDatabaseName(connInfo,node));
		schemaName = DBMComponentUtil.getSchemaName(connInfo,node);
		queryPanel.setCurrentSchema(schemaName);
		//得到创建表的脚本语句
		String script = generateSqlScript(connInfo,node,queryPanel.getDBSession());
		//更新查询分析器中文本内容
		queryPanel.insertSQLStatement(script);
	}
	/**
	 * 抽象方法，定义返回当前脚本的sql语句
	 * @param connInfo
	 * @param currentNode  当前的节点对象
	 * @param dbSession    当前连接会话信息
	 * @return
	 */
	public abstract String generateSqlScript(ConnectionInfo connInfo,DBMTreeNode currentNode, DBSession dbSession);

}

/*
 * 创建时间 2006-09-29
 */
package com.livedoor.dbm.action.mainframe;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.TreePath;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.tree.DBMConnectionNode;
import com.livedoor.dbm.components.tree.DBMRootNode;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.db.DBMConnectionManager;
import com.livedoor.dbm.util.DBMComponentUtil;
import com.livedoor.dbm.util.DBMConnectionUtil;
import com.livedoor.dbm.util.DBMFileUtil;
/**
 * <p>
 * Title: Action类
 * </p>
 * <p>
 * Description: 删除服务.
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
public class RegisterServerDeleteAction extends DBMBaseAction {

	public RegisterServerDeleteAction() {
		super("REGISTER_SERVER_DELETE");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {

		DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();
		DBMTreeNode node = (DBMTreeNode) ((DBMTreePaneView) treeView).getDBMTree().getLastSelectedPathComponent();
		DBMTreeNode parentNode = (DBMTreeNodeList) node.getParent();
		if (node instanceof DBMRootNode) {
			DBMMessageDialog.showErrorMessageDialog("REGISTER_SERVER_DELETED");
			return;
		} else {
			if (DBMMessageDialog.showConfirmDialog("REGISTER_SERVER_DELETE") == JOptionPane.YES_OPTION) {
				if (treeView instanceof DBMTreePaneView) {

					DBMRootNode root = (DBMRootNode) ((DBMTreePaneView) treeView).getDBMTree().getModel().getRoot();

					DBMConnectionNode connectionNode = DBMComponentUtil.getDBMConnectionNode(node);
					root.removeElement(connectionNode);
					// 删除文件
					DBMFileUtil.deleteFile(DBMConnectionUtil.getConnDir() + File.separator + connectionNode.getId() + ".conn");
					// 删除查询分析器中对应的tabbed组件
					frame.removeQueryPanelByConnAliasName(connectionNode.getConnectionInfo().getAliasName());
					// 关闭所有该类型数据库的连接
					DBMConnectionManager.closeConnection(connectionNode.getConnectionInfo());

					// 选中父结点
					treeView.getDBMTree().setSelectionPath(new TreePath(parentNode.getPath()));
				}

			}
		}

	}

}

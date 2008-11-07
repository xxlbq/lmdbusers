package com.livedoor.dbm.action.tree;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreeNodeList;
import com.livedoor.dbm.components.tree.DBMTreePaneView;

public class TreeRefresh extends DBMBaseAction {

	public TreeRefresh() {
		super("TREE_REFRESH");
		// TODO 自动生成构造函数存根
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		DBMTreePaneView treeView = (DBMTreePaneView) frame.getTreePanel();
		DBMTreeNode node = (DBMTreeNode) treeView.getDBMTree().getSelectionPath().getLastPathComponent();
		if(node instanceof DBMTreeNodeList){
			((DBMTreeNodeList)node).updateList();
		}

	}

}

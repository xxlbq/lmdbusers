/*
 * 
 */
package com.livedoor.dbm.components.tree;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import com.livedoor.dbm.components.common.DBMTreeCellRenderer;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.util.DBMComponentUtil;

/**
 * <p>
 * Description: DBMTree
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DBMTree extends JTree {
	private DBMTree currentTree;
	private DBMFrame source;
	private static final DBMTreeCellRenderer _treeCellRenderer = new DBMTreeCellRenderer();
	/**
	 * [機 能] 构造 DBMTree
	 * 
	 * [解 説] 构造 DBMTree，需要根据指定的DBMTreeModel 构造 DBMTree 。
	 * 
	 * @param treeModel
	 */
	public DBMTree(DBMTreeModel treeModel,DBMFrame frame) {
		source = frame;
		currentTree = this;
		setModel(treeModel);
		setSelectionPath(new TreePath(treeModel.getRoot()));
		setCellRenderer(_treeCellRenderer);
		setShowsRootHandles(true);
		TreeWillExpandListener treeWillExpandListener = new TreeWillExpandListener() {
			public void treeWillExpand(TreeExpansionEvent treeexpansionevent) {
				TreePath treePath = treeexpansionevent.getPath();
				DBMTreeNode node = (DBMTreeNode) treePath.getLastPathComponent();
				if (node != null) {
					if (node instanceof DBMTreeNodeList) {
						 if(source != null)
		                        source.setCursor(new Cursor(3));
						((DBMTreeNodeList) node).updateList();
						if(source != null)
	                        source.setCursor(new Cursor(0));
					}
				}

			}
			public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
			}
		};
		addTreeWillExpandListener(treeWillExpandListener);

		MouseAdapter mouseAdapter = new MouseAdapter() {

			public void mouseClicked(MouseEvent mouseevent) {

				@SuppressWarnings("unused")
				int i = currentTree.getRowForLocation(mouseevent.getX(), mouseevent.getY());
				TreePath treePath = currentTree.getPathForLocation(mouseevent.getX(), mouseevent.getY());
				if (treePath == null)
					return;

				DBMTreeNode node = (DBMTreeNode) treePath.getLastPathComponent();

				if (mouseevent.getClickCount() == 2) {

					if (DBMTreeNodeList.class.isInstance(node)){
						if(source != null)
	                        source.setCursor(new Cursor(3));
						((DBMTreeNodeList) node).updateList();
						if(source != null)
	                        source.setCursor(new Cursor(0));
					}
				}
			}

			public void mouseReleased(MouseEvent mouseevent) {

				if (DBMComponentUtil.isPopupTrigger(mouseevent)) {

					int i = mouseevent.getX();
					int j = mouseevent.getY();
					TreePath treepath = currentTree.getPathForLocation(i, j);
					DBMTreeNode treeNode = null;
					if (treepath != null) {
						treeNode = (DBMTreeNode) treepath.getLastPathComponent();
						currentTree.setSelectionPath(treepath);

					} else {
						treeNode = (DBMTreeNode) currentTree.getModel().getRoot();
					}

					JPopupMenu popupmenu = treeNode.getPopupMenu();
					if (popupmenu != null)
						popupmenu.show(currentTree, i, j);

				}
			}

		};
		addMouseListener(mouseAdapter);

	}

	/**
	 * [機 能] 得到当前选中的节点对象
	 * <p>
	 * [解 説] 得到当前选中的节点对象 。
	 * <p>
	 * 
	 * @return (DBMTreeNode) treePath.getLastPathComponent() [備 考] なし
	 */
	public DBMTreeNode getSelectedNode() {
		TreePath treePath = getSelectionPath();
		if (treePath == null) {
			return null;
		}
		return (DBMTreeNode) treePath.getLastPathComponent();
	}

}

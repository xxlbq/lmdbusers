/*
 * 
 */
package com.livedoor.dbm.components.tree;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description: 定义抽象类DBMTreeNodeList
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
public abstract class DBMTreeNodeList extends DBMTreeNode {
	private List dbmChildren = new ArrayList();
	/**
	 * [機 能] DBMTreeNodeList
	 * [解 説] DBMTreeNodeList
	 * @param s
	 * @param font
	 * @param color
	 * @param expandedKey
	 * @param collapseKey
	 */

	public DBMTreeNodeList(String s, Font font, Color color, String expandedKey, String collapseKey) {
		super(s, font, color, expandedKey, collapseKey);
	}
	/**
	 * [機 能] 在树模型中增加一个节点
	 * <p>
	 * [解 説] 在树模型中增加一个节点，需要DBMTreeNode 增加一个节点 。
	 * <p>
	 * [備 考] なし 
	 * @param dbmTreeNode
	 */
	@SuppressWarnings("unchecked")
	public void addElement(DBMTreeNode dbmTreeNode) {
		dbmChildren.add(dbmTreeNode);
		DBMTreeModel treeModel = getTreeModel();
		dbmTreeNode.setTreeModel(treeModel);
		treeModel.insertNodeInto(dbmTreeNode, this, dbmChildren.size() - 1);
	}
	/**
	 * [機 能] 从树模型中删除一个节点
	 * <p>
	 * [解 説] 从树模型中删除一个节点，需要DBMTreeNode 从树模型中删除一个节点 。
	 * <p>
	 * [備 考] なし
	 * @param dbmTreeNode
	 */

	public void removeElement(DBMTreeNode dbmTreeNode) {
		int i = dbmChildren.indexOf(dbmTreeNode);
		if (i >= 0) {
			DBMTreeNode removeNode = (DBMTreeNode) dbmChildren.remove(i);
			getTreeModel().removeNodeFromParent(removeNode);
		}
	}
	/**
	 * [機 能] 批量更新树节点
	 * <p>
	 * [解 説] 批量更新树节点
	 * <p>
	 * [備 考] なし
	 */
	public void updateList() {
		List nodeList = getChildrenList();
		if (nodeList == null)
			return;
		updateList(nodeList);
	}
	/**
	 * <p>
	 * [解 説] 先remove不在nodeLists中的节点
	 * <p>
	 * [備 考] なし
	 * @param nodeList
	 */
	protected void updateList(List nodeList) {

		int size = dbmChildren.size();
		for (int i = size; i > 0; i--) {
			DBMTreeNode node = (DBMTreeNode) dbmChildren.get(i - 1);
			if (!nodeList.contains(node)) {
				removeElement(node);
			}
		}
		/**
		 * <p>
		 * [解 説] 然后把新增加的树节点插入到树模型中
		 * <p>
		 * [備 考] なし
		 */
		size = nodeList.size();
		for (int k = 0; k < size; k++) {
			DBMTreeNode node = (DBMTreeNode) nodeList.get(k);
			if (!dbmChildren.contains(node)) {
				addElement(node);
			}
		}
	}
	public Object elementAt(int i) {
		return dbmChildren.get(i);
	}

	public int size() {
		return dbmChildren.size();
	}

	public int indexOf(Object obj) {
		return dbmChildren.indexOf(obj);
	}
	public boolean isLeaf() {
		return false;
	}
	/**
	 * <p>
	 * [解 説] 生成当前节点下的子节点集合
	 * <p>
	 * [備 考] なし
	 */
	public abstract List getChildrenList();

}

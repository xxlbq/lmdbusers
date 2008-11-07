/*
 * 
 */
package com.livedoor.dbm.components.tree;

import javax.swing.tree.DefaultTreeModel;

/**
 * <p>
 * Description: DBMTreeModel
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DBMTreeModel extends DefaultTreeModel {
	private DBMTreeNodeList root;
	/**
	 * [機 能] 构造 DBMTreeModel
	 * <p>
	 * [解 説] 构造 DBMTreeModel，需要根据指定的DBMTreeNodeList 构造 DBMTreeModel 。
	 * <p>
	 * 
	 * @param treeNode
	 *            [備 考] なし
	 */
	public DBMTreeModel(DBMTreeNodeList treeNode) {
		super(treeNode);
		this.root = treeNode;
		if (root != null) {
			treeNode.setTreeModel(this);
			treeNode.updateList();

		}
	}
	/**
	 * [機 能] 得到孩子对象
	 * <p>
	 * [解 説] 得到孩子对象 。
	 * <p>
	 * [備 考] なし
	 */

	public Object getChild(Object obj, int i) {
		if (obj instanceof DBMTreeNodeList)
			return ((DBMTreeNodeList) obj).elementAt(i);
		else
			return null;
	}
	/**
	 * [機 能] 得到孩子个数
	 * <p>
	 * [解 説] 得到孩子个数 。
	 * <p>
	 * [備 考] なし
	 */
	public int getChildCount(Object obj) {
		if (obj instanceof DBMTreeNodeList)
			return ((DBMTreeNodeList) obj).size();
		else
			return 0;
	}
	/**
	 * [機 能] 得到具体的孩子
	 * <p>
	 * [解 説] 得到具体的孩子 。
	 * <p>
	 * [備 考] なし
	 */
	public int getIndexOfChild(Object obj, Object obj1) {
		if (obj instanceof DBMTreeNodeList)
			return ((DBMTreeNodeList) obj).indexOf(obj1);
		else
			return 0;
	}

	/**
	 * [機 能] 得到根节点
	 * <p>
	 * [解 説] 得到根节点 。
	 * <p>
	 * [備 考] なし
	 */
	public Object getRoot() {
		return root;
	}

}

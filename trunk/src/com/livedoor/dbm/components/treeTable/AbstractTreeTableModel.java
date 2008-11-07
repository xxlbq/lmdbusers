/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

/**
 * <p>
 * Title: 执行计划
 * </p>
 * <p>
 * Description: DbManager sql 语句执行计划
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author YuanBaoKun
 * @version 1.0
 */
public abstract class AbstractTreeTableModel implements TreeTableModel {
	protected Object root;
	protected EventListenerList listenerList = new EventListenerList();

	public AbstractTreeTableModel(Object root) {
		this.root = root;
	}

	/*
	 * Default implmentations for methods in the TreeModel interface. 
	 */
	
	/**
	 * [功 能] 得到树的根节点
	 * <p>
	 * [说 明] 得到树的根节点
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return Object 根节点
	 *         <p>
	 */
	public Object getRoot() {
		return root;
	}
	
	/**
	 * [功 能] 是否是叶子节点
	 * <p>
	 * [说 明] 判断当前节点是否为叶子
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param node
	 *            <p>
	 * 
	 * @return boolean
	 *         <p>
	 */
	public boolean isLeaf(Object node) {
		return getChildCount(node) == 0;
	}
	
	/**
	 * [功 能] 实现接口中的方法
	 * <p>
	 * [说 明] 实现接口中的方法
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	/**
	 * [功 能] 实现接口中的方法
	 * <p>
	 * [说 明] 实现接口中的方法
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public int getIndexOfChild(Object parent, Object child) {
		for (int i = 0; i < getChildCount(parent); i++) {
			if (getChild(parent, i).equals(child)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * [功 能] 实现接口中的方法
	 * <p>
	 * [说 明] 实现接口中的方法
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void addTreeModelListener(TreeModelListener l) {
		listenerList.add(TreeModelListener.class, l);
	}
	
	/**
	 * [功 能] 实现接口中的方法
	 * <p>
	 * [说 明] 实现接口中的方法
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void removeTreeModelListener(TreeModelListener l) {
		listenerList.remove(TreeModelListener.class, l);
	}

	/**
	 * [功 能] 变更节点
	 * <p>
	 * [说 明] 实现接口中的方法， 变更节点
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param Object 当前节点
	 * @param Object[] 当前路径
	 * @param int[] 节点顺序
	 * @param Object[] 子节点
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
			}
		}
	}

	/**
	 * [功 能] 插入节点
	 * <p>
	 * [说 明] 实现接口中的方法， 插入节点
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param Object 当前节点
	 * @param Object[] 当前路径
	 * @param int[] 节点顺序
	 * @param Object[] 子节点
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
			}
		}
	}

	/**
	 * [功 能] 删除节点
	 * <p>
	 * [说 明] 实现接口中的方法， 删除节点
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param Object 当前节点
	 * @param Object[] 当前路径
	 * @param int[] 节点顺序
	 * @param Object[] 子节点
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
			}
		}
	}

	/**
	 * [功 能] 改变节点
	 * <p>
	 * [说 明] 实现接口中的方法， 改变节点
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param Object 当前节点
	 * @param Object[] 当前路径
	 * @param int[] 节点顺序
	 * @param Object[] 子节点
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	protected void fireTreeStructureChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
			}
		}
	}

	/**
	 * [功 能] 得到列类型
	 * <p>
	 * [说 明] 得到指定列类型信息
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param int column 
	 *            <p>
	 * 
	 * @return class 
	 *         <p>
	 */
	public Class getColumnClass(int column) {
		return Object.class;
	}

	/**
	 * [功 能] 判断是否可以编辑
	 * <p>
	 * [说 明] 根据给定的节点，判断制定列是否可以编辑，该方法是对接口的实现
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param int column 
	 * @param Object node
	 *            <p>
	 * 
	 * @return boolean 
	 *         <p>
	 */
	public boolean isCellEditable(Object node, int column) {
		return getColumnClass(column) == TreeTableModel.class;
	}
	
	/**
	 * [功 能] 实现接口中的方法
	 * <p>
	 * [说 明] 实现接口中的方法，设定指定位置单元格的值
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param Object 值
	 * @param node 节点
	 * @param int 列
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void setValueAt(Object aValue, Object node, int column) {
	}
}

/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.AbstractTableModel;
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
public class TreeTableModelAdapter extends AbstractTableModel {
	private JTree tree;

	private TreeTableModel treeTableModel;

	/**
	 * [功 能] 实现抽象类中的方法
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param treeTableModel 表模式
	 * @param tree jtree
	 *            <p>
	 */
	public TreeTableModelAdapter(TreeTableModel treeTableModel, JTree tree) {
		this.tree = tree;
		this.treeTableModel = treeTableModel;

		tree.addTreeExpansionListener(new TreeExpansionListener() {
			// Don't use fireTableRowsInserted() here;
			// the selection model would get updated twice.
			public void treeExpanded(TreeExpansionEvent event) {
				fireTableDataChanged();
			}

			public void treeCollapsed(TreeExpansionEvent event) {
				fireTableDataChanged();
			}
		});
	}

	/**
	 * [功 能] 返回列数量
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return int 数量
	 *         <p>
	 */
	public int getColumnCount() {
		return treeTableModel.getColumnCount();
	}
	
	/**
	 * [功 能] 返回列名称，根据给定的参数
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param column 列顺序 
	 *            <p>
	 * 
	 * @return String 列名称
	 *         <p>
	 */
	public String getColumnName(int column) {
		return treeTableModel.getColumnName(column);
	}

	/**
	 * [功 能] 返回列类型，根据给定的参数
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param column 列顺序 
	 *            <p>
	 * 
	 * @return class 列类型
	 *         <p>
	 */
	public Class getColumnClass(int column) {
		return treeTableModel.getColumnClass(column);
	}

	/**
	 * [功 能] 返回行数量
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return int 数量
	 *         <p>
	 */
	public int getRowCount() {
		return tree.getRowCount();
	}

	/**
	 * [功 能] 根据给定的行，返回节点
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param row 行
	 *            <p>
	 * 
	 * @return Object 节点
	 *         <p>
	 */
	protected Object nodeForRow(int row) {
		TreePath treePath = tree.getPathForRow(row);
		return treePath.getLastPathComponent();
	}

	/**
	 * [功 能] 根据给定的参数，返回表格中显示的数据
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param node 节点
	 * @param column 列顺序 
	 *            <p>
	 * 
	 * @return Object 值
	 *         <p>
	 */
	public Object getValueAt(int row, int column) {
		return treeTableModel.getValueAt(nodeForRow(row), column);
	}

	/**
	 * [功 能] 根据给定的参数，判断该单元格是否可编辑
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param node 节点
	 * @param column 列顺序 
	 *            <p>
	 * 
	 * @return boolean 
	 *         <p>
	 */
	public boolean isCellEditable(int row, int column) {
		return treeTableModel.isCellEditable(nodeForRow(row), column);
	}

	/**
	 * [功 能] 给表格赋值
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param aValue 值
	 * @param node 节点
	 * @param column 列顺序 
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void setValueAt(Object value, int row, int column) {
		treeTableModel.setValueAt(value, nodeForRow(row), column);
	}
}

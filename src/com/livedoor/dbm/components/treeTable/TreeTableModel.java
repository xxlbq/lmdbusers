/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import javax.swing.tree.TreeModel;

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
public interface TreeTableModel extends TreeModel {
	
	/**
	 * [功 能] 返回列数量
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return int 数量
	 *         <p>
	 */
	public int getColumnCount();

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
	public String getColumnName(int column);

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
	public Class getColumnClass(int column);

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
	public Object getValueAt(Object node, int column);

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
	public boolean isCellEditable(Object node, int column);

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
	public void setValueAt(Object aValue, Object node, int column);
}

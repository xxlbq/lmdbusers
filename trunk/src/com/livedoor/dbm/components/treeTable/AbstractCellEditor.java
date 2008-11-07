/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import java.util.EventObject;

import javax.swing.CellEditor;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

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
public class AbstractCellEditor implements CellEditor {

	/*
	 * event listener list
	 */
	protected EventListenerList listenerList = new EventListenerList();

	/**
	 * [功 能] 获得Cell Editor值
	 * <p>
	 * [说 明] 获得Cell Editor值
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
	 * @return Object value
	 *         <p>
	 */
	public Object getCellEditorValue() {
		return null;
	}

	/**
	 * [功 能] 判断是否可编辑
	 * <p>
	 * [说 明] 判断是否可编辑，实现接口中的方法
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param e
	 *            EventObject
	 *            <p>
	 * 
	 * @return boolean
	 *         <p>
	 */
	public boolean isCellEditable(EventObject e) {
		return true;
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
	 * @param anEvent
	 *            EventObject
	 *            <p>
	 * 
	 * @return boolean
	 *         <p>
	 */
	public boolean shouldSelectCell(EventObject anEvent) {
		return false;
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
	 * @return boolean
	 *         <p>
	 */
	public boolean stopCellEditing() {
		return true;
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
	public void cancelCellEditing() {
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
	public void addCellEditorListener(CellEditorListener l) {
		listenerList.add(CellEditorListener.class, l);
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
	public void removeCellEditorListener(CellEditorListener l) {
		listenerList.remove(CellEditorListener.class, l);
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
	protected void fireEditingStopped() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == CellEditorListener.class) {
				((CellEditorListener) listeners[i + 1]).editingStopped(new ChangeEvent(this));
			}
		}
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
	protected void fireEditingCanceled() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == CellEditorListener.class) {
				((CellEditorListener) listeners[i + 1]).editingCanceled(new ChangeEvent(this));
			}
		}
	}
}

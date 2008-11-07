package com.livedoor.dbm.components.mainframe.history;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

/**
 * <p>
 * Description: ArrayListTableModel
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ArrayListTableModel extends AbstractTableModel {

	protected final ArrayList _list = new ArrayList(100);
	private int _columns;
	private String _columnNames[];
	/**
	 * [機 能] 构造 ArrayListTableModel
	 * <p>
	 * [解 説] 构造 ArrayListTableModel，需要根据指定的ArrayList和_columnNames[] 构造 DBMTreeModel 。
	 * <p>
	 * 
	 * @param treeNode
	 *            [備 考] なし
	 */
	public ArrayListTableModel(String as[], ArrayList arraylist) {
		_columns = 0;
		_columnNames = null;
		if (as != null) {
			_columnNames = as;
			_columns = _columnNames.length;
		}
		setArrayList(arraylist);
	}
	/**
	 * @return _columns
	 * [機 能] 得到ColumnCount
	 * <p>
	 * [解 説] 得到ColumnCount 。
	 * <p>
	 * [備 考] なし
	 */
	public int getColumnCount() {
		return _columns;
	}
	/**
	 * @param i 列号
	 * @return "invalid"
	 * [機 能] 得到ColumnName
	 * <p>
	 * [解 説] 得到ColumnName 。
	 * <p>
	 * [備 考] なし
	 */
	public String getColumnName(int i) {
		if (i < _columns && _columnNames != null)
			return _columnNames[i];
		else
			return "invalid";
	}
	/**
	 * [機 能] 得到RowCount
	 * <p>
	 * [解 説] 得到RowCount 。
	 * <p>
	 * [備 考] なし
	 */
	public int getRowCount() {
		int i = _list == null ? 0 : _list.size();
		return i;
	}
	/**
	 * @param i
	 * @param j
	 * @return Object
	 * [機 能] 从焦点取得数据
	 * <p>
	 * [解 説] 从焦点取得数据 。
	 * <p>
	 * [備 考] なし
	 
	 */
	public Object getValueAt(int i, int j) {
		Object obj = null;
		if (_list != null && i < _list.size()) {
			ArrayList arraylist = (ArrayList) _list.get(i);
			if (j < arraylist.size())
				obj = arraylist.get(j);
			else
				obj = new Object();
		}
		if (obj == null)
			return "(null)";
		else
			return obj;
	}
	/**
	 * @param obj
	 * @param i
	 * @param j
	 * [機 能] 设定焦点数据为obj
	 * <p>
	 * [解 説] 设定焦点数据为obj 。
	 * <p>
	 * [備 考] なし
	 
	 */
	@SuppressWarnings("unchecked")
	public void setValueAt(Object obj, int i, int j) {
		if (_list != null && i < _list.size()) {
			ArrayList arraylist = (ArrayList) _list.get(i);
			if (j < arraylist.size())
				arraylist.set(j, obj);
		}
	}
	/**
	 * @param i
	 * @param j
	 * @return Object
	 * [機 能] 从焦点取得数据
	 * <p>
	 * [解 説] 从焦点取得数据 。
	 * <p>
	 * [備 考] なし
	 
	 */
	public Object getObjectValueAt(int i, int j) {
		Object obj = null;
		if (_list != null && i < _list.size()) {
			ArrayList arraylist = (ArrayList) _list.get(i);
			if (j < arraylist.size())
				obj = arraylist.get(j);
			else
				obj = new Object();
		}
		if (obj == null)
			return "(null)";
		else
			return obj;
	}
	/**
	 * @param obj
	 * @param obj1
	 * @param i
	 * @param j
	 * [機 能] 设定焦点数据为obj obj1
	 * <p>
	 * [解 説] 设定焦点数据为obj 。
	 * <p>
	 * [備 考] なし
	 
	 */
	public void setValueAt(Object obj, Object obj1, int i) {
	}
	/**
	 * @param s
	 * @return i -1
	 * [機 能] 取得ColumnIndex
	 * <p>
	 * [解 説] 取得ColumnIndex 。
	 * <p>
	 * [備 考] なし
	 
	 */

	public int getColumnIndex(String s) {
		for (int i = 0; i < _columnNames.length; i++)
			if (_columnNames[i].equalsIgnoreCase(s))
				return i;

		return -1;
	}
	/**
	 * @param as[]
	 * [機 能] 设定columnNames as[]
	 * <p>
	 * [解 説] 设定columnNames as[] 。
	 * <p>
	 * [備 考] なし
	 
	 */
	public void setNames(String as[]) {
		_columnNames = as;
		_columns = _columnNames.length;
	}
	/**
	 * @param arraylist
	 * [機 能] 设定ArrayList arraylist
	 * <p>
	 * [解 説] 设定ArrayList arraylist 。
	 * <p>
	 * [備 考] なし
	 
	 */
	@SuppressWarnings("unchecked")
	public void setArrayList(ArrayList arraylist) {
		if (arraylist == null)
			_list.clear();
		else
			_list.addAll(arraylist);
	}
	/**
	 * [機 能] get ArrayList
	 * <p>
	 * [解 説] get ArrayList 。
	 * <p>
	 * [備 考] なし
	 
	 */
	public ArrayList getArrayList() {
		return _list;
	}
	/**
	 * [機 能] Object[] to Array
	 * <p>
	 * [解 説] Object[] to Array 。
	 * @return _list.toArray(new Object[0])
	 * <p>
	 * [備 考] なし
	 
	 */
	@SuppressWarnings("unchecked")
	public Object[] toArray() {
		return _list.toArray(new Object[0]);
	}
/**
 * [機 能] 判断LIST 中是否contains obj
 * [解 説] 判断LIST 中是否contains obj 。
 * @param obj
 * @return
 */
	public boolean contains(Object obj) {
		boolean flag = _list.contains(obj);
		return flag;
	}
/**
 * [機 能] 把 obj add 到 Element
 * [解 説] 把 obj add 到 Element 。
 * @param obj
 */
	@SuppressWarnings("unchecked")
	public void addElement(Object obj) {
		_list.add(obj);
		fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
	}
/**
 * [機 能] 在i处把obj add到 Element
 * [解 説] 在i处把obj add到 Element 。
 * @param obj
 * @param i
 */
	@SuppressWarnings("unchecked")
	public void insertElementAt(Object obj, int i) {
		_list.add(i, obj);
		fireTableRowsInserted(i, i);
	}
	/**
	 * [機 能] 在i处把obj remove
	 * [解 説] 在i处把obj remove 。
	 * @param i
	 */
	public Object removeElementAt(int i) {
		Object obj = _list.remove(i);
		fireTableRowsDeleted(i, i);
		return obj;
	}
	/**
	 * [機 能] 在i处把obj 获取
	 * [解 説] 在i处把obj 获取 。
	 * @param i
	 */
	public Object getElementAt(int i) {
		return getValueAt(i, 0);
	}
	/**
	 * [機 能] 在i处把obj 获取 
	 * [解 説] 在i处把obj 获取 。
	 * 
	 * @param obj
	 * @param i
	 * @return Object
	 */
	public Object getValueAt(Object obj, int i) {
		if (obj instanceof ArrayList) {
			ArrayList arraylist = (ArrayList) obj;
			return arraylist.get(i);
		} else {
			return "noSubclass";
		}
	}
	/**
	 * [機 能] 在i处把obj 获取 
	 * [解 説] 在i处把obj 获取 。
	 * 
	 * @param obj
	 * @param i
	 * @return Object
	 */
	public Object getObjectAt(int i) {
		if (_list != null && i >= 0 && i < _list.size())
			return _list.get(i);
		else
			return null;
	}
	/**
	 * [機 能] 在i j处把 Object 增加 
	 * [解 説] 在i j处把 Object 增加  。
	 * 
	 * @param obj
	 * @param i
	 * @param j
	 */
	public void addValue(Object obj, int i, int j) {
		if (_list != null && i < _list.size() && j < _columns)
			setValueAt(obj, getObjectAt(i), j);
	}
	/**
	 * [機 能] get Size 
	 * [解 説] get Size  。
	 * @return int
	 */
	public int getSize() {
		return getRowCount();
	}
	/**
	 * [機 能] clear 
	 * [解 説] clear  。
	 */
	public void clear() {
		int i = 0;
		int j = _list.size() - 1;
		for (int k = _list.size() - 1; k >= 0; k--) {
			ArrayList arraylist = (ArrayList) _list.remove(k);
			for (int l = arraylist.size() - 1; l >= 0; l--) {
				Object obj = arraylist.remove(l);
				if (obj instanceof ArrayListObject)
					((ArrayListObject) obj).clear();
			}

			arraylist.clear();
		}

		_list.clear();
		if (j >= 0)
			fireTableRowsDeleted(i, j);
	}
	/**
	 * [機 能] get Columns 
	 * [解 説] get Columns  。
	 * @return TableColumnModel
	 */
	public TableColumnModel getColumns() {
		return null;
	}
	/**
	 * [機 能] canabolizeMemory 
	 * [解 説] canabolizeMemory  。
	 */
	public void canabolizeMemory() {
		_list.clear();
		_list.trimToSize();
	}
}

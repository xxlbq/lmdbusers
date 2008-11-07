package com.livedoor.dbm.components.mainframe.tableedit;

import java.awt.Color;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.livedoor.dbm.components.common.DBMCommonDialog;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.scripts.SqlBuilderFactory;
import com.livedoor.dbm.util.ConvertObject;
import com.livedoor.dbm.util.ConvertedDate;
import com.livedoor.dbm.util.ConvertedDateTime;
import com.livedoor.dbm.util.ConvertedTime;
import com.livedoor.dbm.util.Convertor;

/**
 * <p>
 * Title: 编辑数据库表数据用的表格模型
 * <p>
 * Description: 编辑数据库表数据用的表格模型
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class DBDataEditorJTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2407907052839811876L;

	private List<List> datas; // 保存从数据库里取得的原始数据

	private List columnNames; // 保存数据库表的所有字段名

	private int[] columnTypes; // 保存数据库表的所有字段类型

	private int databaseRowCount; // 保存初始加载到JTable里的数据行数.这些行是数据库表中已经存在的行.

	private HashMap deletedRows; // 保存用户删除的行号,只有当用户选择删除的行号小于databaseRowCount时才将行号记录到这个集合里

	private HashMap updatedRows; // 保存用户对JTable中每个cell的最后一次更新的值.

	// 只有当用户选择删除的行号小于databaseRowCount时才将行号记录到这个集合里


	public DBDataEditorJTableModel(List columnNames, int[] columnTypes, HashMap deletedRows, HashMap updatedRows, List datas) {
		super();
		init(columnNames, columnTypes, deletedRows, updatedRows, datas);

	}

	private void init(List columnNames, int[] columnTypes, HashMap deletedRows, HashMap updatedRows, List datas) {
		this.datas = datas;
		this.columnNames = columnNames;
		this.columnTypes = columnTypes;
		this.databaseRowCount = datas.size();
		this.deletedRows = deletedRows;
		this.updatedRows = updatedRows;
	}

	/*
	 * 重写父类的方法,JTable通过调用这个方法确定要显示多少行
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return this.datas.size();
	}

	/*
	 * 重写父类的方法,JTable通过调用这个方法确定要显示多少列
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {

		return this.columnNames.size();
	}

	/*
	 * 重写父类的方法,JTable通过调用这个方法取得数据,并显示到对应的cell里
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value;
		HashMap updateRow;

		updateRow = (HashMap) this.updatedRows.get(Integer.valueOf(rowIndex));
		if (updateRow != null && updateRow.containsKey(Integer.valueOf(columnIndex))) {
			value = updateRow.get(Integer.valueOf(columnIndex));// 指定的cell修改过,则将这个修改的值返回
		} else {
			value = this.datas.get(rowIndex).get(columnIndex);// 指定的cell没有修改过,则将原始值返回
		}
		return value;

	}

	/*
	 * 重写父类的方法,JTable通过调用这个方法判断指定的cell是否允许编辑
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;// 表的第一列不允许编辑
	}

	/*
	 * 重写父类的方法,JTable通过调用这个方法保存数据的修改
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		HashMap row;
		List dataRow;
		Object oldValue = this.datas.get(rowIndex).get(columnIndex);
		ConvertObject value = null;
		Convertor convertor = new Convertor();

		if (aValue == null)
			value = convertor.string2ConvertObject(null, this.columnTypes[columnIndex]);// 验证用户输入是不是合法,如果合法将转换为对应的java对象,不合法返回null
		else
			value = convertor.string2ConvertObject(aValue.toString(), this.columnTypes[columnIndex]);// 验证用户输入是不是合法,如果合法将转换为对应的java对象,不合法返回null
		if (value.isError()) {
			DBMMessageDialog.showErrorMessageDialog("DATA_TYPE_ERROR");
			return;// 用户输入不合法,直接忽略这个修改
		}
		if (rowIndex < this.databaseRowCount) {
			// 修改的行是数据库表的原有数据,则将修改保存到updatedRows里.
			row = (HashMap) this.updatedRows.get(Integer.valueOf(rowIndex));
			if (row == null) {
				row = new HashMap();
				this.updatedRows.put(Integer.valueOf(rowIndex), row);
			}
			if (value.equals(oldValue)) {
				row.remove(Integer.valueOf(columnIndex));// 修改的值和原始值相同,就直接删除这个修改.
				if (row.keySet().size() < 1)
					this.updatedRows.remove(Integer.valueOf(rowIndex));
			} else
				row.put(Integer.valueOf(columnIndex), value);// 修改的值和原始值不相同,将修改保存到updatedRows里.

		} else {
			// 修改的行是用户新加的行.则直接在行上修改.
			dataRow = this.datas.get(rowIndex);
			dataRow.set(columnIndex, value);
		}

	}

	/*
	 * 重写父类的方法,JTable通过调用这个方法得到列名
	 * 
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String) this.columnNames.get(column);
	}

	/*
	 * 删除一行
	 */
	public void deleteRow(int rowIndex) {
		if (rowIndex >= this.databaseRowCount) {
			datas.remove(rowIndex);// 删除的行是用户添加的行,就直接删除
		} else {
			this.deletedRows.put(Integer.valueOf(rowIndex), Integer.valueOf(rowIndex));// 删除的行是数据库表是原有的行,将行Id保存
		}
		this.fireTableRowsDeleted(rowIndex, rowIndex);
	}

	/*
	 * 添加一个空行
	 */
	public void addEmptyRow() {
		List row = new ArrayList(columnNames.size());
		for (int i = 0; i < columnNames.size(); i++)
			row.add(null);
		datas.add(row);
		this.fireTableRowsInserted(datas.size() - 1, datas.size() - 1);
	}

	/**
	 * 得到指定cell的前景色
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return
	 */
	public Color getColorAt(int rowIndex, int columnIndex) {

		if (rowIndex >= this.databaseRowCount)
			return Color.GREEN;
		if (this.deletedRows.get(Integer.valueOf(rowIndex)) != null)
			return Color.RED;
		HashMap updateRow = (HashMap) this.updatedRows.get(Integer.valueOf(rowIndex));
		if (updateRow != null && updateRow.containsKey(Integer.valueOf(columnIndex)))
			return Color.BLUE;

		return null;

	}

	/**
	 * 清除指定cell上的修改
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	public void clearCellChanges(int rowIndex, int colIndex) {
		HashMap row;

		if (rowIndex >= this.databaseRowCount) {
			// 修改了用户添加的行上的一个cell.直接将其赋null.
			this.datas.get(rowIndex).set(colIndex, null);
			this.fireTableCellUpdated(rowIndex, colIndex);
			return;
		}
		row = (HashMap) this.updatedRows.get(Integer.valueOf(rowIndex));
		if (row != null) {
			// 修改的行是数据库原有的行上的一个cell,并且该cell有过更改.直接清除updateRows里对应的修改值
			row.remove(Integer.valueOf(colIndex));
			if (row.keySet().size() < 1)
				this.updatedRows.remove(Integer.valueOf(rowIndex));
			this.fireTableCellUpdated(rowIndex, colIndex);
		}
	}

	/**
	 * 取消删除行
	 * 
	 * @param rowIndex
	 */
	public void clearDeleteRow(int[] rowIndex) {
		if (rowIndex != null) {
			for (int i = 0; i < rowIndex.length; i++) {
					this.deletedRows.remove(Integer.valueOf(rowIndex[i]));
					this.fireTableRowsUpdated(rowIndex[i], rowIndex[i]);
			}
		}
	}

	/**
	 * 取消所有的修改
	 * 
	 */
	public void clearAllChanges() {
		this.deletedRows.clear();
		HashMap row;
		Iterator tmp;
		tmp = this.updatedRows.values().iterator();
		while (tmp.hasNext()) {
			row = (HashMap) tmp.next();
			row.clear();
		}
		this.updatedRows.clear();
		int maxId = this.datas.size() - 1;
		for (int i = maxId; i >= this.databaseRowCount; i--) {
			this.datas.get(i).clear();
			this.datas.remove(i);
		}
		// System.out.println(maxId + ":" + this.databaseRowCount);
		this.fireTableDataChanged();
	}

	/**
	 * 将指定的cell赋null
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	public void setCellNull(int rowIndex, int colIndex) {

		setValueAt(null, rowIndex, colIndex);
		this.fireTableCellUpdated(rowIndex, colIndex);

	}

	/**
	 * 将指定的cell赋为当前日期或者时间
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	public void setCurrentTime(int rowIndex, int colIndex) {
		// String value = null;
		// SimpleDateFormat df;
		// switch (this.columnTypes[colIndex]) {
		// case Types.DATE :
		//
		// df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// value = df.format(new java.util.Date());
		// break;
		// case Types.TIME :
		// df = new SimpleDateFormat("HH:mm:ss");
		// value = df.format(new java.util.Date());
		// break;
		// case Types.TIMESTAMP :
		// df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// value = df.format(new java.util.Date());
		// break;
		// }
		// if (value != null) {
		// setValueAt(value, rowIndex, colIndex);
		// this.fireTableCellUpdated(rowIndex, colIndex);
		// }

		Convertor convertor = new Convertor();
		Object value;
		switch (this.columnTypes[colIndex]) {
			case Types.DATE :
				value = new ConvertedDate(new Date().getTime(), convertor.getDateFormat());
				break;
			case Types.TIME :
				value = new ConvertedTime(new Date().getTime(), convertor.getTimeFormat());
				break;
			default :
				value = value = new ConvertedDateTime(new Date().getTime(), convertor.getDatetimeFormat());
		}

		setValueAt(value, rowIndex, colIndex);
		this.fireTableCellUpdated(rowIndex, colIndex);

	}

	public void refresh(List columnNames, int[] columnTypes, HashMap deletedRows, HashMap updatedRows, List datas) {
		this.clearAllChanges();
		this.init(columnNames, columnTypes, deletedRows, updatedRows, datas);
	}

	public boolean haveChanges() {

		//检查是否有新加数据
		if(this.datas.size() >this.databaseRowCount  )
			return true;
		//检查是否有删除数据
		if(deletedRows.size() > 0)
			return true;
		//检查是否有更新数据
		if(updatedRows.size() > 0)
			return true;

		return false;
	}



}

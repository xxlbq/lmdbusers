package com.livedoor.dbm.components.mainframe.createdt.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * <p>
 * Title: 修改表字段定义的扩展信息的TableModel
 * <p>
 * Description: 提供了修改表字段扩展信息的最基本操作,字段扩展信息为:1默认值,2整数位数,3小数位数,4是否自增
 * <p>
 * Copyright: Copyright (c) 2006-10-17
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public abstract class AbsSlaveTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2407907052839811876L;

	private List slavePropertyNameList;// 保存字段的扩展属性名,

	private List<List> slavePropertyValueList;// 保存所有字段的扩展属性值,与masterValueList中的对象按list的索引一一对应

	private List<String> columnTitleList;// 保存表格的列名

	private String currentFieldType = "";// 当前正在编辑的数据库字段的类型名

	private List<Integer> precisionParamRowIndex;// precision 参数所在的行

	private List havePrecisionTypeName;// 可以包含精度的类型名

	private int currentRow;// 当前正在编辑的数据库字段在masterValueList中的索引

	private int initRows;// 初始有多少个字段

	/**
	 * @param slavePropertyNameList
	 * @param slavePropertyValueList
	 */
	@SuppressWarnings("unchecked")
	public AbsSlaveTableModel(List slavePropertyNameList, List<List> slavePropertyValueList) {
		super();

		this.slavePropertyValueList = slavePropertyValueList;
		this.slavePropertyNameList = slavePropertyNameList;
		this.precisionParamRowIndex = this.getPrecisionParamIndex();
		if (slavePropertyValueList == null) {
			slavePropertyValueList = new ArrayList();
		}
		this.initRows = slavePropertyValueList.size() - 1;

		columnTitleList = new ArrayList(2);
		columnTitleList.add("Parameter");
		columnTitleList.add("Value");

		this.havePrecisionTypeName = this.havePrecisionTypeName();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@SuppressWarnings("unchecked")
	public int getRowCount() {
		return this.slavePropertyNameList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {

		return this.columnTitleList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return this.slavePropertyNameList.get(rowIndex);
		else
			return this.slavePropertyValueList.get(this.currentRow).get(rowIndex);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {

		if (columnIndex > 0) {
			if (this.precisionParamRowIndex.contains(Integer.valueOf(rowIndex))) {
				if (this.havePrecisionTypeName.contains(this.currentFieldType))
					return true;
				else
					return false;
			} else
				return true;
		} else
			return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	@SuppressWarnings("unchecked")
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		this.slavePropertyValueList.get(this.currentRow).set(rowIndex, aValue);
	}

	/*
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {

		return (String) this.columnTitleList.get(column);
	}

	/**
	 * 刷新整个表格
	 * 
	 * @param columnType
	 *            当前字段的类型
	 * @param rowIndex
	 *            当前编辑的行
	 */
	public void refresh(String columnType, int rowIndex) {

		this.currentFieldType = columnType;
		this.currentRow = rowIndex;
		this.fireTableDataChanged();

	}

	/**
	 * 当前编辑的行是原有的行
	 * 
	 * @return
	 */
	protected boolean isOldRow() {
		return this.currentRow < this.initRows;

	}

	/**
	 * 得到下拉列表的选项
	 * 
	 * @return
	 */
	public abstract String[] getComboBoxValues();

	/**
	 * 得到充许输入精度的类型名
	 * 
	 * @return
	 */
	public abstract List havePrecisionTypeName();

	/**
	 * 精度参数在slavePropertyNameList中的索引
	 * 
	 * @return
	 */
	public abstract List<Integer> getPrecisionParamIndex();

	/**
	 * 需要下拉列表的参数名
	 * 
	 * @return
	 */
	public abstract String haveComboBoxParamName();

}

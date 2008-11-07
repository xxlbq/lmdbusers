package com.livedoor.dbm.components.mainframe.createdt.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.ColumnProperty;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.scripts.SqlBuilderFactory;

/**
 * <p>
 * Title: 修改表字段定义的主要信息的TableModel
 * <p>
 * Description: 提供了修改表字段主要信息的最基本操作,字段主要信息为:1字段名,2字段类型,3字段长度,4是否充许为空,5是否是主键
 * <p>
 * Copyright: Copyright (c) 2006-10-17
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 * @see AbstractTableModel
 */
public abstract class AbsMasterTableModel extends AbstractTableModel {

	/**
	 * p
	 * 
	 */
	private static final long serialVersionUID = 2407907052839811876L;

	private List<List> masterPropertyValueList;// 保存所有字段的主要属性值.

	private List columnTitleList;// 保存表格的列名

	private List<List> slavePropertyValueList;// 保存所有字段的扩展属性值,与masterValueList中的对象按list的索引一一对应

	private List slavePropertyNameList;// 保存字段的扩展属性名,

	private HashMap<String, Integer> typesMap;// 数据库支持的类型名与长度的对照表

	/**
	 * @param masterPropertyValueList
	 * @param slavePropertyValueList
	 */
	@SuppressWarnings("unchecked")
	public AbsMasterTableModel(List<List> masterPropertyValueList, List<List> slavePropertyValueList) {
		super();

		if (masterPropertyValueList == null)
			this.masterPropertyValueList = new ArrayList();
		else
			this.masterPropertyValueList = masterPropertyValueList;

		if (slavePropertyValueList == null)
			this.slavePropertyValueList = new ArrayList();
		else
			this.slavePropertyValueList = slavePropertyValueList;

		columnTitleList = new ArrayList(5);
		columnTitleList.add(ColumnProperty.NAME);
		columnTitleList.add(ColumnProperty.TYPE);
		columnTitleList.add(ColumnProperty.LENGTH);
		columnTitleList.add(ColumnProperty.NULLABLE);
		columnTitleList.add(ColumnProperty.PK);

		this.typesMap = this.generateTypesMap();// 由子类实现的抽象方法
		this.slavePropertyNameList = this.generateSlavePropertyNameList();// 由子类实现的抽象方法

		addRow();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@SuppressWarnings("unchecked")
	public int getRowCount() {
		return this.masterPropertyValueList.size();
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
		return masterPropertyValueList.get(rowIndex).get(columnIndex);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	@SuppressWarnings("unchecked")
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
	
		if (rowIndex == this.masterPropertyValueList.size() - 1 && ( aValue == null || "".equals(aValue))) 
			return;
		// 如果用户修改了字段的类型,则将该字段的扩展属性清空,并且清空length属性
		if (columnIndex == 1) {
			if (!objectEquals(aValue, this.masterPropertyValueList.get(rowIndex).get(columnIndex))) {
				List tmp = this.slavePropertyValueList.get(rowIndex);
				tmp.clear();
				for (int i = 0; i < this.slavePropertyNameList.size(); i++)
					tmp.add("");
				this.masterPropertyValueList.get(rowIndex).set(2, "");
				this.fireTableCellUpdated(rowIndex, 2);
			}
		}
		// 保存用户的修改
		this.masterPropertyValueList.get(rowIndex).set(columnIndex, aValue);

		// if(columnIndex == 4){
		// if(Boolean.TRUE.equals(aValue)){
		// this.masterPropertyValueList.get(rowIndex).set(3, Boolean.FALSE);
		// this.fireTableCellUpdated(rowIndex, 3);
		// }
		// }

		// 如果用户在最后一行输入字段名,就再追加一行
		if (columnIndex == 0 && rowIndex == this.masterPropertyValueList.size() - 1 ) {
	
			addRow();
		}
	}

	/**
	 * 比较两个可能为空的对象是否是同一个对象,提供给子类使用
	 * 
	 * @param obj1
	 * @param obj2
	 * @return 相同返回True
	 */
	protected boolean objectEquals(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null)
			return true;
		if (obj1 != null)
			return obj1.equals(obj2);
		if (obj2 != null)
			return obj2.equals(obj1);
		return obj1 == obj2;

	}

	/**
	 * 添加一个新行 在masterTable与slaveTable中分别添加一个新行
	 */
	@SuppressWarnings("unchecked")
	private void addRow() {
		List tmp;
		tmp = new ArrayList(5);
		tmp.add("");
		tmp.add(getDefaultFiledType());
		tmp.add(getDefaultFieldSize());
		tmp.add(Boolean.TRUE);
		tmp.add(Boolean.FALSE);
		this.masterPropertyValueList.add(tmp);
		tmp = new ArrayList();
		for (int i = 0; i < this.slavePropertyNameList.size(); i++)
			tmp.add("");
		
		this.slavePropertyValueList.add(tmp);
		this.fireTableRowsInserted(this.masterPropertyValueList.size() - 1, this.masterPropertyValueList.size() - 1);
	}

	/**
	 * 得到对应的sql语句.默认的实现是返回建表的语句.如果想返回其它类型的sql需要在对应的子类中重写该方法
	 * 
	 * @param connInfo
	 * @param dbSession
	 * @param dataMetaInfo
	 * @return 对应的sql语句
	 */
	@SuppressWarnings("unchecked")
	public String getSql(ConnectionInfo connInfo, DBSession dbSession, DBMDataMetaInfo dataMetaInfo) {
		List<HashMap> allColumns = new ArrayList();
		HashMap column;
		for (int i = 0; i < this.masterPropertyValueList.size(); i++) {
			column = getRowData(i);
			allColumns.add(column);
		}
		return SqlBuilderFactory.createSqlBuilder(connInfo, dbSession, dataMetaInfo).createDynamic(allColumns);
	}

	/**
	 * 得到一个表字段的所有属性的值
	 * 
	 * @param i
	 *            表字段所在的行索引
	 * @return 包含所有属性值的HashMap,HashMap的key包含在ColumnProperty类中
	 * @see com.livedoor.dbm.constants.ColumnProperty
	 */
	@SuppressWarnings("unchecked")
	protected HashMap getRowData(int i) {
		HashMap column;
		List property;
		List extProperty;
		column = new HashMap();
		property = this.masterPropertyValueList.get(i);
		extProperty = this.slavePropertyValueList.get(i);
		for (int j = 0; j < property.size(); j++)
			column.put(this.columnTitleList.get(j), property.get(j));
		for (int k = 0; k < slavePropertyNameList.size(); k++)
			column.put(this.slavePropertyNameList.get(k), extProperty.get(k));
		return column;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// 检查数据类型是否充许编辑数据长度
		if (columnIndex == 2) {
			Object typeName = this.getValueAt(rowIndex, 1);
			if (typeName != null)
				if (haveLength(typeName.toString()) == -1)
					return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {

		return (String) this.columnTitleList.get(column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	@SuppressWarnings("unchecked")
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/**
	 * 取得字段类型对应的长度
	 * 
	 * @param typeName
	 * @return 如果有对应的长度返回该长度,否则返回-1
	 */
	public int haveLength(String typeName) {

		Integer length;
		if (typeName == null)
			return -1;
		else {
			length = this.typesMap.get(typeName);
			if (length != null)
				return length.intValue();
			else
				return -1;
		}
	}

	/**
	 * 得到所有的类型名
	 * 
	 * @return
	 */
	public Object[] getAllTypeName() {
		return this.typesMap.keySet().toArray();
	}

	/**
	 * 取得所有字段的扩展属性值
	 * 
	 * @return 保存所有字段的扩展属性值的对象的引用
	 */
	public List getSlavePropertyValueList() {
		return this.slavePropertyValueList;
	}

	/**
	 * 取得数据库支持的扩展属性名
	 * 
	 * @return 保存扩展属性名的对象的引用
	 */
	public List getSlavePropertyNameList() {
		return this.slavePropertyNameList;
	}
	/**
	 * 刷新整个表格
	 * 
	 */
	public void refresh() {
		this.fireTableDataChanged();
	}

	/**
	 * 删除数据库表的指定字段
	 * 
	 * @param rowIndex
	 *            字段所在的行索引
	 */
	public void deleteRow(int rowIndex) {

		// 最后一行不充许删除.
		if (rowIndex == this.masterPropertyValueList.size() - 1)
			return;
		List tmp;
		tmp = this.masterPropertyValueList.remove(rowIndex);
		if (tmp != null)
			for (int i = 0; i < tmp.size(); i++)
				tmp.remove(0);
		tmp = this.slavePropertyValueList.remove(rowIndex);
		if (tmp != null)
			for (int i = 0; i < tmp.size(); i++)
				tmp.remove(0);

		this.fireTableRowsDeleted(this.masterPropertyValueList.size(), this.masterPropertyValueList.size());

	}

	/**
	 * 取得每个JTable中每个单元格的背景色
	 * 
	 * @param rowIndex
	 *            单元格所在的行
	 * @param colIndex
	 *            单元格所在的列
	 * @return 颜色对象
	 */
	public abstract Color getColorAt(int rowIndex, int colIndex);

	/**
	 * 生成数据库支持的类型名与长度的对照表
	 * 
	 * @return
	 */
	protected abstract HashMap generateTypesMap();

	/**
	 * 生成数据库支持的扩展属性名
	 * 
	 * @return 包含所有属性名的List
	 */
	protected abstract List<String> generateSlavePropertyNameList();

	/**
	 * 取得默认的字段类型
	 * 
	 * @return 字段类型
	 */
	protected abstract String getDefaultFiledType();

	/**
	 * 取得默认的字段长度
	 * 
	 * @return 字段长度
	 */
	protected abstract String getDefaultFieldSize();
	/**
	 * cloneList
	 * 
	 * @param obj
	 * @return new List
	 */
	@SuppressWarnings("unchecked")
	protected List<List> cloneList(List<List> obj) {
		List descList = new ArrayList();
		List descSubList;
		List tmp;
		if (obj == null)
			return null;
		for (int i = 0; i < obj.size(); i++) {
			tmp = obj.get(i);
			descSubList = new ArrayList();
			for (int j = 0; j < tmp.size(); j++) {
				descSubList.add(tmp.get(j));
			}
			descList.add(descSubList);
		}
		return descList;
	}

	/**
	 * 取得所有主键
	 * 
	 * @return 包含所有主键列名的List
	 */
	@SuppressWarnings("unchecked")
	protected List<String> getParamKey() {
		List pkList = new ArrayList();
		for (int i = 0; i < this.getRowCount(); i++) {
			if (Boolean.TRUE.equals(this.getValueAt(i, 4)))
				pkList.add(this.getValueAt(i, 0));
		}
		return pkList;
	}

	/**
	 * 比较两个行的所有值是否相等,比较过程中发生任何异常都认为有过修改
	 * 
	 * @param row1
	 * @param row2
	 * @return
	 */
	protected boolean equalsMasterRow(List row1, int rowIndex) {
		try {
			List thisRow = this.masterPropertyValueList.get(rowIndex);
			for (int i = 0; i < 4; i++) {
				if (i == 2) {
					//当第二列的时候对象类型不确定,所以将它们转成String比较
					Object tmp;
					String oldValue;
					String currentValue;
					tmp = row1.get(i);
					if(tmp == null)
						oldValue = "";
					else
						oldValue = tmp.toString();
					tmp = thisRow.get(i);
					if(tmp == null)
						currentValue = "";
					else
						currentValue = tmp.toString();
					if (!oldValue.equals(currentValue)){
						return false;
					}
				} else if (!objectEquals(row1.get(i), thisRow.get(i))) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

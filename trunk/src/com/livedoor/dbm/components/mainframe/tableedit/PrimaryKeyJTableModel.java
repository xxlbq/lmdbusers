package com.livedoor.dbm.components.mainframe.tableedit;

import java.awt.Color;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.DBColumnType;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.util.Convertor;
import com.livedoor.dbm.util.DBMComponentUtil;


/**
 * <p>
 * Title: 编辑主键用的表格模型
 * <p>
 * Description: 编辑主键用的表格模型
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class PrimaryKeyJTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2407907052839811876L;

	private List<List> columnsNameAndTypeName;

	private List columns;

	public PrimaryKeyJTableModel(List columnsNameAndTypeName) {
		super();
		columns = new ArrayList(3);
		columns.add("name");
		columns.add("type");
		columns.add("key");

		this.columnsNameAndTypeName = columnsNameAndTypeName;
	}

	@SuppressWarnings("unchecked")
	/*
	 * 重写父类的方法,JTable通过调用这个方法确定要显示多少行
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return this.columnsNameAndTypeName.size();
	}

	/*
	 * 重写父类的方法,JTable通过调用这个方法确定要显示多少列
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {

		return this.columns.size();
	}

	/*
	 * 重写父类的方法,JTable通过调用这个方法取得数据,并显示到对应的cell里
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return columnsNameAndTypeName.get(rowIndex).get(columnIndex);

	}

	/*
	 * 重写父类的方法,JTable通过调用这个方法判断指定的cell是否允许编辑
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex > 1)
			return true;
		else
			return false;// 表的前二列不允许编辑
	}

	/*
	 * 重写父类的方法,JTable通过调用这个方法保存数据的修改
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		this.columnsNameAndTypeName.get(rowIndex).set(columnIndex, aValue);
	}

	/*
	 * 比较两个对象是否相同
	 */
	private boolean objectEquals(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null)
			return true;
		if (obj1 != null)
			return obj1.equals(obj2);
		if (obj2 != null)
			return obj2.equals(obj1);
		return obj1 == obj2;

	}

	/*
	 * 重写父类的方法,JTable通过调用这个方法得到列名
	 * 
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String) this.columns.get(column);
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
	
	public void clear(){
		List row;
		for(int i= 0 ;i<this.columnsNameAndTypeName.size();i++){
			row = this.columnsNameAndTypeName.get(i);
			row.clear();
		}
		this.columnsNameAndTypeName.clear();
		
	}

	public void refresh(List columnsNameAndTypeName){
		
		this.clear();
		this.columnsNameAndTypeName = columnsNameAndTypeName;
		
	}
	
}

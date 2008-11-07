package com.livedoor.dbm.components.mainframe.createdt.view;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableCellEditor;

import com.livedoor.dbm.components.common.DBMTable;
import com.livedoor.dbm.components.mainframe.createdt.model.AbsSlaveTableModel;

/**
* <p>
* Title:创建和修改表用的扩展属性Table
* <p>
* Description:创建和修改表用的扩展属性Table
* <p>
* Copyright: Copyright (c) 2006-10-18
* <p>
* Company: 英極軟件開發（大連）有限公司
* 
* @author WangHuiTang
* @version 1.0
*/
public class SlaveJTable extends DBMTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 236191111964834885L;
	JComboBox types;
	String haveComboBoxParamName;
	/* (non-Javadoc)
	 * @see javax.swing.JTable#getCellEditor(int, int)
	 */
	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		
		if(this.haveComboBoxParamName.equals(this.getModel().getValueAt(row,0)))
			return new DefaultCellEditor(types);
		else
			return super.getCellEditor(row, column);
	}

	/**
	 * @param model
	 */
	public SlaveJTable(AbsSlaveTableModel model) {
		super();
		this.setModel(model);
		types = new JComboBox();
		this.haveComboBoxParamName = model.haveComboBoxParamName();
		String[] values = model.getComboBoxValues();
		for(int i=0; i<values.length; i++)
		types.addItem(values[i]);
		
		getColumnModel().getColumn(0).setPreferredWidth(150);
		getColumnModel().getColumn(1).setPreferredWidth(100);
	}
	
	

	

}

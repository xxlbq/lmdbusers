package com.livedoor.dbm.components.mainframe.createdt.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.livedoor.dbm.components.common.DBMTable;
import com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel;

/**
* <p>
* Title:创建和修改表用的主要属性Table
* <p>
* Description:创建和修改表用的主要属性Table
* <p>
* Copyright: Copyright (c) 2006-10-18
* <p>
* Company: 英極軟件開發（大連）有限公司
* 
* @author WangHuiTang
* @version 1.0
*/
public class MasterJTable extends DBMTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3447335103713948501L;
	JComboBox types;

	/* (non-Javadoc)
	 * @see javax.swing.JTable#getCellEditor(int, int)
	 */
	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		if (column == 1)
			return new DefaultCellEditor(types);
		else
			return super.getCellEditor(row, column);
	}


	/**
	 * @param model
	 */
	public MasterJTable(AbsMasterTableModel model) {
		super();
		this.setModel(model);
		types = new JComboBox();
		Object[] typeNames = model.getAllTypeName();
		for (int i = 0; i < typeNames.length; i++)
			types.addItem(typeNames[i]);
		
		getColumnModel().getColumn(0).setPreferredWidth(100);
		getColumnModel().getColumn(1).setPreferredWidth(100);
		getColumnModel().getColumn(2).setPreferredWidth(80);
		getColumnModel().getColumn(3).setPreferredWidth(100);
		getColumnModel().getColumn(4).setPreferredWidth(120);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer, int, int)
	 */
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {

		Component c = renderer.getTableCellRendererComponent(this, this
				.getValueAt(row, column), false, true, row, column);


		AbsMasterTableModel model = (AbsMasterTableModel) this
					.getModel();
			Color fg = model.getColorAt(row, column);
			if (fg != null)
				c.setForeground(fg);
			else
				c.setForeground(Color.BLACK);
			c.setBackground(Color.WHITE);
		return super.prepareRenderer(renderer, row, column);
	}

}

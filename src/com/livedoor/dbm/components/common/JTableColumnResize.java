package com.livedoor.dbm.components.common;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
* <p>
* Title:表格控制
* <p>
* Description:表格控制
* <p>
* Copyright: Copyright (c) 2006-10-20
* <p>
* Company: 英極軟件開發（大連）有限公司
* 
* @author WangHuiTang
* @version 1.0
*/
public class JTableColumnResize {

	/**
	 * 调整表格列的宽度,以适应表格里的数据
	 * 必需在JTable设置好TableModel之后,调用该方法
	 * @param table 要调整列完的表格的引用
	 */
	public static void initColumnSize(JTable table){
		TableColumn[] tableColumns = new TableColumn[table.getColumnCount()];
		for (int i = 0; i < tableColumns.length; i++) {
			tableColumns[i] = table.getColumn(table.getColumnName(i));
			int width = getPreferredWidthForColumn(tableColumns[i],table);
			tableColumns[i].setPreferredWidth(width + 25);
		}
		table.doLayout();

	}
	private static int getPreferredWidthForColumn(TableColumn column,JTable table) {
		int hw = columnHeaderWidth(column,table), cw = widestCellnColumn(column,table);
		return hw > cw ? hw : cw;
	}

	private static int widestCellnColumn(TableColumn column,JTable table) {
		int columnIndex = column.getModelIndex(), width = 0, maxw = 0;
		for (int rowIndex = 0; rowIndex < table.getRowCount(); ++rowIndex) {
			TableCellRenderer renderer = table.getCellRenderer(rowIndex, columnIndex);
			Component comp = renderer.getTableCellRendererComponent(table, table.getValueAt(rowIndex, columnIndex),
					false, false, rowIndex, columnIndex);
			width = comp.getPreferredSize().width;
			maxw = width > maxw ? width : maxw;

		}
		return maxw;
	}

	private static int columnHeaderWidth(TableColumn column,JTable table) {
		TableCellRenderer renderer = column.getHeaderRenderer();
		if(renderer == null){
			renderer = table.getDefaultRenderer(column.getClass());
		}
		Component comp = renderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, 0);
		return comp.getPreferredSize().width;
	}
	
	
}

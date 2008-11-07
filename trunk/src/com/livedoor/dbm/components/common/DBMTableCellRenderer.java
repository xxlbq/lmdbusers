package com.livedoor.dbm.components.common;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class DBMTableCellRenderer extends DefaultTableCellRenderer {
	//当前的像素大小
	int fontPixel = 0;
	Map colMaxSize = new HashMap(); 
	public DBMTableCellRenderer(JTable table){
		fontPixel = table.getFont().getSize();
		
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if(value != null){
			setText(value.toString());
			setValue(value);
			//取cell大小
			int cellSize = table.getCellRect(row,column,false).width;
			//取当前字符串的大小
			int valueSize = value.toString().length()*fontPixel>cellSize?value.toString().length()*fontPixel:cellSize;
			String key = ""+column;
			if(colMaxSize.containsKey(""+key)){
				int colMax = ((Integer)colMaxSize.get(key)).intValue();
				
				if(valueSize > colMax){
					colMaxSize.put(key,new Integer(valueSize));
				}else{
					valueSize = colMax;
				}
			}else{
				colMaxSize.put(key,new Integer(valueSize));
			}
			table.getColumnModel().getColumn(column).setPreferredWidth(valueSize);
			table.getColumnModel().getColumn(column).sizeWidthToFit();
			
		}
		
		return this;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

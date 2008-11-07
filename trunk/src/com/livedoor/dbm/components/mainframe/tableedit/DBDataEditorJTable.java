package com.livedoor.dbm.components.mainframe.tableedit;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.livedoor.dbm.components.common.DBMTable;
/**
 * <p>
 * Title: 编辑数据库表数据用的表格
 * <p>
 * Description: 编辑数据库表数据用的表格
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class DBDataEditorJTable extends DBMTable {
    @Override
    protected boolean processKeyBinding(KeyStroke keystroke, KeyEvent keyevent, int i, boolean flag) {
        int j = keyevent.getKeyCode();
        
        if(keyevent.isAltDown() && !keyevent.isControlDown() && !keyevent.isShiftDown()){
            //处理ALT_W,ALT_C,ALT_T事件
            if(j == KeyEvent.VK_W || j==KeyEvent.VK_C || j == KeyEvent.VK_T){
                return false;
            }
        }
        if( keyevent.isControlDown() && !keyevent.isShiftDown()){
            //处理CTRL-E,CTRL-ALT-E事件
            if(j == KeyEvent.VK_E){
                return false;
            }
        }
        return super.processKeyBinding(keystroke, keyevent, i, flag);
    }   

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {

		Component c = renderer.getTableCellRendererComponent(this, this
				.getValueAt(row, column), false, true, row, column);


			DBDataEditorJTableModel model = (DBDataEditorJTableModel) this
					.getModel();
			Color fg = model.getColorAt(row, column);
			if (fg != null)
				c.setForeground(fg);
			else
				c.setForeground(Color.BLACK);
			c.setBackground(Color.WHITE);
		return super.prepareRenderer(renderer, row, column);
	}

	public DBDataEditorJTable() {
		super();
	}

	public DBDataEditorJTable(TableModel dm) {
		super(dm,true);
	}

	public DBDataEditorJTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
	}

}

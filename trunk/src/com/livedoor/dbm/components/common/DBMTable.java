package com.livedoor.dbm.components.common;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * <p>
 * Description: DBMTable
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DBMTable extends JTable
 implements TableModelListener
{
	protected boolean processKeyBinding(KeyStroke keystroke, KeyEvent keyevent, int i, boolean flag) {
	        int j = keyevent.getKeyCode();
	        
	        if(!keyevent.isControlDown() && !keyevent.isAltDown() && !keyevent.isShiftDown()) {
	    
	        	if(j == 127){//delete key 
	                return false;
	        	}
	        } 
	        return super.processKeyBinding(keystroke, keyevent, i, flag);
	    }	
	/**
	 * [機 能] 构造 HeaderSelectListener 鼠标监听
	 * <p>
	 * [解 説] 构造 HeaderSelectListener 鼠标监听 。
	 * <p>
	 * 
	 * [備 考] なし
	 */	
 @SuppressWarnings("unused")
private class HeaderSelectListener
     implements MouseListener
 {

     private JTable _table;

     public void mouseEntered(MouseEvent mouseevent)
     {
     }

     public void mouseExited(MouseEvent mouseevent)
     {
     }

     public void mousePressed(MouseEvent mouseevent)
     {
     }

     public void mouseReleased(MouseEvent mouseevent)
     {
     }

     public void mouseClicked(MouseEvent mouseevent)
     {
         int i = convertColumnIndexToModel(getTableHeader().columnAtPoint(mouseevent.getPoint()));
         if(i == 0)
         {
             _table.setRowSelectionInterval(0, _table.getRowCount() - 1);
             _table.setColumnSelectionInterval(0, _table.getColumnCount() - 1);
         }
     }

     public HeaderSelectListener(JTable jtable)
     {
         _table = null;
         _table = jtable;
     }
 }
 /**
	 * [機 能] 构造 ArrowIcon 样式 大小等
	 * <p>
	 * [解 説] 构造 ArrowIcon 样式 大小等 。
	 * <p>
	 * 
	 * [備 考] なし
	 */
 class ArrowIcon
     implements Icon
 {

     public void paintIcon(Component component, Graphics g, int i, int j)
     {
         g.translate(i, j);
         g.setColor(Color.black);
         g.drawLine(0, 0, 0, 8);
         g.drawLine(1, 1, 1, 7);
         g.drawLine(2, 2, 2, 6);
         g.drawLine(3, 3, 3, 5);
         g.drawLine(4, 4, 4, 4);
         g.translate(-i, -j);
     }

     public int getIconWidth()
     {
         return 5;
     }

     public int getIconHeight()
     {
         return 9;
     }

     ArrowIcon()
     {
     }
 }

 class HeaderRenderer extends JLabel
     implements TableCellRenderer, ListSelectionListener
 {

     Icon icon;

     public Component getTableCellRendererComponent(JTable jtable, Object obj, boolean flag, boolean flag1, int i, int j)
     {
         setText(String.valueOf(i + 1));
         if(i == _selectedRow)
             setIcon(icon);
         else
             setIcon(null);
         return this;
     }

     public void valueChanged(ListSelectionEvent listselectionevent)
     {
    	 DBMTable dbmtable = DBMTable.this;
         @SuppressWarnings("unused")
		ListSelectionModel listselectionmodel = dbmtable.getSelectionModel();
         if(!listselectionevent.getValueIsAdjusting())
         {
             if(dbmtable.getSelectedRowCount() > 0)
                 _selectedRow = dbmtable.getSelectedRow();
             else
                 _selectedRow = -1;
             _rowHeaderTable.repaint();
         }
     }

     public HeaderRenderer()
     {
         icon = new ArrowIcon();
         setOpaque(true);
         setHorizontalAlignment(4);
         setBorder(new SoftBevelBorder(0));
         getSelectionModel().addListSelectionListener(this);
     }
 }

 private class RowHeaderTableModel extends AbstractTableModel
     implements TableModelListener
 {

     private TableModel _tableModel;

     public int getColumnCount()
     {
         return 0;
     }

     public int getRowCount()
     {
         if(getModel() != null)
             return _tableModel.getRowCount();
         else
             return 0;
     }

     public Object getValueAt(int i, int j)
     {
         return null;
     }

     public boolean isCellEditable(int i, int j)
     {
         return false;
     }

     public void tableChanged(TableModelEvent tablemodelevent)
     {
         fireTableChanged(new TableModelEvent(this));
     }

     public RowHeaderTableModel(TableModel tablemodel)
     {
         _tableModel = null;
         _tableModel = tablemodel;
     }
 }

 private class RowHeaderTable extends JTable
     implements ListSelectionListener
 {

     private DBMTable _table;
     private HeaderRenderer _rend;

     public void setFont(Font font)
     {
         super.setFont(font);
         if(_rend != null)
             _rend.setFont(font);
     }

     public void valueChanged(ListSelectionEvent listselectionevent)
     {
         if(!listselectionevent.getValueIsAdjusting())
         {
             int i = getSelectedRow();
             if(i >= 0 && i < _table.getRowCount())
             {
                 _table.setRowSelectionInterval(i, i);
                 _table.setColumnSelectionInterval(0, _table.getColumnCount() - 1);
                 _table.stopCellEditing();
                 if(_table.canSetFocusOnSelect())
                     _table.requestFocus();
             }
         }
     }

     public RowHeaderTable(DBMTable dbmtable1)
     {
         _table = null;
         _rend = new HeaderRenderer();
         _table = dbmtable1;
         setAutoCreateColumnsFromModel(false);
         setModel(new RowHeaderTableModel(dbmtable1.getModel()));
         setRowHeight(getRowHeight());
         setColumnSelectionAllowed(true);
         setRowSelectionAllowed(true);
         setCellSelectionEnabled(true);
         TableColumn tablecolumn = new TableColumn();
         tablecolumn.setResizable(false);
         _rend.setFont(getFont());
         tablecolumn.setCellRenderer(_rend);
         addColumn(tablecolumn);
         getSelectionModel().addListSelectionListener(this);
     }
 }


 protected RowHeaderTable _rowHeaderTable;
 protected boolean _showHeader;
 protected int _selectedRow;
 protected String _emptyStr;
 protected boolean _focusOnSelect;

 public DBMTable()
 {
     _rowHeaderTable = null;
     _showHeader = false;
     _selectedRow = -1;
     _emptyStr = null;
     _focusOnSelect = true;
//   禁止改变表头排列
     getTableHeader().setReorderingAllowed(false);
 }

 public DBMTable(boolean flag)
 {
     _rowHeaderTable = null;
     _showHeader = false;
     _selectedRow = -1;
     _emptyStr = null;
     _focusOnSelect = true;
     _showHeader = flag;
     setCellSelectionEnabled(true);
//   禁止改变表头排列
     getTableHeader().setReorderingAllowed(false);
 }

 public DBMTable(TableModel tablemodel, TableColumnModel tablecolumnmodel)
 {
     super(tablemodel, tablecolumnmodel);
     _rowHeaderTable = null;
     _showHeader = false;
     _selectedRow = -1;
     _emptyStr = null;
     _focusOnSelect = true;
//   禁止改变表头排列
     getTableHeader().setReorderingAllowed(false);
 }

 public DBMTable(TableModel tablemodel, boolean flag)
 {
     super(tablemodel);
     _rowHeaderTable = null;
     _showHeader = false;
     _selectedRow = -1;
     _emptyStr = null;
     _focusOnSelect = true;
     _showHeader = flag;
     setCellSelectionEnabled(true);
//   禁止改变表头排列
     getTableHeader().setReorderingAllowed(false);
 }

 public boolean canSetFocusOnSelect()
 {
     return _focusOnSelect;
 }

 public void setFocusOnSelect(boolean flag)
 {
     _focusOnSelect = flag;
 }

 public JTable getRowHeaderTable()
 {
     return _rowHeaderTable;
 }

 public void addNotify()
 {
     super.addNotify();
     if(getParent().getParent() instanceof JScrollPane)
     {
         JScrollPane jscrollpane = (JScrollPane)getParent().getParent();
         if(_showHeader = true)
         {
             _rowHeaderTable = new RowHeaderTable(this);
             _rowHeaderTable.setFont(getFont());
             jscrollpane.setRowHeaderView(_rowHeaderTable);
             JViewport jviewport = jscrollpane.getRowHeader();
             Dimension dimension = jviewport.getPreferredSize();
             TableModel tablemodel = getModel();
             tablemodel.addTableModelListener(_rowHeaderTable);
             int i = tablemodel.getRowCount();
             String s = String.valueOf(i);
             dimension.width = (s.length() + 1) * getFontMetrics(getFont()).charWidth('4') + 20;
             jviewport.setPreferredSize(dimension);
         } else
         {
             JViewport jviewport1 = jscrollpane.getRowHeader();
             Dimension dimension1 = jviewport1.getPreferredSize();
             dimension1.width = 0;
             jviewport1.setPreferredSize(dimension1);
         }
         jscrollpane.getViewport().setBackground(getBackground());
         jscrollpane.getColumnHeader().setBackground(getBackground());
         jscrollpane.getRowHeader().setBackground(getBackground());
     }
 }

 public void tableChanged(TableModelEvent tablemodelevent)
 {
     super.tableChanged(tablemodelevent);
     if(_rowHeaderTable != null)
         _rowHeaderTable.repaint();
 }

 public void setModel(TableModel tablemodel)
 {
    if(_rowHeaderTable != null){
         _rowHeaderTable.setModel(new RowHeaderTableModel(tablemodel));
         tablemodel.addTableModelListener(_rowHeaderTable);
    }
     super.setModel(tablemodel);
 
 }

 public int getCurrentRow()
 {
     return _selectedRow;
 }

 private void stopCellEditing()
 {
     TableCellEditor tablecelleditor = getCellEditor();
     if(tablecelleditor != null)
         tablecelleditor.stopCellEditing();
 }

 public void paint(Graphics g)
 {
     super.paint(g);
     if(getRowCount() == 0 && _emptyStr != null)
     {
         byte byte0 = 15;
         byte byte1 = byte0;
         int i = getFont().getSize() + byte0;
         Font font = new Font(getFont().getFamily(), 2, getFont().getSize());
         g.setFont(font);
         g.drawString(_emptyStr, byte1, i);
     }
 }

 public void setEmptyMessage(String s)
 {
     _emptyStr = s;
 }

 public String getEmptyMessage()
 {
     return _emptyStr;
 }

}

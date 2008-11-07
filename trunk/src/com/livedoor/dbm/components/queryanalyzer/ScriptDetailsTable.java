/**
 * $Id: ScriptDetailsTable.java,v 1.8 2006/11/17 08:01:43 lijc Exp $
 * 脚本执行明细显示
 */
package com.livedoor.dbm.components.queryanalyzer;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.plaf.UIResource;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.livedoor.dbm.components.common.DBMTable;
import com.livedoor.dbm.i18n.ResourceI18n;

/**
 * <p> Title: 显示脚本执行明细显示 </p> 
 * <p> Description: 显示脚本执行明细显示 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class ScriptDetailsTable extends DBMTable {

    private static final long serialVersionUID = 1L;
    
    // 错误图标,正确图标
    public static final ImageIcon ERROR_ICON = ResourceI18n.getImage("GEN_ERROR");
    public static final ImageIcon SUCCESS_ICON = ResourceI18n.getImage("GEN_SUCCESS");

    /**
     * 构造脚本明细显示
     * @param scripts SQL语句
     * @param sqlResults 执行结果
     */
    public ScriptDetailsTable() {
        setModel(new DBMDataModel());
        
        CustomHeaderRenderer renderer =  new CustomHeaderRenderer();
        Enumeration enumeration = getColumnModel().getColumns();
        while(enumeration.hasMoreElements()) { 
            TableColumn tablecolumn = (TableColumn)enumeration.nextElement();
            tablecolumn.setHeaderRenderer(renderer);
        }
        
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    /**
     * 显示脚本明细结果
     * @param icon
     * @param result 
     * @param script
     * @param message
     */
    public void appendRow(ImageIcon icon, String result, String script, String message) {
        DBMDataModel model = (DBMDataModel)getModel();
        model.appendRow(icon, result, script, message);
    }

    /** 
     * HeaderRenderer
     * 表中数据有图标时，显示在Tab中表头会被压扁，直接设置表头高度又会造成刷新异常。
     * @author LiJicheng
     */
    private class CustomHeaderRenderer extends DefaultTableCellRenderer implements UIResource {
        private static final long serialVersionUID = 1L;
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setBorder(BorderFactory.createEtchedBorder(getBackground(), Color.LIGHT_GRAY));
            setHorizontalAlignment(JLabel.CENTER);
            return this;
        }
    }
    
    /**
     * 表格数据模型
     * 
     * @author lijicheng
     */
    private class DBMDataModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;

        private String[] columnNames = {"", "Results", "SQL", "Message"};
        private List<Object[]> datas = new ArrayList<Object[]>();
        
        public int getRowCount() {
            return datas.size();
        }

        public int getColumnCount() {
            return 4;
        }

        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        public Class<?> getColumnClass(int columnIndex) {
            return getValueAt(0,columnIndex).getClass();
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            Object cell = datas.get(rowIndex)[columnIndex];
            return cell == null ? "" : cell;
        }
        
        public void appendRow(ImageIcon icon, String result, String script, String message) {
            Object[] row = {icon, result, script, message};
            datas.add(row);
        }
    }
}

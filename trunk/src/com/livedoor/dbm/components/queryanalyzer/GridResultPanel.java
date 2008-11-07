/**
 * $Id: GridResultPanel.java,v 1.7 2006/11/03 08:59:26 lijc Exp $
 * 显示脚本执行结果
 */
package com.livedoor.dbm.components.queryanalyzer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.livedoor.dbm.components.common.DBMTable;
import com.livedoor.dbm.components.common.JTableColumnResize;
import com.livedoor.dbm.constants.DBResultType;
import com.livedoor.dbm.db.DBColumn;
import com.livedoor.dbm.db.DBMDataResult;
import com.livedoor.dbm.db.DBMSqlResult;

/**
 * <p> Title: 显示脚本执行表格结果 </p> 
 * <p> Description: 显示脚本执行表格结果 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class GridResultPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    //表格高度
    private static final int TABLE_HEIGHT = 100;
    
    // 表格个数
    private int gridCount = 0;
    
    public GridResultPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    public void displayResults(DBMSqlResult[] results) {
        for (DBMSqlResult result : results) {
            displayResult(result);
        }
    }
    
    public void displayResult(DBMSqlResult result) {
        if (result.getType() == DBResultType.MUTI_ROWS || 
                result.getType() == DBResultType.MUTI_RES) {
            JTable table = new DataResultTable(result.getDBMDataResult());
            JTableColumnResize.initColumnSize(table);
            JScrollPane scroll = new JScrollPane(table);
            add(scroll);
            gridCount++;
        }
    }
    
    /**
     * @return 表格个数
     */
    public int getGridCount() {
        return gridCount;
    }
    
    /**
     * 结果表格
     * @author lijicheng
     */
    private class DataResultTable extends DBMTable {
        private static final long serialVersionUID = 1L;
        
        private DataResultHelper drHelper;
        private JTable table = this;
        
        public DataResultTable(DBMDataResult data) {
            setPreferredScrollableViewportSize(new Dimension(0, TABLE_HEIGHT));
            this.drHelper = new DataResultHelper(data);
            
            setModel(new DBMDataModel(data));
            setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
        
        /**
         * 表格数据模型
         * @author lijicheng
         */
        private class DBMDataModel extends AbstractTableModel {
            private List<DBColumn> cols;
            private List<Map> datas;
        
            public DBMDataModel(DBMDataResult data) {
                this.cols = data.getColumns();
                this.datas = data.getData();
            }
        
            public int getRowCount() {
                return datas.size();
            }
            
            public int getColumnCount() {
                return cols.size();
            }
            
            public String getColumnName(int columnIndex) {
                DBColumn col = cols.get(columnIndex);
                return col.getColumnName();
            }
            
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
            
            public Object getValueAt(int rowIndex, int columnIndex) {
                Component cell = getCellRenderer(rowIndex, columnIndex).
                        getTableCellRendererComponent(table, null, false, false, rowIndex, columnIndex);
                if (rowIndex % 2 == 0)
                    cell.setBackground(Color.LIGHT_GRAY);
                else
                    cell.setBackground(Color.WHITE);
                    
                String columnName = getColumnName(columnIndex);
                Map data = datas.get(rowIndex);
                return drHelper.convert(data.get(columnName));
            }
        }
    }    
}



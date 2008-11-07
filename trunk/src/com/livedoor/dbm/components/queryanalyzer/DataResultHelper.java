package com.livedoor.dbm.components.queryanalyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.livedoor.dbm.db.DBColumn;
import com.livedoor.dbm.db.DBMDataResult;

public class DataResultHelper {
    
    private DBMDataResult data;
    private Map<String, Integer> colsWidth; 
    
    public DataResultHelper(DBMDataResult data) {
        this.data = data;
    }

    public int getColumnWidth(String columnName) {
        if(colsWidth==null) 
            colsWidth = getColsWidth();
        
        return colsWidth.get(columnName);
    }
    
    /**
     * 取列显示宽
     * @param data
     * @return
     */
    private Map<String, Integer> getColsWidth() {
        Map<String, Integer> colsWidth = new HashMap<String, Integer>();

        List<DBColumn> cols = data.getColumns();
        List<Map> datas = data.getData();

        // 先处理列名,结果集中可能没数据
        for (DBColumn col : cols) {
            colsWidth.put(col.getColumnName(), col.getColumnName().length());
        }

        // 后处理数据
        for (Map data : datas) {
            for (DBColumn col : cols) {
                Object _colValue = data.get(col.getColumnName());
                String colValue = convert(_colValue);
                Integer len1 = colsWidth.get(col.getColumnName());
                int len2 = colValue.length();
                int len = Math.max(len1, len2);
                colsWidth.put(col.getColumnName(), len);
            }
        }

        return colsWidth;
    }
    
    /**
     * 转换数据对象为字符串
     * 
     * @param value
     * @return
     */
    public String convert(Object value) {
        if (value == null)
            return "(null)";

        if (value instanceof java.sql.Blob || value instanceof java.sql.Clob) {
            return "object";
        }

        return value.toString();
    }

}

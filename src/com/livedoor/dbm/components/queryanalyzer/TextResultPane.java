/**
 * $Id: TextResultPane.java,v 1.18 2006/11/20 09:06:16 lijc Exp $ 
 * 显示脚本执行结果
 */
package com.livedoor.dbm.components.queryanalyzer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.queryanalyzer.file.DBMFileChooser;
import com.livedoor.dbm.components.queryanalyzer.file.DBMFileChooserCreator;
import com.livedoor.dbm.components.queryanalyzer.file.ScriptFile;
import com.livedoor.dbm.components.queryanalyzer.find.LastComponentRef;
import com.livedoor.dbm.constants.DBResultType;
import com.livedoor.dbm.db.DBColumn;
import com.livedoor.dbm.db.DBMDataResult;
import com.livedoor.dbm.db.DBMSqlResult;
import com.livedoor.dbm.util.StringUtil;

/**
 * <p> Title: 显示脚本执行文本结果 </p> 
 * <p> Description: 显示脚本执行文本结果 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class TextResultPane extends JTextPane {

    private static final long serialVersionUID = 1L;
    private static int MAX_DATA_BUFFER = 32000;
    private static int MAX_DATA_SATURATION = 28000;
    // 终止当前输出
    private boolean cancel = false;

    // 已输出行数
    private int displayedRowCount;
    private StringBuffer dataOutput;

    // 创建文件选择对话框
    private DBMFileChooserCreator creator; 

    public TextResultPane() {
        dataOutput = new StringBuffer(MAX_DATA_BUFFER);
        addFocusListener(new LastComponentRef());
    }

    /**
     * 为显示水平滚动条 JEditorPane水平滚动条不能出来
     */
    public boolean getScrollableTracksViewportWidth() {
        return (getSize().width < getParent().getSize().width);
    }

    /**
     * 为显示水平滚动条 JEditorPane水平滚动条不能出来
     */
    public void setSize(Dimension d) {
        if (d.width < getParent().getSize().width) {
            d.width = getParent().getSize().width;
        }
        super.setSize(d);
    }

    /**
     * 显示执行结果
     * @param results 执行结果数组
     */
    public void displayResults(DBMSqlResult[] results) {
        if (results == null)
            return;

        for (DBMSqlResult result : results) {
            displayResult(result);
            displayNormalText("\n");
        }
    }

    /**
     * 显示执行结果
     * @param result 执行结果
     */
    public void displayResult(DBMSqlResult result) {
        String _result = null;
        switch (result.getType()) {
            case DBResultType.ERROR :
                _result = result.getMessage() + "\n";
                displayErrorText(_result);
                break;
            case DBResultType.ZERO_ROWS :
                _result = result.getMessage() + "\n";
                displayNormalText(_result);
                break;
            case DBResultType.MUTI_ROWS :
                displayDataResult(result.getDBMDataResult());
                break;
            case DBResultType.MUTI_RES :
                displayNormalText(result.getMessage() + "\n");
                displayDataResult(result.getDBMDataResult());
                break;
        }

        displayNormalText("\n");
    }

    /**
     * 显示错误文本,错误执行结果显示红色
     * @param _result
     */
    private void displayErrorText(String _result) {
        _result = "<ERROR>:" + _result;
        int offset = getDocument().getLength();
        try {
            getDocument().insertString(offset, _result, errorAttr);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示正常文本
     * @param _result
     */
    private void displayNormalText(String _result) {
        int offset = getDocument().getLength();
        try {
            getDocument().insertString(offset, _result, normalAttr);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        this.cancel = true;
    }

    /**
     * 显示结果集
     */
    private void displayDataResult(DBMDataResult dataResult) {
        List<DBColumn> cols = dataResult.getColumns();
        List<Map> datas = dataResult.getData();
        //结果集显示帮助类
        DataResultHelper drHelper = new DataResultHelper(dataResult);

        // 输出列名
        for (DBColumn col : cols) {
            displayNormalText(StringUtil.rightPad(col.getColumnName(), drHelper.getColumnWidth(col.getColumnName())));
            displayNormalText("\t");
        }
        displayNormalText("\n");

        // 输出横线区分列名和数据
        for (DBColumn col : cols) {
            displayNormalText(StringUtil.fillString('-', drHelper.getColumnWidth(col.getColumnName())));
            displayNormalText("\t");
        }
        displayNormalText("\n");

        // 输出数据
        for (Map data : datas) {
            if (cancel)
                break;

            for (DBColumn col : cols) {
                Object _colValue = data.get(col.getColumnName());
                String colValue = drHelper.convert(_colValue);
                dataOutput.append(StringUtil.rightPad(colValue, drHelper.getColumnWidth(col.getColumnName())));
                dataOutput.append("\t");
            }

            dataOutput.append("\n");
            if (dataOutput.length() > MAX_DATA_SATURATION) {
                displayNormalText(dataOutput.toString());
                dataOutput.setLength(0);
            }

            displayedRowCount++;
        }
        displayNormalText(dataOutput.toString());
        dataOutput.setLength(0);
    }

    /**
     * @return 已显示记录条数
     */
    public int getDisplayedRowCount() {
        return displayedRowCount;
    }

    /**
     * 设置displayedRowCount为0
     */
    public void resetDisplayedRowCount() {
        this.displayedRowCount = 0;
    }

    /**
     * 保存脚本执行结果
     * @param parent 
     */
    public void save(Component parent) {
        if (creator == null)
            creator = new DBMFileChooserCreator();
        DBMFileChooser chooser = creator.getTextFileChooser();
        int returnVal = chooser.showSaveDialog(parent);
        if (returnVal != JFileChooser.APPROVE_OPTION)
            return;

        String fullName = ScriptFile.appendExtension(chooser.getPath(), chooser.getExtension());
        String charset = chooser.getCharset();
        try {
            ScriptFile.save(fullName, getText(), charset);
        } catch (Exception e) {
            e.printStackTrace();
            DBMMessageDialog.showErrorMessageDialog(e.getMessage());
        }
    }

    // 正常文本属性,错误文本属性
    private static SimpleAttributeSet normalAttr = new SimpleAttributeSet();
    private static SimpleAttributeSet errorAttr = new SimpleAttributeSet();
    static {
        StyleConstants.setFontFamily(normalAttr, "Monospaced");
        StyleConstants.setFontSize(normalAttr, 12);
        StyleConstants.setBackground(normalAttr, Color.white);
        StyleConstants.setForeground(normalAttr, Color.black);
        StyleConstants.setBold(normalAttr, false);
        StyleConstants.setItalic(normalAttr, false);

        StyleConstants.setFontFamily(errorAttr, "Monospaced");
        StyleConstants.setFontSize(errorAttr, 12);
        StyleConstants.setBackground(errorAttr, Color.white);
        StyleConstants.setForeground(errorAttr, Color.red);
        StyleConstants.setBold(errorAttr, false);
        StyleConstants.setItalic(errorAttr, false);
    }

}

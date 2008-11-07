/**
 * $Id: DBMFileChooser.java,v 1.2 2006/10/30 08:41:12 lijc Exp $
 * 查询分析器文件操作
 */
package com.livedoor.dbm.components.queryanalyzer.file;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.livedoor.dbm.i18n.ResourceI18n;

/**
 * <p> Title: 脚本文件选择器 </p> 
 * <p> Description: 文件选择器，和JFileChooser相同，只是增加了过虑器 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class DBMFileChooser extends JFileChooser {
    
    private static final long serialVersionUID = 1L;

    private static final String FILECHOOSER_BUTTON_NEW = "FILECHOOSER_BUTTON_NEW";
    private static final String FILECHOOSER_TITLE_NEW = "FILECHOOSER_TITLE_NEW";
    private static final String FILECHOOSER_TITLE_SAVEAS = "FILECHOOSER_TITLE_SAVEAS";
    
    /**
     * 显示新建对话框
     */
    public int showNewDialog(Component parent) {
        setApproveButtonText(ResourceI18n.getText(FILECHOOSER_BUTTON_NEW));
        setDialogTitle(ResourceI18n.getText(FILECHOOSER_TITLE_NEW));
        setApproveButtonMnemonic('N');
        return showOpenDialog(parent);
    }
    
    /**
     * 显示另存为对话框
     */
    public int showSaveAsDialog(Component parent) {
        setDialogTitle(ResourceI18n.getText(FILECHOOSER_TITLE_SAVEAS));
        return showSaveDialog(parent);
    }
    
    /**
     * @return 返回选中文件的路径
     */
    public String getPath() {
        File selectedFile = getSelectedFile();
        if(selectedFile!=null)
            return selectedFile.getPath();
            
         return null;
    }
    
    /**
     * @return 返回选中文件的文件名
     */
    public String getName() {
        File selectedFile = getSelectedFile();
        if(selectedFile!=null)
            return selectedFile.getName();
            
         return null;
    }
    
    /**
     * @return 返回选中文件过虑器的接受文件扩展名
     */
    public String getExtension() {
        FileFilter filter = getFileFilter();
        if(filter!=null && (filter instanceof DBMFileFilter)) {
            return ((DBMFileFilter)filter).getExtension();
        }
     
        return null;
    }
    
    /**
     * @return 返回选中文件过虑器指定的字符集
     */
    public String getCharset() {
        FileFilter filter = getFileFilter();
        if(filter!=null && (filter instanceof DBMFileFilter)) {
            return ((DBMFileFilter)filter).getCharset();
        }
        
        return null;
    }
}

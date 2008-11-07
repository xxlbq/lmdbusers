/**
 * $Id: DBMFileFilter.java,v 1.2 2006/10/18 08:45:03 lijc Exp $
 * 查询分析器文件操作
 */
package com.livedoor.dbm.components.queryanalyzer.file;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * <p> Title: 文件过虑器 </p> 
 * <p> Description: 文件过虑器，文件过虑器实现,当encoding不为空时,使用此编码操作文件 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class DBMFileFilter extends FileFilter {

    private String extension;
    private String description;
    private String charset;

    /**
     * 构造默认扩展名文件过滤器,默认扩展名为"sql"
     * @param desctiption 过滤器描述
     * @param charset 文件编码
     */
    public DBMFileFilter(String description, String charset) {
        this(".sql", description, charset);
    }
    
    /**
     * 构造脚本文件过滤器
     * @param extension 扩展名
     * @param desctiption 过滤器描述
     * @param charset 文件编码
     */
    public DBMFileFilter(String extension, String desctiption, String charset) {
        this.extension = extension.toLowerCase();
        this.description = desctiption;
        this.charset = charset;
    }
    
    /**
     * 过滤文件
     */
    @Override
    public boolean accept(File f) {
        if(f.isDirectory()) 
            return true;
        if(f.isFile()) {
            String fileName = f.getName().toLowerCase();
            if(fileName.endsWith(extension))
                return true;
        }
        return false;
    }
    
    /**
     * @return 返回过滤器接受文件扩展名
     */
    public String getExtension() {
        return extension;
    }
    
    /**
     * 过滤器描述
     */
    @Override
    public String getDescription() {
        return description;
    }
    
    /**
     * 返回编码
     * 在保存和打开文件时，可以获取编码，用对应编码操作文件
     * @return
     */
    public String getCharset() {
        return this.charset;
    }

}

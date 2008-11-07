/**
 * $Id: DBMFileChooserCreator.java,v 1.1 2006/10/30 02:15:57 lijc Exp $
 * 文件选择器相关
 */
package com.livedoor.dbm.components.queryanalyzer.file;

/**
 * <p> Title: 文件选择器创建器 </p> 
 * <p> Description: 文件选择器创建器,为保存脚本和保存结果创建不同文件选择器 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class DBMFileChooserCreator {

    public DBMFileChooser getScriptFileChooser() {
        DBMFileChooser chooser = new DBMFileChooser();
        
        // 默认编码脚本文件过虑器
        DBMFileFilter defFilter = new DBMFileFilter("SQL Scripts - (Default Encoding)", null);
        chooser.addChoosableFileFilter(defFilter);

        DBMFileFilter filter = null;
        filter = new DBMFileFilter("SQL Scripts - Big5(Trandition Chinese)", "big5"); 
        chooser.addChoosableFileFilter(filter);
        
        filter = new DBMFileFilter("SQL Scripts - GBK(GBK, Simplified Chinese)", "gbk"); 
        chooser.addChoosableFileFilter(filter);
        
        filter = new DBMFileFilter("SQL Scripts - GB18030(Simplified Chinese, PRC standard)", "gb18030"); 
        chooser.addChoosableFileFilter(filter);
        
        filter = new DBMFileFilter("SQL Scripts - UTF-8(8-bit Unicode Transformation Format)", "utf8"); 
        chooser.addChoosableFileFilter(filter);

        filter = new DBMFileFilter("SQL Scripts - SJIS - (Shift-JIS,Japanese)", "shift-jis"); 
        chooser.addChoosableFileFilter(filter);

        filter = new DBMFileFilter("SQL Scripts - EUC_JP - (JISX 0201,0208 and 0212, EUC encoding Japanese)", "euc-jp"); 
        chooser.addChoosableFileFilter(filter);
        
        chooser.setFileFilter(defFilter);
        return chooser;
    }
    
    public DBMFileChooser getTextFileChooser() {
        DBMFileChooser chooser = new DBMFileChooser();
        
        // 默认编码脚本文件过虑器
        DBMFileFilter defFilter = new DBMFileFilter(".txt", "Text - (Default Encoding)", null);
        chooser.addChoosableFileFilter(defFilter);

        DBMFileFilter filter = null;
        filter = new DBMFileFilter(".txt", "Text - Big5(Trandition Chinese)", "big5"); 
        chooser.addChoosableFileFilter(filter);
        
        filter = new DBMFileFilter(".txt", "Text - GBK(GBK, Simplified Chinese)", "gbk"); 
        chooser.addChoosableFileFilter(filter);
        
        filter = new DBMFileFilter(".txt", "Text - GB18030(Simplified Chinese, PRC standard)", "gb18030"); 
        chooser.addChoosableFileFilter(filter);
        
        filter = new DBMFileFilter(".txt", "Text - UTF-8(8-bit Unicode Transformation Format)", "utf8"); 
        chooser.addChoosableFileFilter(filter);

        filter = new DBMFileFilter(".txt", "Text - SJIS - (Shift-JIS,Japanese)", "shift-jis"); 
        chooser.addChoosableFileFilter(filter);

        filter = new DBMFileFilter(".txt", "Text - EUC_JP - (JISX 0201,0208 and 0212, EUC encoding Japanese)", "euc-jp"); 
        chooser.addChoosableFileFilter(filter);
        
        chooser.setFileFilter(defFilter);
        return chooser;        
    }
}

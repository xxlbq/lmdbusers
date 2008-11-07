/**
 * $Id: SQLFormatter.java,v 1.2 2006/10/17 11:02:30 lijc Exp $ 
 * SQL格式化
 */
package com.livedoor.dbm.components.queryanalyzer.util;

/**
 * <p> Title: SQL格式化 </p> 
 * <p> Description: 格式化SQL </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class SQLFormatter {

    private final String statementSep = "go";

    /**
     * SQL注释规则
     */
    private final CommentSpec[] commentSpecs = new CommentSpec[]{
            new CommentSpec("/*", "*/"), 
            new CommentSpec("--", "\n")};

    /**
     * 格式化
     * 
     * @param sqlText 待格式化SQL
     * @return
     */
    public String format(String sqlText) {

        CodeReformator cr = new CodeReformator(statementSep, commentSpecs);
        String formatedText = cr.reformat(sqlText);

        return formatedText;
    }

}

/**
 * $Id: CommentSpec.java,v 1.1 2006/10/17 01:10:37 lijian Exp $ 
 * 格式化
 */
package com.livedoor.dbm.components.queryanalyzer.util;

/**
 * <p> Title: 注释规则 </p> 
 * <p> Description: 注释规则 注释规则有注释开始和注释结束两部分, 
 *     比如: 单行注释以"--"开始,以"\n"结束 </p> 
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class CommentSpec {
    public String commentBegin;
    public String commentEnd;

    /**
     * @param commentBegin 注释开始串
     * @param commentEnd 注释结束串
     */
    public CommentSpec(String commentBegin, String commentEnd) {
        this.commentBegin = commentBegin;
        this.commentEnd = commentEnd;
    }
}

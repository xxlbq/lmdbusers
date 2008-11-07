/**
 * $Id: FindOption.java,v 1.1 2006/10/17 01:10:36 lijian Exp $ 
 * 在编辑器中查找文本
 */
package com.livedoor.dbm.components.queryanalyzer.find;

/**
 * <p> Title: 查找选项 </p> 
 * <p> Description: 查找选项 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class FindOption {

    FindOption() {
    }

    // 要查找的文本
    private String findWhat;

    // 要替换的文本
    private String replaceWith;

    // 是否增量查找
    private boolean incSearch = false;

    // 是否匹配大小写
    private boolean matchCase = false;

    // 是否全字匹配
    private boolean wholeWords = false;

    // 是否反方向查找
    private boolean bwdSearch = false;

    // 是否循环查找
    private boolean wrapSearch = false;

    public boolean isBwdSearch() {
        return bwdSearch;
    }
    public void setBwdSearch(boolean bwdSearch) {
        this.bwdSearch = bwdSearch;
    }
    public boolean isIncSearch() {
        return incSearch;
    }
    public void setIncSearch(boolean incSearch) {
        this.incSearch = incSearch;
    }
    public boolean isMatchCase() {
        return matchCase;
    }
    public void setMatchCase(boolean matchCase) {
        this.matchCase = matchCase;
    }
    public boolean isWholeWords() {
        return wholeWords;
    }
    public void setWholeWords(boolean wholeWords) {
        this.wholeWords = wholeWords;
    }
    public boolean isWrapSearch() {
        return wrapSearch;
    }
    public void setWrapSearch(boolean wrapSearch) {
        this.wrapSearch = wrapSearch;
    }
    public String getFindWhat() {
        return findWhat;
    }
    public void setFindWhat(String findWhat) {
        this.findWhat = findWhat;
    }
    public String getReplaceWith() {
        return replaceWith;
    }
    public void setReplaceWith(String replaceWith) {
        this.replaceWith = replaceWith;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder().
            append("FindOptions[").
            append("findWhat=").append(findWhat).append(",").
            append("replaceWith=").append(replaceWith).append(",").
            append("incSearch=").append(incSearch).append(",").
            append("matchCase=").append(matchCase).append(",").
            append("wholeWords=").append(wholeWords).append(",").
            append("bwdSearch=").append(bwdSearch).append(",").
            append("wrapSearch=").append(wrapSearch).
            append("]");
        return sb.toString();
    }
}

/**
 * $Id: Finder.java,v 1.1 2006/10/17 01:10:36 lijian Exp $ 
 * 在编辑器中查找文本
 */
package com.livedoor.dbm.components.queryanalyzer.find;

/**
 * <p> Title: 查找 </p> 
 * <p> Description: 在字符数组中查找首次出现的字符串 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public interface Finder {

    /**
     * 复位 Finder实现类中索引复位
     */
    public void reset();

    /** 查找
     * @param bufferStartPos
     * @param buffer 查找缓冲区
     * @param offset1 查找区域开始位置
     * @param offset2 查找区域结束位置
     * @param reqPos 查找开始位置
     * @param limitPos 限制位置,如果向前查找,限制位置为查找区结束位置,如果向后查找,限制位置为查找区开始位置
     * @return 如果查找到,返回匹配位置
     */
    public int find(int bufferStartPos, char buffer[], int offset1, int offset2, int reqPos, int limitPos);

    /**
     * 是否查找到
     * 
     * @return
     */
    public boolean isFound();

}

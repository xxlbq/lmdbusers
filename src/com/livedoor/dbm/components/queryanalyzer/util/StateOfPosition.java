/**
 * $Id: StateOfPosition.java,v 1.1 2006/10/17 01:10:37 lijian Exp $ 
 * SQL格式化
 */
package com.livedoor.dbm.components.queryanalyzer.util;

/**
 * <p> Title: SQL脚本中每个字符的位置状态 </p> 
 * <p> Description: SQL脚本中每个字符的位置状态 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class StateOfPosition {

    /**
     * 是否最上层 没在括号中,没在注释中,没在单引号中就是最上层
     */
    boolean isTopLevel;

    // 注释在SQL脚本中的位置
    int commentIndex = -1;

    // 字面文本分隔符个数
    int literalSepCount = 0;

    // 括号深度
    int braketDepth = 0;

    public Object clone() {
        StateOfPosition ret = new StateOfPosition();
        ret.commentIndex = commentIndex;
        ret.literalSepCount = commentIndex;
        ret.braketDepth = braketDepth;
        ret.isTopLevel = isTopLevel;

        return ret;
    }
}

/**
 * $Id: FindState.java,v 1.1 2006/10/17 01:10:36 lijian Exp $ 
 * 在编辑器中查找文本
 */
package com.livedoor.dbm.components.queryanalyzer.find;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Title: 查找/替换历史状态维护 </p> 
 * <p> Description: 查找/替换历史状态维护 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class FindState {

    private static List<Object> findHistories = new ArrayList<Object>();
    private static List<Object> replaceHistories = new ArrayList<Object>();

    /**
     * 不需要实例化
     */
    private FindState() {
    }

    /**
     * 更新查找历史
     * 
     * @param item
     */
    public static void updateFindHistory(Object item) {
        updateHistory(findHistories, item);
    }

    /**
     * 取出查找历史
     * @return
     */
    public static List getFindHistories() {
        return findHistories;
    }

    /**
     * 更新替换历史
     * @param item
     */
    public static void updateReplaceHistory(Object item) {
        updateHistory(replaceHistories, item);
    }

    /**
     * 取出替换历史
     * @return
     */
    public static List getReplaceHistories() {
        return replaceHistories;
    }

    /**
     * 更新历史
     * @param histories
     * @param item
     */
    private static void updateHistory(List<Object> histories, Object item) {
        if (item != null) {
            int index = histories.indexOf(item);
            if (index >= 0) {
                histories.remove(index);
            }
            histories.add(0, item);
        }
    }
}

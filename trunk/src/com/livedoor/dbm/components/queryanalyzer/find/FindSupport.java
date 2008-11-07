/**
 * $Id: FindSupport.java,v 1.8 2006/11/10 06:27:42 lijc Exp $ 
 * 在编辑器中查找文本
 */
package com.livedoor.dbm.components.queryanalyzer.find;

import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;

/**
 * <p> Title: 查找 </p> 
 * <p> Description: 在编辑区中执行查找/替换等,并在编辑区反映出来 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class FindSupport {
    // 根据查找选项构造合适的查找类
    private FinderCreator finderCreator;

    public FindSupport() {
        this.finderCreator = new FinderCreator();
    }

    /**
     * 查找
     * 
     * @param findOption
     * @return
     */
    public boolean find(FindOption findOption) {
        JTextComponent findFrom = LastComponentRef.getLastComponent();
        if(findFrom==null)
            return false;
        
        FinderCreator.StringFinder finder = finderCreator.createFinder(findOption);
        Caret caret = findFrom.getCaret();

        String text = findFrom.getText();
        int offset1 = 0, offset2 = 0, reqPos = 0, limitPos = 0;
        if (findOption.isBwdSearch()) { // 向后查找
            offset1 = 0;
            offset2 = caret.getDot();
            reqPos = caret.getDot() - 1;
            limitPos = 0;
        } else { // 向前查找
            offset1 = caret.getDot();
            offset2 = text.length();
            reqPos = caret.getDot();
            limitPos = text.length();
        }

        finder.reset();
        int pos = finder.find(0, text.toCharArray(), offset1, offset2, reqPos, limitPos);

        // 查找到,标记出匹配文本
        if (finder.isFound()) {
            String findWhat = findOption.getFindWhat();
            if (findOption.isBwdSearch()) { // 向后查找
                caret.setDot(pos + findWhat.length());
                caret.moveDot(pos);
            } else { // 向前查找
                caret.setDot(pos);
                caret.moveDot(pos + findWhat.length());
            }
            return true;
        }
        
        if (findOption.isWrapSearch()) {
            if (findOption.isBwdSearch())
                caret.setDot(text.length());
            else
                caret.setDot(0);
        }
        
        return false;
    }

    /**
     * 替换
     * 
     * @param findOption
     * @return
     */
    public boolean replace(FindOption findOption) {
        JTextComponent findFrom = LastComponentRef.getLastComponent();
        if(findFrom==null)
            return false;
        
        Caret caret = findFrom.getCaret();
        int pos = caret.getDot();
        
        String selection = findFrom.getSelectedText();
        String replaceWith = findOption.getReplaceWith();
        if (selection != null && selection.length() > 0) {
            findFrom.replaceSelection(replaceWith);
            
            if(findOption.isBwdSearch()) { //避免死循环
                caret.setDot(pos);
            }
            
            return true;
        }

        return false;
    }

    /**
     * 替换所有
     * 
     * @param findOption
     */
    public void replaceAll(FindOption findOption) {
        JTextComponent findFrom = LastComponentRef.getLastComponent();
        if(findFrom==null)
            return;
        
        Caret caret = findFrom.getCaret();
        if (findOption.isBwdSearch())
            caret.setDot(findFrom.getDocument().getLength());
        else
            caret.setDot(0);
        
        while (find(findOption)) {
            replace(findOption);
        }
    }

    /**
     * 增量查找,暂时没实现
     * 
     * @param findOption
     */
    public void incSearch(FindOption findOption) {
        System.out.println("incremental search, not completed.");
    }

}

/**
 * $Id: LastComponentRef.java,v 1.1 2006/11/10 06:27:42 lijc Exp $
 * 查找相关
 */
package com.livedoor.dbm.components.queryanalyzer.find;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.lang.ref.WeakReference;

import javax.swing.text.JTextComponent;

/**
 * <p> Title: 组件引用 </p> 
 * <p> Description: 引用最后获得焦点的JTextComponent,在查找时取用 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class LastComponentRef extends FocusAdapter {

    private static WeakReference<JTextComponent> ref;

    /**
     * 使用弱引用引用JTextComponent组件
     */
    public void focusGained(FocusEvent e) {
        Object src = e.getSource();
        if (src instanceof JTextComponent) {
            JTextComponent editor = (JTextComponent) src;
            ref = new WeakReference<JTextComponent>(editor);
        }
    }

    /**
     * 获取最后获得焦点的JTextComponent组件
     * @return
     */
    public static JTextComponent getLastComponent() {
        if (ref == null)
            return null;
        
        JTextComponent editor = ref.get();
        editor.requestFocus();
        return editor;
    }
}

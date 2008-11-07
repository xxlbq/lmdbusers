/**
 * $Id: WeakTimerListener.java,v 1.1 2006/10/17 01:10:36 lijian Exp $ 
 * 在编辑器中查找文本
 */
package com.livedoor.dbm.components.queryanalyzer.find;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ref.WeakReference;

import javax.swing.Timer;

/**
 * <p> Title: 查找对话框支持 </p>
 * <p> Description: 查找对话框支持,设置对话框按钮事件,显示对话框等 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class WeakTimerListener implements ActionListener {

    private WeakReference<ActionListener> ref;

    private boolean stopTimer;

    /**
     * 构造新timer监听器,并且执行后自动停止timer
     */
    public WeakTimerListener(ActionListener source) {
        this(source, true);
    }

    /**
     * 构造新的timer监听器
     * 
     * @param source 监听来源
     * @param stopTimer 是否在timer已经触发并且监听源已被垃圾回收器回收,停止timer
     */
    public WeakTimerListener(ActionListener source, boolean stopTimer) {
        this.ref = new WeakReference<ActionListener>(source);
        this.stopTimer = stopTimer;
    }

    public void actionPerformed(ActionEvent evt) {
        ActionListener src = (ActionListener) ref.get();
        if (src != null) {
            src.actionPerformed(evt);

        } else { // 监听源被垃圾回收
            if (evt.getSource() instanceof Timer) {
                Timer timer = (Timer) evt.getSource();
                timer.removeActionListener(this);

                if (stopTimer) {
                    timer.stop();
                }
            }
        }
    }

}
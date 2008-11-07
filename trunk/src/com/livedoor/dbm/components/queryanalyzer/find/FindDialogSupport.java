/**
 * $Id: FindDialogSupport.java,v 1.5 2006/11/10 06:27:42 lijc Exp $ 在编辑器中查找文本
 */
package com.livedoor.dbm.components.queryanalyzer.find;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.JTextComponent;

/**
 * <p> Title: 查找对话框支持 </p> 
 * <p> Description: 查找对话框支持,设置对话框按钮事件,显示对话框等 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class FindDialogSupport extends WindowAdapter implements ActionListener {

    private FindDialog findDialog;

    // 提供查找支持
    private FindSupport findSupport;

    // 增量查找使用,暂时没实现增量查找
    private Timer incSearchTimer;

    private static FindDialogSupport instance = null;
    
    private FindDialogSupport() {
        // int delay = ResourceI18n.getInt(FindDialog.FIND_INC_SEARCH_DELAY, 200);
        // incSearchTimer = new Timer(delay, new WeakTimerListener(this));
        // incSearchTimer.setRepeats(false);
    }

    public static FindDialogSupport getInstance() {
        if(instance==null)
            instance = new FindDialogSupport();
        return instance;
    }

    /**
     * 执行查找/替换/增量查找/取消等
     */
    public void actionPerformed(ActionEvent evt) {
        if (findSupport == null)
            findSupport = new FindSupport();
        FindOption findOption = findDialog.getFindOption();

        Object src = evt.getSource();
        if (src == findDialog.btnFind) { // 查找
            findDialog.updateFindHistory();
            if (findSupport.find(findOption)) {
            }

        } else if (src == findDialog.btnReplace) { // 替换
            findDialog.updateFindHistory();
            findDialog.updateReplaceHistory();

            if (findSupport.replace(findOption)) {
                findSupport.find(findOption);
            }

        } else if (src == findDialog.btnReplaceAll) {// 替换所有
            findDialog.updateFindHistory();
            findDialog.updateReplaceHistory();
            findSupport.replaceAll(findOption);

        } else if (src == findDialog.btnCancel) { // 取消
            findDialog.setVisible(false);
        } else if (src == incSearchTimer) { // 增量查找
            findSupport.incSearch(findOption);
        } 
    }

    /**
     * 显示查找/替换窗口
     */
    public synchronized void showFindDialog() {
        if (findDialog == null) {
            findDialog = new FindDialog();
            findDialog.setAlwaysOnTop(true);

            findDialog.btnFind.addActionListener(this);
            findDialog.btnReplace.addActionListener(this);
            findDialog.btnReplaceAll.addActionListener(this);
            findDialog.btnCancel.addActionListener(this);

            findDialog.addWindowListener(this);
        }

        findDialog.setVisible(true);
        updateFindWhat();
    }

    /**
     * 更新查找文本
     */
    private void updateFindWhat() {
        JTextComponent findFrom = LastComponentRef.getLastComponent();
        String selText = null;
        if (findFrom != null) {
            selText = findFrom.getSelectedText();
            if (selText != null) {
                int n = selText.indexOf('\n');
                if (n >= 0)
                    selText = selText.substring(0, n);
                findDialog.findWhat.getEditor().setItem(selText.trim());
            }
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                findDialog.findWhat.getEditor().getEditorComponent()
                        .requestFocus();
                findDialog.findWhat.getEditor().selectAll();
            }
        });
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // incSearchTimer.start();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // incSearchTimer.stop();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // incSearchTimer.stop();
    }
}

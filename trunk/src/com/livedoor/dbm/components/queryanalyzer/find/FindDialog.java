/**
 * $Id: FindDialog.java,v 1.3 2006/11/01 09:11:48 lijc Exp $ 
 * 在编辑器中查找文本
 */
package com.livedoor.dbm.components.queryanalyzer.find;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.livedoor.dbm.i18n.ResourceI18n;

/**
 * <p>Title: 查找对话框 </p>
 * <p>Description: 查找对话框</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class FindDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public static final String FIND_DIALOG_TITLE = "find-dialog-title";
    public static final String FIND_BUTTON_FIND = "find-button-find";
    public static final String FIND_BUTTON_REPLACE = "find-button-replace";
    public static final String FIND_BUTTON_REPLACE_ALL = "find-button-replace-all";
    public static final String FIND_BUTTON_CANCEL = "find-button-cancel";

    public static final String FIND_WHAT = "find-what";
    public static final String FIND_REPLACE_WITH = "find-replace-with";
    public static final String FIND_HIGHLIGHT_SEARCH = "find-highlight-search";
    public static final String FIND_INC_SEARCH = "find-inc-search";
    public static final String FIND_INC_SEARCH_DELAY = "find-inc-search-delay";
    public static final String FIND_BACKWARD_SEARCH = "find-backward-search";
    public static final String FIND_WRAP_SEARCH = "find-wrap-search";
    public static final String FIND_MATCH_CASE = "find-match-case";
    public static final String FIND_SMART_CASE = "find-smart-case";
    public static final String FIND_WHOLE_WORDS = "find-whole-words";
    public static final String FIND_REG_EXP = "find-reg-exp";
    public static final String FIND_HISTORY = "find-history";
    public static final String FIND_HISTORY_SIZE = "find-history-size";

    public FindDialog() {
        setTitle(ResourceI18n.getText(FIND_DIALOG_TITLE));

        findOption = new FindOption();
        iniComponents();
    }

    private void iniComponents() {
        JPanel findPanel = createFindPanel();
        getContentPane().add(findPanel, BorderLayout.WEST);

        JPanel buttonPanel = createButtonPanel();
        getContentPane().add(buttonPanel, BorderLayout.EAST);

        pack();
        setResizable(false);
    }

    // 创建查找面板
    private JPanel createFindPanel() {
        JPanel findPanel = new JPanel();

        findWhatPanel = new javax.swing.JPanel();
        findWhatLabel = new javax.swing.JLabel();
        findWhat = new javax.swing.JComboBox();
        replaceWithLabel = new javax.swing.JLabel();
        replaceWith = new javax.swing.JComboBox();
        incSearch = createCheckBox(FIND_INC_SEARCH, 'I');
        incSearch.setEnabled(false);
        matchCase = createCheckBox(FIND_MATCH_CASE, 'C');
        wholeWords = createCheckBox(FIND_WHOLE_WORDS, 'W');
        bwdSearch = createCheckBox(FIND_BACKWARD_SEARCH, 'B');
        wrapSearch = createCheckBox(FIND_WRAP_SEARCH, 'p');
        wrapSearch.setSelected(true);
        findOption.setWrapSearch(true);

        findPanel.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints1;

        findWhatPanel.setLayout(new java.awt.GridBagLayout());

        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 0;
        gridBagConstraints1.gridwidth = 3;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets(12, 0, 0, 0);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints1.weightx = 1.0;
        findPanel.add(findWhatPanel, gridBagConstraints1);

        findWhatLabel.setText(ResourceI18n.getText(FIND_WHAT));
        findWhatLabel.setLabelFor(findWhat);
        findWhatLabel.setDisplayedMnemonic('n');
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.insets = new java.awt.Insets(0, 12, 5, 0);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
        findPanel.add(findWhatLabel, gridBagConstraints1);

        findWhat.setEditable(true);
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets(0, 11, 9, 10);
        gridBagConstraints1.weightx = 1.0;
        findPanel.add(findWhat, gridBagConstraints1);

        replaceWithLabel.setText(ResourceI18n.getText(FIND_REPLACE_WITH));
        replaceWithLabel.setLabelFor(replaceWith);
        replaceWithLabel.setDisplayedMnemonic('l');
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 2;
        gridBagConstraints1.insets = new java.awt.Insets(0, 12, 9, 0);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
        findPanel.add(replaceWithLabel, gridBagConstraints1);

        replaceWith.setEditable(true);
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.gridy = 2;
        gridBagConstraints1.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets(0, 11, 9, 10);
        gridBagConstraints1.weightx = 1.0;
        findPanel.add(replaceWith, gridBagConstraints1);

        incSearch.setBorder(new javax.swing.border.EmptyBorder(
                new java.awt.Insets(1, 1, 1, 1)));
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 2;
        gridBagConstraints1.gridy = 3;
        gridBagConstraints1.insets = new java.awt.Insets(0, 11, 0, 10);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
        findPanel.add(incSearch, gridBagConstraints1);

        matchCase.setBorder(new javax.swing.border.EmptyBorder(
                new java.awt.Insets(1, 1, 1, 1)));
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.gridy = 3;
        gridBagConstraints1.insets = new java.awt.Insets(0, 11, 0, 0);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
        findPanel.add(matchCase, gridBagConstraints1);

        wholeWords.setBorder(new javax.swing.border.EmptyBorder(
                new java.awt.Insets(1, 1, 1, 1)));
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.gridy = 4;
        gridBagConstraints1.insets = new java.awt.Insets(0, 11, 0, 0);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
        findPanel.add(wholeWords, gridBagConstraints1);

        bwdSearch.setBorder(new javax.swing.border.EmptyBorder(
                new java.awt.Insets(1, 1, 1, 1)));
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 2;
        gridBagConstraints1.gridy = 4;
        gridBagConstraints1.insets = new java.awt.Insets(0, 11, 0, 10);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
        findPanel.add(bwdSearch, gridBagConstraints1);

        wrapSearch.setBorder(new javax.swing.border.EmptyBorder(
                new java.awt.Insets(1, 1, 1, 1)));
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 2;
        gridBagConstraints1.gridy = 5;
        gridBagConstraints1.insets = new java.awt.Insets(0, 11, 11, 10);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints1.weighty = 1.0;
        findPanel.add(wrapSearch, gridBagConstraints1);

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object src = e.getSource();
                if (src == findWhat)
                    findOption.setFindWhat((String) findWhat.getSelectedItem());
                if (src == replaceWith)
                    findOption.setReplaceWith((String) replaceWith
                            .getSelectedItem());
                if (src == incSearch)
                    findOption.setIncSearch(incSearch.isSelected());
                if (src == matchCase)
                    findOption.setMatchCase(matchCase.isSelected());
                if (src == wholeWords)
                    findOption.setWholeWords(wholeWords.isSelected());
                if (src == bwdSearch)
                    findOption.setBwdSearch(bwdSearch.isSelected());
                if (src == wrapSearch)
                    findOption.setWrapSearch(wrapSearch.isSelected());
            }
        };
        incSearch.addActionListener(al);
        matchCase.addActionListener(al);
        wholeWords.addActionListener(al);
        bwdSearch.addActionListener(al);
        wrapSearch.addActionListener(al);

        findWhat.addActionListener(al);
        replaceWith.addActionListener(al);

        return findPanel;
    }

    // 创建CheckBox
    private JCheckBox createCheckBox(String key, char mnemonic) {
        JCheckBox box = new JCheckBox(ResourceI18n.getText(key));
        box.setMnemonic(mnemonic);
        return box;
    }

    // 创建按钮面板
    private JPanel createButtonPanel() {
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(4, 1));

        btnFind = new JButton(ResourceI18n.getText(FIND_BUTTON_FIND));
        innerPanel.add(btnFind);

        btnReplace = new JButton(ResourceI18n.getText(FIND_BUTTON_REPLACE));
        innerPanel.add(btnReplace);

        btnReplaceAll = new JButton(ResourceI18n
                .getText(FIND_BUTTON_REPLACE_ALL));
        innerPanel.add(btnReplaceAll);

        btnCancel = new JButton(ResourceI18n.getText(FIND_BUTTON_CANCEL));
        innerPanel.add(btnCancel);

        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.add(innerPanel, BorderLayout.NORTH);
        return jPanel;
    }

    /**
     * 取对话框查找选项
     * 
     * @return
     */
    public FindOption getFindOption() {
        return findOption;
    }

    /**
     * 设置对话框查找选项
     * 
     * @param findOption
     */
    public void setFindOption(FindOption findOption) {
        this.findOption = findOption;
        this.matchCase.setSelected(findOption.isMatchCase());
        this.wholeWords.setSelected(findOption.isWholeWords());
        this.bwdSearch.setSelected(findOption.isBwdSearch());
        this.wrapSearch.setSelected(findOption.isWrapSearch());
    }

    /**
     * 更新查找文本历史
     */
    public void updateFindHistory() {
        FindState.updateFindHistory(findWhat.getSelectedItem());
        loadFindHistories();
    }

    /**
     * 更新替换文本历史
     */
    public void updateReplaceHistory() {
        FindState.updateReplaceHistory(replaceWith.getSelectedItem());
        loadReplaceHistories();
    }

    private void loadFindHistories() {
        List findHistories = FindState.getFindHistories();
        ComboBoxModel m = new DefaultComboBoxModel(findHistories.toArray());
        findWhat.setModel(m);
    }

    /**
     * 加载替换历史
     */
    private void loadReplaceHistories() {
        List replaceHistories = FindState.getReplaceHistories();
        ComboBoxModel m = new DefaultComboBoxModel(replaceHistories.toArray());
        replaceWith.setModel(m);
    }

    // 变量声明
    private FindOption findOption;

    private javax.swing.JPanel findWhatPanel;
    private javax.swing.JLabel findWhatLabel;
    javax.swing.JComboBox findWhat;
    private javax.swing.JLabel replaceWithLabel;
    javax.swing.JComboBox replaceWith;
    private javax.swing.JCheckBox incSearch;
    private javax.swing.JCheckBox matchCase;
    private javax.swing.JCheckBox wholeWords;
    private javax.swing.JCheckBox bwdSearch;
    private javax.swing.JCheckBox wrapSearch;

    JButton btnFind;
    JButton btnReplace;
    JButton btnReplaceAll;
    JButton btnCancel;

}

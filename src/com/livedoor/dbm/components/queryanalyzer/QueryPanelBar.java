/**
 * $Id: QueryPanelBar.java,v 1.9 2006/12/04 02:55:14 lijc Exp $ 
 * 查询分析器工具栏
 */
package com.livedoor.dbm.components.queryanalyzer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

import com.livedoor.dbm.action.IActionHandler;
import com.livedoor.dbm.i18n.ResourceI18n;

/**
 * <p> Title: 查询分析器基类 </p> 
 * <p> Description: 定义查询分析器显示在主窗口标签中需要标题和图标 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijian@livedoor.cn">Jian Li </a>
 * @version 1.0
 */
public class QueryPanelBar extends JPanel {

    private static final long serialVersionUID = 1L;

    // 查询分析器
    private QueryAnalyzerPanel qPanel;

    private IActionHandler actionHandler;

    // 定义组件属性
    private final Dimension maxResultButtonSize = new Dimension(50, 16);

    public QueryPanelBar(QueryAnalyzerPanel qPanel, IActionHandler actionHandler) {
        this.qPanel = qPanel;
        this.actionHandler = actionHandler;

        setLayout(new BorderLayout());
        setBorder(new EtchedBorder());

        // 初始化脚本编辑工具栏
        initEditorToolBar();

        // 初始化数据库操作工具栏
        initDatabaseToolbar();
        setExecuteEnabled(true);

        // 初始化数据库信息工具栏
        initInfoToolBar();

        add(tbEditor, "North");
        add(tbDatabase, "Center");
        add(tbInfo, "South");

        setAutoCommit(true);
    }

    // 数据库信息工具栏
    private JToolBar tbInfo;
    private JComboBox _dbCombo;
    private JComboBox _schemaCombo;
    private JLabel _userLabel;

    // 初始化数据库信息工具栏
    private void initInfoToolBar() {
        tbInfo = new JToolBar();
        tbInfo.setRollover(true);
        tbInfo.setFloatable(false);

        JLabel lbDatabase = new JLabel("Database:");
        _dbCombo = new JComboBox();
        _dbCombo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                String schema = (String) _schemaCombo.getSelectedItem();
                String database = (String) _dbCombo.getSelectedItem();
                qPanel.changeDatabase(schema, database);
            }
        });
        tbInfo.add(lbDatabase);
        tbInfo.add(_dbCombo);
        tbInfo.add(new JSeparator());

        JLabel lbSchema = new JLabel("Schema:");
        _schemaCombo = new JComboBox();
        _schemaCombo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                String schema = (String) _schemaCombo.getSelectedItem();
                String database = (String) _dbCombo.getSelectedItem();
                qPanel.changeDatabase(schema, database);
            }
        });
        tbInfo.add(lbSchema);
        tbInfo.add(_schemaCombo);
        tbInfo.add(new JSeparator());

        JLabel lbUser = new JLabel("Username:");
        _userLabel = new JLabel();
        tbInfo.add(lbUser);
        tbInfo.add(_userLabel);

        JLabel label = new JLabel("                                                                       ");
        tbInfo.add(label);
    }

    // 数据库操作工具栏
    private boolean autoCommit;
    private JToolBar tbDatabase;
    private JButton jbExecute = null;
    private JButton jbExecutePlan = null;
    private JButton jbStop = null;
    JToggleButton jtbAutoCommit = null;
    private JButton jbCommit = null;
    private JButton jbRollback = null;
    private JPanel jpMaxResult;
    private JLabel _lblMaxResults;
    private JTextField jtfMaxResult;

    // 初始化数据库操作工具栏
    private void initDatabaseToolbar() {
        tbDatabase = new JToolBar();
        tbDatabase.setRollover(true);
        tbDatabase.setFloatable(false);

        jbExecute = new JButton("", ResourceI18n.getImage("MENU_EXECUTE"));
        jbExecute.setActionCommand("EXECUTE");
        jbExecute.addActionListener(actionHandler);
        jbExecute.setHorizontalTextPosition(0);
        jbExecute.setVerticalTextPosition(3);
        jbExecute.setToolTipText(ResourceI18n.getText("TOOLBAR_RUN"));
        tbDatabase.add(jbExecute);

        jbExecutePlan = new JButton("", ResourceI18n.getImage("MENU_EXECUTEEXPLAIN"));
        jbExecutePlan.setActionCommand("EXECUTEEXPLAIN");
        jbExecutePlan.addActionListener(actionHandler);
        jbExecutePlan.setHorizontalTextPosition(0);
        jbExecutePlan.setVerticalTextPosition(3);
        jbExecutePlan.setToolTipText(ResourceI18n.getText("TOOLBAR_EXECUTEEXPLAIN"));
        tbDatabase.add(jbExecutePlan);

        jbStop = new JButton("", ResourceI18n.getImage("MENU_STOP"));
        jbStop.setActionCommand("CANCEL");
        jbStop.addActionListener(actionHandler);
        jbStop.setHorizontalTextPosition(0);
        jbStop.setVerticalTextPosition(3);
        jbStop.setToolTipText(ResourceI18n.getText("TOOLBAR_STOP"));
        tbDatabase.add(jbStop);
        tbDatabase.addSeparator();

        jtbAutoCommit = new JToggleButton("", ResourceI18n.getImage("MENU_AUTOCOMMIT"), true);
        jtbAutoCommit.setActionCommand("AUTOCOMMIT");
        jtbAutoCommit.addActionListener(actionHandler);
        jtbAutoCommit.setHorizontalTextPosition(0);
        jtbAutoCommit.setVerticalTextPosition(3);
        jtbAutoCommit.setToolTipText(ResourceI18n.getText("TOOLBAR_AUTOCOMMIT"));
        tbDatabase.add(jtbAutoCommit);

        jbCommit = new JButton("", ResourceI18n.getImage("MENU_COMMIT"));
        jbCommit.setActionCommand("COMMIT");
        jbCommit.addActionListener(actionHandler);
        jbCommit.setHorizontalTextPosition(0);
        jbCommit.setVerticalTextPosition(3);
        jbCommit.setToolTipText(ResourceI18n.getText("TOOLBAR_COMMIT"));
        tbDatabase.add(jbCommit);

        jbRollback = new JButton("", ResourceI18n.getImage("MENU_ROLLBACK"));
        jbRollback.setActionCommand("ROLLBACK");
        jbRollback.addActionListener(actionHandler);
        jbRollback.setHorizontalTextPosition(0);
        jbRollback.setVerticalTextPosition(3);
        jbRollback.setToolTipText(ResourceI18n.getText("TOOLBAR_ROLLBACK"));
        tbDatabase.add(jbRollback);
        tbDatabase.add(jbRollback);
        tbDatabase.addSeparator();

        jpMaxResult = new JPanel();
        _lblMaxResults = new JLabel(ResourceI18n.getText("TE_MENU_EDIT_MAX_RESULTS"));
        jtfMaxResult = new JTextField("1000");
        jtfMaxResult.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
                    e.consume();
                }
            }
        });

        jpMaxResult.setLayout(new FlowLayout(0));
        jpMaxResult.add(_lblMaxResults);
        jpMaxResult.add(jtfMaxResult);
        jtfMaxResult.setPreferredSize(maxResultButtonSize);
        tbDatabase.add(jpMaxResult);

        JPanel jpanel = new JPanel();
        jpanel.setLayout(new FlowLayout(0));
        jpanel.add(new JLabel(" "));
        tbDatabase.add(jpanel);
    }

    // 脚本编辑工具栏
    private JToolBar tbEditor;

    // 初始化脚本编辑工具栏
    private void initEditorToolBar() {
        tbEditor = new JToolBar();
        tbEditor.setFloatable(false);
        tbEditor.setRollover(true);

        JButton jbutton = new JButton("", ResourceI18n.getImage("MENU_NEW"));
        jbutton.setActionCommand("NEW_QUERY");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_NEW_QUERY"));
        tbEditor.add(jbutton);

        jbutton = new JButton("", ResourceI18n.getImage("MENU_OPEN"));
        jbutton.setActionCommand("OPEN_QUERY");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_OPEN_SCRIPT"));
        tbEditor.add(jbutton);

        jbutton = new JButton("", ResourceI18n.getImage("MENU_SAVE"));
        jbutton.setActionCommand("SAVE_QUERY");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_SAVE_QUERY"));
        tbEditor.add(jbutton);

        jbutton = new JButton("", ResourceI18n.getImage("MENU_SAVEAS"));
        jbutton.setActionCommand("SAVE_QUERY_AS");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_SAVE_QUERY_AS"));
        tbEditor.add(jbutton);

        jbutton = new JButton("", ResourceI18n.getImage("MENU_SAVERESULTS"));
        jbutton.setActionCommand("SAVE_RESULTS");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_SAVE_RESULTS"));
        tbEditor.add(jbutton);
        tbEditor.addSeparator();

        jbutton = new JButton("", ResourceI18n.getImage("MENU_CUT"));
        jbutton.setActionCommand("CUT");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_CUT"));
        tbEditor.add(jbutton);

        jbutton = new JButton("", ResourceI18n.getImage("MENU_COPY"));
        jbutton.setActionCommand("COPY");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_COPY"));
        tbEditor.add(jbutton);

        jbutton = new JButton("", ResourceI18n.getImage("MENU_PASTE"));
        jbutton.setActionCommand("PASTE");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_PASTE"));
        tbEditor.add(jbutton);

        jbutton = new JButton("", ResourceI18n.getImage("MENU_UNDO"));
        jbutton.setActionCommand("UNDO");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_UNDO"));
        tbEditor.add(jbutton);
        tbEditor.addSeparator();

        jbutton = new JButton("", ResourceI18n.getImage("MENU_FIND"));
        jbutton.setActionCommand("FIND");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_FIND"));
        tbEditor.add(jbutton);

        tbEditor.addSeparator();

        jbutton = new JButton("", ResourceI18n.getImage("MENU_TOLOWER"));
        jbutton.setActionCommand("TOLOWER");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_TOLOWER"));
        tbEditor.add(jbutton);

        jbutton = new JButton("", ResourceI18n.getImage("MENU_TOUPPER"));
        jbutton.setActionCommand("TOUPPER");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_TOUPPER"));
        tbEditor.add(jbutton);

        JPanel jpanel = new JPanel();
        jpanel.setLayout(new FlowLayout(0));
        jpanel.add(new JLabel(" "));
        tbEditor.add(jpanel);
    }

    /**
     * 填充数据库
     * 
     * @param databases
     */
    public void setDatabases(List databases) {
        for (int i = 0; i < databases.size(); i++) {
            _dbCombo.addItem(databases.get(i));
        }
    }

    /**
     * 填充模式
     * 
     * @param schemas
     */
    public void setSchemas(List schemas) {
        for (int i = 0; i < schemas.size(); i++) {
            _schemaCombo.addItem(schemas.get(i));
        }
    }

    /**
     * 设置当前数据库
     * 
     * @param databaseName
     */
    public void setCurrentDatabase(String databaseName) {
        _dbCombo.setSelectedItem(databaseName);
    }

    /**
     * 取当前数据库
     * 
     * @return
     */
    public String getCurrentDatabase() {
        return (String) _dbCombo.getSelectedItem();
    }

    /**
     * 设置当前模式
     * 
     * @param schemaName
     */
    public void setCurrentSchema(String schemaName) {
        _schemaCombo.setSelectedItem(schemaName);
    }

    /**
     * 取当前模式
     * 
     * @return
     */
    public String getCurrentSchema() {
        return (String) _dbCombo.getSelectedItem();
    }

    /**
     * 设置当前用户名
     * 
     * @param userName
     */
    public void setUserName(String userName) {
        _userLabel.setText(userName);
    }

    /**
     * 切换自动/手动提交事务
     * @param auto
     */
    public void setAutoCommit(boolean auto) {
        this.autoCommit = auto;
        jtbAutoCommit.setEnabled(true);
        jtbAutoCommit.setSelected(auto);
        jbCommit.setEnabled(!auto);
        jbRollback.setEnabled(!auto);
    }

    /**
     * @return 是否自动提交
     */
    public boolean isAutoCommit() {
        return autoCommit;
    }
    
    /**
     * 设置执行按钮是否可用
     * @param enabled
     */
    public void setExecuteEnabled(boolean enabled) {
        jbExecute.setEnabled(enabled);
        jbExecutePlan.setEnabled(enabled);
        jbStop.setEnabled(!enabled);
    }

    /**
     * 执行脚本之前禁用按钮，stop按钮除外
     */
    public void disableButtonsExcludeStop() {
        jbExecute.setEnabled(false);
        jbExecutePlan.setEnabled(false);
        jbStop.setEnabled(true);
        jtbAutoCommit.setEnabled(false);
        jbCommit.setEnabled(false);
        jbRollback.setEnabled(false);
    }
    
    /**
     * 取最大结果条数,如果输入框为空,返回-1
     * @return 最大结果条数
     */
    public int getResultLimit() {
        String _limit = jtfMaxResult.getText();
        int limit = -1;
        try {
            limit = Integer.parseInt(_limit);
        } catch (Exception e) {
        }

        return limit;
    }

}

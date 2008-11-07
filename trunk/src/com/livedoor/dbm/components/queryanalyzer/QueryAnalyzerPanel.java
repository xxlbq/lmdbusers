/**
 * $Id: QueryAnalyzerPanel.java,v 1.1 2006/12/04 02:55:14 lijc Exp $
 * 查询分析器
 */
package com.livedoor.dbm.components.queryanalyzer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import com.livedoor.dbm.action.IActionHandler;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.queryanalyzer.completion.Completion;
import com.livedoor.dbm.components.queryanalyzer.file.DBMFileChooser;
import com.livedoor.dbm.components.queryanalyzer.file.DBMFileChooserCreator;
import com.livedoor.dbm.components.queryanalyzer.file.ScriptFile;
import com.livedoor.dbm.components.queryanalyzer.find.FindDialogSupport;
import com.livedoor.dbm.components.queryanalyzer.find.LastComponentRef;
import com.livedoor.dbm.components.queryanalyzer.syntax.CharacterKeyWords;
import com.livedoor.dbm.components.queryanalyzer.syntax.OsterTextControl;
import com.livedoor.dbm.components.queryanalyzer.syntax.SyntaxPreferences;
import com.livedoor.dbm.components.queryanalyzer.util.ScriptTokenizer;
import com.livedoor.dbm.components.treeTable.DBMExplainHandler;
import com.livedoor.dbm.components.treeTable.JDBMPanel;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBMConnectionManager;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.i18n.ResourceI18n;
import com.livedoor.dbm.ldeditor.SQLCompletionQuery;
import com.livedoor.dbm.util.StringUtil;

/**
 * <p>Title: 查询分析器</p> 
 * <p>Description:
 *		查询分析器是数据库客户端工具的重要组成部分
 *		用户可编辑SQL语句,提交SQL语句,查看执行结果等  
 * </p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class QueryAnalyzerPanel extends SQLEditPanel {

	private static final long serialVersionUID = 1L;

	/**
     * 构造查询分析器
	 * @param frame 主窗口
	 * @param connInfo 数据库连接信息
	 * @param actionHandler 事件监听器 
	 */
	public QueryAnalyzerPanel(DBMFrame frame, ConnectionInfo connInfo, IActionHandler actionHandler) {
		this.frame = frame; 
		this.connInfo = connInfo;
		this.actionHandler = actionHandler;
		this.dbSession = new DBSession();
		this.sqlExecuter = DBMSqlExecuterFactory.createExecuter(connInfo, dbSession);
		this.characterKeyWords = new CharacterKeyWords();

		initComponents();
	}

    /**
     * 查询分析器显示在标签中用图标
     */
	@Override
	public Icon getIcon() {
		return ResourceI18n.getImage("TOOLBAR_ANALYZER_DEV");
	}

    /**
     * 初始化组件
     */
	private void initComponents() {
		// 构造工具栏
		panelBar = new QueryPanelBar(this, actionHandler);
		
		/* 构造SQL编辑器
		 * 先设定SQL语法解析器，然后构造SQL编辑器
		 */
		SyntaxPreferences prefs = new SyntaxPreferences();
		sqlEditTextPane = new OsterTextControl(prefs, characterKeyWords);
        document = sqlEditTextPane.getDocument();
        new Completion(sqlEditTextPane);
        
        //监视脚本是否需要保存
        sqlEditTextPane.getDocument().addDocumentListener(new MarkingDocumentListener());
        //监视获得焦点,为查找使用
        sqlEditTextPane.addFocusListener(new LastComponentRef());
        
		// 构造结果显示标签
		resultTab = new JTabbedPane(JTabbedPane.BOTTOM);

		// 填充数据库
		List<String> databases = null;
		try {
			databases = sqlExecuter.getDatabase();
		} catch (DBMException e1) {
			e1.printStackTrace();
		}
		if (StringUtil.isEmpty(databases)) {
			databases = new ArrayList<String>();
			databases.add(connInfo.getDatabase());
		}
		panelBar.setDatabases(databases);

		// 填充模式
		List<String> schemas = null;
		try {
			schemas = sqlExecuter.getSchemas();
		} catch (DBMException e1) {
			e1.printStackTrace();
		}
		if (StringUtil.isEmpty(schemas)) {
			schemas = new ArrayList<String>();
			schemas.add(connInfo.getUserName());
		}
		panelBar.setSchemas(schemas);
		
		// 显示用户
		panelBar.setUserName(connInfo.getUserName());
		
        // 构造状态栏
        statusBar = new StatusBar();
        
        JPanel queryEditPanel = new JPanel();
        queryEditPanel.setLayout(new BorderLayout());
        queryEditPanel.add(panelBar, BorderLayout.NORTH); 
        queryEditPanel.add(new JScrollPane(sqlEditTextPane), BorderLayout.CENTER);
        queryEditPanel.add(statusBar, BorderLayout.SOUTH);
        
        JSplitPane splitPane = getJSplitPane(queryEditPanel, resultTab); 
        setLayout(new BorderLayout());
		add(splitPane, BorderLayout.CENTER);
        
        sqlEditTextPane.getDocument().putProperty(SQLCompletionQuery.DBMSQLEXECUTER, sqlExecuter);
	}

	/**
     * 拆分窗口为上下两个区域,
     * 上面为编辑器,下面为执行结果
	 */
	private JSplitPane getJSplitPane(JComponent comp1, JComponent comp2) {
		JSplitPane splitPane = new JSplitPane(0, comp1, comp2);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerSize(7);
		splitPane.setDividerLocation(450);

		return splitPane;
	}

	/**
	 * 取编辑区SQL脚本
	 * @return
	 */
	public String getScript() {
		return sqlEditTextPane.getText();
	}

	/**
	 * 取编辑区选中SQL脚本
	 * @return
	 */
	public String getSelectedScript() {
		return sqlEditTextPane.getSelectedText();
	}

	/**
	 * 设置编辑区SQL脚本
	 * @param script
	 */
	public void setScript(String script) {
		sqlEditTextPane.setText(script);
	}

	/**
	 * 替换编辑区选中SQL脚本
	 * @param script
	 */
	public void replaceSelection(String script) {
		sqlEditTextPane.replaceSelection(script);
	}

    public void insertSQLStatement(String script) {
        StringBuilder sb = new StringBuilder();
        String _script = getScript().trim();
        if(StringUtil.isNotEmpty(_script)) {
            sb.append("\n").append("go\n");
        }
        sb.append(script);
        replaceSelection(sb.toString());
    }
    
	/**
	 * 全选编辑区文本
	 */
	public void selectAll() {
		sqlEditTextPane.selectAll();
	}

	/**
	 * 取查询分析器适当标题在主窗口标签对应位置显示
	 */
	@Override
	public String getTitle() {
		StringBuilder title = new StringBuilder();
		title.append(connInfo.getAliasName());

		if (!StringUtil.isEmpty(scriptFileName)) {
			title.append("[").append(scriptFileName).append("]");
		}

		if (isDirty) {
			title.append("*");
		}

		return title.toString();
	}

	/**
	 * 保存脚本
     * @return 文件选择器的结果
	 */
	public int saveQuery() {
		if(!isDirty()) 
			return DBMFileChooser.APPROVE_OPTION;
		
		if(StringUtil.isEmpty(scriptFullName)) {
			return saveQueryAs();
		} else {
            try {
                ScriptFile.save(scriptFullName, getScript(), charset);
            } catch (Exception e) {
                e.printStackTrace();
                DBMMessageDialog.showErrorMessageDialog(e.getMessage());
                return DBMFileChooser.APPROVE_OPTION;
            }
            
			setDirty(false);
            return DBMFileChooser.APPROVE_OPTION;
		}
	}
	
	/**
	 * 脚本另存为
     * @return 文件选择器的结果
	 */
	public int saveQueryAs() {
        if (creator == null)
            creator = new DBMFileChooserCreator();
		DBMFileChooser chooser = creator.getScriptFileChooser();
		int returnVal = chooser.showSaveAsDialog(this);
		if (returnVal != DBMFileChooser.APPROVE_OPTION) 
			return returnVal;

		String _scriptFullName = ScriptFile.appendExtension(chooser.getPath(), chooser.getExtension());
		String _scriptFileName = ScriptFile.appendExtension(chooser.getName(), chooser.getExtension());
        String _charset = chooser.getCharset();
        try {
            ScriptFile.save(_scriptFullName, getScript(), _charset);
        } catch (Exception e) {
            e.printStackTrace();
            DBMMessageDialog.showErrorMessageDialog(e.getMessage());
            return returnVal;
        }

        scriptFullName = _scriptFullName;
        scriptFileName = _scriptFileName;
        charset = _charset;
		setDirty(false);
        return returnVal;
	}
	
    /**
	 * 加载脚本文件
	 */
	public void openQuery() {
		if(isDirty()) {
			int result = DBMMessageDialog.showConfirmDialogYNC(null, "ASK_IF_SAVE_QUERY");
            if(result == JOptionPane.DEFAULT_OPTION)
                result = JOptionPane.CANCEL_OPTION;
            
			if(result==JOptionPane.OK_OPTION)     //YES
				saveQuery();
			if(result==JOptionPane.CANCEL_OPTION) //CANCEL
				return;
		}

        if(creator == null)
            creator = new DBMFileChooserCreator();
        DBMFileChooser chooser = creator.getScriptFileChooser();
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal != DBMFileChooser.APPROVE_OPTION) 
			return;

        String _scriptFullName = ScriptFile.appendExtension(chooser.getPath(), chooser.getExtension());
        String _scriptFileName = ScriptFile.appendExtension(chooser.getName(), chooser.getExtension());
        String _charset = chooser.getCharset();
        String script = "";
        try {
            script = ScriptFile.get(_scriptFullName, _charset);
        } catch (Exception e) {
            e.printStackTrace();
            DBMMessageDialog.showErrorMessageDialog(e.getMessage());
            return;
        }
        
        setScript(script);
        scriptFullName = _scriptFullName;
        scriptFileName = _scriptFileName;
        charset = _charset;
		setDirty(false);
	}
	
	/**
	 * 新建脚本
	 */
	public void newQuery() {
		if(isDirty()) {
            int result = DBMMessageDialog.showConfirmDialogYNC(this, "ASK_IF_SAVE_QUERY");
            if(result == JOptionPane.DEFAULT_OPTION)
                result = JOptionPane.CANCEL_OPTION;
            
            if(result==JOptionPane.OK_OPTION)     //YES
                saveQuery();
            if(result==JOptionPane.CANCEL_OPTION) //CANCEL
                return;
		}		

        if (creator == null)
            creator = new DBMFileChooserCreator();
        DBMFileChooser chooser = creator.getScriptFileChooser();
		int returnVal = chooser.showNewDialog(this);
		if (returnVal != DBMFileChooser.APPROVE_OPTION) 
			return;
		
        String _scriptFullName = ScriptFile.appendExtension(chooser.getPath(), chooser.getExtension());
        String _scriptFileName = ScriptFile.appendExtension(chooser.getName(), chooser.getExtension());
        String _charset = chooser.getCharset();
        try {
            ScriptFile.save(_scriptFullName, "", _charset);
        } catch (Exception e) {
            e.printStackTrace();
            DBMMessageDialog.showErrorMessageDialog(e.getMessage());
            return;
        }
        
        setScript("");
        scriptFullName = _scriptFullName;
        scriptFileName = _scriptFileName;
        charset = _charset;
		setDirty(false);
	}
	
	/**
	 * 设定当前的数据库名称
	 * @param dbName
	 * @author lijian
	 */
	public void setCurrentDatabase(String dbName) {
		panelBar.setCurrentDatabase(dbName);
	}

	/**
	 * 设定当前的模式名称
	 * @param schemaName
	 */
	public void setCurrentSchema(String schemaName) {
		panelBar.setCurrentSchema(schemaName);
	}

	/**
	 * 选择不同数据库
	 * @param schema	模式名
	 * @param database	数据库名
	 */
	public void changeDatabase(String schema, String database) {
		sqlExecuter.changeDatabase(database);
		changeSchema(schema, database);
	}

	// 选择不同模式或者不同数据库,从数据库取元数据并更新对于数据模型
	private void changeSchema(String schema, String database) {
        document.putProperty(SQLCompletionQuery.CURRENTDATABASE, database);
        document.putProperty(SQLCompletionQuery.CURRENTSCHEMA, schema);
        document.putProperty(SQLCompletionQuery.SCHEMACACHE, null);
        document.putProperty(SQLCompletionQuery.TABLECACHE, null);
	}

	/** 
	 * 显示查询窗口
	 * @param actionEvent
	 */
	public void showFindDialog(ActionEvent actionEvent) {
		findDialogSupport = FindDialogSupport.getInstance();
		findDialogSupport.showFindDialog();
	}

	/**
	 * 将连接放回连接池中
	 */
	public void releaseConnection() {
        cancelQuery();
		try {
            DBMConnectionManager.releaseConnection(connInfo, dbSession);
        } catch (Exception e) {
            e.printStackTrace();
            DBMMessageDialog.showErrorMessageDialog(e.getMessage());
        }
	}
    
	/**
	 * 关闭数据库连接
	 */
	public void closeConnection(){
        try {
            DBMConnectionManager.setAutoCommit(connInfo, dbSession, true);
        } catch (DBMException e) {
            e.printStackTrace();
        }
		DBMConnectionManager.closeConnection(connInfo, dbSession);
	}
    
	/**
	 * 执行SQL脚本
	 * @param script 待执行脚本
	 */
	public void execute(String script) {
        if(queryThread==null || !queryThread.isAlive()) {
            queryThread = new QueryThread(this, connInfo, dbSession, script, panelBar.getResultLimit());
            queryThread.start();
        }
	}
    
    public void cancelQuery() {
        if(queryThread!=null) {
            queryThread.cancel();
            queryThread.interrupt();
            queryThread = null;
        }
        sqlEditTextPane.requestFocus();
    }

	/**
	 * 执行计划
	 * @param script 待做执行计划的脚本 
	 */
	public void executeExplain(String script) {
		removeTabsByTitle("Explain");

		ScriptTokenizer st = new ScriptTokenizer(script);
		for(int i=1; st.hasScripts(); i++) {
			JDBMPanel explainPanel = null;
			try {
				explainPanel = DBMExplainHandler.getExplainPanel(connInfo, dbSession, st.nextScript());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(explainPanel!=null) {
				resultTab.addTab("Explain-"+i, explainPanel);
				resultTab.setSelectedComponent(explainPanel);
			}
		}
	}
	
	// 删除结果标签中所有标题以title开头的结果页
	private void removeTabsByTitle(String title) {
		for(int i=resultTab.getTabCount()-1; i>=0; i--) {
			String _title = resultTab.getTitleAt(i);
			if(_title.startsWith(title)) {
				resultTab.removeTabAt(i);
			} 
		}
	}
	
	/**
	 * 切换自动/手动提交事务
	 */
	public void changeAutoCommit() {
        // 正在执行脚本
        if(!panelBar.jtbAutoCommit.isEnabled()) return;
        
		panelBar.setAutoCommit(!panelBar.isAutoCommit());
        String error = "";
        boolean failed = false;
		try {
			DBMConnectionManager.setAutoCommit(connInfo, dbSession, panelBar.isAutoCommit());
		} catch (DBMException e) {
			e.printStackTrace();
            error = e.getMessage();
            failed = true;
		}
        
        if(!failed) {
            if(panelBar.isAutoCommit())
                statusBar.setText(StatusBar.MAIN, " [" + DateFormat.getDateTimeInstance(3, 1).format(new Date()) + "] AutoCommit set to ON");
            else
                statusBar.setText(StatusBar.MAIN, " [" + DateFormat.getDateTimeInstance(3, 1).format(new Date()) + "] AutoCommit set to OFF");
        } else {
            panelBar.setAutoCommit(!panelBar.isAutoCommit());
            if(panelBar.isAutoCommit())
                statusBar.setText(StatusBar.MAIN, " [" + DateFormat.getDateTimeInstance(3, 1).format(new Date()) + "] Failed to set AutoCommit ON :" + error);
            else
                statusBar.setText(StatusBar.MAIN, " [" + DateFormat.getDateTimeInstance(3, 1).format(new Date()) + "] Failed to set AutoCommit OFF :" + error);
        }
        
        statusBar.setVisible(true);
        sqlEditTextPane.requestFocus();
	}

	/**
	 * 提交事务
	 */
	public void commit() {
        // 正在执行脚本
        if(!panelBar.jtbAutoCommit.isEnabled()) return;
        
        //如果auto为true,提交会抛异常
        if(panelBar.isAutoCommit()) return;
        
        String error = "";
        boolean failed = false;
		try {
			DBMConnectionManager.commit(connInfo, dbSession);
		} catch (DBMException e) {
			e.printStackTrace();
            error = e.getMessage();
            failed = true;
		}

        if (!failed)
            statusBar.setText(StatusBar.MAIN, " [" + DateFormat.getDateTimeInstance(3, 1).format(new Date()) + "] Commit Successful");
        else
            statusBar.setText(StatusBar.MAIN, " [" + DateFormat.getDateTimeInstance(3, 1).format(new Date()) + "] Failed to Commit :"
                    + error);
        
        statusBar.setVisible(true);
        sqlEditTextPane.requestFocus();
	}

	/**
	 * 回滚事务
	 */
	public void rollback() {
        // 正在执行脚本
        if(!panelBar.jtbAutoCommit.isEnabled()) return;
        
        //如果auto为true,提交会抛异常
        if(panelBar.isAutoCommit()) return;
        
        String error = "";
        boolean failed = false;
		try {
			DBMConnectionManager.rollback(connInfo, dbSession);
		} catch (DBMException e) {
			e.printStackTrace();
            error = e.getMessage();
            failed = true;
        }

        if (!failed)
            statusBar.setText(StatusBar.MAIN, " [" + DateFormat.getDateTimeInstance(3, 1).format(new Date()) + "]  Rollback Successful");
        else
            statusBar.setText(StatusBar.MAIN, " [" + DateFormat.getDateTimeInstance(3, 1).format(new Date()) + "]  Failed to Rollback :"
                    + error);
            
        statusBar.setVisible(true);
        sqlEditTextPane.requestFocus();
	}

	/**
	 * 保存执行结果
	 * @return
	 */
	public void saveExecuteResult() {
        if (textResult == null) {
            JOptionPane.showMessageDialog(this, ResourceI18n.getText("NO_TEXT_RESULT_TO_SAVE"));
            return;
        }
        textResult.save(this);
    }
	
    /**
     * SQL脚本是否未保存
     * @return 如果未保存,返回true;否则返回false.
     */
	public boolean isDirty() {
		return isDirty;
	}
    
    /**
     * 设置SQL脚本是否未保存标志,修改后调用dirtyChanged()方法 
     * @param isDirty
     */
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
		dirtyChanged();
	}
    
    /**
     * SQL脚本未保存标志变化,为更新查询分析器标签中当前标题
     */
	private void dirtyChanged() {
		frame.updateQueryPanelTitle(this);
	}
	
    /**
     * @return 数据库连接信息
     */
    public ConnectionInfo getConnInfo() {
        return connInfo;
    }
    
    /**
     * @return DBSession
     */
    public DBSession getDBSession() {
        return dbSession;
    }
    
    /**
     * @return 结果显示标签
     */
    public JTabbedPane getResultTab() {
        return resultTab;
    }
    
    /**
     * 设置文本结果页,便于保存结果
     * @param textResult
     */
    public void setTextResult(TextResultPane textResult) {
        this.textResult = textResult;
    }

    /**
     * @return 查询分析器工具栏
     */
    public QueryPanelBar getQueryPanelBar() {
        return panelBar;
    }

    /**
     * @return 状态栏
     */
    public StatusBar getStatusBar() {
        return statusBar;
    }
    
    // //////////////////////////////
	private DBMFrame frame;					//主窗口
	
	private QueryPanelBar panelBar;  		// 工具栏
    private StatusBar statusBar;            // 状态栏
	private IActionHandler actionHandler;
    private JTabbedPane resultTab;          // 排列显示多个结果页
    private TextResultPane textResult;      // 文本结果
    private DBMFileChooserCreator creator;  // 创建文件选择对话框
    
	private DBSession dbSession; 			// 当前会话标识
	private ConnectionInfo connInfo;  		// 数据库连接信息
	private DBMSqlExecuter sqlExecuter; 	// 脚本执行器
    private QueryThread queryThread;        // 脚本执行线程
    
	private String scriptFullName;			// 脚本文件全名,包括路径和文件名
	private String scriptFileName;			// 脚本文件名
    private String charset;                 // 脚本文件字符集
	private boolean isDirty = false;		// 脚本是否修改过

	// 查询窗口提供者
	private FindDialogSupport findDialogSupport;

	private CharacterKeyWords characterKeyWords;	// 脚本编辑器上下文帮助,维护关键字
    
    private Document document;
    // //////////////////////////////
    
    // 设置文档是否需保存状态
    private class MarkingDocumentListener implements DocumentListener {
        public void changedUpdate(DocumentEvent documentevent) {}
        public void insertUpdate(DocumentEvent documentevent) {
            setDirty(true);
        }
        public void removeUpdate(DocumentEvent documentevent) {
            setDirty(true);
        }
    }
}

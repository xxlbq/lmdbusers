/**
 * $Id: QueryThread.java,v 1.17 2006/12/07 08:29:53 lijc Exp $
 * 查询分析器相关
 */
package com.livedoor.dbm.components.queryanalyzer;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.livedoor.dbm.action.mainframe.DBMBaseShowAction;
import com.livedoor.dbm.components.common.JTableColumnResize;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.DBResultType;
import com.livedoor.dbm.db.DBMConnectionManager;
import com.livedoor.dbm.db.DBMDataResult;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.db.DBMSqlResult;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.scripts.ScriptStatement;
import com.livedoor.dbm.scripts.engine.LDScriptParser;
import com.livedoor.dbm.util.StringUtil;

/**
 * <p>Title: 脚本执行线程</p> 
 * <p>Description: 脚本执行线程,查询分析器调用线程</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class QueryThread extends Thread {

    private boolean cancel = false;
    
    private QueryAnalyzerPanel panel;
    private ConnectionInfo connInfo;
    private DBSession dbSession;
    private String script;
    private int resultLimit;
    private DBMSqlExecuter sqlExecuter;
    
    private TextResultPane textResult;
    private GridResultPanel gridResult;
    private ScriptDetailsTable detailResult;
    private StatusBar statusBar;
    private String currentStatusText;
    
    public QueryThread(QueryAnalyzerPanel panel, ConnectionInfo connInfo, DBSession dbSession, String script, int resultLimit) {
        this.panel = panel;
        this.connInfo = connInfo;
        this.dbSession = dbSession;
        this.script = script;
        this.resultLimit = resultLimit;
        this.sqlExecuter = DBMSqlExecuterFactory.createExecuter(connInfo, dbSession);

        this.statusBar = panel.getStatusBar();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        statusBar.setVisible(true);
        
        //禁用按钮
        QueryPanelBar panelBar = panel.getQueryPanelBar();
        boolean isAutoCommit = panelBar.isAutoCommit();
        panelBar.disableButtonsExcludeStop();

        //移除上次执行结果
        JTabbedPane resultTab = panel.getResultTab();
        resultTab.removeAll();
        
        if(DBMBaseShowAction.bShowText || 
                (!DBMBaseShowAction.bShowText && !DBMBaseShowAction.bShowGrid) ) {
            textResult = new TextResultPane();
            panel.setTextResult(textResult);
            textResult.setVisible(false);
        }
        
        if(DBMBaseShowAction.bShowGrid) {
            gridResult = new GridResultPanel();
            gridResult.setVisible(false);
        }
        
        detailResult = new ScriptDetailsTable();
        detailResult.setVisible(false);

        StatusThread statusThread = new StatusThread(this, startTime);
        statusThread.start();
        setStatusText(" Parsing script ...", false);
        LDScriptParser parser = new LDScriptParser(true, false);
        parser.parseScript(script);
        List<ScriptStatement> stmts = parser.getScriptStatements();
        
        int errors = 0;
        try {
            for(ScriptStatement stmt:stmts) {
                if(cancel)
                    break;
                
                DBMSqlResult result = null;
                try {
                    setStatusText(" Executing script ...", false);
                    DBMConnectionManager.setAutoCommit(connInfo, dbSession, panelBar.isAutoCommit());
                    result = sqlExecuter.execute(StringUtil.cleanString(stmt.statement), resultLimit);
                } catch (DBMException e) {
                    e.printStackTrace();
                }
                
                if(result==null)
                    continue;

                setStatusText(" Preparing results ...", false);
                if(result.getType() == DBResultType.ERROR)
                    errors++;
                
                if(textResult!=null) {
                    setStatusText(" Preparing text results ...", false);
                    textResult.resetDisplayedRowCount();
                    textResult.displayResult(result);
                }

                if(gridResult!=null) {
                    setStatusText(" Preparing grid results ...", false);
                    gridResult.displayResult(result);
                }
                
                setStatusText(" Preparing script details ...", false);
                ImageIcon icon = null;
                if (result.getType() == DBResultType.ERROR)
                    icon = ScriptDetailsTable.ERROR_ICON;
                else 
                    icon = ScriptDetailsTable.SUCCESS_ICON;
                
                int count = 0;
                if (textResult != null) {
                    count = textResult.getDisplayedRowCount();
                } else {
                    DBMDataResult dataResult = result.getDBMDataResult();
                    if (dataResult != null) {
                        List data = dataResult.getData();
                        if (data != null)
                            count = data.size();
                    }
                }
                detailResult.appendRow(icon, "" + count, stmt.statement, result.getMessage());
            }
        } finally {
        
            JScrollPane scroll = null;
            
            // 显示文本
            if(textResult!=null) {
                scroll = new JScrollPane(textResult);
                textResult.setVisible(true);
                resultTab.addTab("Text", scroll);
            }
            
            // 显示表格
            if(gridResult!=null) {
                scroll = new JScrollPane(gridResult);
                gridResult.setVisible(true);
                resultTab.addTab("Grid", scroll);
            }
            JTableColumnResize.initColumnSize(detailResult);
            
            // 显示脚本执行明细
            scroll = new JScrollPane(detailResult);
            detailResult.setVisible(true);
            resultTab.addTab("Script Details", scroll);
            
            // 如果只显示表格且表格数为0,选择脚本明细页
            if(textResult==null && gridResult!=null && gridResult.getGridCount()==0) {
                resultTab.setSelectedComponent(scroll);
            }
    
            // 设置操作数据库按钮状态
            panelBar.setExecuteEnabled(true);
            panelBar.setAutoCommit(isAutoCommit);
            
            setStatusText(" [" + DateFormat.getDateTimeInstance(3, 1).format(new Date()) + "] Script executed - (" + errors + ") Errors", false);
            if(!statusThread.isInterrupted())
                statusThread.interrupt();
        }
    }
    
    /**
     * 终止当前操作
     */
    public void cancel() {
        cancel = true;
        
        if(textResult!=null)
            textResult.cancel();
        
        sqlExecuter.cancel();
    }
    
    public void setStatusText(String text, boolean append) {
        if(text==null) return;
        
        synchronized (statusBar) {
            String _text = text;
            if(!append) 
                currentStatusText = text;
            else
                _text = currentStatusText + " " + text;
            
            statusBar.setText(StatusBar.MAIN, _text);
        }
    }
}

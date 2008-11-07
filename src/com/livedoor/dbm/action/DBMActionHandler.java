/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.action;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import com.livedoor.dbm.action.mainframe.DBMAboutAciont;
import com.livedoor.dbm.action.mainframe.DBMExitAction;
import com.livedoor.dbm.action.mainframe.LanguageAction;
import com.livedoor.dbm.action.mainframe.RegisterServerCloneAction;
import com.livedoor.dbm.action.mainframe.RegisterServerDeleteAction;
import com.livedoor.dbm.action.mainframe.RegisterServerNewAction;
import com.livedoor.dbm.action.mainframe.RegisterServerProptiesAction;
import com.livedoor.dbm.action.mainframe.SQLHistoryAction;
import com.livedoor.dbm.action.mainframe.ServerConnectAction;
import com.livedoor.dbm.action.mainframe.ServerDisconnectAction;
import com.livedoor.dbm.action.mainframe.ShowGridAction;
import com.livedoor.dbm.action.mainframe.ShowTextAction;
import com.livedoor.dbm.action.mainframe.ToolsErDiagramGeneratorAction;
import com.livedoor.dbm.action.mainframe.ToolsExportAction;
import com.livedoor.dbm.action.mainframe.ToolsImportAction;
import com.livedoor.dbm.action.queryanalyzer.AutoCommitAction;
import com.livedoor.dbm.action.queryanalyzer.CommitAction;
import com.livedoor.dbm.action.queryanalyzer.CopyAction;
import com.livedoor.dbm.action.queryanalyzer.CutAction;
import com.livedoor.dbm.action.queryanalyzer.ExecuteAction;
import com.livedoor.dbm.action.queryanalyzer.ExecuteExplainAction;
import com.livedoor.dbm.action.queryanalyzer.FindAction;
import com.livedoor.dbm.action.queryanalyzer.FindReplaceAction;
import com.livedoor.dbm.action.queryanalyzer.FormatSQLAction;
import com.livedoor.dbm.action.queryanalyzer.NewQueryAction;
import com.livedoor.dbm.action.queryanalyzer.OpenQueryAction;
import com.livedoor.dbm.action.queryanalyzer.PasteAction;
import com.livedoor.dbm.action.queryanalyzer.QueryAnalyzerAction;
import com.livedoor.dbm.action.queryanalyzer.RedoAction;
import com.livedoor.dbm.action.queryanalyzer.RollbackAction;
import com.livedoor.dbm.action.queryanalyzer.SaveQueryAction;
import com.livedoor.dbm.action.queryanalyzer.SaveQueryAsAction;
import com.livedoor.dbm.action.queryanalyzer.SaveResultAction;
import com.livedoor.dbm.action.queryanalyzer.SelectAllAction;
import com.livedoor.dbm.action.queryanalyzer.StopAction;
import com.livedoor.dbm.action.queryanalyzer.ToLowerCaseAction;
import com.livedoor.dbm.action.queryanalyzer.ToUpperCaseAction;
import com.livedoor.dbm.action.queryanalyzer.UndoAction;
import com.livedoor.dbm.action.tree.EditTableDataAction;
import com.livedoor.dbm.action.tree.SchemaDatabaseDropAction;
import com.livedoor.dbm.action.tree.SchemaDatabaseNewAction;
import com.livedoor.dbm.action.tree.SchemaFunctionDropAction;
import com.livedoor.dbm.action.tree.SchemaFunctionNewAction;
import com.livedoor.dbm.action.tree.SchemaFunctionPropertiesAction;
import com.livedoor.dbm.action.tree.SchemaProcedureDropAction;
import com.livedoor.dbm.action.tree.SchemaProcedureNewAction;
import com.livedoor.dbm.action.tree.SchemaProcedurePropertiesAction;
import com.livedoor.dbm.action.tree.SchemaTableAlterAction;
import com.livedoor.dbm.action.tree.SchemaTableDropAction;
import com.livedoor.dbm.action.tree.SchemaTableNewAction;
import com.livedoor.dbm.action.tree.SchemaTriggerDropAction;
import com.livedoor.dbm.action.tree.SchemaTriggerNewAction;
import com.livedoor.dbm.action.tree.SchemaTriggerPropertiesAction;
import com.livedoor.dbm.action.tree.SchemaViewDropAction;
import com.livedoor.dbm.action.tree.SchemaViewNewAction;
import com.livedoor.dbm.action.tree.SchemaViewPropertiesAction;
import com.livedoor.dbm.action.tree.ScriptColumnAlterAction;
import com.livedoor.dbm.action.tree.ScriptColumnCreateAction;
import com.livedoor.dbm.action.tree.ScriptColumnDropAction;
import com.livedoor.dbm.action.tree.ScriptDatabaseCreateAction;
import com.livedoor.dbm.action.tree.ScriptDatabaseDropAction;
import com.livedoor.dbm.action.tree.ScriptDeleteAction;
import com.livedoor.dbm.action.tree.ScriptFunctionCreateAction;
import com.livedoor.dbm.action.tree.ScriptFunctionDropAction;
import com.livedoor.dbm.action.tree.ScriptFunctionExecuteAction;
import com.livedoor.dbm.action.tree.ScriptInsertAction;
import com.livedoor.dbm.action.tree.ScriptProcedureCreatAction;
import com.livedoor.dbm.action.tree.ScriptProcedureDropAction;
import com.livedoor.dbm.action.tree.ScriptProcedureExecuteAction;
import com.livedoor.dbm.action.tree.ScriptTableAlterAction;
import com.livedoor.dbm.action.tree.ScriptTableCreateAction;
import com.livedoor.dbm.action.tree.ScriptTableDropAction;
import com.livedoor.dbm.action.tree.ScriptTableSelectAction;
import com.livedoor.dbm.action.tree.ScriptTableSelectAllAction;
import com.livedoor.dbm.action.tree.ScriptTriggerCreateAction;
import com.livedoor.dbm.action.tree.ScriptTriggerDropAction;
import com.livedoor.dbm.action.tree.ScriptUpdataAction;
import com.livedoor.dbm.action.tree.ScriptViewCreateAction;
import com.livedoor.dbm.action.tree.ScriptViewDropAction;
import com.livedoor.dbm.action.tree.ScriptViewSelectAction;
import com.livedoor.dbm.action.tree.TreeRefresh;
import com.livedoor.dbm.components.mainframe.DBMFrame;

/**
 * <p>
 * Title: Action共同类
 * </p>
 * <p>
 * Description: 主菜单和右键菜单的Action处理类.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author zhangys
 * @version 1.0
 */
public class DBMActionHandler implements IActionHandler {

	/*
	 * 根据每个菜单条目的actionName维护一个具体的事件处理 Action类;该集合中的key指为菜单条目的actionName,value
	 * 为一个实现了DBMBaseAction类的对象
	 */
	private HashMap actionList = new HashMap(500);

	private DBMFrame frame;

	public DBMActionHandler() {
		this(null);
	}
	/**
	 * [功 能] 构造器.
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param frame
	 *            主窗体
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public DBMActionHandler(DBMFrame frame) {
		this.frame = frame;
		initActionList();
	}

	/**
	 * [功 能] 初始化已经定义好的大部分处理Action类;该类直接操作 actionList集合
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	private void initActionList() {
		// register new action
		DBMBaseAction action = new RegisterServerNewAction();
		addAction(action);
		// server propties action
		action = new RegisterServerProptiesAction();
		addAction(action);
		// delete server action
		action = new RegisterServerDeleteAction();
		addAction(action);
		// queryAnalyzer action
		action = new QueryAnalyzerAction();
		addAction(action);
		// 显示文本结果
		action = new ShowTextAction();
		addAction(action);
		// 显示表格结果
		action = new ShowGridAction();
		addAction(action);
		// new query actions
		action = new NewQueryAction();
		addAction(action);
		// open query action
		action = new OpenQueryAction();
		addAction(action);
		// save query action
		action = new SaveQueryAction();
		addAction(action);
		// save query as action
		action = new SaveQueryAsAction();
		addAction(action);
		// save result action
		action = new SaveResultAction();
		addAction(action);
		// cut action
		action = new CutAction();
		addAction(action);
		// copy action
		action = new CopyAction();
		addAction(action);
		// paste action
		action = new PasteAction();
		addAction(action);
		// undoAction
		action = new UndoAction();
		addAction(action);
		// redo action
		action = new RedoAction();
		addAction(action);
		// to lowerCase action
		action = new ToLowerCaseAction();
		addAction(action);
		// to upperCase action
		action = new ToUpperCaseAction();
		addAction(action);
		// select all action
		action = new SelectAllAction();
		addAction(action);
		// find action
		action = new FindAction();
		addAction(action);
		// find replace action
		action = new FindReplaceAction();
		addAction(action);
		// fromat sql action
		action = new FormatSQLAction();
		addAction(action);
		// database operation actions
		action = new ExecuteAction();
		addAction(action);
		// execute explain action
		action = new ExecuteExplainAction();
		addAction(action);
		// stop action
		action = new StopAction();
		addAction(action);
		// auto commit action
		action = new AutoCommitAction();
		addAction(action);
		// commit action
		action = new CommitAction();
		addAction(action);
		// rollback action
		action = new RollbackAction();
		addAction(action);
		// tree refresh Action
		action = new TreeRefresh();
		addAction(action);
		// tree schema clone
		action = new RegisterServerCloneAction();
		addAction(action);
		// tree schema connect
		action = new ServerConnectAction();
		addAction(action);
		// tree schema disConnect
		action = new ServerDisconnectAction();
		addAction(action);
		// tools import
		action = new ToolsImportAction();
		addAction(action);
		// tools export
		action = new ToolsExportAction();
		addAction(action);
		// schema database new
		action = new SchemaDatabaseNewAction();
		addAction(action);
		// schema database drop
		action = new SchemaDatabaseDropAction();
		addAction(action);
		// schema database properties
		// action = new SchemaDatabaseProperties();
		// addAction(action);
		// script database create
		action = new ScriptDatabaseCreateAction();
		addAction(action);
		// script database drop
		action = new ScriptDatabaseDropAction();
		addAction(action);
		// schema table new
		action = new SchemaTableNewAction();
		addAction(action);
		// schema table alter
		action = new SchemaTableAlterAction();
		addAction(action);
		// schema table drop
		action = new SchemaTableDropAction();
		addAction(action);
		// schema table properties
		// action = new SchemaTablePropertiesAction();
		// addAction(action);
		// edit table data
		action = new EditTableDataAction();
		addAction(action);
		// script table select all
		action = new ScriptTableSelectAllAction();
		addAction(action);
		// script table select
		action = new ScriptTableSelectAction();
		addAction(action);
		// script insert
		action = new ScriptInsertAction();
		addAction(action);
		// script updata
		action = new ScriptUpdataAction();
		addAction(action);
		// script delete
		action = new ScriptDeleteAction();
		addAction(action);
		// script table create
		action = new ScriptTableCreateAction();
		addAction(action);
		// script table alter
		action = new ScriptTableAlterAction();
		addAction(action);
		// script table drop
		action = new ScriptTableDropAction();
		addAction(action);
		// schema trigger new
		action = new SchemaTriggerNewAction();
		addAction(action);
		// schema view new
		action = new SchemaViewNewAction();
		addAction(action);
		// schema function new
		action = new SchemaFunctionNewAction();
		addAction(action);
		// schema procedure new
		action = new SchemaProcedureNewAction();
		addAction(action);
		// script procedure execute
		action = new ScriptProcedureExecuteAction();
		addAction(action);
		// script procedure drop
		action = new ScriptProcedureDropAction();
		addAction(action);
		// script procedure create
		action = new ScriptProcedureCreatAction();
		addAction(action);
		// schema procedure properties
		action = new SchemaProcedurePropertiesAction();
		addAction(action);
		// schema procedure drop
		action = new SchemaProcedureDropAction();
		addAction(action);
		// script trigger drop
		action = new ScriptTriggerDropAction();
		addAction(action);
		// script trigger create
		action = new ScriptTriggerCreateAction();
		addAction(action);
		// schema trigger drop
		action = new SchemaTriggerDropAction();
		addAction(action);
		// schema trigger properties
		action = new SchemaTriggerPropertiesAction();
		addAction(action);
		// script function execute
		action = new ScriptFunctionExecuteAction();
		addAction(action);
		// script function create
		action = new ScriptFunctionCreateAction();
		addAction(action);
		// script function drop
		action = new ScriptFunctionDropAction();
		addAction(action);
		// schema function drop
		action = new SchemaFunctionDropAction();
		addAction(action);
		// schema function properties
		action = new SchemaFunctionPropertiesAction();
		addAction(action);
		// script view select
		action = new ScriptViewSelectAction();
		addAction(action);
		// script view create
		action = new ScriptViewCreateAction();
		addAction(action);
		// script view drop
		action = new ScriptViewDropAction();
		addAction(action);
		// schema view drop
		action = new SchemaViewDropAction();
		addAction(action);
		// schema view properties
		action = new SchemaViewPropertiesAction();
		addAction(action);
		// script column create
		action = new ScriptColumnCreateAction();
		addAction(action);
		// script column alter
		action = new ScriptColumnAlterAction();
		addAction(action);
		// script column drop
		action = new ScriptColumnDropAction();
		addAction(action);
		// er diagram
		action = new ToolsErDiagramGeneratorAction();
		addAction(action);
		// SQL History
		action = new SQLHistoryAction();
		addAction(action);
		// Exit
		action = new DBMExitAction();
		addAction(action);
		// Langaue
		action = new LanguageAction();
		addAction(action);
		// HELP
		action = new DBMAboutAciont();
		addAction(action);
	}

	/**
	 * [功 能] 动态增加处理事件action类
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param action
	 *            DBMBaseAction的子类
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void addAction(DBMBaseAction action) {
		actionList.put(action.getActionName(), action);
	}

	/**
	 * [功 能] 实现java awt的事件处理方法,该方法取出当前事件中的 actionCommand字符串,然后找到对应的事件Action对象
	 * 进行处理
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param actionEvent
	 *            action事件
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		String actionName = actionEvent.getActionCommand();
		if (actionName == null)
			return;

		DBMBaseAction action = (DBMBaseAction) actionList.get(actionName);
		if (action != null) {
			setCursorWait(actionName);
			action.processAction(this, actionEvent);
			setCursorDefault(actionName);
		}
	}

	/**
	 * [功 能] 设定鼠标成等待状态.
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param actionName
	 *            action名字
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	private void setCursorWait(String actionName) {
		if (isCursorChange(actionName)) {
			frame.setCursor(new Cursor(3));
		}
	}
	/**
	 * [功 能] 设定鼠标成默认状态.
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param actionName
	 *            action名字
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	private void setCursorDefault(String actionName) {
		if (isCursorChange(actionName)) {
			frame.setCursor(new Cursor(0));
		}
	}
	/**
	 * [功 能] 根据actionName判断鼠标成状态是否改变..
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param actionName
	 *            action名字
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	private boolean isCursorChange(String actionName) {
		if ("QUERY_ANALYZER".equals(actionName) || "TOOLS_ERDIAGRAMGENERATOR".equals(actionName) || "TOOLS_EXPORT".equals(actionName)
				|| "TOOLS_IMPORT".equals(actionName))
			return true;
		else
			return false;
	}
	/**
	 * @see com.livedoor.dbm.action.IActionHandler#setFrame()
	 */
	public void setFrame(DBMFrame frame) {
		this.frame = frame;

	}

	/**
	 * @see com.livedoor.dbm.action.IActionHandler#getFrame()
	 */
	public DBMFrame getFrame() {
		return this.frame;
	}
}

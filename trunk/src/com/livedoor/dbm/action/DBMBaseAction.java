/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.action;

import com.livedoor.dbm.components.mainframe.DBMFrame;
import java.awt.event.ActionEvent;

/**
 * <p>
 * Title: Action共同类
 * </p>
 * <p>
 * Description: 菜单条目事件处理基类Action
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
public abstract class DBMBaseAction {

	private String actionName;

	protected IActionHandler actionHandler;

	/**
	 * 抽象方法，每个菜单条目事件触发都会调用该方法,processAction() 由每个Action子类来实现,为具体事件处理业务逻辑;
	 */
	public abstract void processAction(DBMFrame frame, ActionEvent actionEvent);

	/**
	 * [功 能] 构造函数中参数为actionName，用于标识一个具体的菜单条目.
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param actionName
	 *            action 名字.
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public DBMBaseAction(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * [功 能] 每个菜单条目事件触发都会调用该方法,processAction().
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param actionHandler
	 *            IActionHandler
	 * 
	 * @param actionEvent
	 *            ActionEvent
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void processAction(IActionHandler actionHandler, ActionEvent actionEvent) {
		this.actionHandler = actionHandler;
		processAction(actionHandler.getFrame(), actionEvent);
	}

	
	/**
	 * [功 能] 返回事件的名称.
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return action 名字
	 *         <p>
	 */
	public String getActionName() {
		return actionName;
	}

}

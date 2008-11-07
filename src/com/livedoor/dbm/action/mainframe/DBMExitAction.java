/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.action.mainframe;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.db.DBMConnectionManager;

/**
 * <p>
 * Title: Action类
 * </p>
 * <p>
 * Description: 主窗口关闭事件.
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
public class DBMExitAction extends DBMBaseAction {

	public DBMExitAction() {
		super("EXIT");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		// 关闭所有的连接
		DBMConnectionManager.closeAllConnection();
		// 关闭主窗口
		frame.dispose();
		System.exit(0);

	}

}

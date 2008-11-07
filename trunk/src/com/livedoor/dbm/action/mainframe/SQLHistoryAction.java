/*
 * 创建时间 2006-09-29
 */
package com.livedoor.dbm.action.mainframe;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.mainframe.history.History;
/**
 * <p>
 * Title: Action类
 * </p>
 * <p>
 * Description: sql历史.
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
public class SQLHistoryAction extends DBMBaseAction {

	public SQLHistoryAction() {
		super("HISTORY");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		History.showHistory(frame);

	}

}

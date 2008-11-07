/**
 * $Id: ActionHistory.java,v 1.1 2006/10/17 01:10:32 lijian Exp $
 * 查询分析器工具栏按钮对应
 */
package com.livedoor.dbm.action.queryanalyzer;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.mainframe.history.History;

/**
 * <p>Title: 选择历史SQL</p> 
 * <p>Description: 选择历史sql</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:chep@livedoor.cn">chepeng</a>
 * @version 1.0
 */
public final class ActionHistory extends DBMBaseAction {
	private static final String ACTION_NAME = "HISTORY";

	public ActionHistory() {
		super(ACTION_NAME);
	}

	public void processAction(DBMFrame dbmframe, ActionEvent actionevent) {
		History.showHistory(dbmframe);
	}
}

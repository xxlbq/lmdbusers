/**
 * $Id: FindReplaceAction.java,v 1.1 2006/10/17 01:10:32 lijian Exp $
 * 查询分析器工具栏按钮对应
 */
package com.livedoor.dbm.action.queryanalyzer;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.DBMFrame;

/**
 * <p>Title: 查找替换</p> 
 * <p>Description: 在查询分析器中替换查找到的文本</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 *  
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class FindReplaceAction extends DBMBaseAction {

	private static final String ACTION_NAME = "FIND_REPLACE";

	public FindReplaceAction() {
		super(ACTION_NAME);
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		System.out.println(ACTION_NAME);
	}

}

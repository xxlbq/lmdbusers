/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.action.mainframe;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.mainframe.DBMAboutDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
/**
 * <p>
 * Title: About事件处理类
 * </p>
 * <p>
 * Description:显示About对话框.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author lijian
 * @version 1.0
 */
public class DBMAboutAciont extends DBMBaseAction {

	public DBMAboutAciont() {
		super("ABOUT");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		DBMAboutDialog about = new DBMAboutDialog(frame);
		about.show();

	}

}

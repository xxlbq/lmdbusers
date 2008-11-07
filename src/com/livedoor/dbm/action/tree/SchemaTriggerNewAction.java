package com.livedoor.dbm.action.tree;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;

public class SchemaTriggerNewAction extends DBMBaseAction {

	public SchemaTriggerNewAction() {
		super("SCHEMA_TRIGGER_NEW");
		// TODO 自动生成构造函数存根
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		DBMMessageDialog.showConfirmDialog("SCHEMA_TRIGGER_NEW");

	}

}

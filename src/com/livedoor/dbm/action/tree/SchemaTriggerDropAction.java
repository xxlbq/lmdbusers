package com.livedoor.dbm.action.tree;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;

public class SchemaTriggerDropAction extends DBMBaseAction {

	public SchemaTriggerDropAction() {
		super("SCHEMA_TRIGGER_DROP");
		// TODO 自动生成构造函数存根
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		DBMMessageDialog.showConfirmDialog("SCHEMA_TRIGGER_DROP");

	}


}

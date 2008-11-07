package com.livedoor.dbm.action.tree;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;

public class SchemaProcedureDropAction extends DBMBaseAction {

	public SchemaProcedureDropAction() {
		super("SCHEMA_PROCEDURE_DROP");
		// TODO 自动生成构造函数存根
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		DBMMessageDialog.showConfirmDialog("SCHEMA_PROCEDURE_DROP");

	}


}


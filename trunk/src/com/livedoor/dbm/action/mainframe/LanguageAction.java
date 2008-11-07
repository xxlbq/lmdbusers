/*
 * 创建时间 2006-09-29
 */
package com.livedoor.dbm.action.mainframe;

import java.awt.event.ActionEvent;
import java.util.Properties;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.i18n.ResourceI18n;
import com.livedoor.dbm.util.DBMPropertiesUtil;
/**
 * <p>
 * Title: Action类
 * </p>
 * <p>
 * Description: 国际化事件.
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
public class LanguageAction extends DBMBaseAction {
	public LanguageAction() {
		super("LANGUAGE");
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {

		int i = DBMMessageDialog.showConfirmDialogYes("change_language");

		Properties properties = DBMPropertiesUtil.getUIProperties();
		JCheckBoxMenuItem checkBoxMenuItem = (JCheckBoxMenuItem) actionEvent.getSource();

		resetItemStatus(frame, actionEvent);

		properties.put("Language", checkBoxMenuItem.getName());

		DBMPropertiesUtil.setUIProperties(properties);

	}
	/**
	 * [功 能] 重新设定语言子菜单的状态.
	 * <p>
	 * [作成日期] 2006/09/29
	 * <p>
	 * 
	 * @param frame
	 *            主窗体
	 * @param actionEvent
	 *            ActionEvent窗体
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	private void resetItemStatus(DBMFrame frame, ActionEvent actionEvent) {

		JMenu tools = frame.getJMenuBar().getMenu(4);

		JMenu language = (JMenu) tools.getItem(4);

		JCheckBoxMenuItem checkBoxMenuItem = (JCheckBoxMenuItem) actionEvent.getSource();
		checkBoxMenuItem.setState(true);

		String selectText = checkBoxMenuItem.getName();

		for (int i = 0; i < language.getItemCount(); i++) {
			JCheckBoxMenuItem checkBoxItem = (JCheckBoxMenuItem) language.getItem(i);
			if (!selectText.equals(checkBoxItem.getName())) {
				checkBoxItem.setState(false);
			}
		}

	}

}

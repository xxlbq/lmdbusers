/*
 * 创建时间 2006/09/18
 */
package com.livedoor.dbm;

import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import com.livedoor.dbm.action.DBMActionHandler;
import com.livedoor.dbm.action.IActionHandler;
import com.livedoor.dbm.components.mainframe.DBMFrame;

import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.i18n.ResourceI18n;
import com.livedoor.dbm.util.DBMComponentUtil;
import com.livedoor.dbm.util.DBMSystemInit;

/**
 * <p>
 * Title: main类
 * </p>
 * <p>
 * Description: 程序启动.
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
public class DBMMain {

	/**
	 * [功 能] 程序启动入口.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param args
	 *            命令行参数
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static void main(String[] args) {
		try {
			if (!DBMComponentUtil.isOSX()) {
				// UIManager.setLookAndFeel("org.fife.plaf.Office2003.Office2003LookAndFeel");
				UIManager.setLookAndFeel("org.fife.plaf.VisualStudio2005.VisualStudio2005LookAndFeel");
				// UIManager.setLookAndFeel("org.fife.plaf.OfficeXP.OfficeXPLookAndFeel");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		new DBMMain().start();
	}
	/**
	 * [功 能] 程序启动方法.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void start() {
		// init resource
		ResourceI18n.setTextBundle("com.livedoor.dbm.i18n.resource.dbmMessage");
		ResourceI18n.setImageBundle("com.livedoor.dbm.i18n.resource.dbmImages");

		IActionHandler actionHandler = new DBMActionHandler();
		JMenuBar menuBar = DBMSystemInit.getMenuBar(actionHandler);
		JToolBar toolBar = DBMSystemInit.getToolBar(actionHandler);
		JPanel statusBar = DBMSystemInit.getStatusBar();
		DBMSystemInit.initPopupMenus(actionHandler);
		DBMFrame mainFrame = new DBMFrame(ResourceI18n.getText("MAIN_FRAME_TITLE"), ResourceI18n.getImage("MAIN_FRAME"), menuBar, toolBar, statusBar);
		actionHandler.setFrame(mainFrame);
		DBMSystemInit.initLanguaeMenu(mainFrame);
		// 生成树对象
		mainFrame.addTreeTabPanel(ResourceI18n.getText("TREE_TAB_SERVERS"), null, getTreePanel(actionHandler));
		mainFrame.show();
	}
	/**
	 * [功 能] 得到存放树形结构的面板.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param actionHandler
	 *            IActionHandler
	 *            <p>
	 * 
	 * @return JPanel
	 *         <p>
	 */
	public JPanel getTreePanel(IActionHandler actionHandler) {

		return new DBMTreePaneView(actionHandler);
	}

}

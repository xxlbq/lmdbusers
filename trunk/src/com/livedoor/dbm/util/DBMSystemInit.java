package com.livedoor.dbm.util;

import java.util.Locale;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.livedoor.dbm.action.IActionHandler;
import com.livedoor.dbm.action.mainframe.DBMBaseShowAction;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.i18n.ResourceI18n;

/**
 * 系统初始化类，用于产生主框架类所需要的 MenuBar,ToolBar等组件
 */
public class DBMSystemInit {

	/**
	 * 得到主窗口的菜单条对象，每个条目的事件和 actionHandler监听类绑定
	 */
	public static JMenuBar getMenuBar(IActionHandler actionHandler) {
		JMenuBar menuBar = new JMenuBar();
		// File Menu
		JMenu jmenu = new JMenu(ResourceI18n.getText("MENU_FILE"));
		jmenu.setMnemonic('F');
		// 1.1 New Query
		JMenuItem jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_FILE_NEW_QUERY"), ResourceI18n.getImage("MENU_NEW")));
		jmenuitem.setMnemonic('N');
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(78, 2, false));
		jmenuitem.setActionCommand("NEW_QUERY");
		jmenuitem.addActionListener(actionHandler);
		// 1.2 open Script
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_FILE_OPEN_SCRIPT"), ResourceI18n.getImage("MENU_OPEN")));
		jmenuitem.setMnemonic('O');
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(79, 2, false));
		jmenuitem.setActionCommand("OPEN_QUERY");
		jmenuitem.addActionListener(actionHandler);
		// 1.3 save query
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_FILE_SAVE_QUERY"), ResourceI18n.getImage("MENU_SAVE")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_FILE_SAVE_QUERY_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(83, 2, false));
		jmenuitem.setActionCommand("SAVE_QUERY");
		jmenuitem.addActionListener(actionHandler);
		// 1.4 save query as
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_FILE_SAVE_QUERY_AS"), ResourceI18n.getImage("MENU_SAVEAS")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_FILE_SAVE_QUERY_AS_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(83, 3, false));
		jmenuitem.setActionCommand("SAVE_QUERY_AS");
		jmenuitem.addActionListener(actionHandler);
        // 1.5 save query result
        jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_FILE_SAVE_RESULTS"), ResourceI18n.getImage("MENU_SAVERESULTS")));
        jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_FILE_SAVE_RESULTS_MNEM"));
        jmenuitem.setAccelerator(KeyStroke.getKeyStroke(82, 2, false));
        jmenuitem.setActionCommand("SAVE_RESULTS");
        jmenuitem.addActionListener(actionHandler);
        jmenu.addSeparator();
		// 1.6 exit
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_FILE_EXIT"), ResourceI18n.getImage("MENU_EMPTY")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_FILE_EXIT_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(115, 8, false));
		jmenuitem.setActionCommand("EXIT");
		jmenuitem.addActionListener(actionHandler);

		menuBar.add(jmenu);

		// 2 Edit
		jmenu = new JMenu(ResourceI18n.getText("MENU_EDIT"));
		jmenu.setMnemonic('E');

		// 2.1 Cut
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_EDIT_CUT"), ResourceI18n.getImage("MENU_CUT")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_EDIT_CUT_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(88, 2, false));
		jmenuitem.setActionCommand("CUT");
		jmenuitem.addActionListener(actionHandler);
		// 2.2 copy
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_EDIT_COPY"), ResourceI18n.getImage("MENU_COPY")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_EDIT_COPY_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(67, 2, false));
		jmenuitem.setActionCommand("COPY");
		jmenuitem.addActionListener(actionHandler);

		// 2.3 PASTE
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_EDIT_PASTE"), ResourceI18n.getImage("MENU_PASTE")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_EDIT_PASTE_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(86, 2, false));
		jmenuitem.setActionCommand("PASTE");
		jmenuitem.addActionListener(actionHandler);
		jmenu.addSeparator();
		// 2.4 UNDO
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_EDIT_UNDO"), ResourceI18n.getImage("MENU_UNDO")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_EDIT_UNDO_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(90, 2, false));
		jmenuitem.setActionCommand("UNDO");
		jmenuitem.addActionListener(actionHandler);
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_EDIT_REDO"), ResourceI18n.getImage("MENU_REDO")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_EDIT_REDO_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(89, 2, false));
		jmenuitem.setActionCommand("REDO");
		jmenuitem.addActionListener(actionHandler);
		jmenu.addSeparator();
		// 2.5 SELECT ALL
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_EDIT_SELECTALL"), ResourceI18n.getImage("MENU_SELECTALL")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_EDIT_SELECTALL_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(65, 2, false));
		jmenuitem.setActionCommand("SELECTALL");
		jmenuitem.addActionListener(actionHandler);
		jmenu.addSeparator();
		// 2.6 EDIT FIND
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_EDIT_FIND"), ResourceI18n.getImage("MENU_FIND")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_EDIT_FIND_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(70, 2, false));
		jmenuitem.setActionCommand("FIND");
		jmenuitem.addActionListener(actionHandler);
		jmenu.addSeparator();
        //2.7 TO LOWER CASE
        jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_EDIT_TOLOWER"), ResourceI18n.getImage("MENU_TOLOWER")));
        jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_EDIT_TOLOWER_MNEM"));
        jmenuitem.setAccelerator(KeyStroke.getKeyStroke(76, 2, false));
        jmenuitem.setActionCommand("TOLOWER");
        jmenuitem.addActionListener(actionHandler);
        //2.8 TO UPPER CASE
        jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_EDIT_TOUPPER"), ResourceI18n.getImage("MENU_TOUPPER")));
        jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_EDIT_TOUPPER_MNEM"));
        jmenuitem.setAccelerator(KeyStroke.getKeyStroke(85, 2, false));
        jmenuitem.setActionCommand("TOUPPER");
        jmenuitem.addActionListener(actionHandler);
        jmenu.addSeparator();
		// 2.9 Format
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_EDIT_FORMAT"), ResourceI18n.getImage("MENU_FORMAT")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_EDIT_FORMAT_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(70, 3, false));
		jmenuitem.setActionCommand("FORMAT");
		jmenuitem.addActionListener(actionHandler);
		jmenu.addSeparator();
		menuBar.add(jmenu);

		// 3 SERVER
		jmenu = new JMenu(ResourceI18n.getText("MENU_SERVER"));
		// 3.1 Register Server
		jmenu.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_MNEM"));
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_REGISTER_SERVER"), ResourceI18n.getImage("MENU_REGSERVER")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_REGISTER_SERVER_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(155, 0, false));
		jmenuitem.setActionCommand("REGISTER_SERVER_NEW");
		jmenuitem.addActionListener(actionHandler);

		// 3.2 UnRegister Server
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_UNREGISTER_SERVER"), ResourceI18n
				.getImage("MENU_UNREGSERVER")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_UNREGISTER_SERVER_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(127, 0, false));
		jmenuitem.setActionCommand("REGISTER_SERVER_DELETE");
		jmenuitem.addActionListener(actionHandler);
		// 3.3 Server Properties
		jmenuitem = jmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_SERVER_PROPERTIES"), ResourceI18n.getImage("MENU_PROPERTIES")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_SERVER_PROPERTIES_MNEM"));
		jmenuitem.setActionCommand("REGISTER_SERVER_PROPERTIES");
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(10, 8, false));
		jmenuitem.addActionListener(actionHandler);
		jmenu.addSeparator();
		// 3.4 connect
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_CONNECT"), ResourceI18n.getImage("MENU_CONNECT")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_CONNECT_MNEM"));
		jmenuitem.setActionCommand("SERVER_CONNECT");
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(155, 2, false));
		jmenuitem.addActionListener(actionHandler);
		// 3.5 disconnect
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_DISCONNECT"), ResourceI18n.getImage("MENU_DISCONNECT")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_DISCONNECT_MNEM"));
		jmenuitem.setActionCommand("SERVER_DISCONNECT");
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(127, 2, false));
		jmenuitem.addActionListener(actionHandler);
		jmenu.addSeparator();
		// 3.6 query analyzier
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_QUERY_ANALYZER"), ResourceI18n.getImage("MENU_ANALYZER")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_QUERY_ANALYZER_MNEM"));
		jmenuitem.setActionCommand("QUERY_ANALYZER");
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(81, 2, false));
		jmenuitem.addActionListener(actionHandler);

		menuBar.add(jmenu);
		// 4 QUERY
		jmenu = new JMenu(ResourceI18n.getText("MENU_QUERY"));
		jmenu.setMnemonic(ResourceI18n.getCharInt("MENU_QUERY_MNEM"));
		// 4.1 execute
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_QUERY_RUN"), ResourceI18n.getImage("MENU_EXECUTE")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_QUERY_RUN_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(69, 2, false));
		jmenuitem.setActionCommand("EXECUTE");
		jmenuitem.addActionListener(actionHandler);
		// 4.2 execute explain
		jmenuitem = jmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_QUERY_EXECUTEEXPLAIN"), ResourceI18n.getImage("MENU_EXECUTEEXPLAIN")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_QUERY_EXECUTEEXPLAIN_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(10, 3, false));
		jmenuitem.setActionCommand("EXECUTEEXPLAIN");
		jmenuitem.addActionListener(actionHandler);
		// 4.3 stop
        jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_QUERY_STOP"),
        ResourceI18n.getImage("MENU_STOP")));
        jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_QUERY_STOP_MNEM"));
        jmenuitem.setAccelerator(KeyStroke.getKeyStroke(8, 2, false));
        jmenuitem.setActionCommand("CANCEL");
        jmenuitem.addActionListener(actionHandler);
		jmenu.addSeparator();
		// 4.4Auto commit
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_QUERY_AUTOCOMMIT"), ResourceI18n.getImage("MENU_AUTOCOMMIT")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_QUERY_AUTOCOMMIT_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(65, 3, false));
		jmenuitem.setActionCommand("AUTOCOMMIT");
		jmenuitem.addActionListener(actionHandler);
		// 4.5Commit
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_QUERY_COMMIT"), ResourceI18n.getImage("MENU_COMMIT")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_QUERY_COMMIT_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(67, 3, false));
		jmenuitem.setActionCommand("COMMIT");
		jmenuitem.addActionListener(actionHandler);
		// 4.6rollback
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_QUERY_ROLLBACK"), ResourceI18n.getImage("MENU_ROLLBACK")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_QUERY_ROLLBACK_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(82, 3, false));
		jmenuitem.setActionCommand("ROLLBACK");
		jmenuitem.addActionListener(actionHandler);
		jmenu.addSeparator();
		// 4.7 sql history
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_QUERY_HISTORY"), ResourceI18n.getImage("MENU_HISTORY")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_QUERY_HISTORY_MNEM"));
		jmenuitem.setActionCommand("HISTORY");
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(72, 3, false));
		jmenuitem.addActionListener(actionHandler);

        // 4.8 显示文本结果
        jmenuitem = jmenu.add(new JCheckBoxMenuItem(ResourceI18n.getText("MENU_SHOW_TEXT"), ResourceI18n.getImage("MENU_SHOW_TEXT"), DBMBaseShowAction.bShowText));
        jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_QUERY_SHOW_TEXT_MNEM"));
        jmenuitem.setActionCommand("SHOW_TEXT");
        jmenuitem.setAccelerator(KeyStroke.getKeyStroke(84, 3, false));
        jmenuitem.addActionListener(actionHandler);

        // 4.9 显示表格结果
        jmenuitem = jmenu.add(new JCheckBoxMenuItem(ResourceI18n.getText("MENU_SHOW_GRID"), ResourceI18n.getImage("MENU_SHOW_GRID"), DBMBaseShowAction.bShowGrid));
        jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_QUERY_SHOW_GRID_MNEM"));
        jmenuitem.setActionCommand("SHOW_GRID");
        jmenuitem.setAccelerator(KeyStroke.getKeyStroke(71, 3, false));
        jmenuitem.addActionListener(actionHandler);
        
		menuBar.add(jmenu);

		// 5 TOOLS
		jmenu = new JMenu(ResourceI18n.getText("MENU_TOOLS"));
		jmenu.setMnemonic(ResourceI18n.getCharInt("MENU_TOOLS_MNEM"));
		// 5.1 import
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TOOLS_IMPORT_DATA"), ResourceI18n.getImage("MENU_IMPORT")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_TOOLS_IMPORT_DATA_MNEM"));
		jmenuitem.setActionCommand("TOOLS_IMPORT");
		jmenuitem.addActionListener(actionHandler);
		// 5.2 export
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TOOLS_EXPORT_DATA"), ResourceI18n.getImage("MENU_EXPORT")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_TOOLS_EXPORT_DATA_MNEM"));
		jmenuitem.setActionCommand("TOOLS_EXPORT");
		jmenuitem.addActionListener(actionHandler);
		// 5.3 ER DIAGRAM GENERATOR
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TOOLS_ERDIAGRAM_GENERATOR"), ResourceI18n
				.getImage("MENU_ERDIAGRAMGENERATOR")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_TOOLS_ERDIAGRAM_GENERATOR_MNEM"));
		jmenuitem.setActionCommand("TOOLS_ERDIAGRAMGENERATOR");
		jmenuitem.addActionListener(actionHandler);
		jmenu.addSeparator();

		// 5.4 Language
		JMenu jmenu1 = new JMenu(ResourceI18n.getText("MENU_LANGUAGE"));
		jmenuitem = jmenu.add(jmenu1);
		// 5.4.1
		jmenuitem = jmenu1.add(new JCheckBoxMenuItem(ResourceI18n.getText("MENU_LANGUAGE_ENGLISH")));
		jmenuitem.setActionCommand("LANGUAGE");
		jmenuitem.setName("en");
		jmenuitem.addActionListener(actionHandler);

		jmenuitem = jmenu1.add(new JCheckBoxMenuItem(ResourceI18n.getText("MENU_LANGUAGE_CHINESE")));
		jmenuitem.setActionCommand("LANGUAGE");
		jmenuitem.setName("zh");
		jmenuitem.addActionListener(actionHandler);

		jmenuitem = jmenu1.add(new JCheckBoxMenuItem(ResourceI18n.getText("MENU_LANGUAGE_JAPANESE")));
		jmenuitem.setActionCommand("LANGUAGE");
		jmenuitem.setName("ja");
		jmenuitem.addActionListener(actionHandler);

		menuBar.add(jmenu);
		
		//6 Help
		jmenu = new JMenu(ResourceI18n.getText("MENU_HELP"));
        jmenu.setMnemonic(ResourceI18n.getCharInt("MENU_HELP_MNEM"));
        jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_HELP_ABOUT"), ResourceI18n.getImage("MENU_ABOUT")));
        jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_HELP_ABOUT_MNEM"));
        jmenuitem.setActionCommand("ABOUT");
        jmenuitem.addActionListener(actionHandler);
        menuBar.add(jmenu);
        
		return menuBar;
	}

	/**
	 * 得到主窗口的工具条对象，每个条目的事件和 actionHandler监听类绑定
	 */
	public static JToolBar getToolBar(IActionHandler actionHandler) {
		JToolBar jtoolbar = new JToolBar();
		jtoolbar.putClientProperty("jgoodies.headerStyle", Boolean.TRUE);
		jtoolbar.setRollover(true);
		jtoolbar.setFocusable(false);
		jtoolbar.setFloatable(false);
		JButton jbutton = new JButton("", ResourceI18n.getImage("MENU_REGSERVER"));
		jbutton.setActionCommand("REGISTER_SERVER_NEW");
		jbutton.addActionListener(actionHandler);
		jbutton.setHorizontalTextPosition(0);
		jbutton.setVerticalTextPosition(3);
		jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_REGISTER_SERVER"));
		jtoolbar.add(jbutton);
		jbutton = new JButton("", ResourceI18n.getImage("MENU_UNREGSERVER"));
		jbutton.setActionCommand("REGISTER_SERVER_DELETE");
		jbutton.addActionListener(actionHandler);
		jbutton.setHorizontalTextPosition(0);
		jbutton.setVerticalTextPosition(3);
		jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_UNREGISTER_SERVER"));
		jtoolbar.add(jbutton);
		jbutton = new JButton("", ResourceI18n.getImage("MENU_PROPERTIES"));
		jbutton.setActionCommand("REGISTER_SERVER_PROPERTIES");
		jbutton.addActionListener(actionHandler);
		jbutton.setHorizontalTextPosition(0);
		jbutton.setVerticalTextPosition(3);
		jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_SERVER_PROPERTIES"));
		jtoolbar.add(jbutton);
		jtoolbar.addSeparator();
		jbutton = new JButton("", ResourceI18n.getImage("MENU_CONNECT"));
		jbutton.setActionCommand("SERVER_CONNECT");
		jbutton.addActionListener(actionHandler);
		jbutton.setHorizontalTextPosition(0);
		jbutton.setVerticalTextPosition(3);
		jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_CONNECT"));
		jtoolbar.add(jbutton);
		jbutton = new JButton("", ResourceI18n.getImage("MENU_DISCONNECT"));
		jbutton.setActionCommand("SERVER_DISCONNECT");
		jbutton.addActionListener(actionHandler);
		jbutton.setHorizontalTextPosition(0);
		jbutton.setVerticalTextPosition(3);
		jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_DISCONNECT"));
		jtoolbar.add(jbutton);
		jtoolbar.addSeparator();
        // 打开查询分析器
        jbutton = new JButton("", ResourceI18n.getImage("MENU_ANALYZER"));
		jbutton.setActionCommand("QUERY_ANALYZER");
		jbutton.addActionListener(actionHandler);
		jbutton.setHorizontalTextPosition(0);
		jbutton.setVerticalTextPosition(3);
		jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_QUERY_ANALYZER"));
		jtoolbar.add(jbutton);
        jtoolbar.addSeparator();
        
        //显示历史
        jbutton = new JButton("", ResourceI18n.getImage("MENU_HISTORY"));
        jbutton.setActionCommand("HISTORY");
        jbutton.addActionListener(actionHandler);
        jbutton.setHorizontalTextPosition(0);
        jbutton.setVerticalTextPosition(3);
        jbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_QUERY_HISTORY"));
        jtoolbar.add(jbutton);
        //显示文本结果
        JToggleButton jtbutton = new JToggleButton("", ResourceI18n.getImage("MENU_SHOW_TEXT"), DBMBaseShowAction.bShowText);
        jtbutton.setActionCommand("SHOW_TEXT");
        jtbutton.addActionListener(actionHandler);
        jtbutton.setHorizontalTextPosition(0);
        jtbutton.setVerticalTextPosition(3);
        jtbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_SHOW_TEXT"));
        jtoolbar.add(jtbutton);
        //显示表格结果
        jtbutton = new JToggleButton("", ResourceI18n.getImage("MENU_SHOW_GRID"), DBMBaseShowAction.bShowGrid);
        jtbutton.setActionCommand("SHOW_GRID");
        jtbutton.addActionListener(actionHandler);
        jtbutton.setHorizontalTextPosition(0);
        jtbutton.setVerticalTextPosition(3);
        jtbutton.setToolTipText(ResourceI18n.getText("TOOLBAR_SHOW_GRID"));
        jtoolbar.add(jtbutton);
        
		return jtoolbar;
	}

	/**
	 * 得到主窗口的状态条对象
	 */
	public static JPanel getStatusBar() {
		return new JPanel();

	}

	/**
	 * 初使化弹出菜单
	 */

	public static void initPopupMenus(IActionHandler actionHandler) {

		int i = 2;

		/*
		 * DirRootNode
		 */
		JPopupMenu jpopupmenu = new JPopupMenu();

		// register server
		JMenuItem jmenuitem = null;
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_REGISTER_SERVER"), ResourceI18n
				.getImage("MENU_REGSERVER")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_REGISTER_SERVER_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(155, 0, false));
		jmenuitem.setActionCommand("REGISTER_SERVER_NEW");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		// query aoalyzer
		jmenuitem = jpopupmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_QUERY_ANALYZER"), ResourceI18n.getImage("MENU_ANALYZER")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_QUERY_ANALYZER_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(81, i, false));
		jmenuitem.setActionCommand("QUERY_ANALYZER");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		// refresh
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_REFRESH"), ResourceI18n.getImage("MENU_REFRESH")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_REFRESH_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(116, 0, false));
		jmenuitem.setActionCommand("TREE_REFRESH");
		jmenuitem.addActionListener(actionHandler);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.DBMRootNode", jpopupmenu);

		/*
		 * connectNode
		 */
		jpopupmenu = new JPopupMenu();

		// connect
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_CONNECT"), ResourceI18n.getImage("MENU_CONNECT")));
		jmenuitem.setMnemonic(67);
		jmenuitem.setActionCommand("SERVER_CONNECT");
		jmenuitem.addActionListener(actionHandler);

		// disConnect
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_DISCONNECT"), ResourceI18n.getImage("MENU_DISCONNECT")));
		jmenuitem.setMnemonic(68);
		jmenuitem.setActionCommand("SERVER_DISCONNECT");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		initGeneralSchemaMenu(jpopupmenu, actionHandler, true);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.DBMConnectionNode", jpopupmenu);

		/*
		 * dataBasesNode
		 */
		jpopupmenu = new JPopupMenu();

		// create database
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_DATABASE_CREATE"), ResourceI18n
				.getImage("MENU_SCHEMA_DATABASE_NEW")));
		jmenuitem.setMnemonic(73);
		jmenuitem.setActionCommand("SCHEMA_DATABASE_NEW");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		initGeneralSchemaMenu(jpopupmenu, actionHandler, true);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlDatabasesNode", jpopupmenu);

		/*
		 * schema / Management /Security
		 */
		jpopupmenu = new JPopupMenu();

		initGeneralSchemaMenu(jpopupmenu, actionHandler, true);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleManagementNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleSchemasNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleSchemaNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2SchemasNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2SchemaNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2ManagementNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlManagementNode", jpopupmenu);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlSecurityNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2SecurityNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleSecurityNode", jpopupmenu);

		/*
		 * database of mysql
		 */
		jpopupmenu = new JPopupMenu();
		// create database
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_DATABASE_CREATE"), ResourceI18n
				.getImage("MENU_SCHEMA_DATABASE_NEW")));
		jmenuitem.setMnemonic(73);
		jmenuitem.setActionCommand("SCHEMA_DATABASE_NEW");
		jmenuitem.addActionListener(actionHandler);

		// delete database
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_DATABASE_DELETE"), ResourceI18n
				.getImage("MENU_SCHEMA_DATABASE_DROP")));
		jmenuitem.setMnemonic(68);
		jmenuitem.setActionCommand("SCHEMA_DATABASE_DROP");
		jmenuitem.addActionListener(actionHandler);
		jpopupmenu.addSeparator();

		// database properties
		//jmenuitem = jpopupmenu
		//		.add(new JMenuItem(ResourceI18n.getText("MENU_DATABASE_PROPERTIES"), ResourceI18n.getImage("MENU_PROPERTIES")));
		//jmenuitem.setMnemonic(86);
		//jmenuitem.setActionCommand("SCHEMA_DATABASE_PROPERTIES");
		//jmenuitem.addActionListener(actionHandler);

		//jpopupmenu.addSeparator();

		// script
		JMenu jmenu = new JMenu(ResourceI18n.getText("MENU_DATABASE_SCRIPT_WINDOW_AS"));
		jmenuitem = jpopupmenu.add(jmenu);
		jmenuitem.setMnemonic(87);
		// create script
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_DATABASE_SCRIPT_WINDOW_AS_CREATE"), ResourceI18n
				.getImage("MENU_SCRIPT_CREATE")));
		jmenuitem.setMnemonic(67);
		jmenuitem.setActionCommand("SCRIPT_DATABASE_CREATE");
		jmenuitem.addActionListener(actionHandler);
		// drop script
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_DATABASE_SCRIPT_WINDOW_AS_DROP"), ResourceI18n
				.getImage("MENU_SCRIPT_DROP")));
		jmenuitem.setMnemonic(68);
		jmenuitem.setActionCommand("SCRIPT_DATABASE_DROP");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		initGeneralSchemaMenu1(jpopupmenu, actionHandler);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlDatabaseNode", jpopupmenu);

		/*
		 * tables
		 */
		jpopupmenu = new JPopupMenu();
		// create table
		jmenuitem = jpopupmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_CREATE"), ResourceI18n.getImage("MENU_SCHEMA_TABLE_NEW")));
		jmenuitem.setMnemonic(73);
		jmenuitem.setActionCommand("SCHEMA_TABLE_NEW");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		initGeneralSchemaMenu(jpopupmenu, actionHandler, false);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlTablesNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2TablesNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleTablesNode", jpopupmenu);

		/*
		 * views
		 */

		jpopupmenu = new JPopupMenu();
		// create view
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_VIEWS_CREATE"),
		// ResourceI18n.getImage("MENU_SCHEMA_VIEW_NEW")));
		// jmenuitem.setMnemonic(73);
		// jmenuitem.setActionCommand("SCHEMA_VIEW_NEW");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jpopupmenu.addSeparator();

		initGeneralSchemaMenu(jpopupmenu, actionHandler, false);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlViewsNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2ViewsNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleViewsNode", jpopupmenu);

		/*
		 * Triggers
		 */

		jpopupmenu = new JPopupMenu();
		// create triggers
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_TRIGGER_CREATE"),
		// ResourceI18n.getImage("MENU_SCHEMA_TRIGGER_NEW")));
		// jmenuitem.setMnemonic(73);
		// jmenuitem.setActionCommand("SCHEMA_TRIGGER_NEW");
		// jmenuitem.addActionListener(actionHandler);

		// jpopupmenu.addSeparator();

		initGeneralSchemaMenu(jpopupmenu, actionHandler, false);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlTriggersNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2TriggersNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleTriggersNode", jpopupmenu);

		/*
		 * Functions
		 */
		jpopupmenu = new JPopupMenu();
		// create function
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_FUNCTION_CREATE"),
		// ResourceI18n.getImage("MENU_SCHEMA_FUNCTION_NEW")));
		// jmenuitem.setMnemonic(73);
		// jmenuitem.setActionCommand("SCHEMA_FUNCTION_NEW");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jpopupmenu.addSeparator();

		initGeneralSchemaMenu(jpopupmenu, actionHandler, false);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlFunctionsNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2FunctionsNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleFunctionsNode", jpopupmenu);

		/*
		 * Procedures
		 */
		jpopupmenu = new JPopupMenu();
		// create procedure
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_PROCEDURE_CREATE"),
		// ResourceI18n.getImage("MENU_SCHEMA_PROCEDURE_NEW")));
		// jmenuitem.setMnemonic(73);
		// jmenuitem.setActionCommand("SCHEMA_PROCEDURE_NEW");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jpopupmenu.addSeparator();

		initGeneralSchemaMenu(jpopupmenu, actionHandler, false);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlProceduresNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2ProceduresNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleProceduresNode", jpopupmenu);

		/*
		 * table
		 */

		jpopupmenu = new JPopupMenu();
		// create table
		jmenuitem = jpopupmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_CREATE"), ResourceI18n.getImage("MENU_SCHEMA_TABLE_NEW")));
		jmenuitem.setMnemonic(73);
		jmenuitem.setActionCommand("SCHEMA_TABLE_NEW");
		jmenuitem.addActionListener(actionHandler);

		// alter table
		jmenuitem = jpopupmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_ALTER"), ResourceI18n.getImage("MENU_SCHEMA_TABLE_EDIT")));
		jmenuitem.setMnemonic(85);
		jmenuitem.setActionCommand("SCHEMA_TABLE_ALTER");
		jmenuitem.addActionListener(actionHandler);

		// drop table
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_DROP"), ResourceI18n.getImage("MENU_SCHEMA_TABLE_DROP")));
		jmenuitem.setMnemonic(68);
		jmenuitem.setActionCommand("SCHEMA_TABLE_DROP");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		// edit table data
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_EDIT_TABLE_DATA"), ResourceI18n
				.getImage("MENU_EDIT_TABLEDATA")));
		jmenuitem.setMnemonic(69);
		jmenuitem.setActionCommand("EDIT_TABLE_DATA");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		// table properties
		//jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_PROPERTIES"), ResourceI18n.getImage("MENU_PROPERTIES")));
		//jmenuitem.setMnemonic(86);
		//jmenuitem.setActionCommand("SCHEMA_TABLE_PROPERTIES");
		//jmenuitem.addActionListener(actionHandler);

		//jpopupmenu.addSeparator();

		// script
		jmenu = new JMenu(ResourceI18n.getText("MENU_DATABASE_SCRIPT_WINDOW_AS"));
		jmenuitem = jpopupmenu.add(jmenu);
		jmenuitem.setMnemonic(87);

		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_SCRIPT_SELECT_ALL"), ResourceI18n
				.getImage("MENU_AUTO_SELECTSTARSTMT")));
		jmenuitem.setMnemonic(69);
		jmenuitem.setActionCommand("SCRIPT_TABLE_SELECT_ALL");
		jmenuitem.addActionListener(actionHandler);
		jmenuitem = jmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_SCRIPT_SELECT"), ResourceI18n.getImage("MENU_AUTO_SELECTSTMT")));
		jmenuitem.setMnemonic(83);
		jmenuitem.setActionCommand("SCRIPT_TABLE_SELECT");
		jmenuitem.addActionListener(actionHandler);
		jmenuitem = jmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_SCRIPT_INSERT"), ResourceI18n.getImage("MENU_AUTO_INSERTSTMT")));
		jmenuitem.setMnemonic(73);
		jmenuitem.setActionCommand("SCRIPT_INSERT");
		jmenuitem.addActionListener(actionHandler);
		jmenuitem = jmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_SCRIPT_UPDATE"), ResourceI18n.getImage("MENU_AUTO_UPDATESTMT")));
		jmenuitem.setMnemonic(85);
		jmenuitem.setActionCommand("SCRIPT_UPDATE");
		jmenuitem.addActionListener(actionHandler);
		jmenuitem = jmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_SCRIPT_DELETE"), ResourceI18n.getImage("MENU_AUTO_DELETESTMT")));
		jmenuitem.setMnemonic(68);
		jmenuitem.setActionCommand("SCRIPT_DELETE");
		jmenuitem.addActionListener(actionHandler);
		jmenu.addSeparator();
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_SCRIPT_CREATE"), ResourceI18n.getImage("MENU_SCRIPT_CREATE")));
		jmenuitem.setMnemonic(67);
		jmenuitem.setActionCommand("SCRIPT_TABLE_CREATE");
		jmenuitem.addActionListener(actionHandler);

		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_SCRIPT_ALTER"), ResourceI18n.getImage("MENU_SCRIPT_ALTER")));
		jmenuitem.setMnemonic(65);
		jmenuitem.setActionCommand("SCRIPT_TABLE_ALTER");
		jmenuitem.addActionListener(actionHandler);
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TABLE_SCRIPT_DROP"), ResourceI18n.getImage("MENU_SCRIPT_DROP")));
		jmenuitem.setMnemonic(68);
		jmenuitem.setActionCommand("SCRIPT_TABLE_DROP");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		initGeneralSchemaMenu1(jpopupmenu, actionHandler);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlTableNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2TableNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleTableNode", jpopupmenu);

		/*
		 * view
		 */
		jpopupmenu = new JPopupMenu();
		// create view
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_VIEWS_CREATE"),
		// ResourceI18n.getImage("MENU_SCHEMA_VIEW_NEW")));
		// jmenuitem.setMnemonic(73);
		// jmenuitem.setActionCommand("SCHEMA_VIEW_NEW");
		// jmenuitem.addActionListener(actionHandler);
		// drop view
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_VIEWS_DROP"),
		// ResourceI18n.getImage("MENU_SCHEMA_VIEW_DROP")));
		// jmenuitem.setMnemonic(68);
		// jmenuitem.setActionCommand("SCHEMA_VIEW_DROP");
		// jmenuitem.addActionListener(actionHandler);
		// jpopupmenu.addSeparator();

		// view properties
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_VIEWS_PROPERTIES"),
		// ResourceI18n.getImage("MENU_PROPERTIES")));
		// jmenuitem.setMnemonic(86);
		// jmenuitem.setActionCommand("SCHEMA_VIEW_PROPERTIES");
		// jmenuitem.addActionListener(actionHandler);
		// jpopupmenu.addSeparator();

		// script
		// jmenu = new
		// JMenu(ResourceI18n.getText("MENU_DATABASE_SCRIPT_WINDOW_AS"));
		// jmenuitem = jpopupmenu.add(jmenu);
		// jmenuitem.setMnemonic(87);
		//
		// // view select
		// jmenuitem = jmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_VIEW_SELECT"),
		// ResourceI18n.getImage("MENU_AUTO_SELECTSTMT")));
		// jmenuitem.setMnemonic(83);
		// jmenuitem.setActionCommand("SCRIPT_VIEW_SELECT");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jmenu.addSeparator();
		//
		// // view create
		// jmenuitem = jmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_VIEW_CREATE"),
		// ResourceI18n.getImage("MENU_SCRIPT_CREATE")));
		// jmenuitem.setMnemonic(67);
		// jmenuitem.setActionCommand("SCRIPT_VIEW_CREATE");
		// jmenuitem.addActionListener(actionHandler);
		//
		// // view drop
		// jmenuitem = jmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_VIEW_DROP"),
		// ResourceI18n.getImage("MENU_SCRIPT_DROP")));
		// jmenuitem.setMnemonic(68);
		// jmenuitem.setActionCommand("SCRIPT_VIEW_DROP");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jpopupmenu.addSeparator();
		//
		// initGeneralSchemaMenu1(jpopupmenu, actionHandler);
		//
		// DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlViewNode",
		// jpopupmenu);
		// DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2ViewNode",
		// jpopupmenu);
		// DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleViewNode",
		// jpopupmenu);

		/*
		 * function
		 */
		jpopupmenu = new JPopupMenu();
		// create function
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_FUNCTION_CREATE"),
		// ResourceI18n.getImage("MENU_SCHEMA_FUNCTION_NEW")));
		// jmenuitem.setMnemonic(73);
		// jmenuitem.setActionCommand("SCHEMA_FUNCTION_NEW");
		// jmenuitem.addActionListener(actionHandler);
		//
		// // drop function
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_FUNCTION_DROP"),
		// ResourceI18n.getImage("MENU_SCHEMA_FUNCTION_DROP")));
		// jmenuitem.setMnemonic(73);
		// jmenuitem.setActionCommand("SCHEMA_FUNCTION_DROP");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jpopupmenu.addSeparator();
		//
		// // function properties
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_FUNCTION_PROPERTIES"),
		// ResourceI18n.getImage("MENU_PROPERTIES")));
		// jmenuitem.setMnemonic(86);
		// jmenuitem.setActionCommand("SCHEMA_FUNCTION_PROPERTIES");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jpopupmenu.addSeparator();
		//
		// Script
		// jmenu = new
		// JMenu(ResourceI18n.getText("MENU_DATABASE_SCRIPT_WINDOW_AS"));
		// jmenuitem = jpopupmenu.add(jmenu);
		// jmenuitem.setMnemonic(83);
		//
		// // script create
		// jmenuitem = jmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_FUNCTION_SCRIPT_CREATE"),
		// ResourceI18n.getImage("MENU_SCRIPT_CREATE")));
		// jmenuitem.setMnemonic(67);
		// jmenuitem.setActionCommand("SCRIPT_FUNCTION_CREATE");
		// jmenuitem.addActionListener(actionHandler);
		//
		// // script drop
		// jmenuitem = jmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_FUNCTION_SCRIPT_DROP"),
		// ResourceI18n.getImage("MENU_SCRIPT_DROP")));
		// jmenuitem.setMnemonic(68);
		// jmenuitem.setActionCommand("SCRIPT_FUNCTION_DROP");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jmenu.addSeparator();
		//
		// // script execute
		// jmenuitem = jmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_FUNCTION_SCRIPT_EXECUTE"),
		// ResourceI18n.getImage("MENU_SCRIPT_EXECUTE")));
		// jmenuitem.setMnemonic(69);
		// jmenuitem.setActionCommand("SCRIPT_FUNCTION_EXECUTE");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jpopupmenu.addSeparator();
		//
		// initGeneralSchemaMenu1(jpopupmenu, actionHandler);
		//
		// DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlFunctionNode",
		// jpopupmenu);
		// DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2FunctionNode",
		// jpopupmenu);
		// DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleFunctionNode",
		// jpopupmenu);

		/*
		 * Trigger
		 */
		jpopupmenu = new JPopupMenu();
		// create trigger
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_TRIGGER_CREATE"),
		// ResourceI18n.getImage("MENU_SCHEMA_TRIGGER_NEW")));
		// jmenuitem.setMnemonic(73);
		// jmenuitem.setActionCommand("SCHEMA_TRIGGER_NEW");
		// jmenuitem.addActionListener(actionHandler);
		//
		// // drop trigger
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_TRIGGER_DROP"),
		// ResourceI18n.getImage("MENU_SCHEMA_TRIGGER_DROP")));
		// jmenuitem.setMnemonic(68);
		// jmenuitem.setActionCommand("SCHEMA_TRIGGER_DROP");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jpopupmenu.addSeparator();
		//
		// // trigger properties
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_TRIGGER_PROPERTIES"),
		// ResourceI18n.getImage("MENU_PROPERTIES")));
		// jmenuitem.setMnemonic(86);
		// jmenuitem.setActionCommand("SCHEMA_TRIGGER_PROPERTIES");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jpopupmenu.addSeparator();
		//
		// script
		// jmenu = new
		// JMenu(ResourceI18n.getText("MENU_DATABASE_SCRIPT_WINDOW_AS"));
		// jmenuitem = jpopupmenu.add(jmenu);
		// jmenuitem.setMnemonic(87);
		//
		// // script create
		// jmenuitem = jmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_TRIGGER_SCRIPT_CREATE"),
		// ResourceI18n.getImage("MENU_SCRIPT_CREATE")));
		// jmenuitem.setMnemonic(67);
		// jmenuitem.setActionCommand("SCRIPT_TRIGGER_CREATE");
		// jmenuitem.addActionListener(actionHandler);
		//
		// // script drop
		// jmenuitem = jmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_TRIGGER_SCRIPT_DROP"),
		// ResourceI18n.getImage("MENU_SCRIPT_DROP")));
		// jmenuitem.setMnemonic(68);
		// jmenuitem.setActionCommand("SCRIPT_TRIGGER_DROP");
		//
		// jpopupmenu.addSeparator();
		//
		// initGeneralSchemaMenu1(jpopupmenu, actionHandler);
		//
		// DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlTriggerNode",
		// jpopupmenu);
		// DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2TriggerNode",
		// jpopupmenu);
		// DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleTriggerNode",
		// jpopupmenu);

		/*
		 * Procedure
		 */
		jpopupmenu = new JPopupMenu();
		// create procedure
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_PROCEDURE_CREATE"),
		// ResourceI18n.getImage("MENU_SCHEMA_PROCEDURE_NEW")));
		// jmenuitem.setMnemonic(73);
		// jmenuitem.setActionCommand("SCHEMA_PROCEDURE_NEW");
		// jmenuitem.addActionListener(actionHandler);
		//
		// // drop procedure
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_PROCEDURE_DROP"),
		// ResourceI18n.getImage("MENU_SCHEMA_PROCEDURE_DROP")));
		// jmenuitem.setMnemonic(68);
		// jmenuitem.setActionCommand("SCHEMA_PROCEDURE_DROP");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jpopupmenu.addSeparator();
		//
		// // procedure properties
		// jmenuitem = jpopupmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_PROCEDURE_PROPERTIES"),
		// ResourceI18n.getImage("MENU_PROPERTIES")));
		// jmenuitem.setMnemonic(86);
		// jmenuitem.setActionCommand("SCHEMA_PROCEDURE_PROPERTIES");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jpopupmenu.addSeparator();

		// script
		// jmenu = new
		// JMenu(ResourceI18n.getText("MENU_DATABASE_SCRIPT_WINDOW_AS"));
		// jmenuitem = jpopupmenu.add(jmenu);
		// jmenuitem.setMnemonic(87);
		//
		// // script create
		// jmenuitem = jmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_PROCEDURE_SCRIPT_CREATE"),
		// ResourceI18n.getImage("MENU_SCRIPT_CREATE")));
		// jmenuitem.setMnemonic(67);
		// jmenuitem.setActionCommand("SCRIPT_PROCEDURE_CREATE");
		// jmenuitem.addActionListener(actionHandler);
		//
		// // script drop
		// jmenuitem = jmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_PROCEDURE_SCRIPT_DROP"),
		// ResourceI18n.getImage("MENU_SCRIPT_DROP")));
		// jmenuitem.setMnemonic(68);
		// jmenuitem.setActionCommand("SCRIPT_PROCEDURE_DROP");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jmenu.addSeparator();
		//
		// // script execute
		// jmenuitem = jmenu.add(new
		// JMenuItem(ResourceI18n.getText("MENU_PROCEDURE_SCRIPT_EXECUTE"),
		// ResourceI18n.getImage("MENU_SCRIPT_EXECUTE")));
		// jmenuitem.setMnemonic(69);
		// jmenuitem.setActionCommand("SCRIPT_PROCEDURE_EXECUTE");
		// jmenuitem.addActionListener(actionHandler);
		//
		// jpopupmenu.addSeparator();
		//
		// initGeneralSchemaMenu1(jpopupmenu, actionHandler);
		//
		// DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlProcedureNode",
		// jpopupmenu);
		// DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2ProcedureNode",
		// jpopupmenu);
		// DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleProcedureNode",
		// jpopupmenu);

		/*
		 * columns
		 */
		jpopupmenu = new JPopupMenu();
		initGeneralSchemaMenu(jpopupmenu, actionHandler, true);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlColumnsNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2ColumnsNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleColumnsNode", jpopupmenu);

		/*
		 * column
		 */
		jpopupmenu = new JPopupMenu();
		// script
		jmenu = new JMenu(ResourceI18n.getText("MENU_DATABASE_SCRIPT_WINDOW_AS"));
		jmenuitem = jpopupmenu.add(jmenu);
		jmenuitem.setMnemonic(87);

		// script create
		jmenuitem = jmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_COLUMN_SCRIPT_CREATE"), ResourceI18n.getImage("MENU_SCRIPT_CREATE")));
		jmenuitem.setMnemonic(67);
		jmenuitem.setActionCommand("SCRIPT_COLUMN_CREATE");
		jmenuitem.addActionListener(actionHandler);

		// script alter
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_COLUMN_SCRIPT_ALTER"), ResourceI18n.getImage("MENU_SCRIPT_ALTER")));
		jmenuitem.setMnemonic(65);
		jmenuitem.setActionCommand("SCRIPT_COLUMN_ALTER");
		jmenuitem.addActionListener(actionHandler);

		// script drop
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_COLUMN_SCRIPT_DROP"), ResourceI18n.getImage("MENU_SCRIPT_DROP")));
		jmenuitem.setMnemonic(68);
		jmenuitem.setActionCommand("SCRIPT_COLUMN_DROP");
		jmenuitem.addActionListener(actionHandler);

		initGeneralSchemaMenu1(jpopupmenu, actionHandler);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlColumnNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2ColumnNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleColumnNode", jpopupmenu);

		/*
		 * CurrentActivityNode/processInfos/Locks/
		 */
		jpopupmenu = new JPopupMenu();
		initGeneralSchemaMenu(jpopupmenu, actionHandler, true);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlCurrentActivityNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2CurrentActivityNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleCurrentActivityNode", jpopupmenu);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlProcessInfosNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2ProcessInfosNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleProcessInfosNode", jpopupmenu);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlLocksNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2LocksNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleLocksNode", jpopupmenu);

		/*
		 * processInfo/Lock
		 */
		jpopupmenu = new JPopupMenu();
		initGeneralSchemaMenu1(jpopupmenu, actionHandler);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlProcessInfoNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2ProcessInfoNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleProcessInfoNode", jpopupmenu);

		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlLockNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2LockNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleLockNode", jpopupmenu);

		/*
		 * Users/Groups/User/Group
		 */
		jpopupmenu = new JPopupMenu();
		initGeneralSchemaMenu(jpopupmenu, actionHandler, false);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlUsersNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2UsersNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2GroupsNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleUsersNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.mysql.MySqlUserNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2UserNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.db2.DB2GroupNode", jpopupmenu);
		DBMPopupMenuList.setPopupMenu("com.livedoor.dbm.components.tree.oracle.OracleUserNode", jpopupmenu);
	}
	/**
	 * 初使化共同菜单.
	 * 
	 * @param jpopupmenu
	 * @param actionHandler
	 */
	private static void initGeneralSchemaMenu(JPopupMenu jpopupmenu, IActionHandler actionHandler, boolean isUnRegister) {
		// clone
		JMenuItem jmenuitem = null;
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_REGISTER_SERVER_CLONE"), ResourceI18n
				.getImage("MENU_CLONESERVER")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_REGISTER_SERVER_CLONE_MNEM"));
		jmenuitem.setActionCommand("REGISTER_SERVER_CLONE");
		jmenuitem.addActionListener(actionHandler);
		jpopupmenu.addSeparator();

		// register server
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_REGISTER_SERVER"), ResourceI18n
				.getImage("MENU_REGSERVER")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_REGISTER_SERVER_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(155, 0, false));
		jmenuitem.setActionCommand("REGISTER_SERVER_NEW");
		jmenuitem.addActionListener(actionHandler);

		if (isUnRegister == true) {
			// unRegister Server
			jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_UNREGISTER_SERVER"), ResourceI18n
					.getImage("MENU_UNREGSERVER")));
			jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_UNREGISTER_SERVER_MNEM"));
			jmenuitem.setAccelerator(KeyStroke.getKeyStroke(127, 0, false));
			jmenuitem.setActionCommand("REGISTER_SERVER_DELETE");
			jmenuitem.addActionListener(actionHandler);
		}

		// server properties
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_SERVER_PROPERTIES"), ResourceI18n
				.getImage("MENU_PROPERTIES")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_SERVER_PROPERTIES_MNEM"));
		jmenuitem.setActionCommand("REGISTER_SERVER_PROPERTIES");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		// tools
		JMenu jmenu = new JMenu(ResourceI18n.getText("MENU_TOOLS"));
		jmenuitem = jpopupmenu.add(jmenu);
		jmenuitem.setMnemonic(84);
		// import data
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TOOLS_IMPORT_DATA"), ResourceI18n.getImage("MENU_IMPORT")));
		jmenuitem.setMnemonic(73);
		jmenuitem.setActionCommand("TOOLS_IMPORT");
		jmenuitem.addActionListener(actionHandler);
		// export data
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TOOLS_EXPORT_DATA"), ResourceI18n.getImage("MENU_EXPORT")));
		jmenuitem.setMnemonic(69);
		jmenuitem.setActionCommand("TOOLS_EXPORT");
		jmenuitem.addActionListener(actionHandler);

		jmenu.addSeparator();

		// er
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TOOLS_ERDIAGRAM_GENERATOR"), ResourceI18n
				.getImage("MENU_ERDIAGRAMGENERATOR")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_TOOLS_ERDIAGRAM_GENERATOR_MNEM"));
		jmenuitem.setActionCommand("TOOLS_ERDIAGRAMGENERATOR");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		// query aoalyzer
		jmenuitem = jpopupmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_QUERY_ANALYZER"), ResourceI18n.getImage("MENU_ANALYZER")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_QUERY_ANALYZER_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(81, 2, false));
		jmenuitem.setActionCommand("QUERY_ANALYZER");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		// refresh
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_REFRESH"), ResourceI18n.getImage("MENU_REFRESH")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_REFRESH_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(116, 0, false));
		jmenuitem.setActionCommand("TREE_REFRESH");
		jmenuitem.addActionListener(actionHandler);

	}

	/**
	 * 初使化共同菜单.
	 * 
	 * @param jpopupmenu
	 * @param actionHandler
	 */
	private static void initGeneralSchemaMenu1(JPopupMenu jpopupmenu, IActionHandler actionHandler) {
		JMenuItem jmenuitem = null;
		// server properties
		jmenuitem = jpopupmenu.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_SERVER_PROPERTIES"), ResourceI18n
				.getImage("MENU_PROPERTIES")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_SERVER_PROPERTIES_MNEM"));
		jmenuitem.setActionCommand("REGISTER_SERVER_PROPERTIES");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		// tools
		JMenu jmenu = new JMenu(ResourceI18n.getText("MENU_TOOLS"));
		jmenuitem = jpopupmenu.add(jmenu);
		jmenuitem.setMnemonic(84);
		// import data
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TOOLS_IMPORT_DATA"), ResourceI18n.getImage("MENU_IMPORT")));
		jmenuitem.setMnemonic(73);
		jmenuitem.setActionCommand("TOOLS_IMPORT");
		jmenuitem.addActionListener(actionHandler);
		// export data
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TOOLS_EXPORT_DATA"), ResourceI18n.getImage("MENU_EXPORT")));
		jmenuitem.setMnemonic(69);
		jmenuitem.setActionCommand("TOOLS_EXPORT");
		jmenuitem.addActionListener(actionHandler);

		jmenu.addSeparator();

		// er
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("MENU_TOOLS_ERDIAGRAM_GENERATOR"), ResourceI18n
				.getImage("MENU_ERDIAGRAMGENERATOR")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_TOOLS_ERDIAGRAM_GENERATOR_MNEM"));
		jmenuitem.setActionCommand("TOOLS_ERDIAGRAMGENERATOR");
		jmenuitem.addActionListener(actionHandler);

		jpopupmenu.addSeparator();

		// query aoalyzer
		jmenuitem = jpopupmenu
				.add(new JMenuItem(ResourceI18n.getText("MENU_SERVER_QUERY_ANALYZER"), ResourceI18n.getImage("MENU_ANALYZER")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("MENU_SERVER_QUERY_ANALYZER_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(81, 2, false));
		jmenuitem.setActionCommand("QUERY_ANALYZER");
		jmenuitem.addActionListener(actionHandler);
	}
	/**
	 * 初使化语言菜单.
	 * 
	 * @param frame:DBMFrame
	 */
	public static void initLanguaeMenu(DBMFrame frame) {

		JMenu language = (JMenu) frame.getJMenuBar().getMenu(4).getItem(4);

		Properties properties = DBMPropertiesUtil.getUIProperties();

		String selectText = properties.getProperty("Language");
		if (StringUtil.isEmpty(selectText)) {
			selectText = Locale.getDefault().getLanguage().toLowerCase();
		}

		for (int i = 0; i < language.getItemCount(); i++) {
			JCheckBoxMenuItem checkBoxItem = (JCheckBoxMenuItem) language.getItem(i);
			if (selectText.equals(checkBoxItem.getName())) {
				checkBoxItem.setState(true);

			}
		}

	}

}

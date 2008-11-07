package com.livedoor.dbm.components.tree;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.livedoor.dbm.action.IActionHandler;
import com.livedoor.dbm.util.DBMSchemaTreeInit;
/**
 * <p>
 * Description: DBMTreePaneView
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DBMTreePaneView extends JPanel {
	private DBMTree dbmTree;
	/**
	 * [機 能] DBMTreePaneView
	 * [解 説] DBMTreePaneView
	 * @param actionHandler
	 */
	public DBMTreePaneView(IActionHandler actionHandler) {
		this.dbmTree = DBMSchemaTreeInit.getTree(actionHandler);
		this.setLayout(new BorderLayout());
		this.add("Center", new JScrollPane(dbmTree));

	}
	/**
	 * [機 能] 获取 DBMTree
	 * [解 説] 获取 DBMTree
	 * @return dbmTree
	 */

	public DBMTree getDBMTree() {
		return this.dbmTree;
	}
}

/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

/**
 * <p>
 * Title: 执行计划
 * </p>
 * <p>
 * Description: DbManager sql 语句执行计划
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author YuanBaoKun
 * @version 1.0
 */
public abstract class JDBMTabPanel extends JDBMPanel {
	
	/**
	 * [功 能] 查询计划Panel
	 * <p>
	 * [说 明] oracle, db2等需要返回表格的数据库查询计划Panel的超类
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	JTreeTable treeTable;
	
	/*
	public void initialize() {
		// TODO Auto-generated method stub

	}

	public void createTable(String table) {
		// TODO Auto-generated method stub
		
	}

	public void createTables() {
		// TODO Auto-generated method stub
		
	}
*/
}

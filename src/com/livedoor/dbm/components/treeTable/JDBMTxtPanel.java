/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import javax.swing.JComponent;

import com.livedoor.dbm.components.queryanalyzer.TextResultPane;

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
public class JDBMTxtPanel extends JDBMPanel {

	/**
	 * [功 能] 查询计划Panel
	 * <p>
	 * [说 明] Mysql等需要返回文本的数据库查询计划Panel的超类
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	TextResultPane sqlPane = null;
	
	/**
	 * [功 能] 初始化
	 * <p>
	 * [说 明] 初始化当前组件
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @return 无
	 *         <p>
	 * @see com.livedoor.dbm.components.treeTable.PanelInterface#initialize()	 
	 */
	public void initialize() {
	}
}

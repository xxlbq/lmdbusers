/*
 * 创建时间 2006-09-10
 */
package com.livedoor.dbm.components.common;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.Icon;

/**
 * <p>
 * Title: 标签面板
 * </p>
 * <p>
 * Description: 通用标签面板.
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
public abstract class DBMTabbedPane extends JTabbedPane {

	/**
	 * [功 能] 1.创建tab. 2.得到具体类的tabCount. 3.根据tabCount循环． 4.得到具休类的tabTitle,
	 * 得到具体类的tabIcon, 得到具体类的tabPanel tab增加一个标签.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void initComponents() {
		for (int i = 0; i < getTabCounts(); i++) {
			addTab(getTabTitle(i), getTabIcon(i), getTabPanel(i));
		}
	}

	/**
	 * [功 能] 得到tab的个数.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return tab的个数
	 *         <p>
	 */
	public abstract int getTabCounts();

	/**
	 * [功 能] 返回每个条目的JPanel对象.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param i
	 *            条目数
	 *            <p>
	 * 
	 * @return 每个条目的JPanel对象
	 *         <p>
	 */
	public abstract JPanel getTabPanel(int i);

	/**
	 * [功 能] 返回每个条目的标题.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param i
	 *            条目数
	 *            <p>
	 * 
	 * @return 每个条目的标题
	 *         <p>
	 */
	public abstract String getTabTitle(int i);

	/**
	 * [功 能] 返回每个条目的图形.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param i
	 *            条目数
	 *            <p>
	 * 
	 * @return 每个条目的图形
	 *         <p>
	 */
	public abstract Icon getTabIcon(int i);
}

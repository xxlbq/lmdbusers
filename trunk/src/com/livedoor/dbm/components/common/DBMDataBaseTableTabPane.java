/*
 * 创建时间 2006-09-10
 */
package com.livedoor.dbm.components.common;

/**
 * <p>
 * Title: 标签
 * </p>
 * <p>
 * Description: 创建数据库和创建表的基类．
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
public abstract class DBMDataBaseTableTabPane extends DBMTabbedPane {

	/**
	 * [功 能] 在基类的tab上再加一个共同的sqlTab. ,并实现MouseAdapter ．给tab增加一个mouseReleased 事件．
	 * 在调用之前, 先调用基类的initComponents().
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param 无
	 * 
	 * <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void initComponents() {
	}

	/**
	 * [功 能] 在切换标鉴的时候和点ok时调用．生成SQL.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param 无
	 * 
	 * <p>
	 * 
	 * @return DBMValidateResult
	 *         <p>
	 */
	public abstract DBMValidateResult validateForm();

}

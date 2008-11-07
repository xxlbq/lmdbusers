/*
 * 创建时间 2006-09-10
 */
package com.livedoor.dbm.components.common;

import javax.swing.Icon;
import javax.swing.JPanel;

/**
 * <p>
 * Title: 标签
 * </p>
 * <p>
 * Description: 生成数据库的tabPane对象
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
public class DBMDatabaseTabPane extends DBMDataBaseTableTabPane {

	private static final long serialVersionUID = -2275603589777163409L;

	/**
	 * [功 能] 数据库的校验方法.
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
	public DBMValidateResult validateForm() {
		return null;
	}

	@Override
	public int getTabCounts() {
		return 0;
	}

	@Override
	public Icon getTabIcon(int i) {
		return null;
	}

	@Override
	public JPanel getTabPanel(int i) {
		return null;
	}

	@Override
	public String getTabTitle(int i) {
		return null;
	}

}

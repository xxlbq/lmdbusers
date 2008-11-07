/*
 * 创建时间 2006-09-10
 */
package com.livedoor.dbm.components.common;

import javax.swing.Icon;
import javax.swing.JPanel;
import java.util.List;
/**
 * <p>
 * Title: 对话框
 * </p>
 * <p>
 * Description: 创建表的对话框的标签组件.
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
public class DBMTableTabPane extends DBMDataBaseTableTabPane {

	private static final long serialVersionUID = 2979404497329205829L;

	/**
	 * [功 能] 根据不同的数据库生成clomun表 和clomumDetails表
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param dataType
	 *            数据类型，固定值
	 * @param datas
	 *            表中的数据，用于修改时得到 所有的数据
	 * @param tableType
	 *            (1,insert;2,update;3,propertis)
	 *            <p>
	 * 
	 * @return JPanel
	 *         <p>
	 */
	public JPanel getColumnTablePanel(List dataType, List datas, int tableType) {
		return null;
	}

	/**
	 * [功 能] 根据不同的数据库生成不同的 database.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param 无
	 * 
	 * 
	 * @return JPanel
	 *         <p>
	 */
	public JPanel getDatabasePanel() {
		return null;
	}

	@Override
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

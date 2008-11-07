/*
 * 创建时间 2006-09-10
 */
package com.livedoor.dbm.components.common;

import java.util.List;

import javax.swing.JTabbedPane;

import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Title: 对话框
 * </p>
 * <p>
 * Description: 创建表对话筐组件.
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
public class DBMTableDialog extends DBMCommonDialog {

	private static final long serialVersionUID = 7436775192466122009L;

	/**
	 * [功 能] 构造器.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param frame
	 *            主窗体
	 * @param title
	 *            标题
	 * @param weight
	 *            宽
	 * @param height
	 *            高
	 * @param connection
	 *            连接信息
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public DBMTableDialog(DBMFrame frame, String title, int weight, int height, ConnectionInfo connection) {
		super(frame, title, weight, height, connection);
	}

	@Override
	public JTabbedPane getTab() {
		return null;
	}

	@Override
	public List getButtonList() {
		return null;
	}

}

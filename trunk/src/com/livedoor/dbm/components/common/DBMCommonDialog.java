/*
 * 创建时间 2006-09-10
 */
package com.livedoor.dbm.components.common;

import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.connection.ConnectionInfo;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

/**
 * <p>
 * Title: 对话框
 * </p>
 * <p>
 * Description: 通用对话筐组件.
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
public abstract class DBMCommonDialog extends DBMDialog {

	private ConnectionInfo connection;

	/**
	 * [功 能] 构造器.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param frame
	 *            主窗体
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
	public DBMCommonDialog(DBMFrame frame, String title, int weight, int height, ConnectionInfo connection) {

		super(frame, title, weight, height);

		this.connection = connection;
	}
	/**
	 * [功 能] 得到具体类的JTabbedPane对象.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return JTabbedPane 连接信息
	 *         <p>
	 */
	public abstract JTabbedPane getTab();
	/**
	 * [功 能] 得到具体类的按纽集合.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return JTabbedPane 连接信息
	 *         <p>
	 */
	public abstract List getButtonList();

	/**
	 * [功 能] 初始化处理各个组件.
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

		List buttonList = getButtonList();
		JPanel buttonpanle = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		for (int i = 0; i < buttonList.size(); i++) {
			buttonpanle.add((JButton) (buttonList.get(i)));
		}
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getTab(), BorderLayout.CENTER);
		getContentPane().add(buttonpanle, BorderLayout.SOUTH);

	}
	/**
	 * [功 能] 得到连接信息.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return connection 连接信息
	 *         <p>
	 */
	public ConnectionInfo getConnectionInfo() {
		return this.connection;
	}

}

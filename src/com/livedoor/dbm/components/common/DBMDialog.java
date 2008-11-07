/*
 * 创建时间 2006-09-10
 */
package com.livedoor.dbm.components.common;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JDialog;
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
public class DBMDialog extends JDialog {

	private static final long serialVersionUID = -6735055462509717467L;
	/**
	 * [功 能] 构造器.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param s
	 *            标题
	 * @param i
	 *            宽
	 * @param j
	 *            高
	 * 
	 * <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public DBMDialog(Frame frame, String s, int i, int j) {
		super(frame, true);
		setResizable(false);
		initDialog(frame, s, i, j);
	}
	/**
	 * [功 能] 初始化对话框.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param window
	 *            窗体
	 * @param s
	 *            标题
	 * @param i
	 *            宽
	 * @param j
	 *            高
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	private void initDialog(Window window, String s, int i, int j) {
		reSize(window, i, j);
		setTitle(s);
	}

	/**
	 * [功 能] 重新设定对话框的大小.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param window
	 *            窗体
	 * 
	 * @param i
	 *            宽
	 * @param j
	 *            高
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void reSize(Window window, int i, int j) {
		reSize(((Window) (this)), window, i, j);
	}
	/**
	 * [功 能] 重新设定对话框的大小.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param window
	 *            窗体
	 * 
	 * @param i
	 *            宽
	 * @param j
	 *            高
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static void reSize(Window window, Window window1, int i, int j) {
		if (window1 == null) {
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			window.setBounds((dimension.width - i) / 2, (dimension.height - j) / 2, i, j);
		} else {
			Rectangle rectangle = window1.getBounds();
			window.setBounds(rectangle.x + (rectangle.width - i) / 2, rectangle.y + (rectangle.height - j) / 2, i, j);
		}
	}

}

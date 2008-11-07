/*
 * 创建时间 2006-09-10
 */
package com.livedoor.dbm.components.common;

import java.awt.Component;

import javax.swing.JOptionPane;

import com.livedoor.dbm.i18n.ResourceI18n;
/**
 * <p>
 * Title: 对话框
 * </p>
 * <p>
 * Description: 通用消息对话筐组件.
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
public class DBMMessageDialog {

	/**
	 * [功 能] 显示错误消息.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param str
	 *            资源文件中的资源id或者是要显示的字符串.
	 *            <p>
	 * 
	 * @return 无.
	 *         <p>
	 */
	public static void showErrorMessageDialog(String str) {
		try {
			str = ResourceI18n.getText(str);
		} catch (Exception e) {
			str = str;
		}

		JOptionPane.showMessageDialog(null, str, ResourceI18n.getText("MAIN_FRAME_TITLE"), JOptionPane.ERROR_MESSAGE);
	}

    /**
     * [功 能] 显示错误消息.
     * <p>
     * [作成日期] 2006-09-10
     * <p>
     * @param parent 父窗口
     * @param str
     *            资源文件中的资源id或者是要显示的字符串.
     *            <p>
     * 
     * @return 无.
     *         <p>
     */
    public static void showErrorMessageDialog(Component parent,String str) {
        try {
            str = ResourceI18n.getText(str);
        } catch (Exception e) {
            str = str;
        }

        JOptionPane.showMessageDialog(parent, str, ResourceI18n.getText("MAIN_FRAME_TITLE"), JOptionPane.ERROR_MESSAGE);
    }
	/**
	 * [功 能] 显示确认消息，有yes和no两个按纽.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param str
	 *            资源文件中的资源id或者是要显示的字符串.
	 *            <p>
	 * 
	 * @return 0 yes,1 no
	 *         <p>
	 */
	public static int showConfirmDialog(String msg) {
		try {
			msg = ResourceI18n.getText(msg);
		} catch (Exception e) {
			msg = msg;
		}
		Object[] options = {"YES", "NO"};

		return JOptionPane.showOptionDialog(null, msg, ResourceI18n.getText("MAIN_FRAME_TITLE"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[1]);
	}

	/**
	 * [功 能] 显示确认消息，有yes一个按纽.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * 
	 * @param str
	 *            资源文件中的资源id或者是要显示的字符串.
	 *            <p>
	 * 
	 * @return 0 yes
	 *         <p>
	 */
	public static int showConfirmDialogYes(String msg) {
		try {
			msg = ResourceI18n.getText(msg);
		} catch (Exception e) {
			msg = msg;
		}
		Object[] options = {"YES"};

		return JOptionPane.showOptionDialog(null, msg, ResourceI18n.getText("MAIN_FRAME_TITLE"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);
	}

	/**
	 * [功 能] 显示确认消息，有yes和no和cancle三个按纽.
	 * <p>
	 * [作成日期] 2006-09-10
	 * <p>
	 * @param parent TODO
	 * @param str
	 *            资源文件中的资源id或者是要显示的字符串.
	 *            <p>
	 * 
	 * @return 0 yes,1 no,2cancle
	 *         <p>
	 */
	public static int showConfirmDialogYNC(Component parent, String msg) {
		try {
			msg = ResourceI18n.getText(msg);
		} catch (Exception e) {
			msg = msg;
		}
		Object[] options = {"YES", "NO", "CANCEL"};

		return JOptionPane.showOptionDialog(parent, msg, ResourceI18n.getText("MAIN_FRAME_TITLE"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[2]);
	}

}

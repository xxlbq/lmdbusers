/*
 * 创建时间 2006/09/18
 */
package com.livedoor.dbm.components.mainframe;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.livedoor.dbm.components.common.DBMDialog;
import com.livedoor.dbm.i18n.ResourceI18n;
/**
 * <p>
 * Title: About对话框.
 * </p>
 * <p>
 * Description: 显示About对话框
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author lijian
 * @version 1.0
 */
public class DBMAboutDialog extends DBMDialog {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel9 = null;
	private JPanel jPanel = null;
	public DBMAboutDialog(Frame frame) {
		super(frame, "About Prapiroon DBA", 600, 400);
		initialize();
	}

	/**
	 * [功 能] 初使化面板.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	private void initialize() {
		jLabel9 = new JLabel();
		jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel9.setBounds(new Rectangle(2, 130, 580, 24));
		jLabel9.setText(ResourceI18n.getText("THANK"));
		jLabel8 = new JLabel();
		jLabel8.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel8.setBounds(new Rectangle(2, 87, 580, 24));
		jLabel8.setText(ResourceI18n.getText("TRANSLATE"));
		jLabel7 = new JLabel();
		jLabel7.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel7.setBounds(new Rectangle(1, 59, 580, 24));
		jLabel7.setText(ResourceI18n.getText("QUALITY"));
		jLabel6 = new JLabel();
		jLabel6.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel6.setBounds(new Rectangle(1, 33, 580, 24));
		jLabel6.setText(ResourceI18n.getText("TEST"));
		jLabel5 = new JLabel();
		jLabel5.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel5.setBounds(new Rectangle(1, 7, 580, 24));
		jLabel5.setText(ResourceI18n.getText("DEVELOP"));
		jLabel4 = new JLabel();
		jLabel4.setBounds(new Rectangle(0, 181, 602, 24));
		jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel4.setText(ResourceI18n.getText("COPYRIGHT"));
		jLabel3 = new JLabel();
		jLabel3.setBounds(new Rectangle(0, 139, 602, 24));
		jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel3.setText(ResourceI18n.getText("INTRODUCE2"));
		jLabel2 = new JLabel();
		jLabel2.setBounds(new Rectangle(0, 115, 602, 24));
		jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel2.setText(ResourceI18n.getText("INTRODUCE1"));
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(0, 77, 602, 24));
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel1.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel1.setText(ResourceI18n.getText("MAIN_FRAME_TITLE"));

		jLabel = new JLabel(ResourceI18n.getImage("MAIN_FRAME"));
		jLabel.setBounds(new Rectangle(265, 12, 69, 58));
		jLabel.setText("");

		this.setSize(604, 441);
		this.setLayout(null);
		this.setFont(new Font("Dialog", Font.BOLD, 12));
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(jLabel2, null);
		this.add(jLabel3, null);
		this.add(jLabel4, null);
		this.add(getJPanel(), null);
	}

	/**
	 * [功 能] 得到jPanel.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return jPanel
	 *         <p>
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(10, 231, 584, 182));
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel8, null);
			jPanel.add(jLabel7, null);
			jPanel.add(jLabel9, null);
		}
		return jPanel;
	}
}

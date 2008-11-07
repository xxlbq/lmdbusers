/*
 * 创建时间 2006/09/18
 */
package com.livedoor.dbm.components.mainframe;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.livedoor.dbm.components.common.DBMDialog;
import com.livedoor.dbm.components.tree.DBMConnectionNode;
import com.livedoor.dbm.components.tree.DBMRootNode;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.connection.ConnectionInfo;
/**
 * <p>
 * Title: 对话框
 * </p>
 * <p>
 * Description: 选择服务的对话框.
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
public class ChooseServerDialog extends DBMDialog {

	private static final long serialVersionUID = -2412598269596580297L;

	private JPanel jContentPane = null;

	private JLabel jLabel_ChooseServer = null;

	private JComboBox jComboBox_ChooseServer = null;

	private JButton jButton_ok = null;

	private JButton jButton_Cancel = null;

	private DBMFrame frame = null;

	private Vector conn = new Vector();

	private Vector comboBoxData = new Vector();

	private ConnectionInfo connectionInfo = null;

	private boolean isOk = false;

	/**
	 * [功 能] 构造器.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param frame
	 *            主窗体
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public ChooseServerDialog(DBMFrame frame) {
		super(frame, "Choose Server", 397, 218);
		this.frame = frame;
		initializeVector();
		initialize();
	}

	/**
	 * [功 能] 初使化对话框.
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

		this.setContentPane(getJContentPane());
		
		if(jComboBox_ChooseServer.getItemCount()==0){
			jButton_ok.setEnabled(false);
		}
	}

	/**
	 * [功 能] 初使化jContentPane.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return JPanel
	 *         <p>
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel_ChooseServer = new JLabel();
			jLabel_ChooseServer.setBounds(new Rectangle(42, 40, 114, 18));
			jLabel_ChooseServer.setFont(new Font("Dialog", Font.BOLD, 14));
			jLabel_ChooseServer.setText("Choose Server:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel_ChooseServer, null);
			jContentPane.add(getJComboBox_ChooseServer(), null);
			jContentPane.add(getJButton_ok(), null);
			jContentPane.add(getJButton_Cancel(), null);
		}
		return jContentPane;
	}

	/**
	 * [功 能] 初使化jComboBox_ChooseServer.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return JComboBox
	 *         <p>
	 */
	private JComboBox getJComboBox_ChooseServer() {
		if (jComboBox_ChooseServer == null) {
			jComboBox_ChooseServer = new JComboBox(comboBoxData);
			jComboBox_ChooseServer.setBounds(new Rectangle(41, 77, 150, 24));
		}
		return jComboBox_ChooseServer;
	}

	/**
	 * [功 能] 初使化jButton_ok.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return jButton_ok
	 *         <p>
	 */
	private JButton getJButton_ok() {
		if (jButton_ok == null) {
			jButton_ok = new JButton("ok");
			jButton_ok.setBounds(new Rectangle(171, 130, 65, 24));

			jButton_ok.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					isOk = true;
					connectionInfo = (ConnectionInfo) conn.get(jComboBox_ChooseServer.getSelectedIndex());
					hide();
				}

			});

		}
		return jButton_ok;
	}

	/**
	 * [功 能] 初使化jButton_Cacle.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return jButton_Cancel
	 *         <p>
	 */
	private JButton getJButton_Cancel() {
		if (jButton_Cancel == null) {
			jButton_Cancel = new JButton("cancel");
			jButton_Cancel.setBounds(new Rectangle(270, 130, 70, 24));
			jButton_Cancel.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					isOk = false;
					hide();
				}

			});
		}
		return jButton_Cancel;
	}
	/**
	 * [功 能] 初使化comboBoxData的值.
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
	private void initializeVector() {

		JPanel treeView = frame.getTreePanel();

		DBMRootNode root = (DBMRootNode) ((DBMTreePaneView) treeView).getDBMTree().getModel().getRoot();
		List childerList = root.getChildrenList();

		if (childerList != null) {
			for (int i = 0; i < childerList.size(); i++) {
				DBMConnectionNode connectNode = (DBMConnectionNode) childerList.get(i);
				comboBoxData.add((String) connectNode.getId());
				conn.add(connectNode.getConnectionInfo());
			}
		}

	}
	/**
	 * [功 能] 得到连接信息.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return connectionInfo
	 *         <p>
	 */
	public ConnectionInfo getConnectionInfo() {
		return connectionInfo;
	}
	/**
	 * [功 能] 得到是否点击OK的标志.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return isOk
	 *         <p>
	 */
	public boolean isOk() {
		return isOk;
	}

}
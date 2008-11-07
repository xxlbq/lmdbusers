/*
 * 创建时间 2006/09/18
 */
package com.livedoor.dbm.components.mainframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.livedoor.dbm.components.common.DBMDialog;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.util.StringUtil;

/**
 * <p>
 * Title: 对话框
 * </p>
 * <p>
 * Description: 数据库注册主界面.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author Jian Li
 * @version 1.0
 */
public class RegisterServerDialog extends DBMDialog {

	private static final long serialVersionUID = -3067670536267005442L;

	RegisterServerDialog currentDialog;

	ConnectionInfo connInfo;

	boolean isOk = false;

	JList serverList;

	// 当前服务列表组件
	Map serversMap = new HashMap(3);

	DBSession dbSession = null;

	// 数据库注册信息录入界面

	private ServerInfoPanel serverInfoPanel;

	private JPanel panel;

	private JButton okButton;

	private JButton cancelButton;

	/**
	 * [功 能] 构造器.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param frame
	 *            主窗体
	 * @param dbSession
	 *            DBSession
	 * @return 无
	 *         <p>
	 */
	public RegisterServerDialog(DBMFrame frame, DBSession dbSession) {
		this(frame, null, dbSession, false);
	}

	/**
	 * [功 能] 构造器.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param frame
	 *            主窗体
	 * @param connInfo
	 *            连接信息
	 * @param dbSession
	 *            DBSession
	 * @param readOnly
	 *            是否只读，如果是克隆时为false,如果是修改服务属性时为true
	 * @return 无
	 *         <p>
	 */
	public RegisterServerDialog(DBMFrame frame, ConnectionInfo connInfo, DBSession dbSession, boolean readOnly) {
		super(frame, "Register Server", 650, 550);
		this.dbSession = dbSession;
		currentDialog = this;
		initComponents();
		setInitialValue(connInfo, readOnly);
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
	public void initComponents() {
		initDBMSData();
		okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				onOk();
			}

		});
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				onCancel();
			}

		});
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().setBackground(new Color(0x7F, 0x9D, 0xB9));
		this.getContentPane().add(getServerInfoPanel(), BorderLayout.CENTER);
		this.getContentPane().add(getButtonPanel(), BorderLayout.SOUTH);

	}

	/**
	 * [功 能] 设置初始值.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param connInfo
	 *            连接信息
	 * @param readOnly
	 *            是否只读，如果是修改服务属性时为true,如果是克隆时为false
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	private void setInitialValue(ConnectionInfo connInfo, boolean readOnly) {
		// 设定MYSQL为缺省值
		String defaultType = DBServerType.MYSQL;
		if (connInfo == null) {
			serverInfoPanel = (ServerInfoPanel) serversMap.get(defaultType);
		} else {
			defaultType = connInfo.getDbType();
			// 根据connInfo得到服务器组件
			// serverInfoPanel =
			// ServerInfoPanelFactory.getInstance(connInfo,serversMap);
			serverInfoPanel = (ServerInfoPanel) serversMap.get(connInfo.getDbType());
			serverInfoPanel.loadConnectionInfo(connInfo, readOnly);
			if (readOnly) {
				// 设定面板不能选择
				serverList.setEnabled(false);
			}
		}
		serverList.setSelectedValue(defaultType, false);
		modifiedVisibleState(defaultType);

	}

	/**
	 * [功 能] 得到主面板.
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
	private JPanel getServerInfoPanel() {
		JPanel content = new JPanel();
		content.setLayout(null);
		content.add(getServerList(), null);
		Iterator ite = serversMap.values().iterator();
		while (ite.hasNext()) {
			serverInfoPanel = (ServerInfoPanel) ite.next();
			serverInfoPanel.setLayout(null);
			serverInfoPanel.setBounds(new Rectangle(196, 0, 400, 500));
			content.add(serverInfoPanel, null);
		}

		JLabel jLabel_rdbms = new JLabel();
		jLabel_rdbms = new JLabel();
		jLabel_rdbms.setBounds(new Rectangle(17, 35, 82, 18));
		jLabel_rdbms.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_rdbms.setText("RDBMS:");
		content.add(jLabel_rdbms, null);

		content.setBackground(new Color(0x7F, 0x9D, 0xB9));
		return content;
	}

	/**
	 * [功 能] 得到button面板.
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
	private JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(2));
		buttonPanel.setBackground(new Color(0x7F, 0x9D, 0xB9));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		return buttonPanel;
	}

	/**
	 * [功 能] 得到数据库选择列表组件.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return JList
	 *         <p>
	 */
	private JList getServerList() {
		serverList = new JList(serversMap.keySet().toArray());
		serverList.setBounds(new Rectangle(17, 60, 164, 376));
		serverList.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		// add evevn handler
		serverList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listselectionevent) {
				String s1 = (String) serverList.getSelectedValue();
				modifiedVisibleState(s1);
				serverInfoPanel = (ServerInfoPanel) serversMap.get(s1);
			}
		});

		return serverList;
	}

	/**
	 * [功 能] 初始化数据库组件.
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
	private void initDBMSData() {
		addServer(DBServerType.MYSQL, new MySqlServerInfoPanel());
		addServer(DBServerType.ORACLE9i, new OraServerInfoPanel());
		addServer(DBServerType.DB2, new DB2ServerInfoPanel());
	}

	/**
	 * [功 能] 修改当前选择的服务器组件是否可见.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param serverKey
	 *            DBServerType中定义的常量
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	private void modifiedVisibleState(String serverKey) {
		Iterator ite = serversMap.keySet().iterator();
		while (ite.hasNext()) {
			String key = (String) ite.next();
			ServerInfoPanel panel = (ServerInfoPanel) serversMap.get(key);
			if (key.equals(serverKey)) {
				panel.setVisible(true);
			} else {
				panel.setVisible(false);
			}
		}
	}

	/**
	 * [功 能] 动态添加新的服务器组件.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param key
	 *            DBServerType中定义的常量
	 * @param serverInfoPanel
	 *            服务信息面板
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void addServer(String key, ServerInfoPanel serverInfoPanel) {
		serverInfoPanel.setDbSession(dbSession);
		serversMap.put(key, serverInfoPanel);
	}
	/**
	 * [功 能] ok动作事件.
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
	public void onOk() {

		
		setCursor(new Cursor(3));
		String error = serverInfoPanel.validataForm();

		if (StringUtil.isNotEmpty(error)) {
			setCursor(new Cursor(0));
			DBMMessageDialog.showErrorMessageDialog(this,error);
			return;

		}

		connInfo = serverInfoPanel.getConnectionInfo();
		isOk = true;
		hide();

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
		return this.isOk;

	}
	/**
	 * [功 能] 得到是否点击CANCLE的标志.
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
	public void onCancel() {
		isOk = false;
		hide();
		// return;
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
	 * @return connInfo
	 *         <p>
	 */
	public ConnectionInfo getConnInfo() {
		return connInfo;
	}

}

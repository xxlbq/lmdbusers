/*
 * 创建时间 2006/09/18
 */
package com.livedoor.dbm.components.mainframe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.connection.MySqlConnectionInfo;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.db.DBMConnectionManager;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.util.StringUtil;
import com.livedoor.dbm.util.DBMConnectionUtil;
import com.livedoor.dbm.util.DBMFileUtil;

/**
 * <p>
 * Title: 输入面板
 * </p>
 * <p>
 * Description: 显示Mysql的注册输入界面.
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
public class MySqlServerInfoPanel extends ServerInfoPanel {
	private static final long serialVersionUID = 1L;

	private JLabel jLabel_name = null;

	private JTextField jTextField_name = null;

	private JLabel jLabel_authentication = null;

	private JLabel jLabel_loginName = null;

	private JLabel jLabel_password = null;

	private JLabel jLabel_Location = null;

	private JLabel jLabel_host = null;

	private JLabel jLabel_port = null;

	private JLabel jLabel_dataBase = null;

	private JTextField jTextField_loginName = null;

	private JPasswordField jPasswordField_password = null;

	private JTextField jTextField_host = null;

	private JTextField jTextField_port = null;

	private JTextField jTextField_dataBase = null;

	private JLabel jLabel_characterEncoding = null;

	private JComboBox jComboBox_characterEncoding = null;

	private JSeparator jseparator1 = null;

	private JSeparator jseparator2 = null;

	/**
	 * This is the default constructor
	 */
	public MySqlServerInfoPanel() {
		super();
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
		jLabel_dataBase = new JLabel();
		jLabel_dataBase.setBounds(new Rectangle(30, 420, 82, 18));
		jLabel_dataBase.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_dataBase.setText("DataBase:");
		jLabel_port = new JLabel();
		jLabel_port.setBounds(new Rectangle(30, 377, 82, 18));
		jLabel_port.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_port.setText("Port:<3306>");
		jLabel_host = new JLabel();
		jLabel_host.setBounds(new Rectangle(30, 336, 82, 18));
		jLabel_host.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_host.setText("Host:");
		jLabel_Location = new JLabel();
		jLabel_Location.setBounds(new Rectangle(10, 299, 74, 18));
		jLabel_Location.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_Location.setText("Location");
		jseparator2 = new JSeparator();
		jseparator2.setBounds(132, 306, 240, 20);
		jLabel_password = new JLabel();
		jLabel_password.setBounds(new Rectangle(29, 234, 82, 18));
		jLabel_password.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_password.setText("Password:");
		jLabel_loginName = new JLabel();
		jLabel_loginName.setBounds(new Rectangle(29, 196, 82, 18));
		jLabel_loginName.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_loginName.setText("LoginName:");
		jLabel_authentication = new JLabel();
		jLabel_authentication.setBounds(new Rectangle(9, 158, 118, 18));
		jLabel_authentication.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_authentication.setText("Authentication");
		jseparator1 = new JSeparator();
		jseparator1.setBounds(132, 165, 240, 20);
		jLabel_name = new JLabel();
		jLabel_name.setBounds(new Rectangle(30, 57, 82, 18));
		jLabel_name.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_name.setText("Name:");
		jLabel_characterEncoding = new JLabel();
		jLabel_characterEncoding.setBounds(new Rectangle(29, 103, 150, 18));
		jLabel_characterEncoding.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_characterEncoding.setText("CharacterEncoding:");
		this.setSize(400, 500);
		this.setLayout(null);
		this.setBackground(new Color(0x7F, 0x9D, 0xB9));
		this.add(jLabel_name, null);
		this.add(getJTextField_name(), null);
		this.add(jLabel_authentication, null);
		this.add(jseparator1, null);
		this.add(jLabel_loginName, null);
		this.add(jLabel_password, null);
		this.add(jLabel_Location, null);
		this.add(jseparator2, null);
		this.add(jLabel_host, null);
		this.add(jLabel_port, null);
		this.add(jLabel_dataBase, null);
		this.add(jLabel_characterEncoding, null);
		this.add(getJTextField_loginName(), null);
		this.add(getJPasswordField_password(), null);
		this.add(getJTextField_host(), null);
		this.add(getJTextField_port(), null);
		this.add(getJTextField_dataBase(), null);
		this.add(getJComboBox_characterEncoding(), null);

	}

	/**
	 * [功 能] 得到jTextField_name.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return jTextField_name
	 *         <p>
	 */
	private JTextField getJTextField_name() {
		if (jTextField_name == null) {
			jTextField_name = new JTextField();
			jTextField_name.setBounds(new Rectangle(173, 53, 199, 22));
		}
		return jTextField_name;
	}

	/**
	 * [功 能] 得到jTextField_loginName.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return jTextField_loginName
	 *         <p>
	 */
	private JTextField getJTextField_loginName() {
		if (jTextField_loginName == null) {
			jTextField_loginName = new JTextField();
			jTextField_loginName.setBounds(new Rectangle(172, 192, 199, 22));
		}
		return jTextField_loginName;
	}

	/**
	 * [功 能] 得到jPasswordField_password.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return jPasswordField_password
	 *         <p>
	 */
	private JPasswordField getJPasswordField_password() {
		if (jPasswordField_password == null) {
			jPasswordField_password = new JPasswordField();
			jPasswordField_password.setBounds(new Rectangle(172, 230, 199, 22));
		}
		return jPasswordField_password;
	}

	/**
	 * [功 能] 得到jTextField_host.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return jTextField_host
	 *         <p>
	 */
	private JTextField getJTextField_host() {
		if (jTextField_host == null) {
			jTextField_host = new JTextField();
			jTextField_host.setBounds(new Rectangle(173, 332, 199, 22));
		}
		return jTextField_host;
	}

	/**
	 * [功 能] 得到jTextField_port.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return jTextField_port
	 *         <p>
	 */
	private JTextField getJTextField_port() {
		if (jTextField_port == null) {
			jTextField_port = new JTextField();
			jTextField_port.setBounds(new Rectangle(173, 373, 199, 22));
		}
		return jTextField_port;
	}

	/**
	 * [功 能] 得到jTextField_dataBase.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return jTextField_dataBase
	 *         <p>
	 */
	private JTextField getJTextField_dataBase() {
		if (jTextField_dataBase == null) {
			jTextField_dataBase = new JTextField();
			jTextField_dataBase.setBounds(new Rectangle(173, 416, 199, 22));
		}
		return jTextField_dataBase;
	}

	/**
	 * [功 能] 得到jComboBox_characterEncoding.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return jComboBox_characterEncoding
	 *         <p>
	 */
	private JComboBox getJComboBox_characterEncoding() {
		if (jComboBox_characterEncoding == null) {

			Vector data = new Vector();
			data.add("UTF-8");
			data.add("GB2312");
			data.add("GBK");
			data.add("ISO-8859-1");
//			data.add("Shift_jis");
//			data.add("ujis");
//			data.add("euckr");
			data.add("Shift_JIS");
			data.add("EUC-JP");
			data.add("EUC-KR");
			
			jComboBox_characterEncoding = new JComboBox(data);

			jComboBox_characterEncoding.setBounds(new Rectangle(173, 102, 199, 22));
		}
		return jComboBox_characterEncoding;
	}

	/**
	 * @see com.livedoor.dbm.connection.IConnectionModel#getConnectionInfo()
	 */
	public ConnectionInfo getConnectionInfo() {
		MySqlConnectionInfo connectionInfo = new MySqlConnectionInfo();
		connectionInfo.setConnectionName(jTextField_name.getText());
		connectionInfo.setDbType(DBServerType.MYSQL);
		connectionInfo.setHost(jTextField_host.getText());
		connectionInfo.setPassword(jPasswordField_password.getText());
		String port = jTextField_port.getText();
		if (StringUtil.isEmpty(port))
			port = "3306";
		connectionInfo.setPort(port);
		connectionInfo.setUserName(jTextField_loginName.getText());
		connectionInfo.setCharacterEncoding(convertedCharacterEncoding((String) jComboBox_characterEncoding.getSelectedItem()));
		connectionInfo.setDatabase(jTextField_dataBase.getText());

		return connectionInfo;
	}
	
	/**
	 * [功 能] 转化字符编码.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param str
	 *         待转化的字符编码.
	 *            <p>
	 * 
	 * @return 转化后的字符编码.
	 *         <p>
	 */
	private String convertedCharacterEncoding(String str){
		String retString = "";
		if("Shift_JIS".equals(str)){
			retString="Shift_jis";
		}else if("EUC-JP".equals(str)){
			retString="ujis";
		}else if("EUC-KR".equals(str)){
			retString="euckr";
		}else{
			retString=str;
		}
		return retString;
	}
	
	/**
	 * [功 能] 转化字符编码.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param str
	 *         待转化的字符编码.
	 *            <p>
	 * 
	 * @return 转化后的字符编码.
	 *         <p>
	 */
	private String convertedCharacterEncoding1(String str){
		String retString = "";
		if("Shift_jis".equals(str)){
			retString="Shift_JIS";
		}else if("ujis".equals(str)){
			retString="EUC-JP";
		}else if("euckr".equals(str)){
			retString="EUC-KR";
		}else{
			retString=str;
		}
		return retString;
	}


	@Override
	public String validataForm() {
		String validataForm = "";
		if (StringUtil.isEmpty(jTextField_name.getText())) {
			validataForm = "CONNECTION_NAME_NEED";
		} else if (StringUtil.isEmpty(jTextField_loginName.getText())) {
			validataForm = "LOGIN_NAME_NEED";
		} else if (StringUtil.isEmpty(jTextField_host.getText())) {
			validataForm = "HOST_NEED";
		} else if ((jTextField_name.isEnabled() == true)
				&& (DBMFileUtil.isFileExists(DBMConnectionUtil.getConnDir() + File.separator + jTextField_name.getText() + ".conn"))) {
			validataForm = "CONNECTION_NAME_EXISTS";
		} else {
			try {
				// 1.假如是新建时,生成一个coon,放入连接池，
				// 2.假如是修改时,重新生成一次连接,如果成功,更新连接池
				if (jTextField_name.isEnabled() == false) {
					DBMConnectionManager.insteadConnection(getConnectionInfo(), getDbSession());
				} else {
					DBMConnectionManager.getConnection(getConnectionInfo(), getDbSession());
				}
			} catch (DBMException e) {
				validataForm = "CONNECTION_FAILED";
			}
		}

		return validataForm;
	}

	@Override
	public void loadConnectionInfo(ConnectionInfo conn, boolean readOnly) {
		MySqlConnectionInfo _conn = (MySqlConnectionInfo) conn;
		if (readOnly) {
			jTextField_name.setText(conn.getConnectionName());
			jTextField_name.setEnabled(false);
		} else {
			jTextField_name.setText("");
		}
		jComboBox_characterEncoding.setSelectedItem(convertedCharacterEncoding1(_conn.getCharacterEncoding()));
		jTextField_loginName.setText(conn.getUserName());
		jPasswordField_password.setText(conn.getPassword());
		jTextField_host.setText(conn.getHost());
		jTextField_port.setText(conn.getPort());
		jTextField_dataBase.setText(_conn.getDatabase());

	}
}

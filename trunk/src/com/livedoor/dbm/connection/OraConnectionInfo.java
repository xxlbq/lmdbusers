package com.livedoor.dbm.connection;

import java.util.Properties;

import com.livedoor.dbm.util.Base64;

/**
 * Oracle数据库连接信息类
 */
public class OraConnectionInfo extends ConnectionInfo {

	/**
	 * oracle连接角色
	 */
	private String role;

	/**
	 * oracle的实例ID
	 */
	private String sid;

	public OraConnectionInfo() {

	}

	public OraConnectionInfo(Properties prop) {
		setRole(prop.getProperty("role"));
		setSid(prop.getProperty("sid"));
		setConnectionName(prop.getProperty("connName"));
		setUserName(prop.getProperty("userName"));
		setDbType(prop.getProperty("dbtype"));
		setPassword(Base64.base64Decode(prop.getProperty("password")));
		setPort(prop.getProperty("port"));
		setHost(prop.getProperty("host"));
	}

	/**
	 * 返回jdbc:oracle:thin:@ip:port:sid格式串
	 */
	public String getURL() {
		return "jdbc:oracle:thin:@"+getHost()+":"+getPort()+":"+getSid();
	}

	public Properties getConnProperties() {
		Properties connInfo = new Properties();
		connInfo.put("connName", getConnectionName());
		connInfo.put("dbtype", getDbType());
		connInfo.put("host", getHost());
		connInfo.put("password", Base64.base64Encode(getPassword()));
		connInfo.put("port", getPort());
		connInfo.put("userName", getUserName());
		connInfo.put("role", getRole());
		connInfo.put("sid", getSid());

		return connInfo;
	}

	/**
	 * 返回oracle.jdbc.driver.OracleDriver
	 */
	public String getDriverStr() {
		return "oracle.jdbc.driver.OracleDriver";
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	@Override
	public String getDatabase() {
		// TODO Auto-generated method stub
		return this.sid;
	}
	@Override
	public void clone(ConnectionInfo connInfo) {
		super.clone(connInfo);
		this.role = ((OraConnectionInfo)connInfo).getRole();
		this.sid = ((OraConnectionInfo)connInfo).getSid();
		
	}

}

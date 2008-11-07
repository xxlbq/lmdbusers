package com.livedoor.dbm.connection;

import java.util.Properties;

import com.livedoor.dbm.util.Base64;

public class DB2ConnectionInfo extends ConnectionInfo {

	private String database;

	public DB2ConnectionInfo() {

	}

	public DB2ConnectionInfo(Properties prop) {
		setDatabase(prop.getProperty("database"));
		setConnectionName(prop.getProperty("connName"));
		setUserName(prop.getProperty("userName"));
		setDbType(prop.getProperty("dbtype"));
		setPassword(Base64.base64Decode(prop.getProperty("password")));
		setPort(prop.getProperty("port"));
		setHost(prop.getProperty("host"));
	}

	/**
	 * 返回jdbc:db2://ip:port/database
	 */
	public String getURL() {
		return "jdbc:db2://"+getHost()+":"+getPort()+"/"+getDatabase();
	}

	public Properties getConnProperties() {
		Properties connInfo = new Properties();
		connInfo.put("connName", getConnectionName());
		connInfo.put("dbtype", getDbType());
		connInfo.put("host", getHost());
		connInfo.put("password", Base64.base64Encode(getPassword()));
		connInfo.put("port", getPort());
		connInfo.put("userName", getUserName());
		connInfo.put("database", getDatabase());

		return connInfo;
	}

	/**
	 * 返回com.ibm.db2.jcc.DB2Driver驱动字符串
	 */
	public String getDriverStr() {
		return "com.ibm.db2.jcc.DB2Driver";
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	@Override
	public void clone(ConnectionInfo connInfo) {
		super.clone(connInfo);
		this.database = connInfo.getDatabase();
		
	}

}

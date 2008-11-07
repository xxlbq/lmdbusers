package com.livedoor.dbm.connection;

import java.util.Properties;

import com.livedoor.dbm.util.Base64;

/**
 * Mysql连接信息类
 */
public class MySqlConnectionInfo extends ConnectionInfo {

	private String database;

	/**
	 * mysql连接的字符集编码
	 */
	private String characterEncoding;

	public MySqlConnectionInfo(){
		
	}
	/**
	 * 根据prop构造ConnectionInfo对象
	 * @param prop
	 */
	public MySqlConnectionInfo(Properties prop){
		
		setCharacterEncoding( prop.getProperty("characterEncoding"));
		setDatabase(prop.getProperty("database"));
		setConnectionName(prop.getProperty("connName"));
		setUserName(prop.getProperty("userName"));
		setDbType(prop.getProperty("dbtype"));
		setPassword(Base64.base64Decode(prop.getProperty("password")));
		setPort(prop.getProperty("port"));
		setHost(prop.getProperty("host"));
		
	}
	/**
	 * 返回jdbc:mysql://ip:port/database?characterEncoding=?
	 */
	public String getURL() {
		return "jdbc:mysql://"+getHost()+":"+getPort()+"/"+getDatabase()+"?"+"characterEncoding="+getCharacterEncoding()+
		       "&zeroDateTimeBehavior=round";
	}

	public Properties getConnProperties() {
		
		Properties connInfo = new Properties();
		connInfo.put("connName", getConnectionName());
		connInfo.put("characterEncoding", getCharacterEncoding());
		connInfo.put("database", getDatabase());
		connInfo.put("dbtype", getDbType());
		connInfo.put("host", getHost());
		connInfo.put("password", Base64.base64Encode(getPassword()));
		connInfo.put("port", getPort());
		connInfo.put("userName", getUserName());
		
		return connInfo;
	}

	/**
	 * 返回com.mysql.jdbc.Driver
	 */
	public String getDriverStr() {
		return "com.mysql.jdbc.Driver";
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

	public String getDatabase() {
		if(database == null || database.equals("")){
			database = "mysql";
		}
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
	@Override
	public void clone(ConnectionInfo connInfo) {
		super.clone(connInfo);
		this.database = connInfo.getDatabase();
		this.characterEncoding = ((MySqlConnectionInfo)connInfo).getCharacterEncoding();
		
	}

}

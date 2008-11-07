package com.livedoor.dbm.connection;

import java.util.Properties;

/**
 * 保存有注册的数据库详细信息
 */
public abstract class ConnectionInfo {

	private String connectionName;

	private String userName;

	private String dbType;

	private String password;

	private String port;

	private String host;

	/**
	 * 抽象方法，返回具体数据库的连接字符串；由 每个子类实现该方法
	 */
	public abstract String getURL();
	/**
	 * 抽象方法，返回当前的数据库名称
	 * @return
	 */
	public abstract String getDatabase();

	/**
	 * 以Properties对象返回所有注册信息
	 */
	public abstract Properties getConnProperties();

	/**
	 * 返回指定的驱动连接串字符
	 */
	public abstract String getDriverStr();

	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
    /**
     * @return 连接别名
     */
    public String getAliasName() {
        return getUserName() + "@" + getConnectionName();
    }
    
	/**
	 * clone connInfo
	 * @param connInfo
	 */
	public void clone(ConnectionInfo connInfo) {
		this.connectionName = connInfo.connectionName;
		this.userName = connInfo.getUserName();
		this.dbType = connInfo.getDbType();
		this.password = connInfo.getPassword();
		this.port = connInfo.getPort();
		this.host = connInfo.getHost();
				
	}
}

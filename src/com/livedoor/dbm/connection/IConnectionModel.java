package com.livedoor.dbm.connection;


/**
 *数据连接信息模型接口，用于得到数据库注册连接信息
 */
public interface IConnectionModel {
 	 
	/**
	 *返回具体的数据库连接信息
	 */
	public abstract ConnectionInfo getConnectionInfo();
}
 

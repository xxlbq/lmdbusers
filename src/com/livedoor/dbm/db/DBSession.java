/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.db;

/**
 * <p>
 * Title: 数据库操作
 * </p>
 * <p>
 * Description: DbManager 数据库操作，执行各种sql语句
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author YuanBaoKun
 * @version 1.0
 */
public class DBSession {

	private String stSessionId;

	private String stStartTime;

	/**
	 * [功 能] 构建DBSession，用来标识有状态的数据库连接
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 */
	public DBSession() {
		stSessionId = (new Long(System.currentTimeMillis())).toString();
		stStartTime = stSessionId;
	}

	/**
	 * [功 能] 获取DBSession 的 SessionId
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return String SessionId
	 */
	public String getSessionId() {
		return this.stSessionId;
	}

	/**
	 * [功 能] 获取DBSession 的开始时间
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return String 开始时间
	 */
	public String getStartTime() {
		return this.stStartTime;
	}

	/**
	 * [功 能] 设定DBSession 的 SessionId
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param str
	 *            SessionId
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void setSessionId(String str) {
		this.stSessionId = str;
	}

	/**
	 * [功 能] 设定DBSession 的 开始时间
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param str
	 *            开始时间
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void setStartTime(String str) {
		this.stStartTime = str;
	}

}

/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.constants;

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
public interface DBResultType {
	
	/**
	 * sql statement execute error
	 */
	public int ERROR = -1;
	
	/**
	 * no record return and renturn update count
	 */
	public int ZERO_ROWS = 0;
	
	/**
	 * muti record return
	 */
	public int MUTI_ROWS = 1;
	
	/**
	 * muti record return and renturn update count
	 */
	public int MUTI_RES = 2;
	
	/**
	 * sql statement execute normal
	 */
	public int SUCCESS = 2006;
}

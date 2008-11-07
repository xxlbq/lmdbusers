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
public class DBMSqlResult {

	private int iType; // result, String or error, or result and string
	private String stErrorMessage; // error message
	private DBMDataResult[] objs; // DBMDataResult[]
	private String[] arrayMessage; // message array
	
	private String stMessage;
	private DBMDataResult obj; 
	

	/**
	 * [功 能] 返回结果类型
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return int 结果类型
	 *         <p>
	 */
	public int getType() {
		return iType;
	}

	/**
	 * [功 能] 返回数据库操作执行结果集
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return DBMDataResult 结果集
	 *         <p>
	 */
	public DBMDataResult[] getDBMDataResults() {
		return objs;
	}

	/**
	 * [功 能] 返回数据库操作执行结果信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return String 结果信息
	 *         <p>
	 */
	public String getErrorMessage() {
		return stErrorMessage;
	}

	/**
	 * [功 能] 设定返回结果类型
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param iType
	 *            结果类型
	 * 
	 * @return 无
	 *         <p>
	 */
	public void setType(int iType) {
		this.iType = iType;
	}

	/**
	 * [功 能] 设定数据库操作执行结果集
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param obj
	 *            DBMDataResult 数据库操作执行结果集
	 * 
	 * @return 无
	 *         <p>
	 */
	public void setDBMDataResults(DBMDataResult[] objs) {
		this.objs = objs;
	}

	/**
	 * [功 能] 设定数据库操作执行结果信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param stMessage
	 *            String 数据库操作操作执行结果信息
	 * 
	 * @return 无
	 *         <p>
	 */
	public void setErrorMessage(String stMessage) {
		this.stErrorMessage = stMessage;
	}

	/**
	 * [功 能] 取得数据库操作执行结果信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return String[] 数据库操作操作执行结果信息
	 *         <p>
	 */
	public String[] getArrayMessage() {
		return arrayMessage;
	}
	/**
	 * [功 能] 设定数据库操作执行结果信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param arrayMessage
	 *            String[] 数据库操作操作执行结果信息
	 * 
	 * @return 无
	 *         <p>
	 */
	public void setArrayMessage(String[] arrayMessage) {
		this.arrayMessage = arrayMessage;
	}

	/**
	 * [功 能] 取得DBMDataResult
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return DBMDataResult
	 *         <p>
	 */
	public DBMDataResult getDBMDataResult() {
		return obj;
	}

	/**
	 * [功 能] 设定DBMDataResult
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param obj
	 *            DBMDataResult
	 * 
	 * @return 无
	 *         <p>
	 */
	public void setDBMDataResult(DBMDataResult obj) {
		this.obj = obj;
	}

	/**
	 * [功 能] 取得Message
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return Message
	 *         <p>
	 */
	public String getMessage() {
		return stMessage;
	}

	/**
	 * [功 能] 设定Message
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param stMessage
	 *            Message
	 * 
	 * @return 无
	 *         <p>
	 */
	public void setMessage(String stMessage) {
		this.stMessage = stMessage;
	}
}

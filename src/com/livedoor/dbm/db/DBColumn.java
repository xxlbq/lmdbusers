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
public class DBColumn {
	
	private String stColName;

	private int iColLen;
	
	private int iColType;
	
	/**
	 * [功 能] 返回列类型
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return String 列类型
	 *         <p>
	 */
	public int getColType() {
		return iColType;
	}
	
	/**
	 * [功 能] 设定列类型
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param stColType 列类型
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void setColType(int colType) {
		this.iColType = colType;
	}
	
	/**
	 * [功 能] 取得列名称
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return String 列名称
	 *         <p>
	 */
	public String getColumnName() {
		return stColName;
	}
	
	/**
	 * [功 能] 取得列长度
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return int 列长度
	 *         <p>
	 */
	public int getColumnLen() {
		return iColLen;
	}

	/**
	 * [功 能] 设定列名称
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param str 列名称
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	void setColumnName(String str) {
		this.stColName = str;
	}
	
	/**
	 * [功 能] 设定列长度
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param iLen 列长度
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	void setColumnLen(int iLen) {
		this.iColLen = iLen;
	}

}

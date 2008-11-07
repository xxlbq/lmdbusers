/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

/**
 * <p>
 * Title: 执行计划
 * </p>
 * <p>
 * Description: DbManager sql 语句执行计划
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
public class DataRecord {

	/*
	 * this class is not using in the current application program
	 * 
	 * it's for the future extends.
	 */
	
	/**
	 * 这个类是为了以后应用程序扩展而留下来的，可以用作所有的执行计划查询结果的基类
	 * 暂时并没有使用
	 */
	private String stOperation;
	private String Id;
	private String Parent;
	private String Position;
	private String stObjOwner;
	private String stObjName;
	private String stOpts;

	private String CC;

	Object[] children;

	public String getOperation() {
		return stOperation;
	}
	public String getId() {
		return Id;
	}
	public String getParent() {
		return Parent;
	}
	public String getPosition() {
		return Position;
	}
	public String getObjOwner() {
		return stObjOwner;
	}
	public String getObjName() {
		return stObjName;
	}
	public String getOpts() {
		return stOpts;
	}

	public String getCC() {
		return CC;
	}

	public void setOperation(String str) {
		this.stOperation = str;
	}
	public void setId(String str) {
		this.Id = str;
	}
	public void setParent(String str) {
		this.Parent = str;
	}
	public void setPosition(String str) {
		this.Position = str;
	}
	public void setObjOwner(String str) {
		this.stObjOwner = str;
	}
	public void setObjName(String str) {
		this.stObjName = str;
	}
	public void setOpts(String str) {
		this.stOpts = str;
	}

	public void setCC(String str) {
		this.CC = str;
	}
}

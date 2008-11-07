package com.livedoor.dbm.components.mainframe.importexport;
/**
 * <p>
 * Title: 用户输入信息
 * <p>
 * Description: 保存用户输入信息，方便操作数据时使用
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author ChenGang
 * @version 1.0
 */
public class UserInputInfo {

	private String databaseName;

	private String schemaName;

	private String tableName;

	private String file;

	private String sqlStatement;

	private String encoding;

	private String delimited;

	private String dateTimeFormat;

	private String dateFormat;

	private String timeFormat;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getDateTimeFormat() {
		return dateTimeFormat;
	}

	public void setDateTimeFormat(String dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	public String getDelimited() {
		if (delimited.equals("space"))
			return " ";
		if (delimited.equals("tab"))
			return "\t";
		return delimited;
	}

	public void setDelimited(String delimited) {
		this.delimited = delimited;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getSqlStatement() {
		return sqlStatement;
	}

	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}

}

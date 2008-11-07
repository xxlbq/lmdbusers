package com.livedoor.dbm.constants;

/**
* <p>
* Title:
* <p>
* Description:
* <p>
* Copyright: Copyright (c) 2006
* <p>
* Company: 英極軟件開發（大連）有限公司
* 
* @author WangHuiTang
* @version 1.0
*/
/**
 * 创建表时字段的所有属性名常量
 * @author wings
 *
 */
public interface ColumnProperty {

	/*
	 * column name
	 */
	public String NAME = "Column Name";
	
	/*
	 * column type
	 */
	public String TYPE = "Data Type";
	
	/*
	 * column length
	 */
	public String LENGTH = "Length";
	
	/*
	 * allow null
	 */
	public String NULLABLE = "Allow Nulls";
	
	/*
	 * primary key
	 */
	public String PK = "Primary Key";
	
	/*
	 * default value
	 */
	public String DEFAULT = "Default Value";
	
	/*
	 * column precision
	 */
	public String PRECISION = "Precision";
	
	/*
	 * column scale
	 */
	public String SCALE = "Scale";
	
	/*
	 * identity
	 */
	public String IDENTITY = "Identity";
	
	/*
	 * 是自动增长时identity所取的值
	 */
	public String IS_IDENTITY = "YES";
	
	/*
	 * 不是自动增长时identity所取的值
	 */
	public String NOT_IDEYTITY = "NO";
	
	/*
	 * 修改表结构时列要改变的名字
	 */
	public String OLD_COLUMN_NAME = "Old Column Name";
	
	public String ALLOW_NULL = "allow null";
}

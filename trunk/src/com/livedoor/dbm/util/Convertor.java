package com.livedoor.dbm.util;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.validator.DateValidator;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;

/**
 * <p>
 * Title: 类型转换
 * <p>
 * Description: 将需要操作的数据转换为ConvertObject，方便进行数据库或文件操作
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class Convertor {
	private String dateFormat;

	private String datetimeFormat;

	private String timeFormat;

	public Convertor(String dateFormat, String datetimeFormat, String timeFormat) {

		this.dateFormat = dateFormat;
		this.datetimeFormat = datetimeFormat;
		this.timeFormat = timeFormat;

	}

	public Convertor() {

		this.datetimeFormat = "yyyy-MM-dd HH:mm:ss";

		this.dateFormat = "yyyy-MM-dd";

		this.timeFormat = "HH:mm:ss";

	}

	public ConvertObject string2ConvertObject(String value, int columnType) {
		ConvertObject result = null;
		if ((ConvertObject.nullStr).equals(value))
			value = null;
		switch (columnType) {
		case Types.ARRAY:
			result = new ConvertObject(null,Types.ARRAY);//没有处理该类型
			result.setError(true);
			break;
		case Types.BIGINT:
			result = new ConvertObject(string2Long(value), Types.BIGINT);
			break;
		case Types.BINARY:
			result = new ConvertObject(null,Types.BINARY);//没有处理该类型
			result.setError(true);
			break;
		case Types.BIT:
			result = new ConvertObject(null,Types.BIT);//没有处理该类型
			result.setError(true);
			break;
		case Types.BLOB:
			result = new ConvertObject(null,Types.BLOB);//没有处理该类型
			result.setError(true);
			break;
		case Types.BOOLEAN:
			result = new ConvertObject(string2Boolean(value), Types.BOOLEAN);
			break;
		case Types.CHAR:
			result = new ConvertObject(value, Types.CHAR);
			break;
		case Types.CLOB:
			result = new ConvertObject(value,Types.VARCHAR);//没有处理该类型
			break;
		case Types.DATALINK:
			result = new ConvertObject(null,Types.DATALINK);//没有处理该类型
			result.setError(true);
			break;
		case Types.DATE:
			result = new ConvertObject(string2Date(value), Types.DATE);
			break;
		case Types.DECIMAL:
			result = new ConvertObject(string2Decimal(value), Types.VARCHAR);
			break;
		case Types.DISTINCT:
			result = new ConvertObject(null,Types.DISTINCT);//没有处理该类型
			result.setError(true);
			break;
		case Types.DOUBLE:
			result = new ConvertObject(string2Double(value), Types.DOUBLE);
			break;
		case Types.FLOAT:
			result = new ConvertObject(string2Float(value), Types.FLOAT);
			break;
		case Types.INTEGER:
			result = new ConvertObject(GenericTypeValidator.formatInt(value),
					Types.INTEGER);
			break;
		case Types.JAVA_OBJECT:
			result = new ConvertObject(null,Types.JAVA_OBJECT);//没有处理该类型
			result.setError(true);
			break;
		case Types.LONGVARBINARY:
			result = new ConvertObject(null,Types.LONGVARBINARY);//没有处理该类型
			result.setError(true);
			break;
		case Types.LONGVARCHAR:
			result = new ConvertObject(value, Types.LONGVARCHAR);
			break;
		case Types.NULL:
			result = new ConvertObject(null, Types.NULL);
			break;
		case Types.NUMERIC:
			result = new ConvertObject(string2Decimal(value), Types.NUMERIC);
			break;
		case Types.OTHER:
			result = new ConvertObject(value, Types.VARCHAR);//oracle nvarchar and nvarchar2 types
			break;
		case Types.REF:
			result = new ConvertObject(null,Types.REF);//没有处理该类型
			result.setError(true);
			break;
		case Types.REAL:
			result = new ConvertObject(string2Float(value),Types.REAL);

			break;
		case Types.SMALLINT:
			result = new ConvertObject(GenericTypeValidator.formatInt(value),
					Types.SMALLINT);
			break;
		case Types.STRUCT:
			result = new ConvertObject(null,Types.STRUCT);//没有处理该类型
			result.setError(true);
			break;
		case Types.TIME:
			result = new ConvertObject(string2Time(value), Types.TIME);
			break;
		case Types.TIMESTAMP:
			result = new ConvertObject(string2Datetime(value), Types.TIMESTAMP);
			break;
		case Types.TINYINT:
			result = new ConvertObject(GenericTypeValidator.formatInt(value),
					Types.TINYINT);
			break;
		case Types.VARBINARY:
			result = new ConvertObject(null,Types.VARBINARY);//没有处理该类型
			result.setError(true);
			break;
		case Types.VARCHAR:
			result = new ConvertObject(value, Types.VARCHAR);
			break;
		default:
			result = new ConvertObject(null,columnType);//没有处理该类型
			result.setError(true);
		}
		if (value == null) {
			result.setError(false);
			return result;
		}
		return result;
	}

	public ConvertObject object2ConvertObject(Object obj, int columnType) {
		ConvertObject result = null;
		java.util.Date date;
		if(obj == null)
			return new ConvertObject(null,columnType);
		switch (columnType) {
		case Types.ARRAY:
			result = new ConvertObject(null,Types.ARRAY);//没有处理该类型
			result.setError(true);
			break;
		case Types.BIGINT:
			result = new ConvertObject(obj, Types.BIGINT);
			break;
		case Types.BINARY:
			result = new ConvertObject(null,Types.BINARY);//没有处理该类型
			result.setError(true);
			break;
		case Types.BIT:
			result = new ConvertObject(null,Types.BIT);//没有处理该类型
			result.setError(true);
			break;
		case Types.BLOB:
			result = new ConvertObject(null,Types.BLOB);//没有处理该类型
			result.setError(true);
			break;
		case Types.BOOLEAN:
			result = new ConvertObject(obj, Types.BOOLEAN);
			break;
		case Types.CHAR:
			result = new ConvertObject(obj, Types.CHAR);
			break;
		case Types.CLOB:
			result = new ConvertObject(obj,Types.CLOB);//没有处理该类型
			result.setError(true);
			break;
		case Types.DATALINK:
			result = new ConvertObject(null,Types.DATALINK);//没有处理该类型
			result.setError(true);
			break;
		case Types.DATE:
			if(obj instanceof java.util.Date){
				date = (java.util.Date)obj;
				result = new ConvertObject(new ConvertedDate(date.getTime(),this.dateFormat), Types.DATE);
			}else{
				result = new ConvertObject(null, Types.DATE);
				result.setError(true);
			}
			break;
		case Types.DECIMAL:
			result = new ConvertObject(obj.toString(),Types.VARCHAR);
			break;
		case Types.DISTINCT:
			result = new ConvertObject(null,Types.DISTINCT);//没有处理该类型
			result.setError(true);
			break;
		case Types.DOUBLE:
			result = new ConvertObject(obj.toString(), Types.DOUBLE);
			break;
		case Types.FLOAT:
			result = new ConvertObject(obj.toString(), Types.FLOAT);
			break;
		case Types.INTEGER:
			result = new ConvertObject(obj, Types.INTEGER);
			break;
		case Types.JAVA_OBJECT:
			result = new ConvertObject(null,Types.JAVA_OBJECT);//没有处理该类型
			result.setError(true);
			break;
		case Types.LONGVARBINARY:
			result = new ConvertObject(null,Types.LONGVARBINARY);//没有处理该类型
			result.setError(true);
			break;
		case Types.LONGVARCHAR:
			result = new ConvertObject(obj, Types.LONGVARCHAR);
			break;
		case Types.NULL:
			result = new ConvertObject(null, Types.NULL);
			break;
		case Types.NUMERIC:
			result = new ConvertObject(obj.toString(), Types.NUMERIC);
			break;
		case Types.OTHER:
			result = new ConvertObject(obj, Types.VARCHAR);//oracle nvarchar and nvarchar2 types
			break;
		case Types.REF:
			result = new ConvertObject(null,Types.REF);//没有处理该类型
			result.setError(true);
			break;
		case Types.REAL:
			result = new ConvertObject(obj.toString(),Types.REAL);
			break;
		case Types.SMALLINT:
			result = new ConvertObject(obj, Types.SMALLINT);
			break;
		case Types.STRUCT:
			result = new ConvertObject(null,Types.STRUCT);//没有处理该类型
			result.setError(true);
			break;
		case Types.TIME:
			if(obj instanceof java.util.Date){
				date = (java.util.Date)obj;
				result = new ConvertObject(new ConvertedTime(date.getTime(),this.timeFormat), Types.TIME);
			}else{
				result = new ConvertObject(null, Types.TIME);
				result.setError(true);
			}
			break;
		case Types.TIMESTAMP:
			
			if(obj instanceof java.util.Date){
				date = (java.util.Date)obj;
				result = new ConvertObject(new ConvertedDateTime(date.getTime(),this.datetimeFormat), Types.TIMESTAMP);
			}else{
				result = new ConvertObject(null, Types.TIMESTAMP);
				result.setError(true);
			}
			break;
		case Types.TINYINT:
			result = new ConvertObject(obj, Types.TINYINT);
			break;
		case Types.VARBINARY:
			result = new ConvertObject(null,Types.VARBINARY);//没有处理该类型
			result.setError(true);
			break;
		case Types.VARCHAR:
			result = new ConvertObject(obj, Types.VARCHAR);
			break;
		default:
			result = new ConvertObject(null,columnType);//没有处理该类型
			result.setError(true);
		}

		return result;
	}

	/**
	 * 字符串转java.sql.Date对象
	 * 
	 * @param value
	 * @return 成功返回java.sql.Data对象 不成功返回null
	 */
	public Object string2Date(String value) {

		Object result;
		DateValidator validator = DateValidator.getInstance();
		if (validator.isValid(value, this.datetimeFormat, false)) {
			SimpleDateFormat df = new SimpleDateFormat(this.datetimeFormat);

			try {
				java.util.Date date = df.parse(value);
				result = new ConvertedDate(date.getTime(), this.datetimeFormat);
				return result;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}

		} else if (validator.isValid(value, this.dateFormat, false)) {
			SimpleDateFormat df = new SimpleDateFormat(this.dateFormat);
			try {
				java.util.Date date = df.parse(value);
				result = new ConvertedDate(date.getTime(), this.dateFormat);
				return result;
			} catch (ParseException e) {
				return null;
			}
		} else
			return null;

	}

	/**
	 * 字符串转java.sql.Date对象
	 * 
	 * @param value
	 * @return 成功返回java.sql.Data对象 不成功返回null
	 */
	public Object string2Datetime(String value) {

		Object result;
		DateValidator validator = DateValidator.getInstance();
		if (validator.isValid(value, this.datetimeFormat, false)) {
			SimpleDateFormat df = new SimpleDateFormat(this.datetimeFormat);

			try {
				java.util.Date date = df.parse(value);
				result = new ConvertedDateTime(date.getTime(),
						this.datetimeFormat);
				return result;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}

		} else if (validator.isValid(value, this.dateFormat, false)) {
			SimpleDateFormat df = new SimpleDateFormat(this.dateFormat);
			try {
				java.util.Date date = df.parse(value);
				result = new ConvertedDateTime(date.getTime(), this.dateFormat);
				return result;
			} catch (ParseException e) {
				return null;
			}
		} else
			return null;

	}

	/**
	 * 字符串转成只包含时间的java.sql.Date对象
	 * 
	 * @param value
	 * @return 成功返回java.sql.Data对象 不成功返回null
	 */
	public Object string2Time(String value) {

		DateValidator validator = DateValidator.getInstance();
		if (validator.isValid(value, this.timeFormat, false)) {
			SimpleDateFormat df = new SimpleDateFormat(this.timeFormat);
			try {
				java.util.Date date = df.parse(value);
				return new ConvertedTime(date.getTime(), this.timeFormat);
			} catch (ParseException e) {
				return null;
			}

		} else
			return null;

	}

	public static Object string2Long(String value) {

		if (GenericValidator.isLong(value))
			return Long.valueOf(value);
		else
			return null;

	}

	public static Object string2Float(String value) {

		if (GenericValidator.isFloat(value))
			return value;
		else
			return null;

	}

	public static Object string2Double(String value) {

		if (GenericValidator.isDouble(value))
			return value;
		else
			return null;

	}

	public static Object string2Decimal(String value) {
		BigDecimal obj;
		try {
			obj = new BigDecimal(value);
		} catch (Exception e) {
			return null;
		}
		return value;
	}

	public static Object string2Boolean(String value) {
		if (value == null)
			return value;
		value = value.toLowerCase();
		if ("true".equals(value))
			return Boolean.TRUE;
		if ("false".equals(value))
			return Boolean.FALSE;
		return null;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		if (dateFormat != null)
			this.dateFormat = dateFormat;
	}

	public String getDatetimeFormat() {
		return datetimeFormat;
	}

	public void setDatetimeFormat(String datetimeFormat) {
		if (datetimeFormat != null)
			this.datetimeFormat = datetimeFormat;
	}

	public String getTimeFormat() {

		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		if (timeFormat != null)
			this.timeFormat = timeFormat;
	}

	public static void main(String[] args) {

		Convertor convertor = new Convertor();
			System.out.println(convertor.string2ConvertObject("1",7));
	}

}

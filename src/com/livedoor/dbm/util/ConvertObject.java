package com.livedoor.dbm.util;

/**
 * <p>
 * Title: 转换类
 * <p>
 * Description: 将数据库中的数据类型与需要写入数据库中的值相对应
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class ConvertObject {

	private Object value;

	private int type;

	private boolean error = false;

	public static final String nullStr = String.valueOf((char) 1); //标志数据库空的字符

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public ConvertObject(Object value, int type) {
		this.value = value;
		this.type = type;
		if (value == null)
			this.error = true;
	}

	public ConvertObject() {

	}

	public int getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		if (value != null)
			return value.toString();
		else
			return "(null)";

	}
	public String toFile() {
		if (value != null)
			return value.toString();
		else
			return new String(nullStr);

	}
	public void setType(int type) {
		this.type = type;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if ((obj instanceof ConvertObject)) {

			ConvertObject object = (ConvertObject) obj;
			if (this.type != object.getType())
				return false;
			if (this.error != object.isError())
				return false;
			if (this.value != null)
				return this.value.equals(object.getValue());
			else {
				if (object.getValue() != null)
					return false;
				else
					return true;
			}
		} else {

			return obj.equals(this.value);

		}
	}

}

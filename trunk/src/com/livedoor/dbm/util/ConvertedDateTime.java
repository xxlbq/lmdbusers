package com.livedoor.dbm.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/**
 * <p>
 * Title: 日期时间类转换
 * <p>
 * Description: 按给定格式转换日期时间类型
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class ConvertedDateTime extends Timestamp {

	private String datetimeFormat;
	@Override
	public String toString() {
		// TODO Auto-generated method stub

		SimpleDateFormat df = new SimpleDateFormat(this.datetimeFormat);

		return df.format(this);
	}


	public ConvertedDateTime(long date,String datetimeFormat) {
		super(date);
		this.datetimeFormat = datetimeFormat;
	}
	
	
	@Override
	public boolean equals(Object ts) {
		if(ts == null)
			return false;
		return this.toString().equals(ts.toString());
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

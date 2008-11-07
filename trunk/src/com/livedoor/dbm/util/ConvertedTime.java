package com.livedoor.dbm.util;

import java.sql.Time;
import java.text.SimpleDateFormat;
/**
 * <p>
 * Title: 时间类转换
 * <p>
 * Description: 按给定格式转换时间类型
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class ConvertedTime extends Time {

	private String timeFormat;
	@Override
	public String toString() {
		// TODO Auto-generated method stub

		SimpleDateFormat df = new SimpleDateFormat(this.timeFormat);

		return df.format(this);
	}

	public ConvertedTime(int year, int month, int day) {
		super(year, month, day);
		// TODO Auto-generated constructor stub
	}

	public ConvertedTime(long date,String timeFormat) {
		super(date);
		this.timeFormat = timeFormat;
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

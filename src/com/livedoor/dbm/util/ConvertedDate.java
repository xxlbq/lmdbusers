package com.livedoor.dbm.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * <p>
 * Title: 日期类转换
 * <p>
 * Description: 按给定格式转换日期类型
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class ConvertedDate extends Date {

	private String dateFormat;
	@Override
	public String toString() {
		// TODO Auto-generated method stub

		SimpleDateFormat df = new SimpleDateFormat(this.dateFormat);

		return df.format(this);
	}

	public ConvertedDate(int year, int month, int day) {
		super(year, month, day);
		// TODO Auto-generated constructor stub
	}

	public ConvertedDate(long date,String dateFormat) {
		
		super(date);
		this.dateFormat = dateFormat;
		
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

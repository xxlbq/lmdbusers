package com.livedoor.dbm.util.importexportutil;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * Title: 时间日期格式
 * <p>
 * Description: 本地数据文件中时间日期显示格式
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author ChenGang
 * @version 1.0
 */
public class DateAndTimeFormat {
	private String[] dateAndTimeFormat = {"yyyy/MM/dd hh:mm:ss a", "yyyy-MM-dd hh:mm:ss a", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss"};
	private String[] dateFormat = {"yyyy/MM/dd", "yyyy-MM-dd"};
	private String[] timeFormat = {"hh:mm:ss a", "HH:mm:ss"};
	
	
	public String[] getDateAndTimeFormat() {
		return dateAndTimeFormat;
	}
	public void addDateAndTimeFormat(String dateAndTimeFormat) {
		add(this.dateAndTimeFormat,dateAndTimeFormat);
	}
	public String[] getDateFormat() {
		return dateFormat;
	}
	public void addDateFormat(String dateFormat) {
		add(this.dateFormat,dateFormat);
	}
	public String[] getTimeFormat() {
		return timeFormat;
	}
	public void addTimeFormat(String timeFormat) {
		add(this.timeFormat,timeFormat);
	}
	
	private void add (String[] source, String data) {
		String[] temp = new String[source.length + 1];
		System.arraycopy(source, 0, temp, 0, (source.length - 1));
		temp[source.length] = data;
		source = temp;
	}
	
	public String formatDateAndTime (Date date, String format) {
		Format formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
}

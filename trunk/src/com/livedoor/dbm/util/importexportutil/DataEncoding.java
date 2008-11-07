package com.livedoor.dbm.util.importexportutil;

import java.nio.charset.Charset;
import java.util.Properties;

/**
 * <p>
 * Title: 字符编码
 * <p>
 * Description: 本地数据文件中字符编码格式
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author ChenGang
 * @version 1.0
 */
public class DataEncoding {
	
	private String[] encoding = {"UTF-8","GB2312","GBK","ISO-8859-1","Shift_JIS"};

	public String[] getEncoding() {
		return encoding;
	}
	
	public static void main(String[] args) {
		Properties p = System.getProperties();
		System.out.println(p.get("file.encoding"));
		System.out.println(p.keySet());
		System.out.println(Charset.availableCharsets());
	}
}

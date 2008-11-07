package com.livedoor.dbm.util.importexportutil;
/**
 * <p>
 * Title: 数据分隔符
 * <p>
 * Description: 本地数据文件中数据分隔符号
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author ChenGang
 * @version 1.0
 */
public class DataDelimited {
	private String[] delimited = {",",";",":","space","tab"};

	public String[] getDelimited() {
		return delimited;
	}
}

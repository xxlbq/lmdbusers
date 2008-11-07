package com.livedoor.dbm.util;

import java.io.File;
import java.util.Properties;

/**
 * <p>
 * Description: 存放History文件的位置及属性
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
public class DBMPropertiesUtil {
	private static Properties _cachedProperties = null;

	public static final String UICONFIG_PROPERTIES = System.getProperty("user.home") + File.separator + ".dbmanager" + File.separator
			+ "dbmanager.properties";

	private static final String HISTORY_DIR = System.getProperty("user.home") + File.separator + ".dbmanager" + File.separator + "history";
	static {

		File filder = new File(UICONFIG_PROPERTIES.substring(0, UICONFIG_PROPERTIES.lastIndexOf(File.separator)));
		if (!filder.exists()) {
			filder.mkdirs();
		}

		File file = new File(UICONFIG_PROPERTIES);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	/**
	 * [功 能] 得到History文件的位置.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * <p>
	 * 
	 * @return String
	 *         <p>
	 */
	public static String getHistoryDirectory() {

		return HISTORY_DIR;
	}
	/**
	 * [功 能] 得到缓存的属性.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * <p>
	 * 
	 * @return Properties
	 *         <p>
	 */
	public static Properties getUIProperties() {
		if (_cachedProperties == null) {
			_cachedProperties = getProperties(UICONFIG_PROPERTIES);
		}
		return _cachedProperties;
	}
	/**
	 * [功 能] 得到缓存的属性.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param s
	 *            文件路径.
	 *            <p>
	 * 
	 * @return Properties
	 *         <p>
	 */
	private static Properties getProperties(String s) {
		return DBMFileUtil.readProperties(s);
	}
	/**
	 * [功 能] 设定属性.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param s
	 *            文件路径.
	 * @param properties
	 *            属性
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	private static void setProperties(Properties properties, String s) {
		DBMFileUtil.writeProperties(s, properties);
	}
	/**
	 * [功 能] 设定缓存的属性.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 属性
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static void setUIProperties(Properties properties) {
		_cachedProperties = properties;
		setProperties(properties, UICONFIG_PROPERTIES);
	}

}

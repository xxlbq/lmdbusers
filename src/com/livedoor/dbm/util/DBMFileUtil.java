package com.livedoor.dbm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.List;
/**
 * <p>
 * Description: 文件操作类
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author zhangys
 * @version 1.0
 */
public class DBMFileUtil {
	/**
	 * [功 能] 写属性文件.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param filePah
	 *            文件路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            文件内容
	 * 
	 * <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static void writeProperties(String filePah, String fileName, Properties content) {

		try {
			writeProperties(filePah + File.separator + fileName, content);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * [功 能] 写属性文件.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param fileName
	 *            文件名
	 * @param content
	 *            文件内容
	 * 
	 * <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static void writeProperties(String fileName, Properties content) {
		try {
			FileOutputStream fileOutput = new FileOutputStream(fileName);
			content.save(fileOutput, "DB_MANAGER");

			fileOutput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * [功 能] 读属性文件.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param fileName
	 *            文件名
	 * 
	 * <p>
	 * 
	 * @return Properties
	 *         <p>
	 */
	public static Properties readProperties(String fileName) {

		Properties prop = new Properties();
		try {
			FileInputStream inputStream = new FileInputStream(fileName);
			prop.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	/**
	 * [功 能] 判断文件是否存在.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param fileName
	 *            文件名
	 * 
	 * <p>
	 * 
	 * @return boolean
	 *         <p>
	 */
	public static boolean isFileExists(String fileName) {

		return new File(fileName).exists();
	}
	/**
	 * [功 能] 删除文件.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param fileName
	 *            文件名
	 * 
	 * <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static void deleteFile(String fileName) {
		new File(fileName).delete();
	}

}

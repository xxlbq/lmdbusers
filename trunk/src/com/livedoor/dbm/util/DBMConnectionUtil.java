package com.livedoor.dbm.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Properties;

import com.livedoor.dbm.connection.ConnectionInfo;

/**
 * <p>
 * Description: 文件操作类
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author lijian
 * @version 1.0
 */
public class DBMConnectionUtil {

	private static final String CONNECTION_DIR = System.getProperty("user.home") + File.separator + ".dbmanager" + File.separator
			+ "connections";

	/**
	 * [功 能] 1.取出connection中的properties信息 2.修改properties信息，增加一条 loadClass =
	 * ClassName名字 3.把Properties中的信息保存到数据库中
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param connectionInfo
	 *            连接信息
	 * 
	 * <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static void writeConnectionFile(ConnectionInfo connectionInfo) {
		Properties prop = connectionInfo.getConnProperties();
		prop.put("loadClass", connectionInfo.getClass().getName());
		DBMFileUtil.writeProperties(CONNECTION_DIR, connectionInfo.getConnectionName() + ".conn", prop);
	}

	/**
	 * [功 能] 按文件读取数据库注册配置信息，然后 构造ConnectionInfo对象
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param fileName
	 *            文件名
	 * 
	 * <p>
	 * 
	 * @return ConnectionInfo
	 *         <p>
	 */
	public static ConnectionInfo readConnectionInfo(String fileName) {
		fileName = CONNECTION_DIR + File.separator + fileName + ".conn";
		Properties prop = DBMFileUtil.readProperties(fileName);

		Constructor constructor = null;;
		ConnectionInfo conn = null;;
		try {

			constructor = Class.forName(prop.getProperty("loadClass")).getConstructor(new Class[]{Properties.class});
			conn = (ConnectionInfo) constructor.newInstance(new Object[]{prop});
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

		return conn;

	}

	/**
	 * [功 能] 得到连接路径.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 * 
	 * <p>
	 * 
	 * @return String
	 *         <p>
	 */
	public static String getConnDir() {
		return CONNECTION_DIR;
	}

}

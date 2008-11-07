/**
 * 創建期日 2006-10-15
 */
package com.livedoor.dbm.components.common;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBMConnectionManager;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;
/**
 * <p>
 * Title: 事件类
 * </p>
 * <p>
 * Description: WindowListener的实现.主要实现关闭事件.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * </p>
 * 
 * @author Zhangys
 * @version 1.0
 */
public class DBMWindowListener extends WindowAdapter {

	private ConnectionInfo connectionInfo;
	private DBSession dBSession;

	public DBMWindowListener(ConnectionInfo connectionInfo, DBSession dBSession) {
		this.connectionInfo = connectionInfo;
		this.dBSession = dBSession;
	}

	
	/**
	 * 
	 * Description: 关闭事件.
	 * <p>
	 * 
	 * @param e
	 *            window事件
	 *            <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 */
	public void windowClosed(WindowEvent e) {
		try {
			DBMConnectionManager.releaseConnection(connectionInfo, dBSession);
		} catch (DBMException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
			DBMMessageDialog.showErrorMessageDialog(e1.getMessage());
		}

	}
	
	/**
	 * 
	 * Description: 关闭事件.
	 * <p>
	 * 
	 * @param e
	 *            window事件
	 *            <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 */
	public void windowClosing(WindowEvent e) {
		try {
			DBMConnectionManager.releaseConnection(connectionInfo, dBSession);
		} catch (DBMException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
			DBMMessageDialog.showErrorMessageDialog(e1.getMessage());
		}

	}

	

}

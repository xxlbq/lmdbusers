/*
 * 创建时间 2006/09/18
 */
package com.livedoor.dbm.components.mainframe;

import javax.swing.JPanel;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.connection.IConnectionModel;
import com.livedoor.dbm.db.DBSession;

/**
 * <p>
 * Title: 抽象类
 * 
 * </p>
 * <p>
 * Description: 服务信息面板抽象类.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author Jian Li
 * @version 1.0
 */
public abstract class ServerInfoPanel extends JPanel implements IConnectionModel {

	private DBSession dbSession = null;
	/**
	 * [功 能] 抽象方法，得到连接信息.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return connInfo
	 *         <p>
	 */
	public abstract ConnectionInfo getConnectionInfo();
	/**
	 * [功 能] 抽象方法，提交前校验窗体中输入的值.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public abstract String validataForm();
	/**
	 * [功 能] 抽象方法，加载连接信息.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param info
	 *            连接信息
	 * @param readOnly
	 *            是否只读，如果是修改服务属性时为false,如果是新建时为true
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public abstract void loadConnectionInfo(ConnectionInfo info, boolean readOnly);
	/**
	 * [功 能] 得到DbSession.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param 无
	 *            <p>
	 * 
	 * @return dbSession
	 *         <p>
	 */
	public DBSession getDbSession() {
		return dbSession;
	}
	/**
	 * [功 能] 设定DbSession.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param dbSession
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void setDbSession(DBSession dbSession) {
		this.dbSession = dbSession;
	}

}

/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.db.DBSession;

/**
 * <p>
 * Title: 执行计划
 * </p>
 * <p>
 * Description: DbManager sql 语句执行计划
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英极软件开发（大连）有限公司
 * </p>
 * 
 * @author YuanBaoKun
 * @version 1.0
 */
public class DBMExplainHandler {

	/**
	 * [功 能] 获取JDBMPanel
	 * <p>
	 * [说 明] 根据给定的参数，获取JDBMPanel
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param conInfo
	 *            ConnectionInfo
	 * @param dBSession
	 *            DBSession
	 * @param stSql
	 *            String sql statement
	 *            <p>
	 * 
	 * @return JDBMPanel JDBMPanel object
	 *         <p>
	 */
	public static JDBMPanel getExplainPanel(ConnectionInfo conInfo, DBSession dBSession, String stSql) throws Exception {
		JDBMPanel jDBMPanel = null;

		String stDbType = conInfo.getDbType();

		if (stDbType.equals(DBServerType.DB2) == true) {
			jDBMPanel = new JDBMDb2Panel(conInfo, dBSession, stSql);
		} else if (stDbType.equals(DBServerType.MYSQL) == true) {
			jDBMPanel = new JDBMMysqlPanel(conInfo, dBSession, stSql);
		} else if (stDbType.equals(DBServerType.ORACLE9i) == true) {
			jDBMPanel = new JDBMOraclePanel(conInfo, dBSession, stSql);
		}

		return jDBMPanel;
	}
}
/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.db;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.DBServerType;

/**
 * <p>
 * Title: 数据库操作
 * </p>
 * <p>
 * Description: DbManager 数据库操作，执行各种sql语句
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
public class DBMSqlExecuterFactory {

	/**
	 * [功 能] 根据传入的参数， 建立相应的DBMSqlExecuter
	 * <p>
	 * [说 明] 建立DBMSqlExecuter，以便于执行sql语句操作
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
	 *            <p>
	 * 
	 * @return DBMSqlExecuter
	 *         <p>
	 */
	public static DBMSqlExecuter createExecuter(ConnectionInfo conInfo, DBSession dBSession) {

		String stDbType = conInfo.getDbType();

		DBMSqlExecuter dBMSqlExecuter = null;

		if (stDbType.equals(DBServerType.DB2) == true) {
			dBMSqlExecuter = new DBMDB2Executer();
		} else if (stDbType.equals(DBServerType.MYSQL) == true) {
			dBMSqlExecuter = new DBMMysqlExecuter();
		} else if (stDbType.equals(DBServerType.ORACLE9i) == true) {
			dBMSqlExecuter = new DBMOracleExecuter();
		}

		dBMSqlExecuter.setConnectionInfo(conInfo);
		dBMSqlExecuter.setDBSession(dBSession);

		return dBMSqlExecuter;
	}
}

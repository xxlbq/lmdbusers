package com.livedoor.dbm.scripts;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBSession;

public class SqlBuilderFactory {

	/**
	 * [機 能] create DBMSqlExecuter object
	 * <p>
	 * [解 説] create DBMSqlExecuter object
	 * <p>
	 * [備 考] なし
	 * <p>
	 * [作成日付] 2006/09/16
	 * <p>
	 * [更新日付]
	 * <p>
	 * 
	 * @param conInfo
	 *            ConnectionInfo
	 *            <p>
	 * 
	 * @return DBMSqlExecuter
	 *         <p>
	 */
	public static AbstractSqlBuilder createSqlBuilder(ConnectionInfo conInfo,DBSession dBSession,DBMDataMetaInfo dateMetaInfo) {

		String stDbType = conInfo.getDbType();

		AbstractSqlBuilder abstractSqlBuilder = null;

		if (stDbType.equals(DBServerType.DB2) == true) {
			abstractSqlBuilder = new Db2Builder(dateMetaInfo);
		} else if (stDbType.equals(DBServerType.MYSQL) == true) {
			abstractSqlBuilder = new MysqlBuilder(dateMetaInfo);
		} else if (stDbType.equals(DBServerType.ORACLE9i) == true) {
			abstractSqlBuilder = new OracleBuilder(dateMetaInfo);
		}

		// dBMSqlExecuter.setConnectionInfo(conInfo);
		
		abstractSqlBuilder.setConnectionInfo(conInfo);
		abstractSqlBuilder.setDBSession(dBSession);

		return abstractSqlBuilder;
	}

}

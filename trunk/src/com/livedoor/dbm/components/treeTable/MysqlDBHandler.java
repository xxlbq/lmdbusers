/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.connection.MySqlConnectionInfo;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.db.DBMConnectionManager;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.db.DBMSqlResult;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;

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
public class MysqlDBHandler {

	private DBMSqlResult[] list;

	private ConnectionInfo connectionInfo;

	private DBSession dBSession;

	private DBMSqlExecuter dBMSqlExecuter;
	
	/**
	 * [功 能] 根据给定的参数，生成查询计划结果集 
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * @param conInfo
	 *            ConnectionInfo
	 * @param dBSession
	 *            DBSession
	 *            <p>
	 */
	public MysqlDBHandler(ConnectionInfo connectionInfo, DBSession dBSession) {

		this.connectionInfo = connectionInfo;
		this.dBSession = dBSession;

		dBMSqlExecuter = DBMSqlExecuterFactory.createExecuter(connectionInfo,
				dBSession);
	}
	
	/**
	 * [功 能] 执行查询计划
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param String sql语句
	 * 
	 * @return DBMSqlResult[ 返回查询计划的详细信息
	 *         <p>
	 */
	public DBMSqlResult[] executePlan(String stSql) throws DBMException {

		//String stTime = ((Long) System.currentTimeMillis()).toString();

		String[] PlanSql = new String[]{"explain " + stSql };

		list = dBMSqlExecuter.execute(PlanSql, -1);

		return list;
	}

	public static void main(String[] args) throws Exception {

		System.out.println(System.currentTimeMillis());
		MySqlConnectionInfo ora = new MySqlConnectionInfo();
		ora.setDbType(DBServerType.MYSQL);
		ora.setHost("10.5.1.111");
		ora.setUserName("root");
		ora.setPassword("root");
		// ora.setDatabase("mysql");
		ora.setPort("3306");
		ora.setCharacterEncoding("UTF-8");
		ora.setConnectionName("mysql");

		DBSession dBSession = new DBSession();
		
		MysqlDBHandler oo = new MysqlDBHandler(ora, dBSession);

		String stSql = "select * from db";
/*		
		DBMDataResult d = oo.executePlan(stSql);
		
		List ll = d.getColumns();
		List l2 = d.getData();

		for (int i = 0; i < ll.size(); i++) {
			System.out.println(((DBColumn) ll.get(i)).getColumnName());
		}

		for (int i = 0; i < l2.size(); i++) {
			Map m = (Map) l2.get(i);
			for (int j = 0; j < ll.size(); j++) {
				System.out.println(m
						.get(((DBColumn) ll.get(j)).getColumnName()));
			}
			System.out.println("");
		}
*/
		DBMConnectionManager.closeConnection(ora);
	}

}

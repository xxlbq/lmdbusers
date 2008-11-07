/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.connection.OraConnectionInfo;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.exception.DBMException;

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
public class DBMConnectionManager {

	static Map connectionPool = new HashMap();

	static Map connectionUsing = new HashMap();

	private static String SEPARATOR = ":";

	private DBMConnectionManager() {
	}

	/**
	 * [機 能] get database connection
	 * <p>
	 * [解 説] get database connection。
	 * <p>
	 * [備 考] なし
	 * <p>
	 * [作成日付] 2006/09/16
	 * <p>
	 * [更新日付]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo object
	 *            <p>
	 * 
	 * @return Connection database connection
	 *         <p>
	 * 
	 * @deprecated
	 * 
	 * @throws DBMException
	 */
	public static Connection getConnection(ConnectionInfo connectionInfo) throws DBMException {
		// String url="jdbc:oracle:thin:@localhost:1521:orcl";

		Connection conn = null;

		String stKey = connectionInfo.getConnectionName();

		if (connectionPool.containsKey(stKey) == true) {
			conn = (Connection) connectionPool.get(stKey);

		} else {
			try {
				if (connectionInfo.getDbType().equals(DBServerType.ORACLE9i)) {
					conn = createOracleConnection((OraConnectionInfo) connectionInfo);
				} else {
					conn = createNormalConnection(connectionInfo);
				}

				// setting auto commit is false
				// conn.setAutoCommit(false);

				addConnection(connectionInfo, conn);
			} catch (Exception e) {
				throw new DBMException(e);
			}
		}

		return conn;
	}

	/**
	 * [功 能] 取得数据库连接
	 * <p>
	 * [说 明] 通过给定的参数，取得数据库连接，并返回
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo object
	 * 
	 * @param dBSession
	 *            DBSession object
	 *            <p>
	 * 
	 * @return Connection database connection
	 *         <p>
	 * 
	 * @throws DBMException
	 */
	public static Connection getConnection(ConnectionInfo connectionInfo, DBSession dBSession) throws DBMException {
		// String url="jdbc:oracle:thin:@localhost:1521:orcl";

		Connection conn = null;

		String stId = dBSession.getSessionId();
		String stKey = connectionInfo.getConnectionName();

		String stConnId = stKey + SEPARATOR + stId;

		if (connectionUsing.containsKey(stConnId) == true) {
			conn = (Connection) connectionUsing.get(stConnId);

		} else if ((conn = getConnFromPool(connectionInfo)) != null) {
			connectionUsing.put(stConnId, conn);
		} else {
			try {
				if (connectionInfo.getDbType().equals(DBServerType.ORACLE9i)) {
					conn = createOracleConnection((OraConnectionInfo) connectionInfo);
				} else {
					conn = createNormalConnection(connectionInfo);
				}

				// setting auto commit is false
				// conn.setAutoCommit(false);
				// addConnection(connectionInfo, conn);

				connectionUsing.put(stConnId, conn);

			} catch (Exception e) {
				throw new DBMException(e);
			}
		}

		return conn;
	}

	/**
	 * [功 能] 释放数据库连接
	 * <p>
	 * [说 明] 通过给定的参数，释放相应的数据库连接
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo object
	 * 
	 * @param dBSession
	 *            DBSession object
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static void releaseConnection(ConnectionInfo connectionInfo, DBSession dBSession) throws DBMException{
		Connection conn = null;
		String stId = dBSession.getSessionId();
		String stKey = connectionInfo.getConnectionName();
		String stConnId = stKey + SEPARATOR + stId;

		if (connectionUsing.containsKey(stConnId) == true) {
			conn = (Connection) connectionUsing.get(stConnId);

			try {
				if (conn.getAutoCommit() == false) {
					commit(connectionInfo, dBSession);
					setAutoCommit(connectionInfo, dBSession, true);
				}

			} catch (DBMException e) {
				e.printStackTrace();
				throw new DBMException(e);
			}
			catch(SQLException ex)
			{
				ex.printStackTrace();
				throw new DBMException(ex);
			}

			connectionUsing.remove(stConnId);

			connectionPool.put(stConnId, conn);
		}
	}

	/**
	 * [功 能] 关闭数据库连接
	 * <p>
	 * [说 明] 通过给定的参数，关闭相应的一个数据库连接
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo object
	 * 
	 * @param dBSession
	 *            DBSession object
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static void closeConnection(ConnectionInfo connectionInfo, DBSession dBSession) {

		Connection conn = null;
		String stId = dBSession.getSessionId();
		String stKey = connectionInfo.getConnectionName();

		String stConnId = stKey + SEPARATOR + stId;

		if (connectionUsing.containsKey(stConnId) == true) {
			conn = (Connection) connectionUsing.get(stConnId);
			connectionUsing.remove(stConnId);
		} else if (connectionPool.containsKey(stConnId) == true) {
			conn = (Connection) connectionPool.get(stConnId);

			connectionPool.remove(stConnId);
		}

		try {
			if (conn != null) {
				if(connectionInfo.getDbType().equals(DBServerType.DB2))
				{
					resetConnection(conn);
					if(conn.getAutoCommit() == false)
					{
						conn.setAutoCommit(true);
					}
				}
				conn.close();
				conn = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * [功 能] 关闭数据库连接
	 * <p>
	 * [说 明] 通过给定的参数，关闭相应的一种类型数据库连接
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo object
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static void closeConnection(ConnectionInfo connectionInfo) {

		String stKey = connectionInfo.getConnectionName();
		Iterator iterator = null;

		iterator = connectionUsing.keySet().iterator();
		while (iterator.hasNext()) {

			String key = (String) iterator.next();
			String keyName = key.substring(0, key.indexOf(SEPARATOR));
			if (keyName.equals(stKey)) {
				Connection conn = (Connection) connectionUsing.get(key);
				iterator.remove();
				// connectionUsing.remove(key);

				try {
					if (conn != null) {
						conn.close();
					}

				} catch (SQLException e) {
					// e.printStackTrace();
				}
			}
		}

		iterator = connectionPool.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();

			String keyName = key.substring(0, key.indexOf(SEPARATOR));

			// if (key.startsWith(stKey)) {
			if (keyName.equals(stKey)) {
				Connection conn = (Connection) connectionPool.get(key);

				iterator.remove();
				// connectionPool.remove(key);

				try {
					if (conn != null) {
						conn.close();
					}

				} catch (SQLException e) {
					// e.printStackTrace();
				}
			}
		}
	}

	/**
	 * [功 能] 关闭数据库连接
	 * <p>
	 * [说 明] 应用程序终止之前，关闭所有的数据库连接
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public static void closeAllConnection() {
		for (Iterator iter = connectionPool.keySet().iterator(); iter.hasNext();) {
			Object key = iter.next();
			Connection conn = (Connection) connectionPool.get(key);

			try {
				conn.close();
			} catch (Exception e) {
				continue;
			}
		}

		for (Iterator iter = connectionUsing.keySet().iterator(); iter.hasNext();) {
			Object key = iter.next();
			Connection conn = (Connection) connectionUsing.get(key);

			try {
				conn.close();
			} catch (Exception e) {
				continue;
			}
		}

		connectionPool.clear();
		connectionUsing.clear();
	}

	/**
	 * [功 能] 设置数据库连接的自动提交模式
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param autoCommit
	 *            <code>true</code> to enable auto-commit mode;
	 *            <code>false</code> to disable it
	 * @param connectionInfo
	 *            ConnectionInfo
	 * @param dBSession
	 *            DBSession
	 * @exception SQLException
	 *                if a database access error occurs
	 * 
	 * @return void
	 */
	public static void setAutoCommit(ConnectionInfo connectionInfo, DBSession dBSession, boolean autoCommit) throws DBMException {

		Connection conn = getConnection(connectionInfo, dBSession);

		try {
			conn.setAutoCommit(autoCommit);
		} catch (Exception e) {
			throw new DBMException(e);
		}

	}

	/**
	 * [機 能] Sets this connection's auto-commit mode to the given state.
	 * <p>
	 * [解 説] If a connection is in auto-commit mode, then all its SQL statements
	 * will be executed and committed as individual transactions. Otherwise, its
	 * SQL statements are grouped into transactions that are terminated by a
	 * call to either the method <code>commit</code> or the method
	 * <code>rollback</code>. By default, new connections are in auto-commit
	 * mode.
	 * <P>
	 * [備 考] なし
	 * <p>
	 * [作成日付] 2006/09/16
	 * <p>
	 * [更新日付]
	 * <p>
	 * 
	 * @param autoCommit
	 *            <code>true</code> to enable auto-commit mode;
	 *            <code>false</code> to disable it
	 * @param connectionInfo
	 *            ConnectionInfo
	 * @exception SQLException
	 *                if a database access error occurs
	 * 
	 * @return void
	 * 
	 * @deprecated
	 */
	public static void setAutoCommit(ConnectionInfo connectionInfo, boolean autoCommit) throws DBMException {
		String stKey = connectionInfo.getConnectionName();
		if (connectionPool.containsKey(stKey) == true) {

			try {
				Connection conn = (Connection) connectionPool.get(stKey);
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				throw new DBMException(e);
			}
		}
	}

	/**
	 * [機 能] commit the previous changes to database.
	 * <p>
	 * [解 説] Makes all changes made since the previous commit/rollback permanent
	 * and releases any database locks currently held by this
	 * <code>Connection</code> object. This method should be used only when
	 * auto-commit mode has been disabled.
	 * <P>
	 * [備 考] なし
	 * <p>
	 * [作成日付] 2006/09/16
	 * <p>
	 * [更新日付]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo object
	 *            <p>
	 * @exception DBMException
	 *                if a database access error occurs or this
	 *                <code>Connection</code> object is in auto-commit mode
	 * @see #setAutoCommit
	 * 
	 * @deprecated
	 */
	public static void commit(ConnectionInfo connectionInfo) throws DBMException {
		String stKey = connectionInfo.getConnectionName();
		if (connectionPool.containsKey(stKey) == true) {

			try {
				Connection conn = (Connection) connectionPool.get(stKey);
				conn.commit();
			} catch (SQLException e) {
				throw new DBMException(e);
			}
		}
	}

	/**
	 * [功 能] 将数据库改变的操作予以提交
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo
	 * @param dBSession
	 *            DBSession
	 * @exception SQLException
	 *                if a database access error occurs
	 * 
	 * @return void
	 */
	public static void commit(ConnectionInfo connectionInfo, DBSession dBSession) throws DBMException {
		String stKey = connectionInfo.getConnectionName();
		String stId = dBSession.getSessionId();

		String stConnId = stKey + SEPARATOR + stId;

		if (connectionUsing.containsKey(stConnId) == true) {

			try {
				Connection conn = (Connection) connectionUsing.get(stConnId);
				conn.commit();
			} catch (SQLException e) {
				throw new DBMException(e);
			}
		}
	}

	/**
	 * [機 能] rollback operation
	 * <p>
	 * [解 説] Undoes all changes made in the current transaction and releases any
	 * database locks currently held by this <code>Connection</code> object.
	 * This method should be used only when auto-commit mode has been disabled.
	 * [備 考] なし
	 * <p>
	 * [作成日付] 2006/09/16
	 * <p>
	 * [更新日付]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo object
	 *            <p>
	 * @exception DBMException
	 *                if a database access error occurs or this
	 *                <code>Connection</code> object is in auto-commit mode
	 * @see #setAutoCommit
	 * 
	 * @deprecated
	 */
	public static void rollback(ConnectionInfo connectionInfo) throws DBMException {
		String stKey = connectionInfo.getConnectionName();
		if (connectionPool.containsKey(stKey) == true) {

			try {
				Connection conn = (Connection) connectionPool.get(stKey);
				conn.rollback();
			} catch (SQLException e) {
				throw new DBMException(e);
			}
		}
	}

	/**
	 * [功 能] 回朔将数据库改变的操作
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo
	 * @param dBSession
	 *            DBSession
	 * @exception SQLException
	 *                if a database access error occurs
	 * 
	 * @return void
	 */
	public static void rollback(ConnectionInfo connectionInfo, DBSession dBSession) throws DBMException {
		String stKey = connectionInfo.getConnectionName();
		String stId = dBSession.getSessionId();

		String stConnId = stKey + SEPARATOR + stId;
		if (connectionUsing.containsKey(stConnId) == true) {

			try {
				Connection conn = (Connection) connectionUsing.get(stConnId);
				conn.rollback();
			} catch (SQLException e) {
				throw new DBMException(e);
			}
		}
	}

	/**
	 * [機 能] insetead connection
	 * <p>
	 * [解 説] insetead the current connection to a new connection.
	 * 
	 * [備 考] なし
	 * <p>
	 * [作成日付] 2006/09/16
	 * <p>
	 * [更新日付]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo object
	 *            <p>
	 * @return boolean
	 * 
	 * @deprecated
	 */
	public static void insteadConnection(ConnectionInfo connectionInfo) throws DBMException {
		Connection conn = null;

		try {
			if (connectionInfo.getDbType().equals(DBServerType.ORACLE9i)) {
				conn = createOracleConnection((OraConnectionInfo) connectionInfo);
			} else {
				conn = createNormalConnection(connectionInfo);
			}

		} catch (Exception e) {
			throw new DBMException(e);
		}

		String stKey = connectionInfo.getConnectionName();

		if (connectionPool.containsKey(stKey) == true) {

			closeConnection(connectionInfo);
			/*
			 * Connection con = (Connection) connectionPool.get(stKey); try {
			 * if(con != null) { con.close(); } } catch(SQLException e) { }
			 * connectionPool.remove(stKey);
			 */
		}

		addConnection(connectionInfo, conn);
	}

	/**
	 * [功 能] 将当前的数据库连接根据给定的参数进行替换。
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo
	 * @param dBSession
	 *            DBSession
	 * @exception SQLException
	 *                if a database access error occurs
	 * 
	 * @return void
	 */
	public static void insteadConnection(ConnectionInfo connectionInfo, DBSession dBSession) throws DBMException {
		// closeConnection(connectionInfo, dBSession);
		closeConnection(connectionInfo);
		getConnection(connectionInfo, dBSession);
	}

	/**
	 * [功 能] 将数据库连接放入连接池
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo
	 * @param dBSession
	 *            DBSession
	 *            <p>
	 * @return void
	 *         <p>
	 */
	private static void addConnection(ConnectionInfo connectionInfo, Connection conn) {
		String stKey = connectionInfo.getConnectionName();
		if (connectionPool.containsKey(stKey) == false) {
			connectionPool.put(stKey, conn);
		}
	}

	/**
	 * [功 能] 建立数据库连接
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo
	 *            <p>
	 * @return void
	 *         <p>
	 */
	private static Connection createNormalConnection(ConnectionInfo connectionInfo) throws Exception {
		// 注册数据库
		// Class.for Name(“oracle.jdbc.driver. // 建立连接
		Class.forName(connectionInfo.getDriverStr());

		return DriverManager.getConnection(connectionInfo.getURL(), connectionInfo.getUserName(), connectionInfo.getPassword());
	}

	/**
	 * [功 能] 建立数据库连接
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo
	 *            <p>
	 * @return void
	 *         <p>
	 */
	private static Connection createOracleConnection(OraConnectionInfo connectionInfo) throws Exception {
		java.util.Properties info = new java.util.Properties();
		info.put("user", connectionInfo.getUserName());
		info.put("password", connectionInfo.getPassword());
		info.put("internal_logon", connectionInfo.getRole());

		Class.forName(connectionInfo.getDriverStr());

		return DriverManager.getConnection(connectionInfo.getURL(), info);
	}

	/*
	 * private static void removeConnection(ConnectionInfo connectionInfo) {
	 * String stKey = connectionInfo.getConnectionName(); if
	 * (connectionPool.containsKey(stKey) == true) {
	 * connectionPool.remove(stKey); } }
	 */

	/**
	 * [功 能] 从连接池中取出连接以供使用
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo
	 *            <p>
	 * @return Connection 数据库连接
	 *         <p>
	 */
	private static Connection getConnFromPool(ConnectionInfo connectionInfo) {
		Connection conn = null;

		for (Iterator iter = connectionPool.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();

			if (key.startsWith(connectionInfo.getConnectionName() + SEPARATOR)) {
				conn = (Connection) connectionPool.get(key);
				connectionPool.remove(key);

				break;
			}
		}
		return conn;
	}
	
	/**
	 * [功 能] reset connection
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param connectionInfo
	 *            ConnectionInfo
	 *            <p>
	 * @return Connection 数据库连接
	 *         <p>
	 */
	private static void resetConnection(Connection conn) {
		try
		{
			conn.getAutoCommit();
		}
		catch(SQLException e)
		{
		}
	}
}
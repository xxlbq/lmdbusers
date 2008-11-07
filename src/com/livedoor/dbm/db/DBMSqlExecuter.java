/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.connection.OraConnectionInfo;
import com.livedoor.dbm.constants.DBResultType;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.util.ConvertObject;

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
public abstract class DBMSqlExecuter {

	/*
	 * ConnectionInfo class object
	 */
	protected ConnectionInfo conInfo;

	/*
	 * DBSession class object
	 */
	protected DBSession dBSession;

	/*
	 * List for return the resultset.
	 */
	private List list;

	/*
	 * Statement
	 */
	protected Statement _currentStatement = null;

	/*
	 * executing flag
	 */
	protected boolean _bIsExecuting;

	protected String stUpdateMessage = " record(s) affected ";

	/**
	 * [功 能] 执行多条sql语句
	 * <p>
	 * [说 明] 用来执行多条sql语句
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param sqls
	 *            sql语句数组
	 * @param iCount
	 *            返回的最大记录数量
	 *            <p>
	 * 
	 * @return DBMSqlResult[] 结果集数组
	 *         <p>
	 */
	public DBMSqlResult[] execute(String[] sqls, int iCount) throws DBMException {
		int iLen = sqls.length;
		ResultSet rs = null;
		Statement stmt = null;
		DBMSqlResult[] dBMSqlResults = new DBMSqlResult[iLen];
		Connection conn = null;

		try {
			conn = DBMConnectionManager.getConnection(conInfo, dBSession);
		} catch (Exception e) {
			throw new DBMException(e);
		}

		for (int i = 0; i < iLen; i++) {

			dBMSqlResults[i] = new DBMSqlResult();

			try {
				stmt = conn.createStatement();
				boolean brs = stmt.execute(sqls[i]);

				if (brs == true) {
					rs = stmt.getResultSet();

					DBMDataResult dBMDataResult = new DBMDataResult();
					dBMDataResult.dealResultSet(rs, iCount);
					dBMSqlResults[i].setDBMDataResult(dBMDataResult);
					dBMSqlResults[i].setType(DBResultType.MUTI_ROWS);
					if (((stmt.getMoreResults() == false) && (stmt.getUpdateCount() == -1)) == false) {
						int iRow = stmt.getUpdateCount();
						dBMSqlResults[i].setMessage(iRow + stUpdateMessage);
						dBMSqlResults[i].setType(DBResultType.MUTI_RES);
					}
				} else {
					int iRow = stmt.getUpdateCount();

					dBMSqlResults[i].setMessage(iRow + stUpdateMessage);
					dBMSqlResults[i].setType(DBResultType.ZERO_ROWS);
					if (((stmt.getMoreResults() == false) && (stmt.getUpdateCount() == -1)) == false) {
						rs = stmt.getResultSet();

						DBMDataResult dBMDataResult = new DBMDataResult();
						dBMDataResult.dealResultSet(rs, iCount);
						dBMSqlResults[i].setDBMDataResult(dBMDataResult);
						dBMSqlResults[i].setType(DBResultType.MUTI_RES);
					}
				}
			} catch (Exception e) {
				String stMessage = e.getMessage();
				dBMSqlResults[i].setMessage(stMessage);
				dBMSqlResults[i].setType(DBResultType.ERROR);
				continue;
			} finally {
				try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
					if (stmt != null) {
						stmt.close();
						stmt = null;
					}
				} catch (Exception e) {
				}
			}
		}

		/*
		 * try { if (stmt != null) { stmt.close(); stmt = null; } } catch
		 * (SQLException e) { }
		 */

		return dBMSqlResults;
	}

	/**
	 * [功 能] 执行多条sql语句
	 * <p>
	 * [说 明] 用来执行多条sql语句
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param sqls
	 *            sql语句
	 * @param iCount
	 *            返回的最大记录数量
	 *            <p>
	 * 
	 * @return DBMSqlResult 结果集
	 *         <p>
	 */
	public DBMSqlResult execute(String sqls, int iCount) throws DBMException {

		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;

		DBMSqlResult dBMSqlResult = new DBMSqlResult();
		dBMSqlResult.setType(DBResultType.SUCCESS);

		try {
			conn = DBMConnectionManager.getConnection(conInfo, dBSession);
		} catch (Exception e) {
			throw new DBMException(e);
		}

		try {
			stmt = conn.createStatement();

			if (iCount >= 0) {
				stmt.setMaxRows(iCount);
			}

			_currentStatement = stmt;

			setExecuting(true);
			boolean brs = stmt.execute(sqls);

			if (brs == true) {
				rs = stmt.getResultSet();

				DBMDataResult dBMDataResult = new DBMDataResult();
				dBMDataResult.dealResultSet(rs, iCount);
				dBMSqlResult.setDBMDataResult(dBMDataResult);
				dBMSqlResult.setType(DBResultType.MUTI_ROWS);
				if (((stmt.getMoreResults() == false) && (stmt.getUpdateCount() == -1)) == false) {
					int iRow = stmt.getUpdateCount();
					dBMSqlResult.setMessage(iRow + stUpdateMessage);
					dBMSqlResult.setType(DBResultType.MUTI_RES);
				}
			} else {
				int iRow = stmt.getUpdateCount();

				dBMSqlResult.setMessage(iRow + stUpdateMessage);
				dBMSqlResult.setType(DBResultType.ZERO_ROWS);
				if (((stmt.getMoreResults() == false) && (stmt.getUpdateCount() == -1)) == false) {
					rs = stmt.getResultSet();

					DBMDataResult dBMDataResult = new DBMDataResult();
					dBMDataResult.dealResultSet(rs, iCount);
					dBMSqlResult.setDBMDataResult(dBMDataResult);
					dBMSqlResult.setType(DBResultType.MUTI_RES);
				}
			}
		} catch (Exception e) {
			setExecuting(false);
			String stMessage = e.getMessage();
			dBMSqlResult.setMessage(stMessage);
			dBMSqlResult.setType(DBResultType.ERROR);
		} finally {
			setExecuting(false);
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (Exception e) {
			}
		}
		setExecuting(false);
		return dBMSqlResult;
	}

	private DBMSqlResult execute(String sqls, int iCount, int i) throws DBMException {

		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;

		DBMSqlResult dBMSqlResult = new DBMSqlResult();
		dBMSqlResult.setType(DBResultType.SUCCESS);

		try {
			conn = DBMConnectionManager.getConnection(conInfo, dBSession);
		} catch (Exception e) {
			throw new DBMException(e);
		}

		try {
			stmt = conn.createStatement();
			boolean brs = stmt.execute(sqls);
			List listResult = new ArrayList();
			List listMessage = new ArrayList();

			dealMoreResult(brs, stmt, listResult, listMessage, iCount);

			DBMDataResult[] dBMDataResults = new DBMDataResult[listResult.size()];
			listResult.toArray(dBMDataResults);
			String[] messages = new String[listMessage.size()];
			listMessage.toArray(messages);

			dBMSqlResult.setDBMDataResults(dBMDataResults);
			dBMSqlResult.setArrayMessage(messages);

		} catch (Exception e) {
			String stMessage = e.getMessage();
			dBMSqlResult.setErrorMessage(stMessage);
			dBMSqlResult.setType(DBResultType.ERROR);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (Exception e) {
			}
		}

		return dBMSqlResult;
	}

	private void dealMoreResult(boolean bFlag, Statement stmt, List listResult, List listMessage, int iCount) throws SQLException {

		ResultSet rs = null;

		if (bFlag == true) {
			rs = stmt.getResultSet();
			DBMDataResult dBMDataResult = new DBMDataResult();
			dBMDataResult.dealResultSet(rs, iCount);
			listResult.add(dBMDataResult);

		} else {
			int iRow = stmt.getUpdateCount();
			if (iRow != -1) {
				String stMessage = iRow + stUpdateMessage;
				listMessage.add(stMessage);
			}
		}
		boolean bRes = stmt.getMoreResults();

		if ((bRes == false) && (stmt.getUpdateCount() == -1) == false) {
			dealMoreResult(bRes, stmt, listResult, listMessage, iCount);
		}
	}
	/**
	 * [功 能] 执行多条sql语句
	 * <p>
	 * [说 明] 用来执行多条sql语句
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param sqls
	 *            sql语句数组
	 *            <p>
	 * 
	 * @return DBMSqlResult[] 结果集数组
	 *         <p>
	 */
	public DBMSqlResult[] execute(String[] sqls) throws DBMException {
		return execute(sqls, -1);
	}

	/**
	 * [功 能] 执行sql语句
	 * <p>
	 * [说 明] 用来执行sql语句，包括insert, update or delete 等操作
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param stSql
	 *            sql语句
	 *            <p>
	 * 
	 * @return int 返回执行语句后，影响的记录数量
	 *         <p>
	 */
	public int executeUpdate(String stSql) throws DBMException {
		int iRow = 0;
		Statement statement = null;
		try {
			Connection conn = DBMConnectionManager.getConnection(getConnectionInfo(), dBSession);
			statement = conn.createStatement();

			iRow = statement.executeUpdate(stSql);

		} catch (Exception e) {
			throw new DBMException(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
					statement = null;
				} catch (SQLException e) {
				}
			}
		}

		return iRow;
	}

	/**
	 * [功 能] 执行sql语句
	 * <p>
	 * [说 明] 用来执行sql语句，包括insert, update or delete 等操作，使用的preparedstatement
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param stSql
	 *            sql 语句
	 * @param objs
	 *            参数
	 *            <p>
	 * 
	 * @return int 返回执行语句后，影响的记录数量
	 *         <p>
	 */
	public int executeUpdate(String stSql, Object[] objs) throws DBMException {
		int iRow = 0;
		PreparedStatement stmt = null;
		try {
			Connection conn = DBMConnectionManager.getConnection(getConnectionInfo(), dBSession);
			stmt = conn.prepareStatement(stSql);

			for (int i = 0; i < objs.length; i++) {
				ConvertObject co = (ConvertObject) objs[i];

				stmt.setObject(i + 1, co.getValue(), co.getType());
			}

			iRow = stmt.executeUpdate();

		} catch (Exception e) {
			throw new DBMException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
				}

			}
		}

		return iRow;
	}

	/**
	 * [功 能] 执行sql语句
	 * <p>
	 * [说 明] 用来执行查询操作
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param stSql
	 *            sql 语句
	 * @param iCount
	 *            返回查询的最大数量
	 *            <p>
	 * 
	 * @return DBMDataResult 查询结果集
	 *         <p>
	 */
	public DBMDataResult executeQuery(String stSql, int iCount) throws DBMException {
		ResultSet rs = null;
		Statement statement = null;
		DBMDataResult dBMDataResult = null;

		try {
			Connection conn = DBMConnectionManager.getConnection(conInfo, dBSession);
			statement = conn.createStatement();

			rs = statement.executeQuery(stSql);

			dBMDataResult = new DBMDataResult();
			dBMDataResult.dealResultSet(rs, iCount);

		} catch (Exception e) {
			throw new DBMException(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (statement != null) {
					statement.close();
					statement = null;
				}
			} catch (Exception e) {
			}
		}

		return dBMDataResult;
	}
	/**
	 * [功 能] 执行sql语句
	 * <p>
	 * [说 明] 用来执行查询操作
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param stSql
	 *            sql 语句
	 *            <p>
	 * 
	 * @return DBMDataResult 查询结果集
	 *         <p>
	 */
	public DBMDataResult executeQuery(String stSql) throws DBMException {
		return executeQuery(stSql, -1);
	}

	/**
	 * [功 能] 执行sql语句
	 * <p>
	 * [说 明] 用来执行查询操作
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param stSql
	 *            sql 语句
	 *            <p>
	 * 
	 * @return DBMDataResult 查询结果集
	 *         <p>
	 */
	public ResultSet executeQueryRes(String stSql) throws DBMException {
		ResultSet resultSet = null;

		try {
			Connection conn = DBMConnectionManager.getConnection(conInfo, dBSession);
			Statement statement = conn.createStatement();

			resultSet = statement.executeQuery(stSql);
		} catch (Exception e) {
			throw new DBMException(e);
		}

		return resultSet;
	}
	
	/**
	 * [功 能] 获取Schema
	 * <p>
	 * [说 明] 获取Schema列表信息
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @return List Schema列表
	 *         <p>
	 */
	public List getSchemas() throws DBMException {
		list = new ArrayList();
		ResultSet rs = null;
		DBMDataResult dBMDataResult = null;

		try {

			Connection conn = DBMConnectionManager.getConnection(conInfo, dBSession);

			DatabaseMetaData dm = conn.getMetaData();

			rs = dm.getSchemas();

			while (rs.next()) {
				list.add(rs.getString(1));
			}

		} catch (Exception e) {
			throw new DBMException(e);
		} finally {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {

			}
		}

		return list;
	}

	/**
	 * [功 能] 获取catalogs
	 * <p>
	 * [说 明] 获取catalogs信息
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @return DBMDataResult catalogs结果集
	 *         <p>
	 */
	public DBMDataResult getCatalogs() throws DBMException {
		list = new ArrayList();
		ResultSet rs = null;
		DBMDataResult dBMDataResult = null;

		try {

			Connection conn = DBMConnectionManager.getConnection(conInfo, dBSession);
			DatabaseMetaData dm = conn.getMetaData();

			rs = dm.getCatalogs();
			dBMDataResult = new DBMDataResult();
			dBMDataResult.dealResultSet(rs);

		} catch (Exception e) {
			throw new DBMException(e);
		} finally {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {

			}
		}

		return dBMDataResult;
	}

	/**
	 * [功 能] 获取数据类型
	 * <p>
	 * [说 明] 获取数据类型信息
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @return DBMDataResult 数据类型结果集
	 *         <p>
	 */
	public DBMDataResult getTypeInfo() throws DBMException {
		list = new ArrayList();
		ResultSet rs = null;
		DBMDataResult dBMDataResult = null;

		try {

			Connection conn = DBMConnectionManager.getConnection(conInfo, dBSession);

			DatabaseMetaData dm = conn.getMetaData();

			rs = dm.getTypeInfo();

			dBMDataResult = new DBMDataResult();
			dBMDataResult.dealResultSet(rs);

		} catch (Exception e) {
			throw new DBMException(e);
		} finally {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {

			}
		}

		return dBMDataResult;
	}

	/**
	 * [功 能] 根据给定的参数，返回满足条件的表主键信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return DBMDataResult 主键结果集
	 *         <p>
	 */
	public DBMDataResult getPrimaryKeys(DBMDataMetaInfo dbMetaInfo) throws DBMException {
		return getPrimaryKeys(dbMetaInfo.getCatalog(), dbMetaInfo.getSchemaName(), dbMetaInfo.getTableName());
	}

	/**
	 * [功 能] 根据给定的参数，返回满足条件的表主键信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param catalog
	 *            Catalog 信息
	 * @param schema
	 *            Schema 信息
	 * @param table
	 *            表
	 *            <p>
	 * @return DBMDataResult 主键结果集
	 *         <p>
	 */
	protected DBMDataResult getPrimaryKeys(String catalog, String schema, String table) throws DBMException {
		list = new ArrayList();
		ResultSet rs = null;
		DBMDataResult dBMDataResult = null;

		try {

			Connection conn = DBMConnectionManager.getConnection(conInfo, dBSession);

			DatabaseMetaData dm = conn.getMetaData();

			rs = dm.getPrimaryKeys(catalog, schema, table);

			dBMDataResult = new DBMDataResult();
			dBMDataResult.dealResultSet(rs);

		} catch (Exception e) {
			throw new DBMException(e);
		} finally {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {

			}
		}

		return dBMDataResult;
	}

	/**
	 * [功 能] 根据给定的参数，返回满足条件的表外键信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return DBMDataResult 外键结果集
	 *         <p>
	 */
	public DBMDataResult getForeignKeys(DBMDataMetaInfo dbMetaInfo) throws DBMException {
		return getForeignKeys(dbMetaInfo.getCatalog(), dbMetaInfo.getSchemaName(), dbMetaInfo.getTableName());
	}

	/**
	 * [功 能] 根据给定的参数，返回满足条件的表外键信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param catalog
	 *            Catalog 信息
	 * @param schema
	 *            Schema 信息
	 * @param table
	 *            表
	 *            <p>
	 * @return DBMDataResult 外键结果集
	 *         <p>
	 */
	protected DBMDataResult getForeignKeys(String catalog, String schema, String table) throws DBMException {
		list = new ArrayList();
		ResultSet rs = null;
		DBMDataResult dBMDataResult = null;

		try {

			Connection conn = DBMConnectionManager.getConnection(conInfo, dBSession);

			DatabaseMetaData dm = conn.getMetaData();

			// rs = dm.getExportedKeys(catalog, schema, table);
			rs = dm.getImportedKeys(catalog, schema, table);
			dBMDataResult = new DBMDataResult();
			dBMDataResult.dealResultSet(rs);

		} catch (Exception e) {
			throw new DBMException(e);
		} finally {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {

			}
		}

		return dBMDataResult;
	}

	/**
	 * [功 能] 根据给定的参数，返回满足条件的表信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return DBMDataResult 表结果集
	 *         <p>
	 */
	public abstract DBMDataResult getTables(DBMDataMetaInfo dbMetaInfo) throws DBMException;

	/**
	 * [功 能] 根据给定的参数，返回满足条件的表信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param catalogName
	 *            Catalog 信息
	 * @param schemaPattern
	 *            Schema 信息
	 * @param tableNamePattern
	 *            表
	 * @param types
	 *            表类型
	 *            <p>
	 * @return DBMDataResult 表结果集
	 *         <p>
	 */
	protected DBMDataResult getTables(String catalogName, String schemaPattern, String tableNamePattern, String[] types)
			throws DBMException {
		list = new ArrayList();
		ResultSet rs = null;
		DBMDataResult dBMDataResult = null;

		if (types == null) {
			types = new String[]{"TABLE", "SYSTEM TABLE"};
		}

		try {

			Connection conn = DBMConnectionManager.getConnection(conInfo, dBSession);

			DatabaseMetaData dm = conn.getMetaData();

			rs = dm.getTables(catalogName, schemaPattern, tableNamePattern, types);

			dBMDataResult = new DBMDataResult();
			dBMDataResult.dealResultSet(rs);

		} catch (Exception e) {
			throw new DBMException(e);
		} finally {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {

			}
		}

		return dBMDataResult;
	}

	/**
	 * [功 能] 获取数据库列表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return list 数据库列表
	 *         <p>
	 */
	public abstract List getDatabase() throws DBMException;

	/**
	 * [功 能] 获取视图列表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return list 视图列表
	 *         <p>
	 */
	public abstract List getViews(DBMDataMetaInfo dbMetaInfo) throws DBMException;

	/**
	 * [功 能] 获取Table列表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return list Table列表
	 *         <p>
	 */
	public abstract List<String> getTableNames(DBMDataMetaInfo dbMetaInfo) throws DBMException;

	/**
	 * [功 能] 根据给定的参数，返回满足条件的列信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return DBMDataResult 列结果集
	 *         <p>
	 */
	public abstract DBMDataResult getColumns(DBMDataMetaInfo dbMetaInfo) throws DBMException;

	/**
	 * [功 能] 根据给定的参数，返回满足条件的列信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param catalog
	 *            Catalog 信息
	 * @param schemaPattern
	 *            Schema 信息
	 * @param tableNamePattern
	 *            表
	 * @param columnNamePattern
	 *            列信息
	 *            <p>
	 * @return DBMDataResult 列结果集
	 *         <p>
	 */
	protected DBMDataResult getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)
			throws DBMException {
		list = new ArrayList();
		ResultSet rs = null;
		DBMDataResult dBMDataResult = null;

		try {

			Connection conn = DBMConnectionManager.getConnection(conInfo, dBSession);

			DatabaseMetaData dm = conn.getMetaData();

			rs = dm.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);

			dBMDataResult = new DBMDataResult();
			dBMDataResult.dealResultSet(rs);

		} catch (Exception e) {
			throw new DBMException(e);
		} finally {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {

			}
		}

		return dBMDataResult;
	}

	/**
	 * [功 能] 设定 ConnectionInfo 信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param conInfo
	 *            ConnectionInfo
	 *            <p>
	 * @return 无
	 *         <p>
	 */
	public void setConnectionInfo(ConnectionInfo conInfo) {
		this.conInfo = conInfo;
	}

	/**
	 * [功 能] 取得 ConnectionInfo 信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return ConnectionInfo
	 *         <p>
	 */
	public ConnectionInfo getConnectionInfo() {
		return this.conInfo;
	}

	/**
	 * [功 能] 设定 DBSession 信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dBSession
	 *            DBSession
	 *            <p>
	 * @return 无
	 *         <p>
	 */
	public void setDBSession(DBSession dBSession) {
		this.dBSession = dBSession;
	}

	/**
	 * [功 能] 取得 DBSession 信息
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return DBSession
	 *         <p>
	 */
	public DBSession getDBSession() {
		return this.dBSession;
	}

	/**
	 * [功 能] 改变数据库
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbName
	 *            数据库名称
	 *            <p>
	 * @return 无
	 *         <p>
	 */
	public abstract void changeDatabase(String dbName);

	/**
	 * [功 能] 获取表中所有自增型列列表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param dbMetaInfo
	 *            DBMDataMetaInfo 信息
	 *            <p>
	 * @return list 自增型列列表
	 *         <p>
	 */
	public List getAutoIncrementColumns(DBMDataMetaInfo dbMetaInfo) throws DBMException {

		String stSql = "SELECT * FROM \"" + dbMetaInfo.getSchemaName() + "\".\"" + dbMetaInfo.getTableName() + "\" WHERE 1 = 2";

		return getAutoIncrementColumns(stSql);
	}

	/**
	 * [功 能] 获取表中所有自增型列列表
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param stSql
	 *            sql statement
	 *            <p>
	 * @return list 自增型列列表
	 *         <p>
	 */
	protected List getAutoIncrementColumns(String stSql) throws DBMException {
		list = new ArrayList();

		ResultSet rs = null;
		Statement statement = null;
		ResultSetMetaData rsm = null;
		try {
			Connection conn = DBMConnectionManager.getConnection(getConnectionInfo(), dBSession);
			statement = conn.createStatement();

			rs = statement.executeQuery(stSql);
			rsm = rs.getMetaData();

			int iCount = rsm.getColumnCount();

			for (int i = 1; i <= iCount; i++) {
				if (rsm.isAutoIncrement(i) == true) {
					list.add(rsm.getColumnName(i));
				}
			}

		} catch (Exception e) {
			throw new DBMException(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}

				if (statement != null) {

					statement.close();
					statement = null;
				}
			} catch (Exception ex) {
			}
		}

		return list;
	}

	/**
	 * [功 能] 取消当前执行的数据库操作
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	public void cancel() {
		try {
			if (isExecuting() && _currentStatement != null)
				_currentStatement.cancel();
		} catch (SQLException exception) {
		}
	}

	protected synchronized void setExecuting(boolean flag) {
		_bIsExecuting = flag;
	}

	private synchronized boolean isExecuting() {
		return _bIsExecuting;
	}

	public static void main(String[] args) throws Exception {

		OraConnectionInfo ora = new OraConnectionInfo();
		ora.setDbType(DBServerType.ORACLE9i);
		ora.setHost("10.5.6.53");
		ora.setUserName("sys");
		ora.setPassword("sys");
		ora.setSid("orcl");
		ora.setPort("1521");
		ora.setConnectionName("ora");
		ora.setRole("sysdba");

		DBSession dbSession = new DBSession();

		DBMSqlExecuter db = DBMSqlExecuterFactory.createExecuter(ora, dbSession);

		System.out.println(System.currentTimeMillis());
		DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
		dbMetaInfo.setSchemaName("SCOTT");
		dbMetaInfo.setTableName("BONUS");
		List list = db.getAutoIncrementColumns(dbMetaInfo);

		/*
		 * DBMDataResult d = db.getColumns(null, "SCOTT", "DEPT", null);
		 * 
		 * List ll = d.getColumns(); List l2 = d.getData();
		 * 
		 * for (int i = 0; i < ll.size(); i++) { System.out.println(ll.get(i)); }
		 * 
		 * for (int i = 0; i < l2.size(); i++) { Map m = (Map) l2.get(i); for
		 * (int j = 0; j < ll.size(); j++) {
		 * System.out.println(m.get(ll.get(j))); } System.out.println(""); }
		 * 
		 * System.out.println(System.currentTimeMillis());
		 */

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

		/*
		 * ResultSetMetaData rsmt = rs.getMetaData(); int is =
		 * rsmt.getColumnCount(); System.out.println(is);
		 */

		// for(int i = 0; i < is; i++) {
		// System.out.println(rsmt.getColumnName(i)); }
		DBMConnectionManager.closeConnection(ora);

		System.out.println("hello, world!");

	}
}
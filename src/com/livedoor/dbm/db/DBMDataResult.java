/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.db;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
/*
 * 創建期日 2006-09-16
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.livedoor.dbm.util.StringUtil;

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
public class DBMDataResult {

	/*
	 * Column Name for Result
	 */
	private List listCol;

	/*
	 * Data for Result
	 */
	private List listData;

	/**
	 * [功 能] 获取查询结果数据
	 * <p>
	 * [说 明] 获取查询结果数据
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @return List 查询结果集
	 *         <p>
	 */
	public List getData() {
		return listData;
	}

	/**
	 * [功 能] 获取查询结果列信息
	 * <p>
	 * [说 明] 获取查询结果列信息
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @return List 查询结果列信息
	 *         <p>
	 */
	public List getColumns() {
		return listCol;
	}

	/**
	 * [功 能] 设定查询结果数据
	 * <p>
	 * [说 明] 设定查询结果数据
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param list
	 *            查询结果数据
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	void setData(List list) {
		this.listData = list;
	}

	/**
	 * [功 能] 设定查询结果列信息
	 * <p>
	 * [说 明] 设定查询结果列信息
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param list
	 *            查询结果列信息
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	void setColumns(List list) {
		this.listData = list;
	}

	/**
	 * [功 能] 对查询结果集进行处理
	 * <p>
	 * [说 明] 处理查询结果集，对其进行封装，返回list
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param list
	 *            查询结果列信息
	 * @param iCount
	 *            返回结果数量
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	void dealResultSet(ResultSet rs, int iCount) throws SQLException {
		listCol = new ArrayList();
		listData = new ArrayList();

		ResultSetMetaData rsmd = rs.getMetaData();

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {

			DBColumn dBColumn = new DBColumn();
			dBColumn.setColumnName(rsmd.getColumnName(i));
			dBColumn.setColumnLen(rsmd.getColumnDisplaySize(i));
			dBColumn.setColType(rsmd.getColumnType(i));
			listCol.add(dBColumn);

		}
		int iNum = 0;
		while (rs.next()) {

			if (iCount >= 0 && iNum >= iCount) {
				break;
			}

			Map dataMap = new HashMap();
			Object stTemValue = null;
			// for every row
			for (int i = 1; i <= listCol.size(); i++) {
				// 转换数据类型
				stTemValue = convertData(rs, rsmd.getColumnType(i), i);

				dataMap.put(((DBColumn) listCol.get(i - 1)).getColumnName(), stTemValue);
			}

			listData.add(dataMap);

			iNum++;
		}
		
		clobValue();
	}

	/**
	 * [功 能] 对查询结果集进行处理
	 * <p>
	 * [说 明] 处理查询结果集，对其进行封装，返回list
	 * <p>
	 * [备 考] 无
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param list
	 *            查询结果列信息
	 *            <p>
	 * 
	 * @return 无
	 *         <p>
	 */
	void dealResultSet(ResultSet rs) throws Exception {
		dealResultSet(rs, -1);
	}
	
	private void clobValue() throws SQLException
	{
		List list = new ArrayList();
		
		for(int i = 0; i < listCol.size(); i++)
		{
			DBColumn dbColumn = (DBColumn) listCol.get(i);
			if(dbColumn.getColType() == Types.CLOB)
			{
				list.add(dbColumn.getColumnName());
			}
		}
		
		if(list.size() > 0)
		{
			for(int i = 0; i < listData.size(); i++)
			{
				Map dataMap = (Map)listData.get(i);
				for(int j = 0; j < list.size(); j++)
				{
					Clob clob = (Clob)dataMap.get(list.get(j));
					String retVal = null;
					if(clob != null)
					{
						retVal = StringUtil.clobToString(clob);
						//System.out.println("LLLL::: " + clob.length());
					}
					
					dataMap.put(list.get(j), retVal);
				}
				
			}
		}
	}

	/**
	 * [功 能] 对查询结果数据进行转换
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * [更新日期]
	 * <p>
	 * 
	 * @param rs
	 *            ResultSet
	 * @param dataType
	 *            类型
	 * @param col
	 *            列
	 *            <p>
	 * 
	 * @return Object 转换结果
	 *         <p>
	 */
	private Object convertData(ResultSet rs, int dataType, int col) throws SQLException {
		/*
		 * convert all kinds of data type in ResultSet to string 转换数据类型
		 * 
		 * @param rs result set to convert,ResultSet type @param dataType type
		 * of data,int type @param col column number,int type @return String
		 * @author zhanghan @date 2006/09/26
		 */
		Object retVal = null;
		Integer intObj;
		/*
		 * ARRAY :2003 BIGINT :-5 BINARY :-2 BIT :-7 BLOB :2004 CHAR :1 CLOB
		 * :2005 DATE :91 DECIMAL :3 DISTINCT :2001 DOUBLE :8 FLOAT :6 INTEGER
		 * :4 JAVA_OBJECT :2000 LONGVARBINARY :-4 LONGVARCHAR :-1 NULL :0
		 * NUMERIC :2 OTHER :1111 REAL :7 REF :2006 SMALLINT :5 STRUCT :2002
		 * TIME :92 TIMESTAMP :93 TINYINT :-6 VARBINARY :-3 VARCHAR :12
		 */
		switch (dataType) {
			case Types.BLOB :
				retVal = rs.getObject(col);
				/*if (retVal != null) {
					retVal = StringUtil.blobToString((Blob) retVal);
				}*/
				break;
			case Types.CLOB :
				retVal = rs.getObject(col);
				/*if (retVal != null) {
					retVal = StringUtil.clobToString((Clob) retVal);
				}*/
				break;
			case Types.DATE :
				// java.util.Date date = new java.util.Date();
				/*
				 * java.sql.Date dat = rs.getDate(col); java.sql.Time tim =
				 * rs.getTime(col);
				 * 
				 * System.out.println(dat.toString());
				 * System.out.println(tim.toString());
				 * 
				 * java.util.Date dd = new java.util.Date(dat.getTime() +
				 * Math.abs(tim.getTime()));
				 * 
				 * System.out.println(dd.toString());
				 * 
				 * String date = dat.toString() + " " + tim.toString();
				 */

				java.util.Date date = rs.getTimestamp(col);

				if (date == null) {
					retVal = null;
					break;
				}
				retVal = date;
				break;
			case Types.TIME :
				java.sql.Time time = rs.getTime(col);
				if (time == null) {
					retVal = null;
					break;
				}
				retVal = time;
				break;
			case Types.TIMESTAMP :
				java.sql.Timestamp timestamp = rs.getTimestamp(col);
				if (timestamp == null) {
					retVal = null;
					break;
				}
				retVal = timestamp;
				break;
			case Types.CHAR :
			case Types.VARCHAR :
			case Types.LONGVARCHAR :
				retVal = rs.getString(col);
				break;
			case Types.DECIMAL :
				java.math.BigDecimal numeric = rs.getBigDecimal(col);
				if (numeric == null) {
					retVal = null;
					break;
				}
				retVal = numeric;
				break;
			case Types.BIT :
				boolean bit = rs.getBoolean(col);
				Boolean boolObj = new Boolean(bit);
				retVal = boolObj;
				break;
			case Types.TINYINT :
				byte tinyint = rs.getByte(col);
				intObj = new Integer(tinyint);
				retVal = intObj;
				break;
			/*
			 * case Types.SMALLINT: short smallint = rs.getShort(col); intObj =
			 * new Integer(smallint); if (intObj == null) { retVal = null;
			 * break; } retVal = intObj; break; case Types.INTEGER: int integer =
			 * rs.getInt(col); intObj = new Integer(integer); if (intObj ==
			 * null) { retVal = null; break; } retVal = intObj; break;
			 */
			case Types.BOOLEAN :
				boolean bool = rs.getBoolean(col);
				Boolean bObj = new Boolean(bool);
				if (bObj == null) {
					retVal = null;
					break;
				}
				retVal = bObj;
				break;
			case Types.BIGINT :
				long bigint = rs.getLong(col);
				Long longObj = new Long(bigint);
				if (longObj == null) {
					retVal = null;
					break;
				}
				retVal = longObj;
				break;
			case Types.REAL :
				float real = rs.getFloat(col);
				Float floatObj = new Float(real);
				if (floatObj == null) {
					retVal = null;
					break;
				}
				retVal = floatObj;
				break;
			case Types.FLOAT :
			case Types.DOUBLE :
				double longreal = rs.getDouble(col);
				Double doubleObj = new Double(longreal);
				if (doubleObj == null) {
					retVal = null;
					break;
				}
				retVal = doubleObj;
				break;
			/*
			 * case Types.BINARY: case Types.VARBINARY: case
			 * Types.LONGVARBINARY: byte[] binary = rs.getBytes(col); if(binary ==
			 * null || binary.length == 0) { retVal = ""; } else { retVal = new
			 * String(binary); } break;
			 */
			default :
				retVal = rs.getObject(col);
		}
		return retVal;

	}
}

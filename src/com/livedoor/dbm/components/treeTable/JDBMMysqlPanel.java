/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import com.livedoor.dbm.components.queryanalyzer.TextResultPane;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.DBResultType;
import com.livedoor.dbm.db.DBMSqlResult;
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
public class JDBMMysqlPanel extends JDBMTxtPanel {

	private MysqlDBHandler dBHandler;

	private DBMSqlResult[] sqlResult;

	/**
	 * [功 能] 根据给定的参数，生成查询计划结果Panel，并返回
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * @param conInfo
	 *            ConnectionInfo
	 * @param dBSession
	 *            DBSession
	 * @param stSql
	 *            String sql statement
	 *            <p>
	 */
	public JDBMMysqlPanel(ConnectionInfo connectionInfo, DBSession dBSession,
			String stSql) {
		super();

		dBHandler = new MysqlDBHandler(connectionInfo, dBSession);
		try {
			
			sqlResult = dBHandler.executePlan(stSql);
			
		} catch (Exception e) {
			
			String stMessage = e.toString();
			sqlResult = new DBMSqlResult[1];
			sqlResult[0] = new DBMSqlResult();
			sqlResult[0].setType(DBResultType.ERROR);
			sqlResult[0].setMessage(stMessage);
		}

		initialize();
	}

	/**
	 * [功 能] 初始化
	 * <p>
	 * [说 明] 初始化当前组件
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
	 * @see com.livedoor.dbm.components.treeTable.PanelInterface#initialize()	 
	 */
	public void initialize() {
		setLayout(new BorderLayout());
		this.add(new JScrollPane(getJTextPane()));
	}

	/**
	 * [功 能] 返回javax.swing.JTextPane
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @return javax.swing.JTextPane
	 *         <p>
	 */
	private TextResultPane getJTextPane() {
		if (sqlPane == null) {
			sqlPane = new TextResultPane();

			sqlPane.displayResult(sqlResult[0]);
		}
		return sqlPane;
	}

}

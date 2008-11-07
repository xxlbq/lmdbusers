/*
 * 创建时间 2006-09-26
 */
package com.livedoor.dbm.components.treeTable;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

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
public class JDBMDb2Panel extends JDBMTabPanel {

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
		this.setLayout(new BorderLayout());
	}

	/**
	 * [功 能] 根据给定的参数，生成查询计划结果Panel，并返回
	 * <p>
	 * [作成日期] 2006/09/26
	 * <p>
	 * 
	 * @param conInfo
	 *            ConnectionInfo
	 * @param dBSession
	 *            DBSession
	 * @param stSql
	 *            String sql statement
	 *            <p>
	 */
	public JDBMDb2Panel(ConnectionInfo connectionInfo, DBSession dBSession, String stSql) {
		super();
		initialize();

		Db2DBHandler dBHandler = new Db2DBHandler(connectionInfo, dBSession);

		try {

			dBHandler.createTables();

			List list = dBHandler.executePlan(stSql);

			treeTable = new JTreeTable(new Db2DataModel(list));

			for (int i = 0; i < Db2DataModel.cNames.length; i++) {
				TableColumn tabCol = null;
				if (i == 0) {
					tabCol = treeTable.getColumn(Db2DataModel.cNames[i]);
					tabCol.setPreferredWidth(250);
					continue;
				}

				if (i == 2) {
					tabCol = treeTable.getColumn(Db2DataModel.cNames[i]);
					tabCol.setPreferredWidth(120);
					continue;
				}

				if (Db2DataModel.cNames[i].length() >= 15) {
					tabCol = treeTable.getColumn(Db2DataModel.cNames[i]);
					tabCol.setPreferredWidth(135);
				} else {
					tabCol = treeTable.getColumn(Db2DataModel.cNames[i]);
					tabCol.setPreferredWidth(100);
				}
			}

			// treeTable.setBackground(Color.yellow);

			// treeTable.getTableHeader().setFont(new Font("宋体", Font.BOLD,
			// 16));
			// treeTable.setFont(new Font("宋体", 0, 16));

			/*
			 * Dimension d = treeTable.getIntercellSpacing(); // d.width == 1,
			 * d.height == 1 // Add 5 spaces to the left and right sides of a
			 * cell. // Add 2 spaces to the top and bottom sides of a cell. int
			 * gapWidth = 1; int gapHeight = 4;
			 * treeTable.setIntercellSpacing(new Dimension(gapWidth, 0));
			 */

			treeTable.setShowHorizontalLines(true);
			treeTable.setShowVerticalLines(true);
			//treeTable.setRowSelectionAllowed(true);
			//treeTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

			TableColumn tc = treeTable.getColumn(Db2DataModel.cNames[0]);
			tc.setPreferredWidth(200);// 设置宽度

			/*
			 * TableColumnModel columnModel = treeTable.getColumnModel();
			 * TableColumn tc1 = columnModel.getColumn(1);
			 * 
			 * tc1.setWidth(0); tc1.setPreferredWidth(0);//设置宽度
			 * tc1.setMaxWidth(0); tc1.setMinWidth(0);
			 */

			/*
			 * TableColumn tc1 = treeTable.getColumn(DataModel.cNames[1]);
			 * tc1.setPreferredWidth(0);//设置宽度 tc1.setMaxWidth(0);
			 * tc1.setMinWidth(0);
			 * 
			 * tc1.sizeWidthToFit();
			 */

			treeTable.setAutoResizeMode(treeTable.AUTO_RESIZE_OFF);

			JScrollPane jScrollPane = new JScrollPane();

			jScrollPane.setViewportView(treeTable);

			this.add(jScrollPane, BorderLayout.CENTER);

		} catch (Exception e) {
			String stMessage = e.toString();
			DBMSqlResult[] sqlResult = new DBMSqlResult[1];
			sqlResult[0] = new DBMSqlResult();
			sqlResult[0].setType(DBResultType.ERROR);
			sqlResult[0].setMessage(stMessage);

			TextResultPane sqlPane = new TextResultPane();
			sqlPane.displayResult(sqlResult[0]);

			this.add(new JScrollPane(sqlPane));
		}
	}
}
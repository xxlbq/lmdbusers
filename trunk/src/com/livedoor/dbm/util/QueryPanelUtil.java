package com.livedoor.dbm.util;

import com.livedoor.dbm.action.IActionHandler;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.queryanalyzer.QueryAnalyzerPanel;
import com.livedoor.dbm.components.queryanalyzer.QueryBasePanel;
import com.livedoor.dbm.connection.ConnectionInfo;


/**
 * <p>Description:    </p>
 * Copyright: Copyright (c) 2006 
 * Company: 英極軟件開發（大連）有限公司
 * @author <a href="mailto:lijian@livedoor.cn">Jian Li </a>
 * @version 1.0
 */
public class QueryPanelUtil {
	/**
	 * 创建一个新的QueryAnaylizerPanel对象
	 * @param dbmFrame
	 * @param connInfo
	 * @param actionHandler
	 * @return
	 */
	public static QueryAnalyzerPanel createNewQueryPanel(DBMFrame dbmFrame,ConnectionInfo connInfo,IActionHandler actionHandler){
		QueryAnalyzerPanel queryAnaylizerPanel = new QueryAnalyzerPanel(dbmFrame, connInfo, actionHandler);
		dbmFrame.addQueryPanel(queryAnaylizerPanel);
		return queryAnaylizerPanel;
	}
	/**
	 * 从当前窗口中得到一个QueryAnaylizerPanel对象,如果当前没有可使用的对象，则新建一个后返回
	 * @param dbmFrame 当前Frame对象
	 * @param connInfo 数据库连接信息
	 * @param actionHandler 处理ActionListener
	 * @return
	 */
	public static QueryAnalyzerPanel getQueryAnaylizerPanel(DBMFrame dbmFrame,ConnectionInfo connInfo,IActionHandler actionHandler){
		return getQueryAnaylizerPanel(dbmFrame,connInfo,actionHandler,false);
	}
	/**
	 * 从当前窗口中得到一个QueryAnaylizerPanel对象
	 * @param dbmFrame 当前Frame对象
	 * @param connInfo 数据库连接信息
	 * @param actionHandler 处理ActionListener
	 * @param isCreate 如果为true,则新建一个QueryAnaylizerPanel对象返回；如果为false，则用当前使用的QueryAnaylizerPanel对象,
	 * 如果还没有可使用的QueryAnaylizerPanel对象，则创建新的对象后返回
	 * @return
	 */
	public static QueryAnalyzerPanel getQueryAnaylizerPanel(DBMFrame dbmFrame,ConnectionInfo connInfo,IActionHandler actionHandler,boolean isCreate){
		QueryAnalyzerPanel queryAnaylizerPanel = null;
		if(isCreate){
			//创建新的对象后返回
			queryAnaylizerPanel = createNewQueryPanel(dbmFrame,connInfo, actionHandler);
			
		}else{
			QueryBasePanel queryBasePanle = dbmFrame.getQueryPanel();
			//如果为空，表示当前还没有打开一个QueryAnaylizerPanel
			if(queryBasePanle == null){
				//创建一个新的QueryAnaylizerPanel返回
				queryAnaylizerPanel = createNewQueryPanel(dbmFrame,connInfo, actionHandler);
			}else{
				queryAnaylizerPanel = (QueryAnalyzerPanel)queryBasePanle;
			}
		}
		return queryAnaylizerPanel;
	}

}

/**
 * $Id: PasteAction.java,v 1.2 2006/12/04 02:55:13 lijc Exp $
 * 查询分析器工具栏按钮对应
 */
package com.livedoor.dbm.action.queryanalyzer;

import java.awt.event.ActionEvent;

import com.livedoor.dbm.action.DBMBaseAction;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.queryanalyzer.QueryAnalyzerPanel;

/**
 * <p>Title: 粘贴</p> 
 * <p>Description:
 * 		把系统区的文本放在查询分析器当前位置,如果查询分析器
 * 		有选择文本,系统区文本替换选择文本
 * </p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 *  
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class PasteAction extends DBMBaseAction {

	private static final String ACTION_NAME = "PASTE";

	public PasteAction() {
		super(ACTION_NAME);
	}

	@Override
	public void processAction(DBMFrame frame, ActionEvent actionEvent) {
		QueryAnalyzerPanel queryPanel = (QueryAnalyzerPanel) frame.getQueryPanel();
		if(queryPanel == null){
			DBMMessageDialog.showErrorMessageDialog("QUERY_ANALYZER_NOT_OPEN");
			return;
		}
		queryPanel.paste();
	}

}

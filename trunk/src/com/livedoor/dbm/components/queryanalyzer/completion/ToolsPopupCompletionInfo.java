/**
 * $Id: ToolsPopupCompletionInfo.java,v 1.1 2006/10/17 01:10:35 lijian Exp $
 * SQL编辑器帮助
 */
package  com.livedoor.dbm.components.queryanalyzer.completion;

import javax.swing.*;

/**
 * <p>Title: 弹出菜单中选中的字符串对象</p> 
 * <p>Description: 继承抽象类CompletionInfo 重要是获得当前选中的字符串，以及要比较的字符串。以便比较</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */

public class ToolsPopupCompletionInfo extends CompletionInfo {
	private String _selectionString;

	private Action _action;

	private int _maxCandidateSelectionStringName;

	private String _description;

	public ToolsPopupCompletionInfo(String selectionString, Action action) {
		_selectionString = selectionString;
		_action = action;
	}
	
	public ToolsPopupCompletionInfo(String selectionString){
		_selectionString = selectionString;
		
	}
	

	public String getCompareString() {
		return _selectionString;
	}

	public String getCompletionString() {
		return "";
	}

	public Action getAction() {
		return _action;
	}

	public String toString() {
		return _selectionString;
		//+ getDist() + getDescription();
	}


	public void setMaxCandidateSelectionStringName(
			int maxCandidateSelectionStringName) {
		_maxCandidateSelectionStringName = maxCandidateSelectionStringName;
	}

	public String getSelectionString() {
		return _selectionString;
	}
}

/**
 * $Id: ToolsPopupCompletorModel.java,v 1.1 2006/10/17 01:10:35 lijian Exp $
 * SQL编辑器帮助
 */
package  com.livedoor.dbm.components.queryanalyzer.completion;

import javax.swing.*;
import java.util.Collections;
import java.util.Vector;

/**
 * <p>Title: 弹出数据模型</p> 
 * <p>Description: 弹出数据模型 </p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class ToolsPopupCompletorModel implements ICompletorModel {
	
	Vector _toolsPopupCompletionInfos = new Vector();

	public CompletionCandidates getCompletionCandidates(
			String selectionStringBegin) {
		
		Vector ret = new Vector();
		int maxNameLen = 0;


		for (int i = 0; i < _toolsPopupCompletionInfos.size(); i++) {

			ToolsPopupCompletionInfo tpci = (ToolsPopupCompletionInfo) _toolsPopupCompletionInfos
					.get(i);

			if (tpci.getSelectionString().toUpperCase().startsWith(selectionStringBegin.toUpperCase())) {
				ret.add(tpci);
				maxNameLen = Math.max(maxNameLen, tpci.getSelectionString()
						.length());
			}
		}

		ToolsPopupCompletionInfo[] candidates = (ToolsPopupCompletionInfo[]) ret
				.toArray(new ToolsPopupCompletionInfo[ret.size()]);

		for (int i = 0; i < candidates.length; i++) {
			candidates[i].setMaxCandidateSelectionStringName(maxNameLen);
		}

		return new CompletionCandidates(candidates);
	}

	public void addAction(String selectionString, Action action) {
		_toolsPopupCompletionInfos.add(new ToolsPopupCompletionInfo(
				selectionString, action));

		Collections.sort(_toolsPopupCompletionInfos);

	}

	public void addAction(String selectionString) {
		_toolsPopupCompletionInfos.add(new ToolsPopupCompletionInfo(
				selectionString));

		Collections.sort(_toolsPopupCompletionInfos);

	}
	public void clear(){
		_toolsPopupCompletionInfos.clear();
	}
}

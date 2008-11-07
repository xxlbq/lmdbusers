/**
 * $Id: CompletionCandidates.java,v 1.1 2006/10/17 01:10:35 lijian Exp $
 * SQL编辑器帮助
 */
package com.livedoor.dbm.components.queryanalyzer.completion;

/**
 * <p>Title:弹出菜单中的条目管理类</p> 
 * <p>Description: 主要获得Popup中所有的项目的对象集合</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class CompletionCandidates {
	
	private CompletionInfo[] _candidates;

	private int _replacementStart;

	private String _stringToReplace;

	public CompletionCandidates(CompletionInfo[] candidates,
			int replacementStart, String stringToReplace) {
		_candidates = candidates;
		_replacementStart = replacementStart;
		_stringToReplace = stringToReplace;
	}

	/**
	 * This ctor can be used when the completion uses its own filter text field.
	 * 
	 * @param candidates
	 */
	public CompletionCandidates(CompletionInfo[] candidates) {
		_candidates = candidates;
	}

	public CompletionInfo[] getCandidates() {
		return _candidates;
	}

	public int getReplacementStart() {
		return _replacementStart;
	}

	public String getStringToReplace() {
		return _stringToReplace;
	}
}

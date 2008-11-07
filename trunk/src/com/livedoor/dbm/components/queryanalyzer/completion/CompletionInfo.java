/**
 * $Id: CompletionInfo.java,v 1.1 2006/10/17 01:10:35 lijian Exp $
 * SQL编辑器帮助
 */
package com.livedoor.dbm.components.queryanalyzer.completion;

/**
 * <p>Title: 字符串对象比较类</p> 
 * <p>Description: 主要实现Popup中填写的字符串和所需要的Popup列表	 中的项目是否有匹配的比较功能</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public abstract class CompletionInfo implements Comparable {
	private String _upperCaseCompletionString;

	public abstract String getCompareString();

	public String getCompletionString() {
		return getCompareString();
	}

	public int compareTo(Object obj) {
		CompletionInfo other = (CompletionInfo) obj;

		if (null == _upperCaseCompletionString) {
			_upperCaseCompletionString = getCompareString().toUpperCase();
		}

		if (null == other._upperCaseCompletionString) {
			other._upperCaseCompletionString = other.getCompareString()
					.toUpperCase();
		}

		return _upperCaseCompletionString
				.compareTo(other._upperCaseCompletionString);
	}


	/**
	 * Default implementation
	 */
	public boolean hasColumns() {
		return false;
	}

}

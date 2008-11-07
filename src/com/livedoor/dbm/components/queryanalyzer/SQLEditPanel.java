/**
 * $Id: SQLEditPanel.java,v 1.1 2006/10/17 01:10:35 lijian Exp $
 * SQL编辑器
 */
package com.livedoor.dbm.components.queryanalyzer;

import com.livedoor.dbm.components.queryanalyzer.syntax.OsterTextControl;

/**
 * <p>Title: SQL编辑器</p> 
 * <p>Description: 提供编辑器基本功能</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public abstract class SQLEditPanel extends QueryBasePanel {
	
	/*
	 * 编辑器提供类
	 */
	protected OsterTextControl sqlEditTextPane; 
	
	/**
	 * 剪切
	 */
	public void cut(){
		sqlEditTextPane.cut();
	}

	/**
	 * 拷贝
	 */
	public void copy(){
		sqlEditTextPane.copy();
	}

	/**
	 * 粘贴 
	 */
	public void paste(){
		sqlEditTextPane.paste();
	}

	/**
	 * 撤销
	 */
	public void undo() {
		sqlEditTextPane.undo();
	}
	
	/**
	 * 恢复
	 */
	public void redo() {
		sqlEditTextPane.redo();
	}
	
	/**
	 * 转换为小写
	 */
	public void toLowerCase() {
		String selectedText = sqlEditTextPane.getSelectedText();
		if(selectedText!=null) {
			sqlEditTextPane.replaceSelection(selectedText.toLowerCase());
		}
	}
	
	/**
	 * 转换为大写
	 */
	public void toUpperCase() {
		String selectedText = sqlEditTextPane.getSelectedText();
		if(selectedText!=null) {
			sqlEditTextPane.replaceSelection(selectedText.toUpperCase());
		}
	}
}

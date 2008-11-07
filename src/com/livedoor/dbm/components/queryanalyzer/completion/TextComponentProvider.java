/**
 * $Id: TextComponentProvider.java,v 1.1 2006/10/17 01:10:35 lijian Exp $
 * SQL编辑器帮助
 */
package com.livedoor.dbm.components.queryanalyzer.completion;

import javax.swing.text.JTextComponent;
import javax.swing.*;

/**
 * <p>Title:文本组件提供类</p> 
 * <p>Description:主要获得当前查询分析器文本对象，以及Popup中Text对象</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class TextComponentProvider {
	private JTextComponent _txtEditor;

	private JTextComponent _txtFilter;

	public TextComponentProvider(JTextComponent txtEditor,
			boolean useOwnFilterTextField) {
		_txtEditor = txtEditor;

		if (useOwnFilterTextField) {
			_txtFilter = new JTextField();
//			_txtFilter.setBorder(new EmptyBorder(0,0,0,0));
			_txtFilter.setFont(_txtEditor.getFont());
		}
	}

	public JTextComponent getEditor() {
		return _txtEditor;
	}

	public JTextComponent getFilter() {
		if (null != _txtFilter) {
			return _txtFilter;
		} else {
			return _txtEditor;
		}
	}

	public boolean editorEqualsFilter() {
		return null == _txtFilter;
	}
}

/**
 * $Id: OsterTextControl.java,v 1.9 2006/12/04 02:59:39 lijc Exp $
 * SQL编辑器
 */
package com.livedoor.dbm.components.queryanalyzer.syntax;

import java.awt.Color;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

import com.Ostermiller.Syntax.Lexer.Lexer;
import com.Ostermiller.Syntax.Lexer.SQLLexer;
import com.Ostermiller.Syntax.Lexer.Token;
import com.livedoor.dbm.components.queryanalyzer.syntax.provider.ErrorInfo;
import com.livedoor.dbm.components.queryanalyzer.syntax.provider.FontInfo;
import com.livedoor.dbm.components.queryanalyzer.syntax.provider.SQLTokenListener;
import com.livedoor.dbm.ldeditor.SQLCompletionQuery;

/**
 * <p> Title: SQL编辑器 </p>
 * <p> Description: SQL编辑器 </p>
 * <p> Copyright: Copyright (c) 2006 </p>
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class OsterTextControl extends JTextPane {

	private static final long serialVersionUID = 1L;

	private CharacterKeyWords characterKeyWords = null;

	/**
	 * 文档同步锁对象
	 */
	private Object doclock = new Object();

	/**
	 * 文档对象,编辑器的模型
	 */
	private HighLightedDocument document;

	/**
	 * A reader wrapped around the document so that the document can be fed into
	 * the lexer.
	 */
	private DocumentReader documentReader;

	/**
	 * 解析器,用于决定单词颜色
	 */
	private Lexer syntaxLexer;

	/**
	 * 着色器
	 */
	private Colorer colorer;

	/**
	 * 着色用文本样式
	 */
	private Hashtable<String, SimpleAttributeSet> styles = new Hashtable<String, SimpleAttributeSet>();

	/**
	 * 解析器自定义参数
	 */
	private final SyntaxPreferences _syntaxPrefs;

	private Vector<SQLTokenListener> _sqlTokenListeners = new Vector<SQLTokenListener>();

	private Vector<ErrorInfo> _currentErrorInfos = new Vector<ErrorInfo>();

	/**
	 * 恢复/重做管理
	 */
	private UndoManager undoManager = new DBMUndoManager();

	public OsterTextControl(SyntaxPreferences prefs, CharacterKeyWords characterKeyWords) {
		super();
		_syntaxPrefs = prefs;
		this.characterKeyWords = characterKeyWords;

		// 构造文档模型
		document = new HighLightedDocument();
		setDocument(document);

		// 构造文档阅读器
		documentReader = new DocumentReader(document);

		// 启动着色器线程
		colorer = new Colorer();
		colorer.start();

		// 初始化文本样式
		initStyles();

		// 设置初始文本及初始文本样式
		initDocument();

		//
		new KeyManager(this);

		// 设置恢复/重做管理器
		document.addUndoableEditListener(undoManager);
	}

	public void endColorerThread() {
		colorer.endThread();
	}

	/**
	 * 对模型内所有文本着色
	 */
	public void colorAll() {
		color(0, document.getLength(), false, null);
	}

	/**
	 * 着色部分文本,实际着色在线程中处理
	 * 
	 * @param position
	 *            着色开始位置
	 * @param adjustment
	 *            从着色开始位置,插入或删除字符长度
	 */
	public void color(int position, int adjustment, boolean fireTableOrViewFoundEvent, String change) {
		colorer.color(position, adjustment, fireTableOrViewFoundEvent, change);
	}

	/**
	 * 获取文本样式
	 * 
	 * @param styleName
	 *            文本样式名
	 * @return the style 文本样式
	 */
	private SimpleAttributeSet getMyStyle(String styleName) {
		SimpleAttributeSet attribute = (SimpleAttributeSet) styles.get(styleName);
		return attribute;
	}

	private Token getNextToken() throws IOException {
		return syntaxLexer.getNextToken();
	}

	private void applyStyle(SimpleAttributeSet attribs, SyntaxStyle style, FontInfo fi) {
		StyleConstants.setFontFamily(attribs, fi.getFamily());
		StyleConstants.setFontSize(attribs, fi.getSize());
		StyleConstants.setBackground(attribs, new Color(style.getBackgroundRGB()));
		StyleConstants.setForeground(attribs, new Color(style.getTextRGB()));
		StyleConstants.setBold(attribs, style.isBold());
		StyleConstants.setItalic(attribs, style.isItalic());
	}

	/**
	 * 设置初始文本样式
	 */
	private void initDocument() {
		syntaxLexer = new SQLLexer(documentReader);
		try {
			document.insertString(document.getLength(), "", getMyStyle("text"));
		} catch (BadLocationException ex) {
		}
	}

	/**
	 * 初始化文本样式
	 */
	private void initStyles() {
		final FontInfo fi = new FontInfo();

		SyntaxStyle style;
		SimpleAttributeSet attribs;

		attribs = new SimpleAttributeSet();
		StyleConstants.setFontFamily(attribs, fi.getFamily());
		StyleConstants.setFontSize(attribs, fi.getSize());
		StyleConstants.setBackground(attribs, Color.white);
		StyleConstants.setForeground(attribs, Color.black);
		StyleConstants.setBold(attribs, true);
		StyleConstants.setItalic(attribs, false);
		styles.put("text", attribs);

		style = _syntaxPrefs.getColumnStyle();
		attribs = new SimpleAttributeSet();
		applyStyle(attribs, style, fi);
		styles.put(IConstants.IStyleNames.COLUMN, attribs);

		style = _syntaxPrefs.getCommentStyle();
		attribs = new SimpleAttributeSet();
		applyStyle(attribs, style, fi);
		styles.put(IConstants.IStyleNames.COMMENT, attribs);

		style = _syntaxPrefs.getDataTypeStyle();
		attribs = new SimpleAttributeSet();
		applyStyle(attribs, style, fi);
		styles.put(IConstants.IStyleNames.DATA_TYPE, attribs);

		style = _syntaxPrefs.getErrorStyle();
		attribs = new SimpleAttributeSet();
		applyStyle(attribs, style, fi);
		styles.put(IConstants.IStyleNames.ERROR, attribs);

		style = _syntaxPrefs.getFunctionStyle();
		attribs = new SimpleAttributeSet();
		applyStyle(attribs, style, fi);
		styles.put(IConstants.IStyleNames.FUNCTION, attribs);

		style = _syntaxPrefs.getIdentifierStyle();
		attribs = new SimpleAttributeSet();
		applyStyle(attribs, style, fi);
		styles.put(IConstants.IStyleNames.IDENTIFIER, attribs);

		style = _syntaxPrefs.getLiteralStyle();
		attribs = new SimpleAttributeSet();
		applyStyle(attribs, style, fi);
		styles.put(IConstants.IStyleNames.LITERAL, attribs);

		style = _syntaxPrefs.getOperatorStyle();
		attribs = new SimpleAttributeSet();
		applyStyle(attribs, style, fi);
		styles.put(IConstants.IStyleNames.OPERATOR, attribs);

		style = _syntaxPrefs.getReservedWordStyle();
		attribs = new SimpleAttributeSet();
		applyStyle(attribs, style, fi);
		styles.put(IConstants.IStyleNames.RESERVED_WORD, attribs);

		style = _syntaxPrefs.getSeparatorStyle();
		attribs = new SimpleAttributeSet();
		applyStyle(attribs, style, fi);
		styles.put(IConstants.IStyleNames.SEPARATOR, attribs);

		style = _syntaxPrefs.getTableStyle();
		attribs = new SimpleAttributeSet();
		applyStyle(attribs, style, fi);
		styles.put(IConstants.IStyleNames.TABLE, attribs);

		style = _syntaxPrefs.getWhiteSpaceStyle();
		attribs = new SimpleAttributeSet();
		applyStyle(attribs, style, fi);
		styles.put(IConstants.IStyleNames.WHITESPACE, attribs);
	}

    /**
     * 着色线程
     * @author lijicheng
     */
	private class Colorer extends Thread {

		private boolean _endThread;

		/**
		 * 注释及"'"之间文字开始结束位置
		 * 队列中每项为一个一维整型数组,数组记录注释的开始和结束位置
		 */
		private Vector<int[]> _currentLiteralAndCommentIntervals = new Vector<int[]>();

		/**
		 * 记录列名
		 */
		private Hashtable<String, String> _knownTables = new Hashtable<String, String>();

		/**
		 * 
		 * 着色事件
		 * <p>
		 * 对引起着色的简单封装,以便能放置于Vector中
		 */
		private class RecolorEvent {

			private int position;

			private int adjustment;

			private boolean fireTableOrViewFoundEvent;

			private String change;

			public RecolorEvent(int position, int adjustment, boolean fireTableOrViewFoundEvent, String change) {
				this.position = position;
				this.adjustment = adjustment;
				this.fireTableOrViewFoundEvent = fireTableOrViewFoundEvent;
				this.change = change;
			}
		}

		/**
		 * 着色事件队列
		 */
		private volatile Vector<RecolorEvent> recolorEventQueue = new Vector<RecolorEvent>();

		private volatile boolean asleep = false;

		/**
		 * 着色事件队列同步锁
		 * <p>
		 * Vector已经是同步对象,为什么还要加同步锁?
		 */
		private Object lock = new Object();

		/**
		 * 添加待着色事件到队列中
		 */
		public void color(int position, int adjustment, boolean fireTableOrViewFoundEvent, String change) {
			synchronized (lock) {
				recolorEventQueue.add(new RecolorEvent(position, adjustment, fireTableOrViewFoundEvent, change));
				if (asleep) {
					this.interrupt();
				}
			}
		}

		/**
		 * 终止着色线程
		 */
		public void endThread() {
			_endThread = true;
			if (asleep) {
				this.interrupt();
			}
		}

		/**
		 * The colorer runs forever and may sleep for long periods of time. It
		 * should be interrupted every time there is something for it to do.
		 */
		public void run() {
			int colorStartPos = -1;
			int colorLen = 0;
			boolean fireTableOrViewFoundEvent = true;

			boolean tryAgain = false;
			for (;;) {
				synchronized (lock) {
					if (recolorEventQueue.size() > 0) {
						RecolorEvent re = (RecolorEvent) (recolorEventQueue.elementAt(0));
						recolorEventQueue.removeElementAt(0);

						if (null != re.change) {
							// Only if the text did really changed (null !=
							// re.change) Intervals
							// must be adopted.
							// If the text did not change there is nothing to
							// adopt.
							adoptIntervalsToAdjustment(re);
						}

						colorStartPos = getColorStartPos(re);
						colorLen = getColorLen(colorStartPos, re);

						if (null != re.change) {
							if (-1 != re.change.indexOf('\'') || -1 != re.change.indexOf('/') || -1 != re.change.indexOf('*')
									|| -1 != re.change.indexOf('-') || null != getInvolvedLiteralOrCommentInterval(re.position)) {
								reinitLiteralAndCommentIntervals();

								int colorStartPos2 = getColorStartPos(re);
								int colorLen2 = getColorLen(colorStartPos, re);

								int newStartPos = Math.min(colorStartPos, colorStartPos2);
								int newEndPos = Math.max(colorStartPos2 + colorLen2, colorStartPos + colorLen);

								colorStartPos = newStartPos;
								colorLen = newEndPos - newStartPos;
							}
						}

						fireTableOrViewFoundEvent = re.fireTableOrViewFoundEvent;
					} else {
						tryAgain = false;
						colorStartPos = -1;
						colorLen = 0;
						fireTableOrViewFoundEvent = false;

					}
				}

				if (colorStartPos != -1) {
					try {
						final CharacterCheck si = new CharacterCheck(characterKeyWords);
						Token t;
						synchronized (doclock) {

							// 为提高效率,不用每次都实例化
							syntaxLexer.reset(documentReader, 0, colorStartPos, 0);

							// 定位到文档指定位置
							documentReader.seek(colorStartPos);

							// 取记号
							t = getNextToken();
						}

						SimpleAttributeSet errStyle = getMyStyle(IConstants.IStyleNames.ERROR);
						ErrorInfo[] errInfoClone = (ErrorInfo[]) _currentErrorInfos.toArray(new ErrorInfo[0]);
						while (t != null && t.getCharEnd() <= colorStartPos + colorLen + 1) {

							// 实际着色代码
							synchronized (doclock) {

								if (t.getCharEnd() <= document.getLength()) {

									String type = t.getDescription();
									if (type.equals(IConstants.IStyleNames.IDENTIFIER)) {
										final String data = t.getContents();

										// 表名
										// if (si.isTable(data)) {
                                        if(isTable(data)) {
											type = IConstants.IStyleNames.TABLE;
											if (fireTableOrViewFoundEvent) {
												fireTableOrViewFound(t.getContents());
											}

											String upperCaseTableName = data.toUpperCase();
											if (false == _knownTables.contains(upperCaseTableName)) {
												_knownTables.put(upperCaseTableName, upperCaseTableName);
												recolorColumns(upperCaseTableName);
											}
										}

										// 列名
                                        // else if (si.isColumn(data)) {
                                        else if(isColumn(data)) {
											type = IConstants.IStyleNames.COLUMN;
										}

										// 数据类型
										else if (si.isDataType(data)) {
											type = IConstants.IStyleNames.DATA_TYPE;
										}

										// 关键字
										else if (si.isKeyword(data)) {
											type = IConstants.IStyleNames.RESERVED_WORD;
										}
									}

									int begin = t.getCharBegin();
									int len = t.getCharEnd() - t.getCharBegin();
									SimpleAttributeSet myStyle = null;
									for (int i = 0; i < errInfoClone.length; i++) {
										if (isBetween(errInfoClone[i].beginPos, errInfoClone[i].endPos, begin)
												&& isBetween(errInfoClone[i].beginPos, errInfoClone[i].endPos, (begin + len - 1))) {
											myStyle = errStyle;
										}
									}

									if (null == myStyle) {
										myStyle = getMyStyle(type);
									}

									// 语法着色
									setCharacterAttributes(begin, len, myStyle, true);
								}
							}

							// 取下一个记号
							synchronized (doclock) {
								t = getNextToken();
							}
						}
					} catch (IOException x) {
					}

					tryAgain = true;
				}

				if (_endThread) {
					break;
				}

				asleep = true;
				if (!tryAgain) {
					try {
						sleep(0xffffff);
					} catch (InterruptedException x) {
					}

				}
				if (_endThread) {
					break;
				}

				asleep = false;
			}
		}

        private boolean isTable(String data) {
            Map<String, List<String>> schemaCache = (Map) document.getProperty(SQLCompletionQuery.SCHEMACACHE);
            if (schemaCache != null) {
                for (List<String> tables : schemaCache.values()) {
                    if (tables.contains(data))
                        return true;
                }
            }

            return false;
        }

        private boolean isColumn(String data) {
            Map<String, List<String>> tableCache = (Map)document.getProperty(SQLCompletionQuery.TABLECACHE);
            if(tableCache!=null) {
                for (List<String> columns : tableCache.values()) {
                    if (columns.contains(data))
                        return true;
                }
            }
            return false;
        }

        private void adoptIntervalsToAdjustment(RecolorEvent re) {
			for (int i = 0; i < _currentLiteralAndCommentIntervals.size(); i++) {
				int[] interval = (int[]) _currentLiteralAndCommentIntervals.elementAt(i);

				if (re.position < interval[0]) {
					interval[0] += re.adjustment;
				}
				if (re.position < interval[1]) {
					interval[1] += re.adjustment;
				}
			}
		}

		/**
		 * 着色列名
		 * 
		 * @param tableName
		 */
		private void recolorColumns(String tableName) {
			String text = getText().toUpperCase();

			// TODO: String cols = getCollumnsByTable(tableName);
			Set<String> cols = characterKeyWords.COLUMN_MAP.keySet();

			for (String column : cols) {
				String upperCaseColName = column.toUpperCase();

				int fromIndex = 0;
				for (;;) {
					fromIndex = text.indexOf(upperCaseColName, fromIndex);

					if (-1 == fromIndex) {
						break;
					}

					color(fromIndex, upperCaseColName.length(), false, null);
					++fromIndex;
				}
			}
		}

		/**
		 * 重算注释及"'"之间文字开始结束位置
		 */
		private void reinitLiteralAndCommentIntervals() {
			try {
				_currentLiteralAndCommentIntervals.clear();

				Document doc = getDocument();
				int docLen = doc.getLength();

				int[] curInterval = null;
				boolean inMultiLineComment = false;
				boolean inSinglLineComment = false;
				boolean inLiteral = false;
				for (int i = 0; i < docLen; ++i) {
					if (i < docLen + 1 && "/*".equals(doc.getText(i, 2)) && false == inMultiLineComment && false == inSinglLineComment
							&& false == inLiteral) {
						curInterval = new int[2];
						curInterval[0] = i;
						curInterval[1] = docLen;
						inMultiLineComment = true;
						++i;
						continue;
					}

					if (i < docLen + 1 && "--".equals(doc.getText(i, 2)) && false == inMultiLineComment && false == inSinglLineComment
							&& false == inLiteral) {
						curInterval = new int[2];
						curInterval[0] = i;
						curInterval[1] = docLen;
						inSinglLineComment = true;
						++i;
						continue;
					}

					if ('\'' == doc.getText(i, 1).charAt(0) && false == inMultiLineComment && false == inSinglLineComment
							&& false == inLiteral) {
						curInterval = new int[2];
						curInterval[0] = i;
						curInterval[1] = docLen;
						inLiteral = true;
						continue;
					}

					if (i < docLen + 1 && "*/".equals(doc.getText(i, 2)) && inMultiLineComment) {
						curInterval[1] = i + 1;
						_currentLiteralAndCommentIntervals.add(curInterval);
						curInterval = null;
						inMultiLineComment = false;
						++i;
					}

					if ('\n' == doc.getText(i, 1).charAt(0) && inSinglLineComment) {
						curInterval[1] = i;
						_currentLiteralAndCommentIntervals.add(curInterval);
						curInterval = null;
						inSinglLineComment = false;
					}

					if ('\'' == doc.getText(i, 1).charAt(0) && inLiteral) {
						if (i < docLen + 1 && '\'' == doc.getText(i + 1, 1).charAt(0)) {
							++i;
						} else {
							curInterval[1] = i;
							_currentLiteralAndCommentIntervals.add(curInterval);
							curInterval = null;
							inLiteral = false;
						}
					}
				}

				if (null != curInterval) {
					_currentLiteralAndCommentIntervals.add(curInterval);
				}
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}
		}

		private int getColorLen(int begin, RecolorEvent re) {
			try {
				int reBegin = Math.min(re.position, re.position + re.adjustment);
				reBegin = Math.max(0, reBegin);

				int end = begin + (reBegin - begin) + Math.max(0, re.adjustment);

				int docLen = getDocument().getLength();

				if (end > docLen - 1) {
					return docLen - begin;
				}

				for (; end < docLen - 1; ++end) {
					if (Character.isWhitespace(getDocument().getText(end, 1).charAt(0))) {
						break;
					}
				}

				int[] interval = getInvolvedLiteralOrCommentInterval(end);

				if (null != interval) {
					return Math.max(end - begin, interval[1] - begin);
				} else {
					return end - begin;
				}
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}
		}

		private int getColorStartPos(RecolorEvent re) {
			try {
				int startPos = Math.min(re.position, re.position + re.adjustment);

				if (0 > startPos) {
					return 0;
				}

				for (; startPos > 0; --startPos) {
					if (Character.isWhitespace(getDocument().getText(startPos - 1, 1).charAt(0))) {
						break;
					}
				}

				int[] interval = getInvolvedLiteralOrCommentInterval(startPos);

				if (null != interval) {
					return Math.min(startPos, interval[0]);
				} else {
					return startPos;
				}

			} catch (BadLocationException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * 取注释位置数组
		 * @param pos 在文档中的位置
		 * @return
		 */
		private int[] getInvolvedLiteralOrCommentInterval(int pos) {
			for (int i = 0; i < _currentLiteralAndCommentIntervals.size(); i++) {
				int[] interval = (int[]) _currentLiteralAndCommentIntervals.elementAt(i);

				if (interval[0] - 1 <= pos && pos <= interval[1] + 1) {
					// The Interval is involved even if pos lied one point
					// before
					// or after the interval.
					// This way for example we get
					// -- Select ...
					// out of comment coloring when the first "-" is removed.

					return interval;
				}
			}
			return null;
		}

		private boolean isBetween(int beg, int end, int p) {
			return beg <= p && p <= end;
		}

		private void setCharacterAttributes(final int offset, final int length, final AttributeSet s, final boolean replace) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// Though in API-Doc they say setCharacterAttributes() is
					// thread save we
					// received observed java.lang.Errors from Swing as well as
					// dead locks.
					// That's why we do changes synchron now.

					document.setCharacterAttributes(offset, length, s, replace);
				}
			});
		}

	}

	public void addSQLTokenListener(SQLTokenListener l) {
		_sqlTokenListeners.add(l);
	}

	public void removeSQLTokenListener(SQLTokenListener l) {
		_sqlTokenListeners.remove(l);
	}

	private void fireTableOrViewFound(String name) {
		Vector buf;
		synchronized (_sqlTokenListeners) {
			buf = (Vector) _sqlTokenListeners.clone();
		}

		for (int i = 0; i < buf.size(); ++i) {
			((SQLTokenListener) buf.get(i)).tableOrViewFound(name);
		}
	}

	/**
	 * <p> Title: 可着色文档 </p>
	 * <p> Description: 和DefaultStyledDocument一样,仅仅截获插入和删除以着色处理. </p>
	 * <p> Copyright: Copyright (c) 2006 </p>
	 * <p> Company: 英極軟件開發（大連）有限公司 </p>
	 * 
	 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
	 * @version 1.0
	 */
	private class HighLightedDocument extends DefaultStyledDocument {

		private static final long serialVersionUID = 1L;

		public HighLightedDocument() {
			super();
			putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");
		}

		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			super.insertString(offs, str, a);
			color(offs, str.length(), true, str);
			documentReader.update(offs, str.length());
		}

		public void remove(int offs, int len) throws BadLocationException {
			String change = getText(offs, len);
			super.remove(offs, len);
			color(offs, -len, true, change);
			documentReader.update(offs, -len);
		}
	}

	public void undo() {
		if (undoManager.canUndo()) {
			undoManager.undo();
		}
	}

	public void redo() {
		if (undoManager.canRedo()) {
			undoManager.redo();
		}
	}

	/**
     * <p> Title: 恢复/重做管理 </p>
     * <p> Description: 对 DocumentEvent.EventType.CHANGE 不做undo/redo管理
     * 比如不想对语法着色进行undo/redo操作．</p>
     * <p> Copyright: Copyright (c) 2006 </p>
     * <p> Company: 英極軟件開發（大連）有限公司 </p>
     * 
     * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
     * @version 1.0
	 */
	private class DBMUndoManager extends UndoManager {

		private static final long serialVersionUID = 1L;

		public DBMUndoManager() {
			super();
			setLimit(200000);
		}

		/**
		 * 忽略DocumentEvent.EventType.CHANGE
		 */
		protected UndoableEdit editToBeUndone() {
			UndoableEdit ue = super.editToBeUndone();

			if (ue == null)
				return null;

			int i = edits.indexOf(ue);
			while (i >= 0) {
				UndoableEdit edit = (UndoableEdit) edits.elementAt(i--);
				if (edit.isSignificant()) {
					if (edit instanceof AbstractDocument.DefaultDocumentEvent) {
						if (DocumentEvent.EventType.CHANGE != ((AbstractDocument.DefaultDocumentEvent) edit).getType()) {
							return edit;
						}
					} else {
						return edit;
					}
				}
			}
			return null;
		}

		/**
		 * 忽略DocumentEvent.EventType.CHANGE
		 */
		protected UndoableEdit editToBeRedone() {
			int count = edits.size();
			UndoableEdit ue = super.editToBeRedone();

			if (null == ue)
				return null;

			int i = edits.indexOf(ue);
			while (i < count) {
				UndoableEdit edit = (UndoableEdit) edits.elementAt(i++);
				if (edit.isSignificant()) {
					if (edit instanceof AbstractDocument.DefaultDocumentEvent) {
						if (DocumentEvent.EventType.CHANGE != ((AbstractDocument.DefaultDocumentEvent) edit).getType()) {
							return edit;
						}
					} else {
						return edit;
					}
				}
			}
			return null;
		}
	}
}

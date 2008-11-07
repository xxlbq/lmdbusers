package com.livedoor.dbm.components.mainframe.history;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.livedoor.dbm.components.common.DBMDialog;
import com.livedoor.dbm.components.common.DBMTable;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.queryanalyzer.QueryAnalyzerPanel;
import com.livedoor.dbm.i18n.ResourceI18n;
import com.livedoor.dbm.util.DBMPropertiesUtil;

/**
 * <p>
 * Description: DBMHistoryDialog
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */

@SuppressWarnings("serial")
public class DBMHistoryDialog extends DBMDialog implements ActionListener {
	

	public static String CLOSE = "CLOSE";

	public static String CLEAR_ENTRY = "CLEAR_ENTRY";

	public static String CLEAR_ALL = "CLEAR_ALL";

	public static String SELECT_ENTRY = "SELECT_ENTRY";

	public static String SELECT_ENTRY_AND_CLOSE = "SELECT_ENTRY_AND_CLOSE";

	private DBMFrame _frame;

	private JToolBar _toolbar;

	private JTable _table;

	private JPanel _tablePanel;

	@SuppressWarnings("unused")
	private String _selectedSQL;

	private JLabel _lblMaxHistory;

	private JTextField _maxHistory;
	/**
	 * 
	 * [機 能] 构造 DBMHistoryDialog
	 * <p>
	 * [解 説] 构造 DBMHistoryDialog，需要DBMFrame构造
	 * <p>
	 * [備 考] なし
	 * @param dbmframe
	 */
	public DBMHistoryDialog(DBMFrame dbmframe) {
		super(dbmframe, "SQL History", 650, 350);
		_selectedSQL = "";
		_lblMaxHistory = new JLabel("Max History:");
		_maxHistory = new JTextField();
		_frame = dbmframe;
		_toolbar = createToolBar();
		_toolbar.setFloatable(false);
		_toolbar.setFocusable(false);
		_toolbar.setRollover(true);
		_table = new DBMTable();
		_table.setAutoResizeMode(0);
		_table.setModel(History.getHistory());
		TableColumnModel tablecolumnmodel = _table.getColumnModel();
		TableColumn tablecolumn = null;
		tablecolumn = tablecolumnmodel.getColumn(0);
		tablecolumn.setPreferredWidth(165);
		tablecolumn = tablecolumnmodel.getColumn(1);
		tablecolumn.setPreferredWidth(140);

		tablecolumn = tablecolumnmodel.getColumn(2);
		tablecolumn.setPreferredWidth(500);
		Properties properties = DBMPropertiesUtil.getUIProperties();
		String s = properties.getProperty("history.maxHistory", "10");
		properties.setProperty("history.maxHistory", "10");
		if (!s.equals("-1"))
			_maxHistory.setText(s);

		_tablePanel = new JPanel();
		_tablePanel.setLayout(new BorderLayout());
		_tablePanel.add(new JScrollPane(_table), "Center");
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(_toolbar, "North");
		getContentPane().add(_tablePanel, "Center");
		addWindowListener(new WindowAdapter() {

			public void windowOpened(WindowEvent windowevent) {
				if (_table.getRowCount() > 0)
					_table.setRowSelectionInterval(0, 0);
				_table.requestFocus();
			}

		});
		_table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent mouseevent) {
				if (mouseevent.getClickCount() == 2) {
					onSelectEntry();
					onClose();
					mouseevent.consume();
				}
			}

		});
		_maxHistory.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent focusevent) {
			}

			public void focusLost(FocusEvent focusevent) {
				int i = getMaxHistory();
				Properties properties1 = DBMPropertiesUtil.getUIProperties();
				properties1
						.setProperty("history.maxHistory", String.valueOf(i));
				DBMPropertiesUtil.setUIProperties(properties1);
			}

		});
		_maxHistory.addKeyListener(new KeyListener() {
	            public void keyPressed(KeyEvent e) {
	            }
	            public void keyTyped(KeyEvent e) {
	                int keyChar = e.getKeyChar();
	                if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
	                    e.consume();
	                }
	            }
	            public void keyReleased(KeyEvent e) {
	            }
	        });
	}
	/**
	 * 
	 * [機 能] 构造 JToolBar
	 * <p>
	 * [解 説] 构造 JToolBar 
	 * <p>
	 * [備 考] なし
	 * @param dbmframe
	 */
	private JToolBar createToolBar() {
		JToolBar jtoolbar = new JToolBar();
		JButton jbutton = new JButton("", ResourceI18n
				.getImage("HISTORY_TB_SELECT"));
		jbutton.setActionCommand(SELECT_ENTRY);
		jbutton.addActionListener(this);
		jbutton.setHorizontalTextPosition(0);
		jbutton.setVerticalTextPosition(3);
		jbutton.setToolTipText(ResourceI18n.getText("HISTORY_TB_SELECT"));
		jtoolbar.add(jbutton);
		jbutton = new JButton("", ResourceI18n
				.getImage("HISTORY_TB_SELECT_CLOSE"));
		jbutton.setActionCommand(SELECT_ENTRY_AND_CLOSE);
		jbutton.addActionListener(this);
		jbutton.setHorizontalTextPosition(0);
		jbutton.setVerticalTextPosition(3);
		jbutton.setToolTipText(ResourceI18n.getText("HISTORY_TB_SELECT_CLOSE"));
		jtoolbar.add(jbutton);
		jtoolbar.addSeparator();
		jbutton = new JButton("", ResourceI18n
				.getImage("HISTORY_TB_CLEARSELECTED"));
		jbutton.setActionCommand(CLEAR_ENTRY);
		jbutton.addActionListener(this);
		jbutton.setHorizontalTextPosition(0);
		jbutton.setVerticalTextPosition(3);
		jbutton
				.setToolTipText(ResourceI18n
						.getText("HISTORY_TB_CLEARSELECTED"));
		jtoolbar.add(jbutton);
		jbutton = new JButton("", ResourceI18n.getImage("HISTORY_TB_CLEARALL"));
		jbutton.setActionCommand(CLEAR_ALL);
		jbutton.addActionListener(this);
		jbutton.setHorizontalTextPosition(0);
		jbutton.setVerticalTextPosition(3);
		jbutton.setToolTipText(ResourceI18n.getText("HISTORY_TB_CLEARALL"));
		jtoolbar.add(jbutton);
		jtoolbar.addSeparator();
		jbutton = new JButton("", ResourceI18n
				.getImage("DATAEDITOR_CLOSEWINDOW"));
		jbutton.setActionCommand(CLOSE);
		jbutton.addActionListener(this);
		jbutton.setHorizontalTextPosition(0);
		jbutton.setVerticalTextPosition(3);
		jbutton.setToolTipText(ResourceI18n.getText("HISTORY_TB_CLOSE"));
		jtoolbar.add(jbutton);
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new FlowLayout(0));
		jpanel.add(_lblMaxHistory);
		_maxHistory.setPreferredSize(new Dimension(50, 16));
		jpanel.add(_maxHistory);

		jtoolbar.add(jpanel);
		return jtoolbar;
	}
	/**
	 * 
	 * [機 能] actionPerformed
	 * <p>
	 * [解 説] actionPerformed
	 * <p>
	 * [備 考] なし
	 * @param actionevent
	 */
	public void actionPerformed(ActionEvent actionevent) {
		String s = actionevent.getActionCommand();
		if (s.equals(CLOSE)) {
			onClose();
		} else if (s.equals(CLEAR_ALL)) {
			onClearAll();
		} else if (s.equals(CLEAR_ENTRY)) {
			onClearEntry();
		} else if (s.equals(SELECT_ENTRY)) {
			onSelectEntry();
		} else if (s.equals(SELECT_ENTRY_AND_CLOSE)) {
			onSelectEntry();
			onClose();
		}
	}
	/**
	 * 
	 * [機 能] onSelectEntry action事件处理
	 * <p>
	 * [解 説] onSelectEntry action事件处理
	 * <p>
	 * [備 考] なし
	 */
	private void onSelectEntry() {
		int rows[] = _table.getSelectedRows();
		if (rows == null || rows.length < 1) {
			return;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rows.length; i++) {
			String rowStr = (String) _table.getValueAt(rows[i], 2);
			sb.append(rowStr);
			sb.append("\n");
			sb.append("go");
			sb.append("\n");
		}
		QueryAnalyzerPanel queryPanel = (QueryAnalyzerPanel) _frame
				.getQueryPanel();
		if(queryPanel!=null){
			queryPanel.replaceSelection(sb.toString());
		}
	}
	/**
	 * 
	 * [機 能] onClearEntry action事件处理
	 * <p>
	 * [解 説] onClearEntry action事件处理
	 * <p>
	 * [備 考] なし
	 */
	private void onClearEntry() {
		int []rows = _table.getSelectedRows();
		for(int k = rows.length-1 ;k>=0;k--){
			int i = rows[k];
	
		History.removeHistoryEntry(i);
		if (_table.getRowCount() != 0)
			if (i == _table.getRowCount())
				_table.setRowSelectionInterval(i - 1, i - 1);
			else if (i < _table.getRowCount()) {
				_table.setRowSelectionInterval(i, i);
			} else {
				int j = _table.getRowCount();
				if (j > 0)
					_table.setRowSelectionInterval(j - 1, j - 1);
			}

	}
	}
	/**
	 * 
	 * [機 能] onClearAll action事件处理
	 * <p>
	 * [解 説] onClearAll action事件处理
	 * <p>
	 * [備 考] なし
	 */
	private void onClearAll() {
		History.clearHistory();

	}
	/**
	 * 
	 * [機 能] onClose action事件处理
	 * <p>
	 * [解 説] onClose action事件处理
	 * <p>
	 * [備 考] なし
	 */
	@SuppressWarnings("deprecation")
	private void onClose() {

		hide();
		dispose();
	}
	/**
	 * 
	 * [機 能] get Max History
	 * <p>
	 * [解 説] get Max History
	 * <p>
	 * @return i
	 * [備 考] なし
	 */
	public int getMaxHistory() {
		int i = -1;
		try {
			i = Integer.parseInt(_maxHistory.getText());
		} catch (Exception exception) {
			i = -1;
		}
		return i;
	}

}

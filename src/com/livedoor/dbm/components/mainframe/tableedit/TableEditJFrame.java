package com.livedoor.dbm.components.mainframe.tableedit;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellEditor;

import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.common.DBMTable;
import com.livedoor.dbm.components.common.DBMWindowListener;
import com.livedoor.dbm.components.common.JTableColumnResize;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.i18n.ResourceI18n;
/**
 * <p>
 * Title: 数据表编辑窗体
 * <p>
 * Description: 取出数据表中数据以进行编辑
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class TableEditJFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 2408429403276396426L;

	private JTable dbDataJTable;

	private JTable dbPrimaryKeyJTable;

	private DatabaseTable dbTable;

	private String dbName;

	private String tableName;

	private String schemaName;

	private ConnectionInfo connInfo;

	private DBSession dbSession;

	private JTextArea messages;

	private JTabbedPane tabbedPane;

	private JTextField rowsLimit;

	private DBDataEditorJTableModel tableModel;

	private JTextField whereText;
	/**
	 * Function: 构造一个新的数据表编辑窗体
	 * <p>
	 * Description: 构造一个新的数据表编辑窗体
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 * 
	 * @param connInfo
	 *            数据库连接新息
	 * @param schemaName
	 *            默认被选择的schema
	 * @param dbName
	 *            默认被选择的database
	 * @param tableName
	 *            默认被选择的table
	 * @param dbSession
	 *            数据库连接session
	 * @throws Exception
	 */
	public TableEditJFrame(ConnectionInfo connInfo, String schemaName, String dbName, String tableName, DBSession dbSession)
			throws Exception {
		this.setTitle("Edit Table");
		this.setIconImage(ResourceI18n.getImage("MAIN_FRAME_EDIT").getImage());
		this.connInfo = connInfo;
		this.dbName = dbName;
		this.tableName = tableName;
		this.schemaName = schemaName;
		this.dbSession = dbSession;
		addWindowListener(new DBMWindowListener(connInfo, dbSession));
		initComponents();

	}
	/**
	 * Function: 组件初始化
	 * <p>
	 * Description: 组件初始化
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 * 
	 * @throws DBMException
	 */
	@SuppressWarnings("deprecation")
	private void initComponents() throws DBMException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = dimension.width / 6;
		int y = dimension.height / 6;
		//		
		// this.setBounds(x,y, x*10,y*10);
		this.setLocation(x, y);
		this.setPreferredSize(new Dimension(x * 4, y * 4));

		Container contentPane = getContentPane();

		// 菜单和工具栏
		JToolBar toolbar = new JToolBar();
		JMenuBar menubar = this.getJMenuBar();
		toolbar.setRollover(true);
		toolbar.setFocusable(false);
		toolbar.setFloatable(false);
		toolbar.setLayout(new FlowLayout(FlowLayout.LEADING));

		JButton toolbarRefresh = new JButton(ResourceI18n.getImage("DATAEDITOR_REFRESH"));
		toolbarRefresh.setToolTipText(ResourceI18n.getText("TE_MENU_FILE_REFRESH"));
		JButton toolbarSave = new JButton(ResourceI18n.getImage("DATAEDITOR_SAVECHANGESREFRESH"));
		toolbarSave.setToolTipText(ResourceI18n.getText("TE_MENU_FILE_SAVE_CHANGES"));
		JButton toolbarAdd = new JButton(ResourceI18n.getImage("DATAEDITOR_INSERTROW"));
		toolbarAdd.setToolTipText(ResourceI18n.getText("TE_MENU_EDIT_INSERT_ROW"));
		JButton toolbarDel = new JButton(ResourceI18n.getImage("DATAEDITOR_DELETEROW"));
		toolbarDel.setToolTipText(ResourceI18n.getText("TE_MENU_EDIT_DELETE_ROW"));
		JButton toolbarClearCellChanges = new JButton(ResourceI18n.getImage("DATAEDITOR_CLEARCELL"));
		toolbarClearCellChanges.setToolTipText(ResourceI18n.getText("TE_MENU_EDIT_CLEAR_CELL"));
		JButton toolbarClearDeleteRow = new JButton(ResourceI18n.getImage("DATAEDITOR_CLEARDELETE"));
		toolbarClearDeleteRow.setToolTipText(ResourceI18n.getText("TE_MENU_EDIT_CLEAR_DELETE"));
		JButton toolbarClearAllChanges = new JButton(ResourceI18n.getImage("DATAEDITOR_CLEARCHANGES"));
		toolbarClearAllChanges.setToolTipText(ResourceI18n.getText("TE_MENU_EDIT_CLEAR_CHANGES"));
		JButton toolbarSetCellNull = new JButton(ResourceI18n.getImage("DATAEDITOR_SETNULL"));
		toolbarSetCellNull.setToolTipText(ResourceI18n.getText("TE_MENU_EDIT_SET_NULL"));
		JButton toolbarSetCurrentTime = new JButton(ResourceI18n.getImage("DATAEDITOR_INSERTDATETIME"));
		toolbarSetCurrentTime.setToolTipText(ResourceI18n.getText("TE_MENU_EDIT_INSERT_DATETIME"));
		rowsLimit = new JTextField("1000");
		rowsLimit.addKeyListener(new KeyListener() {
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
		rowsLimit.setPreferredSize(new Dimension(100, 20));

		toolbar.add(toolbarRefresh);
		toolbar.add(toolbarSave);
		toolbar.addSeparator();
		toolbar.add(toolbarAdd);
		toolbar.add(toolbarDel);
		toolbar.addSeparator();
		toolbar.add(toolbarClearAllChanges);
		toolbar.add(toolbarClearDeleteRow);
		toolbar.add(toolbarClearCellChanges);
		toolbar.addSeparator();
		toolbar.add(toolbarSetCellNull);
		toolbar.add(toolbarSetCurrentTime);
		toolbar.addSeparator();
		toolbar.add(new JLabel(ResourceI18n.getText("TE_MENU_EDIT_MAX_RESULTS")));
		toolbar.add(rowsLimit);

		contentPane.add(toolbar, BorderLayout.NORTH);
		getRootPane().setMenuBar(menubar);

		// 数据编辑区
		tabbedPane = new JTabbedPane();
		JPanel panelOne = new JPanel();
		JPanel panelTwo = new JPanel();
		JPanel panelMessage = new JPanel();

		tabbedPane.add(panelOne, ResourceI18n.getText("TAB_TABLEDATA"));
		tabbedPane.add(panelTwo, ResourceI18n.getText("TAB_PRIMARYKEY"));
		tabbedPane.add(panelMessage, ResourceI18n.getText("TAB_MESSAGE"));
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		this.dbDataJTable = new DBDataEditorJTable();
		dbDataJTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		panelOne.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(dbDataJTable);
		panelOne.add(scrollPane, BorderLayout.CENTER);
		
		//where编辑区
		JPanel wherePanel = new JPanel(new BorderLayout());
		whereText = new JTextField();
		wherePanel.add(whereText,BorderLayout.CENTER);
		JLabel whereLabel = new JLabel("where:");
		wherePanel.add(whereLabel,BorderLayout.WEST);
		panelOne.add(wherePanel,BorderLayout.NORTH);
		
		dbPrimaryKeyJTable = new DBMTable();
		dbPrimaryKeyJTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		panelTwo.setLayout(new BorderLayout());
		JScrollPane scrollPane2 = new JScrollPane(dbPrimaryKeyJTable);
		panelTwo.add(scrollPane2, BorderLayout.CENTER);

		panelMessage.setLayout(new BorderLayout());
		messages = new JTextArea();
		panelMessage.add(messages, BorderLayout.CENTER);

		dbTable = new DatabaseTable(this.connInfo, this.schemaName, this.dbName, this.tableName, this.dbSession);
		tableModel = new DBDataEditorJTableModel(dbTable.getColumnNames(), dbTable.getColumnTypes(), dbTable.getDeletedRows(), dbTable
				.getUpdatedRows(), dbTable.getDatas());
		dbDataJTable.setModel(tableModel);
		JTableColumnResize.initColumnSize(dbDataJTable);

		// dbDataJTable.setDefaultRenderer(String.class,new
		// DBMTableCellRenderer(dbDataJTable));
		dbPrimaryKeyJTable.setModel(new PrimaryKeyJTableModel(dbTable.getColumnsNameAndTypeName()));
		JTableColumnResize.initColumnSize(dbPrimaryKeyJTable);
		toolbarAdd.setActionCommand("INSERT_ROW");
		toolbarAdd.addActionListener(this);

		toolbarDel.setActionCommand("DELETE_ROW");
		toolbarDel.addActionListener(this);

		toolbarSave.setActionCommand("SAVE_CHANGES");
		toolbarSave.addActionListener(this);

		toolbarClearCellChanges.setActionCommand("CLEAR_CELL");
		toolbarClearCellChanges.addActionListener(this);

		toolbarClearDeleteRow.setActionCommand("CLEAR_DELETE");
		toolbarClearDeleteRow.addActionListener(this);

		toolbarClearAllChanges.setActionCommand("CLEAR_CHANGES");
		toolbarClearAllChanges.addActionListener(this);

		toolbarSetCellNull.setActionCommand("SET_NULL");
		toolbarSetCellNull.addActionListener(this);

		toolbarSetCurrentTime.setActionCommand("INSERT_DATETIME");
		toolbarSetCurrentTime.addActionListener(this);

		toolbarRefresh.setActionCommand("REFRESH");
		toolbarRefresh.addActionListener(this);
		// addWindowListener(new DBMWindowListener(connInfo, dbSession));

		this.pack();

	}
	/**
	 * Function: 在窗体数据表中添加记录
	 * <p>
	 * Description: 在窗体数据表中添加记录
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 */
	private void addNewRow() {
		DBDataEditorJTableModel model = (DBDataEditorJTableModel) dbDataJTable.getModel();
		model.addEmptyRow();
	}
	/**
	 * Function: 在窗体数据表中删除记录
	 * <p>
	 * Description: 在窗体数据表中删除记录
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 */
	private void delRow() {

		DBDataEditorJTableModel model = (DBDataEditorJTableModel) dbDataJTable.getModel();
		int[] selectedIndex = dbDataJTable.getSelectedRows();
		if (selectedIndex != null) {
			for (int i = selectedIndex.length - 1; i >= 0; i--) {
				model.deleteRow(selectedIndex[i]);
			}
		}

	}
	/**
	 * Function: 保存数据
	 * <p>
	 * Description: 将主窗体内数据表的编辑更新到数据库
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 */
	private void save() {
		stopEdit();
		String errorMessage = dbTable.save();
		this.messages.setText(errorMessage);
		if (errorMessage.length() > 0) {
			tabbedPane.setEnabledAt(2, true);
			tabbedPane.setSelectedIndex(2);
		} else {
			// 保存成功,刷新页面
			refresh();
		}
	}
	/**
	 * Function: 刷新主窗体
	 * <p>
	 * Description: 刷新主窗体，将被修改过但没有保存的数据进行恢复
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 */
	private void refresh() {

		DBDataEditorJTableModel dbDataModel = (DBDataEditorJTableModel) dbDataJTable.getModel();
		PrimaryKeyJTableModel pkModel = (PrimaryKeyJTableModel) dbPrimaryKeyJTable.getModel();
		dbDataModel.clearAllChanges();// 清除所有的更改记录
		this.dbTable.setRowLimit(this.rowsLimit.getText());
		this.dbTable.setWhereStr(this.whereText.getText());
		String errorMessage = "";
		
		
		try {
			this.dbTable.refresh();
		} catch (DBMException e1) {
			errorMessage = e1.getMessage();
			e1.printStackTrace();
		}
		this.messages.setText(errorMessage);
		if (errorMessage.length() > 0) {
			tabbedPane.setEnabledAt(2, true);
			tabbedPane.setSelectedIndex(2);
		}
		
		dbDataModel.refresh(dbTable.getColumnNames(), dbTable.getColumnTypes(), dbTable.getDeletedRows(), dbTable.getUpdatedRows(), dbTable
				.getDatas());
		pkModel.refresh(dbTable.getColumnsNameAndTypeName());
	}
	/**
	 * Function: 恢复单元格元始数据
	 * <p>
	 * Description: 恢复单元格元始数据
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 */
	private void clearCellChanges() {

		cancelEdit();
		DBDataEditorJTableModel model = (DBDataEditorJTableModel) dbDataJTable.getModel();
		int colIndex = dbDataJTable.getSelectedColumn();
		int rowIndex = dbDataJTable.getSelectedRow();

		if (colIndex >= 0 && rowIndex >= 0)
			model.clearCellChanges(rowIndex, colIndex);
	}
	/**
	 * Function: 清空删除的行
	 * <p>
	 * Description: 清空删除的行
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 */
	private void clearDeleteRow() {

		DBDataEditorJTableModel model = (DBDataEditorJTableModel) dbDataJTable.getModel();
		int[] rowIndex = dbDataJTable.getSelectedRows();

		model.clearDeleteRow(rowIndex);
	}
	/**
	 * Function: 清空所有编辑
	 * <p>
	 * Description: 清空所有编辑
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 */
	private void clearAllChange() {
		cancelEdit();
		DBDataEditorJTableModel model = (DBDataEditorJTableModel) dbDataJTable.getModel();
		model.clearAllChanges();

	}
	/**
	 * Function: 数据置空
	 * <p>
	 * Description: 将单元格内数据置为数据库中的空值
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 */
	private void setCellNull() {

		cancelEdit();
		DBDataEditorJTableModel model = (DBDataEditorJTableModel) dbDataJTable.getModel();
		int colIndex = dbDataJTable.getSelectedColumn();
		int rowIndex = dbDataJTable.getSelectedRow();

		if (colIndex >= 0 && rowIndex >= 0)
			model.setCellNull(rowIndex, colIndex);

	}
	/**
	 * Function: 获取当前时间
	 * <p>
	 * Description: 获取当前时间
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 */
	private void setCurrentTime() {
		cancelEdit();
		DBDataEditorJTableModel model = (DBDataEditorJTableModel) dbDataJTable.getModel();
		int colIndex = dbDataJTable.getSelectedColumn();
		int rowIndex = dbDataJTable.getSelectedRow();

		if (colIndex >= 0 && rowIndex >= 0)
			model.setCurrentTime(rowIndex, colIndex);
	}
	/**
	 * Function: 菜单初始化
	 * <p>
	 * Description: 菜单初始化
	 * <p>
	 * 
	 * @author WangHuiTang
	 * @version 1.0
	 * 
	 * @throws DBMException
	 */
	public JMenuBar getJMenuBar() {

		int i = 2;
		JMenuBar jmenubar = new JMenuBar();
		JMenu jmenu = new JMenu(ResourceI18n.getText("MENU_FILE"));
		jmenu.setMnemonic(ResourceI18n.getCharInt("MENU_FILE_MNEM"));
		JMenuItem jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("TE_MENU_FILE_REFRESH"), ResourceI18n
				.getImage("DATAEDITOR_REFRESH")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("TE_MENU_FILE_REFRESH_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(82, i, false));
		jmenuitem.setActionCommand("REFRESH");
		jmenuitem.addActionListener(this);
		jmenu.addSeparator();
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("TE_MENU_FILE_SAVE_CHANGES"), ResourceI18n
				.getImage("DATAEDITOR_SAVECHANGES")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("TE_MENU_FILE_SAVE_CHANGES_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(84, i, false));
		jmenuitem.setActionCommand("SAVE_CHANGES");
		jmenuitem.addActionListener(this);

		jmenubar.add(jmenu);
		jmenu = new JMenu(ResourceI18n.getText("MENU_EDIT"));
		jmenu.setMnemonic(ResourceI18n.getCharInt("MENU_EDIT_MNEM"));
		jmenuitem = jmenu
				.add(new JMenuItem(ResourceI18n.getText("TE_MENU_EDIT_DELETE_ROW"), ResourceI18n.getImage("DATAEDITOR_DELETEROW")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("TE_MENU_EDIT_DELETE_ROW_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(127, 0, false));
		jmenuitem.setActionCommand("DELETE_ROW");
		jmenuitem.addActionListener(this);
		jmenuitem = jmenu
				.add(new JMenuItem(ResourceI18n.getText("TE_MENU_EDIT_INSERT_ROW"), ResourceI18n.getImage("DATAEDITOR_INSERTROW")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("TE_MENU_EDIT_INSERT_ROW_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(155, 0, false));
		jmenuitem.setActionCommand("INSERT_ROW");
		jmenuitem.addActionListener(this);
		jmenu.addSeparator();
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("TE_MENU_EDIT_CLEAR_CHANGES"), ResourceI18n
				.getImage("DATAEDITOR_CLEARCHANGES")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("TE_MENU_EDIT_CLEAR_CHANGES_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(69, i, false));
		jmenuitem.setActionCommand("CLEAR_CHANGES");
		jmenuitem.addActionListener(this);
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("TE_MENU_EDIT_CLEAR_DELETE"), ResourceI18n
				.getImage("DATAEDITOR_CLEARDELETE")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("TE_MENU_EDIT_CLEAR_DELETE_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(69, i | 0x8, false));
		jmenuitem.setActionCommand("CLEAR_DELETE");
		jmenuitem.addActionListener(this);
		jmenuitem = jmenu
				.add(new JMenuItem(ResourceI18n.getText("TE_MENU_EDIT_CLEAR_CELL"), ResourceI18n.getImage("DATAEDITOR_CLEARCELL")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("TE_MENU_EDIT_CLEAR_CELL_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(87, 8, false));
		jmenuitem.setActionCommand("CLEAR_CELL");
		jmenuitem.addActionListener(this);
		jmenu.addSeparator();
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("TE_MENU_EDIT_SET_NULL"), ResourceI18n.getImage("DATAEDITOR_SETNULL")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("TE_MENU_EDIT_SET_NULL_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(67, 8, false));
		jmenuitem.setActionCommand("SET_NULL");
		jmenuitem.addActionListener(this);
		jmenuitem = jmenu.add(new JMenuItem(ResourceI18n.getText("TE_MENU_EDIT_INSERT_DATETIME"), ResourceI18n
				.getImage("DATAEDITOR_INSERTDATETIME")));
		jmenuitem.setMnemonic(ResourceI18n.getCharInt("TE_MENU_EDIT_INSERT_DATETIME_MNEM"));
		jmenuitem.setAccelerator(KeyStroke.getKeyStroke(84, 8, false));
		jmenuitem.setActionCommand("INSERT_DATETIME");
		jmenuitem.addActionListener(this);
		jmenubar.add(jmenu);
		return jmenubar;

	}

	public void actionPerformed(ActionEvent e) {
		String actionName = e.getActionCommand();
		if ("REFRESH".equals(actionName)) {
			if (tableModel.haveChanges()) {
				int status = DBMMessageDialog.showConfirmDialog("close_window_without_save_change");
				if (status == 1)
					return;
			}
			refresh();
		} else if ("SAVE_REFRESH".equals(actionName)) {
			save();
		} else if ("SAVE_CHANGES".equals(actionName)) {
			save();

		} else if ("DELETE_ROW".equals(actionName)) {
			delRow();

		} else if ("INSERT_ROW".equals(actionName)) {
			addNewRow();

		} else if ("CLEAR_CHANGES".equals(actionName)) {
			clearAllChange();

		} else if ("CLEAR_DELETE".equals(actionName)) {
			clearDeleteRow();

		} else if ("CLEAR_CELL".equals(actionName)) {
			clearCellChanges();

		} else if ("SET_NULL".equals(actionName)) {
			setCellNull();

		} else if ("INSERT_DATETIME".equals(actionName)) {
			setCurrentTime();
		}

	}
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			if (tableModel.haveChanges()) {
				int status = DBMMessageDialog.showConfirmDialog("close_window_without_save_change");
				if (status == 0) {
					super.processWindowEvent(e);
				}
			} else {
				super.processWindowEvent(e);
			}
		} else
			super.processWindowEvent(e);
	}

	/**
	 * 停止编辑,忽略所有异常
	 * 
	 */
	private void stopEdit() {
		try {
			TableCellEditor celleditor = this.dbDataJTable.getCellEditor();
			if (celleditor != null)
				celleditor.stopCellEditing();

		} catch (Exception e) {

		}
	}

	/**
	 * 取消编辑,忽略所有异常
	 * 
	 */
	private void cancelEdit() {
		try {
			TableCellEditor celleditor = this.dbDataJTable.getCellEditor();
			if (celleditor != null)
				celleditor.cancelCellEditing();

		} catch (Exception e) {

		}
	}
}

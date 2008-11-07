/**
 * 創建期日 2006-09-22
 */
package com.livedoor.dbm.components.mainframe.er;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;

import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.common.DBMTabbedPane;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.i18n.ResourceI18n;
import com.livedoor.dbm.util.DBMComponentUtil;
/**
 * <p>
 * Title: DbManager
 * </p>
 * <p>
 * Description: ER图向导对话框中的TAB组件.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * </p>
 * 
 * @author Zhangys
 * @version 1.0
 */
public class ErTabbedPane extends DBMTabbedPane {

	private static final long serialVersionUID = 8443696765632866243L;

	private static final String titles[] = {"General", "Status"};

	private JPanel panels[];

	private Icon icons[];

	private JLabel jLabelDatabase = null;

	private JLabel jLabelSchema = null;

	private JComboBox jComboBoxDatabase = null;

	private JComboBox jComboBoxSchema = null;

	private JButton btnSelectAll;

	private JButton btnDeselectAll;

	private JButton btnSelectAllType;

	private JButton btnDeselectAllType;

	private ConnectionInfo conn;

	private DBSession dbSession;

	private ObjectTable objectTable;

	private ObjectTypesTable objectTypesTable;

	private JScrollPane jscrollpaneObjects;

	DBMSqlExecuter execute;

	private JProgressBar jProgressBar_progressBar = null;
	private JEditorPane jEditorPane_editPanel = null;

	private JLabel jLabel_status = null;
	private JLabel jLabel_StatusValue = null;
	private JLabel jLabel_message = null;

	@Override
	public int getTabCounts() {
		return titles.length;
	}

	@Override
	public Icon getTabIcon(int i) {
		return icons[i];
	}

	@Override
	public JPanel getTabPanel(int i) {
		return panels[i];
	}

	@Override
	public String getTabTitle(int i) {
		return titles[i];
	}
	/**
	 * Description: 构造器.
	 * 
	 * @param conn:ConnectionInfo
	 * @param dbSession:DBSession
	 */
	public ErTabbedPane(ConnectionInfo conn, DBSession dbSession) {
		this.dbSession = dbSession;
		this.conn = conn;
		initComponents();
	}
	/**
	 * Description: 初使化ER图向导对话框中的TAB组件.
	 */
	public void initComponents() {
		panels = new JPanel[titles.length];
		panels[0] = new JPanel(new BorderLayout());
		panels[1] = new JPanel();

		icons = new Icon[titles.length];
		icons[0] = ResourceI18n.getImage("TAB_GENERAL");
		icons[1] = ResourceI18n.getImage("TAB_GENERAL");

		btnSelectAll = new JButton(ResourceI18n.getImage("GEN_SELECT"));
		btnDeselectAll = new JButton(ResourceI18n.getImage("GEN_DESELECT"));
		btnSelectAllType = new JButton(ResourceI18n.getImage("GEN_SELECT"));
		btnDeselectAllType = new JButton(ResourceI18n.getImage("GEN_DESELECT"));

		jLabelSchema = new JLabel("Schema:", JLabel.LEFT);
		jLabelSchema.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabelDatabase = new JLabel("Database:", JLabel.LEFT);
		jLabelDatabase.setFont(new Font("Dialog", Font.BOLD, 14));

		objectTypesTable = new ObjectTypesTable();
		objectTable = new ObjectTable();

		buildGeneralPanel();
		buildStatusPanel();

		super.initComponents();
	}
	/**
	 * Description:构造General面板.
	 */
	private void buildGeneralPanel() {
		panels[0].setBorder(new EmptyBorder(0, 10, 10, 10));
		panels[0].add(getDatabaseSchemaPanel(), BorderLayout.NORTH);

		panels[0].add(getTablePanel(), BorderLayout.CENTER);

	}
	/**
	 * Description:得到装载表格的面板.
	 * 
	 * @return:JPanel
	 */
	private JPanel getTablePanel() {

		JPanel jpanel = new JPanel(new BorderLayout());

		jscrollpaneObjects = new JScrollPane(objectTable);
		jscrollpaneObjects.getViewport().setBackground(Color.white);
		jscrollpaneObjects.setRowHeaderView(new ErRowHeader(new DefaultTableModel(0, 0)));

		JScrollPane jscrollpane1 = new JScrollPane(objectTypesTable);
		jscrollpane1.getViewport().setBackground(Color.white);
		jscrollpane1.setRowHeaderView(new ErRowHeader(new DefaultTableModel(2, 1)));

		btnSelectAllType.setPreferredSize(new Dimension(24, 24));
		btnDeselectAllType.setPreferredSize(new Dimension(24, 24));
		btnSelectAll.setPreferredSize(new Dimension(24, 24));
		btnDeselectAll.setPreferredSize(new Dimension(24, 24));
		btnSelectAllType.setToolTipText("Select All");
		btnDeselectAllType.setToolTipText("Unselect All");
		btnSelectAll.setToolTipText("Select All");
		btnDeselectAll.setToolTipText("Unselect All");

		JPanel jpanel1 = new JPanel(new FlowLayout(0));
		jpanel1.add(btnSelectAllType);
		jpanel1.add(btnDeselectAllType);

		JPanel jpanel2 = new JPanel(new BorderLayout());
		jpanel2.add(jpanel1, "North");
		jpanel2.add(jscrollpane1, "Center");

		jpanel2.setBorder(new CompoundBorder(new TitledBorder(new EtchedBorder(1), " Object Types "), new EmptyBorder(0, 10, 10, 10)));

		JPanel jpanel3 = new JPanel(new FlowLayout(0));
		jpanel3.add(btnSelectAll);
		jpanel3.add(btnDeselectAll);

		JPanel jpanel4 = new JPanel(new BorderLayout());
		jpanel4.add(jpanel3, "North");
		jpanel4.add(jscrollpaneObjects, "Center");

		jpanel4.setBorder(new CompoundBorder(new TitledBorder(new EtchedBorder(1), " Objects "), new EmptyBorder(0, 10, 10, 10)));

		jpanel2.setPreferredSize(new Dimension(280, -1));
		jpanel4.setPreferredSize(new Dimension(500, -1));

		jpanel.add(jpanel2, "West");
		jpanel.add(jpanel4, "Center");
		btnSelectAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionevent) {
				objectTable.selectAll(true);

			}

		});
		btnDeselectAll.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionevent) {

				objectTable.selectAll(false);
			}

		});
		btnSelectAllType.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionevent) {
				objectTypesTable.selectAll(true);
			}

		});
		btnDeselectAllType.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionevent) {
				objectTypesTable.selectAll(false);
			}

		});

		return jpanel;

	}
	/**
	 * Description:得到模式/数据库
	 * 
	 * @return:JPanel
	 */
	private JPanel getDatabaseSchemaPanel() {
		JPanel databseSchema = new JPanel();
		databseSchema.setLayout(new GridBagLayout());
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;
		constraints1.weightx = 15;
		constraints1.anchor = GridBagConstraints.WEST;

		constraints1.insets = new Insets(10, 10, 15, 0);

		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridx = 0;
		constraints2.gridy = 1;
		constraints2.anchor = GridBagConstraints.WEST;
		constraints2.insets = new Insets(0, 10, 15, 0);

		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.gridx = 1;
		constraints3.gridy = 0;
		constraints3.fill = GridBagConstraints.HORIZONTAL;
		constraints3.weightx = 25;
		constraints3.insets = new Insets(10, 0, 15, 0);

		GridBagConstraints constraints4 = new GridBagConstraints();
		constraints4.gridx = 1;
		constraints4.gridy = 1;
		constraints4.fill = GridBagConstraints.HORIZONTAL;
		constraints4.insets = new Insets(0, 0, 15, 0);
		GridBagConstraints constraints5 = new GridBagConstraints();
		constraints5.gridx = 2;
		constraints5.gridy = 0;
		constraints5.weightx = 60;

		databseSchema.add(jLabelDatabase, constraints1);
		databseSchema.add(getJComboBoxDatabase(), constraints3);
		databseSchema.add(jLabelSchema, constraints2);
		databseSchema.add(getJComboBoxSchema(), constraints4);
		databseSchema.add(new JLabel(""), constraints5);

		return databseSchema;
	}

	/**
	 * Description: 初使化 jComboBoxSchema
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxSchema() {
		if (jComboBoxSchema == null) {
			jComboBoxSchema = new JComboBox();
			initJComboBoxSchemaVaule();

			jComboBoxSchema.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					erMouseEvent(objectTypesTable);

				}

			});
		}
		return jComboBoxSchema;
	}
	/**
	 * Description: 初使化 jComboBoxSchema的值.
	 * 
	 */
	private void initJComboBoxSchemaVaule() {

		if (DBServerType.MYSQL.equals(conn.getDbType())) {

			List data = new ArrayList();
			data.add("<DEFAULT>");
			DBMComponentUtil.addItems(jComboBoxSchema, data);
			// jComboBoxSchema.setEnabled(false);
		} else {
			try {
				DBMComponentUtil.addItems(jComboBoxSchema, execute.getSchemas());
			} catch (DBMException e) {
				DBMMessageDialog.showErrorMessageDialog(e.getMessage());
				return;
			}
		}
	}
	/**
	 * Description:构造Status面板.
	 * 
	 */
	private void buildStatusPanel() {

		jLabel_message = new JLabel();
		jLabel_message.setBounds(new Rectangle(40, 175, 266, 18));
		jLabel_message.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_message.setText(ResourceI18n.getText("ER_DIAGRAM_GENERATION_TIP"));
		jLabel_StatusValue = new JLabel();
		jLabel_StatusValue.setBounds(new Rectangle(139, 123, 130, 18));
		jLabel_StatusValue.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_StatusValue.setText("");
		jLabel_status = new JLabel();
		jLabel_status.setBounds(new Rectangle(40, 123, 65, 18));
		jLabel_status.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabel_status.setText("Status:");
		panels[1].setSize(535, 358);
		panels[1].setLayout(null);
		panels[1].add(getJProgressBar_progressBar(), null);
		panels[1].add(getJEditorPane_editPanel(), null);
		panels[1].add(jLabel_status, null);
		panels[1].add(jLabel_StatusValue, null);
		panels[1].add(jLabel_message, null);

	}

	/**
	 * Description: 初使化 jProgressBar_progressBar
	 * 
	 * @return javax.swing.JProgressBar
	 */
	public JProgressBar getJProgressBar_progressBar() {
		if (jProgressBar_progressBar == null) {
			jProgressBar_progressBar = new JProgressBar();
			jProgressBar_progressBar.setBounds(new Rectangle(40, 47, 448, 18));
		}
		return jProgressBar_progressBar;
	}

	/**
	 * Description: 初使化 jEditorPane_editPanel
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getJEditorPane_editPanel() {
		if (jEditorPane_editPanel == null) {
			jEditorPane_editPanel = new JEditorPane();
			jEditorPane_editPanel.setBounds(new Rectangle(40, 201, 448, 101));
			int i = jEditorPane_editPanel.getDocument().getLength();
			try {
				jEditorPane_editPanel.getDocument().insertString(i, ResourceI18n.getText("ER_DIAGRAM_GENERATION_MESSAGE") + "\n", null);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			jEditorPane_editPanel.setCaretPosition(jEditorPane_editPanel.getDocument().getLength() - 1);
		}
		return jEditorPane_editPanel;
	}

	/**
	 * Description:得到选择的表.
	 * 
	 * @return Vector
	 */
	public Vector getSelectTables() {
		Vector vector = new Vector();

		for (int i = 0; i < objectTable.getRowCount(); i++) {
			if (((Boolean) objectTable.getValueAt(i, 1)).booleanValue() == true) {
				vector.add((String) objectTable.getValueAt(i, 3));
			}
		}
		return vector;
	}
	/**
	 * Description:得到execute.
	 * 
	 * @return execute
	 */
	public DBMSqlExecuter getExecute() {
		return execute;
	}
	/**
	 * Description:得到jComboBoxSchema.
	 * 
	 * @return jComboBoxSchema
	 */
	public JComboBox getSchemaeComboBox() {

		return jComboBoxSchema;
	}
	/**
	 * Description:得到jComboBoxDatabase.
	 * 
	 * @return jComboBoxDatabase
	 */
	public JComboBox getDatabaseComboBox() {
		return jComboBoxDatabase;
	}
	/**
	 * Description:得到jLabel_StatusValue.
	 * 
	 * @return jLabel_StatusValue
	 */
	public JLabel getJLabel_StatusValue() {
		return jLabel_StatusValue;
	}
	/**
	 * Description:鼠标事件.
	 * 
	 * @param objectTypesTable:ObjectTypesTable
	 */
	private void erMouseEvent(ObjectTypesTable objectTypesTable) {

		Object[][] data = null;
		Object[] title = {" ", "", "Schema", "Name"};

		Boolean bTables = (Boolean) objectTypesTable.getValueAt(0, 1);
		Boolean bViews = (Boolean) objectTypesTable.getValueAt(1, 1);
		List tablesList = new ArrayList();
		List viewsList = new ArrayList();

		if (bTables.booleanValue() == true || bViews.booleanValue() == true) {
			DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
			dbMetaInfo.setSchemaName((String) jComboBoxSchema.getSelectedItem());
			dbMetaInfo.setDatabaseName((String) jComboBoxDatabase.getSelectedItem());

			if (bTables.booleanValue() == true) {
				try {

					execute.changeDatabase((String) jComboBoxDatabase.getSelectedItem());
					tablesList = execute.getTableNames(dbMetaInfo);

				} catch (DBMException e1) {
					DBMMessageDialog.showErrorMessageDialog(e1.getMessage());
					return;
				}

			}

			if (bViews.booleanValue() == true) {
				try {
					execute.changeDatabase((String) jComboBoxDatabase.getSelectedItem());

					viewsList = execute.getViews(dbMetaInfo);

				} catch (DBMException e1) {
					DBMMessageDialog.showErrorMessageDialog(e1.getMessage());
					return;
				}

			}

			tablesList.addAll(viewsList);
			data = new Object[tablesList.size()][4];

			for (int i = 0; i < tablesList.size(); i++) {
				for (int j = 0; j < 4; j++) {
					if (j == 0) {
						data[i][j] = ResourceI18n.getImage("TREE_CONN_TABLES");
					} else if (j == 1) {
						data[i][j] = new Boolean(true);
					} else if (j == 2) {
						data[i][j] = jComboBoxSchema.getSelectedItem().toString();
					} else {
						data[i][j] = (String) tablesList.get(i);
					}
				}
			}

			resetObjectTable(new ErTableModel(data, title), new ErRowHeader(new DefaultTableModel(tablesList.size(), 1)));
		} else {
			resetObjectTable(new ErTableModel(new Object[0][0], title), new ErRowHeader(new DefaultTableModel(0, 1)));
		}

	}

	/**
	 * Description:重新定ObjectTable.
	 * 
	 * @param model:ErTableModel
	 * @param erRowHeader:ErRowHeader
	 */
	private void resetObjectTable(ErTableModel model, ErRowHeader erRowHeader) {
		objectTable.setModel(model);
		objectTable.initComponents();
		jscrollpaneObjects.setRowHeaderView(erRowHeader);
	}

	/**
	 * Description:得到jComboBoxDatabase.
	 * 
	 * @return jComboBoxDatabase
	 */
	private JComboBox getJComboBoxDatabase() {
		if (jComboBoxDatabase == null) {
			jComboBoxDatabase = new JComboBox();
			initJComboBoxDatabaseVaule();

		}
		jComboBoxDatabase.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				erMouseEvent(objectTypesTable);

			}

		});

		return jComboBoxDatabase;
	}
	/**
	 * Description:初使化jComboBoxDatabase的值.
	 * 
	 */
	private void initJComboBoxDatabaseVaule() {

		execute = DBMSqlExecuterFactory.createExecuter(conn, dbSession);
		try {
			DBMComponentUtil.addItems(jComboBoxDatabase, execute.getDatabase());
		} catch (DBMException e) {
			DBMMessageDialog.showErrorMessageDialog(e.getMessage());
			return;
		}

	}

	/**
	 * Description:ObjectTypesTable表格.
	 * 
	 */
	class ObjectTypesTable extends JTable {
		Object[] title = {" ", "", "Object"};

		Object[][] data = {{ResourceI18n.getImage("TREE_CONN_TABLES"), new Boolean(false), "Tables"},
				{ResourceI18n.getImage("TREE_CONN_VIEWS"), new Boolean(false), "Views"}};

		public ObjectTypesTable() {

			setModel(new ErTableModel(data, title));
			initComponents();
		}

		private void initComponents() {

			setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			getTableHeader().setReorderingAllowed(false);
			getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			getColumnModel().getColumn(0).setPreferredWidth(30);
			getColumnModel().getColumn(1).setPreferredWidth(20);
			getColumnModel().getColumn(2).setPreferredWidth(300);

			getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
						int column) {

					setIcon((ImageIcon) (value));

					return this;

				}
			});

			getModel().addTableModelListener(new TableModelListener() {

				public void tableChanged(TableModelEvent e) {
					if (e.getType() == e.UPDATE) {
						erMouseEvent(objectTypesTable);

					}
				}

			});

		}

		public boolean isCellEditable(int i, int j) {
			if (j == 1)
				return true;
			else
				return false;
		}

		public void selectAll(boolean flag) {

			for (int i = 0; i < getRowCount(); i++) {
				setValueAt(new Boolean(flag), i, 1);
			}
		}

	}
	/**
	 * Description:ErRowHeader表格.
	 * 
	 */
	class ErRowHeader extends JTable {

		public ErRowHeader(DefaultTableModel model) {
			super(model);
			model.setColumnCount(1);
			model.setColumnIdentifiers(new Object[]{""});

			setEnabled(false);
			setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			getColumnModel().getColumn(0).setPreferredWidth(30);
			setPreferredScrollableViewportSize(new Dimension(getColumnModel().getColumn(0).getPreferredWidth(), 0));
			setDefaultRenderer(getColumnClass(0), new RowHeaderRenderer(this));

		}

	}
	/**
	 * Description:RowHead装饰器.
	 * 
	 */
	class RowHeaderRenderer extends JLabel implements TableCellRenderer {
		JTable reftable;

		public RowHeaderRenderer(JTable reftable) {
			this.reftable = reftable;
		}

		public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int col) {
			((DefaultTableModel) table.getModel()).setRowCount(reftable.getRowCount());
			JTableHeader header = reftable.getTableHeader();
			this.setOpaque(true);
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			setHorizontalAlignment(CENTER);
			setBackground(header.getBackground());

			if (isSelect(row)) {
				setForeground(Color.white);
				setBackground(Color.lightGray);

				setText(String.valueOf(row + 1));

			} else {
				setForeground(header.getForeground());

				setText(String.valueOf(row + 1));
			}
			return this;
		}

		private boolean isSelect(int row) {
			int[] sel = reftable.getSelectedRows();
			for (int i = 0; i < sel.length; i++) {
				if (sel[i] == row)
					return true;
			}
			return false;
		}
	}
	/**
	 * Description:ObjectTable表格.
	 * 
	 */
	class ObjectTable extends JTable {

		Object[] title = {" ", "", "Schema", "Name"};

		Object[][] data = new Object[0][0];

		public ObjectTable() {

			setModel(new ErTableModel(data, title));
			initComponents();
		}

		public void initComponents() {

			setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			getTableHeader().setReorderingAllowed(false);

			getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			getColumnModel().getColumn(0).setPreferredWidth(25);
			getColumnModel().getColumn(1).setPreferredWidth(25);
			getColumnModel().getColumn(2).setPreferredWidth(90);
			getColumnModel().getColumn(3).setPreferredWidth(190);

			getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
						int column) {

					setIcon((ImageIcon) (value));

					return this;

				}
			});
		}

		public void selectAll(boolean flag) {

			for (int i = 0; i < getRowCount(); i++) {
				setValueAt(new Boolean(flag), i, 1);
			}
		}
		
		
		public boolean isCellEditable(int i, int j) {
			if (j < 2)
				return true;
			else
				return false;
		}

	}
	/**
	 * Description:ErTableModel.
	 * 
	 */
	class ErTableModel extends DefaultTableModel {

		public ErTableModel(Object[][] data, Object[] name) {

			super(data, name);

		}

		public Class getColumnClass(int c) {

			return getValueAt(0, c).getClass();

		}
	}

}

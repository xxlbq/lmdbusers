package com.livedoor.dbm.components.mainframe.createdt;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;

import com.livedoor.dbm.components.common.DBMDialog;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.common.DBMWindowListener;
import com.livedoor.dbm.components.common.JTableColumnResize;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.mainframe.createdt.model.AbsMasterTableModel;
import com.livedoor.dbm.components.mainframe.createdt.model.AbsSlaveTableModel;
import com.livedoor.dbm.components.mainframe.createdt.view.MasterJTable;
import com.livedoor.dbm.components.mainframe.createdt.view.SlaveJTable;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;

/**
 * <p>
 * Title:创建和修改表
 * <p>
 * Description:
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author WangHuiTang
 * @version 1.0
 */
public class OperationTableFrame extends DBMDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2408429403276396426L;

	private String dbName;

	private String schemaName;

	private ConnectionInfo connInfo;

	private JTabbedPane tabbedPane;

	private MasterJTable masterJTable;

	private SlaveJTable slaveJTable;

	private AbsMasterTableModel masterJTableModel;

	private AbsSlaveTableModel slaveJTableModel;

	private DBSession dbSession;

	private JTextField nameJText;

	private JComboBox dbNameJComboBox;

	private JComboBox schemaJComboBox;

	private String tableName;

	public final static int OPERATION_CREATE = 0;

	public final static int OPENATION_ALTER = 1;

	JTextArea previewSqlJText;

	private DBMDataMetaInfo dateMetaInfo;

	private int operationFlag;

	private boolean ok = false;

	public OperationTableFrame(DBMFrame frame, ConnectionInfo connInfo, String schemaName, String dbName, DBSession dbSession,
			int operationFlag, String tableName) throws Exception {
		super(frame, "Alter Table", 400, 700);
		this.dbSession = dbSession;
		this.connInfo = connInfo;
		this.dbName = dbName;
		this.schemaName = schemaName;
		this.tableName = tableName;
		this.dateMetaInfo = new DBMDataMetaInfo();
		this.dateMetaInfo.setDatabaseName(this.dbName);
		this.dateMetaInfo.setSchemaName(this.schemaName);
		this.dateMetaInfo.setTableName(this.tableName);
		this.operationFlag = operationFlag;
		InitData();
		initComponents();

	}

	private void InitData() throws DBMException {
		if (OPERATION_CREATE == operationFlag) {
			this.masterJTableModel = OperatiionTableFactory.generateCreateMasterTableModel(connInfo);
			this.slaveJTableModel = OperatiionTableFactory.generateCreateSlaveTableModel(connInfo, masterJTableModel
					.getSlavePropertyNameList(), masterJTableModel.getSlavePropertyValueList());
		} else {
			this.masterJTableModel = OperatiionTableFactory.generateAlterMasterTableModel(connInfo, dbSession, dateMetaInfo);
			this.slaveJTableModel = OperatiionTableFactory.generateAlterSlaveTableModel(connInfo, masterJTableModel
					.getSlavePropertyNameList(), masterJTableModel.getSlavePropertyValueList());
		}
	}

	private void initComponents() throws DBMException {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((dimension.width - 600) / 2, (dimension.height - 750) / 2, 600, 750);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();

		// 菜单和工具栏

		// 数据编辑区
		tabbedPane = new JTabbedPane();
		JPanel generalPanel = new JPanel();
		previewSqlJText = new JTextArea();
		previewSqlJText.setAutoscrolls(true);
		JScrollPane previewSqlJPanel = new JScrollPane(previewSqlJText);

		tabbedPane.add(generalPanel, "General");
		tabbedPane.add(previewSqlJPanel, "PreviewSql");

		tabbedPane.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {

				pageChange();
			}

		});

		JPanel topPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());

		generalPanel.setLayout(new BorderLayout());

		topPanel.setLayout(null);
		JLabel dbNameLabel = new JLabel("Database:");
		dbNameLabel.setBounds(10, 30, 80, 20);
		topPanel.add(dbNameLabel);
		dbNameJComboBox = new JComboBox();
		dbNameJComboBox.addItem(this.dbName);
		dbNameJComboBox.setBounds(100, 30, 240, 20);
		topPanel.add(dbNameJComboBox);

		JLabel schemaNameLabel = new JLabel("Schema:");
		schemaNameLabel.setBounds(10, 60, 80, 20);
		topPanel.add(schemaNameLabel);
		schemaJComboBox = new JComboBox();
		schemaJComboBox.setBounds(100, 60, 240, 20);
		schemaJComboBox.addItem(this.schemaName);

		topPanel.add(schemaJComboBox);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(10, 90, 80, 20);
		topPanel.add(nameLabel);
		nameJText = new JTextField();
		nameJText.setBounds(100, 90, 240, 20);
		nameJText.setText(this.tableName);
		topPanel.add(nameJText);
		topPanel.setPreferredSize(new Dimension(500, 120));

		if (this.operationFlag == OperationTableFrame.OPENATION_ALTER) {
			this.nameJText.setEnabled(false);
			this.schemaJComboBox.setEnabled(false);
			this.dbNameJComboBox.setEnabled(false);
		} else {
			this.setTitle("Create Table");
			this.dbNameJComboBox.setEnabled(false);
			DBMSqlExecuter sqlExecuter = DBMSqlExecuterFactory.createExecuter(connInfo, dbSession);
			List schemaNames = sqlExecuter.getSchemas();
			for (int i = 0; i < schemaNames.size(); i++)
				this.schemaJComboBox.addItem(schemaNames.get(i));
			this.dbNameJComboBox.setEnabled(false);

		}
		masterJTable = new MasterJTable(masterJTableModel);
		masterJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				propertyChanged();
			}
		});

		KeyAdapter keyAdapter = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_DELETE :
						deleteRow();
				}
			}
		};
		masterJTable.addKeyListener(keyAdapter);

		masterJTable.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				propertyChanged();
			}
		});

		masterJTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scrollPane = new JScrollPane(masterJTable);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		centerPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		centerPanel.setPreferredSize(new Dimension(600, 300));

		JTabbedPane columnsPane = new JTabbedPane();
		slaveJTable = new SlaveJTable(slaveJTableModel);
		slaveJTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JScrollPane columnsScrollPane = new JScrollPane(slaveJTable);
		columnsPane.add(columnsScrollPane, "columns");
		columnsPane.setPreferredSize(new Dimension(500, 200));
		columnsPane.setBorder(new EmptyBorder(12, 12, 12, 12));

		generalPanel.add(topPanel, BorderLayout.NORTH);
		generalPanel.add(centerPanel, BorderLayout.CENTER);
		generalPanel.add(columnsPane, BorderLayout.SOUTH);

		tabbedPane.setBorder(new EmptyBorder(12, 12, 12, 12));

		contentPane.add(tabbedPane, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		// contentPane.add(generalPanel, BorderLayout.CENTER);

		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				okAction();
			}

		});

		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				cancelAction();
			}

		});
		JTableColumnResize.initColumnSize(this.masterJTable);
		JTableColumnResize.initColumnSize(this.slaveJTable);

		// addWindowListener(new DBMWindowListener(connInfo, dbSession));
		this.pack();

	}

	private void okAction() {
		stopEdit();
		String tableName = nameJText.getText();
		if (tableName == null || tableName.trim().length() < 1) {
			DBMMessageDialog.showErrorMessageDialog("TABLE_NO_NAME");
			return;
		}
		String sqlStr = getSql();
		if (sqlStr != null && !"".equals(sqlStr)) {
			String[] sqlQueue = sqlStr.split("\n");
			DBMSqlExecuter sqlExecuter = DBMSqlExecuterFactory.createExecuter(connInfo, dbSession);
			sqlExecuter.changeDatabase(this.dbName);
			if (sqlQueue != null) {
				try {
					for (int i = 0; i < sqlQueue.length; i++)
						if (!"".equals(sqlQueue[i].trim()))
							sqlExecuter.executeUpdate(sqlQueue[i]);

				} catch (DBMException e) {
					DBMMessageDialog.showErrorMessageDialog(e.getMessage());
					if (sqlQueue.length > 1) {
						try {
							InitData();
							this.masterJTable.setModel(this.masterJTableModel);
							this.masterJTableModel.refresh();
							this.slaveJTable.setModel(this.slaveJTableModel);
							this.slaveJTableModel.refresh((String) this.masterJTable.getValueAt(0, 1), 0);
						} catch (DBMException e1) {
							e1.printStackTrace();
							DBMMessageDialog.showErrorMessageDialog(e1.getMessage());
							this.dispose();

						}

					}
					return;
				}

			}

		}
		this.setOk();
		this.dispose();

	}

	private String getSql() {
		DBMDataMetaInfo dataMetaInfo = new DBMDataMetaInfo();
		dataMetaInfo.setDatabaseName(this.dbName);
		dataMetaInfo.setSchemaName(this.schemaJComboBox.getSelectedItem().toString());
		dataMetaInfo.setTableName(nameJText.getText());
		String sql = this.masterJTableModel.getSql(connInfo, dbSession, dataMetaInfo);
		return sql;
	}

	private void cancelAction() {
		this.dispose();
	}

	private void propertyChanged() {

		int rowIndex = masterJTable.getSelectedRow();

		if (rowIndex >= 0) {
			stopEdit();
			slaveJTableModel.refresh((String) this.masterJTable.getValueAt(rowIndex, 1), rowIndex);

		}
	}

	/**
	 * 停止编辑,忽略所有异常
	 *
	 */
	private void stopEdit() {
		try {
			TableCellEditor celleditor = slaveJTable.getCellEditor();
			if (celleditor != null)
				celleditor.stopCellEditing();
			celleditor = masterJTable.getCellEditor();
			if (celleditor != null)
				celleditor.stopCellEditing();
		} catch (Exception e) {

		}
	}

	private void deleteRow() {

		int rowIndex = masterJTable.getSelectedRow();
		if (rowIndex >= 0) {
			this.masterJTableModel.deleteRow(rowIndex);
			propertyChanged();
		}

	}

	
	/**
	 * 属性页切换
	 */
	private void pageChange() {
		stopEdit();
		previewSqlJText.setText(getSql());
	}

	public boolean isOk() {
		return this.ok;
	}

	public void setOk() {
		this.ok = true;
	}
}

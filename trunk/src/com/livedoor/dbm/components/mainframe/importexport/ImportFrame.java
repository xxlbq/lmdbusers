package com.livedoor.dbm.components.mainframe.importexport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.common.DBMWindowListener;

import com.livedoor.dbm.components.mainframe.importexport.UserInputInfo;
import com.livedoor.dbm.components.mainframe.importexport.ManipulateData;
import com.livedoor.dbm.components.mainframe.importexport.ValidateUserInput;
import com.livedoor.dbm.connection.ConnectionInfo;

import com.livedoor.dbm.db.DBMSqlExecuter;

import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.i18n.ResourceI18n;
import com.livedoor.dbm.util.importexportutil.DataDelimited;
import com.livedoor.dbm.util.importexportutil.DataEncoding;
import com.livedoor.dbm.util.importexportutil.DateAndTimeFormat;

/**
 * <p>
 * Title: 导出数据主窗体
 * <p>
 * Description: 将数据库数据导出到本地文件
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author ChenGang
 * @version 1.0
 */
public class ImportFrame extends JFrame {

	private ImageIcon frameIcon = ResourceI18n.getImage("MAIN_FRAME");

	private UserInputInfo userInputInfo = new UserInputInfo();

	private ValidateUserInput validateUserInput = new ValidateUserInput();

	private ManipulateData manipulateData;

	private Thread importData;

	/*
	 * JTabbedPane的组件
	 */
	private Icon enableIcon = ResourceI18n.getImage("TAB_APPEARANCE");

	private Border comboBoxBorder = new LineBorder(new Color(192, 192, 192), 1, false);

	private final JTabbedPane tabbedPane = new JTabbedPane();

	/*
	 * ButtonPanel的组件
	 * 
	 */
	JButton closeButton = new JButton("Close");

	JButton previousButton = new JButton("<< Previous");

	JButton nextButton = new JButton("Next >>");

	private final JPanel buttonPanel = new JPanel();

	/*
	 * GeneralPanel的组件
	 */
	private JComboBox delimitedComboBox;

	private JComboBox encodingComboBox;

	private JTextField fileTextField;

	private JFileChooser fileChooser;

	private DataDelimited dataDelimited = new DataDelimited();

	private DataEncoding dataEncoding = new DataEncoding();

	private final JLabel delimitedLabel = new JLabel();

	private final JLabel encodingLabel = new JLabel();

	private final JPanel generalPanel = new JPanel();

	/*
	 * FormatPanel的组件
	 */
	private JComboBox dataBaseComboBox;

	private JComboBox schemaComboBox;

	private JComboBox tableComboBox;

	private final JLabel tableLabel = new JLabel();

	private final JLabel schemaLabel = new JLabel();

	private final JLabel dataBaseLabel = new JLabel();

	private final JPanel formatPanel = new JPanel();

	/*
	 * OptionsPanel的组件
	 */
	private DateAndTimeFormat dateAndTimeFormat = new DateAndTimeFormat();

	private Date currentDate = new Date();

	private JComboBox dateAndTimeComboBox;

	private JComboBox dateComboBox;

	private JComboBox timeComboBox;

	private final JLabel dateLabel = new JLabel();

	private final JLabel timeLabel = new JLabel();

	private final JLabel dateAndTimeLabel = new JLabel();

	private final JLabel dateAndTimeSampleLabel = new JLabel();

	private final JLabel dateSampleLabel = new JLabel();

	private final JLabel timeSampleLabel = new JLabel();

	private final JPanel optionsPanel = new JPanel();

	/*
	 * StatusPanel的组件
	 */
	JTextArea statusMessageTextArea;

	JLabel currentStatusLabel;

	private final JPanel statusPanel = new JPanel();

	/**
	 * Function: 构造一个新的导入数据窗体
	 * <p>
	 * Description: 构造一个新的导入数据窗体
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 * @param connInfo
	 *            数据库连接新息
	 * @param session
	 *            数据库连接session
	 * @param databaseName
	 *            默认被选择的database
	 * @param schemaName
	 *            默认被选择的schema
	 * @param tableName
	 *            默认被选择的table
	 */
	public ImportFrame(ConnectionInfo connInfo, DBSession session, String databaseName, String schemaName, String tableName) {
		super();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		userInputInfo.setDatabaseName(databaseName);
		userInputInfo.setSchemaName(schemaName);
		userInputInfo.setTableName(tableName);

		manipulateData = new ManipulateData(this, userInputInfo, connInfo, session);
		manipulateData.setImportData(true);

		this.setTitle(ResourceI18n.getText("IMPORT_DATA_TITLE"));
		this.setIconImage(frameIcon.getImage());
		this.setResizable(false);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((dimension.width - 510) / 2, (dimension.height - 640) / 2, 510, 640);

		initJTabbedPane();
		initButtonPanel();

		add(tabbedPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);
		addWindowListener(new DBMWindowListener(connInfo, session));
	}

	/**
	 * <p>
	 * Function: 初始化GeneralPanel所包含的组件
	 * <p>
	 * Description: 初始化GeneralPanel所包含的组件
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 */
	private void initGeneralPanel() {
		fileChooser = new JFileChooser();
		generalPanel.setLayout(null);

		final JLabel fileLabel = new JLabel();
		fileLabel.setText("File");
		fileLabel.setBounds(30, 60, 25, 20);
		generalPanel.add(fileLabel);

		final JLabel readFromLabel = new JLabel();
		readFromLabel.setText("Read From");
		readFromLabel.setBounds(15, 25, 70, 20);
		generalPanel.add(readFromLabel);

		final JSeparator readFromSeparator = new JSeparator();
		readFromSeparator.setBounds(100, 35, 370, 5);
		generalPanel.add(readFromSeparator);

		fileTextField = new JTextField();
		fileTextField.setBounds(100, 60, 320, 20);
		generalPanel.add(fileTextField);

		final JButton selectFileButton = new JButton();

		/*
		 * 注册按钮事件激活JFileChooser组件
		 */
		selectFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int state = fileChooser.showOpenDialog(null);
				File file = fileChooser.getSelectedFile();
				if (file != null && state == JFileChooser.APPROVE_OPTION) {
					fileTextField.setText(file.getPath());
				}
			}
		});
		selectFileButton.setText("...");
		selectFileButton.setBounds(420, 60, 27, 20);
		generalPanel.add(selectFileButton);

		final JLabel dataFormatLabel = new JLabel();
		dataFormatLabel.setText("Data Format");
		dataFormatLabel.setBounds(15, 155, 70, 20);
		generalPanel.add(dataFormatLabel);

		final JSeparator dataFormatSeparator = new JSeparator();
		dataFormatSeparator.setBounds(100, 165, 350, 5);
		generalPanel.add(dataFormatSeparator);

		encodingLabel.setText("Encoding");
		encodingLabel.setBounds(30, 95, 70, 20);
		generalPanel.add(encodingLabel);

		delimitedLabel.setText("Delimited");
		delimitedLabel.setBounds(30, 185, 60, 20);
		generalPanel.add(delimitedLabel);

		encodingComboBox = new JComboBox();
		encodingComboBox.setBorder(comboBoxBorder);
		encodingComboBox.setEditable(true);
		comboBoxAddItem(encodingComboBox, encodingLabel.getText());
		encodingComboBox.setBounds(100, 95, 320, 20);
		generalPanel.add(encodingComboBox);

		delimitedComboBox = new JComboBox();
		delimitedComboBox.setBorder(comboBoxBorder);
		delimitedComboBox.setEditable(true);
		comboBoxAddItem(delimitedComboBox, delimitedLabel.getText());
		delimitedComboBox.setBounds(100, 185, 130, 20);
		generalPanel.add(delimitedComboBox);
		//
	}

	/**
	 * <p>
	 * Function: 初始化ButtonPanel所包含的组件
	 * <p>
	 * Description: 初始化ButtonPanel所包含的组件
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 */
	private void initButtonPanel() {
		ButtonClickedAction buttonClickedAction = new ButtonClickedAction();

		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		previousButton.setEnabled(false);

		closeButton.addActionListener(buttonClickedAction);
		previousButton.addActionListener(buttonClickedAction);
		nextButton.addActionListener(buttonClickedAction);

		buttonPanel.add(closeButton);
		buttonPanel.add(previousButton);
		buttonPanel.add(nextButton);
	}

	/**
	 * <p>
	 * Function: 初始化FormatPanel所包含的组件
	 * <p>
	 * Description: 初始化FormatPanel所包含的组件
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 */
	private void initFormatPanel() {

		formatPanel.setLayout(null);
		dataBaseLabel.setText("DataBase:");
		dataBaseLabel.setBounds(30, 40, 80, 20);
		formatPanel.add(dataBaseLabel);

		dataBaseComboBox = new JComboBox();
		dataBaseComboBox.setBorder(comboBoxBorder);
		dataBaseComboBox.setEditable(false);
		comboBoxAddItem(dataBaseComboBox, dataBaseLabel.getText());
		dataBaseComboBox.setBounds(120, 40, 280, 20);

		/*
		 * 监听dataBaseComboBox事件以调整schemaComboBox与tableComboBox的列表内容
		 */
		dataBaseComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String databaseName = (String) (((JComboBox) e.getSource()).getSelectedItem());
				dataBaseComboBox.removeAllItems();
				schemaComboBox.removeAllItems();
				tableComboBox.removeAllItems();
				/*
				 * 重设userInputInfo信息
				 */
				userInputInfo.setDatabaseName(databaseName);
				userInputInfo.setSchemaName("");
				userInputInfo.setTableName("");

				comboBoxAddItem(dataBaseComboBox, dataBaseLabel.getText());
				comboBoxAddItem(schemaComboBox, schemaLabel.getText());
				comboBoxAddItem(tableComboBox, tableLabel.getText());

			}
		});
		formatPanel.add(dataBaseComboBox);

		schemaLabel.setText("Schema:");
		schemaLabel.setBounds(30, 70, 60, 20);
		formatPanel.add(schemaLabel);

		schemaComboBox = new JComboBox();
		schemaComboBox.setBorder(comboBoxBorder);
		schemaComboBox.setEditable(false);
		comboBoxAddItem(schemaComboBox, schemaLabel.getText());
		schemaComboBox.setBounds(120, 70, 280, 20);
		/*
		 * 监听dataBaseComboBox事件以调整tableComboBox的列表内容
		 */
		schemaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String schemaName = (String) (((JComboBox) e.getSource()).getSelectedItem());
				schemaComboBox.removeAllItems();
				tableComboBox.removeAllItems();
				/*
				 * 重设userInputInfo信息
				 */
				userInputInfo.setSchemaName(schemaName);
				userInputInfo.setTableName("");

				comboBoxAddItem(schemaComboBox, schemaLabel.getText());
				comboBoxAddItem(tableComboBox, tableLabel.getText());
			}
		});
		formatPanel.add(schemaComboBox);

		tableLabel.setText("Table:");
		tableLabel.setBounds(30, 100, 60, 20);
		formatPanel.add(tableLabel);

		tableComboBox = new JComboBox();
		tableComboBox.setBorder(comboBoxBorder);
		tableComboBox.setEditable(false);
		comboBoxAddItem(tableComboBox, tableLabel.getText());
		tableComboBox.setBounds(120, 100, 280, 20);
		formatPanel.add(tableComboBox);
	}

	/**
	 * <p>
	 * Function: 初始化OptionsPanel所包含的组件
	 * <p>
	 * Description: 初始化OptionsPanel所包含的组件
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 */
	private void initOptionsPanel() {

		final JPanel innerPanel = new JPanel();
		innerPanel.setLayout(null);
		innerPanel.setBorder(new TitledBorder(null, "Date and Time Formatting", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		optionsPanel.setBorder(new EmptyBorder(15, 15, 350, 15));
		optionsPanel.setLayout(new BorderLayout());

		dateAndTimeLabel.setText("Date & Time:");
		dateAndTimeLabel.setBounds(20, 40, 80, 20);
		innerPanel.add(dateAndTimeLabel);

		dateAndTimeComboBox = new JComboBox();
		dateAndTimeComboBox.setBorder(comboBoxBorder);
		dateAndTimeComboBox.setEditable(true);
		comboBoxAddItem(dateAndTimeComboBox, dateAndTimeLabel.getText());
		dateAndTimeComboBox.setBounds(100, 40, 180, 20);

		/*
		 * 注册ComboBox事件修改ComboBox组件后面Label标签的文本内容
		 */
		dateAndTimeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String format = (String) ((JComboBox) (arg0.getSource())).getSelectedItem();
				String formatted = dateAndTimeFormat.formatDateAndTime(currentDate, format);
				dateAndTimeSampleLabel.setText(formatted);
			}
		});
		innerPanel.add(dateAndTimeComboBox);

		dateAndTimeSampleLabel.setText(dateAndTimeFormat.formatDateAndTime(currentDate, (String) (dateAndTimeComboBox.getSelectedItem())));
		dateAndTimeSampleLabel.setBounds(290, 40, 160, 20);
		innerPanel.add(dateAndTimeSampleLabel);

		dateLabel.setText("Date:");
		dateLabel.setBounds(20, 70, 80, 20);
		innerPanel.add(dateLabel);

		dateComboBox = new JComboBox();
		dateComboBox.setBorder(comboBoxBorder);
		dateComboBox.setEditable(true);
		comboBoxAddItem(dateComboBox, dateLabel.getText());
		dateComboBox.setBounds(100, 70, 180, 20);

		/*
		 * 注册ComboBox事件修改ComboBox组件后面Label标签的文本内容
		 */
		dateComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String format = (String) ((JComboBox) (arg0.getSource())).getSelectedItem();
				String formatted = dateAndTimeFormat.formatDateAndTime(currentDate, format);
				dateSampleLabel.setText(formatted);
			}

		});
		innerPanel.add(dateComboBox);

		dateSampleLabel.setText(dateAndTimeFormat.formatDateAndTime(currentDate, (String) (dateComboBox.getSelectedItem())));
		dateSampleLabel.setBounds(290, 70, 140, 20);
		innerPanel.add(dateSampleLabel);

		timeLabel.setText("Time:");
		timeLabel.setBounds(20, 100, 80, 20);
		innerPanel.add(timeLabel);

		timeComboBox = new JComboBox();
		timeComboBox.setBorder(comboBoxBorder);
		timeComboBox.setEditable(true);
		comboBoxAddItem(timeComboBox, timeLabel.getText());
		timeComboBox.setBounds(100, 100, 180, 20);

		/*
		 * 注册ComboBox事件修改ComboBox组件后面Label标签的文本内容
		 */
		timeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String format = (String) ((JComboBox) (arg0.getSource())).getSelectedItem();
				String formatted = dateAndTimeFormat.formatDateAndTime(currentDate, format);
				timeSampleLabel.setText(formatted);
			}

		});
		innerPanel.add(timeComboBox);

		timeSampleLabel.setText(dateAndTimeFormat.formatDateAndTime(currentDate, (String) (timeComboBox.getSelectedItem())));
		timeSampleLabel.setBounds(290, 100, 140, 20);
		innerPanel.add(timeSampleLabel);

		optionsPanel.add(innerPanel);
	}

	/**
	 * <p>
	 * Function: 初始化StatusPanel所包含的组件
	 * <p>
	 * Description: 初始化StatusPanel所包含的组件
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 */
	private void initStatusPanel() {
		statusPanel.setLayout(null);

		final JLabel statusLabel = new JLabel();
		statusLabel.setText("Status:");
		statusLabel.setBounds(30, 60, 50, 20);
		statusPanel.add(statusLabel);

		currentStatusLabel = new JLabel();
		currentStatusLabel.setBounds(120, 60, 200, 20);
		statusPanel.add(currentStatusLabel);

		final JLabel messageLabel = new JLabel();
		messageLabel.setText("Import Messages:");
		messageLabel.setBounds(30, 95, 120, 20);
		statusPanel.add(messageLabel);

		statusMessageTextArea = new JTextArea();
		final JScrollPane messageScrollPane = new JScrollPane(statusMessageTextArea);
		messageScrollPane.setBounds(30, 120, 420, 300);
		statusPanel.add(messageScrollPane);
	}

	/**
	 * <p>
	 * Function: 初始化tabbedPane
	 * <p>
	 * Description: 初始化tabbedPane
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 */
	private void initJTabbedPane() {
		final Border border = BorderFactory.createEmptyBorder(10, 10, 10, 10);

		tabbedPane.setBorder(border);

		initGeneralPanel();
		initFormatPanel();
		initOptionsPanel();
		initStatusPanel();

		tabbedPane.addTab("General", enableIcon, generalPanel);
		tabbedPane.addTab("Format", enableIcon, formatPanel);
		tabbedPane.addTab("Option", enableIcon, optionsPanel);
		tabbedPane.addTab("Status", enableIcon, statusPanel);

		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(2, false);
		tabbedPane.setEnabledAt(3, false);
	}

	/**
	 * <p>
	 * Title: 按钮事件监听
	 * <p>
	 * Description: 根据事件源响应不同动作
	 * <p>
	 * Copyright: Copyright (c) 2006
	 * <p>
	 * Company: 英極軟件開發（大連）有限公司
	 * 
	 * @author ChenGang
	 * @version 1.0
	 */
	class ButtonClickedAction implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JButton sourceButton = (JButton) (arg0.getSource());
			if ((sourceButton.getText()).equals(nextButton.getText())) {
				dealNextButtonAction();
			} else if ((sourceButton.getText()).equals(previousButton.getText())) {
				dealPreviousButtonAction();
			} else if ((sourceButton.getText()).equals("Close")) {
				// 关闭导入数据窗体
				ImportFrame.this.dispose();
			} else if (sourceButton.getText().equals("Cancel")) {
				ImportFrame.this.manipulateData.requestStop();
				ImportFrame.this.closeButton.setText("Close");
				ImportFrame.this.previousButton.setEnabled(true);
			}
		}

		/**
		 * <p>
		 * Function: 处理点击Next按钮事件
		 * <p>
		 * Description: 处理点击Next按钮事件
		 * <p>
		 * 
		 * @author ChenGang
		 * @version 1.0
		 * 
		 */
		private void dealNextButtonAction() {
			int selectedIndex = 0;
			int tabCount = 0;
			// 当前tabbedPane内别选中组件的index
			selectedIndex = tabbedPane.getSelectedIndex();
			// tabbedPane内组件总数
			tabCount = tabbedPane.getTabCount();
			// 获取用户输入并验证输入
			switch (selectedIndex) {
				case 0 :
					userInputInfo.setFile(fileTextField.getText());
					userInputInfo.setDelimited((String) (delimitedComboBox.getSelectedItem()));
					userInputInfo.setEncoding((String) (encodingComboBox.getSelectedItem()));
					if (!validateUserInput.validateImportFileInput(userInputInfo)) {
						DBMMessageDialog.showErrorMessageDialog(userInputInfo.getMessage());
						return;
					}
					break;
				case 1 :
					userInputInfo.setDatabaseName((String) dataBaseComboBox.getSelectedItem());
					userInputInfo.setSchemaName((String) schemaComboBox.getSelectedItem());
					userInputInfo.setTableName((String) tableComboBox.getSelectedItem());
					break;
				case 2 :
					userInputInfo.setDateTimeFormat((String) dateAndTimeComboBox.getSelectedItem());
					userInputInfo.setDateFormat((String) dateComboBox.getSelectedItem());
					userInputInfo.setTimeFormat((String) timeComboBox.getSelectedItem());
					break;
			}

			setNextPanelEnable();
			selectedIndex = tabbedPane.getSelectedIndex();
			// 设置按钮状态
			if (!(previousButton.isEnabled()))
				previousButton.setEnabled(true);
			if (selectedIndex + 1 == tabCount) {
				currentStatusLabel.setText("");
				statusMessageTextArea.setText(ResourceI18n.getText("IMPORT_DATA_STARTED"));
				nextButton.setEnabled(false);
				importData = new Thread(manipulateData);
				importData.start();
				statusMessageTextArea.append("\n\n");
				statusMessageTextArea.append(ResourceI18n.getText("IMPORT_DATA_ING"));
				previousButton.setEnabled(false);
				closeButton.setText("Cancel");
			}
		}

		/**
		 * <p>
		 * Function: 处理点击Previous按钮事件
		 * <p>
		 * Description: 处理点击Previous按钮事件
		 * <p>
		 * 
		 * @author ChenGang
		 * @version 1.0
		 * 
		 */
		private void dealPreviousButtonAction() {
			int selectedIndex = 0;

			setPreviousPanelEnable();
			selectedIndex = tabbedPane.getSelectedIndex();

			if (!(nextButton.isEnabled()))
				nextButton.setEnabled(true);
			if (selectedIndex == 0)
				previousButton.setEnabled(false);
		}
	}

	/**
	 * <p>
	 * Function: 在点击Next按钮时设置tabbedPane的被选中内容
	 * <p>
	 * Description: 在点击Next按钮时设置tabbedPane的被选中内容
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 */
	void setNextPanelEnable() {
		int i = tabbedPane.getSelectedIndex();
		tabbedPane.setEnabledAt(i, false);
		tabbedPane.setEnabledAt(i + 1, true);
		tabbedPane.setSelectedIndex(i + 1);

	}

	/**
	 * <p>
	 * Function: 在点击Previous按钮时设置tabbedPane的被选中内容
	 * <p>
	 * Description: 在点击Previous按钮时设置tabbedPane的被选中内容
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 */
	void setPreviousPanelEnable() {
		int i = tabbedPane.getSelectedIndex();
		tabbedPane.setEnabledAt(i, false);
		tabbedPane.setEnabledAt(i - 1, true);
		tabbedPane.setSelectedIndex(i - 1);

	}

	/**
	 * <p>
	 * Function: 为窗体内所有ComboBox添加item
	 * <p>
	 * Description: 为窗体内所有ComboBox添加item
	 * <p>
	 * 
	 * @author ChenGang
	 * @version 1.0
	 * 
	 * @param comboBox
	 *            需要添加item的ComboBox的引用
	 * @param type
	 *            与ComoBox关联的Label的文本内容
	 */
	void comboBoxAddItem(JComboBox comboBox, String type) {
		String[] items = null;// 临时保存comboBox要显示的database,schema或table的名字
		List tempList = null;// 从数据库获取的database,schema或table的信息
		int defaultIndex = 0;// 如果存在缺省选定的database,schema或table时用来记录其index
		if (type.equals(delimitedLabel.getText())) {
			// 添加分隔符列表
			items = dataDelimited.getDelimited();
		} else if (type.equals(encodingLabel.getText())) {
			// 添加文件编码列表
			items = dataEncoding.getEncoding();
		} else if (type.equals(dataBaseLabel.getText())) {
			// 添加database列表
			String databaseName = userInputInfo.getDatabaseName();
			try {
				tempList = manipulateData.getDatabasesName();
			} catch (DBMException e) {
				e.printStackTrace();
			}

			items = new String[tempList.size()];
			for (int i = 0; i < tempList.size(); i++) {
				items[i] = (String) (tempList.get(i));
				// 确定缺省database所在的index
				if (databaseName != null && databaseName.equals(items[i]))
					defaultIndex = i;

			}
		} else if (type.equals(schemaLabel.getText())) {
			// 添加schema列表
			String schemaName = userInputInfo.getSchemaName();
			try {
				tempList = manipulateData.getSchemasName();
			} catch (DBMException e) {
				e.printStackTrace();
			}
			if (tempList.size() > 0) {
				items = new String[tempList.size()];
				for (int i = 0; i < tempList.size(); i++) {
					items[i] = (String) (tempList.get(i));
					// 确定缺省schema所在的index
					if (schemaName != null && schemaName.equals(items[i]))
						defaultIndex = i;
				}
			}

		} else if (type.equals(tableLabel.getText())) {
			// 添加table列表
			String tableName = userInputInfo.getTableName();
			try {
				tempList = manipulateData.getTablesName((String) (schemaComboBox.getSelectedItem()), (String) (dataBaseComboBox
						.getSelectedItem()));
			} catch (DBMException e) {
				e.printStackTrace();
			}
			if (tempList.size() > 0) {
				items = new String[tempList.size()];
				for (int i = 0; i < tempList.size(); i++) {
					items[i] = (String) (tempList.get(i));
					// 确定缺省table所在的index
					if (tableName != null && tableName.equals(items[i]))
						defaultIndex = i;
				}
			}
		} else if (type.equals(dateAndTimeLabel.getText())) {
			// 添加日期时间格式列表
			items = dateAndTimeFormat.getDateAndTimeFormat();
		} else if (type.equals(dateLabel.getText())) {
			// 添加日期格式列表
			items = dateAndTimeFormat.getDateFormat();
		} else if (type.equals(timeLabel.getText())) {
			// 添加时间格式列表
			items = dateAndTimeFormat.getTimeFormat();
		} else {
			return;
		}
		if (items != null) {
			comboBox.setEditable(false);
			comboBox.setEnabled(true);
			for (int i = 0; i < items.length; i++)
				comboBox.addItem(items[i]);
		} else {
			// 在某个ComboBox内没有内容的时候将其置灰
			comboBox.setEditable(false);
			comboBox.setEnabled(false);
		}
		if (defaultIndex != 0) {
			comboBox.setSelectedIndex(defaultIndex);
		}
	}
}

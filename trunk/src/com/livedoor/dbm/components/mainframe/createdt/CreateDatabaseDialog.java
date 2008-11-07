package com.livedoor.dbm.components.mainframe.createdt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.common.DBMWindowListener;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.i18n.ResourceI18n;
/**
 * <p>
 * Title: 创建数据主窗体
 * <p>
 * Description: 创建数据库，仅适用mysql数据库
 * <p>
 * Copyright: Copyright (c) 2006
 * <p>
 * Company: 英極軟件開發（大連）有限公司
 * 
 * @author ChenGang
 * @version 1.0
 */
public class CreateDatabaseDialog  extends JDialog {
	private ImageIcon frameIcon = ResourceI18n.getImage("MAIN_FRAME");
	private Icon panelIcon = ResourceI18n.getImage("TAB_APPEARANCE");
	
	private DBMSqlExecuter executer;
	
	private JTextField databaseNameTextField;
	private JButton okButton;
	private JButton cancelButton;
	
	private boolean ok;
	public CreateDatabaseDialog (JFrame frame, boolean modal, ConnectionInfo connInfo,
			DBSession session, String databaseName, String schemaName,
			String tableName) {
		super(frame, modal);
		executer = DBMSqlExecuterFactory.createExecuter(connInfo, session);
		setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE );
		
		this.setTitle("Create Database");
//		this.setIconImage(frameIcon.getImage());
		
		this.setResizable(false);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((dimension.width - 560) / 2, (dimension.height - 400) / 2, 560, 400);
		
		final JTabbedPane innerTabbedPane = new JTabbedPane();
		final JPanel innerPanel = new JPanel();
		final JPanel buttonPanel = new JPanel();
		
		innerTabbedPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		innerPanel.setLayout(null);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		
		final JLabel nameLabel = new JLabel();
		nameLabel.setText("Name:");
		nameLabel.setBounds(30, 30, 60, 20);
		innerPanel.add(nameLabel);
		
		databaseNameTextField = new JTextField();
		databaseNameTextField.setBounds(100, 30, 300, 20);
		innerPanel.add(databaseNameTextField);
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String name = databaseNameTextField.getText();
				if (name == null || name.trim().length()<1) {
					DBMMessageDialog.showErrorMessageDialog(ResourceI18n.getText("CREATE_DATABASE_NO_NAME"));
					return;
				}
				String sql = "create database " + name;
				try {
					executer.executeUpdate(sql);
					ok = true;
					dispose();
				} catch (DBMException e) {
					DBMMessageDialog.showErrorMessageDialog(e.getMessage());
				}
			}
		});
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				CreateDatabaseDialog.this.dispose();
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		innerTabbedPane.addTab("General", panelIcon, innerPanel);
		this.add(innerTabbedPane, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		addWindowListener(new DBMWindowListener(connInfo, session));
	}
	public boolean isOk() {
		return ok;
	}
}


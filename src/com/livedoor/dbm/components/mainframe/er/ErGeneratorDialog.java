/**
 * 創建期日 2006-09-22
 */
package com.livedoor.dbm.components.mainframe.er;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.livedoor.dbm.components.common.DBMCommonDialog;
import com.livedoor.dbm.components.common.DBMMessageDialog;
import com.livedoor.dbm.components.common.DBMWindowListener;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.components.tree.DBMRootNode;
import com.livedoor.dbm.components.tree.DBMTreeNode;
import com.livedoor.dbm.components.tree.DBMTreePaneView;
import com.livedoor.dbm.components.tree.db2.DB2SchemaNode;
import com.livedoor.dbm.components.tree.mysql.MySqlDatabaseNode;
import com.livedoor.dbm.components.tree.oracle.OracleSchemaNode;
import com.livedoor.dbm.connection.ConnectionInfo;
import com.livedoor.dbm.constants.DBServerType;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBMDataResult;
import com.livedoor.dbm.db.DBMSqlExecuterFactory;
import com.livedoor.dbm.db.DBSession;
import com.livedoor.dbm.util.DBMSchemaTreeInit;
/**
 * <p>
 * Title: DbManager
 * </p>
 * <p>
 * Description: ER图向导对话框.
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
public class ErGeneratorDialog extends DBMCommonDialog {

	private static final long serialVersionUID = -8307489120728500728L;

	private static final String titles[] = {"General", "Status"};

	private JPanel panels[];

	private JTabbedPane tab;

	private JButton btnNext;

	private JButton btnPrevious;

	private JButton btnClose;

	private DBSession dbSession;

	private ErTabbedPane erTab;

	private ErFrame frame;

	private int count;

	boolean isTreadRun = false;

	private Map ForeignKeysMap = null;

	private List listEntity = null;

	/**
	 * Description: 构造器.
	 * 
	 * @param frame:DBMFrame
	 * @param conn:ConnectionInfo
	 * @param dbSession:DBSession
	 */
	public ErGeneratorDialog(DBMFrame frame, ConnectionInfo conn, DBSession dbSession) {
		super(frame, "ER Diagram Generator - [" + conn.getConnectionName() + "]", 720, 480, conn);
		this.dbSession = dbSession;
		ForeignKeysMap = new HashMap();
		erTab = new ErTabbedPane(getConnectionInfo(), dbSession);
		addWindowListener(new DBMWindowListener(getConnectionInfo(), dbSession));
		initSchemaDabasValues(frame);
		initComponents();
		setModal(false);
	}

	@Override
	public List getButtonList() {
		List buttonList = new ArrayList();
		buttonList.add(btnClose);
		buttonList.add(btnPrevious);
		buttonList.add(btnNext);
		return buttonList;
	}

	@Override
	public JTabbedPane getTab() {
		return erTab;
	}

	/**
	 * Description: 初使化ER图向导对话框.
	 */
	public void initComponents() {

		btnNext = new JButton("Next");

		btnPrevious = new JButton("Previous");

		btnClose = new JButton("Close");
		erTab.setEnabledAt(1, false);

		btnPrevious.setEnabled(false);

		btnNext.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Vector vector = erTab.getSelectTables();
				count = vector.size();

				erTab.getJProgressBar_progressBar().setMaximum(count);
				erTab.getJProgressBar_progressBar().setValue(0);

				btnNext.setEnabled(false);
				erTab.setSelectedIndex(1);
				erTab.setEnabledAt(0, false);
				erTab.setEnabledAt(1, true);
				isTreadRun = true;
				btnPrevious.setEnabled(true);
				btnClose.setEnabled(false);
				Thread thread = new Thread(new Runnable() {

					public void run() {
						onGenerate();
					}

				});
				thread.start();

			}

		});

		btnPrevious.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				isTreadRun = false;
				btnPrevious.setEnabled(false);
				btnClose.setEnabled(true);
				btnNext.setEnabled(true);
			}

		});

		btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}

		});

		super.initComponents();

	}

	/**
	 * Description: 初使化模式/数据库组件.
	 * 
	 * @param frame:DBMFrame
	 */
	private void initSchemaDabasValues(DBMFrame frame) {
		JPanel treeView = frame.getTreePanel();
		DBMTreeNode node = (DBMTreeNode) ((DBMTreePaneView) treeView).getDBMTree().getLastSelectedPathComponent();

		while (true) {
			if (node instanceof MySqlDatabaseNode || node instanceof DB2SchemaNode || node instanceof OracleSchemaNode
					|| node instanceof DBMRootNode) {
				break;
			}
			node = (DBMTreeNode) node.getParent();
		}

		if (node instanceof MySqlDatabaseNode) {
			erTab.getDatabaseComboBox().setSelectedItem(node.getId());
		} else if (node instanceof DB2SchemaNode || node instanceof OracleSchemaNode) {
			erTab.getSchemaeComboBox().setSelectedItem(node.getId());
		}
	}
	/**
	 * 启动线程,生成ER图.
	 */
	private void onGenerate() {
		Vector vector = erTab.getSelectTables();
		listEntity = new ArrayList();
		for (int i = 0; i < vector.size(); i++) {
			if (isTreadRun == true) {
				try {
					listEntity.add(creatEntity((String) vector.get(i)));

				} catch (Exception e) {
					DBMMessageDialog.showErrorMessageDialog(e.getMessage());
					isTreadRun = false;
				}
			} else {
				break;
			}

			erTab.getJProgressBar_progressBar().setValue(i);

			erTab.getJLabel_StatusValue().setText(String.valueOf(i + 1) + " / " + count);
		}

		if (isTreadRun == true) {
			dispose();

			frame = new ErFrame(listEntity, getForeignKeysRelation());

			frame.show();

		} else {

			erTab.setSelectedIndex(0);
			erTab.setEnabledAt(1, false);
			erTab.setEnabledAt(0, true);
			btnNext.setEnabled(true);
			btnClose.setEnabled(true);
			btnPrevious.setEnabled(false);
		}
	}
	/**
	 * [功 能] 通过外键关系.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * <p>
	 * 
	 * @return List
	 *         <p>
	 */
	private List getForeignKeysRelation() {
		List relationList = new ArrayList();

		Iterator iterator = ForeignKeysMap.keySet().iterator();

		while (iterator.hasNext()) {
			String fkName = (String) iterator.next();
			List list = (List) ForeignKeysMap.get(fkName);
			relationList.add(new Relationship(getEntityByTableName((String) list.get(0)), getEntityByTableName((String) list.get(1))));
		}

		return relationList;
	}
	/**
	 * [功 能] 通过表名得到一个实体对象.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param tableName
	 *            表名.
	 *            <p>
	 * 
	 * @return Entity
	 *         <p>
	 */
	private Entity getEntityByTableName(String tableName) {
		Entity entity = null;
		for (int i = 0; i < listEntity.size(); i++) {
			entity = (Entity) listEntity.get(i);
			if (tableName.equals(entity.getName())) {
				break;
			}
		}

		return entity;
	}

	/**
	 * [功 能] 创建一个实体对象.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param tableName
	 *            表名.
	 *            <p>
	 * 
	 * @return Entity
	 * @throws SQLException
	 *             <p>
	 */
	private Entity creatEntity(String tableName) throws Exception {

		Entity item = new Entity(tableName);

		DBMDataResult priResult = null;
		// DBMDataResult colResult = null;
		DBMDataResult forResult = null;

		DBMDataMetaInfo dbMetaInfo = new DBMDataMetaInfo();
		dbMetaInfo.setSchemaName((String) erTab.getSchemaeComboBox().getSelectedItem());
		dbMetaInfo.setDatabaseName((String) erTab.getDatabaseComboBox().getSelectedItem());
		dbMetaInfo.setTableName(tableName);
		dbMetaInfo.setColumnName("%");
		try {
			priResult = erTab.getExecute().getPrimaryKeys(dbMetaInfo);

			forResult = erTab.getExecute().getForeignKeys(dbMetaInfo);

		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		}

		List priList = priResult.getData();
		Map priMap = new HashMap();
		for (int i = 0; i < priList.size(); i++) {
			Map map = (Map) priList.get(i);
			priMap.put((String) map.get("COLUMN_NAME"), (String) map.get("COLUMN_NAME"));
		}

		List forList = forResult.getData();
		for (int i = 0; i < forList.size(); i++) {

			Map map = (Map) forList.get(i);

			if (ForeignKeysMap.containsKey(map.get("FK_NAME"))) {
				continue;
			} else {
				List list = new ArrayList();
				list.add(map.get("FKTABLE_NAME"));
				list.add(map.get("PKTABLE_NAME"));
				ForeignKeysMap.put(map.get("FK_NAME"), list);
			}

		}

		List columnInfoList = getColunmInfo(tableName, (String) erTab.getDatabaseComboBox().getSelectedItem(), (String) erTab
				.getSchemaeComboBox().getSelectedItem(), getConnectionInfo());

		for (int i = 0; i < columnInfoList.size(); i++) {

			String columnInfo = (String) columnInfoList.get(i);
			if (priMap.containsKey(columnInfo.substring(0, columnInfo.indexOf(": ("))))
				item.addPk(columnInfo);

		}

		for (int i = 0; i < columnInfoList.size(); i++) {

			String columnInfo = (String) columnInfoList.get(i);
			if (!priMap.containsKey(columnInfo.substring(0, columnInfo.indexOf(": ("))))
				item.addField(columnInfo);

		}

		return item;
	}
	/**
	 * [功 能] 得到列信息.
	 * <p>
	 * [作成日期] 2006/09/18
	 * <p>
	 * 
	 * @param tableName
	 *            表名.
	 * @param database
	 *            数据库名.
	 * @param schema
	 *            模式名.
	 * @param conn
	 *            连接信息.
	 *            <p>
	 * 
	 * @return Entity
	 * @throws Exception
	 * @throws SQLException
	 *             <p>
	 */
	private List getColunmInfo(String tableName, String database, String schema, ConnectionInfo conn) throws Exception {
		List list = new ArrayList();

		String sql = "";
		char c = '`';
		if (conn.getDbType().equals(DBServerType.MYSQL)) {
			sql = "SHOW COLUMNS FROM " + c + tableName + c + " FROM " + c + database + c;
		} else if (conn.getDbType().equals(DBServerType.DB2)) {
			sql = "SELECT COLNAME, TYPENAME, LENGTH, SCALE ";
			sql = sql + "from SYSCAT.COLUMNS ";
			sql = sql + "where TABNAME = '" + tableName + "' AND TABSCHEMA = '" + schema + "' ";
			sql = sql + "order by COLNO";
		} else {
			sql = "select COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, DATA_SCALE, NULLABLE, DATA_DEFAULT from SYS.ALL_TAB_COLUMNS WHERE TABLE_NAME = '"
					+ tableName + "' AND OWNER = '" + schema + "' ORDER BY COLUMN_ID";
		}

		ResultSet rs = null;
		Statement statement = null;
		try {
			// rs = DBMSqlExecuterFactory.createExecuter(conn,
			// this.dbSession).executeQueryRes(sql);
			rs = DBMSqlExecuterFactory.createExecuter(conn, this.dbSession).executeQueryRes(sql);
			statement = rs.getStatement();
			if (rs != null) {

				do {

					if (!rs.next())
						break;

					if (conn.getDbType().equals(DBServerType.MYSQL)) {

						String s = "";
						String s1 = "";
						String s3 = "";
						String s4 = "";
						String s5 = "";
						String s6 = "";
						try {
							s = rs.getString("Field");
							String s2 = rs.getString("Type");
							s3 = DBMSchemaTreeInit.getMySQLDataType(s2);
							s4 = DBMSchemaTreeInit.getMySQLLength(s2);
							s5 = DBMSchemaTreeInit.getMySQLPrecision(s2);
							s6 = DBMSchemaTreeInit.getMySQLScale(s2);
						} catch (Exception exception) {
							s = null;
							throw exception;
						}
						if (s == null) {
							return null;
						} else {
							String s7 = DBMSchemaTreeInit.getMysqlDataTypeDefinitionString(s3, s4, s5, s6);
							list.add(s + ": (" + s3 + s7 + ")");
						}

					} else if (conn.getDbType().equals(DBServerType.DB2)) {

						String s = "";
						String s1 = "";
						String s2 = "";
						String s3 = "";
						String s4 = "";
						try {
							s = rs.getString(1);
							s1 = rs.getString(2);
							s2 = rs.getString(3);
							s4 = rs.getString(4);
						} catch (Exception exception) {
							s = null;
							throw exception;
						}
						if (s == null)
							return null;
						if (s1.equalsIgnoreCase("DECIMAL")) {
							s3 = s2;
							s2 = "";
						}
						String s5 = DBMSchemaTreeInit.getDb2DataTypeDefinitionString(s1, s2, s3, s4);

						list.add(s + ": (" + s1 + s5 + ")");

					} else {

						String s = "";
						String s1 = "";
						String s2 = "";
						String s3 = "";
						String s4 = "";
						try {
							s = rs.getString(1);
							s1 = rs.getString(2);
							s2 = rs.getString(3);
							s3 = rs.getString(4);
							s4 = rs.getString(5);
						} catch (Exception exception) {
							s = null;
							s1 = null;
							s2 = null;
							throw exception;
						}
						if (s1 != null && s1.equalsIgnoreCase("NUMBER")) {
							if (s3 == null)
								s3 = "";
							if (s4 == null)
								s4 = "";
							if (s3.length() == 0)
								s3 = s2;
							if (s4.length() == 0)
								s4 = "0";
						}
						if (s == null) {
							return null;
						} else {

							String s5 = DBMSchemaTreeInit.getOracleDataTypeDefinitionString(s1, s2, s3, s4);

							list.add(s + ": (" + s1 + s5 + ")");
						}

					}

				} while (true);

			}
		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		} finally {
			statement.close();
			rs.close();
		}

		return list;
	}

}

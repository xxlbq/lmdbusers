/**
 * 創建期日 2006-09-22
 */
package com.livedoor.dbm.components.mainframe.er;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.livedoor.dbm.i18n.ResourceI18n;

/**
 * <p>
 * Title: DbManager
 * </p>
 * <p>
 * Description: ER实体类
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
public class DiagramEntity extends JInternalFrame {

	private static final long serialVersionUID = -1659171084383316411L;

	private JPanel fields;

	public String tableName;

	/**
	 * 
	 * Description: 构造器.
	 * 
	 * @param tabelName:String
	 *            <p>
	 * 
	 */
	public DiagramEntity(String tabelName) {

		super(tabelName, false, true);
		this.setFrameIcon(ResourceI18n.getImage("MAIN_FRAME"));
		this.tableName = tabelName;

		setLayer(JLayeredPane.PALETTE_LAYER);
		putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
		setOpaque(true);

		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(new JLabel(tabelName), BorderLayout.NORTH);
		content.add(fields = new JPanel(new GridLayout(0, 1, 0, 0)), BorderLayout.CENTER);

		getContentPane().add(content);
	}
	/**
	 * 
	 * Description: ER实体中的一个条目.
	 * <p>
	 * 
	 * @param label:String
	 *            <p>
	 * @param key:boolean
	 *            <p>
	 * 
	 */
	public void addField(String label, boolean key) {

		JCheckBox field = new JCheckBox(label, key);
		field.setBackground(new Color(255, 255, 205));
		field.setEnabled(false);
		field.setBorder(null);
		fields.add(field);

	}

}

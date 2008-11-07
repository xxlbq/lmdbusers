/**
 * 創建期日 2006-09-22
 */
package com.livedoor.dbm.components.mainframe.er;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import com.livedoor.dbm.i18n.ResourceI18n;
/**
 * <p>
 * Title: DbManager
 * </p>
 * <p>
 * Description: 装载ER图的窗体
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
public class ErFrame extends JFrame {

	private static final long serialVersionUID = 4279829252170867770L;

	public static final String TITLE = "ER-DIAGRAM";

	private JScrollPane scrollPane;

	private List entitys;

	private List relationList;

	private DrawBoard drawBoard = null;

	/**
	 * 
	 * Description: 构造器.
	 * <p>
	 * 
	 * @param execute:DBMSqlExecuter
	 *            <p>
	 * @param schema:String
	 *            <p>
	 * @param database:String
	 *            <p>
	 * 
	 */
	public ErFrame(List entitys, List relationList) {
		super(TITLE);
		setIconImage(ResourceI18n.getImage("MAIN_FRAME").getImage());
		this.entitys = entitys;
		this.relationList = relationList;
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getMainPanel(), BorderLayout.CENTER);
		byte byte0 = 10;
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		if (dimension.width > 925) {
			setBounds(byte0, byte0, dimension.width - 50, dimension.height - 50);
		} else {
			setBounds(byte0, byte0, dimension.width - byte0 * 2 - 50, dimension.height - byte0 * 2 - 100);
		}
		setVisible(true);
	}
	/**
	 * 
	 * Description: 得到主面板.
	 * <p>
	 * 
	 * @return JScrollPane
	 *         <p>
	 * 
	 */
	private JScrollPane getMainPanel() {

		drawBoard = new DrawBoard(entitys, relationList);

		scrollPane = new JScrollPane(drawBoard);

		return scrollPane;
	}

}

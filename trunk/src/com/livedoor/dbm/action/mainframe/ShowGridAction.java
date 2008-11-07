/**
 * $Id: ShowGridAction.java,v 1.4 2006/10/25 08:23:11 lijc Exp $
 * actions
 */
package com.livedoor.dbm.action.mainframe;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.livedoor.dbm.components.mainframe.DBMFrame;

/**
 * <p>Title: 显示表格结果</p> 
 * <p>Description: 显示表格结果</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public class ShowGridAction extends DBMBaseShowAction {
    private static final String ACTION_NAME = "SHOW_GRID";

    public ShowGridAction() {
        super(ACTION_NAME);
    }

    @Override
    public void processAction(DBMFrame frame, ActionEvent actionEvent) {
        bShowGrid = !bShowGrid;
        
        JMenuBar jMunuBar = frame.getJMenuBar();
        JMenu queryMenu = jMunuBar.getMenu(3);
        JCheckBoxMenuItem showGridItem = (JCheckBoxMenuItem)queryMenu.getMenuComponent(10);
        showGridItem.setSelected(bShowGrid);
        
        JToolBar jToolBar = frame.getJToolBar();
        JToggleButton showGridButton = (JToggleButton)jToolBar.getComponent(11);
        showGridButton.setSelected(bShowGrid);
    }

}

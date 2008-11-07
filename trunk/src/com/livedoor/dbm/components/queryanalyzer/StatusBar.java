/**
 * $Id: StatusBar.java,v 1.2 2006/10/28 08:35:39 lijc Exp $
 * 查询分析器相关
 */
package com.livedoor.dbm.components.queryanalyzer;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>Title: 状态栏</p> 
 * <p>Description: 脚本执行状态栏</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class StatusBar extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private List<Cell> cells = new ArrayList<Cell>();
    
    public final static String POSITION = "position";
    public final static String TYPING_MODE = "typing-mode";
    public final static String MAIN = "main";
    
    public StatusBar() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
//        addCell(POSITION, "       ");
//        addCell(TYPING_MODE, "   ");
        addCell(MAIN, "");
        setVisible(false);
    }

    public void addCell(String name, String text) {
        Cell cell = new Cell(name, text);
        add(cell);
        cells.add(cell);
    }
    
    public void setText(String name, String text) {
        Cell cell = getCellByName(name);
        cell.setText(text);
    }
    
    private Cell getCellByName(String name) {
        for(Cell cell : cells) {
            if(name.endsWith(cell.getName()))
                return cell;
        }
        return null;
    }
    
    public int getCellCount() {
        return cells.size();
    }

    static class Cell extends JLabel {
        private static final long serialVersionUID = 1L;

        Cell(String name, String text) {
            setName(name);
            setText(text);
        }
    }

}

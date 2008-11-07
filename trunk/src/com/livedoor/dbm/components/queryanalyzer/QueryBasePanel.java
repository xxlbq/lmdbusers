/**
 * $Id: QueryBasePanel.java,v 1.1 2006/10/17 01:10:35 lijian Exp $
 * 查询分析器基类
 */
package com.livedoor.dbm.components.queryanalyzer;

import javax.swing.Icon;
import javax.swing.JPanel;

/**
 * <p>Title: 查询分析器基类</p> 
 * <p>Description:
 * 		定义查询分析器显示在主窗口标签中需要标题和图标
 * </p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijian@livedoor.cn">Jian Li </a>
 * @version 1.0
 */
public abstract class QueryBasePanel extends JPanel {
	
	/**
	 * 查询分析器标题
	 * @return
	 */
    public abstract String getTitle();

    /**
     * 查询分析器图标 
     * @return
     */
    public abstract Icon getIcon();

}


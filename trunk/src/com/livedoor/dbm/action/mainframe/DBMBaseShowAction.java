/**
 * $Id: DBMBaseShowAction.java,v 1.4 2006/10/28 03:41:49 lijc Exp $
 */
package com.livedoor.dbm.action.mainframe;

import com.livedoor.dbm.action.DBMBaseAction;

/**
 * <p>Title: DBMBaseShowAction</p> 
 * <p>Description: 和DBMBaseAction一样,只是增加了两个布尔变量</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 *  
 * @author <a href="mailto:lijc@livedoor.cn">lijicheng</a>
 * @version 1.0
 */
public abstract class DBMBaseShowAction extends DBMBaseAction {

    /**
     * 是否显示文本结果
     */
    public static boolean bShowText = false;
    
    /**
     * 是否显示表格结果
     */
    public static boolean bShowGrid = true;
    
    public DBMBaseShowAction(String actionName) {
        super(actionName);
    }

}

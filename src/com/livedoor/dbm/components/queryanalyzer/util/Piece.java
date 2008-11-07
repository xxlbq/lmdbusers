/**
 * $Id: Piece.java,v 1.1 2006/10/17 01:10:37 lijian Exp $ 
 * SQL格式化
 */
package com.livedoor.dbm.components.queryanalyzer.util;

/**
 * <p> Title: SQL片断 </p> 
 * <p> Description: SQL片断 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class Piece {
    // SQL片断规则
    public PieceMarkerSpec spec;

    // SQL片断在SQL脚本中的开始位置
    public int beginsAt;
}

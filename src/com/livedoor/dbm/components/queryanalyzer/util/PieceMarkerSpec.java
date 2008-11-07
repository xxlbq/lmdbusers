/**
 * $Id: PieceMarkerSpec.java,v 1.1 2006/10/17 01:10:37 lijian Exp $ 
 * SQL格式化
 */
package com.livedoor.dbm.components.queryanalyzer.util;

/**
 * <p> Title: SQL脚本拆分规则 </p> 
 * <p> Description: SQL脚本拆分规则 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class PieceMarkerSpec {
    /**
     * 记号在拆分后SQL片断的开头
     */
    public static final int TYPE_PIECE_MARKER_AT_BEGIN = 0;

    /**
     * 记号在拆分后SQL片断的结尾
     */
    public static final int TYPE_PIECE_MARKER_AT_END = 1;

    /**
     * 记号做为单独SQL片断
     */
    public static final int TYPE_PIECE_MARKER_IN_OWN_PIECE = 2;

    /**
     * 拆分记号
     */
    public String _pieceMarker;

    /**
     * 拆分规则类型
     */
    public int _type;

    PieceMarkerSpec(String pieceMarker, int type) {
        this._pieceMarker = pieceMarker;

        if (TYPE_PIECE_MARKER_AT_BEGIN != type && 
                TYPE_PIECE_MARKER_AT_END != type && 
                TYPE_PIECE_MARKER_IN_OWN_PIECE != type) {
            throw new IllegalArgumentException("Unknow type: " + type);
        }

        this._type = type;
    }

    /**
     * 返回拆分记号 
     * @return
     */
    public String getPieceMarker() {
        return _pieceMarker;
    }

    /**
     * 拆分类型
     * @return
     */
    public int getType() {
        return _type;
    }

    /**
     * 在右边加空格后的长度
     * @return
     */
    public int getLengthRightSpaced() {
        if (1 == _pieceMarker.length()) {
            return _pieceMarker.length();
        } else {
            return _pieceMarker.length() + 1;
        }
    }

    /**
     * 取左边空格
     * @return 如果拆分记号长度为1,返回"";否则返回" "
     */
    public String getLeftSpace() {
        if (1 == _pieceMarker.length()) {
            return "";
        } else {
            return " ";
        }
    }

    /**
     * 是否需要在前后加空格
     * @return 如果拆分记号长度为1,返回false;否则返回true
     */
    public boolean needsSuroundingWhiteSpaces() {
        if (1 == _pieceMarker.length()) {
            return false;
        } else {
            return true;
        }
    }

}

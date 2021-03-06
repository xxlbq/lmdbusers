/**
 * $Id: CodeReformatorKernel.java,v 1.1 2006/10/17 01:10:37 lijian Exp $ 
 * SQL格式化
 */
package com.livedoor.dbm.components.queryanalyzer.util;

/**
 * <p> Title: SQL拆分 </p> 
 * <p> Description: 根据拆分规则拆分SQL脚本 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
import java.util.Vector;

public class CodeReformatorKernel {

    // SQL语句分隔符
    private String _statementSeparator;

    // 注释规则
    private CommentSpec[] _commentSpecs;

    // 拆分规则
    private PieceMarkerSpec[] _pieceSpecs;

    // SQL脚本字符位置状态
    private StateOfPosition[] _statesOfPosition;

    public CodeReformatorKernel(String statementSeparator, PieceMarkerSpec[] pieceSpecs, CommentSpec[] commentSpecs) {
        _statementSeparator = statementSeparator;
        _commentSpecs = commentSpecs;
        _pieceSpecs = pieceSpecs;
    }

    /**
     * 拆分SQL脚本
     * 
     * @param in
     * @return
     */
    public String[] toPieces(String in) {
        _statesOfPosition = getStatesOfPosition(in);

        Vector<String> ret = new Vector<String>();

        // toUpperCase replaces the German ß by ss.
        // This will kill reformating later.
        // Since upperIn is just for building pieces
        // it is OK to place ß here.
        String upperIn = in.replaceAll("ß", "s");
        upperIn = upperIn.toUpperCase();

        int begin = 0;
        while (begin < in.length()) {
            Piece p = getNextToplevelPiece(begin, upperIn);

            if (null == p.spec) {
                ret.add(in.substring(begin).trim());
                begin = in.length();
            } else {

                int type = p.spec.getType();
                switch (type) {
                    case PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN :
                        if (begin < p.beginsAt && 0 < in.substring(begin, p.beginsAt).trim().length()) {
                            ret.add(in.substring(begin, p.beginsAt).trim());
                        }

                        int afterPieceMarker = p.beginsAt + p.spec.getLengthRightSpaced();
                        Piece nextP = getNextToplevelPiece(afterPieceMarker, upperIn);
                        if (null == nextP.spec) {
                            ret.add(in.substring(p.beginsAt).trim());
                            begin = in.length();
                        } else {
                            if (PieceMarkerSpec.TYPE_PIECE_MARKER_AT_END == nextP.spec.getType()) {
                                if (nextP.beginsAt + nextP.spec.getLengthRightSpaced() < in.length()) {
                                    ret.add(in.substring(p.beginsAt, nextP.beginsAt + nextP.spec.getLengthRightSpaced()).trim());
                                } else {
                                    ret.add(in.substring(p.beginsAt).trim());
                                }
                                begin = nextP.beginsAt + nextP.spec.getLengthRightSpaced();
                            } else {
                                ret.add(in.substring(p.beginsAt, nextP.beginsAt).trim());
                                begin = nextP.beginsAt;
                            }
                        }
                        break;
                    case PieceMarkerSpec.TYPE_PIECE_MARKER_AT_END :
                        if (p.beginsAt + p.spec.getLengthRightSpaced() < in.length()) {
                            ret.add(in.substring(begin, p.beginsAt + p.spec.getLengthRightSpaced()).trim());
                        } else {
                            ret.add(in.substring(begin).trim());
                        }
                        begin = p.beginsAt + p.spec.getLengthRightSpaced();
                        break;
                    case PieceMarkerSpec.TYPE_PIECE_MARKER_IN_OWN_PIECE :
                        if (begin < p.beginsAt && 0 < in.substring(begin, p.beginsAt).trim().length()) {
                            ret.add(in.substring(begin, p.beginsAt).trim());
                        }
                        if (p.beginsAt + p.spec.getLengthRightSpaced() < in.length()) {
                            ret.add(in.substring(p.beginsAt, p.beginsAt + p.spec.getLengthRightSpaced()).trim());
                        } else {
                            ret.add(in.substring(p.beginsAt).trim());
                        }
                        begin = p.beginsAt + p.spec.getLengthRightSpaced();
                        break;
                }
            }
        }
        return (String[]) ret.toArray(new String[0]);
    }

    private Piece getNextToplevelPiece(int startAt, String in) {
        Piece ret = new Piece();
        ret.beginsAt = in.length();

        for (int i = 0; i < _pieceSpecs.length; ++i) {
            int buf = getTopLevelIndex(startAt, in, _pieceSpecs[i]);
            if (-1 < buf && buf < ret.beginsAt) {
                ret.spec = _pieceSpecs[i];
                ret.beginsAt = buf;
            }
        }
        if (null == ret.spec) {
            ret.beginsAt = startAt;
        }

        return ret;
    }

    private int getTopLevelIndex(int startAt, String in, PieceMarkerSpec pieceSpec) {
        int ix = in.indexOf(pieceSpec.getPieceMarker(), startAt);

        while (-1 != ix) {
            if (_statesOfPosition[ix].isTopLevel) {
                if (pieceSpec.needsSuroundingWhiteSpaces()) {
                    char before = (0 == ix ? ' ' : in.charAt(ix - 1));

                    int pieceMarkerEnd = ix + pieceSpec.getPieceMarker().length() - 1;
                    char after = (pieceMarkerEnd == in.length() - 1 ? ' ' : in.charAt(pieceMarkerEnd + 1));

                    if (Character.isWhitespace(before) && Character.isWhitespace(after)) {
                        return ix;
                    }
                } else {
                    return ix;
                }
            }
            ix = in.indexOf(pieceSpec.getPieceMarker(), ix + 1);
        }
        return -1;
    }

    /**
     * 获取SQL脚本中每个字符的位置状态
     * 
     * @param in
     * @return
     */
    public StateOfPosition[] getStatesOfPosition(String in) {
        StateOfPosition[] ret = new StateOfPosition[in.length()];

        StateOfPosition buf = new StateOfPosition();
        for (int i = 0; i < in.length(); ++i) {
            if ('\'' == in.charAt(i)) {
                ++buf.literalSepCount;
            }

            if (0 == buf.literalSepCount % 2) {
                for (int j = 0; j < _commentSpecs.length; ++j) {
                    if (in.substring(i).startsWith(_commentSpecs[j].commentBegin)) {
                        if (-1 == buf.commentIndex) {
                            buf.commentIndex = j;
                        }
                    }
                    if (in.substring(i).startsWith(_commentSpecs[j].commentEnd)) {
                        if (j == buf.commentIndex) {
                            buf.commentIndex = -1;
                        }
                    }
                }
            }

            if (0 == buf.literalSepCount % 2 && -1 == buf.commentIndex) {
                if ('(' == in.charAt(i)) {
                    ++buf.braketDepth;
                }
                if (')' == in.charAt(i)) {
                    --buf.braketDepth;
                }
            }

            if (-1 == buf.commentIndex && 0 == buf.literalSepCount % 2 && 0 == buf.braketDepth) {
                buf.isTopLevel = true;
            } else {
                buf.isTopLevel = false;
            }
            ret[i] = (StateOfPosition) buf.clone();
        }

        return ret;
    }
}

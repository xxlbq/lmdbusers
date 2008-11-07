/**
 * $Id: CodeReformator.java,v 1.1 2006/10/17 01:10:37 lijian Exp $ 
 * SQL格式化
 */
package com.livedoor.dbm.components.queryanalyzer.util;

import java.util.Arrays;
import java.util.Vector;

/**
 * <p> Title: SQL格式化 </p> 
 * <p> Description: 格式化SQL </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class CodeReformator {

    // 缩进用文本
    private static final String INDENT = "    ";

    // 自动换行字符数
    private static final int TRY_SPLIT_LINE_LEN = 0;

    // SQL语句分割符
    private String _statementSeparator;

    // SQL注释规则
    private CommentSpec[] _commentSpecs;

    public CodeReformator(String statementSeparator, CommentSpec[] commentSpecs) {
        _statementSeparator = statementSeparator;
        _commentSpecs = commentSpecs;
    }

    /**
     * 格式化
     * 
     * @param in 待格式化语句
     * @return
     */
    public String reformat(String in) {
        in = flatenWhiteSpaces(in, false);

        // 构造不包括逗号的拆分规则
        PieceMarkerSpec[] markerExcludeComma = createPieceMarkerSpecExcludeColon();

        // 得到拆分后的SQL片断
        String[] pieces = getRefomatedPieces(in, markerExcludeComma);

        // 对Insert语句特殊处理
        pieces = doInsertSpecial(pieces);

        // 对括号SQL片断做缩进处理
        StringBuffer ret = new StringBuffer();
        int braketCount = 0;
        for (int i = 0; i < pieces.length; ++i) {
            if (")".equals(pieces[i])) {
                --braketCount;
            }
            ret.append(indent(pieces[i], braketCount)).append('\n');
            if ("(".equals(pieces[i])) {
                ++braketCount;
            }
        }

        // 校验格式化是否有效
        validate(in, ret.toString());

        return ret.toString();
    }

    /**
     * 校验格式化后的SQL语句
     * 
     * @param beforeReformat
     * @param afterReformat
     */
    private void validate(String beforeReformat, String afterReformat) {
        String normalizedBefore = getNormalized(beforeReformat);
        String normalizedAfter = getNormalized(afterReformat);

        if (!normalizedBefore.equalsIgnoreCase(normalizedAfter)) {
            int minLen = Math.min(normalizedAfter.length(), normalizedBefore.length());
            StringBuffer diffPos = new StringBuffer();
            for (int i = 0; i < minLen; ++i) {
                if (Character.toUpperCase(normalizedBefore.charAt(i)) != Character.toUpperCase(normalizedAfter.charAt(i))) {
                    break;
                }
                diffPos.append('-');
            }
            diffPos.append('^');

            String msg = "format failed";
            throw new IllegalStateException(msg);
        }
    }

    /**
     * SQL语句正常化处理 格式化前后校验用到, <p> 格式化前后的SQL语句经过处理后应该相等
     */
    private String getNormalized(String s) {
        String ret = s.replaceAll("\\(", " ( ");
        ret = ret.replaceAll("\\)", " ) ");
        ret = ret.replaceAll(",", " , ");
        ret = ret.replaceAll(_statementSeparator, " " + _statementSeparator + " ");
        return flatenWhiteSpaces(ret, true).trim();
    }

    /**
     * 拆分SQL语句
     * 
     * @param in 待拆分SQL语句
     * @param markers 拆分规则
     * @return
     */
    private String[] getRefomatedPieces(String in, PieceMarkerSpec[] markers) {

        CodeReformatorKernel kernel = new CodeReformatorKernel(_statementSeparator, markers, _commentSpecs);

        // 根据拆分规则拆分SQL,得到拆分后SQL片断
        String[] pieces = kernel.toPieces(in);

        // 根据字符换行字符数进一步拆分
        Vector<String> piecesBuf = new Vector<String>();
        for (int i = 0; i < pieces.length; ++i) {
            if (TRY_SPLIT_LINE_LEN < pieces[i].length()) {
                String[] splitPieces = trySplit(pieces[i], 0, TRY_SPLIT_LINE_LEN);
                piecesBuf.addAll(Arrays.asList(splitPieces));
            } else {
                piecesBuf.add(pieces[i]);
            }
        }
        return (String[]) piecesBuf.toArray(new String[0]);
    }

    /**
     * 对插入SQL语句特殊处理
     * 
     * @param pieces
     * @return
     */
    private String[] doInsertSpecial(String[] pieces) {
        int insertBegin = -1;
        boolean hasValues = false;

        Vector<String> ret = new Vector<String>();
        Vector<String> insertPieces = new Vector<String>();

        for (int i = 0; i < pieces.length; ++i) {
            if ("INSERT ".length() <= pieces[i].length() && pieces[i].substring(0, "INSERT ".length()).equalsIgnoreCase("INSERT ")) {
                if (-1 != insertBegin) {
                    return pieces;
                }
                insertBegin = i;
            }

            if (-1 == insertBegin) {
                ret.add(pieces[i]);
            } else {
                insertPieces.add(pieces[i]);
            }

            if (-1 < insertBegin && -1 != pieces[i].toUpperCase().indexOf("VALUES")) {
                hasValues = true;
            }

            if (-1 < insertBegin && _statementSeparator.equalsIgnoreCase(pieces[i])) {
                if (hasValues) {
                    ret.addAll(reformatInsert(insertPieces));
                } else {
                    ret.addAll(insertPieces);
                }

                insertBegin = -1;
                hasValues = false;
                insertPieces = new Vector<String>();
            }
        }

        if (-1 < insertBegin) {
            if (hasValues) {
                ret.addAll(reformatInsert(insertPieces));
            } else {
                ret.addAll(insertPieces);
            }
        }

        return (String[]) ret.toArray(new String[0]);
    }

    /**
     * 对插入语句格式化
     * 
     * @param piecesIn
     * @return
     */
    private Vector<String> reformatInsert(Vector<String> piecesIn) {
        String[] pieces = splitAsFarAsPossible((String[]) piecesIn.toArray(new String[piecesIn.size()]));

        Vector<String> insertList = new Vector<String>();
        Vector<String> valuesList = new Vector<String>();
        Vector<String> behindInsert = new Vector<String>();

        StringBuffer statementBegin = new StringBuffer();
        int braketCountAbsolute = 0;
        for (int i = 0; i < pieces.length; ++i) {
            if (3 < braketCountAbsolute) {
                behindInsert.add(pieces[i]);
            }
            if ("(".equals(pieces[i]) || ")".equals(pieces[i])) {
                ++braketCountAbsolute;
            }

            if (0 == braketCountAbsolute) {
                statementBegin.append(pieces[i]).append(' ');
            }
            if (1 == braketCountAbsolute && !"(".equals(pieces[i]) && !")".equals(pieces[i])) {
                String buf = pieces[i].trim();
                if (buf.endsWith(",")) {
                    buf = buf.substring(0, buf.length() - 1);
                }
                insertList.add(buf);
            }
            if (3 == braketCountAbsolute && !"(".equals(pieces[i]) && !")".equals(pieces[i])) {
                String buf = pieces[i].trim();
                if (buf.endsWith(",")) {
                    buf = buf.substring(0, buf.length() - 1);
                }
                valuesList.add(buf);
            }
        }

        Vector<String> ret = new Vector<String>();

        if (0 == insertList.size()) {
            ret.addAll(piecesIn);
            return ret;
        }

        if (insertList.size() == valuesList.size()) {
            ret.add(statementBegin.toString());
            StringBuffer insert = new StringBuffer();
            StringBuffer values = new StringBuffer();

            String insBuf = (String) insertList.get(0);
            String valsBuf = (String) valuesList.get(0);

            insert.append('(').append(adoptLength(insBuf, valsBuf));
            values.append('(').append(adoptLength(valsBuf, insBuf));

            for (int i = 1; i < insertList.size(); ++i) {
                insBuf = (String) insertList.get(i);
                valsBuf = (String) valuesList.get(i);

                insert.append(',').append(adoptLength(insBuf, valsBuf));
                values.append(',').append(adoptLength(valsBuf, insBuf));
            }
            insert.append(") VALUES");
            values.append(')');
            ret.add(insert.toString());
            ret.add(values.toString());
            ret.addAll(behindInsert);
            return ret;
        } else {
            ret.addAll(piecesIn);
            return ret;
        }
    }

    private String[] splitAsFarAsPossible(String[] pieces) {
        Vector<String> ret = new Vector<String>();
        for (int i = 0; i < pieces.length; i++) {
            ret.addAll(Arrays.asList(trySplit(pieces[i], 0, 1)));
        }
        return (String[]) ret.toArray(new String[ret.size()]);
    }

    private String adoptLength(String s1, String s2) {
        int max = Math.max(s1.length(), s2.length());

        if (s1.length() == max) {
            return s1;
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append(s1);
            while (sb.length() < max) {
                sb.append(' ');
            }
            return sb.toString();
        }
    }

    private String[] trySplit(String piece, int braketDepth, int trySplitLineLen) {
        String trimmedPiece = piece.trim();
        CodeReformatorKernel dum = new CodeReformatorKernel(_statementSeparator, new PieceMarkerSpec[0], _commentSpecs);

        if (hasTopLevelColon(trimmedPiece, dum)) {
            PieceMarkerSpec[] pms = createPieceMarkerSpecIncludeColon();
            CodeReformatorKernel crk = new CodeReformatorKernel(_statementSeparator, pms, _commentSpecs);
            String[] splitPieces1 = crk.toPieces(trimmedPiece);
            if (1 == splitPieces1.length) {
                return splitPieces1;
            }

            Vector<String> ret = new Vector<String>();

            for (int i = 0; i < splitPieces1.length; ++i) {
                if (trySplitLineLen < splitPieces1[i].length() + braketDepth * INDENT.length()) {
                    String[] splitPieces2 = trySplit(splitPieces1[i], braketDepth, trySplitLineLen);
                    for (int j = 0; j < splitPieces2.length; ++j) {
                        ret.add(splitPieces2[j].trim());
                    }
                } else {
                    ret.add(splitPieces1[i].trim());
                }
            }
            return (String[]) ret.toArray(new String[0]);
        } else {
            int[] tlbi = getTopLevelBraketIndexes(trimmedPiece, dum);
            if (-1 != tlbi[0] && tlbi[0] < tlbi[1]) {
                // ////////////////////////////////////////////////////////////////////////
                // Split the first two matching toplevel brakets here
                PieceMarkerSpec[] pms = createPieceMarkerSpecExcludeColon();
                CodeReformatorKernel crk = new CodeReformatorKernel(_statementSeparator, pms, _commentSpecs);
                String[] splitPieces1 = crk.toPieces(trimmedPiece.substring(tlbi[0] + 1, tlbi[1]));

                Vector<String> buf = new Vector<String>();
                buf.add(trimmedPiece.substring(0, tlbi[0]).trim());
                buf.add("(");
                for (int i = 0; i < splitPieces1.length; ++i) {
                    buf.add(splitPieces1[i]);
                }
                buf.add(")");
                if (tlbi[1] + 1 < trimmedPiece.length()) {
                    buf.add(trimmedPiece.substring(tlbi[1] + 1, trimmedPiece.length()).trim());
                }
                splitPieces1 = (String[]) buf.toArray(new String[0]);
                //
                // ////////////////////////////////////////////////////////////////////

                // ///////////////////////////////////////////////////////////////////
                // Now check length of Strings in splitPieces1 again
                Vector<String> ret = new Vector<String>();
                for (int i = 0; i < splitPieces1.length; ++i) {
                    if (trySplitLineLen < splitPieces1[i].length() + braketDepth * INDENT.length()) {
                        String[] splitPieces2 = trySplit(splitPieces1[i], braketDepth + 1, trySplitLineLen);
                        for (int j = 0; j < splitPieces2.length; ++j) {
                            ret.add(splitPieces2[j]);
                        }
                    } else {
                        ret.add(splitPieces1[i]);
                    }
                }
                //
                // ///////////////////////////////////////////////////////////////////

                return (String[]) ret.toArray(new String[0]);
            } else {
                return new String[]{piece};
            }
        }
    }

    private boolean hasTopLevelColon(String piece, CodeReformatorKernel crk) {
        int ix = piece.indexOf(",");
        StateOfPosition[] stateOfPositions = crk.getStatesOfPosition(piece);

        while (-1 != ix) {
            if (stateOfPositions[ix].isTopLevel) {
                return true;
            }
            if (ix < piece.length() - 1) {
                ix = piece.indexOf(",", ix + 1);
            } else {
                break;
            }
        }

        return false;

    }

    private int[] getTopLevelBraketIndexes(String piece, CodeReformatorKernel crk) {
        int[] ret = new int[2];
        ret[0] = -1;
        ret[1] = -1;

        StateOfPosition[] stateOfPositions = crk.getStatesOfPosition(piece);

        int bra = piece.indexOf("(");
        while (-1 != bra) {
            crk.getStatesOfPosition(piece);

            if (0 == bra || stateOfPositions[bra - 1].isTopLevel) {
                ret[0] = bra;
                break; // break when first braket found
            }
            if (bra < piece.length() - 1) {
                bra = piece.indexOf("(", bra + 1);
            } else {
                break;
            }
        }

        if (-1 == ret[0]) {
            return ret;
        }

        int ket = piece.indexOf(")", bra);
        while (-1 != ket) {
            if (ket == piece.length() - 1 || stateOfPositions[ket].isTopLevel) {
                // the next top level ket is the counterpart to bra
                ret[1] = ket;
                break;
            }
            if (ket < piece.length() - 1) {
                ket = piece.indexOf(")", ket + 1);
            } else {
                break;
            }
        }
        return ret;
    }

    private String indent(String piece, int callDepth) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < callDepth; ++i) {
            sb.append(INDENT);
        }
        sb.append(piece);

        return sb.toString();
    }

    private String flatenWhiteSpaces(String in, boolean force) {
        if (hasCommentEndingWithLineFeed(in) && !force) {
            // No flaten. We would turn statement parts to comment
            return in;
        }

        StringBuffer ret = new StringBuffer();
        int aposCount = 0;
        for (int i = 0; i < in.length(); ++i) {

            if ('\'' == in.charAt(i)) {
                ++aposCount;
            }

            boolean dontAppend = false;

            if (0 != aposCount % 2) {

            } else {
                if (Character.isWhitespace(in.charAt(i)) && i + 1 < in.length() && Character.isWhitespace(in.charAt(i + 1))) {
                    dontAppend = true;
                }
            }

            if (false == dontAppend) {
                char toAppend;
                if (Character.isWhitespace(in.charAt(i)) && 0 == aposCount % 2) {
                    toAppend = ' ';
                } else {
                    toAppend = in.charAt(i);
                }
                ret.append(toAppend);
            }
        }

        return ret.toString();
    }

    boolean hasCommentEndingWithLineFeed(String in) {
        CodeReformatorKernel dum = new CodeReformatorKernel(_statementSeparator, new PieceMarkerSpec[0], _commentSpecs);
        StateOfPosition[] sops = dum.getStatesOfPosition(in);

        boolean inComment = false;
        for (int i = 0; i < sops.length; ++i) {
            if (!inComment && -1 < sops[i].commentIndex) {
                if (-1 < _commentSpecs[sops[i].commentIndex].commentEnd.indexOf('\n')) {
                    return true;
                }
                inComment = true;
            }
            if (-1 == sops[i].commentIndex) {
                inComment = false;
            }
        }
        return false;
    }

    /**
     * 构造拆分SQL子句的规则,包括逗号
     * 
     * @return
     */
    private PieceMarkerSpec[] createPieceMarkerSpecIncludeColon() {
        PieceMarkerSpec[] buf = createPieceMarkerSpecExcludeColon();
        Vector<PieceMarkerSpec> ret = new Vector<PieceMarkerSpec>();
        ret.addAll(Arrays.asList(buf));
        ret.add(new PieceMarkerSpec(",", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_END));

        return (PieceMarkerSpec[]) ret.toArray(new PieceMarkerSpec[0]);
    }

    /**
     * 构造拆分SQL子句的规则,不包括逗号
     * 
     * @return
     */
    private PieceMarkerSpec[] createPieceMarkerSpecExcludeColon() {
        return new PieceMarkerSpec[]{new PieceMarkerSpec("SELECT", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec("UNION", PieceMarkerSpec.TYPE_PIECE_MARKER_IN_OWN_PIECE),
                new PieceMarkerSpec("FROM", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec("INNER", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec("LEFT", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec("RIGHT", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec("WHERE", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec("AND", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec("GROUP", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec("ORDER", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec("INSERT", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec("VALUES", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec("UPDATE", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec("DELETE", PieceMarkerSpec.TYPE_PIECE_MARKER_AT_BEGIN),
                new PieceMarkerSpec(_statementSeparator.toUpperCase(), PieceMarkerSpec.TYPE_PIECE_MARKER_IN_OWN_PIECE)};
    }
}

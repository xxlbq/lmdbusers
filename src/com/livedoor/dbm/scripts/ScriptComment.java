/**
 * $Id: ScriptComment.java,v 1.1 2006/12/04 02:46:32 lijc Exp $
 */
package com.livedoor.dbm.scripts;

public class ScriptComment {

    public static final int CMT_SLASHSLASH = 0;
    public static final int CMT_DASHDASH = 1;
    public static final int CMT_SLASHASTRICK = 2;
    public String comment;
    public int commentType;
    public int beginLineNo;
    public int endLineNo;
    public int beginChar;
    public int endChar;

    public ScriptComment(String comment, int commentType, int beginLineNo, int endLineNo, int beginChar, int endChar) {
        this.comment = comment;
        this.commentType = commentType;
        this.beginLineNo = beginLineNo;
        this.endLineNo = endLineNo;
        this.beginChar = beginChar;
        this.endChar = endChar;
    }
}
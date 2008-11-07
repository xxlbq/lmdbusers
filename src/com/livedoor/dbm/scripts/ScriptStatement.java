/**
 * $Id: ScriptStatement.java,v 1.1 2006/12/04 02:46:32 lijc Exp $
 */
package com.livedoor.dbm.scripts;

public class ScriptStatement {

    public static final int STMT_UNKNOWN = 0;
    public static final int STMT_COMMAND = 1;
    public static final int STMT_BATCH = 2;
    public static final int STMT_PLSQL = 3;
    public static final int STMT_DCL = 4;
    public static final int STMT_DDL = 5;
    public static final int STMT_DML = 6;
    public static final int STMT_TCL = 7;
    public static final int STMT_JAVA = 8;
    public static final int STMT_ANT_TASK = 9;
    public static final int STATUS_CORRECT = 0;
    public static final int STATUS_INCORRECT = 1;
    public static final int STATUS_UNKNOWN = 2;
    public String statement;
    public int statementType;
    public int beginLineNo;
    public int endLineNo;
    public int beginChar;
    public int endChar;
    public int syntaxStatus;

    public ScriptStatement() {
        statement = "";
        statementType = 0;
        beginLineNo = 0;
        endLineNo = 0;
        beginChar = 0;
        endChar = 0;
        syntaxStatus = 0;
    }

    public ScriptStatement(String s, int i, int j, int k, int l, int i1, int j1) {
        statement = "";
        statementType = 0;
        beginLineNo = 0;
        endLineNo = 0;
        beginChar = 0;
        endChar = 0;
        syntaxStatus = 0;
        statement = s;
        statementType = i;
        beginLineNo = j;
        endLineNo = k;
        beginChar = l;
        endChar = i1;
        syntaxStatus = j1;
    }
}

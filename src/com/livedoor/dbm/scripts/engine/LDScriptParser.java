/**
 * $Id: LDScriptParser.java,v 1.2 2006/12/05 08:53:42 lijc Exp $
 */
package com.livedoor.dbm.scripts.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.livedoor.dbm.scripts.ScriptComment;
import com.livedoor.dbm.scripts.ScriptParser;
import com.livedoor.dbm.scripts.ScriptStatement;

public final class LDScriptParser extends ScriptParser {

    private static final String STMT_UNKNOWN = "Unknown";
    private static final String STMT_COMMAND = "Command";
    private static final String STMT_BATCH = "Batch";
    private static final String STMT_PL_SQL = "PL/SQL";
    private static final String STMT_DCL = "DCL";
    private static final String STMT_DDL = "DDL";
    private static final String STMT_DML = "DML";
    private static final String STMT_TCL = "TCL";
    private static final String STMT_JAVA = "JAVA";
    private static final String STMT_ANT = "ANT";
    private static final String STMT_GO = "GO";
    private static final String STMT_FORWARD_SLASH = "/";
    public static final String STMT_BEGIN_TRANS = "BEGIN TRANS";
    public static final String STMT_COMMIT = "COMMIT";
    public static final String STMT_ROLLBACK = "ROLLBACK";
    public static final String STMT_SELECT = "SELECT";
    public static final String STMT_INSERT = "INSERT";
    public static final String STMT_MERGE = "MERGE";
    public static final String STMT_UPDATE = "UPDATE";
    public static final String STMT_REPLACE = "REPLACE";
    public static final String STMT_DELETE = "DELETE";
    public static final String STMT_TRUNCATE = "TRUNCATE";
    public static final String STMT_SHOW = "SHOW";
    public static final String STMT_ANALYZE = "ANALYZE";
    public static final String STMT_RENAME = "RENAME";
    public static final String STMT_CREATE = "CREATE";
    public static final String STMT_DROP = "DROP";
    public static final String STMT_DROP_DATABASE = "DROP DATABASE";
    public static final String STMT_DROP_TABLE = "DROP TABLE";
    public static final String STMT_DROP_INDEX = "DROP INDEX";
    public static final String STMT_ALTER = "ALTER";
    public static final String STMT_GRANT = "GRANT";
    public static final String STMT_REVOKE = "REVOKE";
    public static final String STMT_AUDIT = "AUDIT";
    public static final String STMT_NOAUDIT = "NOAUDIT";
    public static final String STMT_BEGIN = "BEGIN";
    public static final String STMT_DECLARE = "DECLARE";
    public static final String STMT_PERIOD = ".";
    public static final String STMT_JAVA_CODE = "<%";
    public static final String STMT_ANT_TASK = "<";
    private static final ArrayList _parseResultColumn;
    private static final ArrayList _parseResults = new ArrayList();
    private final ArrayList _scriptStatementList;
    private final ArrayList _scriptCommentList;
    private final StringBuffer _currentUncommentLine;
    private final StringBuffer _currentLine;
    private final StringBuffer _currentStatement;
    private final StringBuffer _currentComment;
    private int _currentStmtLineNo;
    private boolean _currentStmtCharIsBlank;
    private boolean _isParserForExecute;
    private int _currentStmtBeginChar;
    private int _currentStmtEndChar;
    private int _currentCmtBeginChar;
    private int _currentCmtEndChar;
    private boolean _bBlockIsSSC;
    private boolean _bSlashSlashIsSSC;
    private boolean _bDashDashIsSSC;
    private final boolean _bSemiColonSep;

    public LDScriptParser(boolean isParserForExecute, boolean isSemiColonSep) {
        _scriptStatementList = new ArrayList();
        _scriptCommentList = new ArrayList();
        _currentUncommentLine = new StringBuffer("");
        _currentLine = new StringBuffer("");
        _currentStatement = new StringBuffer("");
        _currentComment = new StringBuffer("");
        _currentStmtLineNo = 1;
        _currentStmtCharIsBlank = true;
        _isParserForExecute = true;
        _currentStmtBeginChar = 0;
        _currentStmtEndChar = 0;
        _currentCmtBeginChar = 0;
        _currentCmtEndChar = 0;
        _bBlockIsSSC = false;
        _bSlashSlashIsSSC = false;
        _bDashDashIsSSC = false;
        _isParserForExecute = isParserForExecute;
        _bSemiColonSep = isSemiColonSep;
    }

    public LDScriptParser(boolean isParserForExecute, boolean isSemiColonSep, boolean flag2, boolean flag3, boolean flag4) {
        _scriptStatementList = new ArrayList();
        _scriptCommentList = new ArrayList();
        _currentUncommentLine = new StringBuffer("");
        _currentLine = new StringBuffer("");
        _currentStatement = new StringBuffer("");
        _currentComment = new StringBuffer("");
        _currentStmtLineNo = 1;
        _currentStmtCharIsBlank = true;
        _isParserForExecute = true;
        _currentStmtBeginChar = 0;
        _currentStmtEndChar = 0;
        _currentCmtBeginChar = 0;
        _currentCmtEndChar = 0;
        _bBlockIsSSC = false;
        _bSlashSlashIsSSC = false;
        _bDashDashIsSSC = false;
        _isParserForExecute = isParserForExecute;
        _bBlockIsSSC = flag2;
        _bSlashSlashIsSSC = flag3;
        _bDashDashIsSSC = flag4;
        _bSemiColonSep = isSemiColonSep;
    }

    public ArrayList getScriptStatements() {
        return _scriptStatementList;
    }

    public ArrayList getScriptComments() {
        return _scriptCommentList;
    }

    public ArrayList getParseResultColumns() {
        return _parseResultColumn;
    }

    public ArrayList getParseResults() {
        _parseResults.clear();
        ArrayList<Object> arraylist;
        for (Iterator iterator = _scriptStatementList.iterator(); iterator.hasNext(); _parseResults.add(arraylist)) {
            ScriptStatement scriptstatement = (ScriptStatement) iterator.next();
            arraylist = new ArrayList<Object>();
            arraylist.add(new Integer(scriptstatement.beginLineNo));
            arraylist.add(new Integer(scriptstatement.endLineNo));
            switch (scriptstatement.statementType) {
                case ScriptStatement.STMT_UNKNOWN : // '\0'
                    arraylist.add(STMT_UNKNOWN);
                    break;

                case ScriptStatement.STMT_COMMAND : // '\001'
                    arraylist.add(STMT_COMMAND);
                    break;

                case ScriptStatement.STMT_BATCH : // '\002'
                    arraylist.add(STMT_BATCH);
                    break;

                case ScriptStatement.STMT_PLSQL : // '\003'
                    arraylist.add(STMT_PL_SQL);
                    break;

                case ScriptStatement.STMT_DCL : // '\004'
                    arraylist.add(STMT_DCL);
                    break;

                case ScriptStatement.STMT_DDL : // '\005'
                    arraylist.add(STMT_DDL);
                    break;

                case ScriptStatement.STMT_DML : // '\006'
                    arraylist.add(STMT_DML);
                    break;

                case ScriptStatement.STMT_TCL : // '\007'
                    arraylist.add(STMT_TCL);
                    break;

                case ScriptStatement.STMT_JAVA : // '\b'
                    arraylist.add(STMT_JAVA);
                    break;

                case ScriptStatement.STMT_ANT_TASK : // '\t'
                    arraylist.add(STMT_ANT);
                    break;
            }
            arraylist.add(scriptstatement.statement);
            arraylist.add(new Integer(scriptstatement.beginChar));
            arraylist.add(new Integer(scriptstatement.endChar));
        }

        return _parseResults;
    }

    public void parseScript(String s) {
        statNumberOfStatements = 0;
        statNumberOfINSERT = 0;
        statNumberOfUPDATE = 0;
        statNumberOfDELETE = 0;
        statNumberOfSELECT = 0;
        statNumberOfUserTransactions = 0;
        long l = 0L;
        long l1 = System.currentTimeMillis();
        statTimeStart = new Date();
        if (s.length() < 1)
            return;
        String s1 = "";
        int i = 0;
        int j = 1;
        try {
            boolean flag = false;
            boolean flag1 = false;
            boolean flag2 = false;
            boolean flag3 = false;
            boolean flag4 = false;
            char c = ' ';
            byte byte0 = 32;
            byte byte1 = 32;
            char c4 = s.charAt(i);
            i++;
            while (!flag4) {
                switch (c4) {
                    case '\r' : // '\r'
                        if (!_isParserForExecute)
                            _currentLine.append(c4);
                        if (!flag && !flag1 && !flag2)
                            _currentUncommentLine.append(c4);
                        flag2 = false;
                        flag1 = false;
                        break;

                    case '\n' : // '\n'
                        j++;
                        _currentStmtEndChar++;
                        if (_currentStmtCharIsBlank)
                            _currentStmtBeginChar++;
                        String s2 = _currentUncommentLine.toString().trim();
                        boolean flag5 = isBatchEnd(s2);
                        if (flag5) {
                            createParseRecordNow(j - 1, _currentStmtEndChar);
                        } else {
                            _currentStatement.append(_currentLine.toString());
                            if (!_bSemiColonSep)
                                _currentStatement.append(c4);
                            else if (!flag3 && !flag && !flag1 && !flag2 && s2.endsWith(";"))
                                createParseRecordNow(j - 1, _currentStmtEndChar);
                            else
                                _currentStatement.append(c4);
                        }
                        _currentLine.setLength(0);
                        _currentUncommentLine.setLength(0);
                        flag2 = false;
                        flag1 = false;
                        break;

                    case '\'' : // '\''
                        if (!flag && !flag1 && !flag2)
                            if (flag3)
                                flag3 = false;
                            else
                                flag3 = true;
                        _currentStmtEndChar++;
                        _currentLine.append(c4);
                        if (!flag && !flag1 && !flag2)
                            _currentUncommentLine.append(c4);
                        if (c4 != ' ')
                            _currentStmtCharIsBlank = false;
                        if (_currentStmtCharIsBlank)
                            _currentStmtBeginChar++;
                        break;

                    case '/' : // '/'
                        if (flag3) {
                            _currentStmtEndChar++;
                            _currentLine.append(c4);
                            if (!flag && !flag1 && !flag2)
                                _currentUncommentLine.append(c4);
                            if (c4 != ' ')
                                _currentStmtCharIsBlank = false;
                            if (_currentStmtCharIsBlank)
                                _currentStmtBeginChar++;
                        } else {
                            _currentStmtEndChar++;
                            if (_currentStmtCharIsBlank)
                                _currentStmtBeginChar++;
                            char c1;
                            if (i >= s.length())
                                c1 = ' ';
                            else
                                c1 = s.charAt(i);
                            if (c1 == '*' && _bBlockIsSSC && _isParserForExecute || c1 == '/' && _bSlashSlashIsSSC && _isParserForExecute) {
                                if (c == '*')
                                    flag = false;
                                if (c1 == '*')
                                    flag = true;
                                else
                                    flag1 = true;
                                _currentLine.append(c4);
                                if (c4 != ' ')
                                    _currentStmtCharIsBlank = false;
                                if (_currentStmtCharIsBlank)
                                    _currentStmtBeginChar++;
                            } else if (c1 == '*') {
                                _currentComment.append(c4);
                                _currentComment.append(c1);
                                boolean flag7 = false;
                                i++;
                                _currentStmtEndChar++;
                                _currentCmtBeginChar = _currentStmtEndChar - 2;
                                _currentCmtEndChar = 0;
                                if (_currentStmtCharIsBlank)
                                    _currentStmtBeginChar++;
                                do {
                                    if (i < s.length()) {
                                        c1 = s.charAt(i);
                                        c = s.charAt(i - 1);
                                        i++;
                                        if (c1 == '\n')
                                            j++;
                                        if (c1 != '\r')
                                            _currentStmtEndChar++;
                                        if (c1 != '\r' && _currentStmtCharIsBlank)
                                            _currentStmtBeginChar++;
                                        _currentComment.append(c1);
                                    } else {
                                        flag7 = true;
                                        _currentCmtEndChar = _currentStmtEndChar;
                                    }
                                    if (c1 == '/' && c == '*') {
                                        flag7 = true;
                                        _currentCmtEndChar = _currentStmtEndChar;
                                    }
                                } while (!flag7);
                                _scriptCommentList.add(new ScriptComment(_currentComment.toString(), 2, 0, 0, _currentCmtBeginChar,
                                        _currentCmtEndChar));
                                _currentComment.setLength(0);
                            } else if (c1 == '/') {
                                _currentComment.append(c4);
                                _currentComment.append(c1);
                                boolean flag8 = false;
                                i++;
                                _currentStmtEndChar++;
                                if (_currentStmtCharIsBlank)
                                    _currentStmtBeginChar++;
                                _currentCmtBeginChar = _currentStmtEndChar - 2;
                                _currentCmtEndChar = 0;
                                do {
                                    if (i < s.length()) {
                                        c1 = s.charAt(i);
                                        i++;
                                        if (c1 == '\n')
                                            j++;
                                        if (c1 != '\r')
                                            _currentStmtEndChar++;
                                        if (c1 != '\r' && _currentStmtCharIsBlank)
                                            _currentStmtBeginChar++;
                                        _currentComment.append(c1);
                                    } else {
                                        flag8 = true;
                                        _currentCmtEndChar = _currentStmtEndChar;
                                    }
                                    if (c1 == '\n' || c1 == '\r') {
                                        flag8 = true;
                                        _currentCmtEndChar = _currentStmtEndChar;
                                    }
                                } while (!flag8);
                                _scriptCommentList.add(new ScriptComment(_currentComment.toString(), 0, 0, 0, _currentCmtBeginChar,
                                        _currentCmtEndChar));
                                _currentComment.setLength(0);
                            } else {
                                _currentStmtCharIsBlank = false;
                                _currentLine.append(c4);
                                if (!flag && !flag1 && !flag2)
                                    _currentUncommentLine.append(c4);
                                if (c == '*')
                                    flag = false;
                            }
                        }
                        break;

                    case '-' : // '-'
                        char c2 = s.charAt(i);
                        if (c2 == '-' && _bDashDashIsSSC && _isParserForExecute) {
                            flag2 = true;
                            _currentStmtEndChar++;
                            _currentLine.append(c4);
                            if (c4 != ' ')
                                _currentStmtCharIsBlank = false;
                            if (_currentStmtCharIsBlank)
                                _currentStmtBeginChar++;
                        } else {
                            _currentStmtEndChar++;
                            if (_currentStmtCharIsBlank)
                                _currentStmtBeginChar++;
                            char c3 = s.charAt(i);
                            if (c3 == '-' && !flag3) {
                                _currentComment.append(c4);
                                _currentComment.append(c3);
                                boolean flag9 = false;
                                i++;
                                _currentStmtEndChar++;
                                if (_currentStmtCharIsBlank)
                                    _currentStmtBeginChar++;
                                _currentCmtBeginChar = _currentStmtEndChar - 2;
                                _currentCmtEndChar = 0;
                                do {
                                    if (i < s.length()) {
                                        c3 = s.charAt(i);
                                        i++;
                                        if (c3 == '\n')
                                            j++;
                                        if (c3 != '\r')
                                            _currentStmtEndChar++;
                                        if (c3 != '\r' && _currentStmtCharIsBlank)
                                            _currentStmtBeginChar++;
                                        _currentComment.append(c3);
                                    } else {
                                        flag9 = true;
                                        _currentCmtEndChar = _currentStmtEndChar;
                                    }
                                    if (c3 == '\n' || c3 == '\r') {
                                        flag9 = true;
                                        _currentCmtEndChar = _currentStmtEndChar;
                                    }
                                } while (!flag9);
                                _scriptCommentList.add(new ScriptComment(_currentComment.toString(), 1, 0, 0, _currentCmtBeginChar,
                                        _currentCmtEndChar));
                                _currentComment.setLength(0);
                            } else {
                                _currentStmtCharIsBlank = false;
                                _currentLine.append(c4);
                                if (!flag && !flag1 && !flag2)
                                    _currentUncommentLine.append(c4);
                            }
                        }
                        break;

                    default :
                        _currentStmtEndChar++;
                        _currentLine.append(c4);
                        if (!flag && !flag1 && !flag2)
                            _currentUncommentLine.append(c4);
                        if (c4 != ' ')
                            _currentStmtCharIsBlank = false;
                        if (_currentStmtCharIsBlank)
                            _currentStmtBeginChar++;
                        break;
                }
                if (i < s.length()) {
                    c = c4;
                    c4 = s.charAt(i);
                    i++;
                } else {
                    flag4 = true;
                }
            }
            String s3 = _currentLine.toString().trim();
            boolean flag6 = isBatchEnd(s3);
            if (flag6) {
                createParseRecordNow(j, _currentStmtEndChar);
            } else {
                _currentStatement.append(_currentLine.toString());
                createParseRecordNow(j, _currentStmtEndChar);
            }
            _currentLine.setLength(0);
            _currentUncommentLine.setLength(0);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        l = System.currentTimeMillis();
        statParseTime = l - l1;
        statTimeEnd = new Date();
    }

    private static final boolean isBatchEnd(String s) {
        return s.equalsIgnoreCase(STMT_GO) || s.equals(STMT_FORWARD_SLASH);
    }

    private final void createParseRecordNow(int i, int j) {
        ScriptStatement scriptstatement = new ScriptStatement();
        String s = _currentStatement.toString();
        _currentStatement.setLength(0);
        s = s.trim();
        if (_bSemiColonSep && s.endsWith(";"))
            s = s.substring(0, s.length() - 1);
        if (s.length() > 0) {
            scriptstatement.statement = s;
            scriptstatement.beginLineNo = _currentStmtLineNo;
            scriptstatement.endLineNo = i;
            scriptstatement.beginChar = _currentStmtBeginChar;
            scriptstatement.endChar = j;
            scriptstatement.syntaxStatus = 0;
            String s1 = s.toUpperCase();
            if (s1.startsWith("BEGIN TRANS")) {
                scriptstatement.statementType = ScriptStatement.STMT_TCL;
                statNumberOfUserTransactions++;
            } else if (s1.startsWith("COMMIT") || s1.startsWith("ROLLBACK"))
                scriptstatement.statementType = ScriptStatement.STMT_TCL;
            else if (s1.startsWith("SELECT")) {
                scriptstatement.statementType = ScriptStatement.STMT_DML;
                statNumberOfSELECT++;
            } else if (s1.startsWith("INSERT")) {
                scriptstatement.statementType = ScriptStatement.STMT_DML;
                statNumberOfINSERT++;
            } else if (s1.startsWith("UPDATE")) {
                scriptstatement.statementType = ScriptStatement.STMT_DML;
                statNumberOfUPDATE++;
            } else if (s1.startsWith("DELETE")) {
                scriptstatement.statementType = ScriptStatement.STMT_DML;
                statNumberOfDELETE++;
            } else if (s1.startsWith("CREATE") || s1.startsWith("DROP") || s1.startsWith("ALTER"))
                scriptstatement.statementType = ScriptStatement.STMT_DDL;
            else if (s1.startsWith("GRANT") || s1.startsWith("REVOKE"))
                scriptstatement.statementType = ScriptStatement.STMT_DCL;
            else if (s1.startsWith("BEGIN"))
                scriptstatement.statementType = ScriptStatement.STMT_PLSQL;
            else if (s1.startsWith("DECLARE"))
                scriptstatement.statementType = ScriptStatement.STMT_PLSQL;
            else if (s1.startsWith("."))
                scriptstatement.statementType = ScriptStatement.STMT_COMMAND;
            else if (s1.startsWith("<%"))
                scriptstatement.statementType = ScriptStatement.STMT_JAVA;
            else if (s1.startsWith("<"))
                scriptstatement.statementType = ScriptStatement.STMT_ANT_TASK;
            else
                scriptstatement.statementType = ScriptStatement.STMT_BATCH;
            _scriptStatementList.add(scriptstatement);
            statNumberOfStatements++;
            _currentStmtLineNo = i + 1;
        }
        _currentStmtBeginChar = j;
        _currentStmtCharIsBlank = true;
    }

    public ScriptStatement getStatementByCharPosition(int i) {
        ScriptStatement scriptstatement = null;
        if (_scriptStatementList == null)
            return null;
        if (_scriptStatementList.size() < 1)
            return null;
        int j = _scriptStatementList.size();
        int k = 0;
        do {
            if (k >= j)
                break;
            scriptstatement = (ScriptStatement) _scriptStatementList.get(k);
            if (i >= scriptstatement.beginChar && i < scriptstatement.endChar)
                break;
            scriptstatement = null;
            k++;
        } while (true);
        if (scriptstatement == null) {
            int l = 0;
            do {
                if (l >= j)
                    break;
                scriptstatement = (ScriptStatement) _scriptStatementList.get(l);
                if (i < scriptstatement.beginChar)
                    break;
                l++;
            } while (true);
        }
        return scriptstatement;
    }

    public ScriptComment getCommentByCharPosition(int i) {
        ScriptComment scriptcomment = null;
        if (_scriptCommentList != null && _scriptCommentList.size() > 0) {
            int j = _scriptCommentList.size();
            for (int k = 0; k < j; k++) {
                scriptcomment = (ScriptComment) _scriptCommentList.get(k);
                if (scriptcomment.commentType == 2) {
                    if (i >= scriptcomment.beginChar && i < scriptcomment.endChar)
                        break;
                    scriptcomment = null;
                    continue;
                }
                if (i >= scriptcomment.beginChar && i <= scriptcomment.endChar)
                    break;
                scriptcomment = null;
            }

        }
        return scriptcomment;
    }

    static {
        _parseResultColumn = new ArrayList();
        /*
         * AFStmtResultColumn afstmtresultcolumn = new
         * AFStmtResultColumn("Begin", 4, "INTEGER", false, true, 7, 7, 7);
         * _parseResultColumn.add(afstmtresultcolumn); afstmtresultcolumn = new
         * AFStmtResultColumn("End", 4, "INTEGER", false, true, 7, 7, 7);
         * _parseResultColumn.add(afstmtresultcolumn); afstmtresultcolumn = new
         * AFStmtResultColumn("Type", 12, "VARCHAR", true, true, 8, 8, 8);
         * _parseResultColumn.add(afstmtresultcolumn); afstmtresultcolumn = new
         * AFStmtResultColumn("Statement", 12, "VARCHAR", true, true, 75, 75,
         * 75); _parseResultColumn.add(afstmtresultcolumn);
         */}
}

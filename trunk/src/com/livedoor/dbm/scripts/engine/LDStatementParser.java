/**
 * $Id: LDStatementParser.java,v 1.1 2006/12/04 02:46:33 lijc Exp $
 */
package com.livedoor.dbm.scripts.engine;

import java.util.*;

import com.livedoor.dbm.scripts.StatementParser;

public final class LDStatementParser extends StatementParser {

    private static final HashMap<String, String> _keyWordTerminators;
    
    private static final int SEARCHING_FIELDS_SELECT = 1;
    private static final int SEARCHING_TABLES_SELECT = 2;
    private static final int SEARCHING_FIELDS_SELECT_WHERE = 3;
    private static final int SEARCHING_FIELDS_SELECT_ORDER_BY = 4;
    private static final int SEARCHING_FIELDS_SELECT_GROUP_BY = 5;
    private static final int SEARCHING_INTO_INSERT = 6;
    private static final int SEARCHING_TABLE_INSERT = 7;
    private static final int SEARCHING_FIELDS_INSERT = 8;
    private static final int SEARCHING_VALUES_INSERT = 9;
    private static final int SEARCHING_FIELD_VALUES_INSERT = 10;
    private static final int SEARCHING_TABLE_UPDATE = 11;
    private static final int SEARCHING_FIELD_VALUES_UPDATE = 12;
    private static final int SEARCHING_WHERE_UPDATE = 13;
    private static final int SEARCHING_FROM_DELETE = 14;
    private static final int SEARCHING_TABLE_DELETE = 15;
    private static final int SEARCHING_WHERE_DELETE = 16;
    
    private static final String SELECT = "SELECT";
    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE";
    private static final String DELETE = "DELETE";
    private static final String MERGE = "MERGE";
    private static final String TRUNCATE = "TRUNCATE";
    private static final String USE = "USE";
    private static final String SHOW = "SHOW";
    private static final String REPLACE = "REPLACE";
    private static final String CREATE = "CREATE";
    private static final String DROP = "DROP";
    private static final String ALTER = "ALTER";
    private static final String RENAME = "RENAME";
    private static final String GRANT = "GRANT";
    private static final String REVOKE = "REVOKE";
    private static final String FROM = "FROM";
    private static final String INTO = "INTO";
    private static final String SET = "SET";
    private static final String ON = "ON";
    private static final String WHERE = "WHERE";
    private static final String ORDERBY = "ORDER BY";
    private static final String GROUPBY = "GROUP BY";
    private static final String VALUES = "VALUES";
    private static final String SPACE_ON = " ON";
    private static final String AS = "AS";
    private static final String JOIN = "JOIN";
    private static final String INNER = "INNER";
    private static final String LEFT = "LEFT";
    private static final String RIGHT = "RIGHT";
    private static final String FULL = "FULL";
    private static final String CROSS = "CROSS";
    private static final String STRAIGHT = "STRAIGHT";
    private static final String NATURAL = "NATURAL";
    
    private final HashMap<String, String> _aliases = new HashMap<String, String>();
    private final ArrayList<String> _tables = new ArrayList<String>();
    private final ArrayList _fields = new ArrayList();
    
    private String _stmt;
    private String _stmtOriginal;
    
    private int _currentIndex;
    private int _stmtType;
    private int _stmtLocation;
    private int _cursorPosition;

    public LDStatementParser() {
        _currentIndex = 0;
        _stmtLocation = IN_FIELDS;
        _cursorPosition = 0;
    }

    private boolean isKeyWordTerminator(char c) {
        char ac[] = {c};
        String s = new String(ac);
        s = (String) _keyWordTerminators.get(s);
        return s != null;
    }

    public int getStmtType() {
        return _stmtType;
    }

    public int getStmtLocation() {
        return _stmtLocation;
    }

    public ArrayList getTables() {
        return _tables;
    }

    public String getTableByAlias(String s) {
        String s1 = (String) _aliases.get(s.toUpperCase());
        if (s1 == null)
            return s;
        else
            return s1;
    }

    public String getAliasByTable(String s) {
        String s1 = s;
        Iterator iterator = _aliases.keySet().iterator();
        do {
            if (!iterator.hasNext())
                break;
            String s2 = (String) iterator.next();
            String s3 = (String) _aliases.get(s2);
            if (s3.equalsIgnoreCase(s))
                s1 = s2;
        } while (true);
        return s1;
    }

    public void parseStatement(String s, int cursorPos) {
        _cursorPosition = cursorPos;
        if (s == null) {
            _stmtType = STMT_INVALID;
            return;
        }
        
        _stmtOriginal = s;
        _stmt = s;
        eatSpaces();
        String s1 = "";
        if (_stmt.length() - _currentIndex < 3) {
            _stmtType = STMT_INVALID;
            return;
        }
        s1 = _stmt.substring(_currentIndex, 3);
        s1 = s1.toUpperCase();
        if (s1.startsWith(USE)) {
            _stmtType = STMT_USE;
            _currentIndex += 3;
            setCursor(true, 0);
            parseUse();
            return;
        }
        
        if (_stmt.length() - _currentIndex < 4) {
            _stmtType = STMT_INVALID;
            return;
        }
        s1 = _stmt.substring(_currentIndex, 4);
        s1 = s1.toUpperCase();
        if (s1.startsWith(DROP)) {
            _stmtType = STMT_DROP;
            _currentIndex += 4;
            setCursor(true, 0);
            parseDrop();
            return;
        }
        if (s1.startsWith(SHOW)) {
            _stmtType = STMT_SHOW;
            _currentIndex += 4;
            setCursor(true, 0);
            parseShow();
            return;
        }
        
        if (_stmt.length() - _currentIndex < 5) {
            _stmtType = STMT_INVALID;
            return;
        }
        s1 = _stmt.substring(_currentIndex, 5);
        s1 = s1.toUpperCase();
        if (s1.startsWith(ALTER)) {
            _stmtType = STMT_ALTER;
            _currentIndex += 5;
            setCursor(true, 0);
            parseAlter();
            return;
        }
        if (s1.startsWith(GRANT)) {
            _stmtType = STMT_GRANT;
            _currentIndex += 5;
            setCursor(true, 0);
            parseGrant();
            return;
        }
        if (s1.startsWith(MERGE)) {
            _stmtType = STMT_MERGE;
            _currentIndex += 5;
            setCursor(true, 0);
            parseMerge();
            return;
        }
        
        if (_stmt.length() - _currentIndex < 6) {
            _stmtType = STMT_INVALID;
            return;
        }
        s1 = _stmt.substring(_currentIndex, 6);
        s1 = s1.toUpperCase();
        if (s1.startsWith(SELECT)) {
            _stmtType = STMT_SELECT;
            _currentIndex += 6;
            setCursor(true, 0);
            parseSelect();
            return;
        }
        if (s1.startsWith(RENAME)) {
            _stmtType = STMT_RENAME;
            _currentIndex += 6;
            setCursor(true, 0);
            parseRename();
            return;
        }
        if (s1.startsWith(REVOKE)) {
            _stmtType = STMT_REVOKE;
            _currentIndex += 6;
            setCursor(true, 0);
            parseRevoke();
            return;
        }
        if (s1.startsWith(CREATE)) {
            _stmtType = STMT_CREATE;
            _currentIndex += 6;
            setCursor(true, 0);
            parseCreate();
            return;
        }
        if (s1.startsWith(INSERT)) {
            _stmtType = STMT_INSERT;
            _currentIndex += 6;
            setCursor(true, 0);
            parseInsert();
            return;
        }
        if (s1.startsWith(UPDATE)) {
            _stmtType = STMT_UPDATE;
            _currentIndex += 6;
            setCursor(true, 0);
            parseUpdate();
            return;
        }
        if (s1.startsWith(DELETE)) {
            _stmtType = STMT_DELETE;
            _currentIndex += 6;
            setCursor(true, 0);
            parseDelete();
            return;
        }
        
        if (_stmt.length() - _currentIndex < 7) {
            _stmtType = STMT_INVALID;
            return;
        }
        s1 = _stmt.substring(_currentIndex, 7);
        s1 = s1.toUpperCase();
        if (s1.startsWith(REPLACE)) {
            _stmtType = STMT_REPLACE;
            _currentIndex += 7;
            setCursor(true, 0);
            parseReplace();
            return;
        }
        
        if (_stmt.length() - _currentIndex < 8) {
            _stmtType = STMT_INVALID;
            return;
        }
        s1 = _stmt.substring(_currentIndex, 8);
        s1 = s1.toUpperCase();
        if (s1.startsWith(TRUNCATE)) {
            _stmtType = STMT_TRUNCATE;
            _currentIndex += 8;
            setCursor(true, 0);
            parseTruncate();
            return;
        } else {
            _stmtType = STMT_INVALID;
            setCursor(true, 0);
            return;
        }
    }

    private void eatSpaces() {
        boolean flag = false;
        do {
            char c = _stmt.charAt(_currentIndex);
            switch (c) {
                case 9 : // '\t'
                case 10 : // '\n'
                case 13 : // '\r'
                case 32 : // ' '
                    _currentIndex++;
                    break;

                default :
                    flag = true;
                    break;
            }
        } while (!flag);
    }
    
    private void setCursor(boolean flag, int i) {
        if (flag) {
            if (_cursorPosition < _currentIndex)
                switch (_stmtType) {
                    case STMT_INVALID : // '\0'
                        _stmtLocation = IN_FIELDS;
                        break;

                    case STMT_SELECT : // '\001'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_INSERT : // '\002'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_UPDATE : // '\003'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_DELETE : // '\004'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_MERGE : // '\005'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_TRUNCATE : // '\006'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_USE : // '\007'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_SHOW : // '\b'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_REPLACE : // '\t'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_CREATE : // '\n'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_DROP : // '\013'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_ALTER : // '\f'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_RENAME : // '\r'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_GRANT : // '\016'
                        _stmtLocation = IN_TABLE;
                        break;

                    case STMT_REVOKE : // '\017'
                        _stmtLocation = IN_TABLE;
                        break;
                }
        } else if (_cursorPosition == _currentIndex + 2 || _cursorPosition == _currentIndex + 1)
            switch (i) {
                case SEARCHING_FIELDS_SELECT : // '\001'
                    _stmtLocation = IN_FIELDS;
                    break;

                case SEARCHING_TABLES_SELECT : // '\002'
                    _stmtLocation = IN_TABLE;
                    break;

                case SEARCHING_FIELDS_SELECT_WHERE : // '\003'
                    _stmtLocation = IN_FIELDS_CRITERIA;
                    break;

                case SEARCHING_FIELDS_SELECT_ORDER_BY : // '\004'
                    _stmtLocation = IN_FIELDS_CRITERIA;
                    break;

                case SEARCHING_FIELDS_SELECT_GROUP_BY : // '\005'
                    _stmtLocation = IN_FIELDS_CRITERIA;
                    break;

                case SEARCHING_INTO_INSERT : // '\006'
                    _stmtLocation = IN_TABLE;
                    break;

                case SEARCHING_TABLE_INSERT : // '\007'
                    _stmtLocation = IN_TABLE;
                    break;

                case SEARCHING_FIELDS_INSERT : // '\b'
                    _stmtLocation = IN_FIELDS;
                    break;

                case SEARCHING_VALUES_INSERT : // '\t'
                    _stmtLocation = IN_FIELDS;
                    break;

                case SEARCHING_FIELD_VALUES_INSERT : // '\n'
                    _stmtLocation = IN_FIELDS_VALUE;
                    break;

                case SEARCHING_TABLE_UPDATE : // '\013'
                    _stmtLocation = IN_TABLE;
                    break;

                case SEARCHING_FIELD_VALUES_UPDATE : // '\f'
                    _stmtLocation = IN_FIELDS_VALUE;
                    break;

                case SEARCHING_WHERE_UPDATE : // '\r'
                    _stmtLocation = IN_FIELDS;
                    break;

                case SEARCHING_FROM_DELETE : // '\016'
                    _stmtLocation = IN_TABLE;
                    break;

                case SEARCHING_TABLE_DELETE : // '\017'
                    _stmtLocation = IN_TABLE;
                    break;

                case SEARCHING_WHERE_DELETE : // '\020'
                    _stmtLocation = IN_FIELDS_CRITERIA;
                    break;
            }
    }

    private void parseSelect() {
        StringBuffer stringbuffer = new StringBuffer();
        String s = "";
        String s1 = "";
        int i = SEARCHING_FIELDS_SELECT;
        setCursor(false, i);
        int j = _stmt.length() - _currentIndex;
        _currentIndex--;
        for (int k = 0; k < j; k++) {
            _currentIndex++;
            setCursor(false, i);
            char c = _stmtOriginal.charAt(_currentIndex);
            if (c == '\r' || c == '\n')
                c = ' ';
            label0 : switch (i) {
                default :
                    break;

                case SEARCHING_FIELDS_SELECT : // '\001'
                    stringbuffer.append(c);
                    switch (c) {
                        default :
                            break;

                        case 'M' : // 'M'
                        case 'm' : // 'm'
                            if (k <= 2)
                                break label0;
                            String s2 = _stmt.substring(_currentIndex - 3, _currentIndex + 1);
                            s2 = s2.toUpperCase();
                            if (s2.endsWith(FROM) && isKeyWordTerminator(_stmt.charAt(_currentIndex - 4))) {
                                s1 = stringbuffer.toString();
                                s1 = s1.substring(0, s1.length() - 5);
                                stringbuffer = new StringBuffer();
                                i = SEARCHING_TABLES_SELECT;
                            }
                            break;
                    }
                    break;

                case SEARCHING_TABLES_SELECT : // '\002'
                    stringbuffer.append(c);
                    switch (c) {
                        default :
                            break;

                        case 'N' : // 'N'
                        case 'n' : // 'n'
                            if (k <= 0 || _currentIndex + 1 >= _stmt.length() || _stmt.charAt(_currentIndex + 1) != ' ')
                                break label0;
                            String s3 = stringbuffer.toString();
                            s3 = s3.toUpperCase();
                            if (s3.endsWith(SPACE_ON)) {
                                s = stringbuffer.toString();
                                s = s.substring(0, s.length() - 2).trim();
                                stringbuffer = new StringBuffer();
                                i = SEARCHING_FIELDS_SELECT_WHERE;
                            }
                            break label0;

                        case 'E' : // 'E'
                        case 'e' : // 'e'
                            if (k <= 3)
                                break label0;
                            String s4 = _stmt.substring(_currentIndex - 4, _currentIndex + 1);
                            s4 = s4.toUpperCase();
                            if (s4.endsWith(WHERE) && isKeyWordTerminator(_stmt.charAt(_currentIndex - 5))) {
                                s = stringbuffer.toString();
                                s = s.substring(0, s.length() - 6).trim();
                                stringbuffer = new StringBuffer();
                                i = SEARCHING_FIELDS_SELECT_WHERE;
                            }
                            break label0;

                        case 'Y' : // 'Y'
                        case 'y' : // 'y'
                            if (k <= 6)
                                break label0;
                            String s5 = _stmt.substring(_currentIndex - 7, _currentIndex + 1);
                            s5 = s5.toUpperCase();
                            if (s5.endsWith(GROUPBY)) {
                                s = stringbuffer.toString();
                                s = s.substring(0, s.length() - 8).trim();
                                stringbuffer = new StringBuffer();
                                i = SEARCHING_FIELDS_SELECT_GROUP_BY;
                                break label0;
                            }
                            if (s5.endsWith(ORDERBY)) {
                                s = stringbuffer.toString();
                                s = s.substring(0, s.length() - 8).trim();
                                stringbuffer = new StringBuffer();
                                i = SEARCHING_FIELDS_SELECT_ORDER_BY;
                            }
                            break;
                    }
                    break;

                case SEARCHING_FIELDS_SELECT_WHERE : // '\003'
                    stringbuffer.append(c);
                    break;

                case SEARCHING_FIELDS_SELECT_ORDER_BY : // '\004'
                    stringbuffer.append(c);
                    break;

                case SEARCHING_FIELDS_SELECT_GROUP_BY : // '\005'
                    stringbuffer.append(c);
                    break;
            }
            setCursor(false, i);
        }

        if (i == SEARCHING_FIELDS_SELECT)
            s1 = stringbuffer.toString().trim();
        else if (i == SEARCHING_TABLES_SELECT)
            s = stringbuffer.toString().trim();
        parseTables(s);
    }

    private void parseInsert() {
        StringBuffer stringbuffer = new StringBuffer();
        String s = "";
        String s1 = "";
        boolean flag = false;
        byte byte0 = SEARCHING_INTO_INSERT;
        setCursor(false, byte0);
        int i = _stmt.length() - _currentIndex;
        _currentIndex--;
        for (int j = 0; j < i; j++) {
            _currentIndex++;
            setCursor(false, byte0);
            char c = _stmtOriginal.charAt(_currentIndex);
            if (c == '\r' || c == '\n')
                c = ' ';
            label0 : switch (byte0) {
                default :
                    break;

                case SEARCHING_INTO_INSERT : // '\006'
                    stringbuffer.append(c);
                    switch (c) {
                        case 'O' : // 'O'
                        case 'o' : // 'o'
                            if (j > 2) {
                                String s2 = _stmt.substring(_currentIndex - 3, _currentIndex + 1);
                                s2 = s2.toUpperCase();
                                if (s2.endsWith(INTO)) {
                                    stringbuffer = new StringBuffer();
                                    byte0 = SEARCHING_TABLE_INSERT;
                                }
                            }
                            break;
                    }
                    break;

                case SEARCHING_TABLE_INSERT : // '\007'
                    stringbuffer.append(c);
                    switch (c) {
                        default :
                            break;

                        case '(' : // '('
                            if (j > 2) {
                                s = stringbuffer.toString();
                                s = s.substring(0, s.length() - 1).trim();
                                stringbuffer = new StringBuffer();
                                byte0 = SEARCHING_FIELDS_INSERT;
                                setCursor(false, byte0);
                            }
                            break;
                    }
                    break;

                case SEARCHING_FIELDS_INSERT : // '\b'
                    stringbuffer.append(c);
                    switch (c) {
                        default :
                            break;

                        case ')' : // ')'
                            if (j > 2) {
                                s1 = stringbuffer.toString();
                                s1 = s1.substring(0, s1.length() - 1);
                                stringbuffer = new StringBuffer();
                                byte0 = SEARCHING_VALUES_INSERT;
                            }
                            break;
                    }
                    break;

                case SEARCHING_VALUES_INSERT : // '\t'
                    stringbuffer.append(c);
                    switch (c) {
                        default :
                            break;

                        case 'S' : // 'S'
                        case 's' : // 's'
                            if (j <= 3)
                                break label0;
                            String s3 = _stmt.substring(_currentIndex - 4, _currentIndex + 1);
                            s3 = s3.toUpperCase();
                            if (s3.endsWith(VALUES)) {
                                stringbuffer = new StringBuffer();
                                byte0 = SEARCHING_FIELD_VALUES_INSERT;
                            }
                            break;
                    }
                    break;

                case SEARCHING_FIELD_VALUES_INSERT : // '\n'
                    switch (c) {
                        case '(' : // '('
                        case ')' : // ')'
                            if (flag)
                                stringbuffer.append(c);
                            break label0;

                        case '\'' : // '\''
                            if (flag)
                                flag = false;
                            else
                                flag = true;
                            break;

                        default :
                            stringbuffer.append(c);
                            break;
                    }
                    break;
            }
            setCursor(false, byte0);
        }

        setCursor(false, byte0);
        if (byte0 != SEARCHING_INTO_INSERT)
            if (byte0 == SEARCHING_TABLE_INSERT)
                s = stringbuffer.toString().trim();
            else if (byte0 == SEARCHING_FIELDS_INSERT)
                s1 = stringbuffer.toString().trim();
            else if (byte0 != SEARCHING_VALUES_INSERT)
                if (byte0 != SEARCHING_FIELD_VALUES_INSERT)
                    ;
        parseTables(s);
    }

    private void parseUpdate() {
        StringBuffer stringbuffer = new StringBuffer();
        String s = "";
        String s1 = "";
        boolean flag = false;
        byte byte0 = SEARCHING_TABLE_UPDATE;
        setCursor(false, byte0);
        int i = _stmt.length() - _currentIndex;
        _currentIndex--;
        for (int j = 0; j < i; j++) {
            _currentIndex++;
            setCursor(false, byte0);
            char c = _stmtOriginal.charAt(_currentIndex);
            if (c == '\r' || c == '\n')
                c = ' ';
            label0 : switch (byte0) {
                default :
                    break;

                case SEARCHING_TABLE_UPDATE : // '\013'
                    stringbuffer.append(c);
                    switch (c) {
                        case 'T' : // 'T'
                        case 't' : // 't'
                            if (j > 1) {
                                String s3 = _stmt.substring(_currentIndex - 2, _currentIndex + 1);
                                s3 = s3.toUpperCase();
                                if (s3.endsWith(SET)) {
                                    s = stringbuffer.toString();
                                    s = s.substring(0, s.length() - 4).trim();
                                    stringbuffer = new StringBuffer();
                                    byte0 = SEARCHING_FIELD_VALUES_UPDATE;
                                }
                            }
                            break;
                    }
                    break;

                case SEARCHING_FIELD_VALUES_UPDATE : // '\f'
                    switch (c) {
                        case '(' : // '('
                        case ')' : // ')'
                            if (flag)
                                stringbuffer.append(c);
                            break;

                        case '\'' : // '\''
                            if (flag)
                                flag = false;
                            else
                                flag = true;
                            break;

                        default :
                            stringbuffer.append(c);
                            break;
                    }
                    switch (c) {
                        default :
                            break;

                        case 'E' : // 'E'
                        case 'e' : // 'e'
                            if (j <= 3)
                                break label0;
                            String s4 = _stmt.substring(_currentIndex - 4, _currentIndex + 1);
                            s4 = s4.toUpperCase();
                            if (s4.endsWith(WHERE)) {
                                stringbuffer = new StringBuffer();
                                byte0 = SEARCHING_WHERE_UPDATE;
                            }
                            break;
                    }
                    break;

                case SEARCHING_WHERE_UPDATE : // '\r'
                    stringbuffer.append(c);
                    break;
            }
            setCursor(false, byte0);
        }

        if (byte0 == SEARCHING_TABLE_UPDATE)
            s = stringbuffer.toString().trim();
        else if (byte0 == SEARCHING_FIELD_VALUES_UPDATE)
            s1 = stringbuffer.toString().trim();
        else if (byte0 != SEARCHING_WHERE_UPDATE)
            ;
        parseTables(s);
    }

    private void parseDelete() {
        StringBuffer stringbuffer = new StringBuffer();
        String s = "";
        String s1 = "";
        boolean flag = false;
        byte byte0 = SEARCHING_FROM_DELETE;
        setCursor(false, byte0);
        int i = _stmt.length() - _currentIndex;
        _currentIndex--;
        for (int j = 0; j < i; j++) {
            _currentIndex++;
            setCursor(false, byte0);
            char c = _stmtOriginal.charAt(_currentIndex);
            if (c == '\r' || c == '\n')
                c = ' ';
            label0 : switch (byte0) {
                default :
                    break;

                case SEARCHING_FROM_DELETE : // '\016'
                    stringbuffer.append(c);
                    switch (c) {
                        case 77 : // 'M'
                        case 109 : // 'm'
                            if (j > 2) {
                                String s2 = _stmt.substring(_currentIndex - 3, _currentIndex + 1);
                                s2 = s2.toUpperCase();
                                if (s2.endsWith(FROM)) {
                                    stringbuffer = new StringBuffer();
                                    byte0 = SEARCHING_TABLE_DELETE;
                                }
                            }
                            break;
                    }
                    break;

                case SEARCHING_TABLE_DELETE : // '\017'
                    stringbuffer.append(c);
                    switch (c) {
                        default :
                            break;

                        case 'E' : // 'E'
                        case 'e' : // 'e'
                            if (j <= 3)
                                break label0;
                            String s3 = _stmt.substring(_currentIndex - 4, _currentIndex + 1);
                            s3 = s3.toUpperCase();
                            if (s3.endsWith(WHERE)) {
                                s = stringbuffer.toString();
                                s = s.substring(0, s.length() - 6).trim();
                                stringbuffer = new StringBuffer();
                                byte0 = SEARCHING_WHERE_DELETE;
                            }
                            break;
                    }
                    break;

                case SEARCHING_WHERE_DELETE : // '\020'
                    stringbuffer.append(c);
                    break;
            }
            setCursor(false, byte0);
        }

        if (byte0 == SEARCHING_TABLE_DELETE)
            s = stringbuffer.toString().trim();
        else if (byte0 != SEARCHING_WHERE_DELETE)
            ;
        parseTables(s);
    }

    private void parseUse() {
    }

    private void parseDrop() {
    }

    private void parseShow() {
    }

    private void parseAlter() {
    }

    private void parseGrant() {
    }

    private void parseMerge() {
    }

    private void parseRename() {
    }

    private void parseRevoke() {
    }

    private void parseCreate() {
    }

    private void parseReplace() {
    }

    private void parseTruncate() {
    }

    private void parseTables(String s) {
        String s1 = s.toUpperCase();
        if (s1.indexOf("JOIN") > -1)
            parseJoinTables(s);
        else if (s1.indexOf("(") > -1)
            parseJoinTables(s);
        else if (s1.indexOf(")") > -1)
            parseJoinTables(s);
        else
            parseCommaTables(s);
    }

    private void parseCommaTables(String s) {
        StringBuffer stringbuffer = new StringBuffer();
        Object obj = null;
        boolean flag = false;
        int i = s.length();
        for (int j = 0; j < i; j++) {
            char c = s.charAt(j);
            switch (c) {
                case ' ' : // ' '
                    stringbuffer.append(c);
                    break;

                case '[' : // '['
                    boolean flag1 = true;
                    break;

                case ']' : // ']'
                    boolean flag2 = false;
                    break;

                case ',' : // ','
                    String s2 = stringbuffer.toString();
                    ArrayList arraylist = tokenizeTables(s2);
                    if (arraylist.size() == 1)
                        _tables.add((String) arraylist.get(0));
                    else if (arraylist.size() == 2) {
                        _tables.add((String) arraylist.get(0));
                        _aliases.put(((String) arraylist.get(1)).toUpperCase(), (String) arraylist.get(0));
                    } else {
                        _tables.add((String) arraylist.get(0));
                        _aliases.put(((String) arraylist.get(2)).toUpperCase(), (String) arraylist.get(0));
                    }
                    stringbuffer = new StringBuffer();
                    break;

                default :
                    stringbuffer.append(c);
                    break;
            }
        }

        if (stringbuffer.length() > 0) {
            String s1 = stringbuffer.toString();
            ArrayList arraylist1 = tokenizeTables(s1);
            if (arraylist1.size() == 1)
                _tables.add((String) arraylist1.get(0));
            else if (arraylist1.size() == 2) {
                _tables.add((String) arraylist1.get(0));
                _aliases.put(((String) arraylist1.get(1)).toUpperCase(), (String) arraylist1.get(0));
            } else {
                _tables.add((String) arraylist1.get(0));
                _aliases.put(((String) arraylist1.get(2)).toUpperCase(), (String) arraylist1.get(0));
            }
            stringbuffer = new StringBuffer();
        }
    }

    private void parseJoinTables(String s) {
        ArrayList arraylist = tokenizeJoinTables(s);
        int i = SEARCHING_FIELDS_SELECT;
        int j = arraylist.size();
        for (int k = 0; k < j; k++) {
            String s1 = (String) arraylist.get(k);
            String s2 = s1.toUpperCase();
            switch (i) {
                default :
                    break;

                case SEARCHING_FIELDS_SELECT : // '\001'
                    _tables.add(s1);
                    if (k + 1 < j) {
                        String s3 = (String) arraylist.get(k + 1);
                        s3 = s3.toUpperCase();
                        if (s3.equals(AS)) {
                            if (k + 2 < j) {
                                String s7 = (String) arraylist.get(k + 2);
                                _aliases.put(s7.toUpperCase(), s1);
                                k += 2;
                            }
                        } else if (!s3.equals(INNER) && !s3.equals(LEFT) && !s3.equals(RIGHT) && !s3.equals(FULL)
                                && !s3.equals(CROSS) && !s3.equals(STRAIGHT) && !s3.equals(NATURAL)) {
                            _aliases.put(s3.toUpperCase(), s1);
                            k++;
                        }
                    }
                    i = SEARCHING_TABLES_SELECT;
                    break;

                case SEARCHING_TABLES_SELECT : // '\002'
                    if (s2.equals(JOIN)) {
                        i = SEARCHING_FIELDS_SELECT_WHERE;
                        break;
                    }
                    if (!s2.equals(INNER) && !s2.equals(LEFT) && !s2.equals(RIGHT) && !s2.equals(FULL) && !s2.equals(CROSS)
                            && !s2.equals(STRAIGHT) && !s2.equals(NATURAL)) {
                        i = SEARCHING_FIELDS_SELECT_WHERE;
                        k--;
                        break;
                    }
                    for (String s4 = s2; k + 1 < j && !s4.equals(JOIN); s4 = s4.toUpperCase()) {
                        k++;
                        s4 = (String) arraylist.get(k);
                    }

                    i = SEARCHING_FIELDS_SELECT_WHERE;
                    break;

                case SEARCHING_FIELDS_SELECT_WHERE : // '\003'
                    _tables.add(s1);
                    if (k + 1 < j) {
                        String s5 = (String) arraylist.get(k + 1);
                        s5 = s5.toUpperCase();
                        if (s5.equals(AS)) {
                            if (k + 2 < j) {
                                String s8 = (String) arraylist.get(k + 2);
                                _aliases.put(s8.toUpperCase(), s1);
                                k += 2;
                            }
                        } else if (!s5.equals(INNER) && !s5.equals(LEFT) && !s5.equals(RIGHT) && !s5.equals(FULL)
                                && !s5.equals(CROSS) && !s5.equals(STRAIGHT) && !s5.equals(NATURAL)) {
                            _aliases.put(s5.toUpperCase(), s1);
                            k++;
                        }
                    }
                    i = SEARCHING_FIELDS_SELECT_ORDER_BY;
                    break;

                case SEARCHING_FIELDS_SELECT_ORDER_BY : // '\004'
                    for (String s6 = s2; k + 1 < j && !s6.equals(INNER) && !s6.equals(LEFT) && !s6.equals(RIGHT)
                            && !s6.equals(FULL) && !s6.equals(CROSS) && !s6.equals(STRAIGHT) && !s6.equals(NATURAL)
                            && !s6.equals(JOIN); s6 = s6.toUpperCase()) {
                        k++;
                        s6 = (String) arraylist.get(k);
                    }

                    k--;
                    i = SEARCHING_TABLES_SELECT;
                    break;
            }
        }

    }

    private ArrayList tokenizeTables(String s) {
        StringBuffer stringbuffer = new StringBuffer();
        ArrayList<String> arraylist = new ArrayList<String>();
        boolean flag = false;
        boolean flag1 = false;
        int i = s.length();
        for (int j = 0; j < i; j++) {
            char c = s.charAt(j);
            switch (c) {
                case ' ' : // ' '
                    if (flag1 || flag) {
                        stringbuffer.append(c);
                        break;
                    }
                    String s2 = stringbuffer.toString().trim();
                    if (s2.length() > 0) {
                        arraylist.add(s2);
                        stringbuffer = new StringBuffer();
                    }
                    break;

                case '[' : // '['
                    flag = true;
                    break;

                case ']' : // ']'
                    flag = false;
                    break;

                case '"' : // '"'
                    if (!flag)
                        flag = true;
                    else
                        flag = false;
                    break;

                case '\'' : // '\''
                    if (flag1)
                        flag1 = false;
                    else
                        flag1 = true;
                    stringbuffer.append(c);
                    break;

                default :
                    stringbuffer.append(c);
                    break;
            }
        }

        if (stringbuffer.length() > 0) {
            String s1 = stringbuffer.toString().trim();
            if (s1.length() > 0)
                arraylist.add(s1);
        }
        return arraylist;
    }

    private ArrayList tokenizeJoinTables(String s) {
        StringBuffer stringbuffer = new StringBuffer();
        ArrayList<String> arraylist = new ArrayList<String>();
        boolean flag = false;
        boolean flag1 = false;
        int i = s.length();
        for (int j = 0; j < i; j++) {
            char c = s.charAt(j);
            switch (c) {
                case ' ' : // ' '
                    if (flag1 || flag) {
                        stringbuffer.append(c);
                        break;
                    }
                    String s2 = stringbuffer.toString().trim();
                    if (s2.length() > 0) {
                        arraylist.add(s2);
                        stringbuffer = new StringBuffer();
                    }
                    break;

                case '(' : // '('
                case ')' : // ')'
                    break;

                case '"' : // '"'
                    if (!flag)
                        flag = true;
                    else
                        flag = false;
                    break;

                case '[' : // '['
                    flag = true;
                    break;

                case ']' : // ']'
                    flag = false;
                    break;

                case '\'' : // '\''
                    if (flag1)
                        flag1 = false;
                    else
                        flag1 = true;
                    stringbuffer.append(c);
                    break;

                default :
                    stringbuffer.append(c);
                    break;
            }
        }

        if (stringbuffer.length() > 0) {
            String s1 = stringbuffer.toString().trim();
            if (s1.length() > 0)
                arraylist.add(s1);
        }
        return arraylist;
    }

    static {
        _keyWordTerminators = new HashMap<String, String>();
        _keyWordTerminators.put(",", ",");
        _keyWordTerminators.put(";", ";");
        _keyWordTerminators.put(" ", " ");
        _keyWordTerminators.put("\r", "\r");
        _keyWordTerminators.put("\n", "\n");
        _keyWordTerminators.put("\t", "\t");
        _keyWordTerminators.put("\"", "\"");
        _keyWordTerminators.put("'", "'");
    }
}

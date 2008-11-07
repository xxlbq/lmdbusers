/**
 * $Id: StatementConstants.java,v 1.1 2006/12/04 02:46:32 lijc Exp $
 */
package com.livedoor.dbm.scripts;

public interface StatementConstants {

    public static final int IN_KEYWORD = 0;
    public static final int IN_FIELDS = 1;
    public static final int IN_FIELDS_CRITERIA = 2;
    public static final int IN_FIELDS_VALUE = 3;
    public static final int IN_TABLE = 4;
    
    public static final int STMT_INVALID = 0;
    public static final int STMT_SELECT = 1;
    public static final int STMT_INSERT = 2;
    public static final int STMT_UPDATE = 3;
    public static final int STMT_DELETE = 4;
    public static final int STMT_MERGE = 5;
    public static final int STMT_TRUNCATE = 6;
    public static final int STMT_USE = 7;
    public static final int STMT_SHOW = 8;
    public static final int STMT_REPLACE = 9;
    public static final int STMT_CREATE = 10;
    public static final int STMT_DROP = 11;
    public static final int STMT_ALTER = 12;
    public static final int STMT_RENAME = 13;
    public static final int STMT_GRANT = 14;
    public static final int STMT_REVOKE = 15;
}

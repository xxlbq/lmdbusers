/**
 * $Id: StatementParser.java,v 1.1 2006/12/04 02:46:32 lijc Exp $
 */
package com.livedoor.dbm.scripts;

import java.util.ArrayList;

public abstract class StatementParser implements StatementConstants {

    public StatementParser() {
    }

    public abstract int getStmtType();

    public abstract int getStmtLocation();

    public abstract ArrayList getTables();

    public abstract String getTableByAlias(String s);

    public abstract String getAliasByTable(String s);

    public abstract void parseStatement(String s, int i);

    public static void fullTablePath(String s, StringBuffer tableBuffer, StringBuffer schemaBuffer) {
        boolean inTable = true;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            switch (c) {
                case 46 : // '.'
                    inTable = false;
                    break;

                default :
                    if (inTable)
                        tableBuffer.insert(0, c);
                    else
                        schemaBuffer.insert(0, c);
                    break;
            }
        }
    }

    public static void fullColumnPath(String s, StringBuffer columnBuffer, StringBuffer tableAliasBuffer, StringBuffer schemaBuffer) {
        int i = 0;
        for (int j = s.length() - 1; j >= 0; j--) {
            char c = s.charAt(j);
            if (c == '=')
                break;
            switch (c) {
                case 46 : // '.'
                    i++;
                    break;

                default :
                    if (i == 0) {
                        columnBuffer.insert(0, c);
                        break;
                    }
                    if (i == 1)
                        tableAliasBuffer.insert(0, c);
                    else
                        schemaBuffer.insert(0, c);
                    break;
            }
        }

    }
}

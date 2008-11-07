/**
 * $Id: SQLCompletionQuery.java,v 1.7 2006/12/08 02:52:12 lijc Exp $
 */
package com.livedoor.dbm.ldeditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import com.livedoor.dbm.constants.DBColumnType;
import com.livedoor.dbm.db.DBMDataMetaInfo;
import com.livedoor.dbm.db.DBMDataResult;
import com.livedoor.dbm.db.DBMSqlExecuter;
import com.livedoor.dbm.exception.DBMException;
import com.livedoor.dbm.scripts.ScriptStatement;
import com.livedoor.dbm.scripts.StatementConstants;
import com.livedoor.dbm.scripts.StatementParser;
import com.livedoor.dbm.scripts.engine.LDScriptParser;
import com.livedoor.dbm.scripts.engine.LDStatementParser;
import com.livedoor.dbm.util.StringUtil;

public class SQLCompletionQuery implements CompletionQuery {

    public static final int TABLE_COMPLETION = 1;
    public static final int COLUMN_COMPLETION = 2;
    public static final int UNDETERMINED_COMPLETION = 3;
    public static final int DATATYPE_COMPLETION = 4;
    public static final int BUILTIN_FUNC_COMPLETION = 5;
    public static final int USER_FUNC_COMPLETION = 6;

    public static final String SCHEMACACHE = "SCHEMACACHE";
    public static final String TABLECACHE = "TABLECACHE";
    public static final String CURRENTDATABASE = "CURRENTDATABASE";
    public static final String CURRENTSCHEMA = "CURRENTSCHEMA";
    public static final String DBMSQLEXECUTER = "DBMSQLEXECUTER";

    private static final String[] tableKeyWords = {"FROM ", "UPDATE ", "INTO ", "TABLE ", "JOIN "};
    private static final String[] columnKeyWords = {"SELECT ", "WHERE ", "AND ", "ON ", "OR ", "SET ", "ORDER BY ", "= ", "="};

    private Document document;

    public SQLCompletionQuery() {
    }

    public List<String> query(JTextComponent jtext, int i) {
        int caretPos = jtext.getCaretPosition();
        if(caretPos==0)
            return null;
        
        int backward = 28;
        if (caretPos < backward)
            backward = caretPos;

        document = jtext.getDocument();
        String text = "";
        try {
            text = document.getText(caretPos - backward, backward).toUpperCase();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        if (text.endsWith(" ") && 
                getType(text) == UNDETERMINED_COMPLETION)
            return null;

        StringBuffer firstBuffer = new StringBuffer(text.length());
        StringBuffer secondBuffer = new StringBuffer(text.length());
        boolean inSecond = true;
        for (int j1 = text.length() - 1; j1 >= 0; j1--) {
            char c = text.charAt(j1);
            switch (c) {
                case 32 : // ' '
                case 40 : // '('
                case 44 : // ','
                    inSecond = false;
                    break;
            }
            if (inSecond)
                secondBuffer.insert(0, c);
            else
                firstBuffer.insert(0, c);
        }

        byte type = UNDETERMINED_COMPLETION;
        type = getType(firstBuffer.toString());

        ScriptStatement stmt = getScriptStatement(jtext, caretPos);
        if (stmt == null)
            return null;
        
        LDStatementParser stmtParser = new LDStatementParser();
        boolean parsed = false;
        if (type == UNDETERMINED_COMPLETION) {
            parsed = true;
            stmtParser.parseStatement(stmt.statement, caretPos - stmt.beginChar);
            if (stmtParser.getStmtLocation() == StatementConstants.IN_TABLE)
                type = TABLE_COMPLETION;
            if (stmtParser.getStmtLocation() == StatementConstants.IN_FIELDS
                    || stmtParser.getStmtLocation() == StatementConstants.IN_FIELDS_CRITERIA)
                type = COLUMN_COMPLETION;
        }

        if (type == TABLE_COMPLETION) {
            StringBuffer tableBuffer = new StringBuffer();
            StringBuffer schemaBuffer = new StringBuffer();
            StatementParser.fullTablePath(secondBuffer.toString(), tableBuffer, schemaBuffer);
            if (tableBuffer.length() > 0)
                return null;

            String schema = "";
            if (schemaBuffer.length() > 0)
                schema = schemaBuffer.toString();
            else
                schema = getCurrentSchema();

            return getTables(schema);
        }

        if (type == COLUMN_COMPLETION) {
            if (!parsed)
                stmtParser.parseStatement(stmt.statement, caretPos - stmt.beginChar);

            StringBuffer columnBuffer = new StringBuffer();
            StringBuffer tableAliasBuffer = new StringBuffer();
            StringBuffer schemaBuffer = new StringBuffer();
            StatementParser.fullColumnPath(secondBuffer.toString(), columnBuffer, tableAliasBuffer, schemaBuffer);
            if (columnBuffer.length() > 0)
                return null;

            if (schemaBuffer.length() == 0)
                schemaBuffer.append(getCurrentSchema());

            if (tableAliasBuffer.length() < 1) {
                List<String> columns = new ArrayList<String>();

                ArrayList tables = stmtParser.getTables();
                for (int k1 = 0; k1 < tables.size(); k1++) {
                    String table = (String) tables.get(k1);
                    StringBuffer tblBuffer = new StringBuffer();
                    StringBuffer schBuffer = new StringBuffer();
                    StatementParser.fullTablePath(table, tblBuffer, schBuffer);
                    table = tblBuffer.toString();
                    if (schBuffer.length() > 0)
                        schemaBuffer = schBuffer;

                    columns.addAll(getColumns(schemaBuffer.toString(), table));
                }

                return columns;
            } else {
                String table = stmtParser.getTableByAlias(tableAliasBuffer.toString());
                if (table.equals(tableAliasBuffer.toString())) {
                    return null;
                }

                StringBuffer tblBuffer = new StringBuffer();
                StringBuffer schBuffer = new StringBuffer();
                StatementParser.fullTablePath(table, tblBuffer, schBuffer);
                table = tblBuffer.toString();
                if (schBuffer.length() > 0)
                    schemaBuffer = schBuffer;
                return getColumns(schemaBuffer.toString(), table);
            }
        }

        return null;
    }

    private String getCurrentSchema() {
        return (String) document.getProperty(CURRENTSCHEMA);
    }

    private String getCurrentDatabase() {
        return (String) document.getProperty(CURRENTDATABASE);
    }

    private DBMSqlExecuter getSqlExecuter() {
        return (DBMSqlExecuter) document.getProperty(DBMSQLEXECUTER);
    }

    private List<String> getTables(String schema) {
        Map<String, List<String>> schemaCache = (Map) document.getProperty(SCHEMACACHE);
        if (schemaCache == null) {
            schemaCache = new HashMap<String, List<String>>();
            document.putProperty(SCHEMACACHE, schemaCache);
        }

        List<String> tables = schemaCache.get(schema);
        if (tables == null) {
            tables = getTablesFromDB(schema);
            schemaCache.put(schema, tables);
        }
        return tables;
    }

    private List<String> getTablesFromDB(String schema) {
        List<String> tableNames = new ArrayList<String>();
        DBMDataMetaInfo metaInfo = new DBMDataMetaInfo();
        metaInfo.setDatabaseName(getCurrentDatabase());
        metaInfo.setSchemaName(schema);

        try {
            DBMSqlExecuter sqlExecuter = getSqlExecuter();
            tableNames = sqlExecuter.getTableNames(metaInfo);
        } catch (DBMException e) {
            e.printStackTrace();
        }
        return tableNames;
    }

    private List<String> getColumns(String schema, String table) {
        Map<String, List<String>> tableCache = (Map) document.getProperty(TABLECACHE);
        if (tableCache == null) {
            tableCache = new HashMap<String, List<String>>();
            document.putProperty(TABLECACHE, tableCache);
        }

        String key = schema + "." + table;
        List<String> columns = tableCache.get(key);
        if (columns == null) {
            columns = getColumnsFromDB(schema, table);
            tableCache.put(key, columns);
        }
        return columns;
    }

    private List<String> getColumnsFromDB(String schema, String table) {
        List<String> columnNames = new ArrayList<String>();
        DBMDataMetaInfo metaInfo = new DBMDataMetaInfo();
        metaInfo.setDatabaseName(getCurrentDatabase());
        metaInfo.setSchemaName(schema);
        metaInfo.setTableName(table);

        try {
            DBMSqlExecuter sqlExecuter = getSqlExecuter();
            DBMDataResult result = sqlExecuter.getColumns(metaInfo);
            List<Map> columns = result.getData();
            for (Map column : columns) {
                columnNames.add((String) column.get(DBColumnType.COLUMN_NAME));
            }
        } catch (DBMException e) {
            e.printStackTrace();
        }
        return columnNames;
    }

    private ScriptStatement getScriptStatement(JTextComponent jtext, int pos) {
        LDScriptParser scriptParser = new LDScriptParser(false, false);
        scriptParser.parseScript(jtext.getText());
        return scriptParser.getStatementByCharPosition(pos);
    }

    private byte getType(String text) {
        byte type = UNDETERMINED_COMPLETION;

        for (String keyWords : tableKeyWords) {
            if (text.endsWith(keyWords))
                return TABLE_COMPLETION;
        }

        for (String keyWords : columnKeyWords) {
            if (text.endsWith(keyWords))
                return COLUMN_COMPLETION;
        }
        return type;
    }
}

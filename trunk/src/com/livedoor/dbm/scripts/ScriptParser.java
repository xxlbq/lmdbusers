/**
 * $Id: ScriptParser.java,v 1.1 2006/12/04 02:46:32 lijc Exp $
 */
package com.livedoor.dbm.scripts;

import java.util.ArrayList;
import java.util.Date;

public abstract class ScriptParser {

    public Date statTimeStart;
    public Date statTimeEnd;
    public long statParseTime;
    public int statNumberOfStatements;
    public int statNumberOfINSERT;
    public int statNumberOfUPDATE;
    public int statNumberOfDELETE;
    public int statNumberOfSELECT;
    public int statNumberOfUserTransactions;

    public ScriptParser() {
        statTimeStart = null;
        statTimeEnd = null;
        statParseTime = 0L;
        statNumberOfStatements = 0;
        statNumberOfINSERT = 0;
        statNumberOfUPDATE = 0;
        statNumberOfDELETE = 0;
        statNumberOfSELECT = 0;
        statNumberOfUserTransactions = 0;
    }

    public abstract void parseScript(String s);

    public abstract ArrayList getScriptStatements();

    public abstract ArrayList getParseResultColumns();

    public abstract ArrayList getParseResults();
}

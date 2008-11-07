/**
 * $Id: CompletionQuery.java,v 1.1 2006/12/04 02:46:33 lijc Exp $
 */
package com.livedoor.dbm.ldeditor;

import java.util.List;

import javax.swing.text.JTextComponent;

public interface CompletionQuery {

    public List<String> query(JTextComponent jtext, int i);
    
}

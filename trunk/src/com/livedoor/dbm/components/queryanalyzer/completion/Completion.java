/**
 * $Id: Completion.java,v 1.6 2006/12/05 08:05:07 lijc Exp $
 */
package com.livedoor.dbm.components.queryanalyzer.completion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import com.livedoor.dbm.ldeditor.CompletionQuery;
import com.livedoor.dbm.ldeditor.SQLCompletionQuery;

public class Completion implements ActionListener {
    private JTextComponent jtext;
    private Document document;

    private Timer docChangeTimer;
    private DocumentListener docListener;
    
    private java.util.Timer timer = new java.util.Timer();
    
    private ToolsPopupController controller;
    private List<String> lastResult;
    
    private int caretPos = -1;
    
    private CompletionQuery query;
    
    private CancelableRunnable currentTask = new CancelableRunnable() {
        public void run() {}
    };

    public Completion(JTextComponent jtext) {
        this.jtext = jtext;
        this.document = jtext.getDocument();
        
        docChangeTimer = new Timer(0, this);
        docChangeTimer.setRepeats(false);
        
        docListener = new CompletionDocumentListener();
        document.addDocumentListener(docListener);
        
        controller = new ToolsPopupController((JEditorPane)jtext);
    }
    
    class CompletionDocumentListener implements DocumentListener {
        private void processTimer(){
            docChangeTimer.stop();
            invalidateLastResult();
            docChangeTimer.setInitialDelay(200);
            docChangeTimer.setDelay(200);
            docChangeTimer.start();
        }
        
        public void changedUpdate(DocumentEvent e) {
            processTimer();
        }
        
        public void insertUpdate(DocumentEvent e) {
            processTimer();
        }
        
        public void removeUpdate(DocumentEvent e) {
            processTimer();
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        
        if((caretPos!=-1) && (jtext.getCaret().getDot() - caretPos>0))
            return;
        
        caretPos = jtext.getCaretPosition();
        currentTask.cancel();
        
        class QueryTask extends CancelableRunnable {
            public void run() {
                if (cancelled()) return;
                
                try {
                    performQuery(jtext);
                } catch ( ThreadDeath td ) {
                    throw td;
                } catch (Throwable exc){
                    exc.printStackTrace();
                }finally {
                    if (cancelled()) return;
                    
                    timer.schedule(new java.util.TimerTask() {
                        public void run() {
                            if (cancelled()) return;
                            
                            performResults();
                        }
                    }, 800);
                }
            }
        };
        
        CancelableRunnable task = new QueryTask();
        currentTask = task;
        new Thread(task).start();
    }

    public void invalidateLastResult() {
        currentTask.cancel();
        lastResult = null;
        caretPos = -1;
    }

    private void performQuery(JTextComponent jtext) {
        long start = System.currentTimeMillis();
        
        try {
            lastResult = getQuery().query(jtext, caretPos);
        } finally {
            // System.out.println("performQuery took " + (System.currentTimeMillis() - start) + "ms");
        }
    }
    
    private void performResults() {
        if(lastResult != null) {
            controller.clear();
            for (String item : lastResult) {
                controller.addAction(item);
            }
            controller.show();
        } else {
            caretPos = -1;
        }
    }
    
    public CompletionQuery getQuery() {
        if(query==null)
            query = new SQLCompletionQuery();
        return query;
    }
    
    abstract class CancelableRunnable implements Runnable {
        private boolean cancelled = false;
                
        boolean cancelled() {
            return cancelled;
        }
        
        void cancel() {
            cancelled = true;
        }
    }
}

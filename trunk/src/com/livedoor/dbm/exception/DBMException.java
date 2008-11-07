/**
 * $Id: DBMException.java,v 1.2 2006/10/30 02:01:16 lijc Exp $
 */
package com.livedoor.dbm.exception;

public class DBMException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public DBMException() {
        super();
    }

    /**
     * @param message
     */
    public DBMException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public DBMException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public DBMException(Throwable cause) {
        super(cause);
    }

}

package com.tibco.xpd.bw.eai;

public class BWLinkException extends Exception {

    /** Serial ID. */
    private static final long serialVersionUID = 1L;

    public BWLinkException(String message) {
        super(message);
    }

    public BWLinkException(String message, Throwable t) {
        super(message, t);
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

/**
 * @author nwilson
 */
public class XmlException extends Exception {

    /** Default serial ID. */
    private static final long serialVersionUID = 1L;

    /**
     * @param message The error message.
     */
    public XmlException(String message) {
        super(message);
    }

}

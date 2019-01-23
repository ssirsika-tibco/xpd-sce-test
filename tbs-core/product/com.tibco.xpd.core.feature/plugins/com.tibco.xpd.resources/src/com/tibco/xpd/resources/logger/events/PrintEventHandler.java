/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.resources.logger.events;

import java.io.PrintStream;

/**
 * Prints event log to the PrintStream.
 * 
 * @author jarciuch
 * @since 5 Dec 2011
 */
public class PrintEventHandler extends OutputEventHandler {

    protected final PrintStream ps;

    /**
     * Creates an instance passing a stream to use.
     */
    public PrintEventHandler(PrintStream ps) {
        this.ps = ps;
    }

    /**
     * Creates an instance with a default stream.
     */
    public PrintEventHandler() {
        this(System.out);
    }

    /**
     * @see com.tibco.xpd.resources.logger.events.OutputEventHandler#outputLine(java.lang.String)
     * 
     * @param message
     */
    @Override
    protected void outputLine(String message) {
        ps.println(message);
    }
}

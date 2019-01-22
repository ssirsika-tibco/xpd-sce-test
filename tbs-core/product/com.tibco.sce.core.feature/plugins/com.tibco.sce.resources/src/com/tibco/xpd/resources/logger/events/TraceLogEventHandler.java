/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.resources.logger.events;

import com.tibco.xpd.resources.logger.Logger;

/**
 * event handler that logs output to a trace log (which can be switched on via
 * .options
 * 
 * @author aallway
 * @since 22 Dec 2011
 */
public class TraceLogEventHandler extends OutputEventHandler {

    private Logger logger;

    private String traceCategory;

    /**
     * @param logger
     * @param traceCategory
     */
    public TraceLogEventHandler(Logger logger, String traceCategory) {
        super();
        this.logger = logger;
        this.traceCategory = traceCategory;
    }

    /**
     * @see com.tibco.xpd.resources.logger.events.OutputEventHandler#outputLine(java.lang.String)
     * 
     * @param message
     */
    @Override
    protected void outputLine(String message) {
        logger.trace(traceCategory, message);
    }

}

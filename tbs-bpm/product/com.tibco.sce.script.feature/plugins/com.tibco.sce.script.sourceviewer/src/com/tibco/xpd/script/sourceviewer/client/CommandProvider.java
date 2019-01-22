/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.sourceviewer.client;

import org.eclipse.jface.text.IDocument;

/**
 * 
 * This is a call back method which will be called by the Script editor so that
 * the client can save the string.
 * 
 */
public interface CommandProvider {
    /**
     * This is a call back method which will be called by the Script editor so
     * that the client can save the string.
     * 
     * @param document
     * @param inputDetails
     */
    void executeSaveCommand(IDocument document);

}

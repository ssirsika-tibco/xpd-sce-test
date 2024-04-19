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
	 * This is a call back method which will be called by the Script editor so that the client can save the string.
	 * 
	 * @param document
	 * @return {true} if the save command is executed sucessfully or else returns {false} in when save command is not
	 *         executed or aborted.
	 */
	boolean executeSaveCommand(IDocument document);

	/**
	 * Get the existing non dirty script model which is present in the editior.
	 * @return
	 */
	public String getScriptFromModel();
}

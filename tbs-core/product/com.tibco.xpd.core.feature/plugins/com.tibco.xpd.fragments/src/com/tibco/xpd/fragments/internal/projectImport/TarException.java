/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
/*******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.tibco.xpd.fragments.internal.projectImport;

/**
 * Exception generated upon encountering corrupted tar files.
 */
public class TarException extends Exception {
	/**
	 * Generated serial version UID for this class.
	 */
	private static final long serialVersionUID = 2886671254518853528L;

    /**
     * Constructs a TarException without a detail string.
     */
    public TarException() {
    	super();
    }
	
	/**
     * Constructs a TarException with the specified detail string.
     *
     * @param s the detail string
     */
    public TarException(String s) {
    	super(s);
    }
	
    /**
     * Constructs a TarException with the specified detail string.
     *
     * @param s the detail string
     * @param cause the cause
     */
    public TarException(String s, Throwable cause) {
    	super(s, cause);
    }
}

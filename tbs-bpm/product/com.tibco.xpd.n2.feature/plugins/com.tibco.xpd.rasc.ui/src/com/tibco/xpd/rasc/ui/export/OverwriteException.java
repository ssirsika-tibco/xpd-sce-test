/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import com.tibco.xpd.rasc.ui.Messages;

/**
 * Exception when a file could not be overwritten.
 *
 * @author nwilson
 * @since 5 Apr 2019
 */
public class OverwriteException extends Exception {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param file
     *            The file name.
     */
    public OverwriteException(String file) {
        super(file);
    }

    /**
     * @see java.lang.Throwable#getMessage()
     *
     * @return
     */
    @Override
    public String getMessage() {
        return String.format(Messages.OverwriteException_FileMessage,
                super.getMessage());
    }

}

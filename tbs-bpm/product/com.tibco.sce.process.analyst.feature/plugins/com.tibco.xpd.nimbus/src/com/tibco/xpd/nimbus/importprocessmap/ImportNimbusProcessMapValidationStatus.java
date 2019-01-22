/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.nimbus.importprocessmap;

/**
 * Nimbus import validation status (used by validation import wizard page)
 * 
 * @author aallway
 * @since 16 Oct 2012
 */
class ImportNimbusProcessMapValidationStatus {
    String message;

    String file;

    int severity;

    /**
     * @param severity
     * @param message
     * @param file
     */
    public ImportNimbusProcessMapValidationStatus(int severity, String message,
            String file) {
        super();
        this.severity = severity;
        this.message = message;
        this.file = file;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * @return the severity
     */
    public int getSeverity() {
        return severity;
    }

}
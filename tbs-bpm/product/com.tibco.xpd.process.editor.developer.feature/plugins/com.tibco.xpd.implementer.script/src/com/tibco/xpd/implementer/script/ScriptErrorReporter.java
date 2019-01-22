/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.Collection;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

/**
 * @author nwilson
 */
public class ScriptErrorReporter implements ErrorReporter {
    /** A collection of errors. */
    private Collection<String> errors;

    /** A collection of warnings. */
    private Collection<String> warnings;

    /**
     * Constructor.
     */
    public ScriptErrorReporter() {
        errors = new ArrayList<String>();
        warnings = new ArrayList<String>();
    }

    /**
     * @return The error message.
     */
    public String getErrorMessage() {
        boolean first = true;
        StringBuffer message = new StringBuffer();
        for (String error : errors) {
            if (first) {
                first = false;
            } else {
                message.append("\n"); //$NON-NLS-1$
            }
            message.append(error);
        }
        return message.toString();
    }

    /**
     * @param message The warning message.
     * @param source The source.
     * @param line The line number.
     * @param lineSource The line source.
     * @param lineOffset The line offset.
     * @see org.mozilla.javascript.ErrorReporter#error(java.lang.String,
     *      java.lang.String, int, java.lang.String, int)
     */
    public void error(String message, String source, int line,
            String lineSource, int lineOffset) {
        errors.add(message);
    }

    /**
     * @param message The warning message.
     * @param source The source.
     * @param line The line number.
     * @param lineSource The line source.
     * @param lineOffset The line offset.
     * @return The exception
     * @see org.mozilla.javascript.ErrorReporter#runtimeError(
     *      java.lang.String, java.lang.String, int, java.lang.String, int)
     */
    public EvaluatorException runtimeError(String message, String source,
            int line, String lineSource, int lineOffset) {
        return new EvaluatorException(message);
    }

    /**
     * @param message The warning message.
     * @param source The source.
     * @param line The line number.
     * @param lineSource The line source.
     * @param lineOffset The line offset.
     * @see org.mozilla.javascript.ErrorReporter#warning(java.lang.String,
     *      java.lang.String, int, java.lang.String, int)
     */
    public void warning(String message, String source, int line,
            String lineSource, int lineOffset) {
        warnings.add(message);
    }

    /**
     * @return true if there are errors.
     */
    public boolean hasErrors() {
        return errors.size() > 0;
    }
}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.api;

/**
 * Simple string builder for JavaScript creation.
 * 
 * @author aallway
 * @since 28 Apr 2015
 */
public class JavaScriptStringBuilder {

    private static final String INDENTATION_WHITESPACE = " "; //$NON-NLS-1$

    protected static final String NEWLINE_STRING = "\n"; //$NON-NLS-1$

    private final int indentStepSize = 4;

    private int indentationCounter = 0;

    private StringBuilder stringBuilder = new StringBuilder();

    /**
     * Standard script string builder.
     */
    public JavaScriptStringBuilder() {
    }

    /**
     * Add a comment to the script.
     * 
     * @param commentText
     *            - text to be appended to the given script
     */
    public void addComment(String commentText) {

        /*
         * BY default we'll add whitespace above comment lines (as these usually
         * denote discreet code blocks.
         */
        if (stringBuilder.length() > 0) {
            stringBuilder.append(NEWLINE_STRING);
        }

        addLine("// " + commentText); //$NON-NLS-1$
    }

    /**
     * Add a simple line of text to the script.
     * 
     * @param lineText
     *            - text to be appended to the given script
     */
    public void addLine(String lineText) {
        this.addLine(lineText, false, false);
    }

    /**
     * 
     * @param lineText
     *            - text to be appended to the given script
     * @param startBlockAfterAdd
     *            - set true if you like to increase the indentation after
     *            adding teh line ( (e.g., for start loop text)
     * @param endBlockBeforeAdd
     *            - set true if you like to decrease the indentation before
     *            adding the line (e.g., for end loop text)
     */
    public void addLine(String lineText, boolean startBlockAfterAdd,
            boolean endBlockBeforeAdd) {

        if (endBlockBeforeAdd) {
            indentationCounter -= indentStepSize;
        }

        /*
         * add white spaces for indentation tyo start of text AND subsequent
         * lines in case we've been given multiple lines.
         */
        StringBuilder indent = new StringBuilder();

        for (int i = 0; i < indentationCounter; i++) {
            indent.append(INDENTATION_WHITESPACE);
        }

        stringBuilder.append(indent);

        stringBuilder.append(lineText.replaceAll("\n", "\n" + indent)); //$NON-NLS-1$ //$NON-NLS-2$
        stringBuilder.append(NEWLINE_STRING);

        if (startBlockAfterAdd) {
            indentationCounter += indentStepSize;
        }
    }

    /**
     * Append a simple piece of text (no indentation or newline termination is
     * applied!).
     * 
     * @param text
     */
    public void append(String text) {
        stringBuilder.append(text);
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return stringBuilder.toString();
    }

}

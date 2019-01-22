/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Status;
import org.eclipse.wst.wsdl.validation.internal.IValidationMessage;

/**
 * Extension to the {@link Status} object to provide
 * <code>org.eclipse.wst.wsdl.validation.internal.IValidationMessage</code>
 * specific details as returned from the WST validation.
 * 
 * @author njpatel
 * 
 * @since 3.5
 */
public class WsdlValidationStatus extends Status {

    private int col;

    private int line;

    private List<?> nestedMsgs;

    private List<String> nestedMsgStrings;

    private String uri;

    protected WsdlValidationStatus(int severity, String pluginId,
            String message, String uri, int col, int line, List<?> nestedMsgs) {
        super(severity, pluginId, message);
        this.uri = uri;
        this.col = col;
        this.line = line;
        this.nestedMsgs = nestedMsgs;
    }

    /**
     * Returns the URI for the file that contains the validation message.
     * 
     * @return The URI for the file that contains the validation message.
     */
    public String getUri() {
        return uri;
    }

    /**
     * Return the column where this validation message is located.
     * 
     * @return The column where this validation message is located.
     */
    public int getColumn() {
        return col;
    }

    /**
     * Return the line where this validation message is located.
     * 
     * @return The line where this validation message is located.
     */
    public int getLine() {
        return line;
    }

    /**
     * Get the list of nested validation messages.
     * 
     * @see #getNestedMsgStrings()
     * 
     * @return The list of nested validation messages.
     */
    public List<?> getNestedMsgs() {
        return nestedMsgs;
    }

    /**
     * Get the nested messages in string form.
     * 
     * @return
     */
    @SuppressWarnings("restriction")
    public List<String> getNestedMsgStrings() {
        if (nestedMsgStrings == null) {
            nestedMsgStrings = new ArrayList<String>();

            for (Object next : nestedMsgs) {
                if (next instanceof IValidationMessage) {
                    String msg = ((IValidationMessage) next).getMessage();
                    if (msg != null) {
                        nestedMsgStrings.add(msg);
                    }
                }
            }
        }
        return nestedMsgStrings;
    }
}

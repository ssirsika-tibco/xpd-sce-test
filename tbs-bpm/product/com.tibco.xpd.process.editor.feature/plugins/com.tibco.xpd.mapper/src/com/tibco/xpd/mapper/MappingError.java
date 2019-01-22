/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author nwilson
 */
public class MappingError {
    /** The mapping. */
    private Mapping mapping;

    /** A collection of error messages. */
    private Collection<String> messages;

    /**
     * @param mapping
     *            The mapping.
     */
    public MappingError(Mapping mapping) {
        this.mapping = mapping;
        messages = new ArrayList<String>();
    }

    /**
     * @param message
     *            A collection of error messages.
     */
    public void addMessage(String message) {
        messages.add(message);
    }

    /**
     * @return The mapping.
     */
    public Mapping getMapping() {
        return mapping;
    }

    /**
     * @return A collection of error messages.
     */
    public Collection<String> getMessages() {
        return messages;
    }

    /**
     * @return The concatenated error message.
     */
    public String getMessage() {
        int numMsgs = 0;

        StringBuffer buffer = new StringBuffer();
        boolean first = true;
        for (String message : messages) {
            if (numMsgs++ > 5) {
                break;
            }
            if (first) {
                first = false;
            } else {
                buffer.append('\n');
            }
            if (messages.size() > 1) {
                buffer.append("-"); //$NON-NLS-1$
            }
            buffer.append(message);
        }
        return buffer.toString();
    }

}

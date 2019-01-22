/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.swt.widgets.Event;

/**
 * Interface to be implemented by property sections that use text controls that
 * need to verify their content.
 */
public interface TextFieldVerifier {
    /**
     * Verify the text.
     * 
     * @param event
     */
    void verifyText(Event event);
}

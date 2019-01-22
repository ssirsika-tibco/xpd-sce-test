/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wsdltobom.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author rsomayaj
 *
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.wsdltobom.internal.messages"; //$NON-NLS-1$

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.swagger.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Messages for internationalisation.
 *
 * @author nkelkar
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
			"com.tibco.xpd.rest.swagger.internal.messages";					//$NON-NLS-1$

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

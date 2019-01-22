/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.wm.resources.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * <p>
 * <i>Created: 6 Oct 2008</i>
 * </p>
 * 
 * @author glewis
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.wm.resources.ui.internal.messages"; //$NON-NLS-1$    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

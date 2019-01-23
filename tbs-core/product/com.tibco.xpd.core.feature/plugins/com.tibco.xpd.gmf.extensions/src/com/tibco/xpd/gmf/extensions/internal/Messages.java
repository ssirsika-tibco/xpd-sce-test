/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.gmf.extensions.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author aallway
 *
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.gmf.extensions.internal.messages"; //$NON-NLS-1$

    public static String PaletteButtonStackEditPart_PinOpen_tooltip;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

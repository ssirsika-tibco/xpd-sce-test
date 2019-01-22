/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.bpm.simplified.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author jarciuch
 * @since 15 Jun 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.bpm.simplified.ui.internal.messages"; //$NON-NLS-1$

    public static String SimplifiedUiPreferencePage_adjustCapabiliiesWhenSwitchingPerspective_label;

    public static String SimplifiedUiPreferencePage_perspectivePreference_desc;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

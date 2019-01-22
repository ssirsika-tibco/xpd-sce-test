/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.om.transform.de.script;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author agondal
 * @since 12 Oct 2012
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.om.transform.de.script.messages"; //$NON-NLS-1$

    public static String OrgQueryRQLScriptProvider__scriptInfoProvider_desc;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

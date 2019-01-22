/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards.classapi;

import org.eclipse.osgi.util.NLS;

/**
 * Messages
 *
 *
 * @author aallway
 * @since 3.3 (14 Oct 2009)
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.core.createtestwizards.classapi.messages"; //$NON-NLS-1$

    public static String CreateClassApiTestWizard_CreateClassApiTest_title;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

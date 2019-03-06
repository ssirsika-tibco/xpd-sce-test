/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.rasc.ui;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author nwilson
 * @since 6 Mar 2019
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.sce.rasc.ui.messages"; //$NON-NLS-1$

    public static String RascExportProjectSelectionPage_NoProjectSelectedError;
    public static String RascExportWizard_Description;

    public static String RascExportWizard_Title;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

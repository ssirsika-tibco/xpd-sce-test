/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author jarciuch
 * @since 4 Aug 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.rest.ui.internal.messages"; //$NON-NLS-1$

    public static String RestAssetWizardPage_CreateInitialRsd_label;

    public static String RestAssetWizardPage_DescriptorDetails_label;

    public static String RestAssetWizardPage_FilaName_label;

    public static String RestAssetWizardPage_RestServices_desc;

    public static String RestAssetWizardPage_RestServices_title;
    public static String RestServicesUtil_DefaultRsdFile_name;

    public static String RestServicesUtil_RestSf_name;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

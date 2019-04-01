/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.cdm.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author jarciuch
 * @since 27 Mar 2019
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.n2.cdm.internal.messages"; //$NON-NLS-1$

    public static String BomTransformer_cantResolveType;

    public static String CdmRascContributor_cantLoad_message;

    public static String CdmRascContributor_progress_message;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

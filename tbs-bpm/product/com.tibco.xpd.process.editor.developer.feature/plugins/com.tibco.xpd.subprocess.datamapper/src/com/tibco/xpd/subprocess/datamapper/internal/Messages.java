/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author sajain
 * @since 11 Dec 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.subprocess.datamapper.internal.messages"; //$NON-NLS-1$

    public static String CatchErrorDataReferenceContextProvider_MapFromError_label;

    public static String CSPDataMapperInSection_InputMappingTitle_message;

    public static String CSPDataMapperOutSection_OutputMappingTitle_message;

    public static String CEDataMapperSection_OutputMappingTitle_message;

    public static String SubProcessDataReferenceContextProvider_MapFromError_Label;

    public static String SubProcessDataReferenceContextProvider_MapFromSubProc_label;

    public static String SubProcessDataReferenceContextProvider_MapToSubProc_label;

    public static String GlobalDataErrorDataMapperMapFromErrorSection_SectionTitle;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

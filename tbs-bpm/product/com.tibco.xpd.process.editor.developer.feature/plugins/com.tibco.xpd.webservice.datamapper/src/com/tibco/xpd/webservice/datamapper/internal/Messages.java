/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author sajain
 * @since Jan 20, 2016
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.webservice.datamapper.internal.messages"; //$NON-NLS-1$

    public static String GlobalDataErrorDataMapperMapFromErrorSection_SectionTitle;

    public static String WebServiceDataMapperMapFromErrorSection_MapFromErrorSection_Label;

    public static String WebServiceDataMapperOutputFaultFromProcessSection_WSDataMapperInSection_ProcessDataToWSFaultSectionTitle_message;

    public static String WebServiceDataReferenceContextProvider_InputToProcess_label;

    public static String WebServiceDataReferenceContextProvider_InputToService_label;

    public static String WebServiceDataReferenceContextProvider_MapFromError_label;

    public static String WebServiceDataReferenceContextProvider_OutputFalutFromProcess_Label;

    public static String WebServiceDataReferenceContextProvider_OutputFromProcess_label;

    public static String WebServiceDataReferenceContextProvider_OutputFromService_label;

    public static String WSDataMapperInSection_InputMappingTitle_message;

    public static String WSDataMapperOutSection_OutputMappingTitle_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

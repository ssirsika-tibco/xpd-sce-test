/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author aallway
 * @since 1 Jul 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.rest.datamapper.internal.messages"; //$NON-NLS-1$

    public static String RestDataMapperInSection_RestInputMappingTitle_message;

    public static String RestDataMapperOutSection_RestOutputMappingTitle_message;

    public static String RestDataReferenceContextProvider_InputToService_Label;

    public static String RestDataReferenceContextProvider_OutputFromService_Label;

	public static String SwaggerDataMapperInSection_SwaggerInputMappingTitle_message;

	public static String SwaggerDataMapperOutSection_SwaggerOutputMappingTitle_message;

	public static String		AdvancedRestServiceInputMappingProperties_SetInputMappingExclusions_cmd;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

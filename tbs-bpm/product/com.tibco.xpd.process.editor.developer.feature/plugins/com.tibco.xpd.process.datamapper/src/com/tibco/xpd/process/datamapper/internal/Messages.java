/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author Ali
 * @since 22 Jan 2015
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.process.datamapper.internal.messages"; //$NON-NLS-1$

    public static String ProcessDataMapperScriptEditorSection_ShowMapperTabLinkTitle;

    public static String ProcessDataMapperSection_title;

    public static String AbstractExpressionScriptDataMapperProvider_CreateDataMapperContent_menu;

    public static String ProcessDataMapperFieldRefResolver_LiekMappingExclusion_label;

    public static String ProcessDataMapperFieldRefResolver_ArrayInflation_label;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

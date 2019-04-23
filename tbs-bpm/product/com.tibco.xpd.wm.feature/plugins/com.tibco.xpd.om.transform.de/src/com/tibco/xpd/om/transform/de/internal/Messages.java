/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.transform.de.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * <p>
 * <i>Created: 7 Apr 2008</i>
 * </p>
 * @author Jan Arciuchiewicz
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.om.transform.de.internal.messages"; //$NON-NLS-1$

    public static String OMModelBuilder_buildOMModelModule_desc;
    public static String QueryParticipantScriptDetailsProvider__scriptInfoProvider_desc0;
    public static String RQLScriptEditorSection_DescribeOrgModelParticQ_shortdesc;
    public static String RQLScriptEditorSection_OrganizationalModelScript;
    public static String RQLScriptEditorSection_SetParticipantQ_shortdesc;
    public static String RQLScriptEditorSection_SetParticipantQuery;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

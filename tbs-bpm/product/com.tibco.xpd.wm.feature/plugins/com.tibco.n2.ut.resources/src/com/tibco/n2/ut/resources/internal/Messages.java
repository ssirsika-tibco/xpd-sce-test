/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.n2.ut.resources.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author kthombar
 * @since 13-Oct-2013
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.n2.ut.resources.internal.messages"; //$NON-NLS-1$

    public static String DynamicOrgIdentifierContentProvider_NoDynamicIdentifiersToMap_label0;

    public static String ProcessDataInDynamicParticipantMappingReferenceResolver_DataRefContext_label;

    public static String ProcessDataToDynamicOrgIdentifierMappingCommandFactory_CreateProcessDataToDynamicIdentiferMappingCommand_label;

    public static String ProcessDataToDynamicOrgIdentifierMappingSection_ProcessDataToDynamicOrgIdentifierMapper_title0;

    public static String ProcessDataToDynamicOrgIdentifierMappingCommandFactory_RemoveProcessDataToOrgMapiing_label0;

    public static String ProcessDataToDynamicOrgIdentifierMappingRule_MappingTypeDescription_message;

    public static String WMDataMapperContentProvider_WorkItemAttributesFolder_label;

    public static String WMDataMapperContentProvider_WorkItemFolder_label;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

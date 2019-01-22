/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.export.progress;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author aallway
 * @since 1 Apr 2011
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.bom.xsdtransform.export.progress.messages"; //$NON-NLS-1$

    public static String Bom2XsdBuilder_AscertainingOutOfDateXsds_message;

    public static String Bom2XsdBuilder_AscertainingRootModels_message;

    public static String Bom2XsdBuilder_RemovingExternallyRefdXsds_message;

    public static String Bom2XsdBuilder_TransformingRootModels_message;

    public static String Bom2XsdBuilder_WsdlInlineSchemaValidation_message;

    public static String Bom2XsdBuilder_WsdlInlineSchemaNotValidated_message;

    public static String BOM2XSDTransformer_BuildXsdsFromBomsLeader_message;

    public static String BOM2XSDTransformer_CheckingProblemMarkers_message;

    public static String BOM2XSDTransformer_PerformingValidation_message;

    public static String BOM2XSDTransformer_SavingSchemas_message;

    public static String Bom2XsdMonitorMessages_BuildXsdsFromBomsLeader_message;

    public static String Bom2XsdMonitorMessages_CleanAnonymousTypes_message;

    public static String Bom2XsdMonitorMessages_CollectModelAndRef_message;

    public static String Bom2XsdMonitorMessages_CreateAttrForEnum_message;

    public static String Bom2XsdMonitorMessages_CreateAttrForPrim_message;

    public static String Bom2XsdMonitorMessages_CreateAttributeGroup_message;

    public static String Bom2XsdMonitorMessages_CreateDataType_message;

    public static String Bom2XsdMonitorMessages_CreateDataTypes_message;

    public static String Bom2XsdMonitorMessages_CreateElemForClass_message;

    public static String Bom2XsdMonitorMessages_CreateElemForEnum_message;

    public static String Bom2XsdMonitorMessages_CreateElemForPrim_message;

    public static String Bom2XsdMonitorMessages_CreateGroup_message;

    public static String Bom2XsdMonitorMessages_CreateSchema_message;

    public static String Bom2XsdMonitorMessages_CreateSchemas_message;

    public static String Bom2XsdMonitorMessages_CreateSimpleContent_message;

    public static String Bom2XsdMonitorMessages_CreateSimpleType_message;

    public static String Bom2XsdMonitorMessages_ProcessComplexType_message;

    public static String Bom2XsdMonitorMessages_ProcessDataTypes_message;

    public static String Bom2XsdMonitorMessages_ProcessInheritance_message;

    public static String Bom2XsdMonitorMessages_ProcessSimpleTypes_message;

    public static String Bom2XsdMonitorMessages_ProcessTopLevelElements_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

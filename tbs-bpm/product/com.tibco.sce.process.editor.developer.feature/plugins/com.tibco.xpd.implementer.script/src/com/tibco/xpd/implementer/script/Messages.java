/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import org.eclipse.osgi.util.NLS;

/**
 * @author nwilson
 */
public final class Messages extends NLS {
    /** The resource bundle id. */
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.implementer.script.messages"; //$NON-NLS-1$

    public static String AbstractActivityMessageAdapter_changeReferences;

    public static String AbstractActivityMessageAdapter_removeReferences;

    public static String JavaScriptExpressionParser_ScriptParserNotFound_message;

    public static String SubProcScriptUtil_EditScriptCommand;

    public static String PageflowScriptUtil_EditScriptCommand;

    public static String TaskServiceMessageAdapter_AssignWebServiceCommand;

    public static String TaskServiceMessageAdapter_ClearWebServiceCommand;

    public static String TaskServiceMessageAdapter_getSetGrammerCommand;

    public static String EventMessageAdapter_AssignWebServiceCommand;

    public static String EventMessageAdapter_ClearWebServiceCommand;

    public static String AbstractActivityMessageAdapter_AssignEndpointCommand;

    public static String AbstractActivityMessageAdapter_AssignIsRemoteCommand;

    public static String WsdlUtil_OperationNotFound_message;

    public static String xPathFieldParserNestedPathError;

    public static String xPathFieldParserVariableError;

    public static String Xpdl2WsdlUtil_EditScriptCommand;

    public static String XsdPath_anonymousTypeLabel;

    public static String XsdPath_AnyElementName_label;

    public static String XsdPath_AnyElementTypeName_label;

    public static String XsdPath_choice;

    public static String XsdPath_sequence;

    public static String BizServ_SendTaskAct_SharedRes_Prefix_label;

    public static String NamespacePrefixMapUtil_CantAccessActivityWSDL_message;

    public static String NamespacePrefixMapUtil_DifferentPrefixesForNamespace_message;

    public static String NamespacePrefixMapUtil_DuplicatenamespacesForPrefix_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    /**
     * Private constructor.
     */
    private Messages() {
    }

}

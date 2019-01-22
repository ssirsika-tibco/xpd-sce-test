/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.wsdlgen;

import org.eclipse.osgi.util.NLS;

/**
 * @author nwilson
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.xpd.wsdlgen.messages"; //$NON-NLS-1$

    public static String RemoveExtAttribResolution_dialog_title;

    public static String RemoveExtAttribResolution_ResolutionDesc_shortdesc;

    public static String WsdlGenBuilder_GeneratedServicesFolderName;

    public static String WsdlGenBuilderTransformer_6;

    public static String WsdlGenBuilderTransformer_7;

    public static String WsdlGenBuilderTransformer_builder_monitor_shortdesc;

    public static String WsdlGenBuilderTransformer_generatingWsdl_monitor_shortdesc;

    public static String WsdlGenBuilderTransformer_InvalidWsdlErr_shortdesc;

    public static String WsdlGenBuilderTransformer_problemStartingWsdlCreateTransaction_error_message;

    public static String WsdlGenBuilderTransformer_SameNameWsdlExistsErr_shortdesc;

    public static String WsdlGenBuilderTransformer_UnableCloseStreamErr_shortdesc;

    public static String WsdlGenBuilderTransformer_UnableCreateWsdlErr_shortdesc;

    public static String WsdlGenBuilderTransformer_UnableDeleteErrMsg_shortdesc;

    public static String WsdlGenBuilderTransformer_UnableSaveWsdlErr_shortdesc;

    public static String WsdlGenBuilderTransformer_UnableSetDerivedErr_shortdesc;

    public static String WsdlGenBuilderTransformer_UnableToCreateFolderErr_shortdesc_2;

    public static String WsdlGenBuilderTransformer_unableToCompleteWsdlCreateTransaction_error_message;

    public static String WsdlGenBuilderTransformer_unableToDeleteWsdl_error_message;

    public static String WsdlGenBuilderTransformer_UnableTransformErrMsg_shortdesc;

    public static String XtendTransformerXpdl2Wsdl_ErrSave_shortdesc;

    public static String XtendTransformerXpdl2Wsdl_ErrTransform_shortdesc;

    public static String XtendTransformerXpdl2Wsdl_unableToGenerateWsdl_error_longdesc;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

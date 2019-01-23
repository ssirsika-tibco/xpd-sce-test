/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.wsdl.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author nwilson
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.wsdl.internal.messages"; //$NON-NLS-1$

    public static String WsdlCache_duplicate;

    public static String WsdlCopier_attemptToReadWSDL_progress_message;

    public static String WsdlCopier_ErrorExportingSchemaFile;

    public static String WsdlCopier_errorImportWsdl_error_message;

    public static String WsdlCopier_overwriteAll_button;

    public static String WsdlCopier_security_error;
    public static String WsdlCopier_wsdlImportError_dialog_fileExists_message;

    public static String WsdlCopier_wsdlImportError_dialog_title;

    public static String WsdlCopier_wsdlImportError_dialog_unsupportedReference_message;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

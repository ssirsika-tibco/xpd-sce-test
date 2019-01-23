/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.imports.progress;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author aallway
 * @since 4 Apr 2011
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.bom.xsdtransform.imports.progress.messages"; //$NON-NLS-1$

    public static String Xsd2BomMonitorMessage_BuildingBomsFromWsdlsXsds_message;

    public static String Xsd2BomMonitorMessage_CleaningUpAndFIxingNamespaces_message;

    public static String Xsd2BomMonitorMessage_CreatingBOMForSchema_message;

    public static String Xsd2BomMonitorMessage_NormalisingDataNames_message;

    public static String AbstractXtendImportTransformer_InstatiatingTransform_message;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.bds.designtime.validator.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author wzurek
 * 
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "com.tibco.bds.designtime.validator.internal.messages"; //$NON-NLS-1$

    public static String RefactorModelResolution_RenameElement;

    public static String RenameModelResolution_invalidModelName_shortdesc;

    public static String RenameModelResolution_modelNameAlreadyExists_shortdesc;

    public static String RenameModelResolution_setModelName_label;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}

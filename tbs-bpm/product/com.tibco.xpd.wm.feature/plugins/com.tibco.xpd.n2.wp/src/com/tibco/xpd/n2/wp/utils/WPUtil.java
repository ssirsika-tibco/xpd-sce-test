/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.wp.utils;

/**
 * @author kupadhya
 * 
 */
public class WPUtil {

    public static final String SERVICE_ARCHIVE_DESC_ROOT_FOLDER_NAME = "wp";//$NON-NLS-1$

    /*
     * Sid ACE-1028 - change of name file extension so that run-time recognises
     * the file type (don't want lots of .xml's that things c an get confused
     * with.
     */
    public static final String SERVICE_ARCHIVE_DESC_NAME = "wpModel.wp";//$NON-NLS-1$

    public static final String WP_RESOURCES_FOLDERNAME =
            SERVICE_ARCHIVE_DESC_ROOT_FOLDER_NAME;

    public static final String PRESENTATION_MODEL_PACKAGE_ID =
            "com.tibco.n2.model.wp"; //$NON-NLS-1$

    public static final String WP_OUT_FOLDERNAME = ".wpModules"; //$NON-NLS-1$

    public static final String WP_FILE_EXTENSION = "wp"; //$NON-NLS-1$

    public static final String WP_REQUIRED_CAPABILITY_ID =
            "com.tibco.amx.capability.implementation.wp"; //$NON-NLS-1$

    public static final String WEBAPP_MODEL_PACKAGE_ID =
            "com.tibco.amf.sca.model.implementationtype.webapp"; //$NON-NLS-1$

}

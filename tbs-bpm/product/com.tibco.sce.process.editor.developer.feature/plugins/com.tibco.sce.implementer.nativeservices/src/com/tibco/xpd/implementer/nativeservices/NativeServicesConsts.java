/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices;

/**
 * Native Services plugin constants
 * 
 * @author njpatel
 */
public final class NativeServicesConsts {
    /*
     * Multiple data items in an email field will be delimeted using this
     * character
     */
    public static final String EMAIL_FIELD_DELIM = ";"; //$NON-NLS-1$

    /*
     * Field delimeter used for process fields - this character will be used at
     * the beginning and end of a field, ie. %FIELD%
     */
    public static final String PROCESS_FIELD_DELIM = "%"; //$NON-NLS-1$

    /*
     * Name of the E-Mail service
     */
    public static final String EMAIL_SERVICE_ID = "E-Mail"; //$NON-NLS-1$

    // this is the name of the tab as specified in the plugin.xml file
    public static final String EMAIL_SERVICE_NAME = "%property_tab_email"; //$NON-NLS-1$

    /*
     * Name of the Database service name
     */
    public static final String DB_SERVICE_ID = "Database"; //$NON-NLS-1$

    // this is the name of the tab as specified in the plugin.xml file
    public static final String DB_SERVICE_NAME = "%property_tab_database"; //$NON-NLS-1$

    /*
     * Name of the Java POJO service name
     */
    public static final String JAVA_SERVICE_ID = "Java"; //$NON-NLS-1$

    /*
     * Width hint of all text controls
     */
    public static final int TEXT_WIDTH_HINT = 50;

    public static final String IMG_BROWSER = "/icons/browser.gif"; //$NON-NLS-1$

    public static final String IMG_SRC = "/icons/src.gif"; //$NON-NLS-1$

    public static final String IMG_FOLDER = "/icons/folder.png"; //$NON-NLS-1$

    public static final String IMG_DATAFIELD = "/icons/datafield.gif"; //$NON-NLS-1$

    public static final String IMG_FORMALPARAMETER =
            "/icons/formal_parameter.gif"; //$NON-NLS-1$

    public static final String IMG_BOM = "/icons/bom.png"; //$NON-NLS-1$

    public static final String[] IMAGES =
            new String[] { IMG_BROWSER, IMG_SRC, IMG_FOLDER, IMG_DATAFIELD,
                    IMG_FORMALPARAMETER, IMG_BOM };
}

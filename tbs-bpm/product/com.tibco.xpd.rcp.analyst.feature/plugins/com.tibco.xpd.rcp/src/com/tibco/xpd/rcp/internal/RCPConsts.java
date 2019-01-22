/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

/**
 * Constants commonly used in this plug-in.
 * 
 * @author njpatel
 * 
 */
public final class RCPConsts {

    public static final String FILE_EXT = "maa"; //$NON-NLS-1$

    public static final String BOM_SFOLDER_KIND =
            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND;

    public static final String BOM_FILEEXT =
            BOMResourcesPlugin.BOM_FILE_EXTENSION;

    public static final String OM_SFOLDER_KIND = "om"; //$NON-NLS-1$

    public static final String OM_FILEEXT = "om"; //$NON-NLS-1$

    public static final String PROCESSES_SFOLDER_KIND =
            Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND;

    public static final String PROCESSES_FILEEXT =
            Xpdl2ResourcesConsts.XPDL_EXTENSION;

    public static String PACKAGE_EDITOR_ID =
            "com.tibco.xpd.packageeditor.editor"; //$NON-NLS-1$

    public static String PROCESS_INTERFACE_EDITOR_ID =
            "com.tibco.xpd.analyst.editor.ProcessInterfaceEditor"; //$NON-NLS-1$

}

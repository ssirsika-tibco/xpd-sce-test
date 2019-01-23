/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.daa.internal.util;

import com.tibco.xpd.deploy.ui.DeployUIActivator;

/**
 * Constants used in DAA plug-in.
 * 
 * @author mtorres
 * 
 */
public class DAAConstants {

    public static final String ADMIN_SERVER_TYPE_ID =
            "com.tibco.amf.tools.admincligen.adminserver";//$NON-NLS-1$

    public static final String AMX_BPM_APPLICATION = "amx.bpm.app"; //$NON-NLS-1$

    /**
     * The constructor.
     */
    private DAAConstants() {
    }

    public static final String IMG_DAA_EXPORT_WIZARD =
            "icons/wizban/daa_wiz.png"; //$NON-NLS-1$

    /**
     * Images loaded by plug-in and served by registry.
     * 
     * @see DeployUIActivator#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     */
    public static final String[] IMAGES =
            new String[] { IMG_DAA_EXPORT_WIZARD };
}

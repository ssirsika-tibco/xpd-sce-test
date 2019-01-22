/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.n2.daa;

import com.tibco.xpd.daa.DaaActivator;
import com.tibco.xpd.deploy.ui.DeployUIActivator;

/**
 * Constants used in DAA plug-in.
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DAAConstants {

    /** The server type id of AMX Administrator server. */
    public static final String ADMIN_SERVER_TYPE_ID =
            DaaActivator.ADMIN_SERVER_TYPE_ID;

    public static final String AMX_BPM_APPLICATION =
            DaaActivator.AMX_BPM_APPLICATION;

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

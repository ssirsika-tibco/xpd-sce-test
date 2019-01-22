/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.wp.utils;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;

/**
 * @author mtorres
 * 
 */
public class PFEUtil {

    /** PFE file extension */
    public static final String PFE_EXTENSION = "pfe"; //$NON-NLS-1$

    public static final String PFE_FILE_NAME = "pfe.pfe"; ////$NON-NLS-1$

    public static final String PFE_REQUIRED_CAPABILITY_ID =
            "com.tibco.amx.capability.implementation.pfe"; //$NON-NLS-1$

    public static final String PFE_MODEL_PACKAGE_ID = "com.tibco.n2.model.pfe"; //$NON-NLS-1$

    public static final String PAGE_FLOW_APPEND = "#PageFlow";

    public static IResource getPfeFile(IProject project) {
        IFolder wpModulesFolder = WPUtil.getWpOutDestFolder(project);
        if (wpModulesFolder != null && wpModulesFolder.exists()) {
            return wpModulesFolder.getFile(PFEUtil.PFE_FILE_NAME);
        }
        return null;
    }

    public static RequiredCapability getPFERequiredCapability() {
        RequiredCapability rc =
                ComponentTypeFactory.eINSTANCE.createRequiredCapability();
        rc.setId(PFE_REQUIRED_CAPABILITY_ID);
        rc.setType(CapabilityType.FACTORY);
        rc.setVersion("1.0.0"); //$NON-NLS-1$
        return rc;
    }

    public static ImportPackage getPfeImportPackage() {
        ImportPackage ip = OsgiFactory.eINSTANCE.createImportPackage();
        ip.setName(PFEUtil.PFE_MODEL_PACKAGE_ID);
        VersionRange vr = OsgiFactory.eINSTANCE.createVersionRange();
        vr.setLower("1.0.0"); //$NON-NLS-1$
        vr.setLowerIncluded(true);
        vr.setUpper("2.0.0"); //$NON-NLS-1$
        vr.setUpperIncluded(false);
        ip.setRange(vr);
        return ip;
    }

}

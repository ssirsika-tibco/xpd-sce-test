/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.transform.de.component;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Version;

import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.ProvidedCapability;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.tools.packager.util.PackagerUtils;
import com.tibco.n2.model.common.DEImplementation;
import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.om.transform.de.TransformDEActivator;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @author kupadhya
 * 
 */
public class DECompositeUtil {

    public static final String DE_REQUIRED_CAPABILITY_ID =
            "com.tibco.amx.capability.implementation.de"; //$NON-NLS-1$

    public static RequiredCapability getDERequiredCapability() {
        RequiredCapability rc =
                ComponentTypeFactory.eINSTANCE.createRequiredCapability();
        rc.setId(DE_REQUIRED_CAPABILITY_ID);
        rc.setType(CapabilityType.FACTORY);
        rc.setVersion("1.0.0"); //$NON-NLS-1$
        return rc;
    }

    /**
     * Get the version of the component from the file. The file's persisted
     * properties will be checked for the details and if not found the project's
     * lifecycle version will be returned.
     * 
     * @param file
     * @param project
     * @return
     */
    public static String getOMModelVersion(IFile file, IProject project) {
        String version = null;

        if (file != null) {
            try {
                version =
                        file
                                .getPersistentProperty(TransformDEActivator.MODEL_VERSION);
            } catch (CoreException e) {
                // Do nothing
            }
        }

        return version != null ? version : project != null ? CompositeUtil
                .getVersionNumber(project) : null;
    }

    /**
     * Returns Provided capability for OM file
     * 
     * @param deImpl
     * @return
     */
    public static ProvidedCapability createProvidedCapability(
            DEImplementation deImpl) {
        Component component = (Component) deImpl.eContainer();
        String compVersion = component.getVersion();
        int lastIndexOf = compVersion.lastIndexOf(".");
        if (lastIndexOf != -1) {
            compVersion = compVersion.substring(0, lastIndexOf);
        }
        String organization = deImpl.getOrganization();
        IPath path = new Path(organization);
        path = path.removeFileExtension().addFileExtension("om");
        String deFileName = path.lastSegment();
        IProject project = WorkingCopyUtil.getProjectFor(deImpl);
        String qualifier = PackagerUtils.getQualifier(deImpl);
        ProvidedCapability providedCapability =
                createProvidedCapability(project, deFileName, compVersion);
        return providedCapability;
    }

    public static ProvidedCapability createProvidedCapability(IProject project,
            String omFileName, String omFileVersion) {
        String projectId = ProjectUtil.getProjectId(project);
        ProvidedCapability providedCapability =
                ComponentTypeFactory.eINSTANCE.createProvidedCapability();
        providedCapability.setId(projectId + "." + omFileName + ".capability");
        providedCapability.setType(CapabilityType.CUSTOM);
        providedCapability.setVersion(omFileVersion);
        return providedCapability;
    }    
    
    public static RequiredCapability createRequiredCapability(IProject project,
            String omFileName, String omFileVersion) {
        String projectId = ProjectUtil.getProjectId(project);
        RequiredCapability requiredCapability =
                ComponentTypeFactory.eINSTANCE.createRequiredCapability();
        requiredCapability.setId(projectId + "." + omFileName + ".capability");
        requiredCapability.setType(CapabilityType.CUSTOM);        
        //requiredCapability.setVersion(omFileVersion);
        Version minVersion = new Version(omFileVersion);
        Version maxVersion = new Version(minVersion.getMajor()+1, 0, 0);
        VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
        versionRange.setLower(minVersion.toString());
        versionRange.setLowerIncluded(Boolean.TRUE);
        versionRange.setUpper(maxVersion.toString());
        versionRange.setUpperIncluded(Boolean.FALSE);
        requiredCapability.setVersion(versionRange.toString());
        //requiredCapability.setVersionRange(versionRange);
        return requiredCapability;
    }
}

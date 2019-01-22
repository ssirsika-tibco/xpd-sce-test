/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.component;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.ProvidedCapability;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * @author mtorres
 * 
 */
public class PFEUtil {

    public static final String PFE_REQUIRED_CAPABILITY_ID =
            "com.tibco.amx.capability.implementation.pfe"; //$NON-NLS-1$

    public static final String PFE_MODEL_PACKAGE_ID = "com.tibco.n2.pfe"; //$NON-NLS-1$

    public static final String PAGE_FLOW_APPEND = "#PageFlow"; //$NON-NLS-1$

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

    public static ProvidedCapability getPFCProvidedCapability(IProject project,
            String qualifierReplacer, String componentName) {
        ProvidedCapability providedCapability =
                ComponentTypeFactory.eINSTANCE.createProvidedCapability();
        CapabilityIdVersion providedCapabilityId =
                getProvidedCapabilityId(project,
                        qualifierReplacer,
                        componentName);
        providedCapability.setId(providedCapabilityId.getId());
        providedCapability.setType(CapabilityType.CUSTOM);
        providedCapability.setVersion(providedCapabilityId.getVersion());
        return providedCapability;
    }

    public static CapabilityIdVersion getProvidedCapabilityId(IProject project,
            String qualifierReplacer, String componentName) {
        String projectId = ProjectUtil.getProjectId(project);
        String projectVersion = ProjectUtil.getProjectVersion(project);
        String updatedBundleVersion =
                PluginManifestHelper.getUpdatedBundleVersion(projectVersion,
                        qualifierReplacer);
        String id = projectId + "." + componentName + ".pfc" //$NON-NLS-1$//$NON-NLS-2$
                + ".capability"; //$NON-NLS-1$
        return new CapabilityIdVersion(id, updatedBundleVersion);
    }

    public static RequiredCapability createPFCRequiredCapability() {
        RequiredCapability requiredCapability =
                ComponentTypeFactory.eINSTANCE.createRequiredCapability();
        requiredCapability.setId(PFEUtil.PFE_REQUIRED_CAPABILITY_ID);
        requiredCapability.setType(CapabilityType.FACTORY);
        requiredCapability.setVersion("1.0.0"); //$NON-NLS-1$
        return requiredCapability;
    }

    /**
     * 
     * @param requiredCapabilities
     * @param capabilityId
     * @return
     */
    public static RequiredCapability findCapabilityWithId(
            List<RequiredCapability> requiredCapabilities, String capabilityId) {
        RequiredCapability requiredCapability = null;
        if (requiredCapabilities == null || requiredCapabilities.isEmpty()
                || capabilityId == null || capabilityId.trim().length() < 1) {
            return requiredCapability;
        }
        for (RequiredCapability rc : requiredCapabilities) {
            if (capabilityId.equals(rc.getId())) {
                requiredCapability = rc;
                break;
            }
        }
        return requiredCapability;
    }

    public static class CapabilityIdVersion {
        private String id;

        private String version;

        public String getId() {
            return id;
        }

        public String getVersion() {
            return version;
        }

        public CapabilityIdVersion(String id, String version) {
            this.id = id;
            this.version = version;
        }
    }

}

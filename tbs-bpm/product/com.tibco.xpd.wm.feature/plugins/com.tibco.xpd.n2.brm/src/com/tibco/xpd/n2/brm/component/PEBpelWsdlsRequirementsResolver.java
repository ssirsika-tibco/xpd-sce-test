/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.brm.component;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.extensions.IRequirementsResolver;
import com.tibco.bx.composite.core.util.BxCompositeCoreConstants;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * @author kupadhya
 * 
 */
public class PEBpelWsdlsRequirementsResolver implements IRequirementsResolver {

    public void addImplementationRequirements(Requirements requirements,
            Implementation implementation) {
        if (!(implementation instanceof BxServiceImplementation)) {
            return;
        }
        BxServiceImplementation bxImplementation =
                (BxServiceImplementation) implementation;
        RequiredCapability brmRC = getBRMRequiredCapability(bxImplementation);
        if (brmRC != null) {
            EList<RequiredCapability> requiredCapabilities =
                    requirements.getRequiredCapabilities();
            requiredCapabilities.add(brmRC);
            RequiredCapability bxFactoryRC =
                    getBXFactoryRequiredCapability(requirements);
            bxFactoryRC.getWiths().add(brmRC);

        }

    }

    private RequiredCapability getBXFactoryRequiredCapability(
            Requirements requirements) {
        EList<RequiredCapability> requiredCapabilities =
                requirements.getRequiredCapabilities();
        RequiredCapability bxRequiredCapability = null;
        for (RequiredCapability requiredCapability : requiredCapabilities) {
            if (BxCompositeCoreConstants.CAPABILITY_ID
                    .equals(requiredCapability.getId())) {
                bxRequiredCapability = requiredCapability;
                break;
            }
        }
        if (bxRequiredCapability == null) {
            bxRequiredCapability =
                    ComponentTypeFactory.eINSTANCE.createRequiredCapability();
            bxRequiredCapability.setId(BxCompositeCoreConstants.CAPABILITY_ID);
            bxRequiredCapability.setType(CapabilityType.FACTORY);
            bxRequiredCapability.setVersion("1.0.0"); //$NON-NLS-1$
            requiredCapabilities.add(bxRequiredCapability);
        }
        return bxRequiredCapability;

    }

    private RequiredCapability getBRMRequiredCapability(
            BxServiceImplementation bxImplementation) {
        RequiredCapability requiredCapability = null;
        IProject project = WorkingCopyUtil.getProjectFor(bxImplementation);
        ServiceImplementation serviceModel = bxImplementation.getServiceModel();
        String xpdlFileName = serviceModel.getModuleName();
        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(
                        xpdlFileName));
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        if (workingCopy instanceof Xpdl2WorkingCopyImpl) {
            Xpdl2WorkingCopyImpl xpdlWC = (Xpdl2WorkingCopyImpl) workingCopy;
            Package xpdlPackage = (Package) xpdlWC.getRootElement();
            ArrayList<Package> xpdlPackageList = new ArrayList<Package>();
            xpdlPackageList.add(xpdlPackage);
            Collection<Activity> manualActivities =
                    BRMUtils.getN2ManualActivities(xpdlPackageList);
            if (manualActivities != null && !manualActivities.isEmpty()) {
                String qualifierReplacer =
                        PluginManifestHelper
                                .getQualifierReplacer(bxImplementation);
                requiredCapability =
                        BRMUtils.getBRMRequiredCapability(project,
                                qualifierReplacer);

            }
        }
        return requiredCapability;
    }

}

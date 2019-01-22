/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.brm.component;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.sca.componenttype.implementation.ComponentTypeResolver;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.ProvidedCapability;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.common.ECImplementation;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.n2.daa.utils.N2PECompositeUtil;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Class for the resolution of Event Collector {@link ComponentType} from a
 * given {@link Implementation}
 * 
 * @author jarciuch
 * @since 10 Jan 2014
 */
public class EcComponentTypeResolver extends ComponentTypeResolver {

    @Override
    public Requirements getComponentRequirements(Implementation implementation) {
        if (implementation instanceof ECImplementation) {
            ECImplementation eci = (ECImplementation) implementation;
            Requirements requirements =
                    ComponentTypeFactory.eINSTANCE.createRequirements();
            addProvidedCapability(requirements, eci);
            addIncludedResources(requirements, eci);
            addPackageImports(requirements, eci);
            addRequiredCapabilities(requirements, eci);
            addFeatureDependency(requirements, eci);
            requirements
                    .getDeploymentConstraints()
                    .add(ComponentTypeFactory.eINSTANCE.createProductApplicationDeployment());
            return requirements;
        }
        return super.getComponentRequirements(implementation);
    }

    private void addProvidedCapability(Requirements requirements,
            ECImplementation eci) {
        EList<ProvidedCapability> providedCapabilities =
                requirements.getProvidedCapabilities();
        IProject project = WorkingCopyUtil.getProjectFor(eci);
        String qualifierReplacer =
                PluginManifestHelper.getQualifierReplacer(eci);
        ProvidedCapability providedCapability =
                BRMUtils.getEcProvidedCapability(project, qualifierReplacer);
        providedCapabilities.add(providedCapability);
    }

    private void addIncludedResources(Requirements requirements,
            ECImplementation eci) {
        EList<String> includedResources = requirements.getIncludedResources();
        // Add itself with the full path from project name
        String ecSpecification = eci.getEcSpecification();
        Path filePath = new Path(ecSpecification);
        String relativePath = filePath.lastSegment();
        includedResources.add(relativePath);
        // Names could also be read from the implementation file.
        IProject project = WorkingCopyUtil.getProjectFor(eci);
        if (N2PENamingUtils.getFileFromStagingArea(project,
                new Path(BRMUtils.WLF_FILE_NAME)).isAccessible()) {
            includedResources.add(BRMUtils.WLF_FILE_NAME);
        }
    }

    private void addPackageImports(Requirements requirements,
            ECImplementation eci) {
        EList<ImportPackage> packageImports = requirements.getPackageImports();
        ImportPackage commonImportPackage =
                N2PECompositeUtil.getCommonImportPackage();
        packageImports.add(commonImportPackage);
    }

    private void addRequiredCapabilities(Requirements requirements,
            ECImplementation ecImpl) {
        RequiredCapability ecRequiredCapability =
                ComponentTypeFactory.eINSTANCE.createRequiredCapability();
        ecRequiredCapability.setId(BRMUtils.EC_REQUIRED_CAPABILITY_ID);
        ecRequiredCapability.setType(CapabilityType.FACTORY);
        ecRequiredCapability.setVersion("1.0.0"); //$NON-NLS-1$
        requirements.getRequiredCapabilities().add(ecRequiredCapability);
    }

    @Override
    public ComponentType getComponentType(Implementation implementation) {
        if (implementation instanceof ECImplementation) {
            ComponentType componentType =
                    ComponentTypeFactory.eINSTANCE.createComponentType();
            return componentType;
        }
        return null;
    }

    private void addFeatureDependency(Requirements requirements,
            ECImplementation ecImpl) {
        if (null != requirements) {
            RequiredFeature modelPF = N2PENamingUtils.getBPMITModelPF();
            requirements.getFeatureDependencies().add(modelPF);
        }

    }

    @Override
    public IResource[] getImplementationResources(Implementation implementation) {
        // NOTE: This is only for UI
        // TODO Add here references
        return super.getImplementationResources(implementation);
    }

}

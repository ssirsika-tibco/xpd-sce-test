/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.transform.de.component;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.sca.componenttype.implementation.ComponentTypeResolver;
import com.tibco.amf.sca.model.componenttype.ComponentType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.ProvidedCapability;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.common.DEImplementation;
import com.tibco.xpd.n2.daa.utils.N2PECompositeUtil;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;

/**
 * @author mtorres
 * 
 */
public class DEComponentTypeResolver extends ComponentTypeResolver {

    @Override
    public Requirements getComponentRequirements(Implementation implementation) {
        if (implementation instanceof DEImplementation) {
            DEImplementation dei = (DEImplementation) implementation;
            Requirements requirements =
                    ComponentTypeFactory.eINSTANCE.createRequirements();
            // Add resources
            addIncludedResources(requirements, dei);
            // Add package imports
            addPackageImports(requirements);
            // Add required capabilities
            addRequiredCapabilities(requirements);
            // Add required bundles
            addRequiredBundles(requirements);
            // Add required feature dependency
            addFeatureDependency(requirements, dei);
            // add provided capability
            addProvidedCapability(requirements, dei);

            // add deployment constraint
            requirements.getDeploymentConstraints()
                    .add(ComponentTypeFactory.eINSTANCE
                            .createProductApplicationDeployment());
            return requirements;
        }
        return super.getComponentRequirements(implementation);
    }

    /**
     * @param requirements
     * @param dei
     */
    private void addFeatureDependency(Requirements requirements,
            DEImplementation dei) {
        if (null != requirements) {
            RequiredFeature modelPF = N2PENamingUtils.getBPMITModelPF();
            requirements.getFeatureDependencies().add(modelPF);
        }
    }

    private void addProvidedCapability(Requirements requirements,
            DEImplementation deImpl) {
        EList<ProvidedCapability> providedCapabilities =
                requirements.getProvidedCapabilities();
        ProvidedCapability providedCapability =
                DECompositeUtil.createProvidedCapability(deImpl);
        providedCapabilities.add(providedCapability);
    }

    private void addPackageImports(Requirements requirements) {
        EList<ImportPackage> packageImports = requirements.getPackageImports();
        ImportPackage commonImportPackage =
                N2PECompositeUtil.getCommonImportPackage();
        packageImports.add(commonImportPackage);
    }

    private void addIncludedResources(Requirements requirements,
            DEImplementation dei) {
        EList<String> includedResources = requirements.getIncludedResources();
        Path filePath = new Path(dei.getOrganization());
        String relativePath = filePath.lastSegment();
        includedResources.add(relativePath);
    }

    private void addRequiredCapabilities(Requirements requirements) {
        EList<RequiredCapability> includedCapabilities =
                requirements.getRequiredCapabilities();
        RequiredCapability deRequiredCapability =
                DECompositeUtil.getDERequiredCapability();
        includedCapabilities.add(deRequiredCapability);
    }

    private void addRequiredBundles(Requirements requirements) {

    }

    @Override
    public ComponentType getComponentType(Implementation implementation) {
        if (implementation instanceof DEImplementation) {
            ComponentType componentType =
                    ComponentTypeFactory.eINSTANCE.createComponentType();
            return componentType;
        }
        return null;
    }

    @Override
    public IResource[] getImplementationResources(Implementation implementation) {
        // No resources needed
        return super.getImplementationResources(implementation);
    }
}

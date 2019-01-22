/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.brm.component;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.RequiredBundle;
import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.sca.componenttype.implementation.ComponentTypeResolver;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.ProvidedCapability;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.common.BRMImplementation;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.bds.gd.internal.component.BDSCompositeUtil;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.n2.cds.utils.CDSCustomFeatureUtils;
import com.tibco.xpd.n2.cds.utils.CDSDependencyUtils;
import com.tibco.xpd.n2.daa.utils.N2PECompositeUtil;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.scriptdescriptor.utils.ScriptDescriptorUtils;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;

/**
 * Class for the resolution of a {@link ComponentType} from a given
 * {@link Implementation}
 * 
 * @author mtorres
 * 
 */
public class BRMComponentTypeResolver extends ComponentTypeResolver {

    @Override
    public Requirements getComponentRequirements(Implementation implementation) {

        if (implementation instanceof BRMImplementation) {

            BRMImplementation brmi = (BRMImplementation) implementation;
            Requirements requirements =
                    ComponentTypeFactory.eINSTANCE.createRequirements();
            // Add resources
            addIncludedResources(requirements, brmi);
            // Add package imports
            addPackageImports(requirements, brmi);
            // Add required capabilities
            addRequiredCapabilities(requirements, brmi);
            // Add required bundles
            addRequiredBundles(requirements, brmi);
            // Add required feature dependency
            addFeatureDependency(requirements, brmi);
            // add provided capability
            addProvidedCapability(requirements, brmi);
            // add deployment constraint
            requirements
                    .getDeploymentConstraints()
                    .add(ComponentTypeFactory.eINSTANCE.createProductApplicationDeployment());
            return requirements;
        }
        return super.getComponentRequirements(implementation);
    }

    private void addProvidedCapability(Requirements requirements,
            BRMImplementation brmi) {

        EList<ProvidedCapability> providedCapabilities =
                requirements.getProvidedCapabilities();
        IProject project = WorkingCopyUtil.getProjectFor(brmi);
        String qualifierReplacer =
                PluginManifestHelper.getQualifierReplacer(brmi);
        ProvidedCapability providedCapability =
                BRMUtils.getBRMProvidedCapability(project, qualifierReplacer);
        providedCapabilities.add(providedCapability);
    }

    private void addIncludedResources(Requirements requirements,
            BRMImplementation brmi) {

        EList<String> includedResources = requirements.getIncludedResources();
        // Add itself with the full path from project name
        String workSpecification = brmi.getWorkSpecification();
        Path filePath = new Path(workSpecification);
        String relativePath = filePath.lastSegment();
        includedResources.add(relativePath);
        // TODO Names could be read from the implementation file.
        IProject project = WorkingCopyUtil.getProjectFor(brmi);
        if (N2PENamingUtils.getFileFromStagingArea(project,
                new Path(BRMUtils.WM_FILE_NAME)).isAccessible()) {
            includedResources.add(BRMUtils.WM_FILE_NAME);
        }
        if (N2PENamingUtils.getFileFromStagingArea(project,
                new Path(BRMUtils.WT_FILE_NAME)).isAccessible()) {
            includedResources.add(BRMUtils.WT_FILE_NAME);
        }
        if (N2PENamingUtils.getFileFromStagingArea(project,
                new Path(BRMUtils.WLF_FILE_NAME)).isAccessible()) {
            includedResources.add(BRMUtils.WLF_FILE_NAME);
        }
        IFile scriptDescriptorFile =
                ScriptDescriptorUtils
                        .getScriptDescriptorFromStagingArea(project);

        if (scriptDescriptorFile != null && scriptDescriptorFile.isAccessible()) {
            includedResources.add(scriptDescriptorFile.getName());
        }
    }

    private void addPackageImports(Requirements requirements,
            BRMImplementation brmi) {

        EList<ImportPackage> packageImports = requirements.getPackageImports();
        ImportPackage commonImportPackage =
                N2PECompositeUtil.getCommonImportPackage();
        packageImports.add(commonImportPackage);
    }

    private void addRequiredCapabilities(Requirements requirements,
            BRMImplementation brmImpl) {

        IProject project = WorkingCopyUtil.getProjectFor(brmImpl);
        if (!hasWorkListFacade(project)) {
            String qualifierReplacer =
                    PluginManifestHelper.getQualifierReplacer(brmImpl);
            BRMUtils.addBRMFactoryRequiredCapability(requirements,
                    project,
                    qualifierReplacer);

            /*
             * XPD-5106: add bds capability as required capability on BRM
             * component
             */
            EList<RequiredCapability> includedCapabilities =
                    requirements.getRequiredCapabilities();

            List<RequiredCapability> bomReqCapList =
                    BDSCompositeUtil.getBDSRequiredCapabilityList(project,
                            includedCapabilities);

            includedCapabilities.addAll(bomReqCapList);

            /* add bds capability as withs capability on brm capability */
            for (RequiredCapability rc : requirements.getRequiredCapabilities()) {

                if (BRMUtils.BRM_REQUIRED_CAPABILITY_ID.equals(rc.getId())
                        && CapabilityType.FACTORY.equals(rc.getType())) {

                    rc.getWiths().addAll(bomReqCapList);
                    break;
                }
            }
        } else {
            RequiredCapability brmRequiredCapability =
                    ComponentTypeFactory.eINSTANCE.createRequiredCapability();
            brmRequiredCapability.setId(BRMUtils.BRM_REQUIRED_CAPABILITY_ID);
            brmRequiredCapability.setType(CapabilityType.FACTORY);
            brmRequiredCapability.setVersion("1.0.0"); //$NON-NLS-1$
            requirements.getRequiredCapabilities().add(brmRequiredCapability);
        }
    }

    private void addRequiredBundles(Requirements requirements,
            BRMImplementation brmi) {

        if (brmi != null) {
            String brmModelPath = brmi.getWorkSpecification();

            if (brmModelPath != null) {

                Path filePath = new Path(brmModelPath);
                String projectName = filePath.segment(0);
                String qualifierReplacer =
                        PluginManifestHelper.getQualifierReplacer(brmi);
                EList<RequiredBundle> requiredBundles =
                        requirements.getRequiredBundles();

                /* XPD-1345: add required bundles for data */
                IProject project = WorkingCopyUtil.getProjectFor(brmi);
                BDSCompositeUtil.addRequiredBundles(project,
                        requiredBundles,
                        qualifierReplacer);
            }
        }
    }

    private void addExternalBomRequiredBundles(String projectName,
            List<RequiredBundle> requiredBundles, String qualifierReplacer) {

        if (projectName != null) {
            CDSDependencyUtils
                    .updateRBListWithExternalprojectReferences(projectName,
                            requiredBundles,
                            qualifierReplacer,
                            CDSCustomFeatureUtils.BOM_CDS_GENERATOR_ID);
        }
    }

    @Override
    public ComponentType getComponentType(Implementation implementation) {

        if (implementation instanceof BRMImplementation) {
            ComponentType componentType =
                    ComponentTypeFactory.eINSTANCE.createComponentType();
            return componentType;
        }
        return null;
    }

    @Override
    public IResource[] getImplementationResources(Implementation implementation) {
        // NOTE: This is only for UI
        // TODO Add here references
        return super.getImplementationResources(implementation);
    }

    private void addFeatureDependency(Requirements requirements,
            BRMImplementation brmi) {

        if (brmi != null) {
            String brmModelPath = brmi.getWorkSpecification();

            if (brmModelPath != null) {
                Path filePath = new Path(brmModelPath);
                String projectName = filePath.segment(0);
                IProject project =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getProject(projectName);

                if (project != null) {
                    String qualifierReplacer =
                            PluginManifestHelper.getQualifierReplacer(brmi);

                    /*
                     * XPD-5472: admin throws an error message during
                     * process-app upgrade wherein Admin tries to remove
                     * com.example.glob1.model.bds feature when a new version of
                     * process-user-app is deployed.
                     * 
                     * The workaround(till Platform finds an appropriate
                     * solution) is not to put 'feature-dependency', but just
                     * have a 'require-bundle' dependency when a component
                     * references a feature that is not contained.
                     * 
                     * This fix might be required in BOMRequirmentsResolver and
                     * WPComponentTypeResolver
                     */

                    /*
                     * add feature dependency on its own project
                     */
                    CDSDependencyUtils.addRequiredFeatures(requirements,
                            project,
                            qualifierReplacer);
                }
            }
        }

        if (null != requirements) {
            RequiredFeature modelPF = N2PENamingUtils.getBPMITModelPF();
            requirements.getFeatureDependencies().add(modelPF);
        }

    }

    /**
     * Returns 'true' if project contains Work List Facade file in appropriate
     * special folder.
     */
    private static boolean hasWorkListFacade(IProject project) {
        final List<IResource> wlfFiles =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                WorkListFacadeResourcePlugin.WLF_SPECIAL_FOLDER_KIND,
                                WorkListFacadeResourcePlugin.WLF_FILE_EXTENSION,
                                false);
        return !wlfFiles.isEmpty();
    }
}

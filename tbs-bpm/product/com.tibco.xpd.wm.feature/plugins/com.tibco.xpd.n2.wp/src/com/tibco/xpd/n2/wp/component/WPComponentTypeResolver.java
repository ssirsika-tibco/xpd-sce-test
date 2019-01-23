/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.wp.component;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
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
import com.tibco.n2.model.common.WPImplementation;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorsContributor;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.n2.bds.gd.internal.component.BDSCompositeUtil;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.n2.cds.utils.CDSDependencyUtils;
import com.tibco.xpd.n2.daa.utils.N2PECompositeUtil;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.wp.WPActivator;
import com.tibco.xpd.n2.wp.utils.WPUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @author mtorres
 * 
 */
public class WPComponentTypeResolver extends ComponentTypeResolver {

    /**
     * Extension point id for contribution of 3rd part requirements reaolvers.
     */
    private static final String WP_REQ_RESOLVER_EXTPOINT_ID =
            "WPComponentRequirementsResolver"; //$NON-NLS-1$

    @Override
    public Requirements getComponentRequirements(Implementation implementation) {
        if (implementation instanceof WPImplementation) {
            WPImplementation wpi = (WPImplementation) implementation;
            Requirements requirements =
                    ComponentTypeFactory.eINSTANCE.createRequirements();
            // Add resources
            addIncludedResources(requirements, wpi);
            // Add package imports
            addPackageImports(requirements, wpi);
            // Add required bundles
            addRequiredBundles(requirements, wpi);
            // Add feature dependency
            addFeatureDependency(requirements, wpi);
            // add provided capability
            addProvidedCapability(requirements, wpi);

            // Add required capabilities
            addRequiredCapabilities(requirements, wpi);

            // add deployment constraint
            requirements
                    .getDeploymentConstraints()
                    .add(ComponentTypeFactory.eINSTANCE.createProductApplicationDeployment());

            /*
             * XPD-7266: Allow extension contribution to WP Component
             * requirements.
             */
            invokeRequirementResolverContributions(requirements, wpi);

            return requirements;
        }
        return super.getComponentRequirements(implementation);
    }

    private void addProvidedCapability(Requirements requirements,
            WPImplementation wpi) {
        EList<ProvidedCapability> providedCapabilities =
                requirements.getProvidedCapabilities();
        IProject project = WorkingCopyUtil.getProjectFor(wpi);
        String qualifierReplacer =
                PluginManifestHelper.getQualifierReplacer(wpi);
        ProvidedCapability providedCapability =
                BRMUtils.getWPProvidedCapability(project, qualifierReplacer);
        providedCapabilities.add(providedCapability);
    }

    private void addPackageImports(Requirements requirements,
            WPImplementation wpi) {
        EList<ImportPackage> packageImports = requirements.getPackageImports();
        ImportPackage commonImportPackage =
                N2PECompositeUtil.getCommonImportPackage();
        packageImports.add(commonImportPackage);
        ImportPackage presentationImportPackage =
                WPUtil.getPresentationImportPackage();
        packageImports.add(presentationImportPackage);
        ImportPackage webAPpImportPackage = WPUtil.getWebAppImportPackage();
        packageImports.add(webAPpImportPackage);
    }

    private void addIncludedResources(Requirements requirements,
            WPImplementation wpi) {
        EList<String> includedResources = requirements.getIncludedResources();
        Path filePath = new Path(wpi.getPresentation());
        String relativePath = filePath.lastSegment();
        includedResources.add(relativePath);
        IProject project = WorkingCopyUtil.getProjectFor(wpi);
        if (WPUtil.doesWpResourcesFolderExist(project)) {
            // TODO: this was working in M2 BUT not in M4
            // includedResources.add(WPUtil.WP_RESOURCES_FOLDERNAME);
            IFolder wpResourcesFolder = WPUtil.getWpResourcesFolder(project);
            addIndividualStaticResources(wpResourcesFolder, includedResources);
        }

        // From M6 WT file is optional, as there can only be business services.
        IFolder moduleOutputFolder =
                N2PENamingUtils.getModuleOutputFolder(project, false);
        if (moduleOutputFolder != null && moduleOutputFolder.isAccessible()) {
            if (moduleOutputFolder.getFile(BRMUtils.WT_FILE_NAME).exists()) {
                includedResources.add(BRMUtils.WT_FILE_NAME);
            }
        }

    }

    private void addRequiredCapabilities(Requirements requirements,
            WPImplementation wpi) {

        IProject project = WorkingCopyUtil.getProjectFor(wpi);
        String qualifierReplacer =
                PluginManifestHelper.getQualifierReplacer(wpi);

        WPUtil.addWPRequiredCapability(project, requirements, qualifierReplacer);

        /* XPD-5107: add bds capability as required capability on WP component */
        EList<RequiredCapability> includedCapabilities =
                requirements.getRequiredCapabilities();

        List<RequiredCapability> bomReqCapList =
                BDSCompositeUtil.getBDSRequiredCapabilityList(project,
                        includedCapabilities);

        includedCapabilities.addAll(bomReqCapList);

        /* add bds capability as withs capability on wp capability */
        for (RequiredCapability rc : requirements.getRequiredCapabilities()) {

            if (WPUtil.WP_REQUIRED_CAPABILITY_ID.equals(rc.getId())
                    && CapabilityType.FACTORY.equals(rc.getType())) {

                rc.getWiths().addAll(bomReqCapList);
                break;
            }
        }
    }

    private void addRequiredBundles(Requirements requirements,
            WPImplementation wpi) {

        if (wpi != null) {

            String presentationPath = wpi.getPresentation();

            if (presentationPath != null) {

                Path filePath = new Path(presentationPath);
                String projectName = filePath.segment(0);
                String qualifierReplacer =
                        PluginManifestHelper.getQualifierReplacer(wpi);
                /* adding BDS plugins */
                EList<RequiredBundle> requiredBundles =
                        requirements.getRequiredBundles();

                /* XPD-1345: add required bundles for data */
                IProject project = WorkingCopyUtil.getProjectFor(wpi);
                /**
                 * XPD-7452: bharti: addRequiredBundles has changed to declare
                 * bundles even if there are no type references (direct /
                 * indirect) from a process project. This meant that this change
                 * will affect the WP component (that gets generated in a
                 * Business Data Project) to have the required bundles on bds
                 * (which indeed may not be required at this stage because we do
                 * not add requires capability on BDS for WP component).
                 * 
                 * After discussing this with Joshy it came out that we don't
                 * want to do this for WP component in business data project for
                 * two reasons. One because there is no requires capability on
                 * WP component for bds. Second there were some runtime issues
                 * going on with GVS and platform, and the option of whether to
                 * have WP component in Business Data Project is being
                 * re-considered for 4.0. When we come to resolve the runtime
                 * issues properly then we can decide what meta data information
                 * is required on WP component (in terms of required capability
                 * and required bundles).
                 * 
                 * Hence add required bundles on BDS for WP component only if
                 * the project is not a business data project
                 */

                boolean isBusinessDataProject =
                        BOMUtils.isBusinessDataProject(project);
                if (!isBusinessDataProject) {

                    BDSCompositeUtil.addRequiredBundles(project,
                            requiredBundles,
                            qualifierReplacer);
                }

                /*
                 * XPD-7262 - Forms feature is returning to use of BOM JS custom
                 * feature plugin strategy (business data project handling).
                 * 
                 * Removing our (defunct) call to
                 * CDSDependencyUtils.addRequiredBundlesForGeneratedFormsJsPlugIn
                 * () as the equivalent will be handled in teh forms features
                 * themselves.
                 */
            }
        }
    }

    @Override
    public ComponentType getComponentType(Implementation implementation) {
        if (implementation instanceof WPImplementation) {
            ComponentType componentType =
                    ComponentTypeFactory.eINSTANCE.createComponentType();
            return componentType;
        }
        return null;
    }

    @Override
    public IResource[] getImplementationResources(Implementation implementation) {
        // NOTE: This is only for UI
        return super.getImplementationResources(implementation);
    }

    private void addFeatureDependency(Requirements requirements,
            WPImplementation wpi) {

        if (wpi != null) {
            String presentationPath = wpi.getPresentation();

            if (presentationPath != null) {

                Path filePath = new Path(presentationPath);
                String projectName = filePath.segment(0);
                IProject project =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getProject(projectName);

                if (project != null) {

                    String qualifierReplacer =
                            PluginManifestHelper.getQualifierReplacer(wpi);

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
                     * This fix might be required in BRMComponentTypeResolver
                     * and BOMRequirementsResolver
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

    private void addIndividualStaticResources(
            final IFolder wpServiceArchiveFolder,
            final EList<String> includedResources) {
        try {
            final String wpServiceArchiveFolderPath =
                    wpServiceArchiveFolder.getParent().getFullPath()
                            .toPortableString();
            IResource[] members = wpServiceArchiveFolder.members();
            if (members != null && members.length > 0) {
                for (final IResource folderMember : members) {
                    if (folderMember instanceof IFolder) {
                        folderMember.accept(new IResourceVisitor() {
                            public boolean visit(IResource resource)
                                    throws CoreException {
                                if (resource instanceof IFile) {
                                    // JA: All resources will be added.
                                    // (Excluding empty folders.)

                                    String staticResourceLocation =
                                            getStaticResourceLocation(resource,
                                                    wpServiceArchiveFolderPath);
                                    includedResources
                                            .add(staticResourceLocation);
                                    return false;
                                }
                                return true;
                            }
                        });
                    } else if (folderMember instanceof IFile) {
                        String staticResourceLocation =
                                getStaticResourceLocation(folderMember,
                                        wpServiceArchiveFolderPath);
                        includedResources.add(staticResourceLocation);
                    }
                }
            }
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns path of form artifacts relative to wp-Service-Archive folder
     * 
     * @param resource
     * @param strFormChannelParentFolder
     * @return
     */
    private String getStaticResourceLocation(IResource resource,
            String strFormChannelParentFolder) {
        String strReourcePath = resource.getFullPath().toPortableString();
        int indexOf = strReourcePath.indexOf(strFormChannelParentFolder);
        String resourceLocation =
                strReourcePath.substring(indexOf
                        + strFormChannelParentFolder.length());
        if (resourceLocation.startsWith("/")) {
            resourceLocation = resourceLocation.substring(1);
        }
        return resourceLocation;
    }

    /**
     * Invoke all of the requirement resolvers contributed to
     * com.tibco.xpd.n2.WPComponentRequirementsResolver extension point.
     * 
     * @param requirements
     * @param wpi
     */
    private void invokeRequirementResolverContributions(
            Requirements requirements, WPImplementation wpi) {
        /*
         * Load extension point contributions....
         */
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(WPActivator.PLUGIN_ID,
                                WP_REQ_RESOLVER_EXTPOINT_ID);
        if (point != null) {
            IConfigurationElement[] requirementsResolvers =
                    point.getConfigurationElements();

            if (requirementsResolvers != null) {
                for (int c = 0; c < requirementsResolvers.length; c++) {
                    IConfigurationElement requirementsResolver =
                            requirementsResolvers[c];

                    Object resolverClass = null;
                    try {
                        resolverClass =
                                requirementsResolver
                                        .createExecutableExtension("class"); //$NON-NLS-1$

                    } catch (CoreException ce) {
                        System.err.println(this.getClass().getName()
                                + "CoreException: " + ce.getMessage()); //$NON-NLS-1$
                        ce.printStackTrace(System.err);
                    }

                    if (resolverClass instanceof IWPComponentRequirementsResolver) {
                        /*
                         * Allow the contribution to add any additional
                         * requirements for WP Component.
                         */
                        ((IWPComponentRequirementsResolver) resolverClass)
                                .addWPComponentRequirements(requirements, wpi);

                    } else {

                        String contributerId =
                                requirementsResolver.getContributor().getName();

                        WPActivator
                                .getDefault()
                                .getLogger()
                                .error(contributerId
                                        + ": " //$NON-NLS-1$
                                        + WP_REQ_RESOLVER_EXTPOINT_ID
                                        + ": Incorrectly defined extension - class must implement " //$NON-NLS-1$
                                        + IBpmnCatchableErrorsContributor.class.getCanonicalName());
                    }

                }
            }
        }

    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.bds.gd.internal.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.RequiredBundle;
import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.componenttype.implementation.BaseComponentTypeResolver;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.ProvidedCapability;
import com.tibco.amf.sca.model.componenttype.Reference;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.componenttype.Service;
import com.tibco.amf.sca.model.composite.ComponentProperty;
import com.tibco.amf.sca.model.composite.CompositeFactory;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.amf.sca.model.extensionpoints.Property;
import com.tibco.n2.model.common.BDSImplementation;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper.PluginProjectDetails;
import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.customfeature.CustomFeatureEnum;
import com.tibco.xpd.n2.cds.utils.CDSCustomFeatureUtils;
import com.tibco.xpd.n2.cds.utils.CDSDependencyUtils;
import com.tibco.xpd.n2.daa.utils.N2PECompositeUtil;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.scriptdescriptor.utils.ScriptDescriptorUtils;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Class for the resolution of a {@link ComponentType} from a given
 * {@link Implementation}
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class BDSComponentTypeResolver extends BaseComponentTypeResolver {

    protected final Logger Log = CDSActivator.getDefault().getLogger();

    /* required supporting bundles */
    private static final String ECLIPSE_EMF_TENEO_ID =
            "com.tibco.tpcl.org.eclipse.emf.teneo"; //$NON-NLS-1$

    private static final String ECLIPSE_EMF_TENEO_ANNOTATIONS_ID =
            "com.tibco.tpcl.org.eclipse.emf.teneo.annotations"; //$NON-NLS-1$

    private static final String ECLIPSE_EMF_TENEO_HIBERATE_ID =
            "com.tibco.tpcl.org.eclipse.emf.teneo.hibernate"; //$NON-NLS-1$

    private static final String BDS_CORE_ID = "com.tibco.bds.core"; //$NON-NLS-1$

    @Override
    public Requirements getComponentRequirements(Implementation implementation) {

        if (implementation instanceof BDSImplementation) {

            BDSImplementation bdsi = (BDSImplementation) implementation;
            Requirements requirements =
                    ComponentTypeFactory.eINSTANCE.createRequirements();
            // Add resources
            addIncludedResources(requirements, bdsi);
            // Add package imports
            addPackageImports(requirements, bdsi);
            // add provided capability
            addProvidedCapability(requirements, bdsi);
            // Add required capabilities
            addRequiredCapabilities(requirements, bdsi);
            // Add required bundles
            addRequiredBundles(requirements, bdsi);
            // Add required feature dependency
            addFeatureDependency(requirements, bdsi);
            // add deployment constraint
            requirements
                    .getDeploymentConstraints()
                    .add(ComponentTypeFactory.eINSTANCE.createProductApplicationDeployment());
            return requirements;
        }
        return super.getComponentRequirements(implementation);
    }

    private void addProvidedCapability(Requirements requirements,
            BDSImplementation bdsi) {

        EList<ProvidedCapability> providedCapabilities =
                requirements.getProvidedCapabilities();
        IProject project = WorkingCopyUtil.getProjectFor(bdsi);

        String projectId = ProjectUtil.getProjectId(project);
        String projectVersion = BOMUtils.getBusinessDataBuildVersion(project); // ProjectUtil.getProjectVersion(project);

        ProvidedCapability providedCapability =
                ComponentTypeFactory.eINSTANCE.createProvidedCapability();
        providedCapability.setId(projectId
                + BDSCompositeUtil.BDS_CAPABILITY_POSTFIX);
        providedCapability.setType(CapabilityType.CUSTOM);
        providedCapability.setVersion(projectVersion);
        providedCapabilities.add(providedCapability);
    }

    private void addIncludedResources(Requirements requirements,
            BDSImplementation bdsi) {

        EList<String> includedResources = requirements.getIncludedResources();

        // Add itself with the full path from project name
        String bdsSpecification = bdsi.getBDSSpecification();
        Path filePath = new Path(bdsSpecification);
        String relativePath = filePath.lastSegment();
        includedResources.add(relativePath);

        // TODO Names could be read from the implementation file.
        includedResources.add(BDSDescriptorGenerator.CASE_MODEL_FILE_NAME);

        // include Global-Data-specific script for cac support etc.
        IProject project = WorkingCopyUtil.getProjectFor(bdsi);
        String projectId = ProjectUtil.getProjectId(project);
        String fileName =
                projectId + ScriptDescriptorUtils.SCRIPT_DESCRIPTOR_FILE_EXT;
        IFile scriptDescriptorFile =
                ScriptDescriptorUtils
                        .getScriptDescriptorFromStagingArea(project, fileName);

        if (scriptDescriptorFile != null) {
            includedResources.add(fileName);
        }
    }

    private void addPackageImports(Requirements requirements,
            BDSImplementation bdsi) {

        EList<ImportPackage> packageImports = requirements.getPackageImports();

        ImportPackage commonImportPackage =
                N2PECompositeUtil.getCommonImportPackage();
        packageImports.add(commonImportPackage);

        /*
         * XPD-3179: Required-Bundle now used over Import-Package
         */
    }

    private void addRequiredCapabilities(Requirements requirements,
            BDSImplementation bdsi) {

        EList<RequiredCapability> includedCapabilities =
                requirements.getRequiredCapabilities();

        RequiredCapability bdsRequiredCapability =
                ComponentTypeFactory.eINSTANCE.createRequiredCapability();

        bdsRequiredCapability.setId(BDSCompositeUtil.bdsCapability);
        bdsRequiredCapability.setType(CapabilityType.FACTORY);
        bdsRequiredCapability.setVersion("1.0.0"); //$NON-NLS-1$
        includedCapabilities.add(bdsRequiredCapability);

        /*
         * ABPM-900: add BDS capability as required capability on BDS component
         * in case of one GDBOM project depends on other GDBOM project
         */
        IProject project = WorkingCopyUtil.getProjectFor(bdsi);
        String qualifierReplacer =
                PluginManifestHelper.getQualifierReplacer(bdsi);
        BDSCompositeUtil.addBDSRequiredCapability(requirements,
                project,
                qualifierReplacer);
    }

    private void addRequiredBundles(Requirements requirements,
            BDSImplementation bdsi) {

        if (bdsi != null) {

            /*
             * XPD-3179: Required-Bundle now used over Import-Package
             */
            String brmModelPath = bdsi.getBDSSpecification();

            if (brmModelPath != null) {

                EList<RequiredBundle> requiredBundles =
                        requirements.getRequiredBundles();

                /* required support plug-ins */
                requiredBundles.add(createRequiredBundle(ECLIPSE_EMF_TENEO_ID,
                        "1.0.0", "2.0.0")); //$NON-NLS-1$ //$NON-NLS-2$
                requiredBundles
                        .add(createRequiredBundle(ECLIPSE_EMF_TENEO_ANNOTATIONS_ID,
                                "1.0.0", "2.0.0")); //$NON-NLS-1$ //$NON-NLS-2$
                requiredBundles
                        .add(createRequiredBundle(ECLIPSE_EMF_TENEO_HIBERATE_ID,
                                "1.0.0", "2.0.0")); //$NON-NLS-1$ //$NON-NLS-2$
                requiredBundles.add(createRequiredBundle(BDS_CORE_ID,
                        "1.0.0", "2.0.0")); //$NON-NLS-1$ //$NON-NLS-2$

                /* required Custom Feature plug-ins */
                IProject project = WorkingCopyUtil.getProjectFor(bdsi);
                /*
                 * XPD-7403: Need to add required bundles on direct and indirect
                 * referenced bom resources
                 */
                Set<IResource> allBOMResources =
                        CDSCustomFeatureUtils.getAllBOMResources(project);

                Set<String> generatorIds = new HashSet<String>();
                for (CustomFeatureEnum featureType : getCustomFeatureTypes(project)) {
                    generatorIds.addAll(featureType.getGeneratorIds());
                }

                BOMGenerator2ExtensionHelper genHelper =
                        new BOMGenerator2ExtensionHelper(generatorIds);

                BOMGenerator2Extension[] extensions = genHelper.getExtensions();

                for (IResource bomRes : allBOMResources) {

                    if ((bomRes instanceof IFile) && (extensions.length > 0)) {

                        /*
                         * XPD-7460: Generated BOM does not have global data
                         * profile. So rather we check for business data project
                         * instead of bom with global data profile. Add it to
                         * the required bundle if the bom resource is in a
                         * business data project
                         */
                        IProject bomProject = bomRes.getProject();
                        if (BOMUtils.isBusinessDataProject(bomProject)) {

                            Collection<IProject> projects =
                                    genHelper
                                            .getGeneratedProjects((IFile) bomRes);

                            for (IProject pr : projects) {

                                if (pr != null && pr.isAccessible()) {

                                    PluginProjectDetails pluginProjectDetails =
                                            PluginManifestHelper
                                                    .getPluginProjectDetails(pr);
                                    RequiredBundle bundle =
                                            createRequiredBundle(pluginProjectDetails);

                                    if (!requiredBundlesListContains(bundle,
                                            requiredBundles)) {
                                        requiredBundles.add(bundle);
                                    }
                                } else {
                                    String fmtStr =
                                            "Generated plugin project '%s' cannot be found/accessed. Ensure the contributor responsible for its generation has been declared and run."; //$NON-NLS-1$
                                    Log.error(String.format(fmtStr, project));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param bundle
     * @param requiredBundles
     * @return <code>true</code> if specified bundle already has entry in
     *         requiredBundles list
     */
    private boolean requiredBundlesListContains(RequiredBundle bundle,
            EList<RequiredBundle> requiredBundles) {

        Comparator<RequiredBundle> requiredBundleComparator =
                new Comparator<RequiredBundle>() {

                    public int compare(RequiredBundle arg0, RequiredBundle arg1) {

                        int lastCmp = arg0.getName().compareTo(arg1.getName());

                        if (lastCmp == 0) {
                            String lowerVerBound_0 = arg0.getRange().getLower();
                            String lowerVerBound_1 = arg1.getRange().getLower();
                            lastCmp =
                                    lowerVerBound_0.compareTo(lowerVerBound_1);

                            if (lastCmp == 0) {
                                String upperVerBound_0 =
                                        arg0.getRange().getUpper();
                                String upperVerBound_1 =
                                        arg1.getRange().getUpper();
                                lastCmp =
                                        upperVerBound_0
                                                .compareTo(upperVerBound_1);
                            }

                        }

                        return lastCmp;
                    }

                };

        ECollections.sort(requiredBundles, requiredBundleComparator);
        int index =
                Collections.binarySearch(requiredBundles,
                        bundle,
                        requiredBundleComparator);

        return (index >= 0);
    }

    /**
     * @param pluginProjectDetails
     * @return
     */
    private RequiredBundle createRequiredBundle(
            PluginProjectDetails pluginProjectDetails) {

        RequiredBundle bundle = OsgiFactory.eINSTANCE.createRequiredBundle();
        bundle.setName(pluginProjectDetails.getBundleId());
        VersionRange ver = OsgiFactory.eINSTANCE.createVersionRange();
        ver.setLower(pluginProjectDetails.getBundleVersion());
        // vr.setLowerIncluded(false);
        ver.setUpper(pluginProjectDetails.getBundleVersion());
        ver.setUpperIncluded(true);
        bundle.setRange(ver);

        return bundle;
    }

    /**
     * Overloaded version that will default the Version's upperIncluded
     * attribute to <code>false</code>
     * 
     * @param bundleId
     * @param lowerVersion
     * @param upperVersion
     * @return <code>RequiredBundle</code> instance created using supplied
     *         bundle ID and bundle version
     */
    private RequiredBundle createRequiredBundle(String bundleId,
            String lowerVersion, String upperVersion) {
        return createRequiredBundle(bundleId, lowerVersion, upperVersion, false);
    }

    /**
     * @param bundleId
     * @param lowerVersion
     * @param upperVersion
     * @param upperIncluded
     * @return <code>RequiredBundle</code> instance created using supplied
     *         bundle ID and bundle version
     */
    private RequiredBundle createRequiredBundle(String bundleId,
            String lowerVersion, String upperVersion, boolean upperIncluded) {

        RequiredBundle bundle = OsgiFactory.eINSTANCE.createRequiredBundle();
        bundle.setName(bundleId);
        VersionRange ver = OsgiFactory.eINSTANCE.createVersionRange();
        ver.setLower(lowerVersion);
        ver.setUpper(upperVersion);
        ver.setUpperIncluded(upperIncluded);
        bundle.setRange(ver);

        return bundle;
    }

    /**
     * if the business data project has case classes then returns the complete
     * custom feature enum set, otherwise returns custom feature enum for bds
     * 
     * @param project
     * @return
     */
    private Set<CustomFeatureEnum> getCustomFeatureTypes(IProject project) {

        if (!BOMGlobalDataUtils.hasCaseDataInProject(project)) {

            return EnumSet.of(CustomFeatureEnum.CDS);
        }
        return EnumSet.of(CustomFeatureEnum.CDS,
                CustomFeatureEnum.SI,
                CustomFeatureEnum.DA);
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
    public IResource[] getImplementationResources(Implementation implementation) {
        // NOTE: This is only for UI
        // TODO Add here references
        return super.getImplementationResources(implementation);
    }

    private void addFeatureDependency(Requirements requirements,
            BDSImplementation bdsi) {
        if (null != requirements) {
            IProject project = getProjectForImplementation(bdsi);

            if (project != null) {
                String qualifierReplacer =
                        PluginManifestHelper.getQualifierReplacer(bdsi);

                /*
                 * for a business data project there will be no xpdl, so passing
                 * the same bom project.
                 */
                BDSCompositeUtil.addRequiredFeatures(requirements,
                        project,
                        project,
                        getCustomFeatureTypes(project),
                        qualifierReplacer);
            }
            requirements.getFeatureDependencies()
                    .add(createGdProductFeatureDependency());
            requirements.getFeatureDependencies()
                    .add(N2PENamingUtils.getBPMCommonModelPF());
        }
    }

    /**
     * add this feature dependency for global data projects as requested under
     * XPD-5971 for multi node env issue
     * 
     * @return
     */
    private static RequiredFeature createGdProductFeatureDependency() {
        RequiredFeature pfd = OsgiFactory.eINSTANCE.createRequiredFeature();
        pfd.setName("com.tibco.amx.bpm.bds.globaldata.runtime.product.feature"); //$NON-NLS-1$
        VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
        versionRange.setLower("1.0.0"); //$NON-NLS-1$
        versionRange.setLowerIncluded(true);
        versionRange.setUpper("2.0.0"); //$NON-NLS-1$
        versionRange.setUpperIncluded(false);
        pfd.setRange(versionRange);
        return pfd;
    }

    private IProject getProjectForImplementation(BDSImplementation bdsi) {

        Resource implEResource = bdsi.eResource();

        if (implEResource != null) {
            IFile file = WorkspaceSynchronizer.getFile(implEResource);

            if (file != null) {
                return file.getProject();
            }
        }
        return null;
    }

    /**
     * @see com.tibco.amf.sca.componenttype.implementation.BaseComponentTypeResolver#getProperties(com.tibco.amf.sca.model.extensionpoints.Implementation)
     * 
     * @param arg0
     * @return
     */
    @Override
    protected List<Property> getProperties(Implementation implementation) {

        if (implementation instanceof BDSImplementation) {

            final String teneoPropName = "BDSCaseDataStoreResource"; //$NON-NLS-1$
            final String hibernateNS =
                    "http://xsd.tns.tibco.com/amf/models/sharedresource/hibernate"; //$NON-NLS-1$
            final String teneoLocalPart = "TeneoSessionFactory"; //$NON-NLS-1$ 

            ComponentProperty componentProp =
                    CompositeFactory.eINSTANCE.createComponentProperty();
            componentProp.setName(teneoPropName);
            QName qname = new QName(hibernateNS, teneoLocalPart);
            componentProp.setType(qname);
            componentProp.setMustSupply(true);
            componentProp.setSimpleValue(teneoPropName);

            List<Property> props = new ArrayList<Property>();
            props.add(componentProp);

            return props;
        }

        return null;
    }

    /**
     * @see com.tibco.amf.sca.componenttype.implementation.BaseComponentTypeResolver#getReferences(com.tibco.amf.sca.model.extensionpoints.Implementation)
     * 
     * @param arg0
     * @return
     */
    @Override
    protected List<Reference> getReferences(Implementation arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.amf.sca.componenttype.implementation.BaseComponentTypeResolver#getServices(com.tibco.amf.sca.model.extensionpoints.Implementation)
     * 
     * @param arg0
     * @return
     */
    @Override
    protected List<Service> getServices(Implementation arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.amf.sca.componenttype.implementation.BaseComponentTypeResolver#isAvailable(com.tibco.amf.sca.model.extensionpoints.Implementation)
     * 
     * @param arg0
     * @return
     */
    @Override
    protected boolean isAvailable(Implementation implementation) {
        return implementation instanceof BDSImplementation;
    }
}

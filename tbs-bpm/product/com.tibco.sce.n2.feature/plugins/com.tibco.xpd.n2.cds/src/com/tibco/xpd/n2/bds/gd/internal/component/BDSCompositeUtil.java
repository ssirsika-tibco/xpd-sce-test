/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.bds.gd.internal.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.Version;

import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.RequiredBundle;
import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.dependency.osgi.VersionRange;
import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.gen.internal.BOMGeneratorHelper;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.daa.internal.util.CustomFeatureUtils;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper.PluginProjectDetails;
import com.tibco.xpd.implementer.nativeservices.utilities.TaskServiceExtUtil;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.customfeature.CustomFeatureEnum;
import com.tibco.xpd.n2.cds.utils.CDSCustomFeatureUtils;
import com.tibco.xpd.n2.cds.utils.CDSDependencyUtils;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.wsdltobom.indexer.WsdlBomIndexerUtil;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class to <li>
 * Create/add bds required capability on requested components</li>
 * 
 * @author bharge
 * @since 29 Aug 2013
 */
public class BDSCompositeUtil {

    /** BDS provided capability postfix. */
    public static final String BDS_CAPABILITY_POSTFIX = ".bds.capability"; //$NON-NLS-1$

    private static final Logger LOG = CDSActivator.getDefault().getLogger();

    public static String bdsCapability =
            "com.tibco.amx.capability.implementation.bds";

    /**
     * Add bds required capability to the existing requirements capability
     * 
     * @param requirements
     * @param project
     * @param qualifierReplacer
     */
    public static void addBDSRequiredCapability(Requirements requirements,
            IProject project, String qualifierReplacer) {

        EList<RequiredCapability> includedCapabilities =
                requirements.getRequiredCapabilities();

        List<RequiredCapability> bomReqCapList =
                getBDSRequiredCapabilityList(project, includedCapabilities);

        includedCapabilities.addAll(bomReqCapList);

    }

    /**
     * For a given project get the referenced project to find if it is a global
     * data bom project and create the required capability on the referencing
     * project
     * 
     * @param project
     * @param existingCapabilities
     *            - already existing capabilities
     * @return existing list of capabilities and the new ones
     */
    public static List<RequiredCapability> getBDSRequiredCapabilityList(
            IProject project, EList<RequiredCapability> existingCapabilities) {

        List<RequiredCapability> requiredCapabilityList =
                new ArrayList<RequiredCapability>();

        if (null != project && project.isAccessible()) {

            Set<IProject> referencedProjects = new HashSet<IProject>();
            if (BOMUtils.isBusinessDataProject(project)) {

                try {
                    /*
                     * If the given project is a business data project then we
                     * are not interested in getting the project references from
                     * the util because we want to add the requires capability
                     * only on the directly referenced project and not on any
                     * recursive indirect references.
                     */
                    IProject[] refProjs = project.getReferencedProjects();
                    referencedProjects.addAll(Arrays.asList(refProjs));
                } catch (CoreException e) {

                    LOG.error(e.getMessage());
                }
            } else {

                /*
                 * XPD-7413: If the given project is a process project then we
                 * need to add requires capability for any direct dependency or
                 * first indirect dependency on business data project
                 */
                referencedProjects =
                        getFirstDirectOrIndirectRefBDProjectForProcProject(project,
                                referencedProjects);
            }

            /*
             * Finally go thru the list of referenced projects and add requires
             * capability only on BD project
             */
            if (null != referencedProjects && !referencedProjects.isEmpty()) {

                for (IProject refProject : referencedProjects) {

                    if (BOMUtils.isBusinessDataProject(refProject)) {

                        addReqCapability(project,
                                requiredCapabilityList,
                                existingCapabilities,
                                refProject);
                    }
                }
            }

        } else {

            String fmtStr =
                    "Generated plugin project '%s' cannot be found/accessed. Ensure the contributor responsible for its generation has been declared and run."; //$NON-NLS-1$
            LOG.error(String.format(fmtStr, project));
        }

        return requiredCapabilityList;
    }

    /**
     * XPD-7413: If the given project is a process project then we need to add
     * requires capability for direct dependencies or first indirect dependency
     * on business data project. (We want to add only first indirect business
     * data project reference to process project because admin cannot detect the
     * indirectly referenced bds capability and causes undeploy issues. The
     * references to this indirect bds reference is not required to be added in
     * the process project because admin resolves them correctly from bdp
     * project capabilities and hence we don't need to add them.
     * 
     * <p>
     * For instance PP depends on a LBOP that depends on a BDP (PP -> LBOP ->
     * BDP) or a PP depends on a BOM in another PP that depends on a BDP (PP ->
     * PP2 -> BDP).
     * </p>
     * <p>
     * In the first case LBOP is not deployed so it should be possible to detect
     * that PP depends on BDP via LBOP and should include a requires capability
     * on BDP in PP.
     * </p>
     * <p>
     * In the second case deploying PP2 includes a requires capability on BDP.
     * When PP is deployed, it takes the copy of PP2 bds bundles (PP2 doesn't
     * need to be deployed). So PP also must include the requires capability on
     * BDP
     * </p>
     * 
     * 
     * @param bpmProject
     * @return referencedProjects - set containing direct and indirect
     *         referenced bpm developer process projects and first indirect
     *         referenced business data project
     */
    private static Set<IProject> getFirstDirectOrIndirectRefBDProjectForProcProject(
            IProject bpmProject, Set<IProject> referencedProjects) {

        try {

            IProject[] directReference = bpmProject.getReferencedProjects();
            referencedProjects.addAll(Arrays.asList(directReference));
            for (IProject refProject : directReference) {

                if (!BOMUtils.isBusinessDataProject(refProject)) {

                    getFirstDirectOrIndirectRefBDProjectForProcProject(refProject,
                            referencedProjects);
                }
            }
        } catch (CoreException e) {

            LOG.error(e.getMessage());
        }

        return referencedProjects;
    }

    /**
     * Creates a required capability and adds it to the existing list only if it
     * doesn't already exist
     * 
     * @param sourceProject
     * @param requiredCapabilityList
     * @param existingCapabilities
     * @param bomProject
     */
    private static void addReqCapability(IProject sourceProject,
            List<RequiredCapability> requiredCapabilityList,
            EList<RequiredCapability> existingCapabilities, IProject bomProject) {

        if (sourceProject != bomProject) {

            String fileVersion = getBOMProjectVersion(bomProject);

            RequiredCapability createReqCapability =
                    createRequiredCapability(bomProject, fileVersion);
            boolean alreadyExisting = false;
            for (RequiredCapability existingReqCapability : existingCapabilities) {

                if (CDSDependencyUtils.doRequiredCapabilitiesMatch(bomProject,
                        existingReqCapability,
                        createReqCapability)) {

                    alreadyExisting = true;
                    break;
                }
            }
            if (!alreadyExisting) {

                requiredCapabilityList.add(createReqCapability);
                /*
                 * If BDP1 depends on BDP2 add BDP2 project's bds capability as
                 * withs capability on the basic bds component implementation
                 * capability in BDP1 project's required capabilities
                 */
                for (RequiredCapability rc : existingCapabilities) {

                    if (bdsCapability.equals(rc.getId())
                            && CapabilityType.FACTORY.equals(rc.getType())) {

                        rc.getWiths().add(createReqCapability);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Get the version of the bom project (removing the timestamped qualifier)
     * 
     * @param bomProject
     * @return
     */
    private static String getBOMProjectVersion(IProject bomProject) {

        String bomFileVersion = ProjectUtil.getProjectVersion(bomProject);
        if (BOMUtils.isBusinessDataProject(bomProject)) {

            return BOMUtils.getBusinessDataBuildVersion(bomProject);
        }
        return bomFileVersion;

    }

    /**
     * Create the required capability on the given project and version
     * 
     * @param project
     * @param bomFileVersion
     * @return {@link RequiredCapability} with the version and id set
     */
    private static RequiredCapability createRequiredCapability(
            IProject project, String bomFileVersion) {

        String projectId = ProjectUtil.getProjectId(project);
        RequiredCapability requiredCapability =
                ComponentTypeFactory.eINSTANCE.createRequiredCapability();
        requiredCapability.setId(projectId + BDS_CAPABILITY_POSTFIX);
        requiredCapability.setType(CapabilityType.CUSTOM);

        Version minVersion = new Version(bomFileVersion);
        // Version maxVersion = new Version(minVersion.getMajor() + 1, 0, 0);
        Version maxVersion = new Version(bomFileVersion);

        VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
        versionRange.setLower(minVersion.toString());
        versionRange.setLowerIncluded(Boolean.TRUE);
        versionRange.setUpper(maxVersion.toString());
        versionRange.setUpperIncluded(Boolean.TRUE);

        requiredCapability.setVersion(versionRange.toString());

        return requiredCapability;
    }

    /**
     * For a process project add required bundles on referenced bds projects if
     * there is a direct / indirect dependency from the process.
     * <p>
     * If the dependency is from a BOM in the process project to a BOM in the
     * Business Data project, then <b>NO</b> need to declare it.
     * </p>
     * <p>
     * But if the dependency is from a BOM in the process project to a BOM in a
     * non-Business Data project, then we <b>NEED</b> to declare it.
     * </p>
     * 
     * @param processProject
     * @param requiredBundles
     * @param qualifierReplacer
     */
    public static void addRequiredBundles(IProject processProject,
            List<RequiredBundle> requiredBundles, String qualifierReplacer) {

        /**
         * Case 1: Process Dependency: Process refers BOM type from BD project
         * (basically no dependency from its own local BOM to other BD BOM). Add
         * required bundles for referenced projects if there is a direct
         * dependency from the process.
         * 
         * Process project directly depending on the bom (bom can be internal or
         * external to process project. If it is internal it is a non-Business
         * Data Project (its own bom in BPM Project). If it is external it can
         * be a Business Data Project or non-Business Data Project).
         * 
         * In either case require bundle must declare the referenced bds bundle
         * (Even though in case of Business Data Project reference there is a
         * requires capability on BDP project from process project, we need
         * require bundle reference on the directly referenced bom)
         */
        Set<IResource> bomsForRequiredBundles = new HashSet<IResource>();
        if (processProject.isAccessible()) {

            /*
             * XPD-7452: Hitherto XPD-7452 we used to check for type references
             * from the process(es) in the process project and used to add
             * required bundles only on those type (data field or formal
             * parameter) referenced bom(s). But we had to change this because
             * XPD-7431 demanded to add requires capability on the indirectly
             * referenced business data project from process project. That would
             * cause upgrade issues if required bundles on the bom(s) from those
             * indirectly referenced business data project are not added. So now
             * we get all the BOMs (direct/indirect) referenced from a process
             * to be declared as required bundle.
             */
            Set<IResource> bomsRefFromProcProj =
                    getBomsRefFromProcProj(processProject);
            bomsForRequiredBundles.addAll(bomsRefFromProcProj);
        }

        Set<PluginProjectDetails> bdpPluginProjDetailsSet =
                new HashSet<PluginManifestHelper.PluginProjectDetails>();
        /*
         * Get the generated bds plugin projects for the directly referenced
         * boms
         */
        Set<IResource> caseBOMsByRef =
                getCaseBOMsInBusinessDataProjects(processProject);

        for (IResource bomResource : bomsForRequiredBundles) {

            IProject bomProject = bomResource.getProject();

            if (BOMUtils.isBusinessDataProject(bomProject)) {

                EnumSet<CustomFeatureEnum> enumSet =
                        EnumSet.of(CustomFeatureEnum.CDS);
                if (BOMGlobalDataUtils.hasGlobalCaseClass(bomResource)) {

                    /*
                     * If there are no references from a process project to case
                     * classes by ref, then we don't want to have required
                     * bundle dependency on si.bds projects
                     */
                    if (caseBOMsByRef.contains(bomResource)) {

                        enumSet =
                                EnumSet.of(CustomFeatureEnum.CDS,
                                        CustomFeatureEnum.SI);
                    }
                }

                Set<String> generatorIds = new HashSet<String>();
                for (CustomFeatureEnum featureType : enumSet) {

                    generatorIds.addAll(featureType.getGeneratorIds());
                }

                BOMGenerator2ExtensionHelper genHelper =
                        new BOMGenerator2ExtensionHelper(generatorIds);

                Collection<IProject> projects =
                        genHelper.getGeneratedProjects((IFile) bomResource);
                for (IProject iProject : projects) {

                    if (iProject.isAccessible()) {

                        PluginProjectDetails pluginProjectDetails =
                                PluginManifestHelper
                                        .getPluginProjectDetails(iProject);

                        pluginProjectDetails.setBundleVersion(BOMUtils
                                .getBusinessDataBuildVersion(bomProject));

                        bdpPluginProjDetailsSet.add(pluginProjectDetails);
                    }
                }
            }
        }

        /* Declare them in the required bundles if they are not already declared */
        for (PluginProjectDetails pluginProjectDetails : bdpPluginProjDetailsSet) {

            RequiredBundle createRequiredBundle =
                    createRequiredBundle(pluginProjectDetails);
            boolean alreadyPresent = false;

            for (RequiredBundle existingRB : requiredBundles) {

                if (existingRB == null) {

                    continue;
                }
                if (CDSDependencyUtils.doRequiredBundleMatch(existingRB,
                        createRequiredBundle)) {

                    alreadyPresent = true;
                    break;
                }
            }
            if (!alreadyPresent) {

                requiredBundles.add(createRequiredBundle);
            }
        }

        /**
         * Case 2: BOM Dependency from process project: If the dependency is
         * from a BOM in process project to BOM in Business Data project, then
         * no need to declare it (because they will be implicitly pulled in by
         * requires capability mechanism). If the dependency is from a BOM in
         * process project to BOM in non-Business Data project then we need to
         * declare it (because the referenced boms are packaged into this daa).
         * So we get boms defined in the process project (and its referenced
         * projects that are not Business Data Projects) and declare the
         * required bundles .bds on them
         */
        Map<String, String> genPluginsForLocalBOM =
                getGenPluginsForLocalBOMs(processProject);

        Set<Entry<String, String>> entrySet = genPluginsForLocalBOM.entrySet();
        /* Declare them in the required bundles */
        for (Entry<String, String> eachEntry : entrySet) {

            String pluginId = eachEntry.getKey();
            String pluginVersion = eachEntry.getValue();
            RequiredBundle createRequireBundleFromIncludedPlugin =
                    CDSDependencyUtils
                            .createRequireBundleFromIncludedPlugin(pluginId,
                                    pluginVersion,
                                    qualifierReplacer);
            requiredBundles.add(createRequireBundleFromIncludedPlugin);
        }

    }

    /**
     * Create and return the required bundle object
     * 
     * @param pluginProjectDetails
     * @return {@link RequiredBundle} with the bundle id and bundle version for
     *         the given plugin project
     */
    public static RequiredBundle createRequiredBundle(
            PluginProjectDetails pluginProjectDetails) {

        RequiredBundle bundle = OsgiFactory.eINSTANCE.createRequiredBundle();
        bundle.setName(pluginProjectDetails.getBundleId());

        String bundleVersion = pluginProjectDetails.getBundleVersion();
        VersionRange versionRange = getVersionRange(bundleVersion);
        bundle.setRange(versionRange);

        return bundle;
    }

    /**
     * 
     * @param bundleVersion
     * @return {@link VersionRange} for the given bundle version string
     */
    private static VersionRange getVersionRange(String bundleVersion) {

        VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
        Version minVersion = new Version(bundleVersion);
        // Version maxVersion = new Version(minVersion.getMajor() + 1, 0, 0);
        Version maxVersion = new Version(bundleVersion);

        versionRange.setLower(minVersion.toString());
        versionRange.setLowerIncluded(Boolean.TRUE);
        versionRange.setUpper(maxVersion.toString());
        versionRange.setUpperIncluded(Boolean.TRUE);
        return versionRange;
    }

    /**
     * Returns bds plugin project(s) for process project referencing any bom by
     * val that is not a global data bom
     * 
     * @param processProject
     * @return map of project id and version
     */
    private static Map<String, String> getGenPluginsForLocalBOMs(
            IProject processProject) {

        Set<IResource> bomsToBeTreatedAsLocal = getLocalBOMs(processProject);

        Map<String, String> generatedProjectIds =
                CustomFeatureUtils
                        .getGeneratedEMFProjectIdsWithVersion(bomsToBeTreatedAsLocal,
                                CDSCustomFeatureUtils.BOM_CDS_GENERATOR_ID);

        return generatedProjectIds;
    }

    /**
     * Returns all the bom resources directly/indirectly referenced from a
     * process project. Gets all the referenced boms (user defined and
     * generated) for the given project (and its referenced projects).
     * 
     * @param processProject
     * @return Set of direct/indirect referenced bom resources from a process
     *         project
     */
    private static Set<IResource> getBomsRefFromProcProj(IProject processProject) {

        Set<IResource> bomsRefFromProcProj = new HashSet<IResource>();

        if (processProject.isAccessible()) {

            Set<IResource> allBOMResources =
                    CDSCustomFeatureUtils.getAllBOMResources(processProject);
            /*
             * XPD-7452: Don't have to check for type references. Include bds
             * bundles for all direct/indirect project references
             * 
             * Hitherto XPD-7452 we used to check for type references from the
             * process(es) in the process project and used to add required
             * bundles only on those type (data field or formal parameter)
             * referenced bom(s). But we had to change this because XPD-7431
             * demanded to add requires capability on the indirectly referenced
             * business data project from process project. That would cause
             * upgrade issues if required bundles on the bom(s) from those
             * indirectly referenced business data project are not added. So now
             * we get all the BOMs (direct/indirect) referenced from a process
             * to be declared as required bundle.
             */

            // Set<ExternalReference> allExternalReference =
            // getAllExternalReference(processProject);
            // for (ExternalReference externalReference : allExternalReference)
            // {
            //
            // if (null != externalReference) {
            //
            // String location = externalReference.getLocation();
            // for (IResource bomRes : allBOMResources) {
            //
            // if (location.equals(bomRes.getName())) {
            //
            // bomsDirectlyRefFromProcess.add(bomRes);
            // }
            // }
            // }
            // }
            bomsRefFromProcProj.addAll(allBOMResources);

            /*
             * Sid XPD-7571. ScriptDescriptorGenerator also adds factories for
             * all BOMs related to WSDLs that are referenced from the processes
             * in the project.
             * 
             * The reason that this is significant over the direct/indirect BOM
             * references via data is that it is possible that a process has
             * web-service activities <b>without</b> having any process data
             * that references the BOM types related to the WSDL.
             * 
             * However, at runtime the process engine will still be using the
             * BOM factories for creation of the WSDL message part related BOM
             * objects; therefore we need the bundles for those.
             */
            Set<IResource> bomsReferencedViaWsdls =
                    getBomsReferencedViaWsdls(processProject);

            bomsRefFromProcProj.addAll(bomsReferencedViaWsdls);
        }

        return bomsRefFromProcProj;
    }

    /**
     * Get all of the BOMs that the process references via WSDL references.
     * 
     * @param processProject
     * 
     * @return
     */
    private static Set<IResource> getBomsReferencedViaWsdls(
            IProject processProject) {

        Set<IResource> referencedBoms = new HashSet<IResource>();

        List<IResource> xpdlResources =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(processProject,
                                N2PENamingUtils.PROCESS_SPECIALFOLDER_KIND,
                                N2PENamingUtils.XPDL_FILE_EXTENSION,
                                false);

        for (final IResource xpdlRes : xpdlResources) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlRes);

            if (wc instanceof Xpdl2WorkingCopyImpl) {
                Package pkg = (Package) wc.getRootElement();

                for (Process process : pkg.getProcesses()) {

                    Collection<Activity> allActivitiesInProc =
                            Xpdl2ModelUtil.getAllActivitiesInProc(process);

                    /* Check all the activities of a process for wsdl reference */
                    Set<IResource> wsdlReferencedFromProcess =
                            new HashSet<IResource>();

                    for (Activity eachActivity : allActivitiesInProc) {
                        IFile wsdlFile =
                                Xpdl2WsdlUtil.getWsdlFile(eachActivity);
                        if (wsdlFile != null && wsdlFile.exists()) {
                            wsdlReferencedFromProcess.add(wsdlFile);
                        }
                    }

                    for (IResource wsdlResource : wsdlReferencedFromProcess) {
                        /* Get all the BOM's referenced from the wsdl. */
                        Set<IResource> queryBOMsForWSDL =
                                WsdlBomIndexerUtil
                                        .queryBOMsForWSDL(wsdlResource);

                        referencedBoms.addAll(queryBOMsForWSDL);
                    }
                }
            }
        }

        return referencedBoms;
    }

    /**
     * Get all the external bom reference locations all the processes (or
     * process package) in a process project point to
     * 
     * @param processProject
     * @return Set of external reference objects process bom type or case ref
     *         types point to
     */
    private static Set<ExternalReference> getAllExternalReference(
            IProject processProject) {

        Set<ExternalReference> allExternalRef =
                new HashSet<ExternalReference>();

        if (processProject.isAccessible()) {

            List<ProcessRelevantData> allExtRefPRD =
                    new ArrayList<ProcessRelevantData>();
            List<IResource> xpdlResources =
                    SpecialFolderUtil
                            .getAllDeepResourcesInSpecialFolderOfKind(processProject,
                                    N2PENamingUtils.PROCESS_SPECIALFOLDER_KIND,
                                    N2PENamingUtils.XPDL_FILE_EXTENSION,
                                    false);
            for (final IResource xpdlRes : xpdlResources) {

                IFile xpdlFile = (IFile) xpdlRes;
                Package processPackage = null;

                try {

                    processPackage = BPELN2Utils.getProcessPackage(xpdlFile);
                } catch (IOException e) {

                    LOG.error(e);
                }
                if (null != processPackage) {

                    List<ProcessRelevantData> pkgExtRefPRD =
                            ProcessInterfaceUtil
                                    .getAllExternalProcessRelevantData(processPackage);
                    if (!pkgExtRefPRD.isEmpty()) {

                        allExtRefPRD.addAll(pkgExtRefPRD);
                    }
                    EList<Process> processes = processPackage.getProcesses();
                    for (Process process : processes) {

                        List<ProcessRelevantData> processExtRefPRD =
                                ProcessInterfaceUtil
                                        .getAllExternalProcessRelevantData(process);
                        if (!processExtRefPRD.isEmpty()) {

                            allExtRefPRD.addAll(processExtRefPRD);
                        }
                    }
                }
            }
            for (ProcessRelevantData prd : allExtRefPRD) {

                ExternalReference externalReference = null;
                if (prd.getDataType() instanceof RecordType) {

                    RecordType recordType = (RecordType) prd.getDataType();
                    EList<Member> memberList = recordType.getMember();
                    Member member = memberList.get(0);
                    externalReference = member.getExternalReference();
                } else if (prd.getDataType() instanceof ExternalReference) {

                    externalReference = (ExternalReference) prd.getDataType();
                }
                if (null != externalReference) {

                    allExternalRef.add(externalReference);
                }
            }
        }

        return allExternalRef;
    }

    /**
     * Get the global boms referenced as case ref types from process relevant
     * data and global data task(s)
     * 
     * @param processProject
     * @return set of global data bom resources that are referenced from a
     *         process package by reference
     */
    private static Set<IResource> getCaseBOMsInBusinessDataProjects(
            IProject processProject) {

        Set<IResource> caseBomsByRef = new HashSet<IResource>();
        Set<ExternalReference> extRefSet = new HashSet<ExternalReference>();

        if (processProject.isAccessible()) {

            Set<IResource> projectBOMResources =
                    CDSCustomFeatureUtils.getAllBOMResources(processProject);

            List<IResource> xpdlResources =
                    SpecialFolderUtil
                            .getAllDeepResourcesInSpecialFolderOfKind(processProject,
                                    N2PENamingUtils.PROCESS_SPECIALFOLDER_KIND,
                                    N2PENamingUtils.XPDL_FILE_EXTENSION,
                                    false);
            List<ProcessRelevantData> allCaseRefData =
                    new ArrayList<ProcessRelevantData>();

            for (final IResource xpdlRes : xpdlResources) {

                IFile xpdlFile = (IFile) xpdlRes;
                Package processPackage = null;

                try {
                    processPackage = BPELN2Utils.getProcessPackage(xpdlFile);
                } catch (IOException e) {
                    LOG.error(e);
                }

                if (null != processPackage) {

                    List<ProcessRelevantData> pkgCaseRefData =
                            ProcessInterfaceUtil
                                    .getAllCaseRefData(processPackage);
                    if (pkgCaseRefData.size() > 0) {

                        allCaseRefData.addAll(pkgCaseRefData);
                    }

                    for (Process proc : processPackage.getProcesses()) {

                        List<ProcessRelevantData> procCaseRefData =
                                ProcessInterfaceUtil.getAllCaseRefData(proc);
                        if (procCaseRefData.size() > 0) {

                            allCaseRefData.addAll(procCaseRefData);
                        }
                        /*
                         * XPD-7844: Global data task is a script task at
                         * runtime, so Case class(es) referenced via global data
                         * task(s) need to be considered as equivalent to case
                         * ref types from process relevant data. So that
                         * references to those bom resources can be picked up
                         * for adding .si.bds required bundles.
                         */
                        Collection<Activity> allActivitiesInProc =
                                Xpdl2ModelUtil.getAllActivitiesInProc(proc);
                        /*
                         * Get global data task(s) in a process
                         */
                        for (Activity eachActivity : allActivitiesInProc) {

                            Implementation implementation =
                                    eachActivity.getImplementation();
                            if (implementation instanceof Task) {

                                Task task = (Task) implementation;
                                if (task.getTaskService() != null) {

                                    EObject extendedModel =
                                            TaskServiceExtUtil
                                                    .getExtendedModel(task
                                                            .getTaskService(),
                                                            XpdExtensionPackage.eINSTANCE
                                                                    .getDocumentRoot_GlobalDataOperation());
                                    /*
                                     * Get the corresponding bom resource
                                     * reference from the case class referenced
                                     * by the global data task and add it to the
                                     * external reference list. These bom
                                     * resources can then be added to case boms
                                     * reference list and in turn they can be
                                     * picked up for adding .si.bds required
                                     * bundles
                                     */
                                    if (extendedModel instanceof GlobalDataOperation) {

                                        GlobalDataOperation globalDataOp =
                                                (GlobalDataOperation) extendedModel;
                                        CaseAccessOperationsType caseAccessOperations =
                                                globalDataOp
                                                        .getCaseAccessOperations();
                                        if (null != caseAccessOperations) {

                                            ExternalReference externalReference =
                                                    caseAccessOperations
                                                            .getCaseClassExternalReference();
                                            if (null != externalReference) {

                                                extRefSet
                                                        .add(externalReference);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (ProcessRelevantData processRelevantData : allCaseRefData) {

                if (processRelevantData.getDataType() instanceof RecordType) {

                    RecordType recordType =
                            (RecordType) processRelevantData.getDataType();
                    EList<Member> memberList = recordType.getMember();
                    Member member = memberList.get(0);

                    ExternalReference externalReference =
                            member.getExternalReference();
                    if (null != externalReference) {

                        extRefSet.add(externalReference);
                    }
                }
            }

            for (ExternalReference externalReference : extRefSet) {

                String location = externalReference.getLocation();
                for (IResource bomRes : projectBOMResources) {

                    if (location.equals(bomRes.getName())) {

                        caseBomsByRef.add(bomRes);
                    }
                }
            }
        }

        return caseBomsByRef;

    }

    /**
     * Returns the set of user defined and generated boms referenced by a
     * process project (and its referenced projects)
     * 
     * @param processProject
     * @return set of local bom resources
     */
    public static Set<IResource> getLocalBOMs(IProject processProject) {

        Set<IResource> localBoms = new LinkedHashSet<IResource>();

        List<IFile> allBOMFilesForProject = null;
        try {
            /* XPD-5432: need to get the generated and user defined boms */
            allBOMFilesForProject =
                    BOMGeneratorHelper.getAllBOMFilesForProject(processProject);
            for (IFile iFile : allBOMFilesForProject) {

                IProject bomProject = iFile.getProject();
                if (!BOMUtils.isBusinessDataProject(bomProject)) {

                    localBoms.add(iFile);
                }
            }
        } catch (CoreException e) {
            LOG.error(e);
        }
        return localBoms;
    }

    /**
     * Adds CustomFeature file entry (if present) to the requirements object
     * 
     * @param requirements
     * @param xpdlProject
     * @param qualifierReplacer
     */
    public static void addRequiredFeatures(Requirements requirements,
            IProject bomProject, IProject xpdlProject,
            Set<CustomFeatureEnum> custFeatures, String qualifierReplacer) {

        if (custFeatures == null) {

            addRequiredFeatures(requirements,
                    bomProject,
                    xpdlProject,
                    qualifierReplacer);
        } else {

            for (CustomFeatureEnum custFeature : custFeatures) {

                RequiredFeature createRequiredFeature =
                        createRequiredFeature(bomProject,
                                xpdlProject,
                                qualifierReplacer,
                                custFeature);

                boolean alreadyPresent = false;
                EList<RequiredFeature> featureDependencies =
                        requirements.getFeatureDependencies();

                for (RequiredFeature requiredFeature : featureDependencies) {

                    if (requiredFeature == null) {
                        continue;
                    }
                    if (CDSDependencyUtils
                            .doRequiredFeatureMatch(requiredFeature,
                                    createRequiredFeature)) {
                        alreadyPresent = true;
                        break;
                    }
                }

                if (!alreadyPresent && null != createRequiredFeature) {

                    featureDependencies.add(createRequiredFeature);
                }
            }
        }
    }

    /**
     * Adds CustomFeature file entry (if present) to the requirements object
     * 
     * @param requirements
     * @param xpdlProject
     * @param qualifierReplacer
     */
    public static void addRequiredFeatures(Requirements requirements,
            IProject bomProject, IProject xpdlProject, String qualifierReplacer) {

        RequiredFeature createRequiredFeature =
                createRequiredFeature(bomProject,
                        xpdlProject,
                        qualifierReplacer,
                        null);

        boolean alreadyPresent = false;
        EList<RequiredFeature> featureDependencies =
                requirements.getFeatureDependencies();

        for (RequiredFeature requiredFeature : featureDependencies) {

            if (requiredFeature == null) {
                continue;
            }
            if (CDSDependencyUtils.doRequiredFeatureMatch(requiredFeature,
                    createRequiredFeature)) {
                alreadyPresent = true;
                break;
            }
        }

        if (!alreadyPresent && null != createRequiredFeature) {

            featureDependencies.add(createRequiredFeature);
        }
    }

    /**
     * Returns instance of RequiredFeature if a Custom feature file exists on
     * Staging area. If custom feature file is not present then returns null.
     * 
     * @param xpdlProject
     * @param qualifierReplacer
     * @return
     */
    private static RequiredFeature createRequiredFeature(IProject bomProject,
            IProject xpdlProject, String qualifierReplacer,
            CustomFeatureEnum custFeatureEnum) {

        IResource customFeature =
                CDSCustomFeatureUtils.getStagingCustomFeatureFile(xpdlProject
                        .getName(), custFeatureEnum);

        if (customFeature != null && customFeature.isAccessible()) {

            RequiredFeature requiredFeature =
                    OsgiFactory.eINSTANCE.createRequiredFeature();
            String featureSuffix =
                    (custFeatureEnum != null) ? custFeatureEnum
                            .getFeatureSuffix() : null;
            requiredFeature.setName(N2PENamingUtils
                    .getCustomFeatureId(bomProject, featureSuffix));
            String customFeatureVersion =
                    CDSUtils.getCustomFeatureVersion(bomProject);
            requiredFeature.setRange(getVersionRange(customFeatureVersion));
            return requiredFeature;
        }
        return null;
    }

}

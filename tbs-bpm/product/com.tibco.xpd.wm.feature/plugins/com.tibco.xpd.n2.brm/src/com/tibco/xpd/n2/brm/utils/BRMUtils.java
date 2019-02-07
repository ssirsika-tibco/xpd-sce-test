/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.brm.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.handlers.PruneHandler;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.osgi.framework.Version;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.n2.brm.BRMActivator;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.om.core.om.BaseOrgModel;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author kupadhya
 * 
 */
public class BRMUtils {

    private static final Logger LOG = BRMActivator.getDefault().getLogger();

    private static final String CAPABILITY = ".capability"; //$NON-NLS-1$

    public static final String BRM_REQUIRED_CAPABILITY_ID =
            "com.tibco.amx.capability.implementation.brm"; //$NON-NLS-1$

    public static final String EC_REQUIRED_CAPABILITY_ID =
            "com.tibco.amx.capability.implementation.ec"; //$NON-NLS-1$

    public static final String BRM_MODEL_FILENAME = "brm-spec.brm";//$NON-NLS-1$

    public static final String EC_MODEL_FILENAME = "ec-spec.ec";//$NON-NLS-1$

    // public static final String BRM_MODULES_SPECIAL_FOLDER = ".brmModules";
    // //$NON-NLS-1$

    public static final String WT_FILE_NAME = "wt.xml";//$NON-NLS-1$

    public static final String WM_FILE_NAME = "wm.xml";//$NON-NLS-1$

    public static final String WLF_FILE_NAME = "wlf.xml";//$NON-NLS-1$

    /** XPDL file extension. */
    private static final String XPDL_EXTENSION = "xpdl"; //$NON-NLS-1$

    public static IResource getWTResource(IContainer stagingArea) {
        if (stagingArea == null || !stagingArea.exists()) {
            return null;
        }
        IFile file = stagingArea.getFile(new Path(WT_FILE_NAME));
        if (file.exists()) {
            return file;
        }
        return null;
    }

    /** OM indexer id. */
    public static final String OM_INDEXER =
            "com.tibco.xpd.om.resources.indexing.omIndexer"; //$NON-NLS-1$

    /** Special folder kind for OM special folders. */
    public final static String OM_SPECIAL_FOLDER_KIND = "om"; //$NON-NLS-1$

    public static EObject getOMModelElement(
            ExternalReference externalReference) {

        String sfRelativeLoc = externalReference.getLocation();

        /*
         * JA: Fixes .om files with spaces (location contains for example %20
         * for spaces).
         */
        sfRelativeLoc = URI.decode(sfRelativeLoc);
        String orgModelEntityId = externalReference.getXref();

        IProject contextProject =
                WorkingCopyUtil.getProjectFor(externalReference);
        IndexerService service =
                XpdResourcesPlugin.getDefault().getIndexerService();

        IndexerItemImpl criteria = new IndexerItemImpl();

        criteria.set("id", orgModelEntityId); //$NON-NLS-1$

        Collection<IndexerItem> items = service.query(OM_INDEXER, criteria);

        for (IndexerItem indexerItem : items) {
            String uriString = indexerItem.getURI();
            try {
                IResource resourceFromURL = getIResourceFromURL(uriString);
                if (resourceFromURL instanceof IFile) {
                    IProject urlProject = resourceFromURL.getProject();
                    // Check if resource in the same or referenced project.
                    if (contextProject.equals(urlProject) || ProjectUtil
                            .isProjectReferenced(contextProject, urlProject)) {
                        // And it is in the OM special folder.
                        ProjectConfig config = XpdResourcesPlugin.getDefault()
                                .getProjectConfig(urlProject);

                        // Config may be null if not XPD project.
                        if (config != null) {
                            SpecialFolder sf = config.getSpecialFolders()
                                    .getFolderContainer(resourceFromURL);
                            if (sf != null && OM_SPECIAL_FOLDER_KIND
                                    .equals(sf.getKind())) {
                                // Also check that SF relative path is the
                                // same.
                                IPath specialFolderRelativePath =
                                        SpecialFolderUtil
                                                .getSpecialFolderRelativePath(
                                                        resourceFromURL,
                                                        OM_SPECIAL_FOLDER_KIND);
                                if (sfRelativeLoc != null && sfRelativeLoc
                                        .equals(specialFolderRelativePath
                                                .toPortableString())) {
                                    TransactionalEditingDomain ed =
                                            XpdResourcesPlugin.getDefault()
                                                    .getEditingDomain();
                                    if (resourceFromURL != null) {
                                        // load resource
                                        Resource resource = ed.getResourceSet()
                                                .getResource(URI
                                                        .createPlatformResourceURI(
                                                                resourceFromURL
                                                                        .getFullPath()
                                                                        .toPortableString(),
                                                                true),
                                                        true);
                                        if (resource != null) {
                                            return resource.getEObject(
                                                    orgModelEntityId);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * Gives one of workspace resources linked with provided URI.
     * 
     * @param uri
     *            the URI of the workspace resource.
     * @return the one of the IResource associated with the URI. If URI does not
     *         point to valid resource location or if the resource does not
     *         exist the <code>null</null> value will be returned.
     */
    private static IResource getIResourceFromURL(String uri) {
        URI fileURI = URI.createURI(uri);
        String platformString = fileURI.toPlatformString(true);
        return ResourcesPlugin.getWorkspace().getRoot()
                .findMember(platformString);
    }

    public static int parseInt(String valueString, int defaultValue) {
        try {
            return Integer.parseInt(valueString);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Returns the map of default save options for XML files used by EMF
     * resources serialization
     * 
     * @return the map of default save options for XML files used by EMF
     *         resources serialization.
     * @deprecated Use {@link N2Utils#getDefaultXMLSaveOptions()} instead.
     */
    @Deprecated
    public static Map<Object, Object> getDefaultXMLSaveOptions() {
        return N2Utils.getDefaultXMLSaveOptions();
    }

    /**
     * Obtains special folder of specified kind and name positioned on project
     * level. If folder doesn't exist it will be created and if it is not a
     * special folder of specified type it will be marked as such.
     * 
     * @param project
     *            the project.
     * @param folderName
     *            the name of the folder to be obtained.
     * @param kind
     *            the special folder kind.
     * @return the IFolder representing special folder of provided type.
     */
    public static IFolder getSpecialFolder(IProject project, String folderName,
            String kind) {
        SpecialFolder sf = SpecialFolderUtil
                .getCreateSpecialFolderOfKind(project, kind, folderName);
        return sf != null ? sf.getFolder() : null;
    }

    @SuppressWarnings("unchecked")
    public static Collection<Activity> getN2ManualActivities(
            Collection<Package> xpdlPackages) {

        // Prune all processes without global N2 destination environment
        // enabled also page flows.
        PruneHandler pruneHandler = new PruneHandler() {
            /** {@inheritDoc} */
            @Override
            public boolean shouldPrune(EObject object) {
                if (object instanceof com.tibco.xpd.xpdl2.Process) {
                    com.tibco.xpd.xpdl2.Process process =
                            (com.tibco.xpd.xpdl2.Process) object;
                    if (!GlobalDestinationHelper.isGlobalDestinationEnabled(
                            process,
                            N2Utils.N2_GLOBAL_DESTINATION_ID)) {
                        return true;
                    }
                    if (Xpdl2ModelUtil.isPageflow(process)) {
                        return true;
                    }
                }
                return false;
            }
        };
        IQueryResult result = new SELECT(new FROM(xpdlPackages),
                new WHERE(new IsUserTaskActivity(pruneHandler))).execute();
        Exception e = result.getException();
        if (e != null) {
            throw new RuntimeException(e);
        }
        return (Collection<Activity>) result.getEObjects();
    }

    @SuppressWarnings("unchecked")
    public static Collection<Activity> getN2PageFlowManualActivities(
            Collection<Package> xpdlPackages) {

        // Prune all processes without global N2 destination environment
        // enabled also page flows.
        PruneHandler pruneHandler = new PruneHandler() {
            /** {@inheritDoc} */
            @Override
            public boolean shouldPrune(EObject object) {
                if (object instanceof com.tibco.xpd.xpdl2.Process) {
                    com.tibco.xpd.xpdl2.Process process =
                            (com.tibco.xpd.xpdl2.Process) object;
                    if (!GlobalDestinationHelper.isGlobalDestinationEnabled(
                            process,
                            N2Utils.N2_GLOBAL_DESTINATION_ID)) {
                        return true;
                    }
                    if (!Xpdl2ModelUtil.isPageflow(process)) {
                        return true;
                    }
                }
                return false;
            }
        };
        IQueryResult result = new SELECT(new FROM(xpdlPackages),
                new WHERE(new IsUserTaskActivity(pruneHandler))).execute();
        Exception e = result.getException();
        if (e != null) {
            throw new RuntimeException(e);
        }
        return (Collection<Activity>) result.getEObjects();
    }

    /**
     * Condition for selection of manual task activities.
     */
    public static class IsUserTaskActivity extends EObjectCondition {
        public IsUserTaskActivity() {
            super();
        }

        public IsUserTaskActivity(PruneHandler pruneHandler) {
            super(pruneHandler);
        }

        @Override
        public boolean isSatisfied(EObject object) {
            if (object instanceof Activity) {
                Activity activity = (Activity) object;
                if (activity.getImplementation() instanceof Task
                        && ((Task) activity.getImplementation())
                                .getTaskUser() != null) {
                    return true;
                }
            }
            return false;
        }

    }

    /**
     * Condition for selection of manual task activities.
     */
    public static class IsSubFlowActivity extends EObjectCondition {
        public IsSubFlowActivity() {
            super();
        }

        public IsSubFlowActivity(PruneHandler pruneHandler) {
            super(pruneHandler);
        }

        @Override
        public boolean isSatisfied(EObject object) {
            if (object instanceof Activity) {
                Activity activity = (Activity) object;
                if (activity.getImplementation() instanceof SubFlow) {
                    return true;
                }
            }
            return false;
        }
    }

    public static Collection<Package> getN2ProcessPackages(IProject project) {
        Collection<Package> packages = new ArrayList<Package>();
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null) {
            EList<IFolder> packageFolders =
                    config.getSpecialFolders().getEclipseIFoldersOfKind(
                            Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
            try {
                SpecialFolderVisitor specialFolderVisitor =
                        new SpecialFolderVisitor(packages);
                for (IFolder folder : packageFolders) {
                    folder.accept(specialFolderVisitor);
                }
            } catch (CoreException e) {
                LOG.error(e);
            }
        }

        return packages;
    }

    private static class SpecialFolderVisitor implements IResourceVisitor {
        private final Collection<Package> packages;

        /**
         * Constructor takes the reference to the packages to which packages
         * will be added.
         */
        public SpecialFolderVisitor(Collection<Package> packages) {
            this.packages = packages;
        }

        /** {@inheritDoc} */
        public boolean visit(IResource resource) throws CoreException {
            if (resource.getType() == IResource.FILE) {
                IFile file = (IFile) resource;
                String fileExtension = file.getFileExtension();
                if (fileExtension != null
                        && XPDL_EXTENSION.equals(fileExtension.toLowerCase())) {
                    WorkingCopy wc = XpdResourcesPlugin.getDefault()
                            .getWorkingCopy(resource);
                    if (wc instanceof Xpdl2WorkingCopyImpl
                            && !wc.isInvalidFile()
                            && !((Xpdl2WorkingCopyImpl) wc)
                                    .isInvalidVersion()) {
                        Package xpdlPackage = (Package) wc.getRootElement();
                        if (xpdlPackage != null) {
                            packages.add(xpdlPackage);
                        } else {
                            LOG.error(String.format(
                                    "Could not load XPDL WC for '%s'.", //$NON-NLS-1$
                                    file.getName()));
                        }
                    }
                }
                return false;
            }
            return true;
        }
    }

    /**
     * Add all user tasks found in given pageflow to the forTask list.
     * <p>
     * XPD-5343: This method should not recurs. Each project contains it's own
     * pageflows and forms resources and should not add stuff for pageflows in
     * other projects.
     * 
     * @param pageFlow
     *            the context page flow process.
     */
    @SuppressWarnings("unchecked")
    private static void getFormTasksFromPageFlow(Process pageFlow,
            LinkedHashSet<Activity> formTasks) {
        if (Xpdl2ModelUtil.isPageflow(pageFlow)) {
            IQueryResult formTasksResult = new SELECT(new FROM(pageFlow),
                    new WHERE(new BRMUtils.IsUserTaskActivity())).execute();
            if (formTasksResult.getException() != null) {
                throw new RuntimeException(formTasksResult.getException());
            }
            Collection<Activity> pfFormTasks =
                    (Collection<Activity>) formTasksResult.getEObjects();
            formTasks.addAll(pfFormTasks);

        }

        return;
    }

    /**
     * Gets manual tasks from PageFlow.
     * 
     * @param pageFlow
     *            the context page flow process.
     * @return collection of manual tasks activities.
     */
    @SuppressWarnings("unchecked")
    public static Collection<Activity> getManualPageFlowActivities(
            Process pageFlow) {
        HashSet<Process> visitedPageflows = new HashSet<Process>();
        LinkedHashSet<Activity> formTasks = new LinkedHashSet<Activity>();
        getFormTasksFromPageFlow(pageFlow, formTasks);
        return formTasks;
    }

    private static final String WP = "wp";

    private static final String BRM = "brm";

    private static final String EC = "ec";

    private static final String KEY_SEPARATOR = "::"; //$NON-NLS-1$

    private static final String VER_KEY_FORMAT = "%s" + KEY_SEPARATOR + "%s"; //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * @param project
     * @return Map of version numbers for OM within a project's scope. OMs keyed
     *         using the resource and OM name
     */
    public static Map<String, String> getReferencedOMVersions(
            IProject project) {

        Map<String, String> ret = new HashMap<String, String>();
        ret.putAll(getProjectsOMVersions(project));

        try {
            Set<IProject> referencedProjects =
                    ProjectUtil2.getReferencedProjectsHierarchy(project, false);

            for (IProject refProject : referencedProjects) {
                ret.putAll(getProjectsOMVersions(refProject));
            }
        } catch (CyclicDependencyException e) {
            String msg = String.format(
                    "Project cyclic dependency detected whilst attempt to determine version numbers for OMs referenced by %s:\n\t %s", //$NON-NLS-1$
                    project.getName(),
                    e.getMessage());
            BRMActivator.getDefault().getLogger().error(msg);
        }

        return ret;
    }

    /**
     * @param ret
     * @param project
     */
    private static Map<String, String> getProjectsOMVersions(IProject project) {

        // determine whether given referenced project contains OM files
        List<IResource> omResources = SpecialFolderUtil
                .getAllDeepResourcesInSpecialFolderOfKind(project,
                        OMResourcesActivator.OM_SPECIAL_FOLDER_KIND,
                        OMResourcesActivator.OM_FILE_EXTENSION,
                        false);

        Map<String, String> ret = new HashMap<String, String>();

        for (IResource omResource : omResources) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(omResource);
            if ((wc instanceof OMWorkingCopy) && (!wc.isInvalidFile())) {

                BaseOrgModel rootElement = (BaseOrgModel) wc.getRootElement();
                String key = createOmVerKey(omResource.getName(),
                        rootElement.getName());
                String omFileVersion = rootElement.getVersion();
                ret.put(key, omFileVersion);
            }
        }

        return ret;
    }

    /**
     * @param project
     *            with references to OMs
     * @return common OM major version number; -1 if no OMs are referenced; null
     *         if a common major version could not be determined
     */
    public static Integer getReferencedOMMajorVersion(IProject project) {

        int majorVer = -1;

        // retrieve a set of the OM versions
        Set<String> versions =
                new HashSet<String>(getReferencedOMVersions(project).values());

        for (String version : versions) {

            Integer currentMajorVer;
            try {
                currentMajorVer = new Version(version).getMajor();
            } catch (IllegalArgumentException e) {
                String msg = String.format(
                        "Major version number (%s) for project '%s' could not be determined", //$NON-NLS-1$
                        project.getName(),
                        version);
                BRMActivator.getDefault().getLogger().error(msg);
                return null; // fail
            }

            if (majorVer == -1) {
                majorVer = currentMajorVer;
            } else if (majorVer != currentMajorVer) {
                return null; // fail
            }
        }

        return majorVer;
    }

    private static void getOMDependencyList(IFile xpdlFile,
            Set<IResource> omFiles) {
        WorkingCopy xpdlWC = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        List<IResource> dependencyList = xpdlWC.getDependency();
        if (dependencyList != null && !dependencyList.isEmpty()) {
            for (IResource dependencyResource : dependencyList) {
                WorkingCopy wc =
                        WorkingCopyUtil.getWorkingCopy(dependencyResource);
                if ((wc instanceof OMWorkingCopy) && (!wc.isInvalidFile())) {
                    omFiles.add(dependencyResource);
                }

            }
        }
    }

    private static String getOMFileVersion(IResource omResource) {
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(omResource);
        BaseOrgModel rootElement = (BaseOrgModel) workingCopy.getRootElement();
        String omFileVersion = rootElement.getVersion();
        int lastIndexOf = omFileVersion.lastIndexOf(".");
        if (lastIndexOf != -1) {
            omFileVersion = omFileVersion.substring(0, lastIndexOf);
        }
        return omFileVersion;
    }

    public static List<Process> getPageflowOrBusinessServiceList(String path) {
        List<Process> pageflows = new ArrayList<Process>();
        IFile xpdlFile = ResourcesPlugin.getWorkspace().getRoot()
                .getFile(new Path(path));
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        if (wc != null) {
            EObject root = wc.getRootElement();
            if (root instanceof Package) {
                for (Process process : ((Package) root).getProcesses()) {
                    if (Xpdl2ModelUtil.isPageflow(process)
                            && GlobalDestinationHelper
                                    .isGlobalDestinationEnabled(process,
                                            N2Utils.N2_GLOBAL_DESTINATION_ID)) {
                        pageflows.add(process);
                    }
                }
            }
        }
        return pageflows;
    }

    /**
     * Obtains the business services from a collection of packages.
     * 
     * @param xpdlPackages
     *            the collection of packages.
     * @return the collection of business services for the provided packages.
     */
    public static Collection<Process> getBusinessServices(
            Collection<Package> xpdlPackages) {
        List<Process> businessServices = new ArrayList<Process>();
        for (Package xpdlPackage : xpdlPackages) {
            for (Process xpdlProcess : xpdlPackage.getProcesses()) {
                if (isBusinessService(xpdlProcess)) {
                    businessServices.add(xpdlProcess);
                }
            }
        }
        return businessServices;
    }

    /**
     * Obtains the case services from a collection of packages.
     * 
     * @param xpdlPackages
     *            the collection of packages.
     * @return the collection of case services for the provided packages.
     */
    public static Collection<Process> getCaseServices(
            Collection<Package> xpdlPackages) {

        List<Process> caseServices = new ArrayList<Process>();
        for (Package xpdlPackage : xpdlPackages) {

            for (Process xpdlProcess : xpdlPackage.getProcesses()) {

                if (isCaseService(xpdlProcess)) {

                    caseServices.add(xpdlProcess);
                }
            }
        }
        return caseServices;
    }

    /**
     * Check if the process is a Case Service.
     * 
     * @param xpdlProcess
     *            the process to check.
     * @return true if process is a case service.
     */
    private static boolean isCaseService(Process xpdlProcess) {

        return Xpdl2ModelUtil.isCaseService(xpdlProcess)
                && GlobalDestinationHelper.isGlobalDestinationEnabled(
                        xpdlProcess,
                        N2Utils.N2_GLOBAL_DESTINATION_ID);
    }

    /**
     * Obtains the standard page flows (not marked as a business services) from
     * a collection of packages.
     * 
     * @param xpdlPackages
     *            the collection of packages.
     * @return the collection of standard page flows (not marked as a business
     *         services) for the provided packages.
     */
    public static Collection<Process> getStandardPageFlows(
            Collection<Package> xpdlPackages) {
        List<Process> standardPageFlows = new ArrayList<Process>();
        for (Package xpdlPackage : xpdlPackages) {
            for (Process xpdlProcess : xpdlPackage.getProcesses()) {
                if (isStandardPageFlow(xpdlProcess)) {
                    standardPageFlows.add(xpdlProcess);
                }
            }
        }
        return standardPageFlows;
    }

    /**
     * Check if the process is a business service.
     * 
     * @param xpdlProcess
     *            the process to check.
     * @return true if process is a business service.
     */
    private static boolean isBusinessService(Process xpdlProcess) {

        return Xpdl2ModelUtil.isPageflowBusinessService(xpdlProcess)
                && !Xpdl2ModelUtil.isCaseService(xpdlProcess)
                && GlobalDestinationHelper.isGlobalDestinationEnabled(
                        xpdlProcess,
                        N2Utils.N2_GLOBAL_DESTINATION_ID);
    }

    /**
     * Check if the process is a business service.
     * 
     * @param xpdlProcess
     *            the process to check.
     * @return true if process is a business service.
     */
    private static boolean isStandardPageFlow(Process xpdlProcess) {
        return Xpdl2ModelUtil.isPageflow(xpdlProcess)
                && !Xpdl2ModelUtil.isPageflowBusinessService(xpdlProcess)
                && GlobalDestinationHelper.isGlobalDestinationEnabled(
                        xpdlProcess,
                        N2Utils.N2_GLOBAL_DESTINATION_ID);
    }

    /**
     * @param key
     * @return Two element array consisting of name of OM resource and name of
     *         OM
     */
    public static String[] parseOmVerMapKey(String key) {
        String[] tokens = key.split(VER_KEY_FORMAT);
        if (tokens.length != 2) {
            return null;
        }
        return tokens;
    }

    /**
     * @param resourceName
     *            name of OM resource
     * @param omName
     *            name of OM
     * @return key composed of name of OM resource and name of OM
     */
    private static String createOmVerKey(String resourceName, String omName) {
        return String.format(VER_KEY_FORMAT, resourceName, omName);
    }

}

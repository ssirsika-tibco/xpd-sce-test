/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.wsdlgen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.wst.wsdl.Definition;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.gen.BOMGenActivator;
import com.tibco.xpd.bom.gen.ui.Activator;
import com.tibco.xpd.bom.gen.ui.preferences.BomGenPreferenceConstants;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.AbstractValidatingIncrementalProjectBuilder;
import com.tibco.xpd.validation.utils.ValidationProblemUtil;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.wsdlgen.transform.XtendTransformerXpdl2Wsdl;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

/**
 * 
 * Builder that transforms XPDL files to WSDL files. This works on changes on
 * both the XPDL and the BOM.
 * 
 * @author rsomayaj
 * 
 * 
 */
public class WsdlGenBuilderTransformer extends
        AbstractValidatingIncrementalProjectBuilder {

    /**
     * Value added to the generated attribute of the generated services special
     * folder.
     */
    public static final String GENERATED_SERVICE_FOLDER_TYPE = "bpm.xpdl2wsdl"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String WSDL_EXISTS_MARKER_ID_VALUE =
            "com.tibco.xpd.wsdl.wsdlexists";//$NON-NLS-1$

    /**
     * 
     */
    private static final String MARKER_ID = "id";//$NON-NLS-1$

    /**
     * 
     */
    private static final String WSDL_FILE_EXTN =
            WsdlUIPlugin.WSDL_FILE_EXTENSION;

    private static final String DEFAULT_XPDL_FILE_EXTENSION =
            Xpdl2ResourcesConsts.XPDL_EXTENSION;

    private static final String DEFAULT_DFLOW_FILE_EXTENSION =
            Xpdl2ResourcesConsts.DFLOW_EXTENSION;

    private static final Logger LOG = WsdlgenPlugin.getDefault().getLogger();

    private static final String WSDL_SPECIAL_FOLDER_KIND =
            WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND;

    private static final String DEFAULT_BOM_FILE_EXTENSION =
            BOMResourcesPlugin.BOM_FILE_EXTENSION;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.AbstractValidatingIncrementalProjectBuilder#
     * doBuild(int, java.util.Map, org.eclipse.core.runtime.IProgressMonitor,
     * java.util.List)
     * 
     * XPD-391
     */
    @Override
    protected IProject[] doBuild(int kind, Map args, IProgressMonitor monitor,
            Set<IResource> filesToValidate) throws CoreException {
        TRACE("Build started: " + getProject()); //$NON-NLS-1$
        IResourceDelta delta = getDelta(getProject());

        Set<IProject> referencedProjects;
        try {
            referencedProjects =
                    ProjectUtil2.getReferencedProjectsHierarchies(Collections
                            .singleton(getProject()), true);
        } catch (CyclicDependencyException e1) {
            LOG.error(e1,
                    String.format("Wsdl Generator Builder: Detected cyclic dependency in referenced projects of project '%s'.", //$NON-NLS-1$
                            getProject().getName()));
            return null;
        }

        monitor.beginTask(Messages.WsdlGenBuilderTransformer_builder_monitor_shortdesc,
                IProgressMonitor.UNKNOWN);

        /*
         * If the derived services folder is not marked as derived then do so
         * here. This will make sure that this folder is always marked derived
         * and hence will be ignore by export and svn check-in
         */
        IFolder servicesFolder = getGeneratedServicesFolder(getProject());
        if (servicesFolder != null && !servicesFolder.isDerived()) {
            try {
                servicesFolder.setDerived(true, null);
            } catch (CoreException e) {
                LOG.error(e,
                        String.format("Failed to mark '%s' folder as derived", //$NON-NLS-1$
                                servicesFolder.getName()));
            }
        }

        // A full build is required when delta is NULL(i.e. when a Project Clean
        // is done) and when the Project Config file is changed(this would
        // include any deletion/addition of special folders along with the
        // project destination changes).
        if (delta == null || isConfigUpdated(delta)) {
            fullbuild(monitor, filesToValidate);
        } else {
            incrementalBuild(delta,
                    monitor,
                    filesToValidate,
                    referencedProjects);
        }

        TRACE("Build finished" + getProject()); //$NON-NLS-1$
        return referencedProjects.toArray(new IProject[referencedProjects
                .size()]);
    }

    /**
     * Check if the config file has been updated. This can happen when special
     * folders have changed/created/removed. It will also check if the config
     * file has been deleted.
     * 
     * @param delta
     *            changed resource delta.
     * @return <code>true</code> if the config file is an affected child of the
     *         resource delta, <code>false</code> otherwise.
     */
    private boolean isConfigUpdated(IResourceDelta delta) {
        boolean updated = false;

        if (delta != null) {
            IResourceDelta[] children = delta.getAffectedChildren();

            for (IResourceDelta child : children) {
                if (child.getResource().getProjectRelativePath().toString()
                        .equals(XpdResourcesPlugin.PROJECTCONFIGFILE)) {
                    // Only return true if contents of the file have changed or
                    // the file has been removed
                    updated =
                            child.getKind() == IResourceDelta.REMOVED
                                    || (child.getKind() == IResourceDelta.CHANGED && (child
                                            .getFlags() & IResourceDelta.CONTENT) != 0);
                    break;
                }
            }
        }

        return updated;
    }

    /**
     * Run a full build.
     * 
     * @param monitor
     * @param filesToValidate
     */
    private void fullbuild(final IProgressMonitor monitor,
            final Set<IResource> filesToValidate) {
        final Set<IFile> xpdlFiles = new HashSet<IFile>();

        for (IFolder packageFolder : getPackagesFolders(getProject())) {
            try {
                packageFolder.accept(new IResourceVisitor() {
                    @Override
                    public boolean visit(IResource resource)
                            throws CoreException {
                        if (resource instanceof IFile
                                && isXpdl((IFile) resource)) {
                            xpdlFiles.add((IFile) resource);
                        }
                        checkCancel(monitor, true);
                        return true;
                    }

                });
            } catch (CoreException e) {
                LOG.error(e);
                throw new RuntimeException(e);
            }
        }

        for (IFolder packageFolder : getDflowFolder(getProject())) {
            try {
                packageFolder.accept(new IResourceVisitor() {
                    @Override
                    public boolean visit(IResource resource)
                            throws CoreException {
                        if (resource instanceof IFile
                                && isDflow((IFile) resource)) {
                            xpdlFiles.add((IFile) resource);
                        }
                        checkCancel(monitor, true);
                        return true;
                    }

                });
            } catch (CoreException e) {
                LOG.error(e);
                throw new RuntimeException(e);
            }
        }

        if (!xpdlFiles.isEmpty()) {
            handleXpdlResourceChanged(monitor, xpdlFiles, filesToValidate);
        }

    }

    /**
     * This will run an incremental build.
     * 
     * @param delta
     * @param monitor
     * @param filesToValidate
     * @param referencedProjects
     */
    private void incrementalBuild(IResourceDelta delta,
            final IProgressMonitor monitor,
            final Set<IResource> filesToValidate,
            Set<IProject> referencedProjects) {

        final Set<IFile> changedOrAddedXPDLFiles = new HashSet<IFile>();
        final Set<IFile> removedXpdlFiles = new HashSet<IFile>();
        final Set<IFile> changedOrAddedBOMFiles = new HashSet<IFile>();
        final Set<IFile> removedBOMFiles = new HashSet<IFile>();

        /*
         * First process the delta of the project being built
         */
        try {
            delta.accept(new IResourceDeltaVisitor() {

                @Override
                public boolean visit(IResourceDelta delta) throws CoreException {
                    int kind = delta.getKind();
                    if (delta.getResource() instanceof IFile) {
                        IFile file = (IFile) delta.getResource();

                        if (kind == IResourceDelta.REMOVED) {
                            if (isBOM(file)) {
                                removedBOMFiles.add(file);
                            } else if (isXpdl(file) || isDflow(file)) {
                                removedXpdlFiles.add(file);
                            }
                            // handleResourceRemoved(monitor,
                            // delta.getResource(),
                            // filesToValidate);
                        } else if (kind == IResourceDelta.ADDED
                                || (kind == IResourceDelta.CHANGED && (delta
                                        .getFlags() & IResourceDelta.CONTENT) != 0)) {
                            if (isBOM(file)) {
                                changedOrAddedBOMFiles.add(file);
                            } else if (isXpdl(file) || isDflow(file)) {
                                changedOrAddedXPDLFiles.add(file);
                            }
                        }
                    }
                    checkCancel(monitor, true);
                    return true;
                }
            });

            /*
             * Now process all referenced projects - only interested in BOM
             * changes in referenced projects.
             */
            for (IProject refProject : referencedProjects) {
                IResourceDelta refDelta = getDelta(refProject);
                if (refDelta != null) {
                    refDelta.accept(new IResourceDeltaVisitor() {

                        @Override
                        public boolean visit(IResourceDelta delta)
                                throws CoreException {
                            int kind = delta.getKind();
                            if (delta.getResource() instanceof IFile) {
                                IFile file = (IFile) delta.getResource();

                                if (kind == IResourceDelta.REMOVED) {
                                    if (isBOM(file)) {
                                        removedBOMFiles.add(file);
                                    }
                                } else if (kind == IResourceDelta.ADDED
                                        || (kind == IResourceDelta.CHANGED && (delta
                                                .getFlags() & IResourceDelta.CONTENT) != 0)) {
                                    if (isBOM(file)) {
                                        changedOrAddedBOMFiles.add(file);
                                    }
                                }
                            }
                            checkCancel(monitor, true);
                            return true;
                        }
                    });
                }
            }

            handleXPDLResourcesRemoved(monitor,
                    removedXpdlFiles,
                    filesToValidate);
            handleBOMResourcesRemoved(monitor, removedBOMFiles, filesToValidate);

            handleXpdlResourceChanged(monitor,
                    changedOrAddedXPDLFiles,
                    filesToValidate);
            handleBOMResourceChanged(monitor,
                    changedOrAddedBOMFiles,
                    filesToValidate);
        } catch (CoreException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Handle the remove of XPDL file. This will delete the generated WSDL if
     * appropriate.
     * 
     * @param monitor
     * @param removedXpdlFiles
     * @param filesToValidate
     */
    private void handleXPDLResourcesRemoved(IProgressMonitor monitor,
            Set<IFile> removedXpdlFiles, final Set<IResource> filesToValidate) {
        for (IFile xpdlFile : removedXpdlFiles) {
            IFile wsdlFile = getWsdlFile(xpdlFile);
            if (wsdlFile != null && wsdlFile.exists()
                    && Xpdl2ModelUtil.isWsdlStudioGenerated(wsdlFile)) {
                try {
                    wsdlFile.delete(true, new NullProgressMonitor());
                } catch (CoreException e) {
                    LOG.error(e,
                            Messages.WsdlGenBuilderTransformer_UnableDeleteErrMsg_shortdesc);
                }
            }
        }
    }

    /**
     * Handle the remove of a BOM file. This will find any XPDLs that reference
     * these boms and rebuild them.
     * 
     * @param monitor
     * @param removedBOMFiles
     * @param filesToValidate
     */
    private void handleBOMResourcesRemoved(IProgressMonitor monitor,
            Set<IFile> removedBOMFiles, Set<IResource> filesToValidate) {
        for (IFile bomFile : removedBOMFiles) {
            Set<String> queryReferencingXpdls =
                    ProcessUIUtil.queryReferencingXpdls(getProject(),
                            bomFile.getName(),
                            true);
            Set<IFile> xpdlFiles = new HashSet<IFile>();
            for (String xpdlStr : queryReferencingXpdls) {
                IPath filePathRelativeToRoot =
                        getFilePathRelativeToRoot(xpdlStr);
                IFile xpdlFile =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(filePathRelativeToRoot);
                if (xpdlFile.isAccessible()
                        && getProject().equals(xpdlFile.getProject())) {
                    xpdlFiles.add(xpdlFile);
                }
            }
            handleXpdlResourceChanged(monitor, xpdlFiles, filesToValidate);
        }
    }

    /**
     * Handle a change to a XPDL file.
     * 
     * @param monitor
     * @param bomFiles
     * @param filesToValidate
     */
    private void handleXpdlResourceChanged(IProgressMonitor monitor,
            Set<IFile> xpdlFiles, Set<IResource> filesToValidate) {
        TRACE("==> handleXpdlResourceChanged"); //$NON-NLS-1$
        Set<IFile> wsdlFiles = new HashSet<IFile>();
        for (IFile xpdlFile : xpdlFiles) {
            WorkingCopy xpdlWC = getWorkingCopy(xpdlFile);
            if (xpdlWC != null) {
                if (isFileValid(xpdlFile)) {
                    TRACE("   >> transforming XPDL: " + xpdlFile.getFullPath()); //$NON-NLS-1$
                    // if (!ProcessDestinationUtil
                    // .isGenerateWsdlEnabledInDestinations(resource)) {
                    // return true;
                    // }

                    // The transform looks at each process/process interface
                    // destination to see if there is a need for a WSDL to
                    // be
                    // generated.
                    monitor.subTask(String
                            .format(Messages.WsdlGenBuilderTransformer_generatingWsdl_monitor_shortdesc,
                                    xpdlFile.getFullPath().toString()));
                    IFile wsdlFile =
                            transformXpdlFile(xpdlFile, null, filesToValidate);
                    if (wsdlFile != null) {
                        wsdlFiles.add(wsdlFile);
                    }
                } else {
                    TRACE("   >> ignoring XPDL as it has errors: " //$NON-NLS-1$
                            + xpdlFile.getFullPath());
                }
            }
        }

        // Validate the generated wsdl files
        for (IFile wsdlFile : wsdlFiles) {
            runWsdlWSTValidations(wsdlFile);
        }

        TRACE("<== handleXpdlResourceChanged"); //$NON-NLS-1$
    }

    /**
     * Handle a change to a BOM file. This will find all XPDLs (in the project
     * being built) that are affected by this change and will (re-)build the
     * wsdls for them.
     * 
     * @param monitor
     * @param bomFiles
     * @param filesToValidate
     */
    private void handleBOMResourceChanged(IProgressMonitor monitor,
            Set<IFile> bomFiles, Set<IResource> filesToValidate) {
        TRACE("==> handleBOMResourceChanged"); //$NON-NLS-1$
        Set<IFile> affectedXpdls = new HashSet<IFile>();
        for (IFile bomFile : bomFiles) {
            TRACE("   >> changed BOM: " + bomFile.getFullPath()); //$NON-NLS-1$
            // Listen to changes to the BOM
            getAffectedXpdls(bomFile, affectedXpdls);
        }

        if (!affectedXpdls.isEmpty()) {
            handleXpdlResourceChanged(monitor, affectedXpdls, filesToValidate);
        }
        TRACE("<== handleResourceChanged"); //$NON-NLS-1$
    }

    /**
     * Get all xpdl files (from the project being built) that are affected by a
     * change to the given BOM file.
     * 
     * @param bomFile
     * @return
     */
    private void getAffectedXpdls(IFile bomFile, Set<IFile> affectedXpdls) {
        if (bomFile != null) {
            Set<IFile> bomFiles = new HashSet<IFile>();
            bomFiles.add(bomFile);
            // Find all files that depend on this bom (this will include
            // indirect dependents as well)
            bomFiles.addAll(getAffectedBOMs(bomFile, new HashSet<IFile>()));
            Set<String> xpdlsReferringBom = new HashSet<String>();

            for (IFile bom : bomFiles) {
                IPath relativePath =
                        SpecialFolderUtil.getSpecialFolderRelativePath(bom);
                xpdlsReferringBom.addAll(ProcessUIUtil
                        .queryReferencingXpdls(bomFile.getProject(),
                                relativePath.toString(),
                                false));
            }

            for (String xpdlUriStr : xpdlsReferringBom) {
                IPath removeResourceSegment =
                        getFilePathRelativeToRoot(xpdlUriStr);
                IFile xpdlFile =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(removeResourceSegment);
                if (xpdlFile.exists()
                        && (isXpdl(xpdlFile) || isDflow(xpdlFile))
                        && getProject().equals(xpdlFile.getProject())) {

                    /*
                     * Need to check whether the formal parameters of any
                     * process API actually reference the affected BOMs.
                     */
                    if (isAnyBomReferencedByAPIProcess(xpdlFile, bomFiles)) {
                        affectedXpdls.add(xpdlFile);
                    }
                }
            }
        }
    }

    /**
     * Check if any of the bom files provided are referenced from the formal
     * parameters of API processes in the given xpdl file.
     * 
     * @param xpdlFile
     * @param bomFiles
     * @return
     */
    private boolean isAnyBomReferencedByAPIProcess(IFile xpdlFile,
            Set<IFile> bomFiles) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        Set<FormalParameter> parameters = new HashSet<FormalParameter>();

        if (wc != null && wc.getRootElement() instanceof Package) {
            Package pkg = (Package) wc.getRootElement();
            for (Process process : pkg.getProcesses()) {
                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);

                for (Activity activity : allActivitiesInProc) {
                    /*
                     * Message API activities only.
                     */
                    if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {

                        Collection<ActivityInterfaceData> activityInterfaceData =
                                ActivityInterfaceDataUtil
                                        .getActivityInterfaceData(activity);
                        for (ActivityInterfaceData data : activityInterfaceData) {
                            ProcessRelevantData prData = data.getData();
                            if (prData instanceof FormalParameter) {
                                parameters.add((FormalParameter) prData);
                            }
                        }
                    }
                }
            }

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pkg);
            if (processInterfaces != null) {
                for (ProcessInterface pInterface : processInterfaces
                        .getProcessInterface()) {

                    for (StartMethod method : pInterface.getStartMethods()) {
                        /*
                         * All start methods generate operations (for some
                         * reason!)
                         */
                        Collection<ActivityInterfaceData> interfaceData =
                                ActivityInterfaceDataUtil
                                        .getInterfaceEventInterfaceData(method);

                        for (ActivityInterfaceData data : interfaceData) {
                            ProcessRelevantData prData = data.getData();
                            if (prData instanceof FormalParameter) {
                                parameters.add((FormalParameter) prData);
                            }
                        }
                    }

                    for (IntermediateMethod method : pInterface
                            .getIntermediateMethods()) {
                        /*
                         * Only message intermediate methods generate operations
                         */
                        if (TriggerType.MESSAGE_LITERAL.equals(method
                                .getTrigger())) {
                            Collection<ActivityInterfaceData> interfaceData =
                                    ActivityInterfaceDataUtil
                                            .getInterfaceEventInterfaceData(method);

                            for (ActivityInterfaceData data : interfaceData) {
                                ProcessRelevantData prData = data.getData();
                                if (prData instanceof FormalParameter) {
                                    parameters.add((FormalParameter) prData);
                                }
                            }
                        }
                    }
                }
            }

            for (FormalParameter param : parameters) {
                if (doesFormalParameterReferBom(param, bomFiles)) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Check if the given formal parameter references any BOM in the given set.
     * 
     * @param param
     * @param bomFiles
     * @return
     */
    private boolean doesFormalParameterReferBom(FormalParameter param,
            Set<IFile> bomFiles) {
        Object baseType =
                BasicTypeConverterFactory.INSTANCE.getBaseType(param, false);
        if (baseType instanceof EObject && !(baseType instanceof BasicTypeType)) {
            IFile file = WorkingCopyUtil.getFile((EObject) baseType);
            if (file != null && bomFiles.contains(file)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all the BOMs that are affected by a change to the given BOM
     * (including indirect dependents).
     * 
     * @param bomFile
     *            file to get dependents of
     * @param alreadyProcessedBOMs
     *            temporary cache to register all processed boms (to avoid
     *            cyclic processing)
     * @return
     */
    private Set<IFile> getAffectedBOMs(IFile bomFile,
            Set<IFile> alreadyProcessedBOMs) {
        Set<IFile> dependents = new HashSet<IFile>();

        if (bomFile != null && !alreadyProcessedBOMs.contains(bomFile)) {
            alreadyProcessedBOMs.add(bomFile);

            for (IResource res : WorkingCopyUtil.getAffectedResources(bomFile)) {
                if (res instanceof IFile && isBOM((IFile) res)) {
                    dependents.add((IFile) res);
                    dependents.addAll(getAffectedBOMs((IFile) res,
                            alreadyProcessedBOMs));
                }
            }
        }

        return dependents;
    }

    /**
     * @param xpdlUriStr
     * @return
     */
    private IPath getFilePathRelativeToRoot(String xpdlUriStr) {
        String replacedUriStr = xpdlUriStr.replace("%20", " "); //$NON-NLS-1$ //$NON-NLS-2$
        IPath pathWithoutPlatform = new Path(replacedUriStr).setDevice(null);
        IPath removeResourceSegment =
                pathWithoutPlatform.removeFirstSegments(1);
        return removeResourceSegment;
    }

    /**
     * Gets the WSDL file for the XPDL File and validates that
     * 
     * @param xpdlWC
     * @param wsdlFile
     */
    private void runWsdlWSTValidations(IFile wsdlFile) {
        if (wsdlFile != null && Xpdl2ModelUtil.isWsdlStudioGenerated(wsdlFile)) {
            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(wsdlFile);
            if (workingCopy != null) {
                workingCopy.getRootElement();
            }

            IPreferenceStore preferenceStore =
                    Activator.getDefault().getPreferenceStore();
            boolean wsdlValidationEnabled =
                    preferenceStore
                            .getBoolean(BomGenPreferenceConstants.P_ENABLE_WSDL_VALIDATION);

            if (wsdlValidationEnabled) {
                WSDLTransformUtil.validateWSDL(wsdlFile);
            }
        }
    }

    /**
     * @param xpdlFile
     * @param filesToValidate
     * @return generated wsdl file.
     * 
     */
    private IFile transformXpdlFile(IFile xpdlFile, IFile bomFile,
            Set<IResource> filesToValidate) {
        removeWsdlExistsFileMarkers(xpdlFile);

        if (isWsdlRequired(xpdlFile)) {
            /*
             * Sid XPD-2878 - We no longer create a new WSDL file (or update
             * existing one) with empty Definition element. This was causing all
             * sorts of problems.
             * 
             * Prior to XPD-2878 we used to recreate the generated WSDL as blank
             * before transforming it. This was done by replacing the current
             * Definition element in the WSDL's emf Resource with a new one and
             * saving the WSDL file BEHIND THE WSDL working copy's back.
             * 
             * This meant that the original Definition element became orphaned
             * from the emf resource set (but working copy did not know this).
             * Therefore when working copy was requested to reload and it tried
             * to do a cleanup() this WOULD NOT UNLOAD the resource because
             * old-Defintion.eResource() would return null because the
             * definition had been orphaned from resource set.
             * 
             * Once you get into this kind of situation then VERY often, when
             * attempting to reload the new WSDL definition into resource set,
             * EMF would say "I've already got it so no need to do anything" -
             * however, (timing dependent) the one it already has could be the
             * empty one that used to be created here by createWSDLFile().
             * 
             * So now we allow the xpdl2WsdlTransformer to transform into a new
             * wsdl Definition an in memory emf resource in a separate editing
             * domain an - then when it does a save on that resource, the
             * original working copy will get an event and reload the working
             * copy via the usual channels.
             */
            IFile wsdlFile = getWsdlFileHandle(xpdlFile);

            if (wsdlFile != null
                    && createTargetFolder(xpdlFile.getProject(), wsdlFile)) {
                try {
                    XtendTransformerXpdl2Wsdl xpdl2WsdlTransformer =
                            new XtendTransformerXpdl2Wsdl();

                    xpdl2WsdlTransformer.transformXpdlToWsdl(xpdlFile,
                            wsdlFile,
                            bomFile);

                    /*
                     * XPD-2910 - re-load of working copy is done in the
                     * transformer now.
                     */

                    if (wsdlFile.exists()) {
                        /*
                         * XPD-391: Make sure the WSDL and it's dependent xpdl
                         * is revalidated.
                         * 
                         * Don't need to add the xpdl explicitly cos it should
                         * have a validation dependency on the wsdl file anyway.
                         */
                        filesToValidate.add(wsdlFile);

                        /**
                         * XPD-1095: instead of adding a single xpdl file to
                         * validate, we are now adding all the xpdls that are
                         * dependent on the wsdl
                         */
                        Collection<IResource> resources =
                                WorkingCopyUtil.getAffectedResources(wsdlFile);
                        filesToValidate.addAll(resources);
                    }

                    return wsdlFile;

                } catch (Exception e) {
                    LOG.error(e,
                            Messages.WsdlGenBuilderTransformer_UnableTransformErrMsg_shortdesc);
                }
            }

        } else {
            /*
             * As a WSDL file is not being generated then remove the existing
             * file, if any.
             */
            IFile wsdlFile = getWsdlFile(xpdlFile);
            if (wsdlFile != null && wsdlFile.exists()) {
                try {
                    wsdlFile.delete(true, null);
                } catch (CoreException e) {
                    LOG.error(e,
                            String.format(Messages.WsdlGenBuilderTransformer_unableToDeleteWsdl_error_message,
                                    wsdlFile.getFullPath().toString()));
                }
            }
        }
        return null;
    }

    private WorkingCopy getWorkingCopy(IResource resource) {
        return XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
    }

    private static boolean isXpdl(IFile file) {
        if (DEFAULT_XPDL_FILE_EXTENSION.equalsIgnoreCase(file
                .getFileExtension())) {
            return true;
        }
        return false;
    }

    private static boolean isDflow(IFile file) {
        if (DEFAULT_DFLOW_FILE_EXTENSION.equalsIgnoreCase(file
                .getFileExtension())) {
            return true;
        }
        return false;
    }

    /**
     * @param resource
     * @return
     */
    private boolean isBOM(IFile resource) {
        if (DEFAULT_BOM_FILE_EXTENSION.equalsIgnoreCase(resource
                .getFileExtension())) {
            return true;
        }
        return false;
    }

    private static boolean isPortTypeRequiredForProcIfc(ProcessInterface procIfc) {
        Boolean shouldGen =
                ProcessDestinationUtil
                        .shouldGenerateWSDLForProcessInterfaceDestinations(procIfc);
        if (!shouldGen) {
            return false;
        }

        /*
         * Check if the "EnableWsdlGenForProcInterface" extended attribute is
         * set on the Package containing this process interface. This indicates
         * that port types should be generated for all types of Start and
         * Intermediate methods.
         */
        boolean isWsdlGenExtAttrSet =
                isWsdlGenEnabledForProcessInterface(procIfc);

        List<StartMethod> startMethods = procIfc.getStartMethods();

        // If creating port type for any start method then return true if there
        // are any start methods
        if (isWsdlGenExtAttrSet && !startMethods.isEmpty()) {
            return true;
        }

        // Check if message type start methods are present
        for (StartMethod startMethod : startMethods) {
            if (TriggerType.MESSAGE_LITERAL.equals(startMethod.getTrigger())) {
                return true;
            }
        }

        List<IntermediateMethod> intermediateMethods =
                procIfc.getIntermediateMethods();

        // If creating port type for any intermediate method then return true if
        // there are any intermediate methods
        if (isWsdlGenExtAttrSet && !intermediateMethods.isEmpty()) {
            return true;
        }

        // Check if message type intermediate methods are present
        for (IntermediateMethod intermediateMethod : intermediateMethods) {
            if (TriggerType.MESSAGE_LITERAL.equals(intermediateMethod
                    .getTrigger())) {
                return true;
            }
        }
        return false;

    }

    /**
     * Having passed and an xpdl file, this utility returns a wsdl file.
     * 
     * @param xpdlFile
     * 
     * @return The autogenerated WSDL file for the given xpdl file.
     */
    public static IFile getWsdlFile(IFile xpdlFile) {
        IProject xpdlProject = xpdlFile.getProject();
        IFolder servicesFolder = getGeneratedServicesFolder(xpdlProject);

        if (servicesFolder != null) {
            IPath path =
                    SpecialFolderUtil.getSpecialFolderRelativePath(xpdlFile);
            if (path != null) {
                path =
                        path.removeFileExtension()
                                .addFileExtension(WSDL_FILE_EXTN);

                return servicesFolder.getFile(path.toString());
            }
        }
        return null;
    }

    /**
     * Having passed and an xpdl file, this utility returns a wsdl file. This
     * will return a value even if the parent folders of wsdl do not exist.
     * 
     * @param xpdlFile
     * 
     * @return The handle auto-generated WSDL file for the given xpdl file (it
     *         or it's parent folder may not exist). <code>null</code> is
     *         returned only if the special folder relative path for the
     *         xpdlFile cannot be resolved.
     */
    public static IFile getWsdlFileHandle(IFile xpdlFile) {
        IProject xpdlProject = xpdlFile.getProject();
        IFolder servicesFolder = getGeneratedServicesFolder(xpdlProject);

        // If the special folder hasn't been created yet then just get handle to
        // the folder
        if (servicesFolder == null) {
            servicesFolder =
                    xpdlProject
                            .getFolder(WsdlgenPlugin.GENERATED_SERVICES_FOLDER);
        }

        IPath path = SpecialFolderUtil.getSpecialFolderRelativePath(xpdlFile);
        if (path != null) {
            path = path.removeFileExtension().addFileExtension(WSDL_FILE_EXTN);

            return servicesFolder.getFile(path.toString());
        }
        return null;
    }

    /**
     * Having passed project for a wsdl file, this utility returns a xpdl file.
     * 
     * @param xpdlFile
     * 
     * @return The XPDL file for the given wsdl file.
     */
    public static List<IResource> getXpdlFiles(IProject project) {
        ArrayList<IResource> arrayList =
                SpecialFolderUtil.getResourcesInSpecialFolderOfKind(project,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                        DEFAULT_XPDL_FILE_EXTENSION);
        return arrayList;
    }

    /**
     * Create the target folder(s) for the generated WSDL file.
     * 
     * Sid XPD-2878 - We no longer create a new WSDL file (or update existing
     * one) with empty Definition element. This was causing all sorts of
     * problems.
     * 
     * Prior to XPD-2878 we used to recreate the generated WSDL as blank before
     * transforming it. This was done by replacing the current Definition
     * element in the WSDL's emf Resource with a new one and saving the WSDL
     * file BEHIND THE WSDL working copy's back.
     * 
     * This mean that the original Definition element became orphaned from the
     * emf resource set (but working copy did not know this). Therefore when
     * working copy was requested to reload and it tried to do a cleanup() this
     * WOULD NOT UNLOAD the resource because old-Defintion.eResource() would
     * return null because the definition had been orphaned from resource set.
     * 
     * Once you get into this kind of situation then VERY often, when attempting
     * to reload the new WSDL definition into resource set, EMF would say
     * "I've already got it so no need to do anything" - however, (timing
     * dependent) the one it already has could be the empty one that used to be
     * created here by createWSDLFile().
     * 
     * So now we allow the xpdl2WsdlTransformer to transform into a new wsdl
     * Definition an in memory emf resource in a separate editing domain an -
     * then when it does a save on that resource, the original working copy will
     * get an event and reload the working copy via the usual channels.
     * 
     * @param project
     * @param wsdlFile
     * 
     * @return <code>true</code> on success.
     */
    private boolean createTargetFolder(IProject project, IFile wsdlFile) {
        try {
            IFolder servicesFolder = getGeneratedServicesFolder(project);

            if (servicesFolder == null) {
                servicesFolder = createGeneratedServicesFolder(project);
            }

            createSubFolders(servicesFolder, wsdlFile);

            return true;
        } catch (CoreException e) {
            LOG.error(e,
                    String.format(Messages.WsdlGenBuilderTransformer_UnableToCreateFolderErr_shortdesc_2,
                            wsdlFile.getFullPath().toString()));
        }
        return false;
    }

    /**
     * Start a write transaction.
     * 
     * @param ed
     * @return
     * @throws InterruptedException
     */
    private Transaction startTransaction(TransactionalEditingDomain ed)
            throws InterruptedException {
        Transaction transaction = null;

        transaction =
                ((InternalTransactionalEditingDomain) ed)
                        .getActiveTransaction();

        if (transaction == null || transaction.isReadOnly()) {
            Map<String, Object> attrs = new HashMap<String, Object>();
            attrs.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);

            transaction =
                    ((InternalTransactionalEditingDomain) ed)
                            .startTransaction(false, attrs);
            return transaction;
        }

        return null;
    }

    /**
     * If the wsdlFile is contained in a subfolder in the services folder then
     * create them if not already present.
     * 
     * @param servicesFolder
     *            genererated services folder
     * @param wsdlFile
     *            wsdl file to create
     * @throws CoreException
     */
    private void createSubFolders(IFolder servicesFolder, IFile wsdlFile)
            throws CoreException {
        if (servicesFolder != null && wsdlFile != null
                && !servicesFolder.equals(wsdlFile.getParent())) {
            ProjectUtil.createFolder(wsdlFile.getParent(),
                    false,
                    new NullProgressMonitor());
        }
    }

    /**
     * @param file
     */
    private void removeWsdlExistsFileMarkers(IFile file) {
        try {
            IMarker marker =
                    findMarker(file, MARKER_ID, WSDL_EXISTS_MARKER_ID_VALUE);

            if (marker != null) {
                marker.delete();
            }

        } catch (CoreException e) {
            LOG.error(e, "Unable to remove file markers"); //$NON-NLS-1$
        }

        return;
    }

    /**
     * @param file
     * @return
     * @throws CoreException
     */
    private IMarker findMarker(IFile file, String attr,
            String expectedAttributeVal) throws CoreException {
        IMarker[] markers =
                file.findMarkers(null, true, IResource.DEPTH_INFINITE);
        IMarker wsdlInvalidFileMarker = null;
        for (IMarker marker : markers) {
            Object attributeVal = marker.getAttribute(attr);
            if (attributeVal != null
                    && expectedAttributeVal.equals(attributeVal)) {
                wsdlInvalidFileMarker = marker;
                break;
            }
        }
        return wsdlInvalidFileMarker;
    }

    /**
     * Create the Generated Services folder.
     * 
     * @param xpdlProject
     * @return the created folder
     * @throws CoreException
     * 
     */
    private IFolder createGeneratedServicesFolder(IProject xpdlProject)
            throws CoreException {
        IFolder servicesFolder =
                xpdlProject.getFolder(WsdlgenPlugin.GENERATED_SERVICES_FOLDER);
        if (!servicesFolder.exists()) {
            servicesFolder.create(true, true, new NullProgressMonitor());
        }
        servicesFolder.setDerived(true);
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(xpdlProject);

        if (projectConfig.getSpecialFolders().getFolder(servicesFolder,
                WSDL_SPECIAL_FOLDER_KIND) == null) {

            /*
             * Create a generated services special folder
             */
            projectConfig.getSpecialFolders().addFolder(servicesFolder,
                    WSDL_SPECIAL_FOLDER_KIND,
                    GENERATED_SERVICE_FOLDER_TYPE);

        }
        return servicesFolder;
    }

    /**
     * Get the generated services folder.
     * 
     * @param project
     * @return the folder if it exists, <code>null</code> otherwise.
     */
    private static IFolder getGeneratedServicesFolder(IProject project) {
        /*
         * Check if there is a generated special folder already created.
         */
        Collection<SpecialFolder> sFolders =
                SpecialFolderUtil.getGeneratedSpecialFoldersOfKind(project,
                        WSDL_SPECIAL_FOLDER_KIND,
                        GENERATED_SERVICE_FOLDER_TYPE);
        SpecialFolder sf =
                !sFolders.isEmpty() ? sFolders.iterator().next() : null;
        if (sf != null) {
            return sf.getFolder();
        }

        /*
         * No generated folder found, then maybe this is an older project and
         * does not have the generated attribute set, so check if there is a
         * "Generated Services" special folder present and mark it as a
         * generated folder now and return this folder.
         */
        IFolder folder =
                project.getFolder(WsdlgenPlugin.GENERATED_SERVICES_FOLDER);
        if (folder.exists()) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null) {
                sf = config.getSpecialFolders().getFolder(folder);
                if (sf != null) {
                    config.getSpecialFolders().markAsGenerated(sf,
                            GENERATED_SERVICE_FOLDER_TYPE);
                    return sf.getFolder();
                }
            }
        }

        return null;
    }

    /**
     * 
     * @param xpdlResource
     * @return
     */
    private boolean isWsdlRequired(IResource xpdlResource) {
        EObject rootElement = getWorkingCopy(xpdlResource).getRootElement();
        if (rootElement != null && rootElement instanceof Package) {
            Package pkg = (Package) rootElement;
            return shouldCreateWsdl(pkg);
        }
        return false;
    }

    /**
     * @param pkg
     * @return
     */
    public static boolean shouldCreateWsdl(Package pkg) {
        EList<Process> processes = pkg.getProcesses();
        for (Process proc : processes) {
            if (isPortTypeRequiredForProcess(proc)) {
                return true;
            }
        }

        ProcessInterfaces processInterfaces =
                ProcessInterfaceUtil.getProcessInterfaces(pkg);
        if (processInterfaces != null) {
            for (ProcessInterface procIfc : processInterfaces
                    .getProcessInterface()) {
                if (isPortTypeRequiredForProcIfc(procIfc)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Looks at the process destinations enabled. If the destination has a
     * validation component that requires it not to generate the WSDLs then
     * returns false;
     * 
     * @param proc
     */
    private static boolean isPortTypeRequiredForProcess(Process proc) {

        Boolean shouldGen =
                ProcessDestinationUtil
                        .shouldGenerateWSDLForProcessDestinations(proc);
        if (!shouldGen) {
            return false;
        }
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(proc);

        for (Activity act : allActivitiesInProc) {

            if (Xpdl2ModelUtil.isGeneratedRequestActivity(act)) {
                /*
                 * If implements interface then don't return true, this may or
                 * may not be in the same xpdl. If it is then the other checks
                 * for message based interface in calling method will deal with
                 * this.
                 */
                if (!ProcessInterfaceUtil.isEventImplemented(act)) {
                    return true;
                }
            }

            // To Check if it is a start event of type none with the extended
            // attribute on the process
            if (act.getEvent() != null) {
                EventFlowType flowType = EventObjectUtil.getFlowType(act);
                EventTriggerType eventTriggerType =
                        EventObjectUtil.getEventTriggerType(act);
                if (flowType != null) {
                    if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                        if (act.eContainer() != null
                                && act.eContainer().equals(act.getProcess())) {
                            if (EventTriggerType.EVENT_NONE_LITERAL
                                    .equals(eventTriggerType)
                                    && doesContainRequiredExtendedAttribute(proc)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true, if the process contains an extended attribute called
     * "InternalJmxDebug" with value "true"(Case ignored on the value).
     * 
     * @param process
     *            the process in consideration
     * 
     */
    public static boolean doesContainRequiredExtendedAttribute(Process process) {
        ExtendedAttribute internalJmxDebugExtAttr =
                XpdlSearchUtil.findExtendedAttribute(process,
                        WsdlgenPlugin.INTERNAL_JMX_DEBUG);
        if (internalJmxDebugExtAttr != null
                && internalJmxDebugExtAttr.getValue() != null
                && internalJmxDebugExtAttr.getValue().equalsIgnoreCase("true")) { //$NON-NLS-1$
            return true;
        }
        return false;

        // XPD-288 & XPD-72 - discussions going on, not supported currently
        // we want to always generate wsdl for start event type none; even if
        // the InternalJmxDebug extended attribute is set to true or not
        // return true;
    }

    /**
     * Check if the extended attribute is set to indicate that port type should
     * be generated for process interfaces.
     * 
     * @param processInterface
     * @return
     */
    public static boolean isWsdlGenEnabledForProcessInterface(
            ProcessInterface processInterface) {

        if (processInterface != null) {
            Package pkg =
                    ProcessInterfaceUtil
                            .getProcessInterfacePackage(processInterface);

            if (pkg != null) {
                ExtendedAttribute extAttr =
                        XpdlSearchUtil.findExtendedAttribute(pkg,
                                WsdlgenPlugin.ENABLE_WSDLGEN_ON_PROCINTERFACE);

                return extAttr != null && extAttr.getValue() != null
                        && Boolean.parseBoolean(extAttr.getValue());
            }
        }
        return false;
    }

    @Override
    protected void clean(IProgressMonitor monitor) throws CoreException {
        super.clean(monitor);
        // Delete all the derived resources in the "Generated Services" folder
        IProject proj = getProject();
        IFolder genServicesFolder = getGeneratedServicesFolder(proj);
        List<IResource> membersToDelete = new ArrayList<IResource>();

        if (genServicesFolder != null) {
            IResource[] members = genServicesFolder.members();
            for (int mem = 0; mem < members.length; mem++) {
                // Change to delete only those WSDLs that have been marked with
                // a TIBEX attribute. Those that haven't wont get deleted.
                // The idea is that all those generated will have the TIBEX
                // attribute and should be regenerated when projects are
                // imported

                IResource resource = members[mem];

                if (resource.isAccessible()
                        && WSDL_FILE_EXTN.equals(resource.getFileExtension())) {
                    WorkingCopy workingCopy =
                            WorkingCopyUtil.getWorkingCopy(resource);
                    if (workingCopy != null
                            && workingCopy.getRootElement() instanceof Definition) {
                        Definition definition =
                                (Definition) workingCopy.getRootElement();
                        if (definition.getElement() != null) {
                            String servcTaskTibexAttrib =
                                    definition
                                            .getElement()
                                            .getAttribute(WsdlUIPlugin.TIBEX_SERVICE_TASK);

                            if (servcTaskTibexAttrib != null
                                    && servcTaskTibexAttrib.length() > 0) {
                                // Don't want to delete Service task generated
                                // files if they are dropped into the Generated
                                // Services folder.
                                continue;
                            }

                            // Concrete WSDLs can be generated from PAAS WSDLs,
                            // so the above and below checks are necessary.
                            String tibxAttrib =
                                    definition
                                            .getElement()
                                            .getAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER);
                            if ((tibxAttrib == null || tibxAttrib.length() > 0)
                                    && isNotConcrete(definition)) {
                                membersToDelete.add(resource);
                            }
                        }
                    }
                }
            }
        }

        for (IResource resource : membersToDelete) {
            resource.delete(true, monitor);
        }

    }

    /**
     * Concrete WSDLs can be generated out of Abstract ones, this utility checks
     * whether the provided WSDL definition one is that which is generated by
     * Studio as part of the Concrete WSDL generation wizard
     * 
     * @param definition
     * @return
     */
    private boolean isNotConcrete(Definition definition) {
        String concreteFlag =
                definition
                        .getElement()
                        .getAttribute(com.tibco.xpd.wsdl.Activator.TIBEX_CONCRETE_FLAG);
        if (concreteFlag != null && "true".equals(concreteFlag)) { //$NON-NLS-1$
            return false;
        }
        return true;
    }

    @Override
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        super.setInitializationData(config, propertyName, data);
    }

    @Override
    protected void startupOnInitialize() {
        super.startupOnInitialize();
    }

    private List<IFolder> getDflowFolder(IProject project) {
        SpecialFolders specialFolders =
                XpdResourcesPlugin.getDefault().getProjectConfig(project)
                        .getSpecialFolders();
        if (specialFolders != null) {
            return specialFolders
                    .getEclipseIFoldersOfKind(Xpdl2ResourcesConsts.DECISIONS_SPECIAL_FOLDER_KIND);
        }
        return Collections.emptyList();
    }

    /**
     * Returns list of eclipse folders which are marked as special packages
     * folder for provided project.
     * 
     * @param project
     *            the eclipse project.
     * @return list of special packages folders for the project.
     */
    public static List<IFolder> getPackagesFolders(IProject project) {
        SpecialFolders specialFolders =
                XpdResourcesPlugin.getDefault().getProjectConfig(project)
                        .getSpecialFolders();
        if (specialFolders != null) {
            return specialFolders
                    .getEclipseIFoldersOfKind(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
        }
        return Collections.emptyList();
    }

    private static final void TRACE(String message) {
        LOG.trace(Logger.CATEGORY_BUILD,
                String.format("[%d]WsdlGenBuild: ", Thread.currentThread() //$NON-NLS-1$
                        .getId()) + message);
    }

    /**
     * Get all xpdl files that are affected by a change to the given WSDL file.
     * This will return all xpdls that depend on the given wsdl file (include
     * indirect dependencies).
     * 
     * @param wsdlFile
     *            the wsdl file.
     * @return set of xpdl files that references the wsdl.
     */
    public static Set<IFile> getAffectedXpdlsForWsdl(IFile wsdlFile) {
        if (wsdlFile != null) {
            Set<IFile> xpdls = new HashSet<IFile>();

            IProject project = wsdlFile.getProject();

            IPath wsdlRelativePath =
                    SpecialFolderUtil
                            .getSpecialFolderRelativePath(wsdlFile,
                                    com.tibco.xpd.wsdl.Activator.WSDL_SPECIALFOLDER_KIND);

            Set<String> xpdlsReferringWsdl =
                    ProcessUIUtil.queryWsdlReferencingXpdls(project,
                            wsdlRelativePath.toString());

            for (String xpdlUriStr : xpdlsReferringWsdl) {
                IPath xpdlPath = new Path(xpdlUriStr).setDevice(null);
                IFile xpdlFile =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(xpdlPath);
                /*
                 * TODO: when bpel builder for decisions is ready need to
                 * revisit whether this entire method must be re-written for
                 * indexers and other stuff to work accordingly for decisions
                 * project
                 */
                if (xpdlFile.exists()
                        && (isXpdl(xpdlFile) || isDflow(xpdlFile))) {
                    xpdls.add(xpdlFile);
                }
            }
            return xpdls;
        }
        return Collections.emptySet();
    }

    /**
     * Check if the given BOM file is valid. This will check for any BOM Generic
     * issues in the file.
     * 
     * @param file
     * @return
     */
    private boolean isFileValid(IFile file) {
        if (file != null && file.exists()) {
            try {
                Collection<IMarker> markers =
                        ValidationProblemUtil
                                .getProblemMarkers("cds.process.destination", //$NON-NLS-1$
                                        IMarker.SEVERITY_ERROR,
                                        true,
                                        file);
                return markers.isEmpty();
            } catch (CoreException e) {
                BOMGenActivator.getDefault().getLogger().log(e.getStatus());
            }
        }
        return false;
    }

}
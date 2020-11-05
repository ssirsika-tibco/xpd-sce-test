/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.n2.bpel.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.n2.bpel.Messages;
import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.builder.ondemand.AbstractOnDemandBuilder;
import com.tibco.xpd.resources.builder.ondemand.BuildSourceSet;
import com.tibco.xpd.resources.builder.ondemand.BuildTargetSet;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rsd.ui.RsdUIPlugin;
import com.tibco.xpd.validation.utils.ValidationProblemUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.RestServiceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * On Demand builder for XPDL -> AMXBPM BPEL
 * 
 * @author aallway
 * @since 19 Dec 2011
 */
public class BPELOnDemandBuilder extends AbstractOnDemandBuilder {

    /**
     * @param project
     */
    public BPELOnDemandBuilder(IProject project) {
        super(project);
    }

    /**
     * @see com.tibco.xpd.resources.builder.ondemand.AbstractOnDemandBuilder#getMainMonitorLabel(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected String getMainMonitorLabel(IProject project) {
        return String
                .format(Messages.BPELOnDemandBuilder_CreatingRuntimeProcesses_message);
    }

    /**
     * @see com.tibco.xpd.resources.builder.ondemand.AbstractOnDemandBuilder#getBuildSourceSets(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected List<BuildSourceSet> getBuildSourceSets(IProject project)
            throws CoreException {

        List<BuildSourceSet> sourceSets = new ArrayList<BuildSourceSet>();

        /*
         * Get all the XPDL files in special folders in the project.
         */
        List<IResource> allXpdls =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                                Xpdl2ResourcesConsts.XPDL_EXTENSION,
                                false);

        if (allXpdls != null) {
            /*
             * Create a source set for each XPDL that has N2 processes.
             * 
             * The source set is used to ascertain the "up-to-date-ness" of the
             * BPEL and other (wsdl) files generated from this builder.
             */
            for (IResource resource : allXpdls) {
                if (resource instanceof IFile) {
                    IFile xpdl = (IFile) resource;

                    if (N2Utils.hasN2ProcessesOrInterfaces(xpdl)) {
                        sourceSets.add(new Xpdl2BpelSourceSet(xpdl));
                    }
                }
            }
        }

        return sourceSets;
    }

    /**
     * @see com.tibco.xpd.resources.builder.ondemand.AbstractOnDemandBuilder#getBuildTargetSet(com.tibco.xpd.resources.builder.ondemand.AbstractOnDemandBuilder.BuildSourceSet)
     * 
     * @param sourceSet
     * @return
     */
    @Override
    protected BuildTargetSet getBuildTargetSet(BuildSourceSet sourceSet)
            throws CoreException {

        Xpdl2BpelSourceSet xpdl2BpelSourceSet = (Xpdl2BpelSourceSet) sourceSet;

        IFile xpdlFile = xpdl2BpelSourceSet.getXpdlFile();

        Xpdl2BpelTargetSet xpdl2BpelTargetSet =
                new Xpdl2BpelTargetSet(xpdlFile);

        return xpdl2BpelTargetSet;
    }

    /**
     * @see com.tibco.xpd.resources.builder.ondemand.AbstractOnDemandBuilder#getTargetRootFolder(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected IFolder getTargetRootFolder(IProject project)
            throws CoreException {
        return project.getFolder(BPELN2Utils.BPEL_ROOT_OUTPUTFOLDER_NAME);
    }

    /**
     * @see com.tibco.xpd.resources.builder.ondemand.AbstractOnDemandBuilder#buildSourceSet(com.tibco.xpd.resources.builder.ondemand.AbstractOnDemandBuilder.BuildSourceSet,
     *      com.tibco.xpd.resources.builder.ondemand.AbstractOnDemandBuilder.BuildTargetSet,
     *      org.eclipse.core.runtime.SubProgressMonitor)
     * 
     * @param sourceSet
     * @param targetSet
     * @param subProgressMonitor
     * 
     */
    @Override
    protected void buildSourceSet(BuildSourceSet sourceSet,
            BuildTargetSet targetSet, IProgressMonitor monitor)
            throws CoreException {

        try {
            monitor.beginTask("", 3); //$NON-NLS-1$

            Xpdl2BpelSourceSet xpdl2BpelSourceSet =
                    (Xpdl2BpelSourceSet) sourceSet;
            Xpdl2BpelTargetSet xpdl2BpelTargetSet =
                    (Xpdl2BpelTargetSet) targetSet;

            if (N2Utils.hasN2ValidationProblems(xpdl2BpelSourceSet
                    .getXpdlFile())) {
                IStatus errorMarkersStatus =
                        ValidationProblemUtil
                                .getErrorMarkersStatus(xpdl2BpelSourceSet
                                        .getXpdlFile().getProject());
                IStatus status =
                        new MultiStatus(
                                XpdResourcesPlugin.ID_PLUGIN,
                                0,
                                errorMarkersStatus.getChildren(),
                                String.format(Messages.BPELOnDemandBuilder_CantBuildBpelBecauseOfProblemMarkers_message,
                                        xpdl2BpelSourceSet.getXpdlFile()
                                                .getFullPath().toString()),
                                null);

                throw new CoreException(status);
            }

            /*
             * Remove all potential old targets by removing the whole
             * .processOut/process/xxx.xpdl folder (same for pageflow).
             */
            cleanupTargetFolder(xpdl2BpelTargetSet.getProcessDestFolder(),
                    new SubProgressMonitor(monitor, 1));
            cleanupTargetFolder(xpdl2BpelTargetSet.getPageflowDestFolder(),
                    new SubProgressMonitor(monitor, 1));

            /*
             * Build the BPEL and associated files....
             */
            try {
                BPELN2Utils.buildXPDLFile(xpdl2BpelSourceSet.getXpdlFile(),
                        xpdl2BpelTargetSet.getProcessDestFolder(),
                        xpdl2BpelTargetSet.getPageflowDestFolder(),
                        new SubProgressMonitor(monitor, 1));
                /*
                 * XPD-3045 : Kapil: If (Sometimes) a empty BPEL File is
                 * generated , then clear all the Target BPEL Folders so that
                 * when there is a subsequent attempt , the BPEL file/files will
                 * be re-build.
                 */
                String emptyFilePath = getEmptyBPELFile(targetSet);
                if (emptyFilePath != null) {
                    throw new CoreException(
                            createStatus(IStatus.ERROR,
                                    String.format(Messages.BPELOnDemandBuilder_ErrorEmptyBPELFile_message,
                                            emptyFilePath)));
                }
            } catch (Exception e) {
                /*
                 * XPD-3045 : Kapil: If an Exception occurs during the DAA *
                 * export then clear all the Target BPEL Folders so that when
                 * there is a subsequent attempt , the BPEL file/files will be
                 * re-build.
                 */
                cleanupTargetFolder(xpdl2BpelTargetSet.getProcessDestFolder(),
                        new SubProgressMonitor(monitor, 1));
                cleanupTargetFolder(xpdl2BpelTargetSet.getPageflowDestFolder(),
                        new SubProgressMonitor(monitor, 1));

                XpdResourcesPlugin.getDefault().getLogger().error(e);
                throw new CoreException(
                        createStatus(IStatus.ERROR,
                                String.format(Messages.BPELOnDemandBuilder_BPELConversionException_message,
                                        xpdl2BpelSourceSet.getXpdlFile()
                                                .getFullPath().toString(),
                                        e.getMessage())));
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * Clean up the given target folder (by removing it)
     * 
     * @param processDestFolder
     * @param monitor
     * 
     * @throws CoreException
     *             If failed to remove target folder.
     */
    protected void cleanupTargetFolder(IFolder processDestFolder,
            IProgressMonitor monitor) throws CoreException {
        if (processDestFolder.exists()) {
            try {
                // XPD-6655 We need to clean up working copies
                // when the folder is deleted.
                WorkingCopyUtil
                        .deleteResourceInWorkspaceOperation(processDestFolder,
                                monitor);
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);

                throw new CoreException(
                        createStatus(e.getStatus().getCode(),
                                String.format(Messages.BPELOnDemandBuilder_ErrorRemoveTargetFolder_message,
                                        processDestFolder.getFullPath()
                                                .toString(),
                                        e.getMessage())));
            }
        }
    }

    /**
     * Checks all the generated BPEL Files , if any of the files is empty
     * returns the file-path, else null.
     * 
     * @param targetSet
     * @return filePath when the BPEL file is empty, else return null indicating
     *         everything is OK.
     */
    protected String getEmptyBPELFile(BuildTargetSet targetSet) {
        Set<IResource> members = targetSet.getTargetResources();
        // Check only BPEL files
        for (IResource eachResource : members) {
            if (eachResource.exists()
                    && eachResource instanceof IFile
                    && eachResource.getName()
                            .endsWith(N2PEConstants.BPEL_EXTENSION)) {
                File bpelFile = eachResource.getLocation().toFile();
                if (bpelFile != null && bpelFile.length() == 0) {
                    return eachResource.getFullPath().toString();
                }
            }
        }
        return null;
    }

    /**
     * Xpdl2Bpel source set.
     * <p>
     * This encapsulates the source XPDL file and the other files (referenced
     * WSDLs / BOMs) that the BPEL depends on.
     * 
     * @author aallway
     * @since 20 Dec 2011
     */
    private class Xpdl2BpelSourceSet extends BuildSourceSet {

        private IFile xpdlFile;

        /**
         * @param xpdlFile
         *            The source xpdl file.
         * @throws CoreException
         */
        public Xpdl2BpelSourceSet(IFile xpdlFile) throws CoreException {
            super(xpdlFile.getName());

            this.xpdlFile = xpdlFile;
            addSourceResource(xpdlFile);

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);
            if (wc == null) {
                throw new CoreException(
                        createStatus(IStatus.ERROR,
                                String.format(Messages.BPELOnDemandBuilder_CouldNotAccessWorkingCopy_message,
                                        xpdlFile.getFullPath().toString())));
            }

            /*
             * The source set must contain all of the files that derived targets depend upon.
             */
            List<IResource> dependencies = wc.getDependency();

            for (IResource dependency : dependencies) {
                if (dependency instanceof IFile) {
                    String fileExtension = dependency.getFileExtension();

                    /*
                     * Sid ACE-180: We should never be asked for WebServices any
                     * more in ACE
                     */
                    if (BOMResourcesPlugin.BOM_FILE_EXTENSION
                                    .equalsIgnoreCase(fileExtension)
                            || OMResourcesActivator.OM_FILE_EXTENSION
                                    .equalsIgnoreCase(fileExtension)
                            || RsdUIPlugin.RSD_EXTENSION
                                    .equalsIgnoreCase(fileExtension)) {

                        addSourceResource(dependency);
                    }
                }
            }

            /*
             * Sid ACE-4801 As sub-process references can contain project version ranges, we need to rebuild BPEL if the
             * parent project version of any referenced sub-process xpdl changes.
             * 
             * In order to do this we will add the .config file of the parent and all referenced projects to ensure that
             * the BPEL is built...
             */
            Set<IResource> projectConfigs = getProjectConfigs(xpdlFile);

            if (projectConfigs != null && !projectConfigs.isEmpty()) {
                for (IResource config : projectConfigs) {
                    addSourceResource(config);
                }
            }

        }

        /**
         * Return a list of the project .config files for the parent project AND all referenced projects.
         * 
         * @param file
         * @return list of .config resources
         */
        private Set<IResource> getProjectConfigs(IFile file) {
            Set<IProject> projects =
                    ProjectUtil.getReferencedProjectsHierarchy(file.getProject(), new HashSet<IProject>(), true);
            projects.add(file.getProject());

            Set<IResource> configs = new HashSet<IResource>();

            for (IProject project : projects) {
                IResource config = project.findMember(XpdResourcesPlugin.PROJECTCONFIGFILE);

                if (config != null && config.isAccessible()) {
                    configs.add(config);
                }
            }

            return configs;
        }

        /**
         * @return the xpdlFile
         */
        public IFile getXpdlFile() {
            return xpdlFile;
        }
    }

    /**
     * Build target set for Xpdl2Bpel.
     * <p>
     * Encapsulates the target bpel files and any ancillary targets (like copies
     * of WSDLs etc).
     * 
     * @author aallway
     * @since 20 Dec 2011
     */
    private class Xpdl2BpelTargetSet extends BuildTargetSet {

        private Package pkg;

        private Set<Process> pageflowsAndBizServices;

        private Set<Process> bizProcesses;

        private Set<Process> restServices;

        private IFolder pageflowDestFolder = null;

        private IFolder processDestFolder = null;

        /**
         * 
         * @param xpdlFile
         * @throws CoreException
         */
        public Xpdl2BpelTargetSet(IFile xpdlFile) throws CoreException {
            super(xpdlFile.getName() + "(bpel's)"); //$NON-NLS-1$

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);
            if (wc == null || !(wc.getRootElement() instanceof Package)) {
                throw new CoreException(
                        createStatus(IStatus.ERROR,
                                String.format(Messages.BPELOnDemandBuilder_CouldNotAccessPackage_message,
                                        xpdlFile.getFullPath().toString())));
            }

            this.pkg = (Package) wc.getRootElement();

            /* Grab a list of pageflows and of processes. */
            cacheN2Processes();

            pageflowDestFolder =
                    BPELN2Utils.getBpelPageFlowXpdlDestFolder(xpdlFile
                            .getProject(), xpdlFile);

            if (!pageflowsAndBizServices.isEmpty()) {
                /*
                 * Add the bpel file for this pageflow and any referenced WSDLs
                 */
                for (Process pageflow : pageflowsAndBizServices) {
                    addTargetResourcesForProcess(pageflowDestFolder, pageflow);
                }
            }

            if (!restServices.isEmpty()) {
                /*
                 * Add the bpel files for the REST services
                 */
                for (Process restService : restServices) {
                    addTargetResourcesForProcess(pageflowDestFolder,
                            restService);
                }
            }

            processDestFolder =
                    BPELN2Utils.getBpelBusinessProcessXpdlDestFolder(xpdlFile
                            .getProject(), xpdlFile);

            if (!bizProcesses.isEmpty()) {
                /*
                 * Add the bpel file for this business process and any
                 * referenced WSDLs
                 */
                for (Process process : bizProcesses) {
                    addTargetResourcesForProcess(processDestFolder, process);
                }
            }
        }

        /**
         * @return the pageflowDestFolder
         */
        public IFolder getPageflowDestFolder() {
            return pageflowDestFolder;
        }

        /**
         * @return the processDestFolder
         */
        public IFolder getProcessDestFolder() {
            return processDestFolder;
        }

        /**
         * Add the target resources (Bpel and referenced WSDLs) to the given
         * folder for the given business / pageflow / REST process.
         * 
         * @param destFolder
         * @param process
         */
        private void addTargetResourcesForProcess(IFolder destFolder,
                Process process) {

            /*
             * We will always add the "xpdl package name" folders (for pageflow
             * and processes) explicitly to the list of targets. This prevents
             * AbstractOnDemandBuilder.removeUnusedOldTargets() from clearing
             * out and file except what we explicitly from under those fodlers.
             * 
             * We do this because we don't own the xpdl2bpel converter so we
             * don't want to accidentally have files other than the ones we
             * currently know about removed accidentally.
             */
            this.addTargetResource(destFolder);

            IFile bpelFile =
                    destFolder.getFile(process.getName()
                            + N2PEConstants.BPEL_EXTENSION);

            this.addTargetResource(bpelFile, process);

            /*
             * Sid ACE-714 (should have been ACE-194 - we don't do WSDL for
             * process as service or REST service invoke is not done via pass
             * thru WSDL any more. So removed all WSDL handling.
             */

        }

        /**
         * Load the cache of processes / pageflows / REST services
         */
        private void cacheN2Processes() {

            pageflowsAndBizServices = new HashSet<Process>();
            bizProcesses = new HashSet<Process>();
            restServices = new HashSet<Process>();

            for (Process process : pkg.getProcesses()) {

                if (XPDLUtils.hasN2Destination(process)) {

                    if (Xpdl2ModelUtil.isPageflowOrSubType(process)) {
                        pageflowsAndBizServices.add(process);

                    } else if (Xpdl2ModelUtil.isServiceProcess(process)) {
                        /*
                         * Add Service Processes - if the target destination is
                         * pageflow add them to pageflows, if the target
                         * destination Process engine add them to business
                         * processes
                         */
                        if (ProcessInterfaceUtil
                                .isPageflowEngineServiceProcess(process)) {
                            pageflowsAndBizServices.add(process);
                        }

                        if (ProcessInterfaceUtil
                                .isProcessEngineServiceProcess(process)) {
                            bizProcesses.add(process);
                        }
                    } else {
                        bizProcesses.add(process);
                    }

                    restServices.addAll(RestServiceUtil
                            .getRestServices(process));
                }
            }
        }
    }

}

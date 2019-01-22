/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.bpel.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.n2.bpel.BpelBuilderActivator;
import com.tibco.xpd.n2.bpel.Messages;
import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.resources.builder.XpdIncrementalProjectBuilder;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.wsdlgen.WsdlGenBuilderTransformer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.edit.util.XpdlUtils;

/**
 * @author kupadhya
 * 
 */
public class BPELBuilder extends XpdIncrementalProjectBuilder {

    private static final Logger LOG = BpelBuilderActivator.getDefault()
            .getLogger();

    public static final String BUILDER_ID = BpelBuilderActivator.PLUGIN_ID
            + ".bpelBuilder"; //$NON-NLS-1$

    private static final String PROCESS_SPECIAL_FOLDER =
            Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND;

    private static final String PROCESS_FILE_EXTENSION =
            Xpdl2ResourcesConsts.XPDL_EXTENSION;

    private static final String WSDL_FILE_EXTENSION = "wsdl"; //$NON-NLS-1$

    private static final String SERVICE_DESCRIPTOR_SPECIAL_FOLDER_KIND =
            WSDL_FILE_EXTENSION;

    /**
     * XPD-2613: TEMPORARY switch for turning incremental build of bpel back ON
     * as the build of BPEL is now moved to DAA export time create-on-demand.
     * 
     * Remove as soon as we're happy and confident with new method (along with
     * old build incremental code except for clean build.
     */
    private static boolean DO_OLD_INCREMENTAL_BPEL_BUILD = false;

    @Override
    protected List<String> getSpecialFolderKinds() {
        return Arrays.asList(PROCESS_SPECIAL_FOLDER,
                SERVICE_DESCRIPTOR_SPECIAL_FOLDER_KIND);
    }

    /**
     * @see com.tibco.xpd.n2.resources.builder.XpdIncrementalProjectBuilder#doBuild(int,
     *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param kind
     * @param options
     * @param monitor
     * @return
     * @throws CoreException
     */
    @Override
    protected IProject[] doBuild(int kind, Map<?, ?> options,
            IProgressMonitor monitor) throws CoreException {
        /*
         * Prevent doBuild() from hapenning now that we build on demand. We used
         * to just disable this in handleResoruceChanged()/Removed() BUT
         * XpdlIncrementalProjectBuilder.doBuild() has a significant overhead of
         * checking for files affected in this projects by changes to files in
         * referenced projects (so when build during daa generate happens it was
         * taking a significant amount of time deciding what files to pass to
         * handleesoruceChanged() when that was just going to return without
         * doing anything anyway!).
         */
        if (DO_OLD_INCREMENTAL_BPEL_BUILD) {
            return super.doBuild(kind, options, monitor);
        }

        return new IProject[0];
    }

    /**
     * This builder is responsible for generating the needed Bpel files and the
     * referenced wsdls for a xpdl file.
     * 
     * This listens to changes in WSDL files
     * 
     * @deprecated Incremental build method content should be removed once happy
     *             with on-demand build during export DAA.
     **/
    @Deprecated
    @Override
    protected boolean handleResourceChanged(IProgressMonitor monitor,
            IResource resource) throws CoreException {
        boolean ret = true;

        if (DO_OLD_INCREMENTAL_BPEL_BUILD && resource != null) {

            /*
             * We need to build if an XPDL file changes.
             */
            if (resource instanceof IFile
                    && XpdlUtils.isSupportedXPDLFile(resource)) {

                /*
                 * We don't need to rebuild the target bpel files IF (a) they
                 * exists and (b) it has a later modification date than both the
                 * wsdl and the xpdl.
                 * 
                 * This prevents us from building twice (1st for xpdl and then
                 * for wsdl) in the same build cycle.
                 */
                IFile generatedWsdl =
                        WsdlGenBuilderTransformer.getWsdlFile((IFile) resource);

                if (!isBpelUpToDate((IFile) resource, generatedWsdl)) {
                    TRACE("  Building bpel because xpdl changed and bpel out of date.");
                    generateBpel(resource, monitor);
                } else {
                    TRACE("  Not building bpel on xpdl change as bpel timestamp later than source xpdl and wsdl: "
                            + resource.getFullPath());
                }
                /**
                 * XPD-1201: find affected xpdls that has same system
                 * participant shared resource configuration as that of source
                 * xpdl and generate bpel files for all affected
                 */
                IFile sourceXpdl = (IFile) resource;
                if (sourceXpdl != null) {
                    try {
                        Set<IFile> affectedXpdls =
                                getAffectedXpdlsForSharedRes(sourceXpdl);
                        for (IFile xpdlFile : affectedXpdls) {
                            if (!isBpelUpToDate(xpdlFile, resource)) {
                                TRACE("  Building bpel because gnerated wsdl changed and bpel out of date.");
                                generateBpel(xpdlFile, monitor);
                            } else {
                                TRACE("  Not building bpel on generated wsdl change as bpel timestamp later than source xpdl and wsdl: "
                                        + resource.getFullPath());
                            }
                        }
                    } catch (IOException e) {
                        LOG.error(e);
                    } finally {
                    }
                }
                ret = false;

            }
            /*
             * We also need to change if a WSDL changes that is generated from
             * an xpdl that we would normally build.
             */
            else if (isModelFile(resource,
                    WSDL_FILE_EXTENSION,
                    SERVICE_DESCRIPTOR_SPECIAL_FOLDER_KIND)
                    && resource.isDerived()) {

                IFile sourceXpdl = getSourceXpdl((IFile) resource);
                if (sourceXpdl != null) {
                    /*
                     * Ok it looks like this is a wsdl generated from a process
                     * as service xpdl so we may need a build.
                     * 
                     * However, we don't need to rebuild the target bpel file IF
                     * (a) it exists and (b) it has a later modification date
                     * than both the wsdl and the xpdl.
                     * 
                     * This prevents us from building twice (1st for xpdl and
                     * then for wsdl) in the same build cycle.
                     */
                    if (!isBpelUpToDate(sourceXpdl, resource)) {
                        TRACE("  Building bpel because gnerated wsdl changed and bpel out of date.");
                        generateBpel(sourceXpdl, monitor);
                    } else {
                        TRACE("  Not building bpel on generated wsdl change as bpel timestamp later than source xpdl and wsdl: "
                                + resource.getFullPath());
                    }
                }
                ret = false;
            }
            /*
             * We also need to change if a user defined WSDL changes that is
             * referenced from an xpdl.
             */
            else if (isModelFile(resource,
                    WSDL_FILE_EXTENSION,
                    SERVICE_DESCRIPTOR_SPECIAL_FOLDER_KIND)
                    && !resource.isDerived()) {

                /** get all wsdl affected xpdls */
                Set<IFile> affectedXpdlsForWsdl =
                        WsdlGenBuilderTransformer
                                .getAffectedXpdlsForWsdl((IFile) resource);

                for (IFile sourceXpdl : affectedXpdlsForWsdl) {
                    if (!isBpelUpToDate(sourceXpdl, resource)) {
                        TRACE("  Building bpel because referred wsdl changed and bpel out of date.");
                        generateBpel(sourceXpdl, monitor);
                    } else {
                        TRACE("  Not building bpel on referred wsdl change as bpel timestamp later than source xpdl and wsdl: "
                                + resource.getFullPath());
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 
     * @param sourceXpdl
     * @return
     * @throws IOException
     * @deprecated Incremental build method content should be removed once happy
     *             with on-demand build during export DAA.
     */
    @Deprecated
    private Set<IFile> getAffectedXpdlsForSharedRes(IFile sourceXpdl)
            throws IOException {
        if (null != sourceXpdl) {
            Set<IFile> affectedXpdls = new HashSet<IFile>();
            Participant targetParticipant = null;

            /** Get all xpdls from the source xpdl's project */
            IProject project = sourceXpdl.getProject();
            List<IResource> xpdlFiles =
                    WsdlGenBuilderTransformer.getXpdlFiles(project);
            /** Get Process Package for the source xpdl */
            Package sourceProcessPkg =
                    BPELN2Utils.getProcessPackage(sourceXpdl);

            /*
             * Get all participants in the package in question.
             */
            if (null != sourceProcessPkg) {
                Collection<Participant> pkgParticipants =
                        N2Utils.getPackageParticipants(sourceProcessPkg);
                if (pkgParticipants.size() > 0) {
                    /*
                     * Get the indexer items so we can quickly compare names
                     * (without loading stuff without reason.
                     */
                    List<IndexerItem> sortedProjectPartics =
                            N2Utils.getProjectParticipants(project);

                    for (Participant participant : pkgParticipants) {
                        boolean isSameConfigPartic =
                                N2Utils.isParticipantExistsInOtherXpdls(sourceProcessPkg,
                                        participant,
                                        sortedProjectPartics,
                                        project);
                        if (isSameConfigPartic) {
                            targetParticipant = participant;
                            break;
                        }
                    }
                }
            }

            if (null != targetParticipant) {
                for (IResource xpdlFile : xpdlFiles) {
                    if (xpdlFile instanceof IFile) {
                        Package processPackage =
                                BPELN2Utils.getProcessPackage((IFile) xpdlFile);
                        boolean particFound =
                                isTargetParticInThisPackage(targetParticipant,
                                        processPackage);
                        if (particFound) {
                            if (!xpdlFile.equals(sourceXpdl)) {
                                affectedXpdls.add((IFile) xpdlFile);
                            }
                        }
                    }
                }
            }
            return affectedXpdls;
        }
        return Collections.EMPTY_SET;
    }

    /**
     * @param targetParticipant
     * @deprecated Incremental build method content should be removed once happy
     *             with on-demand build during export DAA.
     */
    @Deprecated
    private boolean isTargetParticInThisPackage(Participant targetParticipant,
            Package pckg) {
        List<String> pkgParticipantNames = new ArrayList<String>();

        for (Participant partic : pckg.getParticipants()) {
            pkgParticipantNames.add(partic.getName());
        }

        for (Process process : pckg.getProcesses()) {
            for (Participant partic : process.getParticipants()) {
                pkgParticipantNames.add(partic.getName());
            }
        }

        if (pkgParticipantNames.contains(targetParticipant.getName())) {
            return true;
        }

        return false;
    }

    /**
     * @param sourceXpdl
     * @param generatedWsdl
     * 
     * @return true if the bpel files for the given xpdl file are all later date
     *         than the source xpdl and it's generated wsdl.
     */
    private boolean isBpelUpToDate(IFile sourceXpdl, IResource generatedWsdl) {
        /*
         * Gather the BPEL files in th eoutput folder.
         */
        IFolder businessProcessDestFolder =
                BPELN2Utils.getBpelBusinessProcessXpdlDestFolder(sourceXpdl
                        .getProject(), sourceXpdl);

        final Set<IFile> bpelFiles = new HashSet<IFile>();

        if (businessProcessDestFolder.exists()) {
            try {
                businessProcessDestFolder.accept(new IResourceVisitor() {
                    public boolean visit(IResource resource)
                            throws CoreException {
                        if (resource instanceof IFolder) {
                            return true;
                        } else if (resource instanceof IFile) {
                            if (BPELN2Utils.BPEL_EXTENSION.equalsIgnoreCase("."
                                    + resource.getFileExtension())) {
                                bpelFiles.add((IFile) resource);
                            }
                        }
                        return false;
                    }
                });
            } catch (CoreException e) {
                BpelBuilderActivator
                        .getDefault()
                        .getLogger()
                        .warn(e,
                                "Trapped exception itereating thru bpel target folder: "
                                        + businessProcessDestFolder
                                                .getFullPath());
            }
        }

        if (bpelFiles.isEmpty()) {
            /* If the set of bpel files is empty then always attempt a rebuild. */
            return false;
        }

        /*
         * If the bpel stamp is earlier than the xpdl and wsdl then it has to be
         * rebuilt.
         */
        final long xpdlStamp = sourceXpdl.getLocalTimeStamp();
        final long wsdlStamp =
                (generatedWsdl != null && generatedWsdl.exists()) ? generatedWsdl
                        .getLocalTimeStamp() : -1;

        for (IFile bpelFile : bpelFiles) {
            long bpelStamp = bpelFile.getLocalTimeStamp();

            if (bpelStamp <= xpdlStamp || bpelStamp <= wsdlStamp) {
                return false;
            }

        }

        return true;
    }

    /**
     * @param xpdlResource
     * @param monitor
     * @return
     * @deprecated Incremental build method content should be removed once happy
     *             with on-demand build during export DAA.
     */
    @Deprecated
    private void generateBpel(IResource xpdlResource, IProgressMonitor monitor) {
        try {
            if (N2Utils.hasN2ProcessesOrInterfaces(xpdlResource)) {
                if (N2Utils.hasN2ValidationProblems(xpdlResource)) {
                    TRACE("    Deleting Bpel for xpdl: "
                            + xpdlResource.getFullPath()
                            + " (there are problem markers on soruce xpdl)");

                    deleteBpelFiles(xpdlResource, monitor);

                } else {
                    TRACE("    Building Bpel for xpdl: "
                            + xpdlResource.getFullPath());

                    cleanBpelErrorMarkers(xpdlResource);
                    deleteBpelFiles(xpdlResource, monitor);
                    createBpelFiles(xpdlResource);
                }
            } else {
                deleteBpelFiles(xpdlResource, monitor);
            }

        } catch (Throwable e) {
            LOG.error(e, "Exception thrown from BPEL " //$NON-NLS-1$
                    + "builder while building resource: " + xpdlResource);
        }

        return;
    }

    /**
     * Get the source xpdl file of the generated wsdl file.
     * 
     * @param genWsdlFile
     * @return xpdl file if found or <code>null</code> if it cannot be resolved.
     * @deprecated Incremental build method content should be removed once happy
     *             with on-demand build during export DAA.
     */
    @Deprecated
    private IFile getSourceXpdl(IFile genWsdlFile) {
        IFile xpdl = null;

        if (genWsdlFile != null) {
            IPath path =
                    SpecialFolderUtil.getSpecialFolderRelativePath(genWsdlFile,
                            SERVICE_DESCRIPTOR_SPECIAL_FOLDER_KIND);
            if (path != null) {
                path =
                        path.removeFileExtension()
                                .addFileExtension(PROCESS_FILE_EXTENSION);

                // Find the xpdl with the given special folder relative path
                xpdl =
                        SpecialFolderUtil
                                .resolveSpecialFolderRelativePath(genWsdlFile
                                        .getProject(),
                                        PROCESS_SPECIAL_FOLDER,
                                        path.toString());
            }
        }

        return xpdl;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.n2.resources.builder.XpdIncrementalProjectBuilder#
     * cleanGeneratedArtifacts(org.eclipse.core.runtime.IProgressMonitor,
     * org.eclipse.core.resources.IProject)
     */
    @Override
    protected void cleanGeneratedArtifacts(IProgressMonitor monitor,
            IProject project) {
        List<IResource> xpdlFiles =
                WsdlGenBuilderTransformer.getXpdlFiles(project);
        for (IResource resource : xpdlFiles) {
            deleteBpelFiles(resource, monitor);
        }
    }

    /**
     * 
     * @see com.tibco.xpd.n2.resources.builder.XpdIncrementalProjectBuilder#handleResourceRemoved(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param monitor
     * @param resource
     * @throws CoreException
     * @deprecated Incremental build method content should be removed once happy
     *             with on-demand build during export DAA.
     */
    @Deprecated
    @Override
    protected void handleResourceRemoved(IProgressMonitor monitor,
            IResource resource) throws CoreException {
        /*
         * Sid XPD-2613: For on demand building the on demand builder will
         * remove any old unwanted resources in .processOut so we don't need to
         * here.
         */
        if (DO_OLD_INCREMENTAL_BPEL_BUILD) {
            if (resource != null) {
                if (XpdlUtils.isSupportedXPDLFile(resource)) {
                    deleteBpelFiles(resource, monitor);
                }
            }
        }
    }

    /**
     * Participate in project clean
     * 
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @throws CoreException
     */
    @Override
    protected void clean(IProgressMonitor monitor) throws CoreException {
        cleanBpelOutFolder(monitor);
    }

    /**
     * Remove the entire contents of the bpel output folder (.processOut) for a
     * project clean.
     * 
     * @param monitor
     */
    private void cleanBpelOutFolder(IProgressMonitor monitor) {
        IFolder bpelOutFolder = BPELN2Utils.getBpelOutDestFolder(getProject());
        if (bpelOutFolder != null && bpelOutFolder.exists()) {
            try {

                /*
                 * Sid XPD-2613: Should really take a copy of the members list
                 * before removing items from it!
                 */
                Set<IResource> toRemove = new HashSet<IResource>();
                for (IResource resource : bpelOutFolder.members()) {
                    toRemove.add(resource);
                }

                for (IResource resource : toRemove) {
                    try {
                        removeResource(resource, monitor);
                    } catch (CoreException e) {
                        LOG.error(e);
                        addBpelErrorMarker(resource,
                                Messages.BPELBuilder_processArtifactCleanProblem);
                    }
                }
            } catch (CoreException e) {
                LOG.error(e);
                addBpelErrorMarker(bpelOutFolder,
                        Messages.BPELBuilder_processArtifactCleanProblem);
            }
        }
    }

    /**
     * 
     * @param resource
     * @param monitor
     * @deprecated Incremental build method content should be removed once happy
     *             with on-demand build during export DAA.
     **/

    @Deprecated
    private void deleteBpelFiles(IResource resource, IProgressMonitor monitor) {
        // Delete the bpel files
        IFolder businessProcessDestFolder =
                BPELN2Utils.getBpelBusinessProcessXpdlDestFolder(resource
                        .getProject(), resource);
        try {
            removeResource(businessProcessDestFolder, monitor);
        } catch (CoreException e) {
            LOG.error(e);
            addBpelErrorMarker(resource,
                    Messages.BPELBuilder_processArtifactDeletionProblem,
                    Collections.singletonList((Object) resource.getName()));
        }
        IFolder pageFlowDestFolder =
                BPELN2Utils
                        .getBpelPageFlowXpdlDestFolder(resource.getProject(),
                                resource);
        try {
            removeResource(pageFlowDestFolder, monitor);
        } catch (CoreException e) {
            LOG.error(e);
            addBpelErrorMarker(resource,
                    Messages.BPELBuilder_processArtifactDeletionProblem,
                    Collections.singletonList((Object) resource.getName()));
        }
    }

    /**
     * 
     * @param resource
     * @deprecated Incremental build method content should be removed once happy
     *             with on-demand build during export DAA.
     */
    @Deprecated
    private void createBpelFiles(IResource resource) {
        // Generate the bpel files
        IFolder businessProcessDestFolder =
                BPELN2Utils.getBpelBusinessProcessXpdlDestFolder(resource
                        .getProject(), resource);
        IFolder pageFlowDestFolder =
                BPELN2Utils
                        .getBpelPageFlowXpdlDestFolder(resource.getProject(),
                                resource);

        try {
            BPELN2Utils.buildXPDLFile(resource,
                    businessProcessDestFolder,
                    pageFlowDestFolder,
                    null);
        } catch (Exception e) { /* XPD-2416. */
            LOG.error(e);
            addBpelErrorMarker(resource,
                    Messages.BPELBuilder_processArtifactCreationProblem,
                    Collections.singletonList((Object) resource.getName()));
        }
    }

    private void cleanBpelErrorMarkers(IResource resource) {
        try {
            N2PENamingUtils.cleanDaaErrorMarkers(resource,
                    BPELN2Utils.BPEL_ERROR_MARKER_ID);
        } catch (CoreException e) {
            LOG.error(e);
        }
    }

    private void addBpelErrorMarker(IResource resource, String msg) {
        try {
            N2PENamingUtils.addDaaErrorMarker(resource,
                    msg,
                    BPELN2Utils.BPEL_ERROR_MARKER_ID,
                    Collections.emptyList());
        } catch (CoreException e) {
            LOG.error(e);
        }
    }

    private void addBpelErrorMarker(IResource resource, String msg,
            List<Object> args) {
        try {
            N2PENamingUtils.addDaaErrorMarker(resource,
                    msg,
                    BPELN2Utils.BPEL_ERROR_MARKER_ID,
                    args);
        } catch (CoreException e) {
            LOG.error(e);
        }
    }

    private static final void TRACE(String message) {
        LOG.trace(Logger.CATEGORY_BUILD,
                String.format("[%d]BPELBuilder: ", Thread.currentThread() //$NON-NLS-1$
                        .getId()) + message);
    }

}

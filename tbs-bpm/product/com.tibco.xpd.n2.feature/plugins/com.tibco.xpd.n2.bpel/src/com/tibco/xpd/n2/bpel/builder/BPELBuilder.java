/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.bpel.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.n2.bpel.BpelBuilderActivator;
import com.tibco.xpd.n2.bpel.Messages;
import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.resources.builder.XpdIncrementalProjectBuilder;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
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
             * Sid ACE-194: Removed old incremental build parts as they weren't
             * used anymore.
             */
        }
        return ret;
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
        ArrayList<IResource> xpdlFiles =
                SpecialFolderUtil.getResourcesInSpecialFolderOfKind(project,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                        Xpdl2ResourcesConsts.XPDL_EXTENSION);
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

/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.globalSignalDefinition.util.GsdConstants;
import com.tibco.xpd.globalSignalDefinition.workingcopy.GsdWorkingCopy;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Helper class for GSD Move/Rename.
 * 
 * @author kthombar
 * @since Jul 2, 2015
 */
public class GsdRefactorHelper {

    /**
     * Get all GSD files that are contained in the given folder (including any
     * subfolders).
     * 
     * @param folder
     * @return list of files, empty list if no GSDs are found.
     * @throws CoreException
     */
    public static List<IFile> getGsdFiles(IFolder folder) throws CoreException {
        final List<IFile> gsdFiles = new ArrayList<IFile>();

        folder.accept(new IResourceProxyVisitor() {

            @Override
            public boolean visit(IResourceProxy proxy) throws CoreException {
                if (proxy.getType() == IResource.FILE) {
                    if (proxy.getName().endsWith("." //$NON-NLS-1$
                            + GsdConstants.GSD_FILE_EXTENSION)) {
                        IFile file = (IFile) proxy.requestResource();
                        if (file.exists()) {
                            gsdFiles.add(file);
                        }
                    }
                    return false;
                }
                return true;
            }
        }, 0);

        return gsdFiles;
    }

    /**
     * 
     * @return All the Activites which reference Global Signals in the GSD file
     *         whose name is being changes.
     */
    public static List<Activity> getAllRefrencingGlobalSignalActivities(
            IFile refactoredGsdFile) {

        List<Activity> allRefrencingGsActivities = new ArrayList<Activity>();
        /*
         * get all the reverse dependent resource from the indexer.
         */
        Collection<IResource> affectedResources =
                WorkingCopyUtil.getAffectedResources(refactoredGsdFile);

        for (IResource iResource : affectedResources) {
            /*
             * We are interested only in XPDL files
             */
            if (Xpdl2ResourcesConsts.XPDL_EXTENSION.equals(iResource
                    .getFileExtension())) {

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(iResource);
                if (wc instanceof Xpdl2WorkingCopyImpl) {

                    EObject rootElement = wc.getRootElement();

                    if (rootElement instanceof com.tibco.xpd.xpdl2.Package) {

                        com.tibco.xpd.xpdl2.Package pkg =
                                (com.tibco.xpd.xpdl2.Package) rootElement;

                        for (Process process : pkg.getProcesses()) {

                            for (Activity activity : Xpdl2ModelUtil
                                    .getAllActivitiesInProc(process)) {

                                if (GlobalSignalUtil
                                        .isGlobalSignalEvent(activity)) {

                                    GlobalSignal referencedGlobalSignal =
                                            GlobalSignalUtil
                                                    .getReferencedGlobalSignal(activity);
                                    /*
                                     * check if the global signal is from the
                                     * GSD whose name is being refactored.
                                     */
                                    if (referencedGlobalSignal != null
                                            && refactoredGsdFile
                                                    .equals(WorkingCopyUtil
                                                            .getFile(referencedGlobalSignal))) {

                                        allRefrencingGsActivities.add(activity);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return allRefrencingGsActivities;
    }

    /**
     * Check whether any affected working copies are in the dirty state. If they
     * are then the refactor cannot continue.
     * 
     * @see RefactoringParticipant#checkConditions(IProgressMonitor,
     *      CheckConditionsContext)
     * 
     * @param pm
     * @param context
     * @param affectedGsdWC
     * @return
     * @throws OperationCanceledException
     */
    public static RefactoringStatus checkWorkingCopyDirtyConditions(
            IProgressMonitor pm, CheckConditionsContext context,
            Collection<GsdWorkingCopy> affectedGsdWC)
            throws OperationCanceledException {

        if (pm != null) {
            pm = SubProgressMonitorEx.createNestedSubProgressMonitor(pm, 1);
        } else {
            pm = new NullProgressMonitor();
        }

        ResourceChangeChecker checker =
                (ResourceChangeChecker) context
                        .getChecker(ResourceChangeChecker.class);
        IResourceChangeDescriptionFactory deltaFactory =
                checker.getDeltaFactory();

        /*
         * Check that none of the working copies being affected are in the dirty
         * state.
         */
        try {
            pm.beginTask(Messages.GsdRefactorHelper_CheckingWorkingCopiesProgressMonitor_msg,
                    affectedGsdWC.size());
            for (GsdWorkingCopy wc : affectedGsdWC) {
                List<IResource> resources = wc.getEclipseResources();
                if (!resources.isEmpty()) {
                    deltaFactory.change((IFile) resources.get(0));

                    if (wc.isWorkingCopyDirty()) {
                        return RefactoringStatus
                                .createFatalErrorStatus(String
                                        .format(Messages.GsdRefactorHelper_WorkingCopydirtyError_msg,
                                                wc.getName()));
                    }
                }
                pm.worked(1);
            }
        } finally {
            pm.done();
        }

        return new RefactoringStatus();
    }
}

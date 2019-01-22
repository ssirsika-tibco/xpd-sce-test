/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.resources.internal.refactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.internal.Messages;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Helper class used by the BOM rename and move refactoring participants.
 * 
 * @author njpatel
 */
public class BOMRefactorHelper {

    /**
     * Helper class used by the BOM rename and move refactoring participants.
     */
    public BOMRefactorHelper() {
    }

    /**
     * Get a collection of all BOM working copies that have a reference to the
     * given file.
     * 
     * @param file
     * @return set of working copies, empty set if this file is not referenced
     *         by any BOMs.
     */
    public Set<BOMWorkingCopy> getAllAffectedBOMs(IFile file) {
        Set<BOMWorkingCopy> affectedResources = new HashSet<BOMWorkingCopy>();

        Collection<IResource> resources =
                WorkingCopyUtil.getAffectedResources(file);
        for (IResource res : resources) {
            if (res instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(res
                            .getFileExtension())) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(res);
                if (wc instanceof BOMWorkingCopy) {
                    affectedResources.add((BOMWorkingCopy) wc);
                }
            }
        }

        return affectedResources;
    }

    /**
     * Gets the set of Projects that the passed BOM requires to reference post
     * move.
     * 
     * @param bomFile
     * @return set of Projects that the passed BOM requires to reference post
     *         move.
     */
    public Set<IProject> getAllBOMProjectsToReference(IFile bomFile) {
        Set<IProject> allProjectsToReference = new HashSet<IProject>();

        Set<IResource> deepDependencies =
                WorkingCopyUtil.getDeepDependencies(bomFile);

        for (IResource iResource : deepDependencies) {

            if (iResource instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(iResource
                            .getFileExtension())) {

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(iResource);
                if (wc instanceof BOMWorkingCopy) {

                    IProject project = iResource.getProject();
                    if (project != null && project.isAccessible()) {

                        allProjectsToReference.add(project);
                    }
                }
            }
        }

        return allProjectsToReference;
    }

    /**
     * Gets the set of Projects that need to reference the bomFile post move.
     * 
     * @param bomFile
     * @return set of Projects that need to reference the bomFile post move.
     */
    public Set<IProject> getAllProjectsToAddReferenceFrom(IFile bomFile) {
        Set<IProject> allProjectsToAddReferenceFrom = new HashSet<IProject>();

        Collection<IResource> resources =
                WorkingCopyUtil.getAffectedResources(bomFile);

        for (IResource res : resources) {

            IProject project = res.getProject();
            if (project != null && project.isAccessible()) {

                allProjectsToAddReferenceFrom.add(project);
            }
        }

        return allProjectsToAddReferenceFrom;
    }

    /**
     * Get all BOM files that are contained in the given folder (including any
     * subfolders).
     * 
     * @param folder
     * @return list of files, empty list if no BOMs are found.
     * @throws CoreException
     */
    public List<IFile> getBOMFiles(IFolder folder) throws CoreException {
        final List<IFile> bomFiles = new ArrayList<IFile>();

        folder.accept(new IResourceProxyVisitor() {

            @Override
            public boolean visit(IResourceProxy proxy) throws CoreException {
                if (proxy.getType() == IResource.FILE) {
                    if (proxy.getName().endsWith("." //$NON-NLS-1$
                            + BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                        IFile file = (IFile) proxy.requestResource();
                        if (file.exists()) {
                            bomFiles.add(file);
                        }
                    }
                    return false;
                }
                return true;
            }
        }, 0);

        return bomFiles;
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
     * @param affectedBOMs
     * @return
     * @throws OperationCanceledException
     */
    public RefactoringStatus checkWorkingCopyDirtyConditions(
            IProgressMonitor pm, CheckConditionsContext context,
            Collection<BOMWorkingCopy> affectedBOMs)
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
            pm.beginTask("Checking working copies...", affectedBOMs.size());
            for (BOMWorkingCopy wc : affectedBOMs) {
                List<IResource> resources = wc.getEclipseResources();
                if (!resources.isEmpty()) {
                    deltaFactory.change((IFile) resources.get(0));

                    if (wc.isWorkingCopyDirty()) {
                        return RefactoringStatus
                                .createFatalErrorStatus(String
                                        .format(Messages.BOMRefactorHelper_fileNeedsSaving_error_shortdesc,
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

    /**
     * Create {@link Change} objects to update the references in the affected
     * working copies.
     * 
     * @param pm
     * 
     * @param affectedBOMs
     *            Working copies of models to update the references in.
     * @param origBOMFiles
     *            the bom files as they were before the rename/move
     * @param refactoredBOMFiles
     *            the bom files as they are after the rename/move
     * @return list of change objects.
     */
    public List<Change> createUpdateReferenceChanges(IProgressMonitor pm,
            Collection<BOMWorkingCopy> affectedBOMs, IFile[] origBOMFiles,
            IFile[] refactoredBOMFiles) {
        List<Change> changes = new ArrayList<Change>();

        if (pm == null) {
            pm = new NullProgressMonitor();
        } else {
            pm = SubProgressMonitorEx.createNestedSubProgressMonitor(pm, 1);
        }
        /*
         * Create a change for each working copy that needs to be updated with
         * the new reference.
         */
        pm.beginTask(Messages.BOMRefactorHelper_addReferenceChange_monitor_shortdesc,
                origBOMFiles.length);
        for (BOMWorkingCopy wc : affectedBOMs) {
            if (wc.getRootElement() != null && !wc.getRootElement().eIsProxy()) {
                changes.add(new UpdateReferenceChange(wc, origBOMFiles,
                        refactoredBOMFiles));
                pm.worked(1);
            }
        }

        pm.done();

        return changes;
    }

    /**
     * Check if the special folder relative paths of the two resources is
     * identical.
     * 
     * @param currentRes
     * @param refactoredRes
     * @return
     */
    public static boolean isRelativePathsIdentical(IResource currentRes,
            IResource refactoredRes) {
        IPath currPath;
        IPath refactoredPath;

        SpecialFolder sf = SpecialFolderUtil.getRootSpecialFolder(currentRes);
        if (sf != null) {
            currPath =
                    SpecialFolderUtil.getSpecialFolderRelativePath(sf,
                            currentRes);
        } else {
            currPath = currentRes.getProjectRelativePath();
        }

        sf = SpecialFolderUtil.getRootSpecialFolder(refactoredRes);
        if (sf != null) {
            refactoredPath =
                    SpecialFolderUtil.getSpecialFolderRelativePath(sf,
                            refactoredRes);
        } else {
            refactoredPath = refactoredRes.getProjectRelativePath();
        }

        if (currPath != null && refactoredPath != null) {
            return currPath.equals(refactoredPath);
        }

        return false;
    }

    /**
     * Check if the target of this move is a folder that is a BOM special
     * folder, or contained in a BOM special folder.
     * 
     * @param target
     * @return
     */
    public boolean isTargetInBOMSpecialFolder(IResource target) {
        /*
         * Check if the target is contained in a BOM special folder
         */
        SpecialFolder sFolder = SpecialFolderUtil.getRootSpecialFolder(target);

        return sFolder != null
                && BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND.equals(sFolder
                        .getKind());
    }

    /**
     * Get the list of BOM files as they will be after the move of the given
     * folder (corresponding to the files being passed in).
     * 
     * @param oldFiles
     *            bom files affected by the move of the folder
     * @param folderBeingMoved
     *            folder being moved
     * @param targetFolder
     *            the new target of the folder
     * @return
     */
    public IFile[] getMovedBomFileHandles(IFile[] oldFiles,
            IFolder folderBeingMoved, IFolder targetFolder) {
        int count = folderBeingMoved.getFullPath().segmentCount() - 1;
        IFile[] newFiles = new IFile[oldFiles.length];

        for (int idx = 0; idx < oldFiles.length; idx++) {
            newFiles[idx] =
                    targetFolder.getFile(oldFiles[idx].getFullPath()
                            .removeFirstSegments(count));
        }

        return newFiles;
    }

}

/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.bom.resources.internal.refactor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

import com.tibco.xpd.bom.resources.internal.Messages;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;

/**
 * BOM file rename participant. This will ensure all references to the BOM (from
 * other BOMs) are updated with the new file name / path.
 * 
 * @author njpatel
 * @since 3.5.10
 */
public class BOMRenameParticipant extends RenameParticipant {

    private final BOMRefactorHelper helper;

    /**
     * BOMs affected by this rename.
     */
    private Set<BOMWorkingCopy> affectedBOMs;

    /**
     * BOM files that have been renamed
     */
    private IFile[] oldBOMFiles;

    /**
     * The BOM files as they will be after rename
     */
    private IFile[] newBOMFiles;

    /**
     * 
     */
    public BOMRenameParticipant() {
        helper = new BOMRefactorHelper();
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected boolean initialize(Object element) {
        if (element instanceof IFile) {
            oldBOMFiles = new IFile[] { (IFile) element };

            IFile newFile =
                    ((IFile) element).getParent().getFile(new Path(
                            getArguments().getNewName()));
            newBOMFiles = new IFile[] { newFile };

            affectedBOMs = helper.getAllAffectedBOMs((IFile) element);
        } else if (element instanceof IFolder) {
            try {
                List<IFile> bomFiles = helper.getBOMFiles((IFolder) element);
                oldBOMFiles = bomFiles.toArray(new IFile[bomFiles.size()]);
                newBOMFiles =
                        getNewBomFileNames(oldBOMFiles, (IFolder) element);

                if (!bomFiles.isEmpty()) {
                    affectedBOMs = new HashSet<BOMWorkingCopy>();

                    for (IFile bomFile : bomFiles) {
                        affectedBOMs.addAll(helper.getAllAffectedBOMs(bomFile));
                    }
                }

            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return affectedBOMs != null && !affectedBOMs.isEmpty()
                && oldBOMFiles.length > 0 && newBOMFiles.length > 0;
    }

    /**
     * Get the list of the BOM files as they will be after the rename of the
     * folder (corresponding to the list of files passed in).
     * 
     * @param oldFiles
     * @param currentFolder
     * @return
     */
    private IFile[] getNewBomFileNames(IFile[] oldFiles, IFolder currentFolder) {
        IFile[] newFiles = new IFile[oldFiles.length];

        int count = currentFolder.getFullPath().segmentCount();
        IFolder newFolder =
                currentFolder.getParent().getFolder(new Path(getArguments()
                        .getNewName()));
        for (int idx = 0; idx < oldFiles.length; idx++) {
            IPath relativePath =
                    oldFiles[idx].getFullPath().removeFirstSegments(count);
            newFiles[idx] = newFolder.getFile(relativePath);
        }

        return newFiles;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return Messages.BOMRenameParticipant_shortdesc;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
     * 
     * @param pm
     * @param context
     * @return
     * @throws OperationCanceledException
     */
    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {

        /*
         * Check that none of the working copies being affected are in the dirty
         * state.
         */
        return helper
                .checkWorkingCopyDirtyConditions(pm, context, affectedBOMs);
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        List<Change> changes =
                helper.createUpdateReferenceChanges(pm,
                        affectedBOMs,
                        oldBOMFiles,
                        newBOMFiles);

        return new CompositeChange(
                Messages.BOMRefactorHelper_updateReferenceInModels_change_shortdesc,
                changes.toArray(new Change[changes.size()]));
    }

}

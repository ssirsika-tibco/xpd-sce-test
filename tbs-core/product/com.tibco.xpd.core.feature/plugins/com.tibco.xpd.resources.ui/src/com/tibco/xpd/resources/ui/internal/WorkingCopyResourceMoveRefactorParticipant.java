/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.resources.ui.internal;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * This move refactor participant deals moves being performed on files that have
 * associated working copies that are dirty (i.e. have unsaved changes).
 * <p>
 * When a file with a dirty working copy is dropped we contribution a preChange
 * change that will save the working copy (this means the user will see a
 * "save changes to file %s" checkbox entry in move refactor wizard PROVIDED
 * that the drop assistant uses the move refactor wizard - unfortunately Java
 * editor contributions seem to be the drop assistant for practically everything
 * and they all work slightly differently and they don't all use the LTK
 * correctly.
 * 
 * @author aallway
 * @since 14 Mar 2012
 */
public class WorkingCopyResourceMoveRefactorParticipant extends MoveParticipant {

    private WorkingCopy workingCopy;

    private boolean doSave = false;

    /**
     * 
     */
    public WorkingCopyResourceMoveRefactorParticipant() {
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected boolean initialize(Object element) {
        /*
         * Only interested in files for which there is a working copy.
         */
        if (element instanceof IFile) {
            workingCopy = WorkingCopyUtil.getWorkingCopy((IFile) element);
            if (workingCopy != null && workingCopy.isWorkingCopyDirty()) {
                return true;
            }
        }
        workingCopy = null;

        return false;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return Messages.WorkingCopyRsourceMoveRefactorParticipant_Name_label;
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

        pm.done();
        return RefactoringStatus.create(Status.OK_STATUS);
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
        pm.done();
        return null;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createPreChange(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @Override
    public Change createPreChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        try {
            pm.beginTask("", 1); //$NON-NLS-1$
            return new SaveWorkingCopyRefactorChange(workingCopy);
        } finally {
            pm.done();
        }
    }

    private static class SaveWorkingCopyRefactorChange extends Change {
        private WorkingCopy workingCopy;

        /**
         * @param workingCopy
         */
        public SaveWorkingCopyRefactorChange(WorkingCopy workingCopy) {
            super();
            this.workingCopy = workingCopy;
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#getName()
         * 
         * @return
         */
        @Override
        public String getName() {
            return String
                    .format(Messages.WorkingCopyRsourceMoveRefactorParticipant_SaveChangesToFile_message,
                            workingCopy.getName());
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param pm
         */
        @Override
        public void initializeValidationData(IProgressMonitor pm) {
            pm.done();
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#isValid(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param pm
         * @return
         * @throws CoreException
         * @throws OperationCanceledException
         */
        @Override
        public RefactoringStatus isValid(IProgressMonitor pm)
                throws CoreException, OperationCanceledException {
            if (workingCopy.isExist()) {
                return RefactoringStatus.create(Status.OK_STATUS);
            }
            return RefactoringStatus
                    .createFatalErrorStatus(String
                            .format(Messages.WorkingCopyRsourceMoveRefactorParticipant_FileNotExist_message,
                                    workingCopy.getName()));
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#perform(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param pm
         * @return
         * @throws CoreException
         */
        @Override
        public Change perform(IProgressMonitor pm) throws CoreException {
            try {
                pm.beginTask("", 1); //$NON-NLS-1$
                pm.subTask(Messages.WorkingCopyRsourceMoveRefactorParticipant_CheckDirty_message);

                if (workingCopy.isWorkingCopyDirty()) {
                    try {
                        // Save the model
                        workingCopy.save();

                    } catch (IOException e) {
                        XpdResourcesUIActivator.getDefault().getLogger()
                                .error(e);

                        throw new CoreException(
                                new Status(
                                        IStatus.ERROR,
                                        XpdResourcesUIActivator.ID,
                                        String.format(Messages.WorkingCopyRsourceMoveRefactorParticipant_SaveError_message,
                                                workingCopy.getName())));
                    }
                }

                /*
                 * Return a NullChange() as the undo operation - if we return
                 * null itself we would be saying
                 * "you cannot undo what I have done" - this would also prevent
                 * the MOVE itself being undone. Returning NullChange() means
                 * that we simply do nothing when asked to undo our change.
                 */
                return new NullChange();

            } finally {
                pm.done();
            }
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
         * 
         * @return
         */
        @Override
        public Object getModifiedElement() {
            return WorkingCopyUtil.getFile(workingCopy.getRootElement());
        }

    }
}

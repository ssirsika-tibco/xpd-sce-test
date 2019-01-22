/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.resources.refactoring;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

/**
 * If the solution to a problem marker is to rename a file under working copy
 * control then we need to remove the invalid file markers.
 * <p>
 * This is because we don't know what the new resoruce is after the rename BUT
 * the original resoruce's markers are copied by eclipse and we can't get rid of
 * the markers on the renamed file!
 * 
 * @author aallway
 * @since 27 Jan 2012
 */
public class InvalidFileMarkerRemoverRenameParticipant extends
        RenameParticipant {

    private AbstractWorkingCopy renamedResourceWorkingCopy = null;

    public InvalidFileMarkerRemoverRenameParticipant() {
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected boolean initialize(Object element) {

        if (element instanceof IResource) {
            IResource resource = (IResource) element;

            if (resource.exists()) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(resource);

                if (wc instanceof AbstractWorkingCopy) {
                    renamedResourceWorkingCopy = (AbstractWorkingCopy) wc;
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return Messages.InvalidFileMarkerRemoverRenameParticipant_RemoveInvalidFileMarker_label;
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
        if (pm != null) {
            pm.done();
        }
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
        if (pm == null) {
            pm = new NullProgressMonitor();
        }

        try {
            return new Change() {

                @Override
                public Change perform(IProgressMonitor pm) throws CoreException {
                    if (pm == null) {
                        pm = new NullProgressMonitor();
                    }

                    try {
                        pm.beginTask(Messages.InvalidFileMarkerRemoverRenameParticipant_RemoveInvalidFileMarker_label,
                                1);

                        if (renamedResourceWorkingCopy != null) {
                            pm.subTask(Messages.InvalidFileMarkerRemoverRenameParticipant_RemoveInvalidFileMarker_label);

                            renamedResourceWorkingCopy
                                    .removeInvalidFileMarkers();
                        }
                        return new NullChange();

                    } finally {
                        pm.done();
                    }
                }

                @Override
                public RefactoringStatus isValid(IProgressMonitor pm)
                        throws CoreException, OperationCanceledException {
                    if (pm != null) {
                        pm.done();
                    }
                    return RefactoringStatus.create(Status.OK_STATUS);
                }

                @Override
                public void initializeValidationData(IProgressMonitor pm) {
                    if (pm != null) {
                        pm.done();
                    }
                }

                @Override
                public String getName() {
                    return Messages.InvalidFileMarkerRemoverRenameParticipant_RemoveInvalidFileMarker_label;
                }

                @Override
                public Object getModifiedElement() {
                    return null;
                }
            };

        } finally {
            pm.done();
        }
    }
}

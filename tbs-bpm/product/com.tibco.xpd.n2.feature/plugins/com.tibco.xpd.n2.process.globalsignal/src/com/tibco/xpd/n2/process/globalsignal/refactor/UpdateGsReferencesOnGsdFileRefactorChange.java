/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * Change that will update the Global Signal Events Signal Name with the new
 * refactored GSD file name. Supports UNDO-REDO as well.
 * 
 * @author kthombar
 * @since Jun 22, 2015
 */
public class UpdateGsReferencesOnGsdFileRefactorChange extends Change {

    private Activity globalSignalActivity;

    private String oldSignalName;

    private String newSignalName;

    private EditingDomain editingDomain;

    /**
     * 
     * @param globalSignalActivity
     *            the Global Signal Event to whose Signal name is to be updated
     * @param oldSignalName
     *            the old signal name
     * @param newSignalName
     *            the new Signal name
     * @param editingDomain
     *            the Editing Domain
     */
    UpdateGsReferencesOnGsdFileRefactorChange(Activity globalSignalActivity,
            String oldSignalName, String newSignalName,
            EditingDomain editingDomain) {
        this.globalSignalActivity = globalSignalActivity;
        this.oldSignalName = oldSignalName;
        this.newSignalName = newSignalName;
        this.editingDomain = editingDomain;

    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getName()
     * 
     * @return
     */
    @Override
    public String getName() {

        String activityDisplayName =
                Xpdl2ModelUtil.getDisplayNameOrName(globalSignalActivity);

        String projectName =
                WorkingCopyUtil.getProjectFor(globalSignalActivity).getName();

        String processName =
                Xpdl2ModelUtil.getDisplayNameOrName(globalSignalActivity
                        .getProcess());

        String xpdlFileName =
                WorkingCopyUtil.getFile(globalSignalActivity).getName();

        String activityQualifiedName =
                activityDisplayName + " ( " + projectName + "/" //$NON-NLS-1$ //$NON-NLS-2$
                        + xpdlFileName + "/" + processName + ")"; //$NON-NLS-1$ //$NON-NLS-2$

        return String
                .format(Messages.UpdateGsReferencesOnGsdFileRenameParticipant_RenameGsdChange_name,
                        activityQualifiedName);
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     */
    @Override
    public void initializeValidationData(IProgressMonitor pm) {
        // Do nothing here
    }

    private WorkingCopy getWorkingCopy() {
        return WorkingCopyUtil.getWorkingCopyFor(globalSignalActivity);
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
    public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {

        RefactoringStatus status = new RefactoringStatus();

        try {
            pm.beginTask(String
                    .format(Messages.UpdateGsReferencesOnGsdFileRenameParticipant_ValidatingChanges_msg,
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName(globalSignalActivity)),
                    1);

            Command renameCommand = getUpdateSignalNameCommand();
            /*
             * Check if the command can be executed, if not then return fatal
             * Refactoring status.
             */
            if (renameCommand == null || !renameCommand.canExecute()) {
                status.addFatalError(String
                        .format(Messages.UpdateGsReferencesOnGsdFileRenameParticipant_CannotExecuteCommandError_msg,
                                getName()));
            }
            pm.worked(1);
        } finally {
            pm.done();
        }

        return new RefactoringStatus();
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

        Command renameCommand = getUpdateSignalNameCommand();

        if (renameCommand != null && renameCommand.canExecute()) {
            WorkingCopy wc = getWorkingCopy();

            if (wc != null) {
                /*
                 * get the state of working copy before firing command
                 */
                boolean workingCopyDirty = wc.isWorkingCopyDirty();

                editingDomain.getCommandStack().execute(renameCommand);
                /*
                 * if the working copy was not dirty before refactor then save
                 * it post refactor.
                 */
                if (!workingCopyDirty) {

                    try {

                        wc.save();

                    } catch (IOException e) {

                        XpdResourcesPlugin.getDefault().getLogger().error(e);
                    }
                }

                /*
                 * Allow Undo-Redo.
                 */
                return new UpdateGsReferencesOnGsdFileRefactorChange(
                        globalSignalActivity, newSignalName, oldSignalName,
                        editingDomain);
            }
        }

        return null;
    }

    /**
     * 
     * @return the Command to update the Global Signal Name.
     */
    private Command getUpdateSignalNameCommand() {
        return EventObjectUtil.getSetSignalNameCommand(editingDomain,
                globalSignalActivity,
                newSignalName);
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
     * 
     * @return
     */
    @Override
    public Object getModifiedElement() {

        return null;
    }
}

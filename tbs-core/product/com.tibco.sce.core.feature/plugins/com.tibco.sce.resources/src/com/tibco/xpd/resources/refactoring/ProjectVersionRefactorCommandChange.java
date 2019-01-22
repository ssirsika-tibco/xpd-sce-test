/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.refactoring;

import java.io.IOException;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;

/**
 * Project lifecycle refactor change provider that extends
 * <code>ProjectVersionRefactorChange</code> to provide EMF {@link Command}
 * based processing for the change.
 * 
 * @author njpatel
 * 
 */
public abstract class ProjectVersionRefactorCommandChange extends
        ProjectVersionRefactorChange {

    private final EditingDomain editingDomain;

    private final WorkingCopy workingCopy;

    /**
     * {@link Command} based refactor change descriptor.
     * 
     * @param editingDomain
     *            model's editing domain
     * @param workingCopy
     *            model working copy that will be refactored
     * @param label
     *            name of change that will be used in the UI
     * @param changeArguments
     *            the change details
     * @param refactorHint
     */
    public ProjectVersionRefactorCommandChange(EditingDomain editingDomain,
            WorkingCopy workingCopy, String label,
            ProjectVersionRefactoringArguments changeArguments, int refactorHint) {
        super(label, changeArguments, refactorHint);
        Assert.isNotNull(workingCopy, "Working copy is null"); //$NON-NLS-1$
        Assert.isNotNull(editingDomain, "Editing domain is null"); //$NON-NLS-1$
        this.workingCopy = workingCopy;
        this.editingDomain = editingDomain;
    }

    /**
     * Get the {@link Command} to refactor this change's artifact.
     * 
     * @return
     */
    protected abstract Command getRefactorCommand();

    @Override
    public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        Command command = getRefactorCommand();
        RefactoringStatus status = new RefactoringStatus();
        final WorkingCopy wc = getWorkingCopy();
        RefactoringStatusContext context = new RefactoringStatusContext() {
            @Override
            public Object getCorrespondingElement() {
                return wc;
            }
        };

        pm
                .beginTask(String
                        .format(Messages.ProjectVersionRefactorCommandChange_validatingChanges_shortdesc,
                                getName()),
                        2);

        if (!wc.isExist()) {
            status
                    .addError(String
                            .format(Messages.ProjectVersionRefactorCommandChange_resourceNotExist_error_shortdesc,
                                    wc.getName()),
                            context);
        } else if (wc.isInvalidFile()) {
            status
                    .addWarning(String
                            .format(Messages.ProjectVersionRefactorCommandChange_NoUpdateAsInvalidContent_error_shortdesc,
                                    wc.getName()),
                            context);
        } else if (wc.isReadOnly()) {
            status
                    .addWarning(String
                            .format(Messages.ProjectVersionRefactorCommandChange_noUpdateAsReadOnly_error_shortdesc,
                                    wc.getName()),
                            context);
        }
        pm.worked(1);

        if (command == null) {
            status
                    .addError(String
                            .format(Messages.ProjectVersionRefactorCommandChange_NoCommandToRefactor_error_shortdesc,
                                    getName()),
                            context);
        } else if (!command.canExecute()) {
            status
                    .addError(String
                            .format(Messages.ProjectVersionRefactorCommandChange_UnexecutableCommand_error_shortdesc,
                                    getName()),
                            context);
        }
        pm.worked(1);

        return status;
    }

    @Override
    public Change perform(IProgressMonitor pm) throws CoreException {
        if (isValid(pm).isOK()) {
            pm
                    .beginTask(String
                            .format(Messages.ProjectVersionRefactorCommandChange_updatingResource_progress_shortdesc,
                                    getName()),
                            1);
            boolean isDirty = isResourceDirty();
            Command cmd = getRefactorCommand();
            editingDomain.getCommandStack().execute(cmd);
            isResourceDirty();
            if (!isDirty) {
                saveResource();
            }
            pm.worked(1);
        }
        return null;
    }

    /**
     * Get this change's editing domain.
     * 
     * @return
     */
    protected EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * Get this change's artifact's working copy.
     * 
     * @return
     */
    protected WorkingCopy getWorkingCopy() {
        return workingCopy;
    }

    /**
     * Check if the working copy is dirty.
     * 
     * @return
     */
    protected boolean isResourceDirty() {
        return workingCopy.isWorkingCopyDirty();
    }

    /**
     * Save the working copy (called after the refactor).
     * 
     * @throws CoreException
     */
    protected void saveResource() throws CoreException {
        try {
            workingCopy.save();
        } catch (IOException e) {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            XpdResourcesPlugin.ID_PLUGIN,
                            String
                                    .format(Messages.ProjectVersionRefactorCommandChange_failedToSave_error_shortdesc,
                                            workingCopy.getName()), e));
        }
    }
}

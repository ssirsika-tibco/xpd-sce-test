/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.refactoring;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Change for the renaming of a BOM element
 * 
 * @author mtorres
 * 
 */
public class BOMElementNameChange extends Change {

    private final EditingDomain editingDomain;

    private String oldName = null;

    private String newName = null;

    /**
     * Old display name of element.
     */
    private String oldDisplayName = null;

    /**
     * New display name of element.
     */
    private String newDisplayName = null;

    private EObject element;

    public BOMElementNameChange(EditingDomain editingDomain, String oldName,
            String newName, EObject element) {
        this.editingDomain = editingDomain;
        this.oldName = oldName;
        this.newName = newName;
        this.element = element;
    }

    /**
     * Change for the renaming of a BOM element which has a display name too.
     * 
     * @param editingDomain
     * @param oldName
     * @param newName
     * @param oldDisplayName
     * @param newDisplayName
     * @param element
     */
    public BOMElementNameChange(EditingDomain editingDomain, String oldName,
            String newName, String oldDisplayName, String newDisplayName,
            EObject element) {

        this.editingDomain = editingDomain;
        this.oldName = oldName;
        this.newName = newName;
        this.oldDisplayName = oldDisplayName;
        this.newDisplayName = newDisplayName;
        this.element = element;
    }

    @Override
    public Object getModifiedElement() {
        return element;
    }

    @Override
    public String getName() {

        if (oldName.equals(newName)) {

            String message = Messages.BOMElementNameChange_ChangeLabel;
            return String.format(message, oldDisplayName, newDisplayName);

        } else {

            String message = Messages.BOMElementNameChange_Rename1;
            return String.format(message,
                    oldName,
                    newName,
                    oldDisplayName,
                    newDisplayName);
        }
    }

    @Override
    public void initializeValidationData(IProgressMonitor pm) {
    }

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

        pm.beginTask(String
                .format(Messages.BOMElementNameChange_ValidatingChange,
                        getName()), 2);

        if (!wc.isExist()) {
            status.addError(String
                    .format(Messages.BOMElementNameChange_ResourceDoesNotExist,
                            wc.getName()), context);
        } else if (wc.isInvalidFile()) {
            status.addWarning(String
                    .format(Messages.BOMElementNameChange_InvalidContent,
                            wc.getName()), context);
        } else if (wc.isReadOnly()) {
            status.addWarning(String
                    .format(Messages.BOMElementNameChange_ReadOnly,
                            wc.getName()), context);
        }
        pm.worked(1);

        if (command == null) {
            status.addError(String
                    .format(Messages.BOMElementNameChange_NoCommand, getName()),
                    context);
        } else if (!command.canExecute()) {
            status.addError(String
                    .format(Messages.BOMElementNameChange_NoExecutableCommand,
                            getName()), context);
        }
        pm.worked(1);

        return status;
    }

    protected Command getRefactorCommand() {
        if (element != null && newName != null) {

            CompoundCommand cmpCmd = new CompoundCommand();

            cmpCmd.append(SetCommand.create(editingDomain,
                    element,
                    UMLPackage.eINSTANCE.getNamedElement_Name(),
                    newName));

            Command cmd =
                    new RecordingCommand(
                            (TransactionalEditingDomain) editingDomain) {
                        @Override
                        protected void doExecute() {
                            PrimitivesUtil
                                    .setDisplayLabel((NamedElement) element,
                                            newDisplayName,
                                            false);
                        }
                    };

            cmpCmd.append(cmd);

            return cmpCmd;
        }
        return null;
    }

    private WorkingCopy getWorkingCopy() {
        return WorkingCopyUtil.getWorkingCopyFor(element);
    }

    @Override
    public Change perform(IProgressMonitor pm) throws CoreException {
        if (isValid(pm).isOK()) {
            pm.beginTask(Messages.BOMElementNameChange_RenamingElement, 1);
            Command cmd = getRefactorCommand();
            if (cmd != null) {
                WorkingCopy wc = getWorkingCopy();
                if (wc != null) {
                    boolean workingCopyDirty = wc.isWorkingCopyDirty();
                    editingDomain.getCommandStack().execute(cmd);
                    if (!workingCopyDirty) {
                        try {
                            wc.save();
                        } catch (IOException e) {
                            XpdResourcesPlugin.getDefault().getLogger()
                                    .error(e);
                        }
                    }
                }
            }
            pm.worked(1);
        }
        return null;
    }

}
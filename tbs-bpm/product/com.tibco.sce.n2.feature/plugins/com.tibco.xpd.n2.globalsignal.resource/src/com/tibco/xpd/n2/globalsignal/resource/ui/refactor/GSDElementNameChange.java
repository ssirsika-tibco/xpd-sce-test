/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui.refactor;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Change for the renaming of a GSD element (Global signal or Payload Data).
 * 
 * @author sajain
 * @since May 11, 2015
 */
public class GSDElementNameChange extends Change {

    /**
     * Editing domain.
     */
    private final EditingDomain editingDomain;

    /**
     * Old name of element.
     */
    private String oldName = null;

    /**
     * New name of element.
     */
    private String newName = null;

    /**
     * Old display name of element.
     */
    private String oldDisplayName = null;

    /**
     * New display name of element.
     */
    private String newDisplayName = null;

    /**
     * Element being renamed.
     */
    private EObject element;

    /**
     * Change for the renaming of a GSD element (Global signal or Payload Data).
     * 
     * @param editingDomain
     * @param oldName
     * @param newName
     * @param element
     */
    public GSDElementNameChange(EditingDomain editingDomain, String oldName,
            String newName, EObject element) {

        this.editingDomain = editingDomain;
        this.oldName = oldName;
        this.newName = newName;
        this.element = element;
    }

    /**
     * Change for the renaming of a GSD element which has a display name too
     * (e.g. Payload data).
     * 
     * @param editingDomain
     * @param oldName
     * @param newName
     * @param oldDisplayName
     * @param newDisplayName
     * @param element
     */
    public GSDElementNameChange(EditingDomain editingDomain, String oldName,
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

            String message = Messages.GSDElementNameChange_ChangeLabel;
            return String.format(message, oldDisplayName, newDisplayName);

        } else {

            String message = Messages.GSDElementNameChange_Rename;
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
                .format(Messages.GSDElementNameChange_ValidatingChange,
                        getName()), 2);

        if (!wc.isExist()) {

            status.addError(String
                    .format(Messages.GSDElementNameChange_ResourceDoesNotExist,
                            wc.getName()), context);

        } else if (wc.isInvalidFile()) {

            status.addWarning(String
                    .format(Messages.GSDElementNameChange_InvalidContent,
                            wc.getName()), context);

        } else if (wc.isReadOnly()) {

            status.addWarning(String
                    .format(Messages.GSDElementNameChange_ReadOnly,
                            wc.getName()), context);
        }

        pm.worked(1);

        if (command == null) {

            status.addError(String
                    .format(Messages.GSDElementNameChange_NoCommand, getName()),
                    context);

        } else if (!command.canExecute()) {

            status.addError(String
                    .format(Messages.GSDElementNameChange_NoExecutableCommand,
                            getName()), context);

        }

        pm.worked(1);

        return status;
    }

    /**
     * Get command to refactor the GSD element.
     * 
     * @return Command to refactor the GSD element.
     */
    protected Command getRefactorCommand() {

        if (element != null && newName != null) {

            if (element instanceof GlobalSignal) {

                return SetCommand.create(editingDomain,
                        element,
                        GlobalSignalDefinitionPackage.eINSTANCE
                                .getGlobalSignal_Name(),
                        newName);

            } else if (element instanceof PayloadDataField) {

                CompoundCommand cmpCmd = new CompoundCommand();

                cmpCmd.append(SetCommand.create(editingDomain,
                        element,
                        Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                        newName));

                cmpCmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                (PayloadDataField) element,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName(),
                                newDisplayName));

                return cmpCmd;
            }
        }

        return null;
    }

    /**
     * Get working copy of the GSD element
     * 
     * @return Working copy of the GSD element
     */
    private WorkingCopy getWorkingCopy() {

        return WorkingCopyUtil.getWorkingCopyFor(element);
    }

    @Override
    public Change perform(IProgressMonitor pm) throws CoreException {

        if (isValid(pm).isOK()) {

            pm.beginTask(Messages.GSDElementNameChange_RenamingElement, 1);

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

                            GsdResourcePlugin.getDefault().getLogger().error(e);
                        }
                    }
                }
            }

            pm.worked(1);
        }
        return null;
    }

}
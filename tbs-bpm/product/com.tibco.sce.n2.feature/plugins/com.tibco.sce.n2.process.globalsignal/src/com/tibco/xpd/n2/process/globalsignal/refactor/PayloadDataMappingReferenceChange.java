/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.refactoring.PreviewDescription;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Class to track the change in the event activity mappings references to
 * payload data.
 * 
 * @author sajain
 * @since May 18, 2015
 */
public class PayloadDataMappingReferenceChange extends Change {

    /**
     * Old name of payload data.
     */
    private String oldPayloadName;

    /**
     * New name of payload data.
     */
    private static String newPayloadName;

    /**
     * Rename arguments.
     */
    private RenameArguments args = null;

    /**
     * GSD element being renamed.
     */
    private EObject element;

    /**
     * Mapping referencing payload data.
     */
    private DataMapping mapping;

    /**
     * Editing domain.
     */
    private EditingDomain editingDomain;

    /**
     * Class to provide the change that has occured in mapping to payload
     * reference.
     * 
     * @param oldPayloadName
     * @param args
     * @param element
     * @param mapping
     * @param editingDomain
     */
    public PayloadDataMappingReferenceChange(String oldPayloadName,
            RenameArguments args, EObject element, DataMapping mapping,
            EditingDomain editingDomain) {

        this.oldPayloadName = oldPayloadName;
        this.args = args;
        this.element = element;
        this.mapping = mapping;
        this.editingDomain = editingDomain;
        PayloadDataMappingReferenceChange.newPayloadName = modifyPayloadName();
    }

    @Override
    public Object getModifiedElement() {
        return element;
    }

    @Override
    public String getName() {

        return Messages.PayloadDataMappingReferenceChange_Name;
    }

    @Override
    public void initializeValidationData(IProgressMonitor pm) {

        // Nothing to do here.
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
                .format(Messages.GSDReferenceChange_ValidatingChange, getName()),
                2);

        if (!wc.isExist()) {

            status.addError(String
                    .format(Messages.GSDReferenceChange_ResourceDoesnotExist,
                            wc.getName()), context);

        } else if (wc.isInvalidFile()) {

            status.addWarning(String
                    .format(Messages.GSDReferenceChange_CannotUpdate,
                            wc.getName()), context);

        } else if (wc.isReadOnly()) {

            status.addWarning(String
                    .format(Messages.GSDReferenceChange_ReadOnly, wc.getName()),
                    context);

        }
        pm.worked(1);

        if (command == null) {

            status.addError(String
                    .format(Messages.GSDReferenceChange_NoCommandFound,
                            getName()), context);

        } else if (!command.canExecute()) {

            status.addError(String
                    .format(Messages.GSDReferenceChange_NoCommandSet, getName()),
                    context);

        }
        pm.worked(1);

        return status;
    }

    /**
     * Get working copy.
     * 
     * @return Working copy.
     */
    private WorkingCopy getWorkingCopy() {

        return WorkingCopyUtil.getWorkingCopyFor(mapping);
    }

    @Override
    public Change perform(IProgressMonitor pm) throws CoreException {

        if (isValid(pm).isOK()) {

            pm.beginTask(String
                    .format(Messages.PayloadDataMappingReferenceChange_ProgressMonitor_Label,
                            getName()),
                    1);

            Command cmd = getRefactorCommand();

            if (cmd != null && cmd.canExecute()) {

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

    /**
     * Return <code>true</code> if there are modifications in the payload name
     * i.e., the old and new names are different, <code>false</code> otherwise.
     * 
     * @return <code>true</code> if there are modifications in the payload name
     *         i.e., the old and new names are different, <code>false</code>
     *         otherwise.
     */
    public boolean containsModifications() {

        if (oldPayloadName != null && newPayloadName != null
                && !oldPayloadName.equals(newPayloadName)) {

            return true;

        }

        return false;
    }

    /**
     * Get current name of payload.
     * 
     * @return Current name of payload.
     */
    public PreviewDescription getCurrentValue() {
        return new PreviewDescription(Messages.GSDReferenceChange_CurrentValue,
                oldPayloadName);
    }

    /**
     * Get refactored name of payload.
     * 
     * @return Refactored name of payload.
     */
    public PreviewDescription getRefactoredValue() {
        return new PreviewDescription(
                Messages.GSDReferenceChange_RefactoredValue, newPayloadName);
    }

    /**
     * Perform the modifications in script according to the element name change.
     * 
     * @return Modified script.
     */
    private String modifyPayloadName() {

        return args.getNewName();
    }

    /**
     * Get command to refactor payload mapping.
     * 
     * @return Command to refactor payload mapping.
     */
    protected Command getRefactorCommand() {

        if (mapping != null && oldPayloadName.equals(mapping.getFormal())) {

            return SetCommand.create(editingDomain,
                    mapping,
                    Xpdl2Package.eINSTANCE.getDataMapping_Formal(),
                    newPayloadName);

        }

        return null;
    }

    /**
     * Get editing domain.
     * 
     * @return Editing domain.
     */
    protected EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * Get new name of payload.
     * 
     * @return New name of payload.
     */
    protected String getNewPayloadName() {
        return newPayloadName;
    }

    /**
     * Get mapping referencing the payload.
     * 
     * @return Mapping referencing the payload.
     */
    protected EObject getMapping() {
        return mapping;
    }

    /**
     * Get containing process.
     * 
     * @return Containing process.
     */
    protected Process getProcess() {

        if (getMapping() != null) {

            return Xpdl2ModelUtil.getProcess(getMapping());

        }

        return null;
    }
}

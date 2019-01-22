/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui.refactor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.refactor.XPDRenameArguments;

/**
 * Refactor participant for GSD elements (Global signal and payload data).
 * 
 * @author sajain
 * @since May 12, 2015
 */
public class GSDElementRefactorParticipant extends RenameParticipant {

    /**
     * GSD element to be refactored.
     */
    private EObject element = null;

    /**
     * Refactor participant for GSD elements (Global signal and payload data).
     */
    public GSDElementRefactorParticipant() {
    }

    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {

        RefactoringStatus status = new RefactoringStatus();

        return status;
    }

    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        if (element instanceof GlobalSignal) {

            String oldName = ((GlobalSignal) element).getName();

            return new GSDElementNameChange(ed, oldName, getArguments()
                    .getNewName(), element);

        } else if (element instanceof PayloadDataField) {

            String oldName = ((PayloadDataField) element).getName();

            RenameArguments renameArgs = getArguments();

            if (renameArgs instanceof XPDRenameArguments) {

                XPDRenameArguments xpdRenameArgs =
                        (XPDRenameArguments) renameArgs;

                return new GSDElementNameChange(ed, oldName,
                        xpdRenameArgs.getNewName(),
                        xpdRenameArgs.getOldDisplayName(),
                        xpdRenameArgs.getNewDisplayName(), element);

            }
        }

        return new NullChange(
                Messages.GSDElementRefactorParticipant_NoElementsAffected);
    }

    @Override
    public String getName() {

        if (element instanceof GlobalSignal) {

            return ((GlobalSignal) element).getName();

        } else if (element instanceof PayloadDataField) {

            return ((PayloadDataField) element).getName();

        }

        return null;
    }

    @Override
    protected boolean initialize(Object element) {

        if (element instanceof EObject) {

            this.element = (EObject) element;

            return true;
        }

        return false;
    }

}

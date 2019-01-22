/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.refactoring;

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
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.refactor.XPDRenameArguments;

/**
 * 
 * @author mtorres
 * 
 */
public class BOMElementRefactorParticipant extends RenameParticipant {

    private EObject element = null;

    public BOMElementRefactorParticipant() {
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
        if (element instanceof NamedElement) {
            String oldName = ((NamedElement) element).getName();

            RenameArguments renameArgs = getArguments();

            if (renameArgs instanceof XPDRenameArguments) {

                XPDRenameArguments xpdRenameArgs =
                        (XPDRenameArguments) renameArgs;

                return new BOMElementNameChange(ed, oldName,
                        xpdRenameArgs.getNewName(),
                        xpdRenameArgs.getOldDisplayName(),
                        xpdRenameArgs.getNewDisplayName(), element);

            }
        }
        return new NullChange(
                Messages.BOMElementRefactorParticipant_NoElementsAffected);
    }

    @Override
    public String getName() {
        if (element instanceof NamedElement) {
            return ((NamedElement) element).getName();
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

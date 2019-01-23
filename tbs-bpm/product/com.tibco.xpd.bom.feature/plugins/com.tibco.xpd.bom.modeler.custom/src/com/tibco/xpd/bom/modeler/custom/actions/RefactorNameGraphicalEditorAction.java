/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.resources.ui.refactoring.RenameBOMElementWizard;

/**
 * Process Package Action for the refactoring of the name of a BOM element
 * 
 * @author mtorres
 * 
 */
public class RefactorNameGraphicalEditorAction implements IObjectActionDelegate {

    private Shell shell;

    private EditPart element;

    public RefactorNameGraphicalEditorAction() {
    }

    @Override
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        shell = targetPart.getSite().getShell();
    }

    @Override
    public void run(IAction action) {
        if (shell != null) {
            NamedElement namedElement = null;
            if (element instanceof GraphicalEditPart) {
                EObject resolveSemanticElement =
                        ((GraphicalEditPart) element).resolveSemanticElement();
                if (resolveSemanticElement instanceof NamedElement) {
                    namedElement = (NamedElement) resolveSemanticElement;
                }
            }
            if (namedElement != null) {
                RenameBOMElementWizard refactoringWizard =
                        new RenameBOMElementWizard(namedElement);
                RefactoringWizardOpenOperation op =
                        new RefactoringWizardOpenOperation(refactoringWizard);
                try {
                    op.run(shell,
                            Messages.RefactorNameDiagramAction_RenameElement);
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
        }
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        boolean enabled = false;
        element = null;
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sSel = (IStructuredSelection) selection;
            if (sSel.size() == 1 && sSel.getFirstElement() instanceof EditPart) {
                element = (EditPart) sSel.getFirstElement();
                enabled = true;
            }
        }
        action.setEnabled(enabled);
    }

}

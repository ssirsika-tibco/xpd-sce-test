/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import org.eclipse.emf.edit.ui.action.LoadResourceAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;

/**
 * @generated
 */
public class UMLLoadResourceAction implements IObjectActionDelegate {

    /**
     * @generated
     */
    private CanvasPackageEditPart mySelectedElement;

    /**
     * @generated
     */
    private Shell myShell;

    /**
     * @generated
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        myShell = targetPart.getSite().getShell();
    }

    /**
     * @generated
     */
    public void run(IAction action) {
        LoadResourceAction.LoadResourceDialog loadResourceDialog = new LoadResourceAction.LoadResourceDialog(
                myShell, mySelectedElement.getEditingDomain());
        loadResourceDialog.open();
    }

    /**
     * @generated
     */
    public void selectionChanged(IAction action, ISelection selection) {
        mySelectedElement = null;
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;
            if (structuredSelection.size() == 1
                    && structuredSelection.getFirstElement() instanceof CanvasPackageEditPart) {
                mySelectedElement = (CanvasPackageEditPart) structuredSelection
                        .getFirstElement();
            }
        }
        action.setEnabled(isEnabled());
    }

    /**
     * @generated
     */
    private boolean isEnabled() {
        return mySelectedElement != null;
    }

}

/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.popup.actions;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author wzurek
 */
public class DirectEditAction implements IActionDelegate {

    private EditPart editPart;

    /**
     * Constructor for DirectEditAction.
     */
    public DirectEditAction() {
        super();
    }

    /*
     * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

    /*
     * @see IActionDelegate#run(IAction)
     */
    public void run(IAction action) {
        if (editPart != null) {
            editPart.performRequest(new DirectEditRequest());
        }
    }

    /*
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() == 1) {
                Object obj = sel.getFirstElement();
                if (obj instanceof EditPart) {
                    editPart = (EditPart) obj;
                    action.setEnabled(true);
                    return;
                }
            }
        }
        editPart = null;
        action.setEnabled(false);
    }

}

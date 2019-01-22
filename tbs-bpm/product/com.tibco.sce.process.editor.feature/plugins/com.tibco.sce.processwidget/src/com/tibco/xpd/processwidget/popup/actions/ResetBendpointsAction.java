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

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processwidget.adapters.BaseConnectionAdapter;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;

/**
 * @author wzurek
 */
public class ResetBendpointsAction implements IActionDelegate {

    private BaseConnectionAdapter connAdapter;

    /**
     * Constructor for ResetBendpointsAction.
     */
    public ResetBendpointsAction() {
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
        if (connAdapter != null) {
            int size = connAdapter.getBendpoints().size();
            EObject eo = (EObject) connAdapter.getTarget();
            IEditingDomainProvider ep = (IEditingDomainProvider) EcoreUtil
                    .getExistingAdapter(eo.eResource(),
                            IEditingDomainProvider.class);
            EditingDomain ed = ep.getEditingDomain();

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.ResetBendpointsAction_menu);
            for (int i = 0; i < size; i++) {
                cmd.append(connAdapter.getDeleteBendpointCommand(ed, i));
            }
            ed.getCommandStack().execute(cmd);
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
                if (obj instanceof BaseConnectionEditPart) {
                    connAdapter = (BaseConnectionAdapter) ((BaseConnectionEditPart) obj)
                            .getModelAdapter();
                    action.setEnabled(true);
                    return;
                }
            }
        }
        connAdapter = null;
        action.setEnabled(false);
    }

}

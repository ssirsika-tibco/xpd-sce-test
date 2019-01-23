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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.commands.ChangeSeqFlowTypeCommand;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;

/**
 * @author wzurek
 */
public class SetSequenceFlowTypeAction implements IActionDelegate {

    private final SequenceFlowType type;

    private SequenceFlowAdapter connAdapter;

    private SequenceFlowEditPart seqFlowEditPart = null;

    private EditPartViewer viewer;

    /**
     * Constructor for BaseSetFlowConnTypeAction.
     */
    public SetSequenceFlowTypeAction(SequenceFlowType type) {
        super();
        this.type = type;
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
        if (connAdapter != null && seqFlowEditPart != null) {
            SequenceFlowType tt = connAdapter.getFlowType();
            if (tt.equals(type)) {
                return;
            }

            EObject eo = (EObject) connAdapter.getTarget();
            IEditingDomainProvider ep =
                    (IEditingDomainProvider) EcoreUtil.getExistingAdapter(eo
                            .eResource(), IEditingDomainProvider.class);

            Command cmd =
                    ChangeSeqFlowTypeCommand.create(ep.getEditingDomain(),
                            (EObject)seqFlowEditPart.getModel(),
                            type);

            ep.getEditingDomain().getCommandStack().execute(cmd);
            
            //
            // Sid:
            // There can be a problem (not of our making) with
            // property sheets for the selected object (say a task).
            //
            // If there are different properties tabs for the new
            // task type then the General Tab normally detects the
            // change in task type and performs a refreshTabs().
            //
            // However if the last tab selected for a task was NOT
            // general and the task is deselected and reselected
            // then the general section is not necessarily reloaded.
            //
            // This means that when we change the typ[e the tabs
            // don't get changed. Therefore in order to force a
            // refresh we will perform force a reselect of the edipt
            // part which should filter up thru the system.
            
            // In order for selection change not to be ignored we
            // have to unset and reset the selection.
            viewer.getSelectionManager()
                    .setSelection(new StructuredSelection());
            viewer.getSelectionManager()
                    .setSelection(new StructuredSelection(seqFlowEditPart));
            
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
                if (obj instanceof SequenceFlowEditPart) {
                    viewer = ((EditPart) obj).getViewer();

                    seqFlowEditPart = (SequenceFlowEditPart) obj;

                    connAdapter =
                            (SequenceFlowAdapter) seqFlowEditPart
                                    .getModelAdapter();
                    SequenceFlowType tt = connAdapter.getFlowType();
                    action.setEnabled(true);
                    action.setChecked(tt.equals(type));
                    return;
                }
            }
        }
        connAdapter = null;
        seqFlowEditPart = null;
        action.setEnabled(false);
        action.setChecked(false);
    }

    /**
     * ================================================================
     * <p>
     * Set to specific task type actions...
     * </p>
     * ================================================================
     */

    public static class Uncontrolled extends SetSequenceFlowTypeAction {
        public Uncontrolled() {
            super(SequenceFlowType.UNCONTROLLED_LITERAL);
        }
    }

    public static class Conditional extends SetSequenceFlowTypeAction {
        public Conditional() {
            super(SequenceFlowType.CONDITIONAL_LITERAL);
        }
    }

    public static class Default extends SetSequenceFlowTypeAction {
        public Default() {
            super(SequenceFlowType.DEFAULT_LITERAL);
        }
    }

}

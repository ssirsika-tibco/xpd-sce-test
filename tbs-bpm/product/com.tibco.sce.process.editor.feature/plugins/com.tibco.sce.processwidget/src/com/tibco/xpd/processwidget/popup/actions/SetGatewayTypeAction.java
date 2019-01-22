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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processwidget.adapters.GatewayAdapter;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.commands.ChangeGatewayTypeCommand;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.GatewayEditPart;

/**
 * 
 * 
 * @author wzurek
 */
public class SetGatewayTypeAction implements IActionDelegate {

    private final GatewayType gatewayType;

    private List gatewayAdapters;

    private List<EditPart> editParts;

    private EditPartViewer viewer;

    /**
     * Use one the static subclasses for specific type
     */
    private SetGatewayTypeAction(GatewayType type) {
        super();
        this.gatewayType = type;
    }

    /*
     * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        // do nothing
    }

    /*
     * @see IActionDelegate#run(IAction)
     */
    public void run(IAction action) {
        if (gatewayAdapters != null) {
            GatewayType newGatewayType;
            if (action.isChecked()) {
                newGatewayType = gatewayType;
            } else {
                newGatewayType = GatewayType.EXCLUSIVE_DATA_LITERAL;
            }

            if (gatewayAdapters.size() > 0) {
                EObject eo =
                        (EObject) ((GatewayAdapter) gatewayAdapters.get(0))
                                .getTarget();
                IEditingDomainProvider ep =
                        (IEditingDomainProvider) EcoreUtil
                                .getExistingAdapter(eo.eResource(),
                                        IEditingDomainProvider.class);
                EditingDomain ed = ep.getEditingDomain();
                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.SetGatewayTypeAction_menu);

                for (Iterator iter = gatewayAdapters.iterator(); iter.hasNext();) {
                    GatewayAdapter gatewayAdapter =
                            (GatewayAdapter) iter.next();

                    cmd.append(ChangeGatewayTypeCommand.create(ed,
                            (EObject) gatewayAdapter.getTarget(),
                            newGatewayType));
                }

                ed.getCommandStack().execute(cmd);
                
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
                        .setSelection(new StructuredSelection(editParts
                                .toArray()));
            }
        }
    }

    /*
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() > 0) {
                gatewayAdapters = new ArrayList();
                editParts = new ArrayList<EditPart>();

                GatewayType tt = null;

                for (Iterator iter = sel.iterator(); iter.hasNext();) {
                    Object obj = iter.next();
                    if (obj instanceof GatewayEditPart) {
                        viewer = ((EditPart) obj).getViewer();

                        GatewayAdapter adapter =
                                (GatewayAdapter) ((GatewayEditPart) obj)
                                        .getModelAdapter();

                        tt = adapter.getGatewayType();

                        if (!gatewayType.equals(tt)) {
                            gatewayAdapters.add(adapter);
                            editParts.add((EditPart) obj);
                        }

                    } else {
                        gatewayAdapters = null;
                        action.setEnabled(false);
                        action.setChecked(false);
                        return;
                    }
                }

                action.setEnabled(true);

                // If there are multiple selections then don't check any.
                if (sel.size() > 1) {
                    action.setChecked(false);
                } else if (tt != null) {
                    action.setChecked(gatewayType.equals(tt));
                }
                return;
            }
        }
        action.setEnabled(false);
        action.setChecked(false);
    }

    /**
     * ================================================================
     * <p>
     * Set to specific gateway type actions...
     * </p>
     * ================================================================
     */

    public static class XORDataGateway extends SetGatewayTypeAction {
        public XORDataGateway() {
            super(GatewayType.EXCLUSIVE_DATA_LITERAL);
        }
    }

    public static class XOREventGateway extends SetGatewayTypeAction {
        public XOREventGateway() {
            super(GatewayType.EXLCUSIVE_EVENT_LITERAL);
        }
    }

    public static class ParallelGateway extends SetGatewayTypeAction {
        public ParallelGateway() {
            super(GatewayType.PARALLEL_LITERAL);
        }
    }

    public static class ORGateway extends SetGatewayTypeAction {
        public ORGateway() {
            super(GatewayType.INCLUSIVE_LITERAL);
        }
    }

    public static class ComplexGateway extends SetGatewayTypeAction {
        public ComplexGateway() {
            super(GatewayType.COMPLEX_LITERAL);
        }
    }

}

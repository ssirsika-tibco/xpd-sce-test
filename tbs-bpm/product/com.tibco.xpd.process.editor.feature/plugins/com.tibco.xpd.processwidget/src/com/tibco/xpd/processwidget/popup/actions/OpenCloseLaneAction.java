/**
 * OpenCloseLaneAction.java
 *
 * Action that toggles close state of a pool.
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.popup.actions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.parts.LaneEditPart;

public class OpenCloseLaneAction implements IActionDelegate {

    private LaneEditPart laneEP;

    /**
     * Action that toggles close state of a pool.
     */
    public OpenCloseLaneAction() {
    }

    public void run(IAction action) {
        if (laneEP != null) {
            LaneAdapter la = (LaneAdapter) laneEP.getModelAdapter();

            boolean isClosed = la.isClosed();

            IEditingDomainProvider edp = (IEditingDomainProvider) EcoreUtil
                    .getExistingAdapter(((EObject) laneEP.getModel())
                            .eResource(), IEditingDomainProvider.class);

            Command cmd = la.getSetIsClosedCommand(edp.getEditingDomain(),
                    !isClosed);
            edp.getEditingDomain().getCommandStack().execute(cmd);
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        if (!selection.isEmpty()) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() > 1) {
                action.setEnabled(false);
                action.setChecked(false);
                laneEP = null;
            } else {
                laneEP = (LaneEditPart) sel.getFirstElement();
                LaneAdapter la = (LaneAdapter) laneEP.getModelAdapter();
                action.setChecked(la.isClosed());
                action.setEnabled(true);
            }
        }
    }
}

/**
 * OpenClosePoolAction.java
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

import com.tibco.xpd.processwidget.adapters.PoolAdapter;
import com.tibco.xpd.processwidget.parts.PoolEditPart;

public class OpenClosePoolAction implements IActionDelegate {

    private PoolEditPart poolEP;

    /**
     * Action that toggles closed state of a pool.
     *
     */
    public OpenClosePoolAction() {
    }

    public void run(IAction action) {
        if (poolEP != null) {
            PoolAdapter pa = (PoolAdapter)poolEP.getModelAdapter();
            
            boolean isClosed = pa.isClosed();
            
            IEditingDomainProvider edp = (IEditingDomainProvider) EcoreUtil
                    .getExistingAdapter(((EObject) poolEP.getModel())
                            .eResource(), IEditingDomainProvider.class);
            
            Command cmd = pa.getSetIsClosedCommand(edp.getEditingDomain(),
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
                poolEP = null;
            } else {
                poolEP = (PoolEditPart) sel.getFirstElement();
                PoolAdapter pa = (PoolAdapter) poolEP.getModelAdapter();
                action.setChecked(pa.isClosed());
                action.setEnabled(true);
            }
        }
    }
}

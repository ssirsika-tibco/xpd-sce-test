/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.popup.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;

/**
 * @author bharge
 * 
 */
public class RetainFamiliarAction implements IActionDelegate {

    private ISelection selection;

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     * 
     * @param action
     */
    public void run(IAction action) {
        if (selection instanceof StructuredSelection) {
            StructuredSelection structuredSelection =
                    (StructuredSelection) selection;

            List<BaseFlowNodeEditPart> editParts =
                    new ArrayList<BaseFlowNodeEditPart>();
            EditingDomain ed = null;
            ProcessEditPart processEditPart = null;

            for (Object next : structuredSelection.toArray()) {
                if (next instanceof BaseFlowNodeEditPart) {
                    BaseFlowNodeEditPart baseFlowNodeEditPart =
                            (BaseFlowNodeEditPart) next;
                    if (ed == null) {
                        ed = baseFlowNodeEditPart.getEditingDomain();
                    }
                    if (processEditPart == null) {
                        processEditPart =
                                baseFlowNodeEditPart.getParentProcess();
                    }
                    editParts.add(baseFlowNodeEditPart);
                }
            }

            if (null != ed && null != processEditPart) {
                ProcessDiagramAdapter procDiagramAdapter =
                        (ProcessDiagramAdapter) processEditPart
                                .getAdapterFactory().adapt(processEditPart
                                        .getModel(),
                                        ProcessWidgetConstants.ADAPTER_TYPE);
                if (null != procDiagramAdapter && editParts.size() > 0) {
                    Command command =
                            procDiagramAdapter.getRetainFamiliarCommand(ed,
                                    editParts);
                    if (null != command && command.canExecute()) {
                        ed.getCommandStack().execute(command);
                    }
                }
            }
        }
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param action
     * @param selection
     */
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }

}

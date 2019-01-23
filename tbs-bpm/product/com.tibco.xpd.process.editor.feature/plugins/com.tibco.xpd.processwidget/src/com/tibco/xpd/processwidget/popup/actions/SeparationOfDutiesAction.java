/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;

/**
 * @author nwilson
 */
public class SeparationOfDutiesAction implements IActionDelegate {

    private ISelection selection;

    /**
     * @param action
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        if (selection instanceof StructuredSelection) {
            StructuredSelection structured = (StructuredSelection) selection;
            List<BaseFlowNodeEditPart> parts =
                    new ArrayList<BaseFlowNodeEditPart>();
            EditingDomain ed = null;
            ProcessEditPart process = null;
            for (Object next : structured.toArray()) {
                if (next instanceof BaseFlowNodeEditPart) {
                    BaseFlowNodeEditPart part = (BaseFlowNodeEditPart) next;
                    if (ed == null) {
                        ed = part.getEditingDomain();
                    }
                    if (process == null) {
                        process = part.getParentProcess();
                    }
                    parts.add(part);
                }
            }
            if (ed != null && process != null) {
                ProcessDiagramAdapter processAdapter =
                        (ProcessDiagramAdapter) process.getAdapterFactory()
                                .adapt(process.getModel(),
                                        ProcessWidgetConstants.ADAPTER_TYPE);
                if (processAdapter != null && parts.size() > 0) {
                    Command cmd =
                            processAdapter.getSeparationOfDutiesCommand(ed,
                                    parts);
                    if (cmd != null && cmd.canExecute()) {
                        ed.getCommandStack().execute(cmd);
                    }
                }
            }
        }
    }

    /**
     * @param action
     * @param selection
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(
     *      org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }

}

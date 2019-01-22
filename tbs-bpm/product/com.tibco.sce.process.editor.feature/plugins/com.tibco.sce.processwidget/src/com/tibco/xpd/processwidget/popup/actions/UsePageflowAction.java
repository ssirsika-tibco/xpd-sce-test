/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * @author NWilson
 * 
 */
public class UsePageflowAction implements IObjectActionDelegate {

    private TaskEditPart part;

    /**
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
     *      org.eclipse.ui.IWorkbenchPart)
     * 
     * @param action
     * @param targetPart
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     * 
     * @param action
     */
    public void run(IAction action) {
        BaseProcessAdapter adapter = part.getModelAdapter();
        if (adapter instanceof TaskAdapter) {
            ((TaskAdapter) adapter).useExistingPageflow();
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
        if (selection instanceof StructuredSelection) {
            StructuredSelection structured = (StructuredSelection) selection;
            if (structured.size() == 1) {
                Object item = structured.getFirstElement();
                if (item instanceof TaskEditPart) {
                    part = (TaskEditPart) item;
                }
            }
        }
    }

}

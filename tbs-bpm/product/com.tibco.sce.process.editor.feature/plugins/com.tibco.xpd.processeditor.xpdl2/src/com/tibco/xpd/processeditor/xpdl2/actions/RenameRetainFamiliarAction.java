/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.tibco.xpd.processeditor.xpdl2.util.RetainFamiliarCommandActions;

/**
 * @author bharge
 * 
 */
public class RenameRetainFamiliarAction implements IViewActionDelegate {
    /** The selection. */
    private ISelection selection;

    private Control control;

    /**
     * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
     * 
     * @param view
     */
    public void init(IViewPart view) {
        // do nothing
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     * 
     * @param action
     */
    public void run(IAction action) {
        if (selection instanceof StructuredSelection) {
            RetainFamiliarCommandActions retainFamiliarCommandActions =
                    new RetainFamiliarCommandActions();
            retainFamiliarCommandActions.renameTaskGroup(selection, control);
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
        control = Display.getDefault().getFocusControl();
    }

}

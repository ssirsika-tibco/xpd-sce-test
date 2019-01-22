/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.decisions.actions;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Base class for actions on decision service tasks.
 * 
 * @author aallway
 * @since 11 Aug 2011
 */
public abstract class AbstractDecisionServiceTaskAction implements
        IObjectActionDelegate {

    private Activity selectedDecisionServiceTask = null;

    /**
     * @return the selectedDecisionServiceTask
     */
    protected Activity getSelectedDecisionServiceTask() {
        return selectedDecisionServiceTask;
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param action
     * @param selection
     */
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        selectedDecisionServiceTask = null;
        if (selection instanceof IStructuredSelection) {
            Object firstElement =
                    ((IStructuredSelection) selection).getFirstElement();

            if (firstElement instanceof Activity) {
                selectedDecisionServiceTask = (Activity) firstElement;

            } else if (firstElement instanceof IAdaptable) {
                Object adapter =
                        ((IAdaptable) firstElement).getAdapter(Activity.class);
                if (adapter instanceof Activity) {
                    selectedDecisionServiceTask = (Activity) adapter;
                }
            }
        }

        action.setEnabled(selectedDecisionServiceTask != null
                && Xpdl2ResourcesPlugin.isDecisionsFeatureAvailable()
                && shouldEnable(selectedDecisionServiceTask));
    }

    /**
     * @param selectedDecisionServiceTask2
     * @return <code>true</code> if having checked everything
     */
    protected abstract boolean shouldEnable(Activity selectedDecisionServiceTask);

    /**
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
     *      org.eclipse.ui.IWorkbenchPart)
     * 
     * @param action
     * @param targetPart
     */
    @Override
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

}

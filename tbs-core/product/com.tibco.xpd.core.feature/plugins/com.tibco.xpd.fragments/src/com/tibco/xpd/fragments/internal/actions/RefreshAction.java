/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;

import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;

/**
 * Fragments view refresh action. This will refresh the selected category and
 * the associated folder in the workspace.
 * 
 * @author njpatel
 * 
 */
public class RefreshAction extends FragmentActionDelegate {

    /**
     * Refresh action.
     */
    public RefreshAction() {
    }

    @Override
    protected boolean isEnabled(Object sel) {
        return sel instanceof IFragmentCategory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action
     * .IAction, org.eclipse.swt.widgets.Event)
     */
    public void runWithEvent(IAction action, Event event) {
        IStructuredSelection selection = getSelection();

        if (selection != null
                && selection.getFirstElement() instanceof FragmentCategoryImpl) {
            FragmentsManager.getInstance().refreshFragmentsView(
                    (FragmentCategoryImpl) selection.getFirstElement());
        }
    }

}

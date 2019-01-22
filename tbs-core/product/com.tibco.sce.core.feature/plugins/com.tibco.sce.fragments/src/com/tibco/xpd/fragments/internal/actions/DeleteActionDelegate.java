/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.xpd.fragments.IContainedFragmentElement;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.dialog.DeleteDialog;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentImpl;
import com.tibco.xpd.fragments.internal.operations.DeleteCategoryOperation;
import com.tibco.xpd.fragments.internal.operations.DeleteFragmentOperation;
import com.tibco.xpd.fragments.internal.operations.DeleteOperation;

/**
 * Delete fragment element action in the fragments view.
 * 
 * @author njpatel
 * 
 */
public class DeleteActionDelegate extends FragmentActionDelegate {

    private Shell shell;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.fragments.internal.actions.FragmentActionDelegate#init(
     * org.eclipse.ui.IViewPart)
     */
    public void init(IViewPart view) {
        super.init(view);
        shell = view.getSite().getShell();
        IAction action = getAction();

        if (action != null) {
            view.getViewSite().getActionBars().setGlobalActionHandler(
                    ActionFactory.DELETE.getId(), action);
        }
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

        if (selection != null && !selection.isEmpty()) {
            Object element = selection.getFirstElement();
            IFragmentCategory parent = null;

            if (element instanceof IContainedFragmentElement) {
                parent = ((IContainedFragmentElement) element).getParent();
            }

            if (parent instanceof FragmentCategoryImpl) {
                DeleteOperation op = null;
                String message = null;
                if (element instanceof FragmentImpl) {
                    // Delete fragment
                    op = new DeleteFragmentOperation(
                            (FragmentCategoryImpl) parent,
                            (FragmentImpl) element);
                    message = String
                            .format(
                                    Messages.DeleteActionDelegate_deleteFragment_dialog_message,
                                    ((FragmentImpl) element).getName());
                } else if (element instanceof FragmentCategoryImpl) {
                    op = new DeleteCategoryOperation(
                            (FragmentCategoryImpl) parent,
                            (FragmentCategoryImpl) element);
                    message = String
                            .format(
                                    Messages.DeleteActionDelegate_deleteCategory_dialog_message,
                                    ((FragmentCategoryImpl) element).getName());
                }

                if (op != null) {
                    DeleteDialog.open(shell,
                            Messages.DeleteActionDelegate_delete_dialog_title,
                            message, op);
                }
            }
        }
    }
}

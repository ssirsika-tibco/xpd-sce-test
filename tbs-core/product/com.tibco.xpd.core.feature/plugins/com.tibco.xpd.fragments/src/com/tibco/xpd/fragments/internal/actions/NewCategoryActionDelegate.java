/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.impl.FragmentCategoryImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentRootCategory;
import com.tibco.xpd.fragments.internal.operations.CreateCategoryOperation;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * New category action in the fragments view.
 * 
 * @author njpatel
 * 
 */
public class NewCategoryActionDelegate extends FragmentActionDelegate {

    private IFragmentCategory currentSel;
    private Shell shell;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.fragments.internal.actions.FragmentActionDelegate#init(
     * org.eclipse.ui.IViewPart)
     */
    public void init(IViewPart view) {
        shell = view.getSite().getShell();
    }

    @Override
    protected boolean isEnabled(Object sel) {
        boolean enabled = false;
        if (sel instanceof IFragmentCategory) {
            currentSel = (IFragmentCategory) sel;
            if (currentSel instanceof FragmentRootCategory) {
                // Can add user category to root category
                enabled = true;
            } else {
                // Not allowed to add user categories to system categories
                enabled = !currentSel.isSystem();
            }
        }

        return enabled;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action
     * .IAction, org.eclipse.swt.widgets.Event)
     */
    public void runWithEvent(IAction action, Event event) {
        if (currentSel instanceof FragmentCategoryImpl) {
            FragmentCategoryImpl cat = (FragmentCategoryImpl) currentSel;
            // Get the next available category name
            String name = cat.getNextCategoryName();

            CreateCategoryOperation op = new CreateCategoryOperation(cat, null,
                    name, null, false);
            try {
                FragmentsUtil.execute(op, null);
            } catch (ExecutionException e) {
                FragmentsActivator.getDefault().getLogger().error(e);
                ErrorDialog
                        .openError(
                                shell,
                                Messages.NewCategoryActionDelegate_newCategoryError_dialog_title,
                                Messages.NewCategoryActionDelegate_1newCategoryError_dialog_message,
                                new Status(IStatus.ERROR,
                                        FragmentsActivator.PLUGIN_ID, e
                                                .getLocalizedMessage(), e));
            }
        }
    }

}

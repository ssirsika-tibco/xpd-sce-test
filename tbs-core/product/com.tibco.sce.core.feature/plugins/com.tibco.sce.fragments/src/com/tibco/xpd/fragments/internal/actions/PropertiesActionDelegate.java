/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.dialog.PropertiesDialog;
import com.tibco.xpd.fragments.internal.impl.FragmentElementImpl;
import com.tibco.xpd.fragments.internal.impl.FragmentRootCategory;
import com.tibco.xpd.fragments.internal.operations.SetPropertiesOperation;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Properties action in the fragments view.
 * 
 * @author njpatel
 * 
 */
public class PropertiesActionDelegate extends FragmentActionDelegate {

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

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action
     * .IAction, org.eclipse.swt.widgets.Event)
     */
    public void runWithEvent(IAction action, Event event) {
        if (getSelection() != null
                && getSelection().getFirstElement() instanceof FragmentElementImpl) {
            FragmentElementImpl elem = (FragmentElementImpl) getSelection()
                    .getFirstElement();

            PropertiesDialog dlg = new PropertiesDialog(shell, elem);
            if (dlg.open() == Dialog.OK) {
                // Update name and description
                SetPropertiesOperation op = new SetPropertiesOperation(elem,
                        dlg.getName(), dlg.getDescription());

                try {
                    FragmentsUtil.execute(op, null);
                } catch (ExecutionException e) {
                    FragmentsActivator.getDefault().getLogger().error(e);
                    ErrorDialog
                            .openError(
                                    shell,
                                    Messages.PropertiesActionDelegate_properties_dialog_title,
                                    Messages.PropertiesActionDelegate_properties_dialog_message,
                                    new Status(IStatus.ERROR,
                                            FragmentsActivator.PLUGIN_ID, e
                                                    .getLocalizedMessage(), e));
                }
            }
        }
    }

    @Override
    protected boolean isEnabled(Object sel) {
        return sel instanceof IFragmentElement
                && !(sel instanceof FragmentRootCategory);
    }

}

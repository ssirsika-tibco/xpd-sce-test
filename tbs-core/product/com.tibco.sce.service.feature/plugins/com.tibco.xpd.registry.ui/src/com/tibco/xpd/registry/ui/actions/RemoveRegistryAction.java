/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.actions;

import java.util.Iterator;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.registry.IRegistryManager;
import com.tibco.xpd.registry.Registry;
import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.ImageCache;
import com.tibco.xpd.registry.ui.internal.Messages;

/**
 * Action to remove registries from the registry manager.
 * 
 * @author nwilson
 */
public class RemoveRegistryAction extends RemoveElementAction {
    /** Message title for remove registry. */
    private static final String REMOVE_REGISTRY_ACTION_TITLE = Messages.RemoveRegistryAction_label;

    /** Message text for remove registry. */
    private static final String REMOVE_REGISTRY_ACTION_TOOLTIP = Messages.RemoveRegistryAction_tooltip;

    /** Confirmation title for remove registry. */
    private static final String CONFIRM_TITLE = Messages.RemoveRegistryAction_title;

    /** Confirmation text for remove registry. */
    private static final String CONFIRM_TEXT = Messages.RemoveRegistryAction_message;

    /** The shell to associate the wizard with. */
    private final Shell shell;

    /** The selection provider. */
    private final ISelectionProvider provider;

    /**
     * @param shell
     *            The shell to associate the wizard with.
     * @param provider
     *            The selection provider.
     */
    public RemoveRegistryAction(Shell shell, ISelectionProvider provider) {
        super();
        this.shell = shell;
        this.provider = provider;
        setText(REMOVE_REGISTRY_ACTION_TITLE);
        setImageDescriptor(Activator
                .getImageDescriptor(ImageCache.REMOVE_REGISTRY));
        setToolTipText(REMOVE_REGISTRY_ACTION_TOOLTIP);
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        if (MessageDialog.openConfirm(shell, CONFIRM_TITLE, CONFIRM_TEXT)) {
            IRegistryManager registryManager = com.tibco.xpd.registry.Activator
                    .getRegistryManager();
            ISelection selection = provider.getSelection();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection structSel = (IStructuredSelection) selection;
                for (Iterator<?> iterator = structSel.iterator(); iterator
                        .hasNext();) {
                    Object element = iterator.next();
                    if (element instanceof Registry) {
                        registryManager.removeRegistry((Registry) element);
                    }
                }
            }
        }
    }

    /*
     * This action removes registries.
     * 
     * @see com.tibco.xpd.registry.ui.actions.RemoveElementAction#getElementType()
     */
    @Override
    protected Class<?> getElementType() {
        return Registry.class;
    }
}

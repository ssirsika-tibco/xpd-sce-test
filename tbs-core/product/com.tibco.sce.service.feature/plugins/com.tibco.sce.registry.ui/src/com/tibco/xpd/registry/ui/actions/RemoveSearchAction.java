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

import com.tibco.xpd.registry.Registry;
import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.ImageCache;
import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.registry.ui.selector.RegistrySearch;

/**
 * Action for removing searches from a registry.
 * 
 * @author nwilson
 */
public class RemoveSearchAction extends RemoveElementAction {
    /** Message title for remove search. */
    private static final String REMOVE_SEARCH_ACTION_TITLE = Messages.RemoveSearchAction_label;

    /** Message text for remove search. */
    private static final String REMOVE_SEARCH_ACTION_TOOLTIP = Messages.RemoveSearchAction_tooltip;

    /** Confirmation title for remove search. */
    private static final String CONFIRM_TITLE = Messages.RemoveSearchAction_confirm_title;

    /** Confirmation text for remove search. */
    private static final String CONFIRM_TEXT = Messages.RemoveSearchAction_confirm_message;

    /** The shell to associate the dialog with. */
    private final Shell shell;

    /** The selection provider. */
    private final ISelectionProvider provider;

    /**
     * Constructor.
     * 
     * @param shell
     *            The shell to associate the dialog with.
     * @param provider
     *            The selection provider.
     */
    public RemoveSearchAction(Shell shell, ISelectionProvider provider) {
        super();
        this.shell = shell;
        this.provider = provider;
        setText(REMOVE_SEARCH_ACTION_TITLE);
        setImageDescriptor(Activator
                .getImageDescriptor(ImageCache.REMOVE_SEARCH));
        setToolTipText(REMOVE_SEARCH_ACTION_TOOLTIP);
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        if (MessageDialog.openConfirm(shell, CONFIRM_TITLE, CONFIRM_TEXT)) {
            ISelection selection = provider.getSelection();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection structSel = (IStructuredSelection) selection;
                for (Iterator<?> iterator = structSel.iterator(); iterator
                        .hasNext();) {
                    Object element = iterator.next();
                    if (element instanceof RegistrySearch) {
                        RegistrySearch registrySearch = (RegistrySearch) element;
                        Registry registry = registrySearch.getRegistry();
                        registry.removeSearch(registrySearch.getSearch());
                    }
                }
            }
        }
    }

    /*
     * RegistrySearch elements can be removed by this action.
     * 
     * @see com.tibco.xpd.registry.ui.actions.RemoveElementAction#getElementType()
     */
    @Override
    protected Class<?> getElementType() {
        return RegistrySearch.class;
    }
}

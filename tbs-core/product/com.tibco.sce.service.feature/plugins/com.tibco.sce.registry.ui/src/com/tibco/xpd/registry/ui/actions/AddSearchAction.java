/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.registry.Registry;
import com.tibco.xpd.registry.Search;
import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.ImageCache;
import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.registry.ui.wizard.NewRegistrySearchWizard;

/**
 * Action for adding a search to a registry.
 * 
 * @author nwilson
 */
public class AddSearchAction extends Action {
    /** Message title for add search. */
    private static final String ADD_SEARCH_ACTION_TITLE = Messages.AddSearchAction_label;

    /** Message text for add search. */
    private static final String ADD_SEARCH_ACTION_TOOLTIP = Messages.AddSearchAction_tooltip;

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
    public AddSearchAction(Shell shell, ISelectionProvider provider) {
        super();
        this.shell = shell;
        this.provider = provider;
        setText(ADD_SEARCH_ACTION_TITLE);
        setImageDescriptor(Activator.getImageDescriptor(ImageCache.ADD_SEARCH));
        setToolTipText(ADD_SEARCH_ACTION_TOOLTIP);
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        ISelection selection = provider.getSelection();
        if (selection instanceof ITreeSelection) {
            ITreeSelection treeSelection = (ITreeSelection) selection;
            TreePath[] paths = treeSelection.getPaths();
            if (paths.length == 1) {
                Object last = paths[0].getLastSegment();
                if (last instanceof Registry) {
                    Registry registry = (Registry) last;
                    NewRegistrySearchWizard wizard = new NewRegistrySearchWizard();
                    wizard.init(null, treeSelection);
                    WizardDialog dialog = new WizardDialog(shell, wizard);
                    if (dialog.open() == WizardDialog.OK) {
                        Search search = wizard.getSearch();
                        if (search != null) {
                            if (!registry.getSearches().contains(search)) {
                                registry.addSearch(search);
                            }
                        }
                    }
                }
            }
        }
    }

}

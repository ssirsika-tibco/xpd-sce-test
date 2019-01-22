/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.ImageCache;
import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.registry.ui.wizard.NewRegistryWizard;

/**
 * Action for adding a new registry.
 * 
 * @author nwilson
 */
public class AddRegistryAction extends Action {
    /** Message title for add search. */
    private static final String ADD_REGISTRY_ACTION_TITLE = Messages.AddRegistryAction_label;

    /** Message text for add search. */
    private static final String ADD_REGISTRY_ACTION_TOOLTIP = Messages.AddRegistryAction_tooltip;

    /** The shell to associate the wizard with. */
    private final Shell shell;

    /**
     * @param shell
     *            The shell to associate the wizard with.
     */
    public AddRegistryAction(Shell shell) {
        super(ADD_REGISTRY_ACTION_TITLE, Activator
                .getImageDescriptor(ImageCache.ADD_REGISTRY));
        this.shell = shell;
        setText(ADD_REGISTRY_ACTION_TITLE);
        setImageDescriptor(Activator
                .getImageDescriptor(ImageCache.ADD_REGISTRY));
        setToolTipText(ADD_REGISTRY_ACTION_TOOLTIP);
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        NewRegistryWizard wizard = new NewRegistryWizard();
        wizard.init(null, null);
        WizardDialog dialog = new WizardDialog(shell, wizard);
        dialog.open();
    }
}

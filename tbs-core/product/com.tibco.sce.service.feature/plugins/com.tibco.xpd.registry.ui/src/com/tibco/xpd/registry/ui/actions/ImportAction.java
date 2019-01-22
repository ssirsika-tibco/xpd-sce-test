/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.registry.ui.Activator;
import com.tibco.xpd.registry.ui.ImageCache;
import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.registry.ui.selector.RegistryServiceSelector;
import com.tibco.xpd.registry.ui.wizard.WsdlImportFromExistingRegistryWizard;

/**
 * @author nwilson
 */
public class ImportAction extends Action {

    /** Message title for remove search. */
    private static final String IMPORT_ACTION_TITLE = Messages.ImportAction_label;

    /** Message text for remove search. */
    private static final String IMPORT_ACTION_TOOLTIP = Messages.ImportAction_tooltip;

    /** The wizard parent shell. */
    private Shell shell;

    /** The registry view. */
    private final RegistryServiceSelector registry;

    private IStructuredSelection selection;

    /**
     * The constructor.
     * 
     * @param shell
     *            The wizard parent shell.
     * @param registry
     *            The registry view.
     */
    public ImportAction(Shell shell, RegistryServiceSelector registry) {
        super();
        this.registry = registry;
        setText(IMPORT_ACTION_TITLE);
        setImageDescriptor(Activator.getImageDescriptor(ImageCache.IMPORT));
        setToolTipText(IMPORT_ACTION_TOOLTIP);
    }

    /*
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
    	WsdlImportFromExistingRegistryWizard wizard = new WsdlImportFromExistingRegistryWizard(registry);
        wizard.init(PlatformUI.getWorkbench(), selection);
        WizardDialog dialog = new WizardDialog(shell, wizard);
        dialog.open();
    }

    /**
     * @param selection
     *            The selection used as a initial selection for this action.
     */
    public void setSelection(IStructuredSelection selection) {
        this.selection = selection;
    }

    /**
     * @return The current selection.
     */
    public IStructuredSelection getSelection() {
        return selection;
    }
}

/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.custom.CCombo;

/**
 * Delayed combo handler that uses <code>Command</code> to update model.
 * 
 */
public class CommandCComboFieldHandler extends BaseCComboFieldHandler {

    private final CommandProvider commandProvider;

    /**
     * Constructor
     * 
     * @param combo
     * @param commandProvider
     */
    public CommandCComboFieldHandler(CCombo combo,
            CommandProvider commandProvider) {
        super(combo);
        this.commandProvider = commandProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.BaseCComboFieldHandler#createCommand()
     */
    protected Command createCommand() {
        return commandProvider.getCommand(combo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
     */
    public EditingDomain getEditingDomain() {
        return commandProvider.getEditingDomain();
    }

}

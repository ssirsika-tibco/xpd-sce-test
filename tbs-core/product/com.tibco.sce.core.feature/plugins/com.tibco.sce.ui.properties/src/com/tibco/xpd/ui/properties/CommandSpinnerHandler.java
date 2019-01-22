/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Spinner;

/**
 * Delayed handler for <code>Spinner</code> that uses <code>Command</code>
 * to update the model.
 * 
 * @author njpatel
 */
/**
 * @author njpatel
 * 
 */
public class CommandSpinnerHandler extends BaseSpinnerFieldHandler {

    private final CommandProvider commandProvider;

    /**
     * Constructor.
     * 
     * @param spinner
     * @param commandProvider
     */
    public CommandSpinnerHandler(Spinner spinner,
            CommandProvider commandProvider) {
        super(spinner);
        this.commandProvider = commandProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.BaseSpinnerFieldHandler#createCommand()
     */
    protected Command createCommand() {
        return commandProvider.getCommand(spinner);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
     */
    public EditingDomain getEditingDomain() {
        return commandProvider.getEditingDomain();
    }

    @Override
    protected void ignoreModelEvents(boolean ignore) {
        if (commandProvider instanceof ModelChangeListener) {
            ((ModelChangeListener) commandProvider).ignoreModelEvents(ignore);
        }
    }

}

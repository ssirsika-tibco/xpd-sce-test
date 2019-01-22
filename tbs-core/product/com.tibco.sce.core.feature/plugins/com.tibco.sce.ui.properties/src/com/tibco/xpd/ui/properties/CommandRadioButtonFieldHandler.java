/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Button;

/**
 * Text field handler that relay on command provide that can produce commad to
 * modify model
 * 
 * @author wzurek
 */
public class CommandRadioButtonFieldHandler extends BaseButtonFieldHandler {

    private final CommandProvider commandProvider;

    /**
     * Constructor.
     * 
     * @param button
     * @param commandProvider
     */
    public CommandRadioButtonFieldHandler(Button button,
            CommandProvider commandProvider) {
        super(button);
        this.commandProvider = commandProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
     */
    public EditingDomain getEditingDomain() {
        return commandProvider.getEditingDomain();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.BaseButtonFieldHandler#createCommand()
     */
    protected Command createCommand() {
        return commandProvider.getCommand(button);
    }
}

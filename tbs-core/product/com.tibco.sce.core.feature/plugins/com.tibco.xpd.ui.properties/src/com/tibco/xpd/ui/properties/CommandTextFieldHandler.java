/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;

/**
 * Text field handler that relay on command provide that can produce command to
 * modify model
 * 
 * @author wzurek
 */
public class CommandTextFieldHandler extends BaseTextFieldHandler {

    /**
     * Constructor.
     * 
     * @param text
     * @param commandProvider
     */
    public CommandTextFieldHandler(Text text, CommandProvider commandProvider) {
        super(text, commandProvider);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.BaseTextFieldHandler#getEditingDomain()
     */
    protected EditingDomain getEditingDomain() {
        return getCommandProvider().getEditingDomain();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.BaseTextFieldHandler#createCommand()
     */
    protected Command createCommand() {
        return getCommandProvider().getCommand(text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.BaseTextFieldHandler#verify(org.eclipse.swt.widgets.Event)
     */
    protected void verify(Event event) {
        if (getCommandProvider() instanceof TextFieldVerifier) {
            ((TextFieldVerifier) getCommandProvider()).verifyText(event);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.BaseTextFieldHandler#ignoreModelEvents(boolean)
     */
    protected void ignoreModelEvents(boolean ignore) {
        if (getCommandProvider() instanceof ModelChangeListener) {
            ((ModelChangeListener) getCommandProvider())
                    .ignoreModelEvents(ignore);
        }
    }
}

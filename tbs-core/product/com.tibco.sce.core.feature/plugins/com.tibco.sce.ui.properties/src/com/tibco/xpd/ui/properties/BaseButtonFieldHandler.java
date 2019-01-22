/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Base delayed command button handler, see subclasses for more comments
 * 
 * @author wzurek
 */
public abstract class BaseButtonFieldHandler implements Listener,
        IEditingDomainProvider {

    protected final Button button;

    /**
     * Constructor.
     * 
     * @param button
     */
    public BaseButtonFieldHandler(Button button) {
        this.button = button;
        register(this.button);
    }

    /**
     * Register listeners to the given <code>Button</code>.
     * 
     * @param text
     */
    protected void register(Button text) {
        text.addListener(SWT.Selection, this);
        text.addListener(SWT.Dispose, this);
        text.addListener(SWT.Deactivate, this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent(Event event) {
        switch (event.type) {
        case SWT.Selection:
            // For radio buttons we are only interested in selection NOT
            // deselection.
            if ((event.widget.getStyle() & SWT.RADIO) == 0
                    || ((Button) event.widget).getSelection() == true) {
                doSelection(event);
            }
            break;
        case SWT.Dispose:
        case SWT.Deactivate:
            break;
        }
    }

    /**
     * Called in response to selection event.
     * 
     * @param event
     */
    protected void doSelection(Event event) {
        Command cmd = createCommand();
        if (cmd != null && cmd.canExecute()) {
            getEditingDomain().getCommandStack().execute(cmd);
        }
    }

    /**
     * Create command to update model.
     * 
     * @return <code>Command</code> to update model, <code>null</code> if no
     *         update is required.
     */
    protected abstract Command createCommand();
}

/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Base delayed command combo handler.
 * 
 * @author njpatel
 * 
 */
public abstract class BaseCComboFieldHandler implements Listener,
        IEditingDomainProvider {
    protected final CCombo combo;

    /**
     * Constructor.
     * 
     * @param combo
     */
    public BaseCComboFieldHandler(CCombo combo) {
        this.combo = combo;
        register(combo);
    }

    /**
     * Register listeners with the given combo.
     * 
     * @param combo
     */
    protected void register(CCombo combo) {
        combo.addListener(SWT.Selection, this);
        combo.addListener(SWT.Modify, this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent(Event event) {
        switch (event.type) {
        case SWT.Selection:
        case SWT.Modify:
            doSelection(event);
            break;

        }
    }

    /**
     * Called in response to a selection and modify event.
     * 
     * @param event
     */
    private void doSelection(Event event) {
        Command cmd = createCommand();
        if (cmd != null) {
            if (cmd.canExecute()) {
                if (getEditingDomain() != null
                        && getEditingDomain().getCommandStack() != null)
                    getEditingDomain().getCommandStack().execute(cmd);
            }
        }
    }

    /**
     * Create command to update the model.
     * 
     * @return <code>Command</code> to update model, <code>null</code> if
     *         model doesn't need updating.
     */
    protected abstract Command createCommand();
}

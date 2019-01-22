/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * Base delayed command check table handler.
 * 
 */
public abstract class BaseCheckTableFieldHandler implements Listener,
        IEditingDomainProvider {

    protected final Table table;

    /**
     * Constructor.
     * 
     * @param table
     */
    public BaseCheckTableFieldHandler(Table table) {
        this.table = table;
        register(this.table);
    }

    /**
     * Register listeners to the table.
     * 
     * @param table
     */
    protected void register(Table table) {
        table.addListener(SWT.Selection, this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent(Event event) {
        switch (event.type) {
        case SWT.Selection:
            if (event.detail == SWT.CHECK) {
                doSelection(event);
            }
            break;
        case SWT.Dispose:
        case SWT.Deactivate:
            break;
        }
    }

    /**
     * Called in response to a selection event.
     * 
     * @param event
     */
    private void doSelection(Event event) {
        TableItem item = (TableItem) event.item;
        Command cmd = createCommand(item);
        if (cmd != null) {
            if (cmd.canExecute()) {
                getEditingDomain().getCommandStack().execute(cmd);
            }
        }
    }

    /**
     * Create command to update the model.
     * 
     * @param item
     * 
     * @return <code>Command</code> to update model, <code>null</code> if
     *         model doesn't need updating.
     */
    protected abstract Command createCommand(TableItem item);
}

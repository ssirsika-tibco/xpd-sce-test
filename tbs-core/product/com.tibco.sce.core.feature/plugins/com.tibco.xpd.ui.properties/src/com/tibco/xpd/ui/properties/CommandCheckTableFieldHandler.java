/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * Check table delayed handler that uses <code>Command</code> to update model.
 */
public class CommandCheckTableFieldHandler extends BaseCheckTableFieldHandler {

    private final CommandProvider commandProvider;

    /**
     * Constructor.
     * 
     * @param table
     * @param commandProvider
     */
    public CommandCheckTableFieldHandler(Table table,
            CommandProvider commandProvider) {
        super(table);
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
     * @see com.tibco.xpd.ui.properties.BaseCheckTableFieldHandler#createCommand(org.eclipse.swt.widgets.TableItem)
     */
    protected Command createCommand(TableItem item) {
        return commandProvider.getCommand(item);
    }
}

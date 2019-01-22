/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * Hyperlink delayed handler that uses <code>Command</code> to update model.
 */
public class CommandHyperlinkHandler extends BaseHyperlinkHandler {

    private final CommandProvider commandProvider;

    /**
     * Constructor.
     * 
     * @param text
     * @param commandProvider
     */
    public CommandHyperlinkHandler(FormText text,
            CommandProvider commandProvider) {
        super(text);
        this.commandProvider = commandProvider;
    }

    /**
     * Constructor.
     * 
     * @param text
     * @param commandProvider
     */
    public CommandHyperlinkHandler(Hyperlink text,
            CommandProvider commandProvider) {
        super(text);
        this.commandProvider = commandProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.BaseHyperlinkHandler#createCommand(java.lang.Object)
     */
    protected Command createCommand(Object href) {
        return commandProvider.getCommand(href);
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

/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * Base class for hyperlinks handlers.
 * 
 * @author wzurek
 */
public abstract class BaseHyperlinkHandler implements Listener,
        IEditingDomainProvider, IHyperlinkListener {

    protected final Control button;

    /**
     * Constructor.
     * 
     * @param button
     */
    public BaseHyperlinkHandler(Control button) {
        this.button = button;
        register(this.button);
    }

    /**
     * Register listeners to the given <code>Control</code>.
     * 
     * @param text
     */
    protected void register(Control text) {
        text.addListener(SWT.Dispose, this);
        text.addListener(SWT.Deactivate, this);

        if (text instanceof FormText) {
            ((FormText) text).addHyperlinkListener(this);
        } else if (text instanceof Hyperlink) {
            ((Hyperlink) text).addHyperlinkListener(this);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent(Event event) {
        switch (event.type) {
        case SWT.Dispose:
        case SWT.Deactivate:
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.events.IHyperlinkListener#linkActivated(org.eclipse.ui.forms.events.HyperlinkEvent)
     */
    public void linkActivated(HyperlinkEvent e) {
        doSelection(e.getHref());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.events.IHyperlinkListener#linkEntered(org.eclipse.ui.forms.events.HyperlinkEvent)
     */
    public void linkEntered(HyperlinkEvent e) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.events.IHyperlinkListener#linkExited(org.eclipse.ui.forms.events.HyperlinkEvent)
     */
    public void linkExited(HyperlinkEvent e) {
    }

    /**
     * Called in response to a link activated event.
     * 
     * @param href
     */
    protected void doSelection(Object href) {
        Command cmd = createCommand(href);
        if (cmd != null) {
            getEditingDomain().getCommandStack().execute(cmd);
        }
    }

    /**
     * Create command to update the model.
     * 
     * @param href
     * @return <code>Command</code> to update model, <code>null</code> if
     *         model doesn't need updating.
     */
    protected abstract Command createCommand(Object href);
}

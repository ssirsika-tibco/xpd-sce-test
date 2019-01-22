/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.pages;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.Page;

import com.tibco.xpd.fragments.internal.Messages;

/**
 * Message view page. This will be displayed in the fragments view part when no
 * provider is bound with the current active editor or the fragments API is
 * still initializing. This display a message to the user.
 * 
 * @author njpatel
 * 
 */
public class MessageViewPage extends Page implements ISelectionProvider {

    private static final String DEFAULT_MSG = Messages.MessageViewPage_fragmentView_whenNoFragmentProvider_message;
    private String message = DEFAULT_MSG;
    private Composite root;
    private Label messageLabel;
    private Set<ISelectionChangedListener> listeners = new HashSet<ISelectionChangedListener>();

    @Override
    public void createControl(Composite parent) {
        root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout());

        messageLabel = new Label(root, SWT.NONE);
        messageLabel.setText(message);
    }

    /**
     * Set the message to display in this page.
     * 
     * @param message
     *            message to display or <code>null</code> to display default
     *            message.
     */
    public void setMessage(String message) {
        this.message = message != null ? message : DEFAULT_MSG;
        refresh();
    }

    @Override
    public Control getControl() {
        return root;
    }

    @Override
    public void setFocus() {
        // Nothing to do
    }

    /**
     * Refresh the page.
     */
    public void refresh() {
        // Refresh the message
        if (messageLabel != null && !messageLabel.isDisposed()) {
            messageLabel.setText(message);
            root.layout();
        }
        setSelection(StructuredSelection.EMPTY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener
     * (org.eclipse.jface.viewers.ISelectionChangedListener)
     */
    public void addSelectionChangedListener(ISelectionChangedListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
     */
    public ISelection getSelection() {
        // Nothing to select on this page so return empty selection
        return StructuredSelection.EMPTY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener
     * (org.eclipse.jface.viewers.ISelectionChangedListener)
     */
    public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse
     * .jface.viewers.ISelection)
     */
    public void setSelection(ISelection selection) {

        for (ISelectionChangedListener listener : listeners) {
            listener
                    .selectionChanged(new SelectionChangedEvent(this, selection));
        }
    }

}

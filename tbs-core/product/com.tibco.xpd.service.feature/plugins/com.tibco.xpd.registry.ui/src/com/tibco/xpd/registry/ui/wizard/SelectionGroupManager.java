/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;

/**
 * Groups a set of buttons (usually radio) together and associates a set of
 * controls with each button. When a button is selected all of its controls are
 * enabled. All of the other buttons will be unselected and their controls
 * disabled.
 * @author nwilson
 */
public class SelectionGroupManager implements SelectionListener {
    /** A map of buttons to controls associated with that button. */
    private HashMap<Button, ArrayList<Control>> map;
    /** A set of listeners for changes in button selection. */
    private HashSet<SelectionListener> listeners;
    
    /**
     * Constructor to initialise the button list.
     */
    public SelectionGroupManager() {
        map = new HashMap<Button, ArrayList<Control>>();
        listeners = new HashSet<SelectionListener>();
    }
    
    /**
     * Adds a new button to the group.
     * @param button The new button to add.
     */
    public void addButton(Button button) {
        map.put(button, new ArrayList<Control>());
        button.addSelectionListener(this);
    }

    /**
     * Associates a control with a button. The button should have already been
     * added to the group.
     * @param button The button to associate the control with.
     * @param control The control.
     */
    public void associate(Button button, Control control) {
        ArrayList<Control> list = map.get(button);
        if (list != null) {
            list.add(control);
            control.setEnabled(button.getSelection());
        }
    }

    /**
     * Not used.
     * @param e n/a.
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(
     *      org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent e) {
    }

    /**
     * Callback for when a button is selected.
     * @param e The selection event.
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(
     *      org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent e) {
        Object source = e.getSource();
        if (source instanceof Button) {
            Button sourceButton = (Button) source;
            if (sourceButton.getSelection()) {
                for (Iterator<?> i = map.keySet().iterator(); i.hasNext();) {
                    Button button = (Button) i.next();
                    if (e.getSource() == button) {
                        setEnabled(button, true);
                    } else {
                        button.setSelection(false);
                        setEnabled(button, false);
                    }
                }
                fireModified(e);
            }
        }
    }

    /**
     * Enables or disables the controls associated with a button.
     * @param button The button associated with the controls.
     * @param enabled true to enable the controls, false to disable.
     */
    private void setEnabled(Button button, boolean enabled) {
        ArrayList<Control> list = map.get(button);
        if (list != null) {
            for (Control control : list) {
                control.setEnabled(enabled);
            }
        }
    }

    /**
     * Notifies any listeners of a change of button selection.
     * @param event The selection event.
     */
    private void fireModified(SelectionEvent event) {
        for (SelectionListener listener : listeners) {
            listener.widgetSelected(event);
        }
    }

    /**
     * Adds a new listener for selection events.
     * @param listener The new listener.
     */
    public void addSelectionListener(SelectionListener listener) {
        listeners.add(listener);
    }

}

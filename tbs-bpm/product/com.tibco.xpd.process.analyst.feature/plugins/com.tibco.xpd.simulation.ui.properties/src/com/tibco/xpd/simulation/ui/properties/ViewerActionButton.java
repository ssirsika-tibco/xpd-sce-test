/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Viewer action button.
 */
public class ViewerActionButton {

    /** Button selection listener. */
    private SelectionListener actionButtonListener;

    /** Button. */
    private final Button button;

    /**
     * @param parent The parent composite.
     * @param toolkit The UI toolkit.
     * @param action The action associated with the button.
     */
    public ViewerActionButton(Composite parent, XpdFormToolkit toolkit,
            IAction action) {
        button = toolkit.createButton(parent, "", SWT.NONE);//$NON-NLS-1$
        button.setData(action);
        ImageDescriptor id = action.getImageDescriptor();
        if (id != null) {
            button.setImage(id.createImage());
            button.addDisposeListener(new DisposeListener() {
                public void widgetDisposed(DisposeEvent e) {
                    Button btn = (Button) e.widget;
                    Image img = btn.getImage();
                    if (img != null && !img.isDisposed()) {
                        img.dispose();
                    }

                }
            });
        }
        if (actionButtonListener == null) {
            actionButtonListener = new ActionButtonListener();
        }
        button.addSelectionListener(actionButtonListener);
        action.addPropertyChangeListener(new IPropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                IAction act = ((IAction) event.getSource());
                if (button.isDisposed()) {
                    act.removePropertyChangeListener(this);
                } else {
                    button.setEnabled(act.isEnabled());
                    button.setToolTipText(act.getToolTipText());
                }
            }
        });
    }

    /**
     * The button selection listener class.
     */
    private static class ActionButtonListener implements SelectionListener {
        /**
         * @param e The selection event.
         * @see org.eclipse.swt.events.SelectionListener#widgetSelected(
         *      org.eclipse.swt.events.SelectionEvent)
         */
        public void widgetSelected(SelectionEvent e) {
            Object data = e.widget.getData();
            if (data instanceof IAction) {
                IAction action = (IAction) data;
                if (action.isEnabled()) {
                    action.run();
                }
            }
        }

        /**
         * @param e The selection event.
         * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(
         *      org.eclipse.swt.events.SelectionEvent)
         */
        public void widgetDefaultSelected(SelectionEvent e) {
            // do nothing
        }

    }

    /**
     * @return true if the button is enabled.
     */
    public boolean isEnabled() {
        return button.isEnabled();
    }

    /**
     * @param enabled sets the button enabled state.
     */
    public void setEnabled(boolean enabled) {
        button.setEnabled(enabled);
    }

    /**
     * @return The button.
     */
    public Button getButton() {
        return button;
    }
}

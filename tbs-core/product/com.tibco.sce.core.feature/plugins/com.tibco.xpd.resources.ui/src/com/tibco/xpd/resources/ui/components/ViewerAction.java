/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Base class for all viewer actions. The actions could be rendered in many ways
 * by the control managing structured viewer, for example as button or pop up
 * menu item.
 * <p>
 * <i>Created: 1 Dec 2006</i>
 * 
 * @since 3.2
 * @author Jan Arciuchiewicz
 * 
 */
public class ViewerAction extends Action implements ISelectionChangedListener {

    private final StructuredViewer viewer;

    /**
     * Creates a new action with no text and no image.
     * <p>
     * Configure the action later using the set methods.
     * </p>
     * 
     * @param viewer
     *            the action's viewer.
     */
    public ViewerAction(StructuredViewer viewer) {
        this.viewer = viewer;
    }

    /**
     * Creates a new action with the given text and no image. Calls the one-arg
     * constructor, then <code>setText</code>.
     * 
     * @param viewer
     *            the action's viewer.
     * @param text
     *            the string used as the text for the action, or
     *            <code>null</code> if there is no text
     * @see #setText
     */
    public ViewerAction(StructuredViewer viewer, String text) {
        super(text);
        this.viewer = viewer;
    }

    /**
     * Creates a new action with the given text and imageDescriptor. Calls the
     * one-arg constructor, then <code>setText</code> and
     * <code>setImageDescriptor</code>.
     * 
     * @param viewer
     *            the action's viewer.
     * @param text
     *            the action's text, or <code>null</code> if there is no text
     * @param imageDescriptor
     *            the action's image, or <code>null</code> if there is no image
     * @see #setText
     * @see #setImageDescriptor
     */
    public ViewerAction(StructuredViewer viewer, String text,
            ImageDescriptor imageDescriptor) {
        super(text, imageDescriptor);
        this.viewer = viewer;
    }

    /**
     * Creates a new action with the given text and style.
     * 
     * @param text
     *            the action's text, or <code>null</code> if there is no text
     * @param style
     *            one of <code>AS_PUSH_BUTTON</code>, <code>AS_CHECK_BOX</code>,
     *            <code>AS_DROP_DOWN_MENU</code>, <code>AS_RADIO_BUTTON</code>,
     *            and <code>AS_UNSPECIFIED</code>.
     */
    public ViewerAction(StructuredViewer viewer, String text, int style) {
        super(text, style);
        this.viewer = viewer;
    }

    public StructuredViewer getViewer() {
        return viewer;
    }

    protected Shell getShell() {
        if (getViewer().getControl() != null) {
            return getViewer().getControl().getShell();
        } else {
            return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getShell();
        }
    }

    /** {@inheritDoc} */
    public void selectionChanged(SelectionChangedEvent event) {
        ISelection selection = event.getSelection();
        if (selection instanceof IStructuredSelection) {
            selectionChanged((IStructuredSelection) selection);
        }
    }

    public void selectionChanged(IStructuredSelection selection) {
        // do nothing
    }
}

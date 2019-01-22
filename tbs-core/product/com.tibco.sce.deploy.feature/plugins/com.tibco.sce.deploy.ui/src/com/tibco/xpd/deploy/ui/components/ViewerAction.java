/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.components;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Abstract action associated with viewer. Action is used in
 * {@see ViewerWithButtonsEditor} and will be rendered as a button/and or
 * context menu element.
 * <p>
 * <i>Created: 1 Dec 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public abstract class ViewerAction extends Action {

    private final StructuredViewer viewer;

    /**
     * The constructor.
     */
    public ViewerAction(StructuredViewer viewer) {
        this.viewer = viewer;
    }

    /**
     * The constructor.
     * 
     * @param text
     */
    public ViewerAction(StructuredViewer viewer, String text) {
        super(text);
        this.viewer = viewer;
    }

    /**
     * The constructor.
     * 
     * @param text
     * @param image
     */
    public ViewerAction(StructuredViewer viewer, String text,
            ImageDescriptor image) {
        super(text, image);
        this.viewer = viewer;
    }

    /**
     * The constructor.
     * 
     * @param text
     * @param style
     */
    public ViewerAction(StructuredViewer viewer, String text, int style) {
        super(text, style);
        this.viewer = viewer;
    }

    /**
     * Gets the reference to the associated viewer.
     * 
     * @return reference to associated viewer.
     */
    public StructuredViewer getViewer() {
        return viewer;
    }

    /**
     * This method will be invoked on action when selection on the associated
     * structured viewer has changed.
     * 
     * @param selection
     *            the new selection.
     */
    public void selectionChanged(IStructuredSelection selection) {
        // implement in subclass if necessary
    }

    /**
     * Returns shell associated with the viewer.
     * 
     * @return
     */
    protected Shell getShell() {
        if (getViewer().getControl() != null) {
            return getViewer().getControl().getShell();
        } else {
            return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getShell();
        }
    }
}

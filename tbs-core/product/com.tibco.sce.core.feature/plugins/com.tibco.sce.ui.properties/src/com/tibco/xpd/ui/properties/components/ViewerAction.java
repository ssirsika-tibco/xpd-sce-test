/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties.components;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * <p>
 * <i>Created: 1 Dec 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * @deprecated Use {@link com.tibco.xpd.resources.ui.components.ViewerAction}
 *             instead.
 */
@Deprecated
public class ViewerAction extends Action {

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
        // TODO Auto-generated constructor stub
    }

    public StructuredViewer getViewer() {
        return viewer;
    }

    public void selectionChanged(IStructuredSelection selection) {
        // do nothing
    }

    protected Shell getShell() {
        if (getViewer().getControl() != null) {
            return getViewer().getControl().getShell();
        } else {
            return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getShell();
        }
    }
}

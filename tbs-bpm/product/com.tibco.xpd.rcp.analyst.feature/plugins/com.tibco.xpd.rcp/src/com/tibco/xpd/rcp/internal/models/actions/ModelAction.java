/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.rcp.RCPActivator;

/**
 * Abstract action to be implemented by an action contribution to the Ovierview
 * page and the Model ribbon group menu.
 * 
 * @author njpatel
 * 
 */
public abstract class ModelAction extends Action {

    private final String label;

    private final String imagePath;

    private final Image image;

    private final IWorkbenchWindow window;

    public ModelAction(IWorkbenchWindow window, String label, String imagePath) {
        super(label, RCPActivator.getImageDescriptor(imagePath));
        this.window = window;
        this.label = label;
        this.imagePath = imagePath;
        this.image = null;
    }

    public ModelAction(IWorkbenchWindow window, String label, Image image) {
        super(label);
        this.window = window;
        this.label = label;
        this.imagePath = null;
        this.image = image;
    }

    /**
     * Get the workbench window.
     * 
     * @return
     */
    protected IWorkbenchWindow getWorkbenchWindow() {
        return window;
    }

    /**
     * Get the label of this action.
     * 
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the image of this action
     * 
     * @return {@link Image} or <code>null</code>.
     */
    public Image getImage() {
        if (imagePath != null) {
            return RCPActivator.getDefault().getImageRegistry().get(imagePath);
        } else {
            return image;
        }
    }

    /**
     * Check whether this action is to create a new entity. Default
     * implementation will return <code>false</code>, subclasses may override.
     * 
     * @return
     */
    public boolean isNewAction() {
        return false;
    }

    /**
     * Check whether this action will create a new file in the workspace.
     * Default implementation returns <code>false</code>, subclasses may
     * override.
     * 
     * @return <code>true</code> if this action will create a new file.
     */
    public boolean isNewFileAction() {
        return false;
    }

    /**
     * Execute the action.
     * 
     * @return
     */
    @Override
    public abstract void run();
}

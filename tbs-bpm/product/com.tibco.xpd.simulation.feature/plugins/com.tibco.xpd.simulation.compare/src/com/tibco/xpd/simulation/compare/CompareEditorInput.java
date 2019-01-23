/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * @author nwilson
 */
public class CompareEditorInput implements IEditorInput {
    /** The resources to compare. */
    private IResource[] resources;    
    /**
     * @param resources The resources to compare.
     */
    public CompareEditorInput(final IResource[] resources) {
        this.resources = resources;
    }

    /**
     * @return true.
     * @see org.eclipse.ui.IEditorInput#exists()
     */
    public boolean exists() {
        return true;
    }

    /**
     * @return The icon to display for the comparison editor.
     * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
     */
    public ImageDescriptor getImageDescriptor() {
        return ComparePlugin.getImageDescriptor("icons/SimulationCompare.gif"); //$NON-NLS-1$
    }

    /**
     * @return The name of the editor input.
     * @see org.eclipse.ui.IEditorInput#getName()
     */
    public String getName() {
        return ""; //$NON-NLS-1$
    }

    /**
     * @return null, not used.
     * @see org.eclipse.ui.IEditorInput#getPersistable()
     */
    public IPersistableElement getPersistable() {
        return null;
    }

    /**
     * @return ""
     * @see org.eclipse.ui.IEditorInput#getToolTipText()
     */
    public String getToolTipText() {
        return ""; //$NON-NLS-1$
    }

    /**
     * Allows this class to be adapted to an IResource[] class.
     * @param adapter The class to adapt to.
     * @return The adapted object.
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(final Class adapter) {
        if (adapter == IResource[].class) {
            return resources;
        }
        return null;
    }

    /**
     * @return The resources.
     */
    public IResource[] getResources() {
        IResource[] resources = new IResource[0];
        return resources;
    }
}

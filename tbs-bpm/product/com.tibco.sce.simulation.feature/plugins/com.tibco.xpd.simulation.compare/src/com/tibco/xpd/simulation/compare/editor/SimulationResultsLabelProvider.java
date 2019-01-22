/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.editor;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.simulation.compare.ComparePlugin;

/**
 * @author nwilson
 */
public class SimulationResultsLabelProvider implements ILabelProvider {
    /** The image to use for a .sim file. */
    private Image simImage;

    /**
     * 
     */
    public SimulationResultsLabelProvider() {
        ImageDescriptor descriptor =
                ComparePlugin.getImageDescriptor("icons/SimulationResults.gif"); //$NON-NLS-1$
        simImage = descriptor.createImage();
    }

    /**
     * @param element The element to get an image for.
     * @return The image for the given element.
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     */
    public Image getImage(final Object element) {
        return simImage;
    }

    /**
     * @param element The element to get text for.
     * @return The text for the given element.
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    public String getText(final Object element) {
        if (element instanceof IResource) {
            return ((IResource) element).getName();
        }
        return element.toString();
    }

    /**
     * Not supported.
     * 
     * @param listener n/a
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(
     *      org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void addListener(final ILabelProviderListener listener) {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose() {
        simImage.dispose();
    }

    /**
     * Always returns false.
     * 
     * @param element n/a
     * @param property n/a
     * @return false.
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(
     *      java.lang.Object, java.lang.String)
     */
    public boolean isLabelProperty(final Object element, final String property) {
        return false;
    }

    /**
     * Not supported.
     * 
     * @param listener n/a
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(
     *      org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void removeListener(final ILabelProviderListener listener) {
    }

}

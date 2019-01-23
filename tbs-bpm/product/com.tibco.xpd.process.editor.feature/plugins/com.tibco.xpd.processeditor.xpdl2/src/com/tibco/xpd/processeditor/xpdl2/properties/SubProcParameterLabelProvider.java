/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @author nwilson
 */
public class SubProcParameterLabelProvider implements ILabelProvider {

    /**
     * @param element
     *            The element to get the image for.
     * @return The image for the element.
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage( java.lang.Object)
     */
    public Image getImage(Object element) {
        Image image = null;
        if (element instanceof EObject) {
            image = WorkingCopyUtil.getImage((EObject) element);
        }
        return image;
    }

    /**
     * @param element
     *            The element to get the text for.
     * @return The text label for the element.
     * @see org.eclipse.jface.viewers.ILabelProvider#getText( java.lang.Object)
     */
    public String getText(Object element) {
        String text;
        if (element instanceof EObject) {
            text = WorkingCopyUtil.getText((EObject) element);
        } else {
            text = element.toString();
        }
        return text;
    }

    /**
     * Not supported.
     * 
     * @param listener
     *            n/a
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(
     *      org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void addListener(ILabelProviderListener listener) {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose() {
    }

    /**
     * @param element
     *            The elemement to check.
     * @param property
     *            The element property.
     * @return false.
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(
     *      java.lang.Object, java.lang.String)
     */
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    /**
     * Not supported.
     * 
     * @param listener
     *            n/a
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(
     *      org.eclipse.jface.viewers.ILabelProviderListener)
     */
    public void removeListener(ILabelProviderListener listener) {
    }

}

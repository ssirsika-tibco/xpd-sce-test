/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.custom.terminalstates;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 *
 *
 * @author nwilson
 * @since 2 Apr 2019
 */
public class TerminalStateLabelProvider implements ILabelProvider {

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     *
     * @param listener
     */
    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     *
     */
    @Override
    public void dispose() {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     *
     * @param element
     * @param property
     * @return
     */
    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     *
     * @param listener
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     *
     * @param element
     * @return
     */
    @Override
    public Image getImage(Object element) {
        return Activator.getDefault().getImageRegistry().get(BOMImages.ENUMLIT);
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     *
     * @param element
     * @return
     */
    @Override
    public String getText(Object element) {
        if (element instanceof EnumerationLiteral) {
            return PrimitivesUtil.getDisplayLabel((EnumerationLiteral) element,
                    true);
        }
        return null;
    }

}

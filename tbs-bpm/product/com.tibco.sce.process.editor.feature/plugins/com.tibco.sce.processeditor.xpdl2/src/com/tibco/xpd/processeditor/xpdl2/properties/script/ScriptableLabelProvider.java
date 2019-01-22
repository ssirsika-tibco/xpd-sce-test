/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.xpdExtension.ScriptInformation;

/**
 * @author nwilson
 */
public class ScriptableLabelProvider implements ILabelProvider, IColorProvider {

    private ILabelProvider provider;

    private Image script;

    /**
     * @param parameterLabelProvider
     */
    public ScriptableLabelProvider(ILabelProvider provider) {
        this.provider = provider;

    }

    /**
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element) {
        Image image;
        if (element instanceof ScriptInformation) {
            if (script == null) {
                ImageRegistry ir =
                        Xpdl2ProcessEditorPlugin.getDefault()
                                .getImageRegistry();
                script = ir.get(ProcessEditorConstants.IMG_SCRIPT);
            }
            image = script;
        } else {
            image = provider.getImage(element);
        }
        return image;
    }

    /**
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element) {
        String text = ""; //$NON-NLS-1$
        if (element instanceof ScriptInformation) {
            ScriptInformation details = (ScriptInformation) element;
            text = details.getName();
        } else {
            text = provider.getText(element);
        }
        return text;
    }

    /**
     * @param listener
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    @Override
    public void dispose() {
    }

    /**
     * @param element
     * @param property
     * @return
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     */
    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    /**
     * @param listener
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {
    }

    /**
     * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Color getForeground(Object element) {
        /*
         * Give oppportunity to delegate label provider to provider override
         * color.
         */
        if (provider instanceof IColorProvider) {
            return ((IColorProvider) provider).getForeground(element);
        }
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Color getBackground(Object element) {
        /*
         * Give oppportunity to delegate label provider to provider override
         * color.
         */
        if (provider instanceof IColorProvider) {
            return ((IColorProvider) provider).getBackground(element);
        }
        return null;
    }
}

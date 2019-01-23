/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.staticcontent;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.DataField;

/**
 * Label provider for the Data mapper Content Contributor that supports the use
 * of the static content objects (such as static JavaScript class properties.
 * <p>
 * This handles labelling of all content provided as the following object
 * types...
 * <li>{@link VirtualDataMapperFolder}</li>
 * <li>{@link StaticContentDataMapperElement}</li>
 * <li>Derivatives of {@link AbstractStaticContentDataMapperElement}</li>
 * <li>{@link ConceptPath}</li>
 * 
 */
public class StaticContentDataMapperLabelProvider implements ILabelProvider,
        IColorProvider {

    private ParameterLabelProvider conceptPathLabelProvider = null;

    /**
     * 
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

        if (element instanceof VirtualDataMapperFolder) {
            return ((VirtualDataMapperFolder) element).getImage();

        } else if (element instanceof ConceptPath) {
            if (conceptPathLabelProvider == null) {
                conceptPathLabelProvider = new ParameterLabelProvider();
            }

            return conceptPathLabelProvider.getImage(element);
        }

        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getText(Object objectFromMappingOrContent) {
        if (objectFromMappingOrContent instanceof VirtualDataMapperFolder) {
            return ((VirtualDataMapperFolder) objectFromMappingOrContent)
                    .getLabel();

        } else if (objectFromMappingOrContent instanceof StaticContentDataMapperElement) {

            /*
             * XPD-7448: Append the Human readable data type to the text.
             */
            String readableDataType = null;

            if (objectFromMappingOrContent instanceof ConceptPath) {
                if (conceptPathLabelProvider == null) {
                    conceptPathLabelProvider = new ParameterLabelProvider();
                }

                readableDataType =
                        conceptPathLabelProvider
                                .getReadablePrdDataTpe(((ConceptPath) objectFromMappingOrContent)
                                        .getItem());
            }

            if (readableDataType != null) {
                return String
                        .format("%1$s : %2$s", //$NON-NLS-1$
                                ((StaticContentDataMapperElement) objectFromMappingOrContent)
                                        .getLabel(),
                                readableDataType);
            } else {

                return ((StaticContentDataMapperElement) objectFromMappingOrContent)
                        .getLabel();
            }

        } else if (objectFromMappingOrContent instanceof ConceptPath) {
            if (conceptPathLabelProvider == null) {
                conceptPathLabelProvider = new ParameterLabelProvider();
            }

            return conceptPathLabelProvider.getText(objectFromMappingOrContent);
        }

        return "???"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public Color getForeground(Object element) {
        if (element instanceof ConceptPath) {
            Object item = ((ConceptPath) element).getItem();

            if (item instanceof DataField) {
                if (((DataField) item).isReadOnly()) {
                    return ColorConstants.gray;
                }
            }
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
        return null;
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * 
     * @param listener
     */
    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
    }

    /**
     * 
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

}
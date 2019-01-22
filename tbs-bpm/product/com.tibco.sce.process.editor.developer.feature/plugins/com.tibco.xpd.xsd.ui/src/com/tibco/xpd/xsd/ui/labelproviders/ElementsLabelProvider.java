/*

 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.

 */
package com.tibco.xpd.xsd.ui.labelproviders;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.xsd.ui.models.ElementModel;

/**
 * Label provider for the table dealing with the
 * input and output elements for xml over xml descriptor imports 
 * @author glewis
 */
public class ElementsLabelProvider implements ITableLabelProvider {
    
    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     *
     * @param listener
     */
    public void addListener(ILabelProviderListener listener) {  
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     *
     */
    public void dispose() {            
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
     *
     * @param element
     * @param property
     * @return
     */
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     *
     * @param listener
     */
    public void removeListener(ILabelProviderListener listener) {            
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     *
     * @param element
     * @param columnIndex
     * @return
     */
    public Image getColumnImage(Object element, int columnIndex) {
        ElementModel model = (ElementModel)element;        
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     *
     * @param element
     * @param columnIndex
     * @return
     */
    public String getColumnText(Object element, int columnIndex) {
        ElementModel model = (ElementModel)element;
        switch (columnIndex){
        case 0:
            return model.getName();            
        case 1:
            return model.getType();            
        }
        return null;
    }
}
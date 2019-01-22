/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.providers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.om.resources.ui.models.OMTypeDetails;
import com.tibco.xpd.ui.properties.table.CheckboxCellEditor;

/**
 * Label provider for the om types picker.
 * 
 * @author glewis
 * 
 */
public class OMTypesLabelProvider implements ITableLabelProvider,
        ITableColorProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang
     * .Object, int)
     */
    public Image getColumnImage(Object element, int columnIndex) {
        switch (columnIndex) {
        case 0:
            if (element instanceof OMTypeDetails) {
                OMTypeDetails omTypeDetails = (OMTypeDetails) element;

                if (omTypeDetails.isSelected()) {
                    return CheckboxCellEditor.getImgChecked();

                } else {
                    return CheckboxCellEditor.getImgUnchecked();
                }
            }
            break;
        default:
            return null;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang
     * .Object, int)
     */
    public String getColumnText(Object element, int columnIndex) {
        if (element instanceof OMTypeDetails) {
            OMTypeDetails omTypeDetails = (OMTypeDetails) element;
            switch (columnIndex) {
            case 1:
                String fullClsName = omTypeDetails.getTypeCls().getName();
                int index = fullClsName.lastIndexOf('.') + 1;
                return fullClsName.substring(index, fullClsName.length());
            default:
                return null;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
     * jface.viewers.ILabelProviderListener)
     */
    public void addListener(ILabelProviderListener listener) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
     * .Object, java.lang.String)
     */
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
     * .jface.viewers.ILabelProviderListener)
     */
    public void removeListener(ILabelProviderListener listener) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITableColorProvider#getBackground(java.lang
     * .Object, int)
     */
    public Color getBackground(Object element, int columnIndex) {
        return Display.getDefault().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITableColorProvider#getForeground(java.lang
     * .Object, int)
     */
    public Color getForeground(Object element, int columnIndex) {
        return Display.getDefault().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
    }
}

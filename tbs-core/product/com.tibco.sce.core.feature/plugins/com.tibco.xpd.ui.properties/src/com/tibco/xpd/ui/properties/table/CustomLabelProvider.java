/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties.table;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * <p>
 * <i>Created: Nov 10, 2006</i>.
 * 
 * @author mmaciuki
 */
class CustomLabelProvider implements ITableLabelProvider, ITableColorProvider {

    /**
     * TODO comment me.
     */
    private final ITableLabelProvider labelProvider;

    /**
     * TODO comment me.
     * 
     * @param labelProvider
     *            param
     */
    public CustomLabelProvider(ITableLabelProvider labelProvider) {
        this.labelProvider = labelProvider;
    }

    /**
     * TODO comment me.
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * @param listener
     *            param
     */
    public void addListener(ILabelProviderListener listener) {
        this.labelProvider.addListener(listener);
    }

    /**
     * TODO comment me.
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose() {
        this.labelProvider.dispose();
    }

    /**
     * TODO comment me.
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     * @param element
     *            param
     * @param property
     *            param
     * @return boolean
     */
    public boolean isLabelProperty(Object element, String property) {
        boolean result;
        if (element != PotentialNewRowData.INSTANCE) {
            result = this.labelProvider.isLabelProperty(element, property);
        } else {
            result = false;
        }

        return result;
    }

    /**
     * TODO comment me.
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     * @param listener
     *            param
     */
    public void removeListener(ILabelProviderListener listener) {
        this.labelProvider.removeListener(listener);
    }

    /**
     * TODO comment me.
     * 
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
     *      int)
     * @param element
     *            param
     * @param columnIndex
     *            param
     * @return Image
     */
    public Image getColumnImage(Object element, int columnIndex) {
        Image result;
        if (element != PotentialNewRowData.INSTANCE) {
            result = this.labelProvider.getColumnImage(element, columnIndex);
        } else {
            result = null;
        }

        return result;
    }

    /**
     * TODO comment me.
     * 
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
     *      int)
     * @param element
     *            param
     * @param columnIndex
     *            param
     * @return String
     */
    public String getColumnText(Object element, int columnIndex) {
        String result;
        if ((element == PotentialNewRowData.INSTANCE) && (columnIndex == 0)) {
            result = "..."; //$NON-NLS-1$
        } else if (element != PotentialNewRowData.INSTANCE) {
            result = this.labelProvider.getColumnText(element, columnIndex);
        } else {
            result = null;
        }

        return result;
    }

    public Color getBackground(Object element, int columnIndex) {
        if(labelProvider instanceof ITableColorProvider){
            return ((ITableColorProvider)labelProvider).getBackground(element, columnIndex);
            
        }
        return Display.getDefault().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
    }

    public Color getForeground(Object element, int columnIndex) {
        if(labelProvider instanceof ITableColorProvider){
            return ((ITableColorProvider)labelProvider).getForeground(element, columnIndex);
            
        }
        return Display.getDefault().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
    }

}

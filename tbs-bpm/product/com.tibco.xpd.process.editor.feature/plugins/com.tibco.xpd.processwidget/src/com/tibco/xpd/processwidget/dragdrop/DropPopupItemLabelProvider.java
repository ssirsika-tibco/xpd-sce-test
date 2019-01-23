/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.dragdrop;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.adapters.DropObjectPopupItem;

public class DropPopupItemLabelProvider implements ILabelProvider {
    public void addListener(ILabelProviderListener listener) {
    }

    public void dispose() {
    }

    public Image getImage(Object element) {
        DropObjectPopupItem item = (DropObjectPopupItem) element;
        return item.getImage();
    }

    public String getText(Object element) {
        if (element instanceof DropObjectPopupItem) {
            DropObjectPopupItem item = (DropObjectPopupItem) element;
            return item.getLabel();
        }
        return null;
    }

    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    public void removeListener(ILabelProviderListener listener) {
    }
}
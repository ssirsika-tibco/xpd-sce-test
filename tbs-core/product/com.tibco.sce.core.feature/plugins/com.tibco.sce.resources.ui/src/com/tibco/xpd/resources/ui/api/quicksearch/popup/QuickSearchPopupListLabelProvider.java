/**
 * SearchPopupLabelProvider.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.api.quicksearch.popup;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.internal.quickSearch.QuickSearchElementAndContributor;


class QuickSearchPopupListLabelProvider implements ITableLabelProvider {

    public Image getColumnImage(Object element, int columnIndex) {
        Image img = null;
        if (columnIndex == 0) {
            if (element instanceof QuickSearchElementAndContributor) {
                img = ((QuickSearchElementAndContributor) element).getImage();

            } else {
                // Handle the couple of special content elements.
                img =
                        XpdResourcesUIActivator.getDefault().getImageRegistry()
                                .get(XpdResourcesUIConstants.IMG_INFO_ICON);
            }
        }
        return img;
    }

    public String getColumnText(Object element, int columnIndex) {
        String text = null;

        if (columnIndex == 0) {
            if (element instanceof QuickSearchElementAndContributor) {
                text = ((QuickSearchElementAndContributor) element).getLabel();

                if (text == null || text.length() == 0) {
                    text =
                            "<" //$NON-NLS-1$
                                    + ((QuickSearchElementAndContributor) element)
                                            .getElementTypeName() + ">"; //$NON-NLS-1$
                }

            } else {
                // Handle the couple of special content elements.
                if (QuickSearchPopupListContentProvider.ENTER_PATTERN_ELEMENT
                        .equals(element)) {
                    text = Messages.QuickSearchPopupListLabelProvider_EnterSearchPattern_label;
                } else if (QuickSearchPopupListContentProvider.NO_MATCHING_ELEMENT
                        .equals(element)) {
                    text = Messages.QuickSearchPopupListLabelProvider_NoMatchingItems_label;
                }
            }
        }

        return text == null ? "" : text; //$NON-NLS-1$
    }

    public void addListener(ILabelProviderListener listener) {
    }

    public void dispose() {
    }

    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    public void removeListener(ILabelProviderListener listener) {
    }
}
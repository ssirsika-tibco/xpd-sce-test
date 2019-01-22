/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Base class for {@link IPickerLabelProvider}s. Typically only
 * {@link #getText(PickerItem)} and {@link #getImage(PickerItem)} methods are
 * overridden.
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public class BasePickerLabelProvider extends BaseLabelProvider implements
        IPickerLabelProvider {
    /**
     * {@inheritDoc}
     */
    public String getText(PickerItem pickerItem) {
        String name = pickerItem.getName();
        return name == null ? "" : name; //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     */
    public Image getImage(PickerItem pickerItem) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image decorateSelectedItemImage(Image image, PickerItem element) {
        return image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String decorateSelectedItemText(String text, PickerItem element) {
        return text;
    }

    /**
     * By default provides image of a resource in which picker item is
     * contained.
     */
    @Override
    public Image getStatusImage(PickerItem item) {
        final WorkbenchLabelProvider wbLabelProvider =
                new WorkbenchLabelProvider();
        if (item.getURI() == null) {
            return null;
        }
        URI uri = URI.createURI(item.getURI());
        if (uri != null) {
            uri = uri.trimFragment();
            String platformString = uri.toPlatformString(true);
            if (platformString != null) {
                IResource resource =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .findMember(platformString);
                if (resource != null) {
                    return wbLabelProvider.getImage(resource);
                }
            }
        }
        return null;
    }

    /**
     * By default provides full path of a resource in which picker item is
     * contained.
     */
    @Override
    public String getStatusText(PickerItem item) {
        String text = null;

        if (item.getURI() == null) {
            return item.getQualifiedName();
        }
        URI uri = URI.createURI(item.getURI());
        if (uri != null) {
            uri = uri.trimFragment();
            // If this is not a platform URI then just display the
            // qualification
            if (uri.isPlatformResource()) {
                text = uri.toPlatformString(true);
            } else {
                text = item.getQualifiedName();
            }
        }
        return text != null ? text : ""; //$NON-NLS-1$
    }

}

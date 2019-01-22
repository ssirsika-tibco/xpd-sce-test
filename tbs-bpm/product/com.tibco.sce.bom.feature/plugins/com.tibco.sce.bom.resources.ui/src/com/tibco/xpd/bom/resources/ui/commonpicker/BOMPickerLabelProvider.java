/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.commonpicker;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.ui.picker.BasePickerLabelProvider;
import com.tibco.xpd.resources.ui.picker.PickerItem;

/**
 * Provides labels for BOM picker items.
 * 
 * @see BOMPickerLabelProvider
 * 
 * @author Jan Arciuchiewicz
 */
public class BOMPickerLabelProvider extends BasePickerLabelProvider implements
        IPluginContribution {

    @Override
    public Image getImage(PickerItem pickerItem) {
        String imgURIStr =
                pickerItem.get(BOMResourcesPlugin.INDEXER_ATTRIBUTE_IMAGE_URI);
        if (imgURIStr != null) {
            URI imageUri = URI.createURI(imgURIStr);
            if (imageUri != null) {
                return ExtendedImageRegistry.getInstance().getImage(imageUri);
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(PickerItem pickerItem) {
        String text = null;
        String name = pickerItem.getName();
        // Get display name
        String displayLabel =
                pickerItem.get(BOMIndexerService.INDEXER_ATTR_DISPLAY_LABEL);

        // Check if label and name should be displayed
        boolean isNameFiltered = WorkbenchActivityHelper.filterItem(this);
        if (displayLabel != null) {
            if (isNameFiltered) {
                text = displayLabel;
            } else {
                text = displayLabel + " (" + name + ")"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        } else {
            text = name;
        }

        String qualifiedName = pickerItem.getQualifiedName();
        if (qualifiedName.length() > 0) {
            int pos =
                    qualifiedName.indexOf(BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR
                            + name);
            if (pos != -1) {
                String packageName = qualifiedName.substring(0, pos);
                text += " - " + packageName; //$NON-NLS-1$
            }
        }

        return text;
    }

    public String getLocalId() {
        return "developer-name"; //$NON-NLS-1$
    }

    public String getPluginId() {
        return Activator.PLUGIN_ID;
    }
}

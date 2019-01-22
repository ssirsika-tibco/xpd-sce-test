/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui.commonpicker;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.globalSignalDefinition.indexer.GsdResourceIndexProvider;
import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.resources.ui.picker.BasePickerLabelProvider;
import com.tibco.xpd.resources.ui.picker.PickerItem;

/**
 * Provides labels for Global Signal picker items.
 * 
 * @see GsdTypeQuery
 * 
 * @author kthombar
 * @since Feb 9, 2015
 */
public class GsdPickerLabelProvider extends BasePickerLabelProvider implements
        IPluginContribution {

    /**
     * @see com.tibco.xpd.resources.ui.picker.BasePickerLabelProvider#getImage(com.tibco.xpd.resources.ui.picker.PickerItem)
     * 
     * @param pickerItem
     * @return
     */
    @Override
    public Image getImage(PickerItem pickerItem) {

        URI imageUri =
                URI.createPlatformPluginURI(GsdResourcePlugin.PLUGIN_ID
                        + "/" //$NON-NLS-1$
                        + GsdResourcePlugin.GLOBAL_SIGNAL_IMAGE,
                        true);

        if (imageUri != null) {
            return ExtendedImageRegistry.getInstance().getImage(imageUri);
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.picker.BasePickerLabelProvider#getText(com.tibco.xpd.resources.ui.picker.PickerItem)
     * 
     * @param pickerItem
     * @return
     */
    @Override
    public String getText(PickerItem pickerItem) {
        String text = pickerItem.getName();

        String gsdProjectId =
                pickerItem
                        .get(GsdResourceIndexProvider.INDEXER_ATTRIBUTE_GSD_FILE_NAME);

        if (gsdProjectId != null && gsdProjectId.length() != 0) {
            text = text + " - " + gsdProjectId; //$NON-NLS-1$
        }

        return text;
    }

    /**
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     * 
     * @return
     */
    @Override
    public String getLocalId() {
        return "developer-name"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     * 
     * @return
     */
    @Override
    public String getPluginId() {

        return GsdResourcePlugin.PLUGIN_ID;
    }

}

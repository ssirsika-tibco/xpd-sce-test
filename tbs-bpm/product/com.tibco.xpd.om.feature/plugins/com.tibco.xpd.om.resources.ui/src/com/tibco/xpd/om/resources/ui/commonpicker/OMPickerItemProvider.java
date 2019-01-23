/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.commonpicker;

import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.picker.BasePickerItemProvider;
import com.tibco.xpd.resources.ui.picker.PickerContentExtension;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.resources.ui.picker.PickerItemImpl;

/**
 * Provides picker items for Org. Models.
 * 
 * @author Jan Arciuchiewicz
 */
public class OMPickerItemProvider extends BasePickerItemProvider {

    @Override
    public String getIndexerId() {
        return OMResourcesActivator.OM_INDEXER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PickerItem createPickerItem(IndexerItem indexerItem,
            PickerContentExtension pickerExtension) {
        PickerItemImpl pickerItem =
                new PickerItemImpl(indexerItem, pickerExtension);
        String qualifiedName = pickerItem.getName();
        pickerItem.setName(getOMItemName(qualifiedName));
        pickerItem.setQualifiedName(qualifiedName);
        return pickerItem;
    }

    private String getOMItemName(String qualifiedName) {
        String name = null;
        if (qualifiedName != null) {
            int idx =
                    qualifiedName
                            .lastIndexOf(OMWorkingCopy.JAVA_PACKAGE_SEPARATOR);

            if (idx >= 0) {
                name = qualifiedName.substring(idx + 1);
            } else {
                name = qualifiedName;
            }
        }
        return name;
    }
}

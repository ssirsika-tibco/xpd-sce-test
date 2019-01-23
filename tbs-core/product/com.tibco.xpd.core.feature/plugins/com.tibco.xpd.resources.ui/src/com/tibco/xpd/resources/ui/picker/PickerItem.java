/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker;

import com.tibco.xpd.resources.indexer.IndexerItem;

/**
 * Item displayed in a CommonPicker. It serves as a proxy for the object to be
 * displayed in a picker. It extends {@link IndexerItem} but doesn't necessary
 * come as a result of a indexer query.
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public interface PickerItem extends IndexerItem {

    /**
     * Returns PickerItemProvider for the item.
     * 
     * @return the provider.
     */
    PickerContentExtension getPickerExtension();

    /**
     * Returns the qualified name of the item.
     * 
     * @return the qualified name of the item.
     */
    String getQualifiedName();
}

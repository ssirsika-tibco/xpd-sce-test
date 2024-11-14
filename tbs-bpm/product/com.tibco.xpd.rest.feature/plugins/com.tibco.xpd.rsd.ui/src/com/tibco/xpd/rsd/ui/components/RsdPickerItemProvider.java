/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.components;

import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.ui.picker.BasePickerItemProvider;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.rsd.wc.RsdIndexProvider;

/**
 * Picker content provider for the RSD index.
 * 
 * @author nwilson
 * @since 30 Jan 2015
 */
public class RsdPickerItemProvider extends BasePickerItemProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIndexerId() {
        return RsdIndexProvider.INDEXER_ID;
    }

	/**
	 * 
	 * @see com.tibco.xpd.resources.ui.picker.BasePickerItemProvider#resolvePickerItem(com.tibco.xpd.resources.ui.picker.PickerItem)
	 *
	 * @param item
	 * @return
	 */
	@Override
	public Object resolvePickerItem(PickerItem item)
	{
		// Nikita ACE-8267 Return the actual picker item - the RestServiceTaskAdapter will handle the difference between
		// a RSD method and a Swagger operation
		return item;
	}
}

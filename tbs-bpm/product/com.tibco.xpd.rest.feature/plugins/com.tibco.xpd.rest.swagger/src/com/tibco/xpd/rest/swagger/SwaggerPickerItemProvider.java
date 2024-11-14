/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rest.swagger;

import com.tibco.xpd.resources.ui.picker.BasePickerItemProvider;
import com.tibco.xpd.resources.ui.picker.PickerItem;

/**
 * 
 * Picker content provider for the Swagger index.
 *
 * @author nkelkar
 * @since Aug 8, 2024
 */
public class SwaggerPickerItemProvider extends BasePickerItemProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIndexerId() {
		return SwaggerIndexProvider.INDEXER_ID;
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

/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.components;

import com.tibco.xpd.resources.ui.picker.BasePickerItemProvider;
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

}

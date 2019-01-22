/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui.commonpicker;

import com.tibco.xpd.globalSignalDefinition.indexer.GsdResourceIndexProvider;
import com.tibco.xpd.resources.ui.picker.BasePickerItemProvider;

/**
 * Provides picker items for BOM specific types. Type constants are specified in
 * {@link GsdTypeQuery}.
 * 
 * @see GsdTypeQuery
 * 
 * @author kthombar
 * @since Feb 9, 2015
 */
public class GsdPickerItemProvider extends BasePickerItemProvider {

    /**
     * @see com.tibco.xpd.resources.ui.picker.BasePickerItemProvider#getIndexerId()
     * 
     * @return
     */
    @Override
    protected String getIndexerId() {
        /*
         * return the indexer ID.
         */
        return GsdResourceIndexProvider.GSD_INDEXER_ID;
    }
}

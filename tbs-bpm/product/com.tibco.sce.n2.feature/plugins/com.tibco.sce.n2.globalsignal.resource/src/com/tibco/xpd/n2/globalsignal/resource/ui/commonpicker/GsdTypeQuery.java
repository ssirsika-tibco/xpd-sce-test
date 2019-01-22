/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui.commonpicker;

import com.tibco.xpd.globalSignalDefinition.indexer.GsdResourceIndexProvider;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;

/**
 * Helper class to create Global Signal Picker type queries. This class also
 * provide constants for all Global Signal picker item types.
 * 
 * @author kthombar
 * @since Feb 9, 2015
 */
public class GsdTypeQuery extends PickerTypeQuery {
    public static String GLOBAL_SIGNAL_PICKER_EXTENSION_ID =
            "com.tibco.xpd.n2.globalsignal.resource.pickerContentProvider"; //$NON-NLS-1$

    public static String QUERY_GLOBAL_SIGNAL =
            GsdResourceIndexProvider.GLOBAL_SIGNAL_REF_INDEX_TYPE;

    /**
     * Creates a GSD type query.
     * 
     * @param types
     */
    public GsdTypeQuery(String... types) {
        super(GLOBAL_SIGNAL_PICKER_EXTENSION_ID, types);
    }
}

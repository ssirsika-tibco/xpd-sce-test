/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.mapping;

import java.util.ArrayList;
import java.util.List;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Source Content provider for Throw Global Signal Events.
 * 
 * 
 * @author kthombar
 * @since Feb 5, 2015
 */
public class ThrowGlobalSignalMapperSourceContentProvider extends
    ProcessDataMapperConceptPathProvider {

    /**
     * Id given to dummy parameter created on problem accessing payload.
     */
    public static final String CANT_ACCESS_SIGNAL_PAYLOAD_PARAM_ID =
            "$$_CANT_ACCESS_PAYLOAD"; //$NON-NLS-1$

    /**
     * Source Content provider for Throw Global Signal Events.
     * 
     * @param direction
     */
    public ThrowGlobalSignalMapperSourceContentProvider() {
    }
    
    /**
     * @see com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider#getInScopeProcessData(com.tibco.xpd.xpdl2.Activity)
     *
     * @param activity
     * @return
     */
    @Override
    protected List<ProcessRelevantData> getInScopeProcessData(
            Activity activity) {
        
        List<ProcessRelevantData> allProcessRelevantData = super.getInScopeProcessData(activity);
        
        List<ProcessRelevantData> inScopeProcessData = new ArrayList<ProcessRelevantData>();
        
        /*
         * Filter out correlation data.
         */
        for (ProcessRelevantData eachProcessRelevantData : allProcessRelevantData) {
            if (eachProcessRelevantData instanceof DataField) {
                DataField currentDataField = (DataField) eachProcessRelevantData;
                if (!currentDataField.isCorrelation()) {
                    inScopeProcessData.add(eachProcessRelevantData);
                }
            } else {
                inScopeProcessData.add(eachProcessRelevantData);
            }
        }
        
        return inScopeProcessData;
    }
}

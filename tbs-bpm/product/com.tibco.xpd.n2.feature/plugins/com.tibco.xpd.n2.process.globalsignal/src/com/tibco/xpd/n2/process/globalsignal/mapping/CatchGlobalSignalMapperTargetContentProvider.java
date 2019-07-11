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
 * 
 * Target Content provider for Catch Global Signal.
 * 
 * @author kthombar
 * @since Feb 5, 2015
 */
public class CatchGlobalSignalMapperTargetContentProvider extends
    ProcessDataMapperConceptPathProvider {

    /**
     * Target Content provider for Catch Global Signal.
     * 
     * @param direction
     */
    public CatchGlobalSignalMapperTargetContentProvider() {
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

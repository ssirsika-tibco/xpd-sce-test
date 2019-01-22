/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.cds.script;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * 
 * 
 * @author rsawant
 * @since 26-Jun-2013
 */
public class CdsEngineScriptsFactoriesJavaScriptRelevantDataProvider extends
        CdsFactoriesJavaScriptRelevantDataProvider {

    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getAssociatedProcessRelevantData()
     * 
     * @return
     */
    @Override
    protected List<ProcessRelevantData> getAssociatedProcessRelevantData() {

        List<ProcessRelevantData> processDataList =
                new ArrayList<ProcessRelevantData>();
        if (getActivity() != null) {
            processDataList =
                    ProcessInterfaceUtil
                            .getAllAvailableRelevantDataForActivity(getActivity());

        } else {
            return super.getAssociatedProcessRelevantData();
        }
        return processDataList;
    }
}

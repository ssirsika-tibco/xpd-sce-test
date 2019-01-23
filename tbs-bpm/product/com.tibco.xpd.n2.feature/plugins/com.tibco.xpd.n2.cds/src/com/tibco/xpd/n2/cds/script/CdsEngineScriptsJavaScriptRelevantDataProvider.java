/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptRelevantDataProvider;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Default implementation for javascript of the Abstract class for engine
 * scripts {@link AbstractScriptRelevantDataProvider}.
 * 
 * @author mtorres
 */
public class CdsEngineScriptsJavaScriptRelevantDataProvider extends
        CdsDefaultJavaScriptRelevantDataProvider {

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

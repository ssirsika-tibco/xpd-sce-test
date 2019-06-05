/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.process.js.model;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.StartEvent;
/**
 * @author mtorres
 */
public class TimeEventScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

    /**
     * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getAssociatedProcessRelevantData()
     * 
     * Sid ACE-1317 - cannot allow getScriptRelevantData() to be overridden anymore as it wraps the data up in a 
     * 
     * @return
     */
    @Override
    protected List<ProcessRelevantData> getAssociatedProcessRelevantData() {
        Process process = getProcess();
        List<ProcessRelevantData> processDataList = new ArrayList<ProcessRelevantData>();
        List<ProcessRelevantData> parametersOnlyDataList = new ArrayList<ProcessRelevantData>();
        Activity activity = getActivity();
        if (activity != null) {
            processDataList = ProcessInterfaceUtil
                    .getAssociatedProcessRelevantDataForActivity(activity);
        }
        // when it is a Start event, you only want to show Formal Parameters
        if (activity != null && activity.getEvent() instanceof StartEvent) {
            for (ProcessRelevantData processRelevantData : processDataList) {
                if (processRelevantData instanceof FormalParameter) {
                    parametersOnlyDataList.add(processRelevantData);
                }
            }
        }
        // when it is a Intermediate event, you want to show both Formal
        // Parameters and data fields
        if (activity != null
                && activity.getEvent() instanceof IntermediateEvent) {
            parametersOnlyDataList.addAll(processDataList);
        }
        
        /*
         * Sid ACE-1317 - cannot allow getScriptRelevantData() to be overridden
         * anymore as it wraps the data up in a
         */
        return parametersOnlyDataList;

    }
    

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.process.js.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
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

    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
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
        if (process != null) {
            IProject project = WorkingCopyUtil.getProjectFor(process);
            if (project != null) {
                List<IScriptRelevantData> srdList =
                        convertToScriptRelevantData(parametersOnlyDataList);
                if (srdList != null) {
                    return srdList;
                }
            }
        }
        return Collections.emptyList();
    }
    
    

}

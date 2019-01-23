/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.process.js.model;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.DecisionStandardMappingUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
/**
 * @author mtorres
 */
public class DecFlowScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

    // Prefix of the temp variable
    public static final String TEMP_VARIABLE_PREFIX = "OUTPARAM_";//$NON-NLS-1$
    
    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        if (isMappedScript()) {
            if (isInputScript()) {
                return super.getScriptRelevantDataList();
            } else {
                return getOutputDecFlowScriptRelevantData();
            }
        } else {
            if (isInputScript()) {
                return super.getScriptRelevantDataList();
            } else {
                return getOutputDecFlowScriptRelevantData();
            }
        }
    }
    
    private List<IScriptRelevantData> getOutputDecFlowScriptRelevantData() {
        List<IScriptRelevantData> scriptRelevantData =
                new ArrayList<IScriptRelevantData>();
        Activity activity = getActivity();
        if (activity != null) {
            List<FormalParameter> decFlowOutModeParams =
                    DecisionStandardMappingUtil.getDecFlowFormalParameters(activity,
                            MappingDirection.OUT);
            List<IScriptRelevantData> tempAdditionalData =
                    convertToScriptRelevantData(new ArrayList<ProcessRelevantData>(
                            decFlowOutModeParams));
            if (tempAdditionalData != null) {
                for (IScriptRelevantData tempPRDData : tempAdditionalData) {
                    tempPRDData.setName(TEMP_VARIABLE_PREFIX + tempPRDData.getName());
                    scriptRelevantData.add(tempPRDData);    
                }                
            }
        }
        return scriptRelevantData;
    }
    
}

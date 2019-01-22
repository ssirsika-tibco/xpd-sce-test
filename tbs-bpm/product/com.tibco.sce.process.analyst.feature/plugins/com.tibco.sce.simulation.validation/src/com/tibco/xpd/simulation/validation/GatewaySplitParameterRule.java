/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.simulation.SplitParameterType;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class GatewaySplitParameterRule extends ProcessValidationRule {

    /** Invalid parameter issue ID. */
    public static final String INVALID_VALUE = "sim.invalidSplitParameterSpecified"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {        
        if (process != null){
            List formalParameters = ProcessInterfaceUtil.getAllFormalParameters(process);
            List<String> fpListId = new ArrayList<String>();
            for (Object next : formalParameters) {
                FormalParameter element = (FormalParameter) next;
                fpListId.add(element.getName());
            }       
            for (Object next : activities) {
                Activity activity = (Activity) next;
                SplitSimulationDataType activitySplitData = SimulationXpdlUtils
                        .getSplitSimulationData(activity);
                if (activitySplitData == null) {
                    continue;
                }
                boolean c = SimulationXpdlUtils.isConditionalSplit(activity);
                if (!c) {
                    continue;
                }
                SplitParameterType splitParameter = activitySplitData
                        .getSplitParameter();
                String parameterId = splitParameter.getParameterId();
                if (parameterId == null || parameterId.length() < 1) {
                    addIssue(INVALID_VALUE, activity);
                    continue;
                }
                boolean b = fpListId.contains(parameterId);
                if (!b) {
                    addIssue(INVALID_VALUE, activity);
                }
            }
        }        
    }

}

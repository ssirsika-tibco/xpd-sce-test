/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.EnumBasedExpressionType;
import com.tibco.xpd.simulation.ExpressionType;
import com.tibco.xpd.simulation.ParameterBasedDistribution;
import com.tibco.xpd.simulation.ParameterDependentDistribution;
import com.tibco.xpd.simulation.SimulationRealDistributionType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Checks that activities have simulation parameters associated with them and
 * that the parameter distribution is valid.
 * 
 * @author nwilson
 */
public class ActivityParameterRule extends ProcessValidationRule {

    /** Missing parameter issue ID. */
    public static final String PARAMETER_MISSING =
            "sim.activityParameterMissing"; //$NON-NLS-1$

    /** Invalid parameter distribution issue ID. */
    public static final String INVALID_DISTRIBUTION =
            "sim.invalidParameterBasedDistribution"; //$NON-NLS-1$


    /**
     * @param process The process to get parameter names for.
     * @return A list of parameter names.
     */
    private List getProcessParameterNames(Process process) {
        List<String> paramNames = new ArrayList<String>();
        List formalParameters = ProcessInterfaceUtil.getAllFormalParameters(process);
        for (Object next : formalParameters) {
            FormalParameter fp = (FormalParameter) next;
            paramNames.add(fp.getName());
        }
        return paramNames;
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {       
        List processParameterNames = null; 
        for (Object next : activities) {
            Activity activity = (Activity) next;
            
            if (processParameterNames == null){
                processParameterNames = getProcessParameterNames(activity.getProcess()); 
            }
            
            ActivitySimulationDataType simulationData =
                    SimulationXpdlUtils.getActivitySimulationData(activity);
            if (simulationData != null) {
                SimulationRealDistributionType dist =
                        simulationData.getDuration();
                if (dist != null) {
                    ParameterBasedDistribution paramDist =
                            dist.getParameterBasedDistribution();
                    if (paramDist != null) {
                        List dependantList =
                                paramDist.getParameterDependentDistributions();
                        boolean enumBasedExpressionFound = false;
                        for (Object nextj : dependantList) {
                            ParameterDependentDistribution dependant =
                                    (ParameterDependentDistribution) nextj;
                            ExpressionType expressionType =
                                    dependant.getExpression();
                            if (expressionType != null) {
                                EnumBasedExpressionType enumBasedExpression =
                                        expressionType.getEnumBasedExpression();
                                if (enumBasedExpression != null) {
                                    enumBasedExpressionFound = true;
                                    String paramName =
                                            (String) enumBasedExpression
                                                    .getParamName();
                                    boolean fpPresent =
                                            processParameterNames
                                                    .contains(paramName);
                                    if (!fpPresent) {
                                        addIssue(PARAMETER_MISSING, activity);
                                    }
                                }
                            }
                        }
                        if (!enumBasedExpressionFound) {
                            addIssue(INVALID_DISTRIBUTION, activity);
                        }
                    }
                }
            }
        }
        
    }
}

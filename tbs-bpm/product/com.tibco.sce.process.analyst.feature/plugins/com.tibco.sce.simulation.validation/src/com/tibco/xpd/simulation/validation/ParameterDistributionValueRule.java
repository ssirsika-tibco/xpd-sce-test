/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.EnumBasedExpressionType;
import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ExpressionType;
import com.tibco.xpd.simulation.ParameterBasedDistribution;
import com.tibco.xpd.simulation.ParameterDependentDistribution;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SimulationRealDistributionType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class ParameterDistributionValueRule extends ProcessValidationRule {

    /** Missing value issue ID. */
    public static final String ID = "sim.parameterDistributionMissingValue"; //$NON-NLS-1$

    /**
     * @param process The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(
     *      com.tibco.xpd.xpdl2.Process)
     */
    @Override
    public void validate(Process process) {
        
    }

    /**
     * @param process The process to get parameters for.
     * @return A map of parameter IDs to a list of values.
     */
    private Map<String, List<String>> getParameterValues(Process process) {
        Map<String, List<String>> paramNames = new HashMap<String, List<String>>();
        List paramDist =
                SimulationXpdlUtils.getWorkflowSimulationParameters(process);
        for (Object next : paramDist) {
            ParameterDistribution dist = (ParameterDistribution) next;
            List<String> values = getParameterValues(dist);
            paramNames.put(dist.getParameterId(), values);
        }
        return paramNames;
    }

    /**
     * @param dist The distribution.
     * @return A list of parameter values.
     */
    private List<String> getParameterValues(ParameterDistribution dist) {
        List<String> values = new ArrayList<String>();
        List enums = dist.getEnumerationValue();
        for (Object next : enums) {
            EnumerationValueType type = (EnumerationValueType) next;
            values.add(type.getValue());
        }
        return values;
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {
        Map<String, List<String>> parameterValues = null;      
        for (Object nexti : activities) {
            Activity activity = (Activity) nexti;
            if (parameterValues == null){
                parameterValues = getParameterValues(activity.getProcess());
            }
            ActivitySimulationDataType simulationData =
                    SimulationXpdlUtils.getActivitySimulationData(activity);
            boolean hasEnumBasedExpression = false;
            boolean valuesMatch = true;
            if (simulationData != null) {
                SimulationRealDistributionType dist =
                        simulationData.getDuration();
                if (dist != null) {
                    ParameterBasedDistribution paramDist =
                            dist.getParameterBasedDistribution();
                    if (paramDist != null) {
                        List dependantList =
                                paramDist.getParameterDependentDistributions();
                        for (Object nextj : dependantList) {
                            ParameterDependentDistribution dependant =
                                    (ParameterDependentDistribution) nextj;
                            ExpressionType expressionType =
                                    dependant.getExpression();
                            if (expressionType != null) {
                                EnumBasedExpressionType enumBasedExpression =
                                        expressionType.getEnumBasedExpression();
                                if (enumBasedExpression != null) {
                                    hasEnumBasedExpression = true;
                                    String param =
                                            (String) enumBasedExpression
                                                    .getParamName();
                                    String value =
                                            (String) enumBasedExpression
                                                    .getEnumValue();
                                    if (param != null && value != null) {
                                        List<String> values = parameterValues
                                                        .get(param);
                                        if (values == null
                                                || !values.contains(value)) {
                                            valuesMatch = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (hasEnumBasedExpression && !valuesMatch) {
                addIssue(ID, activity);
            }
        }        
    }
}

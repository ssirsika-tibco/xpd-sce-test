/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.StructuredConditionType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class TransitionSplitParameterValueRule extends ProcessValidationRule {

    /** No parameter issue ID. */
    public static final String ID = "sim.noTransitionParameterValueSpecified"; //$NON-NLS-1$

    /** Invalid parameter issue ID. */
    public static final String INVALID_VALUE =
            "sim.invalidTransitionParameterValueSpecified"; //$NON-NLS-1$

    /**
     * @param process The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(
     *      com.tibco.xpd.xpdl2.Process)
     */
    @Override
    public void validate(Process process) {
        List transitions = process.getTransitions();
        WorkflowProcessSimulationDataType workflowProcessSimulationData =
                SimulationXpdlUtils.getWorkflowProcessSimulationData(process);
        if (workflowProcessSimulationData == null) {
            return;
        }
        List parameterDistribution =
                workflowProcessSimulationData.getParameterDistribution();
        if (parameterDistribution == null) {
            return;
        }
        Map<String, List> map = new HashMap<String, List>();
        for (Object next : parameterDistribution) {
            ParameterDistribution element = (ParameterDistribution) next;
            String parameterId = element.getParameterId();
            List enumerationValue = element.getEnumerationValue();
            map.put(parameterId, enumerationValue);
        }
        for (Object next : transitions) {
            Transition tran = (Transition) next;
            Condition condition = tran.getCondition();
            if (condition == null) {
                continue;
            }
            if (!ConditionType.CONDITION_LITERAL.equals(condition.getType())) {
                continue;
            }
            TransitionSimulationDataType transitionSimulationData =
                    SimulationXpdlUtils.getTransitionSimulationData(tran);
            if (transitionSimulationData == null) {
                continue;
            }
            StructuredConditionType structuredCondition =
                    transitionSimulationData.getStructuredCondition();
            if (structuredCondition == null) {
                continue;
            }
            String parameterValue = structuredCondition.getParameterValue();
            if (parameterValue == null || parameterValue.length() < 1) {
                addIssue(ID, tran);
            } else {
                String parameterId = structuredCondition.getParameterId();
                List enumValues = (List) map.get(parameterId);
                boolean b = isEnumValuePresent(enumValues, parameterValue);
                if (!b) {
                    addIssue(INVALID_VALUE, tran);
                }
            }
        }
    }

    /**
     * @param enumValueList The enumeration value list.
     * @param enumValue The value to check for.
     * @return true if the value is in the list.
     */
    private boolean isEnumValuePresent(List enumValueList, String enumValue) {
        if (enumValueList == null) {
            return false;
        }
        for (Object next : enumValueList) {
            EnumerationValueType element = (EnumerationValueType) next;
            if (element.getValue().equals(enumValue)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {        
    }

}

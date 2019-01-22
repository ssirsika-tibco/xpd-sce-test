/* 
 ** 
 **  MODULE:             $RCSfile: GeneratorContext.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-01-24 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.preprocess;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.EnumerationValueType;
import com.tibco.xpd.simulation.ParameterDistribution;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Context class that holds the context of process generation information.
 * 
 * @author jarciuch
 */
public class GeneratorContext {

    private Map simProcessParameters;

    private Map formalParamsMap;

    private EditingDomain ed;

    private CompoundCommand compoundCmd;

    private Activity currentActivity;

    private Transition[] outTransitions;

    private SplitSimulationDataType splitData;

    private int defaultTransitionIndex = -1;

    private Process workflowProcess;

    public static class SimProcessParameter {
        private String id;

        private Map values;

        private boolean isUsedInSplit;

        private boolean created;

        public SimProcessParameter(String id, Map values) {
            this.id = id;
            this.values = values;
        }

        public SimProcessParameter(String id) {
            this.id = id;
            this.values = new LinkedHashMap();
        }

        public Map getValues() {
            return values;
        }

        public double getMeanParameterEnumWeight() {
            double sum = 0;
            int size = values.values().size();
            if (size == 0) {
                return 0.0d;
            }
            for (Iterator iter = values.values().iterator(); iter.hasNext();) {
                SimEnumValue e = (SimEnumValue) iter.next();
                sum += Math.abs(e.getWeightFactor());
            }
            return sum / size;
        }

        public String getId() {
            return id;
        }

        public boolean isUsedInSplit() {
            return isUsedInSplit;
        }

        public void setUsedInSplit(boolean isUsedInSplit) {
            this.isUsedInSplit = isUsedInSplit;
        }
    }

    public static class SimEnumValue {
        private String value;

        private double weightFactor;

        private boolean isUsedInCondition;

        private boolean created;

        public SimEnumValue(String value, double weightFactor) {
            this.value = value;
            this.weightFactor = weightFactor;
            isUsedInCondition = false;
            created = false;
        }

        public boolean isCreated() {
            return created;
        }

        public void setCreated(boolean created) {
            this.created = created;
        }

        public boolean isUsedInCondition() {
            return isUsedInCondition;
        }

        public void setUsedInCondition(boolean isUsedInCondition) {
            this.isUsedInCondition = isUsedInCondition;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public double getWeightFactor() {
            return weightFactor;
        }

        public void setWeightFactor(double weightFactor) {
            this.weightFactor = weightFactor;
        }
    }

    public void init(EditingDomain ed, CompoundCommand compoundCmd,
            Process process) {
        this.ed = ed;
        this.compoundCmd = compoundCmd;
        this.workflowProcess = process;
        simProcessParameters = createSimParametersFromProcess(process);
        formalParamsMap = createFormalParametersFromProcess(process);
    }

    private Map createFormalParametersFromProcess(Process process) {
        return SimulationXpdlUtils.getWorkflowParametersMap(process);
    }

    private Map createSimParametersFromProcess(Process process) {
        Map params = new HashMap();
        List simParamsList = SimulationXpdlUtils
                .getWorkflowSimulationParameters(process);
        for (Iterator iter = simParamsList.iterator(); iter.hasNext();) {
            Object next = iter.next();
            ParameterDistribution p = (ParameterDistribution) next;
            String paramId = p.getParameterId();
            List paramValuesList = p.getEnumerationValue();
            Map values = new LinkedHashMap();
            for (Iterator iterator = paramValuesList.iterator(); iterator
                    .hasNext();) {
                EnumerationValueType paramEnum = (EnumerationValueType) iterator
                        .next();
                values.put(paramEnum.getValue(), new SimEnumValue(paramEnum
                        .getValue(), paramEnum.getWeightingFactor()));
            }
            params.put(paramId, new SimProcessParameter(paramId, values));
        }
        return params;
    }

    public boolean existProcessParameters(String parameterId) {
        return simProcessParameters.containsKey(parameterId);
    }

    public SimProcessParameter getProcessParameter() {
        return (SimProcessParameter) simProcessParameters
                .get(getSplitParameterId());
    }

    public CompoundCommand getCompoundCommand() {
        return compoundCmd;
    }

    public void setCompoundCommand(CompoundCommand compoundCmd) {
        this.compoundCmd = compoundCmd;
    }

    public EditingDomain getEitingDomain() {
        return ed;
    }

    public void setEitingDomain(EditingDomain ed) {
        this.ed = ed;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        if (currentActivity != null) {
            this.currentActivity = currentActivity;
            this.outTransitions = SimulationXpdlUtils.getOutgoingTransitions(
                    (FlowContainer) currentActivity.eContainer(),
                    currentActivity);
            this.splitData = SimulationXpdlUtils
                    .getSplitSimulationData(currentActivity);
            this.defaultTransitionIndex = initDefaultTransitionIndex();
        } else {
            // clearing activity related context.
            this.currentActivity = null;
            this.outTransitions = null;
            this.splitData = null;
            this.defaultTransitionIndex = -1;
        }
    }

    public Transition[] getOutgoingTransitions() {
        return outTransitions;
    }

    public boolean hasSplitSimulationData() {
        return (splitData != null);
    }

    public boolean isParameterDeterminedSplit() {
        if (splitData != null) {
            return splitData.isParameterDeterminedSplit();
        }
        return false;
    }

    public String getSplitParameterId() {
        if (splitData != null && splitData.getSplitParameter() != null) {
            return splitData.getSplitParameter().getParameterId();
        }
        return null;
    }

    public void setSplitData(SplitSimulationDataType splitData) {
        this.splitData = splitData;
    }

    /**
     * Return default transition index.
     * 
     * @return default transition index or -1 if there is not default
     *         transition.
     */
    private int initDefaultTransitionIndex() {
        for (int i = 0; i < outTransitions.length; i++) {
            Condition cond = outTransitions[i].getCondition();
            if (cond != null) {
                if (cond.getType() == ConditionType.OTHERWISE_LITERAL) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void setDefaultTransitionIndex(int defaultTransitionIndex) {
        this.defaultTransitionIndex = defaultTransitionIndex;
    }

    public void addProcessParameters(String splitParamId, boolean created,
            boolean isUsedInSplit) {
        SimProcessParameter param = new SimProcessParameter(splitParamId);
        param.created = created;
        param.setUsedInSplit(isUsedInSplit);
        simProcessParameters.put(splitParamId, param);

    }

    public void setProcessParameterIsUsedInSplit(String splitParamId,
            boolean isUsedInSplit) {
        ((SimProcessParameter) simProcessParameters.get(splitParamId))
                .setUsedInSplit(isUsedInSplit);
    }

    public SplitSimulationDataType getSplitData() {
        return splitData;
    }

    public Process getWorkflowProcess() {
        return workflowProcess;
    }

    public Map getSimProcessParameters() {
        return simProcessParameters;
    }

    public int getDefaultTransitionIndex() {
        return defaultTransitionIndex;
    }

}

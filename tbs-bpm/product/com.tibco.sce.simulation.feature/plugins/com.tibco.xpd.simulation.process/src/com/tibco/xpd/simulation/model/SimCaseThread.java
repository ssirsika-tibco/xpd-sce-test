/* 
 ** 
 **  MODULE:             $RCSfile: SimCaseThread.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-09-19 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.inteng.IntEngConsts;
import com.tibco.inteng.InteractionEngine;
import com.tibco.inteng.InteractionEngineFactory;
import com.tibco.inteng.Invocable;
import com.tibco.inteng.ProcessState;
import com.tibco.inteng.ProcessThread;
import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.xpdldata.XpdlData;
import com.tibco.xpd.simulation.model.WorkflowModel.MaxElapsedTimeStrategy;
import com.tibco.xpd.simulation.model.WorkflowModel.MaxLoopCountStrategy;
import com.tibco.xpd.simulation.model.WorkflowModel.NormalDistLoopCountStrategy;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.No;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.SimTime;

public class SimCaseThread extends SimProcess {

    private final ProcessThread processThread;

    private String applicationId;

    private final SimCase threadCase;

    private String activityId;

    private volatile double timeWorked = 0.0D;

    private volatile double serviceTime = 0.0D;

    public SimCaseThread(Model owner, String name, boolean showInTrace,
            SimCase threadCase, ProcessThread processThread) {
        super(owner, name, showInTrace);
        this.threadCase = threadCase;
        this.processThread = processThread;
    }

    public void lifeCycle() {
        WorkflowModel model = (WorkflowModel) getModel();

        // obtaining Interaction Engine
        InteractionEngine interactionEngine = InteractionEngineFactory
                .getInteractionEngine();
        Map intEngConfig = new HashMap();
        intEngConfig.put(IntEngConsts.IGNORE_APP_REFERENCE, Boolean.TRUE);
        intEngConfig.put(IntEngConsts.IGNORE_SUBFLOW_REFERENCE, Boolean.TRUE);
        intEngConfig.put(IntEngConsts.AUTO_ACT_AS_MANUAL, Boolean.TRUE);
        intEngConfig.put(IntEngConsts.SUB_PROCESS_AS_MANUAL, Boolean.TRUE);
        interactionEngine.setConfigParameters(intEngConfig);

        // swith currentThread from state to this thread.
        ProcessState processState = processThread.getProcessState();
        processState.switchToThread(processThread.getName());
        Invocable currentApplication = interactionEngine
                .getCurrentApplication(processState);
        if (currentApplication != null) {
            applicationId = currentApplication.getId();
        } else {
            applicationId = ""; //$NON-NLS-1$
        }
        activityId = processThread.getCurrentActivityId();
        updateLoopControlVariables(model, processState);
        Process process = model.getWorkFlowProcess();
        Activity activity = process.getActivity(activityId);
        if (activity != null) {
            boolean ok = false;
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task || impl instanceof SubFlow
                    || impl instanceof Reference) {
                ok = true;
            }
            if (ok) {
                // insert manual Activity into queue
                model.getManualActivityThreadQueue().insert(activityId, this);

                String performerId = processThread
                        .getCurrentActivityPerformer();

                // check if participant is now available (is in the idle queue)
                if (!model.getIdleParticipantQueue(performerId).isEmpty()) {
                    // there is idle participant
                    SimParticipant participant = (SimParticipant) model
                            .getIdleParticipantQueue(performerId).first();
                    model.getIdleParticipantQueue(performerId).remove(
                            participant);

                    // place participant on the eventlist right after me,
                    // to ensure that I will be the next customer to get
                    // serviced
                    participant.activateAfter(this);
                }
            }
        }
        passivate();

        // notify case that thread was processed.
        threadCase.activate(new SimTime(0.0));
    }

    /**
     * This method updates ProcessState variables as per the current strategy
     * 
     * @param model
     * @param processState
     */
    private void updateLoopControlVariables(WorkflowModel model,
            ProcessState processState) {
        Object strategy = model.getActivityLoopControls().get(activityId);
        if (strategy == null) {
            return;
        }
        if (strategy instanceof MaxLoopCountStrategy) {
            MaxLoopCountStrategy maxLoopCountStrategy = (MaxLoopCountStrategy) strategy;
            applyMaxLoopCountStrategy(maxLoopCountStrategy, processState);
            return;
        }
        if (strategy instanceof NormalDistLoopCountStrategy) {
            NormalDistLoopCountStrategy uniformStrat = (NormalDistLoopCountStrategy) strategy;
            applyNormalLoopCountStrategy(uniformStrat, processState);
            return;
        }
        if (strategy instanceof MaxElapsedTimeStrategy) {
            MaxElapsedTimeStrategy maxElapseTime = (MaxElapsedTimeStrategy) strategy;
            applyMaxElapseTimeStrategy(maxElapseTime, processState);
            return;
        }

    }

    private void applyMaxElapseTimeStrategy(
            MaxElapsedTimeStrategy maxElapseTimeStrategy,
            ProcessState processState) {
        Double maxElapseTime = (Double) processState.getSessionData().get(
                WorkflowModel.ACTIVITY_MAX_ELAPSE_TIME + activityId);
        if (maxElapseTime == null) {
            double currentValue = getModel().currentTime().getTimeValue();
            double maxValue = maxElapseTimeStrategy.getMaxElapsedTime();
            maxElapseTime = new Double(currentValue + maxValue);
            processState.getSessionData().put(
                    WorkflowModel.ACTIVITY_MAX_ELAPSE_TIME + activityId,
                    maxElapseTime);
        }
        double maxTimeValue = maxElapseTime.doubleValue();
        double currentTimeValue = getModel().currentTime().getTimeValue();
        if (currentTimeValue >= maxTimeValue) {
            setParameterValue(processState, maxElapseTimeStrategy
                    .getParameterId(), maxElapseTimeStrategy
                    .getParameterValueToSet());
        }
    }

    private void applyMaxLoopCountStrategy(
            MaxLoopCountStrategy maxLoopCountStrategy, ProcessState processState) {

        Integer currentLoopValue = (Integer) processState.getSessionData().get(
                WorkflowModel.ACTIVITY_CURRENT_LOOP_COUNT + activityId);
        int currentIntValue = currentLoopValue.intValue();
        int maxIntValue = maxLoopCountStrategy.getMaxLoopCount();
        if (currentIntValue >= maxIntValue) {
            setParameterValue(processState, maxLoopCountStrategy
                    .getParameterId(), maxLoopCountStrategy
                    .getParameterValueToSet());
        } else {
            currentIntValue++;
            processState.getSessionData().put(
                    WorkflowModel.ACTIVITY_CURRENT_LOOP_COUNT + activityId,
                    new Integer(currentIntValue));
        }
    }

    private void setParameterValue(ProcessState processState,
            String parameterId, String parameterValue) {
        Map formalParameters = processState.getFields();
        try {
            for (Iterator iter = formalParameters.entrySet().iterator(); iter
                    .hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                XpdlData parameter = (XpdlData) entry.getValue();
                if (parameter.getName().equals(parameterId)) {
                    parameter.setValue(parameterValue);
                    break;
                }
            }
        } catch (XpdlDataFormatException e) {
            e.printStackTrace();
        }
    }

    private void applyNormalLoopCountStrategy(
            NormalDistLoopCountStrategy normalLoopCountStrategy,
            ProcessState processState) {

        Integer currentLoopValue = (Integer) processState.getSessionData().get(
                WorkflowModel.ACTIVITY_CURRENT_LOOP_COUNT + activityId);
        Integer maxLoopValue = (Integer) processState.getSessionData().get(
                WorkflowModel.ACTIVITY_MAX_LOOP_COUNT + activityId);
        if (maxLoopValue == null) {
            int uniformDistValue = normalLoopCountStrategy.getLoopCountValue();
            maxLoopValue = new Integer(uniformDistValue);
            processState.getSessionData().put(
                    WorkflowModel.ACTIVITY_MAX_LOOP_COUNT + activityId,
                    maxLoopValue);
        }
        int currentIntValue = currentLoopValue.intValue();
        int maxIntValue = maxLoopValue.intValue();
        if (currentIntValue >= maxIntValue) {
            setParameterValue(processState, normalLoopCountStrategy
                    .getParameterId(), normalLoopCountStrategy
                    .getParameterValueToSet());
        } else {
            currentIntValue++;
            processState.getSessionData().put(
                    WorkflowModel.ACTIVITY_CURRENT_LOOP_COUNT + activityId,
                    new Integer(currentIntValue));
        }
    }

    public String getApplicationId() {
        return applicationId;
    }

    public ProcessThread getProcessThread() {
        return processThread;
    }

    public String getActivityId() {
        return activityId;
    }

    public double getTimeWorked() {
        return timeWorked;
    }

    public void setTimeWorked(double timeWorked) {
        this.timeWorked = timeWorked;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;

    }

    public void addCost(double cost) {
        threadCase.addCost(cost);
    }
}

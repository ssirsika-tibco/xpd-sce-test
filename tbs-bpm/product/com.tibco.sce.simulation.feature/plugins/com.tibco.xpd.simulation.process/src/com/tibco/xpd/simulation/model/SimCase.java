/* 
 ** 
 **  MODULE:             $RCSfile: SimCase.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-09-16 $ 
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.inteng.IntEngConsts;
import com.tibco.inteng.InteractionEngine;
import com.tibco.inteng.InteractionEngineFactory;
import com.tibco.inteng.Process;
import com.tibco.inteng.ProcessState;
import com.tibco.inteng.ProcessThread;
import com.tibco.inteng.exceptions.XpdlDataFormatException;
import com.tibco.inteng.xpdldata.XpdlData;
import com.tibco.xpd.simulation.preprocess.Case;
import com.tibco.xpd.simulation.preprocess.CaseParameter;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.SimTime;

public class SimCase extends SimProcess {

    private final WorkflowModel model;

    private final Case theCase;

    private final boolean showInTrace;
    
    private double cost;

    public SimCase(Model owner, String name, boolean showInTrace,
            Case caseWithParams) {
        super(owner, name, showInTrace);
        this.showInTrace = showInTrace;
        this.theCase = caseWithParams;
        model = (WorkflowModel) owner;
        cost = 0;
    }

    public void lifeCycle() {
        double startTime = model.currentTime().getTimeValue();
        model.updateStartedCasesCount();
        Process xpdlProcess = model.getXPDLProcess(theCase.getPackageName(),
                theCase.getProcessName());

        // setting simulation parameters
        List formalParameters = xpdlProcess.getFormalParameters();
        try {
            for (Iterator iter = formalParameters.iterator(); iter.hasNext();) {
                XpdlData parameter = (XpdlData) iter.next();
                CaseParameter caseParam = theCase.getCaseParameter(parameter
                        .getName());
                if (caseParam != null) {
                    parameter.setValue(caseParam.getValue());
                }
            }
        } catch (XpdlDataFormatException e) {
            e.printStackTrace();
        }
        // creating new state
        ProcessState state = xpdlProcess.newProcessState(formalParameters);
        initialiseLoopControlVariables(state);
        // obtaining Interaction Engine
        InteractionEngine interactionEngine = InteractionEngineFactory
                .getInteractionEngine();
        Map intEngConfig = new HashMap();
        intEngConfig.put(IntEngConsts.IGNORE_APP_REFERENCE, Boolean.TRUE);
        intEngConfig.put(IntEngConsts.IGNORE_SUBFLOW_REFERENCE, Boolean.TRUE);
        intEngConfig.put(IntEngConsts.AUTO_ACT_AS_MANUAL, Boolean.TRUE);
        intEngConfig.put(IntEngConsts.SUB_PROCESS_AS_MANUAL, Boolean.TRUE);
        interactionEngine.setConfigParameters(intEngConfig);

        Set runningThreads = new HashSet();

        while (!state.isFinished()) {
            // run interaction engin to next manual step(s)
            interactionEngine.executeProcess(state);

            if (state.isFinished()) {
                break;
            }

            // start all threads not yet started
            List threadNames = new ArrayList(state.getAllUserThreadsName()); // prevent
            // from
            // unmodificable
            threadNames.add(state.getCurrentThread().getName());
            for (Iterator iter = threadNames.iterator(); iter.hasNext();) {
                String threadName = (String) iter.next();
                ProcessThread processThread = state.getThreadByName(threadName);
                String activityId = processThread.getCurrentActivityId();
                String threadOnActivityId = threadName + activityId;
                if (!runningThreads.contains(threadOnActivityId)) {
                    runningThreads.add(threadOnActivityId);
                    // create & activate thread
                    SimCaseThread simCaseThread = new SimCaseThread(model,
                            "SimCaseThread", showInTrace, //$NON-NLS-1$
                            this, processThread);
                    // TODO Now there will be activation based on LIFO
                    // maybe it should be consider to change to FIFO.
                    simCaseThread.activate(new SimTime(0.0));
                }
            }

            passivate();

            // remove all processed threads
            Set processedThreads = new HashSet();
            List processedThreadNames = new ArrayList(state
                    .getAllUserThreadsName());
            // processedThreadNames.add(state.getCurrentThread().getName());
            for (Iterator iter = processedThreadNames.iterator(); iter
                    .hasNext();) {
                String threadName = (String) iter.next();
                ProcessThread processThread = state.getThreadByName(threadName);
                String activityId = processThread.getCurrentActivityId();
                String threadOnActivityId = threadName + activityId;
                processedThreads.add(threadOnActivityId);
            }
            runningThreads.retainAll(processedThreads);
            // processThread;
            // model.getManualAppThreadQueue().insert(applicationId, )
        }

        model.updateFinishedCasesCount();
        model.addCaseDurationTime(model.currentTime().getTimeValue()
                - startTime);
        model.addCaseCost(cost);
    }

    private void initialiseLoopControlVariables(ProcessState state) {
        Map sessionData = state.getSessionData();
        Map activityLoopControls = model.getActivityLoopControls();
        for (Iterator iter = activityLoopControls.entrySet().iterator(); iter
                .hasNext();) {
            Map.Entry actEntry = (Map.Entry) iter.next();
            String actId = (String) actEntry.getKey();
            sessionData.put(WorkflowModel.ACTIVITY_CURRENT_LOOP_COUNT + actId,
                    new Integer(1));
        }

    }

    public void addCost(double cost) {
        this.cost += cost;
    }

}

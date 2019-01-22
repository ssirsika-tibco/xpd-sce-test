/* 
 ** 
 **  MODULE:             $RCSfile: SimParticipant.java $ 
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

import com.tibco.inteng.ProcessState;
import com.tibco.inteng.ProcessThread;
import com.tibco.inteng.impl.ProcessThreadImpl;

import desmoj.core.simulator.InterruptCode;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.SimTime;

public class SimParticipant extends SimProcess {

    private final String participantId;

    private boolean available = true;

    private Object lock = new Object();

    public SimParticipant(Model owner, String name, boolean showInTrace,
            String participantId) {
        super(owner, name, showInTrace);
        this.participantId = participantId;
    }

    public void lifeCycle() {
        WorkflowModel model = (WorkflowModel) getModel();

        while (true) {
            if (available
                    && !model.getManualActivityThreadQueue()
                            .isQueueForParticipantEmpty(participantId)) {
                SimCaseThread thread = (SimCaseThread) model
                        .getManualActivityThreadQueue().firstForParticipant(
                                participantId);
                String activityId = thread.getActivityId();
                ProcessThread processThread = thread.getProcessThread();
                ProcessState processState = processThread.getProcessState();
                model.getManualActivityThreadQueue().remove(activityId, thread);
                // wait for service period of time.
                double timeWorked = thread.getTimeWorked();
                double serviceTimeForThread = 0.0D;
                if (timeWorked == 0.0D) {
                    serviceTimeForThread = model.getServiceTimeForActivity(
                            activityId, processState);
                    thread.setServiceTime(serviceTimeForThread);
                } else {
                    serviceTimeForThread = thread.getServiceTime();
                }
                double remainingServiceTime = serviceTimeForThread - timeWorked;
                double startTime = currentTime().getTimeValue();
                if (remainingServiceTime > 0.0D && !isInterrupted()) {
                    hold(new SimTime(remainingServiceTime));
                }
                double elapsedTime = currentTime().getTimeValue() - startTime;
                if (isInterrupted() && elapsedTime < remainingServiceTime) {
                    thread.setTimeWorked(timeWorked + elapsedTime);
                    // set time worked and put it back on the queue.
                    thread.setPriority(1);
                    model.getManualActivityThreadQueue().reinsert(activityId,
                            thread);
                } else {
                    thread.setPriority(0);
                    thread.setTimeWorked(0);
                    model.activityServed(activityId, participantId,
                            serviceTimeForThread);
                    double cost = model.getActivityCost(participantId, serviceTimeForThread);
                    thread.addCost(cost);
                    // submit process thread
                    processState.switchToThread(processThread.getName());
                    ((ProcessThreadImpl) processThread).setSubmitted(true);

                    // now the thread application has been served and can be
                    // notified to proceed
                    // we will reactivate it, to allow it to finish its life
                    // cycle
                    thread.activate(new SimTime(0.0));
                }
                clearInterruptCode();
            } else {

                // insert myself into waiting queue.
                synchronized (lock) {
                    if (available) {
                        model.getIdleParticipantQueue(participantId).insert(
                                this);
                    }
                }

                // passivate myself
                passivate();
            }
        }
    }

    public String getParticipantId() {
        return participantId;
    }

    public void finishWork() {
        setAvailable(false);
        interrupt(new InterruptCode("Stop Working")); //$NON-NLS-1$
        // activate(SimTime.NOW);
    }

    public void startWork() {
        setAvailable(true);
        activate(SimTime.NOW);
    }

    public void setAvailable(boolean available) {
        WorkflowModel model = (WorkflowModel) getModel();
        synchronized (lock) {
            this.available = available;
            if (available) {
                model.getNonWorkingQueue(participantId).remove(this);
                model.getIdleParticipantQueue(participantId).insert(this);
            } else {
                model.getIdleParticipantQueue(participantId).remove(this);
                model.getNonWorkingQueue(participantId).insert(this);
            }
        }
    }
}

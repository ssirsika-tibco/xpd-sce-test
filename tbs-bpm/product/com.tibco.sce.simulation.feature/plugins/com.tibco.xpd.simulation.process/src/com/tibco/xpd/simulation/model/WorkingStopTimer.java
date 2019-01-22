package com.tibco.xpd.simulation.model;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.SimTime;

public class WorkingStopTimer extends SimProcess {
    private String participantId;
    private SimParticipant simParticipant;

    public WorkingStopTimer(Model owner, String name, boolean showInTrace, SimParticipant simParticipant) {
        super(owner, name, showInTrace);
        this.simParticipant = simParticipant;
        participantId = simParticipant.getParticipantId();
    }

    public void lifeCycle() {
        while (true) {
            WorkflowModel model = (WorkflowModel) getModel();
            double nextStop = model.getNextWorkingStopTime(participantId, currentTime());
            simParticipant.finishWork();
            hold(new SimTime(nextStop - currentTime().getTimeValue()));
        }
    }

}

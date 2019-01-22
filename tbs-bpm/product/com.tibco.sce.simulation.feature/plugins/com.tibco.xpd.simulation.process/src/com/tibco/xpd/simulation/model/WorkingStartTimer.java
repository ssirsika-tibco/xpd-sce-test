package com.tibco.xpd.simulation.model;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.SimTime;

/**
 * Process to transition participants between the idle and non-working queues and between
 * the working and assigned queues.
 *
 * @author nwilson
 */
public class WorkingStartTimer extends SimProcess {
    private String participantId;
    private SimParticipant simParticipant;

    public WorkingStartTimer(Model owner, String name, boolean showInTrace, SimParticipant simParticipant) {
        super(owner, name, showInTrace);
        this.simParticipant = simParticipant;
        participantId = simParticipant.getParticipantId();
    }

    public void lifeCycle() {
        while (true) {
            WorkflowModel model = (WorkflowModel) getModel();
            double nextStart = model.getNextWorkingStartTime(participantId, currentTime());
            simParticipant.startWork();
            hold(new SimTime(nextStart - currentTime().getTimeValue()));
        }
    }

}

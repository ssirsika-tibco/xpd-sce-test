/**
 * 
 */
package com.tibco.xpd.simulation.edit;

import java.util.Iterator;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.common.command.SimulationAddActivityContribution;
import com.tibco.xpd.simulation.common.command.SimulationAddParticipantContribution;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;

/**
 * Add simulation data after simulation destination enviroment is added to the
 * process
 * 
 * @author wzurek
 */
public class AddSimulationDataCommand extends AbstractCommand {

    private CompoundCommand wrapper = new CompoundCommand();

    private final Command origCommand;

    private final EObject eo;

    private final EditingDomain domain;

    /**
     * Assign sim data to the whole process, the process will have to be a
     * parent os provided EObject <i>after</i> origCommand has been executed.
     * 
     * @param origCommand
     * @param eo
     * @param domain
     */
    public AddSimulationDataCommand(Command origCommand, EObject eo,
            EditingDomain domain) {
        this.origCommand = origCommand;
        this.eo = eo;
        this.domain = domain;
    }

    public void execute() {
        wrapper.appendAndExecute(origCommand);

        CompoundCommand cmd = new CompoundCommand();
        Process proc = SimulationXpdlUtils.getWorkflowProcess(eo);
        assignToProcess(cmd, proc, domain);
        if (cmd.canExecute()) {
            wrapper.appendAndExecute(cmd);
        }
    }

    private void assignToProcess(CompoundCommand cmd, Process process,
            EditingDomain domain) {
        for (Iterator j = process.getActivities().iterator(); j.hasNext();) {
            Activity activity = (Activity) j.next();
            SimulationAddActivityContribution.assignSimDataToActivity(domain,
                    activity,
                    cmd);
        }
        for (Iterator j = process.getParticipants().iterator(); j.hasNext();) {
            Participant participant = (Participant) j.next();
            SimulationAddParticipantContribution
                    .assignSimDataToParticipant(domain, participant, cmd);
        }
    }

    public void redo() {
        wrapper.redo();
    }

    public void undo() {
        wrapper.undo();
    }

    protected boolean prepare() {
        return origCommand.canExecute();
    }
}

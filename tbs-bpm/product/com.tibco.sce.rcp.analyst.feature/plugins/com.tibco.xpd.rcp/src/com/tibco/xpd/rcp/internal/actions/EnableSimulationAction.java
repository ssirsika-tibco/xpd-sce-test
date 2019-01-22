/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.utils.RCPSimulationUtils;
import com.tibco.xpd.xpdl2.Process;

/**
 * Enable Simulation Action that will enable the simulation destination if it
 * does not exist
 * 
 * @author mtorres
 * 
 */
public class EnableSimulationAction extends AbstractSimulationAction {

    public EnableSimulationAction(IWorkbenchWindow window) {
        super(window, Messages.EnableSimulationAction_EnableSimulation,
                "enablesimulation", //$NON-NLS-1$
                Messages.EnableSimulationAction_EnableSimulation);
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.EditorEventAction#run(org.eclipse.ui.IEditorInput)
     * 
     * @param input
     * @throws CoreException
     */
    @Override
    protected void run(IEditorInput input) throws CoreException {
        // TODO Auto-generated method stub
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.EditorEventAction#run()
     * 
     */
    @Override
    public void run() {
        Process contextProcess =
                RCPSimulationUtils.getContextProcess(getWindow());
        if (contextProcess != null) {
            CompoundCommand cmd = new CompoundCommand();
            if (isChecked()) {
                cmd.append(DestinationUtil
                        .getSetDestinationEnabledCommand(getEditingDomain(),
                                contextProcess,
                                RCPSimulationUtils.SIMULATION_DESTINATION_ID,
                                true));

                /*
                 * Sid XPD-4165: removed forced setting of simulation
                 * destination onto project.Simulation destination is only
                 * really there to ensure that specific processes that user
                 * wishes to perform simulation on are valid to be run as such.
                 * But most processes won't be valid so don't want to set it by
                 * default for new processes (which will happen once the
                 * simulation dest is set in project. I have modified rule that
                 * enforces project must have same dest's as process to ignore
                 * for simulation destination.
                 */

            } else {
                cmd.append(DestinationUtil
                        .getSetDestinationEnabledCommand(getEditingDomain(),
                                contextProcess,
                                RCPSimulationUtils.SIMULATION_DESTINATION_ID,
                                false));
            }
            if (!cmd.isEmpty() && cmd.canExecute()) {
                getEditingDomain().getCommandStack().execute(cmd);
            }
        }
    }

    @Override
    public void partActivated(IWorkbenchPart part) {
        IEditorPart editor = getEditorActivated(part);
        if (editor != null) {
            if (editor instanceof ProcessEditorInput) {
                setEnabled(true);
                updateChecked(((ProcessEditorInput) editor).getProcess());
                return;
            } else {
                Object input = getInput(editor.getEditorInput());
                if (input instanceof Process) {
                    setEnabled(true);
                    updateChecked((Process) input);
                    return;
                }
            }
        }
        setChecked(false);
        setEnabled(false);
    }

    private void updateChecked(Process xpdlProcess) {
        setChecked(RCPSimulationUtils
                .isSimulationDestinationEnabled(xpdlProcess));
    }
}

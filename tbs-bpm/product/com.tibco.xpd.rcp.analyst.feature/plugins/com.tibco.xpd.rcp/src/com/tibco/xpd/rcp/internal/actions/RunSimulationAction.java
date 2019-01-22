/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.utils.RCPSimulationUtils;
import com.tibco.xpd.simulation.ui.runner.SimulationLaunchShortcut;
import com.tibco.xpd.xpdl2.Process;

/**
 * Action to run simulation.
 * 
 * @author mtorres
 * 
 */
public class RunSimulationAction extends AbstractSimulationAction {

    public RunSimulationAction(IWorkbenchWindow window) {
        super(window, Messages.RunSimulationAction_RunSimulation,
                "runSimulation", Messages.RunSimulationAction_RunSimulation); //$NON-NLS-1$ 
        setImageDescriptor(RCPActivator
                .getImageDescriptor(RCPImages.RUNSIMULATION.getPath()));
        setDisabledImageDescriptor(RCPActivator
                .getImageDescriptor(RCPImages.RUNSIMULATION.getDisabledPath()));
    }

    @Override
    public void run() {
        Process contextProcess =
                RCPSimulationUtils.getContextProcess(getWindow());
        if (contextProcess != null) {
            ISelection selection = new StructuredSelection(contextProcess);
            SimulationLaunchShortcut launch = new SimulationLaunchShortcut();
            launch.launch(selection, "run"); //$NON-NLS-1$
        }
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.EditorEventAction#run(org.eclipse.ui.IEditorInput)
     * 
     * @param input
     * @throws CoreException
     */
    @Override
    protected void run(IEditorInput input) throws CoreException {

    }

    @Override
    public void partActivated(IWorkbenchPart part) {
        IEditorPart editor = getEditorActivated(part);
        if (editor != null) {
            if (editor instanceof ProcessEditorInput) {
                setEnabled(RCPSimulationUtils
                        .isSimulationDestinationEnabled(((ProcessEditorInput) editor)
                                .getProcess()));
                return;
            } else {
                Object input = getInput(editor.getEditorInput());
                if (input instanceof Process) {
                    setEnabled(RCPSimulationUtils
                            .isSimulationDestinationEnabled((Process) input));
                    return;
                }
            }
        }
        setEnabled(false);
    }
}

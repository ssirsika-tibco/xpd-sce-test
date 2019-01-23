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
import com.tibco.xpd.simulation.compare.CompareResultsAction;
import com.tibco.xpd.xpdl2.Process;

/**
 * Action to prepare simulation.
 * 
 * @author mtorres
 * 
 */
public class CompareSimulationAction extends AbstractSimulationAction {

    public CompareSimulationAction(IWorkbenchWindow window) {
        super(window, Messages.CompareSimulationAction_CompareResults,
                "compareSimulation", //$NON-NLS-1$
                Messages.CompareSimulationAction_CompareResults);
        setImageDescriptor(RCPActivator
                .getImageDescriptor(RCPImages.COMPARESIMULATION.getPath()));
        setDisabledImageDescriptor(RCPActivator
                .getImageDescriptor(RCPImages.COMPARESIMULATION
                        .getDisabledPath()));
    }

    @Override
    public void run() {
        Process contextProcess =
                RCPSimulationUtils.getContextProcess(getWindow());
        if (contextProcess != null) {
            ISelection selection = new StructuredSelection(contextProcess);
            CompareResultsAction compareResultsAction =
                    new CompareResultsAction();
            compareResultsAction.selectionChanged(this, selection);
            compareResultsAction.run();
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

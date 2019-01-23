/* 
 ** 
 **  MODULE:             $RCSfile: StartSimulationActionDelegate.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-09-27 $ 
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
package com.tibco.xpd.simulation.ui.actions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;

import com.tibco.xpd.simulation.launch.SimulationEventKeys;
import com.tibco.xpd.simulation.ui.Messages;
import com.tibco.xpd.simulation.ui.SimulationUIPlugin;
import com.tibco.xpd.simulation.ui.runner.SimulationLaunchShortcut;
import com.tibco.xpd.xpdl2.Process;

public class StartSimulationActionDelegate extends AbstractActionDelegate {

    private static Map enabledState;

    public StartSimulationActionDelegate() {
        super();
        setInitialState(true);
        enabledState = new HashMap();
        enabledState.put(SimulationEventKeys.SIMULATION_STATE_ABORTED,
                Boolean.TRUE);
        enabledState.put(SimulationEventKeys.SIMULATION_STATE_FINISHED,
                Boolean.TRUE);
        enabledState.put(SimulationEventKeys.SIMULATION_STATE_NOT_STARTED,
                Boolean.TRUE);
        enabledState.put(SimulationEventKeys.SIMULATION_STATE_PAUSED,
                Boolean.FALSE);
        enabledState.put(SimulationEventKeys.SIMULATION_STATE_RUNNING,
                Boolean.FALSE);
    }

    protected void doRun() {
        Object selectedObject = getSelectedObject();
        if (selectedObject instanceof ILaunchConfiguration) {
            ILaunchConfiguration config = (ILaunchConfiguration) selectedObject;
            runConfiguration(config);
        } else if (selectedObject instanceof IAdaptable) {
            ILaunchConfiguration config = (ILaunchConfiguration) ((IAdaptable) selectedObject)
                    .getAdapter(ILaunchConfiguration.class);
            runConfiguration(config);

        }
    }

    private void runConfiguration(ILaunchConfiguration config) {
        if (isConfigValid(config)) {
            DebugUITools.launch(config, ILaunchManager.RUN_MODE);
        } else {
            String title = Messages.TITLE_SIMULATION;
            String message = Messages.MSG_NO_LAST_SIMULATION_CONFIG;  
            MessageDialog.openWarning(SimulationUIPlugin.getShell(), title, message);
        }
    }

    private boolean isConfigValid(ILaunchConfiguration config) {
        try {
            ILaunchConfiguration[] configs = DebugPlugin.getDefault()
                    .getLaunchManager().getLaunchConfigurations();
            for (int i = 0; i < configs.length; i++) {
                if (config.equals(configs[i])) {
                    return true;
                }
            }
        } catch (CoreException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "CoreException while validation the launch config.", e); //$NON-NLS-1$
        }
        return false;
    }

    protected Map getActionStates() {
        return enabledState;
    }

    /**
     * @see com.tibco.xpd.simulation.ui.actions.AbstractActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        super.selectionChanged(action, selection);
        if (getSelectedObject() instanceof Process) {
            this.getAction().setEnabled(true);
        } else {
            this.getAction().setEnabled(false);
        }
    }

}

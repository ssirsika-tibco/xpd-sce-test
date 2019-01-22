/* 
 ** 
 **  MODULE:             $RCSfile: PauseSimulationActionDelegate.java $ 
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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.launch.SimulationEventKeys;
import com.tibco.xpd.simulation.ui.SimulationUIPlugin;

public class PauseSimulationActionDelegate extends AbstractActionDelegate {
    
    private Map enabledState;

    public PauseSimulationActionDelegate() {
        super();
        setInitialState(false);
        
        enabledState=new HashMap();
        enabledState.put(SimulationEventKeys.SIMULATION_STATE_ABORTED,Boolean.FALSE);
        enabledState.put(SimulationEventKeys.SIMULATION_STATE_FINISHED,Boolean.FALSE);
        enabledState.put(SimulationEventKeys.SIMULATION_STATE_NOT_STARTED,Boolean.FALSE);
        enabledState.put(SimulationEventKeys.SIMULATION_STATE_PAUSED,Boolean.FALSE);
        enabledState.put(SimulationEventKeys.SIMULATION_STATE_RUNNING,Boolean.TRUE);
    }
    
    /**
     * TODO comment me!
     * @see com.tibco.xpd.simulation.ui.actions.AbstractActionDelegate#getActionStates()
     */
    protected Map getActionStates() {
        return enabledState;
    }
    
    /**
     * TODO comment me!
     * @see com.tibco.xpd.simulation.ui.actions.AbstractActionDelegate#doRun()
     */
    protected void doRun() {
        LaunchPlugin.getSimulationControler().pauseSimulation();
    }
    
}

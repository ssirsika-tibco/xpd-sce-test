/**
 * 
 */
package com.tibco.xpd.simulation.ui.actions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.tibco.simulation.report.SimRepExperimentState;
import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.launch.SimulationControler;
import com.tibco.xpd.simulation.launch.SimulationEventKeys;
import com.tibco.xpd.simulation.ui.SimulationUIPlugin;

/**
 * TODO comment me!
 * @author mmaciuki
 *
 */
public abstract class AbstractActionDelegate implements IViewActionDelegate, IActionDelegate2, PropertyChangeListener {

    private IAction action;
    private SimulationControler simulationControler;
    private IViewPart viewPart;
    private Object selectedObject;
    private boolean initialState;
    private ISelection currentSelection;

    public AbstractActionDelegate(){
        this.simulationControler = LaunchPlugin.getSimulationControler();
        this.simulationControler.addListener(this);
    }
    
    /**
     * TODO comment me!
     * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
     */
    public void init(IViewPart view) {
        this.viewPart = view;
    }

    /**
     * TODO comment me!
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public final void run(IAction action) {
        doRun();
    }

    /**
     * TODO comment me!
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        currentSelection = selection; 
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structSel = (IStructuredSelection) selection;
            this.selectedObject = structSel.getFirstElement();
        }
    }

    
    /**
     * TODO comment me!
     * @see org.eclipse.ui.IActionDelegate2#init(org.eclipse.jface.action.IAction)
     */
    public void init(IAction action) {
        this.action=action;
        this.action.setEnabled(initialState);
    }

    /**
     * TODO comment me!
     * @see org.eclipse.ui.IActionDelegate2#dispose()
     */
    public void dispose() {
        this.simulationControler.removeListener(this);
    }

    /**
     * TODO comment me!
     * @see org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action.IAction, org.eclipse.swt.widgets.Event)
     */
    public final void runWithEvent(IAction action, Event event) {
        run(action);
    }

    /**
     * @return Returns the action.
     */
    protected IAction getAction() {
        return this.action;
    }

    /**
     * @return Returns the selectedObject.
     */
    protected Object getSelectedObject() {
        return this.selectedObject;
    }

    /**
     * @return Returns the simulationControler.
     */
    protected SimulationControler getSimulationControler() {
        return this.simulationControler;
    }

    /**
     * @return Returns the viewPart.
     */
    protected IViewPart getViewPart() {
        return this.viewPart;
    }

    /**
     * TODO comment me!
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if(propertyName.equalsIgnoreCase(SimulationEventKeys.EXPERIMENT_STATE)){
            Object newValue = evt.getNewValue();
            SimRepExperimentState simulationState=(SimRepExperimentState)newValue;
            Map actionStates = getActionStates();
            boolean actionEnabled;
            if(actionStates.containsKey(simulationState.getName())){
            Boolean enabled = (Boolean)actionStates.get(simulationState.getName());
            actionEnabled=enabled.booleanValue();
            } else {
                actionEnabled=false;
            }
            getAction().setEnabled(actionEnabled);
        }
    }

    /**
     * TODO comment me!
     * @return
     */
    protected abstract Map getActionStates();
    
    protected abstract void doRun();
    
    /**
     * TODO comment me!
     * @param b
     */
    protected void setInitialState(boolean initialState) {
        this.initialState=initialState;
    }

    /**
     * Gets current selection.
     *
     * @return
     */
    public ISelection getCurrentSelection() {
        return currentSelection;
    }
}

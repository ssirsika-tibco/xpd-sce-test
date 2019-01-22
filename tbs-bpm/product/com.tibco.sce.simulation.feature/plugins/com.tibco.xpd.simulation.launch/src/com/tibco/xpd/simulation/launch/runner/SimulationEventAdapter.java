package com.tibco.xpd.simulation.launch.runner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EAttribute;

import desmoj.core.util.ExperimentListener;


/**
 * Adopts {@link SimulationEventNotificator} for use as other listener.
 * 
 * @author mmaciuki
 * @see ExperimentListener
 * @see PropertyChangeListener
 */
class SimulationEventAdapter extends AdapterImpl {
    /**
     * 
     */
    private final SimulationControlerImpl controler;
    private final SimulationEventNotificator notificator;
    
    /**
     * TODO comment me!
     * @param totalNoOfCases 
     * @param controler TODO
     */
    public SimulationEventAdapter(SimulationControlerImpl controler, SimulationEventNotificator notificator) {
        this.controler = controler;
        this.notificator = notificator;
    }
    
    /**
     * TODO comment me!
     * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
     */
    public void notifyChanged(Notification notification) {
        int eventType = notification.getEventType();
        switch(eventType){
        case Notification.SET:
            Object feature = notification.getFeature();
            if(feature instanceof EAttribute){
                try{
                    handleEMFEvent(notification);
                }catch(Throwable ex){
                    ex.printStackTrace();
                }
            }
            break;
        }
        
        super.notifyChanged(notification);
    }
    
    /**
     * TODO comment me!
     * @param notification
     */
    private void handleEMFEvent(Notification notification) {
        EAttribute attr=(EAttribute)notification.getFeature();
        String attribName = attr.getName();
        Object oldValue = notification.getOldValue();
        Object newValue = notification.getNewValue();
        
        Object source=controler.getSimRepExperiment();
        String propertyName=attribName;
        PropertyChangeEvent propertyChangeEvent = new PropertyChangeEvent(source,propertyName,oldValue,newValue);
        notificator.notifyListeners(propertyChangeEvent);
    }
}
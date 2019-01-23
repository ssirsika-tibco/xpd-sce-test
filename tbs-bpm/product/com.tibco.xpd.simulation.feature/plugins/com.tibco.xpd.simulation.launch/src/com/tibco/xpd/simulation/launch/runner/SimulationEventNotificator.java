/**
 * 
 */
package com.tibco.xpd.simulation.launch.runner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulationEventNotificator {
    
    private PropertyChangeSupport propertyChangeSupport;
    
    /**
     * TODO comment me!
     */
    public SimulationEventNotificator() {
        propertyChangeSupport = new PropertyChangeSupport(new Object());
    }
 
    public void notifyListeners(final PropertyChangeEvent event){
        propertyChangeSupport.firePropertyChange(event);
    }
        
    /**
     * TODO comment me!
     * @see com.tibco.xpd.simulation.launch.SimulationControler#addListener(java.util.EventListener)
     */
    public synchronized void addListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * TODO comment me!
     * @see com.tibco.xpd.simulation.launch.SimulationControler#removeListener(java.util.EventListener)
     */
    public synchronized void removeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
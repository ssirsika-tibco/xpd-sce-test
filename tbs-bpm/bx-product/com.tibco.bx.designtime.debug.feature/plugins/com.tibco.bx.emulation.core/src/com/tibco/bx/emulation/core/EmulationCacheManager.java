package com.tibco.bx.emulation.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.runtime.ListenerList;

import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class EmulationCacheManager implements  PropertyChangeListener{
	private EmulationData currentEmulationData;
	
	private static EmulationCacheManager instance = null;
	
	ListenerList emulationListeners = new ListenerList();
	
	boolean isDirty;
	
	public static synchronized EmulationCacheManager getDefault(){
		if(instance == null){
			instance = new EmulationCacheManager();
		}
		return instance;
	}
	
	private EmulationCacheManager() {
	}

	public void dispose(){
		if(currentEmulationData != null)
			WorkingCopyUtil.getWorkingCopyFor(currentEmulationData).removeListener(this);
		emulationListeners = null;
	}
	
	public void addEmulationListener(IEmulationListener listener){
		emulationListeners.add(listener);
	}
	
	public void removeEmulationListener(IEmulationListener listener){
		emulationListeners.remove(listener);
	}
	
	
	
	public void setCurrentEmulationData(EmulationData newData) {
		if(currentEmulationData != newData){
			EmulationData oldData = currentEmulationData;
			if(oldData != null){
				WorkingCopyUtil.getWorkingCopyFor(oldData).removeListener(this);
			}
			if(newData != null){
				WorkingCopyUtil.getWorkingCopyFor(newData).addListener(this);
				isDirty = WorkingCopyUtil.getWorkingCopyFor(newData).isWorkingCopyDirty();
			}else{
				isDirty = false;
			}
			this.currentEmulationData = newData;
			fireCurrentEmulationDataChanged(newData, oldData);
		}
	}
	
	public EmulationData getCurrentEmulationData() {
		return currentEmulationData;
	}

	public void refresh(){
		fireCurrentEmulationDataChanged(currentEmulationData, currentEmulationData);
	}
	
	private void fireEmulationDataChanged(EmulationData emulationData, int status) {
		Object[] listeners = emulationListeners.getListeners();
		for (int i= 0; i < listeners.length; i++) {
			IEmulationListener listener = (IEmulationListener)listeners[i];
            listener.emulationDataChanged(emulationData, status);
		}
	}

	private void fireCurrentEmulationDataChanged(EmulationData newData, EmulationData oldData) {
		Object[] listeners = emulationListeners.getListeners();
		for (int i= 0; i < listeners.length; i++) {
			IEmulationListener listener = (IEmulationListener)listeners[i];
            listener.currentEmulationDataChanged(newData, oldData);
		}
	}

	public void propertyChange(PropertyChangeEvent event) {
		String propName = event.getPropertyName();
		if (propName.equals(WorkingCopy.PROP_REMOVED)){
			EmulationData oldData = currentEmulationData;
			currentEmulationData = null;
			isDirty = false;
			fireCurrentEmulationDataChanged(null, oldData);
		}else if(propName.equals(WorkingCopy.CHANGED)){
			isDirty = true;
			fireEmulationDataChanged(currentEmulationData, IEmulationConstants.CHANGED);
		}else if(propName.equals(WorkingCopy.PROP_DIRTY)){
			isDirty = WorkingCopyUtil.getWorkingCopyFor(currentEmulationData).isWorkingCopyDirty();
			fireEmulationDataChanged(currentEmulationData, IEmulationConstants.SAVED);
		}
	}
	
	public boolean isDirty(){
		return isDirty;
	}
}

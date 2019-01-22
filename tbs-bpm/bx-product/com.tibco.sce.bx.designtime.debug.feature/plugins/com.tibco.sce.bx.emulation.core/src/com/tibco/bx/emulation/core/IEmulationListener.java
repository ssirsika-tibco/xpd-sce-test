package com.tibco.bx.emulation.core;

import com.tibco.bx.emulation.model.EmulationData;

public interface IEmulationListener {

	public void emulationDataChanged(EmulationData emulationData, int status);
	
	public void currentEmulationDataChanged(EmulationData newData, EmulationData oldData);
	
}

package com.tibco.bx.debug.core.runtime.events;


public class BxProcessResumptionEvent extends BxRuntimeEvent {
	
	private final String state;
	
	public BxProcessResumptionEvent(String processInstanceId, String state) {
		super(processInstanceId, EventType.ProcessResumption);
		
		this.state = state;
	}

	public String getState() {
		return state;
	}

}

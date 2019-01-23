package com.tibco.bx.debug.core.runtime.events;


public class BxProcessSuspensionEvent extends BxRuntimeEvent {	
	
	private final String state;
	private final String suspendMessage;
	
	public BxProcessSuspensionEvent(String processInstanceId, String state, String suspendMessage) {
		super(processInstanceId, EventType.ProcessSuspension);
		
		this.state = state;
		this.suspendMessage = suspendMessage;
	}

	public String getState() {
		return state;
	}

	public String getSuspendMessage() {
		return suspendMessage;
	}
}

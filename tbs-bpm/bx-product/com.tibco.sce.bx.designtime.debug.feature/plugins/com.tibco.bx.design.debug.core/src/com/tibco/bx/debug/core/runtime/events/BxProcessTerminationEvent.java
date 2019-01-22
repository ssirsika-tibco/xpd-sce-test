package com.tibco.bx.debug.core.runtime.events;


public class BxProcessTerminationEvent extends BxProcessEvent {
	
	public enum EndKind {
		COMPLETED,
		FAULTED,
		TERMINATED
	}

	private final String state;
	private final EndKind eventKind;

	public BxProcessTerminationEvent(String processInstanceId, 
			String state, EndKind eventKind) {
		super(processInstanceId, EventType.ProcessTermination);
		this.state = state;
		this.eventKind = eventKind;
	}

	public String getState() {
		return state;
	}

	public EndKind getEventKind() {
		return eventKind;
	}
}

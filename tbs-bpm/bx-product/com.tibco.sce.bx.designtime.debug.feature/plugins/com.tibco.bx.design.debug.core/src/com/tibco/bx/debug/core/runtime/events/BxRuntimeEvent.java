package com.tibco.bx.debug.core.runtime.events;

public class BxRuntimeEvent {
	
	public enum EventType {
		ProcessCreation,
		ProcessTermination,
		ProcessSuspension,
		ProcessResumption,
		ProcessLink,
		BreakpointHit,
		ActivityCreation,
		ActivityTermination,
		TemplateRegistration
	}
	private final String processInstanceId;
	private final EventType eventType;
	
	public BxRuntimeEvent(String processInstanceId, EventType eventType) {
		this.processInstanceId = processInstanceId;
		this.eventType = eventType;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public EventType getEventType() {
		return eventType;
	}

}

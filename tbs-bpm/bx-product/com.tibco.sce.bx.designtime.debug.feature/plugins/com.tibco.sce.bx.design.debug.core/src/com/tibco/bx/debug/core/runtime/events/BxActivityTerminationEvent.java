package com.tibco.bx.debug.core.runtime.events;


public class BxActivityTerminationEvent extends BxRuntimeEvent {
	
	private String activityInstanceId;

	private String state; 
	public BxActivityTerminationEvent(String processInstanceId, String activityInstanceId, String state) {
		super(processInstanceId, EventType.ActivityTermination);
		
		this.activityInstanceId = activityInstanceId;
		this.state = state;
	}

	public String getActivityInstanceId() {
		return activityInstanceId;
	}

	public String getState() {
		return state;
	}

}

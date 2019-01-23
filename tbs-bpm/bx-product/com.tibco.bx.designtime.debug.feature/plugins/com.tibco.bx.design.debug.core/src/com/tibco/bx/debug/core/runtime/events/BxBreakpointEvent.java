package com.tibco.bx.debug.core.runtime.events;


public class BxBreakpointEvent extends BxRuntimeEvent {
	
	private final String activityId;
	private final String activityInstanceId;
	private final boolean onEntry;

	public BxBreakpointEvent(String processInstanceId, String activityId, String activityInstanceId, boolean onEntry) {
		super(processInstanceId, EventType.BreakpointHit);
		
		this.activityId = activityId;
		this.activityInstanceId = activityInstanceId;
		this.onEntry = onEntry;
	}

	public String getActivityId() {
		return activityId;
	}

	public String getActivityInstanceId() {
		return activityInstanceId;
	}

	public boolean isOnEntry() {
		return onEntry;
	}

}

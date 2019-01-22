package com.tibco.bx.debug.core.runtime.events;



public class BxActivityCreationEvent extends BxRuntimeEvent {
	
	private final String id;
	private final String name;
	private final String activityId;
	private final String parentInstanceId;
	private final String type;
	private final String state;
	public BxActivityCreationEvent(String processInstanceId,
			String id, String name,String activityId, String type,
			String state, String parentInstanceId) {
		super(processInstanceId, EventType.ActivityCreation);
		this.id = id;
		this.name = name;
		this.state = state;
		this.activityId = activityId;
		this.type = type;
		this.parentInstanceId = parentInstanceId;
	}

	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}


	public String getActivityId() {
		return activityId;
	}

	public String getParentInstanceId() {
		return parentInstanceId;
	}

	public String getState() {
		return state;
	}

	public String getType() {
		return type;
	}
	
}

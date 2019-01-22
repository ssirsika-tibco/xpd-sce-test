package com.tibco.bx.debug.core.runtime.events;

public class BxProcessCreationEvent extends BxProcessEvent {
	
	private final String processTemplateId;
	private final String state;
	private final boolean isStarter;
	
	
	public BxProcessCreationEvent(String processTemplateId, String processInstanceId,
			String state, boolean isStarter) {
		super(processInstanceId, EventType.ProcessCreation);
		
		this.processTemplateId = processTemplateId;
		this.state = state;
		this.isStarter = isStarter;
	}

	public String getProcessTemplateId() {
		return processTemplateId;
	}

	public String getState() {
		return state;
	}

	public boolean isStarter() {
		return isStarter;
	}

}

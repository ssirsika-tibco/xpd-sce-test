package com.tibco.bx.debug.core.runtime.events;


public class BxProcessLinkEvent extends BxRuntimeEvent {
	
	private final String linkId;
	private final String linkName;
	private final String linkInstanceId;
	private final String fromInstanceId;
	private final String toInstanceId;

	public BxProcessLinkEvent(String processInstanceId,
			String linkId, String linkName, String linkInstanceId,
			String fromInstanceId, String toInstanceId) {
		super(processInstanceId, EventType.ProcessLink);
		
		this.linkId = linkId;
		this.linkName = linkName;
		this.linkInstanceId = linkInstanceId;
		this.fromInstanceId = fromInstanceId;
		this.toInstanceId = toInstanceId;
	}


	public String getLinkId() {
		return linkId;
	}

	public String getLinkName() {
		return linkName;
	}

	public String getLinkInstanceId() {
		return linkInstanceId;
	}

	public String getFromInstanceId() {
		return fromInstanceId;
	}

	public String getToInstanceId() {
		return toInstanceId;
	}

}

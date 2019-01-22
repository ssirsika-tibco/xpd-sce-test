package com.tibco.bx.debug.common.model;
/**
 * Represents an executed link
 * 
 */
public class ProcessLink extends ProcessElement{

	private ProcessVisibleNode from;
	
	private ProcessVisibleNode to;
	
	public ProcessLink(ProcessContainer parent, String linkId,
			String name, String instanceId, ProcessVisibleNode from,
			ProcessVisibleNode to) {
		super(instanceId,name,linkId,parent);
		this.from = from;
		this.to = to;
	}


	public ProcessVisibleNode getFrom() {
		return from;
	}

	public ProcessVisibleNode getTo() {
		return to;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==this) return true;
		if(!(obj instanceof ProcessLink)) return false;
		return ((ProcessLink) obj).getFrom().getInstanceId().equals(
				from.getInstanceId())
				&& ((ProcessLink) obj).getTo().getInstanceId().equals(
						to.getInstanceId());
	}

}

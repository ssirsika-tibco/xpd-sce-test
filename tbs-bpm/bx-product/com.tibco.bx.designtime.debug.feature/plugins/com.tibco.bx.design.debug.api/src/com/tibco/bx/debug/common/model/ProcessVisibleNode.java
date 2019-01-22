package com.tibco.bx.debug.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an node which could be displayed in Launch View
 * it could maps an BPEL Activity or an virtual element such as Track. 
 */
public class ProcessVisibleNode extends ProcessContainer {

	public interface State{
		public static String NOT_STARTED = "not_started";
	    public static String ACTIVE = "active";
	    public static String STARTING = "starting";
	    public static String WAITING = "waiting";
	    public static String FAULTING = "faulting";
	    public static String COMPENSATING = "compensating";
	    public static String CANCELLING = "cancelling";
	    public static String CANCEL_PENDING = "cancelPending";
	    public static String FAULT_PENDING = "faultPending";
	    public static String DONE = "done";
	    public static String SKIPPED = "skipped";
	    public static String SKIPPING = "skipping";
	    public static String CANCELLED = "cancelled";
	    public static String FAULTED = "faulted";
	    public static String COMPENSATED = "compensated";
	    public static String INSTALLED = "installed";
	    public static String ARMED = "armed";
	    public static String DISARMED = "disarmed";
	    public static String NULL = "";//virtual node has no state
	}
	
	private String state;
	private String type;
	private int level;//
	private List<ProcessLink> links = new ArrayList<ProcessLink>(); //link.to = this
	public ProcessVisibleNode(String instanceId, String name,String id,
			ProcessContainer parent) {
		super(instanceId, name, id, parent);
		if(this.parent!=null){
			this.parent.getElements().add(this);
		}
		if(parent instanceof ProcessInstance)
			this.level = 0;
		else
			this.level = ((ProcessVisibleNode)parent).getLevel() + 1;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public boolean addLink(ProcessLink link){
		return links.add(link);
	}
	
	public ProcessLink[] getLinks(){
		return links.toArray(new ProcessLink[0]);
	}
	
	public boolean isEnded(){
		return State.CANCELLED.equals(state)||  //$NON-NLS-1$
		State.DONE.equals(state) || //$NON-NLS-1$
		State.SKIPPED.equals(state) ||
		State.INSTALLED.equals(state);
	}
}

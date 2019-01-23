/**
 * 
 */
package com.tibco.bx.debug.common.model;

import java.util.ArrayList;
import java.util.List;


public class ProcessInstance extends ProcessContainer{
	public interface State{
	 public static String ACTIVE = "active";
	 public static String NOT_STARTED = "not_started";
	 public static String STARTED = "started";  
	 public static String RESUMED = "resumed"; 
	 public static String SUSPENDED = "suspended";  
	 public static String CANCELLED = "cancelled";
	 public static String COMPLETED = "completed";
	 public static String FAILED = "failed";
	}
	
	private ProcessTemplate processTemplate;
	private String state;
	private String suspendMessage;
	private boolean isStarter;
	private List<ProcessLink> links = new ArrayList<ProcessLink>(); //all the links
	public ProcessInstance(ProcessTemplate processTemplate,
			String instanceId) {
		super(instanceId, processTemplate.getProcessName(), processTemplate
				.getProcessId(), null);
		this.processTemplate = processTemplate;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public void setStarter(boolean isStarter) {
		this.isStarter = isStarter;
	}

	public boolean isStarter() {
		return isStarter;
	}

	public ProcessTemplate getProcessTemplate(){
		return processTemplate;
	}
	
	
	
	public String getSuspendMessage() {
		return suspendMessage;
	}

	public void setSuspendMessage(String suspendMessage) {
		this.suspendMessage = suspendMessage;
	}

	public boolean isEnded(){
		return State.CANCELLED.equals(state)|| 
		State.COMPLETED.equals(state)||
		State.FAILED.equals(state);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((processTemplate == null) ? 0 : processTemplate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessInstance other = (ProcessInstance) obj;
		if (processTemplate == null) {
			if (other.processTemplate != null)
				return false;
		} else if (!processTemplate.equals(other.processTemplate))
			return false;
		return true;
	}

	public boolean addLink(ProcessLink link){
		return links.add(link);
	}
	
	public ProcessLink[] getLinks(){
		return links.toArray(new ProcessLink[0]);
	}
}
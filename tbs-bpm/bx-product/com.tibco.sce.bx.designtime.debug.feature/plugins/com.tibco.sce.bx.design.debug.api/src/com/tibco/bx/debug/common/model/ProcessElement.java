package com.tibco.bx.debug.common.model;

public abstract class ProcessElement{

	ProcessContainer parent;
	
	private String instanceId;
	
	private String name ;

	private String index;
	
	private ProcessInstance processInstance = null;
	
	public ProcessElement(String instanceId,String name,String index,ProcessContainer parent) {
		this.parent = parent;
		this.instanceId = instanceId;
		this.name = name;
		this.index = index;
	}

	public final String getName(){
		return name;
	}
	
	public final ProcessContainer getParent(){
		return parent;
	}
	
	public final String getInstanceId() {
		return instanceId;
	}
	
	public final String getIndex() {
		return index;
	}
	
	public final ProcessInstance getProcessInstance() {
		if (processInstance == null) {
			ProcessContainer tmp = parent;
			while (! (tmp instanceof ProcessInstance)) {
				tmp = tmp.getParent();
			}
			if (tmp != null) {
				processInstance = (ProcessInstance) tmp;
			}
		}
		return processInstance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instanceId == null) ? 0 : instanceId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		ProcessElement other = (ProcessElement) obj;
		
		if (instanceId == null) {
			if (other.instanceId != null)
				return false;
		} else if (!instanceId.equals(other.instanceId)) {
			return false;
		}
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index)) {
			return false;
		}
		
		return true;
	}
}

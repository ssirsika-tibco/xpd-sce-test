package com.tibco.bx.debug.common.model;


public class ProcessVariable extends ProcessContainer{

	private String namespace;
	
	private VariableType type;
	
	private Object value;

	private boolean isList;
	/*
	 * parent could be a ProcessInstance or a ProcessVisibleNode or a ProcessVariable
	 * it can be updated if parent is a ProcessInstance or a ProcessVisibleNode, other wise it's a part of a real variable. 
	 * its instanceId equals its name.
	 */
	public ProcessVariable(String instanceId, String name,
			ProcessContainer parent) {
		super(instanceId, name, null, parent);
		if(parent!=null && parent instanceof ProcessVariable){
			this.parent.getElements().add(this);
		}
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public VariableType getType() {
		return type;
	}

	public void setType(VariableType type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isList() {
		return isList;
	}

	public void setList(boolean isList) {
		this.isList = isList;
	}
}

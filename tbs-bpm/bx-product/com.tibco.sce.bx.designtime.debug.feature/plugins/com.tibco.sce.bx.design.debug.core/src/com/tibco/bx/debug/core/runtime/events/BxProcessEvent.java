package com.tibco.bx.debug.core.runtime.events;

import java.util.HashMap;
import java.util.Map;

public abstract class BxProcessEvent extends BxRuntimeEvent{

	public BxProcessEvent(String processInstanceId, EventType eventType) {
		super(processInstanceId, eventType);
	}

	private Map<String, String> varMap = new HashMap<String, String>();
	
	public void addVariable(String name, String value){
		varMap.put(name, value);
	}
	
	public Map<String, String> getVariables(){
		return varMap;
	}
}

package com.tibco.bx.debug.operation;

import com.tibco.bx.debug.common.model.ProcessTemplate;

public class StarterOperation {

	private ProcessTemplate processTemplate;
	private String operationName;
	private StarterOperationParameter[] parameters;

	public StarterOperation(ProcessTemplate processTemplate,
			String operationName, StarterOperationParameter[] parameters) {
		this.processTemplate = processTemplate;
		this.operationName = operationName;
		this.parameters = parameters;

		processTemplate.addStarterOperation(this);
	}

	public ProcessTemplate getProcessTemplate() {
		return processTemplate;
	}
	
	public String getOperationName(){
		return operationName;
	}
	
	public StarterOperationParameter[] getParameters() {
		return parameters;
	}

}

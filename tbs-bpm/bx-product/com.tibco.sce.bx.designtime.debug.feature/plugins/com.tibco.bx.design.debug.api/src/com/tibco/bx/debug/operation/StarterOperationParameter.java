package com.tibco.bx.debug.operation;

public class StarterOperationParameter {

	private String parameterName;
	private String parameterType;

	public StarterOperationParameter(String parameterName, String parameterType) {
		super();
		this.parameterName = parameterName;
		this.parameterType = parameterType;
	}

	public String getParameterName() {
		return parameterName;
	}

	public String getParameterType() {
		return parameterType;
	}

}

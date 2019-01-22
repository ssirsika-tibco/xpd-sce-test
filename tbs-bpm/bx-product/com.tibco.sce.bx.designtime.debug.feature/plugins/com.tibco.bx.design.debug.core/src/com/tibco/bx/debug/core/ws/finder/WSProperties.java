package com.tibco.bx.debug.core.ws.finder;

public class WSProperties {

	private Boolean isRemoteService;
	private String serviceName;
	private String portName;
	private String portTypeName;
	private String operationName;
	
	public Boolean getIsRemoteService() {
		return isRemoteService;
	}
	public void setIsRemoteService(Boolean isRemoteService) {
		this.isRemoteService = isRemoteService;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getPortName() {
		return portName;
	}
	public void setPortName(String portName) {
		this.portName = portName;
	}
	public String getPortTypeName() {
		return portTypeName;
	}
	public void setPortTypeName(String portTypeName) {
		this.portTypeName = portTypeName;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
}

package com.tibco.n2.pfe.services.pageflowType;

public class PageFlowExecutionFault extends Exception {
	public static final long serialVersionUID = 20120717093451L;

	private com.tibco.n2.pfe.services.pageflowType.DetailedException pageFlowExecutionFault;

	public PageFlowExecutionFault() {
		super();
	}

	public PageFlowExecutionFault(String message) {
		super(message);
	}

	public PageFlowExecutionFault(String message, Throwable cause) {
		super(message, cause);
	}

	public PageFlowExecutionFault(String message, com.tibco.n2.pfe.services.pageflowType.DetailedException pageFlowExecutionFault) {
		super(message);
		this.pageFlowExecutionFault = pageFlowExecutionFault;
	}

	public PageFlowExecutionFault(String message, com.tibco.n2.pfe.services.pageflowType.DetailedException pageFlowExecutionFault, Throwable cause) {
		super(message, cause);
		this.pageFlowExecutionFault = pageFlowExecutionFault;
	}

	public com.tibco.n2.pfe.services.pageflowType.DetailedException getFaultInfo() {
		return this.pageFlowExecutionFault;
	}
}

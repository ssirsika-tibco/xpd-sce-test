package com.tibco.n2.pfe.services.pageflowType;

public class InvalidArgumentFault extends Exception {
	public static final long serialVersionUID = 20120717093451L;

	private com.tibco.n2.pfe.services.pageflowType.DetailedException invalidArgumentFault;

	public InvalidArgumentFault() {
		super();
	}

	public InvalidArgumentFault(String message) {
		super(message);
	}

	public InvalidArgumentFault(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidArgumentFault(String message, com.tibco.n2.pfe.services.pageflowType.DetailedException invalidArgumentFault) {
		super(message);
		this.invalidArgumentFault = invalidArgumentFault;
	}

	public InvalidArgumentFault(String message, com.tibco.n2.pfe.services.pageflowType.DetailedException invalidArgumentFault, Throwable cause) {
		super(message, cause);
		this.invalidArgumentFault = invalidArgumentFault;
	}

	public com.tibco.n2.pfe.services.pageflowType.DetailedException getFaultInfo() {
		return this.invalidArgumentFault;
	}
}

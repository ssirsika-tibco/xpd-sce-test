package com.tibco.bx.xpdl2bpel.converter.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.bx.xpdl2bpel.ConverterActivator;

public class ConversionStatus extends Status {

	private String xpdlId;
	
	public ConversionStatus(String xpdlId, int severity, String message, Throwable exception) {
		super(severity, ConverterActivator.PLUGIN_ID, IStatus.OK, message, exception);
		this.xpdlId = xpdlId;
	}
	
	public ConversionStatus(String xpdlId, IStatus status) {
		this(xpdlId, status.getSeverity(), status.getMessage(), status.getException());
	}

	public String getXpdlId() {
		return xpdlId;
	}

}

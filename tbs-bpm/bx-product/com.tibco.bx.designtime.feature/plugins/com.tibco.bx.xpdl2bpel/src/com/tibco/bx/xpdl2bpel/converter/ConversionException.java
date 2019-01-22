package com.tibco.bx.xpdl2bpel.converter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import com.tibco.bx.xpdl2bpel.ConverterActivator;


public class ConversionException extends CoreException {

	private static final long serialVersionUID = 1L;
	
	public ConversionException(IStatus status) {
		super(status);
	}
	
	public ConversionException(String message, Throwable t) {
		this(ConverterActivator.createErrorStatus(message, t));
	}

	public ConversionException(String message) {
		this(message, null);
	}

}

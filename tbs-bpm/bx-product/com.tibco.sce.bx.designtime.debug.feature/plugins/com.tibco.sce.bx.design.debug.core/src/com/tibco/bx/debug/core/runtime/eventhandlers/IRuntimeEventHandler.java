package com.tibco.bx.debug.core.runtime.eventhandlers;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent;

public interface IRuntimeEventHandler {
	
	boolean handleRuntimeEvent(BxRuntimeEvent event) throws CoreException;

}

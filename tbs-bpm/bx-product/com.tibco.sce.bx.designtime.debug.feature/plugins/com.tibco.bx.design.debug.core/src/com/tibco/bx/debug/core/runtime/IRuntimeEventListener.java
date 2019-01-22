package com.tibco.bx.debug.core.runtime;

import org.eclipse.core.runtime.CoreException;


public interface IRuntimeEventListener {

	void notifyEvent(Object event) throws CoreException;
}

package com.tibco.bx.debug.core.runtime.eventhandlers;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.core.models.BxDebugEvent;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.models.IBxThread;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.runtime.events.BxProcessSuspensionEvent;
import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent;

public class SuspendEventHandler implements IRuntimeEventHandler {

	private final IBxDebugTarget debugTarget;

	public SuspendEventHandler(IBxDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
	}

	@Override
	public boolean handleRuntimeEvent(BxRuntimeEvent re) throws CoreException {
		BxProcessSuspensionEvent event = (BxProcessSuspensionEvent) re;

		IBxThread thread = debugTarget.getThread(event.getProcessInstanceId());
		if(debugTarget.isTerminated() || thread == null){
			return false;
		}

		IBxProcessListing processListing = (IBxProcessListing) debugTarget.getProcessListing();
		if (processListing != null) {
			ProcessInstance processInstance = processListing.getProcessInstance(event.getProcessInstanceId());
			if (processInstance != null) {
				processInstance.setState(event.getState());
				processInstance.setSuspendMessage(event.getSuspendMessage());
			}
		}
		thread.setSuspended(true);
		debugTarget.fireBxThreadChanged(thread.getInstanceId(),
				new BxDebugEvent(thread, BxDebugEvent.SUSPENDED, event.getSuspendMessage()));
		
		return true;
	}

}

package com.tibco.bx.debug.core.runtime.eventhandlers;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.models.IBxThread;
import com.tibco.bx.debug.core.runtime.events.BxProcessResumptionEvent;
import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent;

public class ResumeEventHandler implements IRuntimeEventHandler {

	private final IBxDebugTarget debugTarget;

	public ResumeEventHandler(IBxDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
	}

	@Override
	public boolean handleRuntimeEvent(BxRuntimeEvent re) throws CoreException {
		BxProcessResumptionEvent event = (BxProcessResumptionEvent) re;

		IBxThread thread = debugTarget.getThread(event.getProcessInstanceId());
		if(debugTarget.isTerminated() || thread == null){
			return false;
		}
		
		ProcessInstance processInstance = (ProcessInstance)thread.getProcessElement();
		processInstance.setState(event.getState());
		//TODO thread.setSuspended(false);
		
		return true;
	}

}

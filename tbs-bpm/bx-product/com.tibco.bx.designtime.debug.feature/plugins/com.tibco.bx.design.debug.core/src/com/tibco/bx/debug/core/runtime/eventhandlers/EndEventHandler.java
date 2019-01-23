package com.tibco.bx.debug.core.runtime.eventhandlers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;

import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.models.BxDebugElement;
import com.tibco.bx.debug.core.models.BxDebugEvent;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.models.IBxThread;
import com.tibco.bx.debug.core.runtime.events.BxProcessTerminationEvent;
import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent;
import com.tibco.bx.debug.core.runtime.events.BxProcessTerminationEvent.EndKind;

public class EndEventHandler implements IRuntimeEventHandler {

	private final IBxDebugTarget debugTarget;

	public EndEventHandler(IBxDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
	}

	@Override
	public boolean handleRuntimeEvent(BxRuntimeEvent re) throws CoreException {
		BxProcessTerminationEvent event = (BxProcessTerminationEvent) re;
		
		IBxThread thread = debugTarget.getThread(event.getProcessInstanceId());
		if(debugTarget.isTerminated() || thread == null){
			return false;
		}
		
		((ProcessInstance)thread.getProcessElement()).setState(event.getState());
		
		thread.terminate();
		// when thread exit,then remove high line
		DebugCoreActivator.fireCurrentStackFrameChange(DebugCoreActivator.LINK_BACK_P, null, null);

		((BxDebugElement) thread).fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
		((BxDebugElement) thread).fireTerminateEvent();// do not remove
		// terminated thread
		debugTarget.fireBxThreadChanged(thread.getInstanceId(),
				new BxDebugEvent(thread, getDebugEventKind(event.getEventKind()), event.getVariables()));
		
		return true;
	}

	private int getDebugEventKind(EndKind endKind) {
		switch (endKind) {
		case COMPLETED: return BxDebugEvent.COMPLETED;
		case FAULTED: return BxDebugEvent.FAULT;
		case TERMINATED: return BxDebugEvent.TERMINATED;
		default: return -1;
		}
	}

}

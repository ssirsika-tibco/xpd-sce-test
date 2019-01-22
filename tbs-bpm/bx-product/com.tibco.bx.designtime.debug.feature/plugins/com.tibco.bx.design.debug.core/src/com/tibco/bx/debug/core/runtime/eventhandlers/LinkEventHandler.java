package com.tibco.bx.debug.core.runtime.eventhandlers;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessLink;
import com.tibco.bx.debug.common.model.ProcessVisibleNode;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.models.IBxStackFrame;
import com.tibco.bx.debug.core.models.IBxThread;
import com.tibco.bx.debug.core.runtime.events.BxProcessLinkEvent;
import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent;

public class LinkEventHandler implements IRuntimeEventHandler {

	private final IBxDebugTarget debugTarget;

	public LinkEventHandler(IBxDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
	}

	@Override
	public boolean handleRuntimeEvent(BxRuntimeEvent re) throws CoreException {
		BxProcessLinkEvent event = (BxProcessLinkEvent) re;

		IBxThread thread = debugTarget.getThread(event.getProcessInstanceId());
		if(debugTarget.isTerminated() || thread == null){
			return false;
		}

		IBxStackFrame toStackFrame = thread.getStackFrame(event.getToInstanceId());
		IBxStackFrame fromStackFrame = thread.getStackFrame(event.getFromInstanceId());
		ProcessLink link = new ProcessLink((ProcessInstance)thread.getProcessElement(), 
					event.getLinkId(), event.getLinkName(),
					event.getLinkInstanceId(), 
					(ProcessVisibleNode) fromStackFrame.getProcessElement(),
					(ProcessVisibleNode) toStackFrame.getProcessElement());
		((ProcessVisibleNode) toStackFrame.getProcessElement()).addLink(link);
		((ProcessInstance) thread.getProcessElement()).addLink(link);
		DebugCoreActivator
			.fireCurrentStackFrameChange(DebugCoreActivator.LINK_BACK_P, fromStackFrame, toStackFrame);
		return true;
	}

}

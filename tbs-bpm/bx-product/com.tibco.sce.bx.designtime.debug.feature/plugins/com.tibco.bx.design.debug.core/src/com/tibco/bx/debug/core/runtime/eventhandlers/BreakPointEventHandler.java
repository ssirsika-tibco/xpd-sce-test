package com.tibco.bx.debug.core.runtime.eventhandlers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.models.BxDebugElement;
import com.tibco.bx.debug.core.models.BxDebugEvent;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.models.IBxStackFrame;
import com.tibco.bx.debug.core.models.IBxThread;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.runtime.events.BxBreakpointEvent;
import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent;

public class BreakPointEventHandler implements IRuntimeEventHandler {

	private final IBxDebugTarget debugTarget;

	public BreakPointEventHandler(IBxDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
	}

	@Override
	public boolean handleRuntimeEvent(BxRuntimeEvent re) throws CoreException {
		BxBreakpointEvent event = (BxBreakpointEvent) re;

		String processInstanceId = event.getProcessInstanceId();
		IBxThread thread = debugTarget.getThread(processInstanceId);
		if(debugTarget.isTerminated() || thread == null){
			return false;
		}
		// thread is not null
		IBxProcessListing processListing = debugTarget.getProcessListing();
		if (processListing == null) {
			return false;
		}
		/*IBxProcessActivity[] activities = processListing.getActivities(processInstanceId);
		// reset state of activities
		for(int i =0; i < activities.length; i++){
			String tempId = activities[i].getInstanceId();
			BxProcessNode pa = (BxProcessNode) processInstance.getDescendant(tempId);
			if (pa != null) {
				pa.setState(activities[i].getState());
			}
		}	*/
		// get stackFrame according to this activity
		suspendAt(thread, thread.getStackFrame(event.getActivityInstanceId()), event.isOnEntry());
		return true;
	}

	private void suspendAt(IBxThread thread, IBxStackFrame hitStackFrame, boolean isOnEntry){
		// fire event
		((ProcessInstance) thread.getProcessElement()).setState(ProcessInstance.State.SUSPENDED);
		hitStackFrame.setBreakWhen(isOnEntry ? BreakWhen.ENTRY:BreakWhen.RETURN);
		thread.setTopStackFrame(hitStackFrame);
		thread.setSuspended(true);
		String processInstanceId = thread.getInstanceId();
		debugTarget.fireBxThreadChanged(processInstanceId, 
				new BxDebugEvent(hitStackFrame, isOnEntry ? 
						BxDebugEvent.ON_ENTRY:BxDebugEvent.ON_EXIT));
		((BxDebugElement) thread).fireSuspendEvent(DebugEvent.BREAKPOINT);
		DebugCoreActivator.fireCurrentStackFrameChange(
				DebugCoreActivator.CURRENT_STACKFRAME_P, null,hitStackFrame);
	}

}

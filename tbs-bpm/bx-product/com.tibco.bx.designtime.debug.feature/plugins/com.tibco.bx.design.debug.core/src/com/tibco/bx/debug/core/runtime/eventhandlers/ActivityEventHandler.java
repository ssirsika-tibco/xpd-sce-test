package com.tibco.bx.debug.core.runtime.eventhandlers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IThread;

import com.tibco.bx.debug.api.IDebugConstants;
import com.tibco.bx.debug.common.model.ProcessContainer;
import com.tibco.bx.debug.common.model.ProcessElement;
import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessVisibleNode;
import com.tibco.bx.debug.core.models.BxDebugElement;
import com.tibco.bx.debug.core.models.BxDebugEvent;
import com.tibco.bx.debug.core.models.BxStackFrame;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.models.IBxStackFrame;
import com.tibco.bx.debug.core.models.IBxThread;
import com.tibco.bx.debug.core.runtime.events.BxActivityCreationEvent;
import com.tibco.bx.debug.core.runtime.events.BxActivityTerminationEvent;
import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent;
import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent.EventType;

public class ActivityEventHandler implements IRuntimeEventHandler {

	protected final IBxDebugTarget debugTarget;

	public ActivityEventHandler(IBxDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
	}
	
	@Override
	public boolean handleRuntimeEvent(BxRuntimeEvent re) throws CoreException {
		if (EventType.ActivityCreation.equals(re.getEventType())) {
			return handleActivityStarted((BxActivityCreationEvent)re);
		} else {
			return handleActivityEnded((BxActivityTerminationEvent)re);
		}
		
	}

	private boolean handleActivityStarted(BxActivityCreationEvent event) throws CoreException {
		IBxThread thread = debugTarget.getThread(event.getProcessInstanceId());
		if(debugTarget.isTerminated() || thread == null){
			return false;
		}
		ProcessVisibleNode processNode = constructProcessNode(
				thread, event.getId(), event.getName(), 
				event.getActivityId(), event.getType(), event.getState(), event.getParentInstanceId());
		IBxStackFrame bxStackFrame = constructStackFrame(thread, processNode);

		((BxDebugElement)bxStackFrame).fireCreationEvent();
		debugTarget.fireBxThreadChanged(thread.getInstanceId(),
				new BxDebugEvent(bxStackFrame, BxDebugEvent.STARTED));
		return true;
	}

	private boolean handleActivityEnded(BxActivityTerminationEvent event) throws CoreException {
		// activity ended, set state and return.
		IBxThread thread = debugTarget.getThread(event.getProcessInstanceId());
		if(debugTarget.isTerminated() || thread == null){
			return false;
		}
		ProcessInstance processInstance = (ProcessInstance) thread.getProcessElement();
		ProcessElement descendant = processInstance.getDescendant(event.getActivityInstanceId());
		if (null != descendant) {
			((ProcessVisibleNode) descendant).setState(event.getState());
			IBxStackFrame stackFrame = thread.getStackFrame(event.getActivityInstanceId());
			if (stackFrame != null) {
				debugTarget.fireBxThreadChanged(thread.getInstanceId(),
						new BxDebugEvent(stackFrame, BxDebugEvent.COMPLETED));
			}
		}
		
		return true;
	}

	protected ProcessVisibleNode constructProcessNode(IBxThread bxThread, String id, 
			String name, String activityId, String type, String state, String parentInstanceId) {
		ProcessInstance instance = (ProcessInstance) bxThread.getProcessElement();

		ProcessContainer parent = null;
		if (!parentInstanceId.equals(IDebugConstants.NULL_ATTR)) {
			parent = (ProcessContainer) instance.getDescendant(parentInstanceId);
		}
		if (parent == null) {
			parent = instance;
		}

		ProcessVisibleNode node = new ProcessVisibleNode(id, name, activityId, parent);
		node.setType(type);
		node.setState(state);
		return node;
	}

	private IBxStackFrame constructStackFrame(
			IBxThread bxThread, ProcessVisibleNode processNode) {
		IBxStackFrame bxStackFrame = null;
		IThread parent = null;
		String parentInstanceId = processNode.getParent().getInstanceId();
		if (parentInstanceId.equals(bxThread.getInstanceId())) {
			parent = bxThread;
		} else {
			parent = bxThread.getStackFrame(parentInstanceId);
		}
		// construct stackframe
		bxStackFrame = new BxStackFrame(processNode,parent, bxThread);
		return bxStackFrame;
	}

}

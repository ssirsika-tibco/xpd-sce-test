package com.tibco.bx.debug.http.runtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.bx.debug.api.DebuggerMBean;
import com.tibco.bx.debug.api.IDebugConstants;
import com.tibco.bx.debug.api.IDebuggerEvents;
import com.tibco.bx.debug.core.Tracing;
import com.tibco.bx.debug.core.runtime.events.BxActivityCreationEvent;
import com.tibco.bx.debug.core.runtime.events.BxActivityTerminationEvent;
import com.tibco.bx.debug.core.runtime.events.BxBreakpointEvent;
import com.tibco.bx.debug.core.runtime.events.BxProcessCreationEvent;
import com.tibco.bx.debug.core.runtime.events.BxProcessEvent;
import com.tibco.bx.debug.core.runtime.events.BxProcessLinkEvent;
import com.tibco.bx.debug.core.runtime.events.BxProcessResumptionEvent;
import com.tibco.bx.debug.core.runtime.events.BxProcessSuspensionEvent;
import com.tibco.bx.debug.core.runtime.events.BxProcessTerminationEvent;
import com.tibco.bx.debug.core.runtime.events.BxProcessTerminationEvent.EndKind;
import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent;
import com.tibco.bx.debug.core.runtime.events.BxTemplateRegisterEvent;
import com.tibco.bx.debug.core.runtime.events.IBxRuntimeEventFactory;
import com.tibco.bx.debug.http.util.HTTPDataManager;
import com.tibco.www.bx._2010.debugging.debuggerType.NameValuePair;
import com.tibco.www.bx._2010.debugging.debuggerType.Notification;
import com.tibco.www.bx._2010.debugging.debuggerType.Variable;

public class HTTPRuntimeEventFactory implements IBxRuntimeEventFactory {

	@Override
	public BxRuntimeEvent createRuntimeEvent(Object receivedEvent) {
		if (!(receivedEvent instanceof Notification)) {
			return null;
		}

		Notification notification = (Notification) receivedEvent;

		String eventType = notification.getType();
		if (!eventType.startsWith("com.tibco.bx.")) {//$NON-NLS-1$
			// it's not a debug event
			return null;
		}
		List<NameValuePair> nameValuePairs = notification.getData().getEventAttribute();
		Map<String, String> data = new HashMap<String, String>();
		for (NameValuePair nameValuePair : nameValuePairs) {
			data.put(nameValuePair.getName(), nameValuePair.getValue());
		}
		if (IDebuggerEvents.EVENT_START.equals(eventType)) {
			if (Tracing.ENABLED)
				HTTPDataManager.printLifeCycleNotificationData(eventType, data, notification.getMessage());
			String processTemplateId = (String) data.get(DebuggerMBean.PROCESS_ID);
			String processInstanceId = (String) data.get(DebuggerMBean.PROCESS_INSTANCE_ID);
			String state = (String) data.get(DebuggerMBean.PROCESS_STATE);
			Boolean isStarter = Boolean.valueOf(data.get(DebuggerMBean.STARTER));
			BxProcessCreationEvent event = new BxProcessCreationEvent(processTemplateId, processInstanceId, state, isStarter);
			// TODO setVariables(event, notification.getVariables());
			return event;
		} else if (IDebuggerEvents.EVENT_ONENTRY.equals(eventType) || IDebuggerEvents.EVENT_ONEXIT.equals(eventType)) {
			if (Tracing.ENABLED)
				HTTPDataManager.printBreakpointNotificationData(eventType, data);
			String processInstanceId = (String) data.get(DebuggerMBean.PROCESS_INSTANCE_ID);
			String activityId = (String) data.get(DebuggerMBean.ACTIVITY_ID);
			String activityInstanceId = (String) data.get(DebuggerMBean.INSTANCE_ID);
			return new BxBreakpointEvent(processInstanceId, activityId, activityInstanceId, IDebuggerEvents.EVENT_ONENTRY.equals(eventType));
		} else if (IDebuggerEvents.EVENT_TERMINATE.equals(eventType) || IDebuggerEvents.EVENT_FAULT.equals(eventType)
				|| IDebuggerEvents.EVENT_COMPLETE.equals(eventType)) {
			if (Tracing.ENABLED)
				HTTPDataManager.printLifeCycleNotificationData(eventType, data, notification.getMessage());
			String processInstanceId = (String) data.get(DebuggerMBean.PROCESS_INSTANCE_ID);
			String state = (String) data.get(DebuggerMBean.PROCESS_STATE);
			EndKind eventKind = getEndKind(eventType);
			BxProcessTerminationEvent event = new BxProcessTerminationEvent(processInstanceId, state, eventKind);
			// TODO setVariables(event, notification.getVariables());
			return event;
		} else if (IDebuggerEvents.EVENT_SUSPEND.equals(eventType)) {
			if (Tracing.ENABLED)
				HTTPDataManager.printLifeCycleNotificationData(eventType, data, notification.getMessage());
			String processInstanceId = (String) data.get(DebuggerMBean.PROCESS_INSTANCE_ID);
			String state = (String) data.get(DebuggerMBean.PROCESS_STATE);
			return new BxProcessSuspensionEvent(processInstanceId, state, notification.getMessage());
		} else if (IDebuggerEvents.EVENT_RESUME.equals(eventType)) {
			if (Tracing.ENABLED)
				HTTPDataManager.printLifeCycleNotificationData(eventType, data, notification.getMessage());
			String processInstanceId = (String) data.get(DebuggerMBean.PROCESS_INSTANCE_ID);
			String state = (String) data.get(DebuggerMBean.PROCESS_STATE);
			return new BxProcessResumptionEvent(processInstanceId, state);
		} else if (IDebuggerEvents.EVENT_LINK.equals(eventType)) {
			if (Tracing.ENABLED)
				HTTPDataManager.printLinkNotificationData(eventType, data);
			String processInstanceId = (String) data.get(DebuggerMBean.PROCESS_INSTANCE_ID);
			String linkID = (String) data.get(DebuggerMBean.LINK_ID);
			String linkName = (String) data.get(DebuggerMBean.LINK_NAME);
			String linkInstanceID = (String) data.get(DebuggerMBean.INSTANCE_ID);

			String fromInstanceId = (String) data.get(DebuggerMBean.LINK_FROM_ID);
			String toInstanceId = (String) data.get(DebuggerMBean.LINK_TO_ID);

			return new BxProcessLinkEvent(processInstanceId, linkID, linkName, linkInstanceID, fromInstanceId, toInstanceId);
		} else if (IDebuggerEvents.EVENT_ACTIVITY.equals(eventType)) {
			if (Tracing.ENABLED)
				HTTPDataManager.printActivityNotificationData(eventType, data);
			String processInstanceId = (String) data.get(DebuggerMBean.PROCESS_INSTANCE_ID);
			Integer status = Integer.valueOf(data.get(DebuggerMBean.STATUS));
			boolean activityEnded = status.intValue() == DebuggerMBean.ACTIVITY_END;
			String state = data.get(DebuggerMBean.ACTIVITY_STATE);
			if (activityEnded) {
				String instanceId = (String) data.get(DebuggerMBean.INSTANCE_ID);
				return new BxActivityTerminationEvent(processInstanceId, instanceId, state);
			} else {
				// activity start event, create a new node and then create a new
				// StackFrame
				String id = (String) data.get(DebuggerMBean.INSTANCE_ID);
				String name = (String) data.get(DebuggerMBean.ACTIVITY_NAME);
				String type = (String) (data.get(DebuggerMBean.ACTIVITY_TYPE));
				String activityId = (String) data.get(DebuggerMBean.ACTIVITY_ID);
				String parentInstanceId = (String) data.get(DebuggerMBean.PARENT_INSTANCE_ID);
				return new BxActivityCreationEvent(processInstanceId, id, name, activityId, type, state, parentInstanceId);
			}
		} else if (IDebuggerEvents.EVENT_TEMPLATE.equals(eventType)) {
			if (Tracing.ENABLED)
				HTTPDataManager.printTemplateNotificationData(eventType, data);
			String processId = (String) data.get(DebuggerMBean.PROCESS_ID);
			String processName = (String) data.get(DebuggerMBean.PROCESS_NAME);
			String moduleName = (String) (data.get(DebuggerMBean.MODULE_NAME));
			String moduleVersion = (String) data.get(DebuggerMBean.MODULE_VERSION);
			Integer status = Integer.valueOf(data.get(DebuggerMBean.STATUS));
			if (status == 1)
				return new BxTemplateRegisterEvent(processId, processName, moduleName, moduleVersion);
		}
		if (Tracing.ENABLED)
			HTTPDataManager.printUnsupportedEvent(eventType);
		return null;
	}

	private EndKind getEndKind(String eventType) {
		if (IDebuggerEvents.EVENT_COMPLETE.equals(eventType)) {
			return EndKind.COMPLETED;
		} else if (IDebuggerEvents.EVENT_FAULT.equals(eventType)) {
			return EndKind.FAULTED;
		} else if (IDebuggerEvents.EVENT_TERMINATE.equals(eventType)) {
			return EndKind.TERMINATED;
		}
		return EndKind.COMPLETED;
	}

	private void setVariables(BxProcessEvent event, Variable[] variables) {
		for (int i = 0; i < variables.length; i++) {
			String instanceId = variables[i].getVariableName();
			String valueString = variables[i].getValue();
			event.addVariable(instanceId, IDebugConstants.NULL_ATTR.equals(valueString) ? null : valueString);
		}
	}
}

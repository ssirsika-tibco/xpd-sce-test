package com.tibco.bx.debug.http.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;

import com.tibco.bx.debug.api.DebuggerMBean;
import com.tibco.bx.debug.common.model.IBxProcessActivity;
import com.tibco.bx.debug.common.model.ProcessContainer;
import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.core.models.variable.IVariableHandler;
import com.tibco.bx.debug.http.HTTPActivator;
import com.tibco.bx.debug.http.runtime.HTTPProcessActivity;
import com.tibco.www.bx._2010.debugging.debuggerType.Activity;
import com.tibco.www.bx._2010.debugging.debuggerType.GetActivitiesOutput;
import com.tibco.www.bx._2010.debugging.debuggerType.GetProcessTemplatesOutput;
import com.tibco.www.bx._2010.debugging.debuggerType.GetVariablesOutput;
import com.tibco.www.bx._2010.debugging.debuggerType.Variable;

public class HTTPDataManager {

	public static List<ProcessTemplate> getProcessTemplates(GetProcessTemplatesOutput listProcessTemplatesOutput) {
		if (listProcessTemplatesOutput == null)
			return new ArrayList<ProcessTemplate>(0);
		List<com.tibco.www.bx._2010.debugging.debuggerType.ProcessTemplate> templates = listProcessTemplatesOutput.getProcessTemplates()
				.getProcessTemplate();
		List<ProcessTemplate> list = new ArrayList<ProcessTemplate>();
		for (com.tibco.www.bx._2010.debugging.debuggerType.ProcessTemplate temp : templates) {
			list.add(getProcessTemplate(temp));
		}
		return list;
	}

	private static ProcessTemplate getProcessTemplate(com.tibco.www.bx._2010.debugging.debuggerType.ProcessTemplate processTemplate) {
		String processId = processTemplate.getProcessId();
		String processName = processTemplate.getProcessName();
		String moduleName = processTemplate.getModuleName();
		String moduleVersion = processTemplate.getModuleVersion();
		ProcessTemplate template = new ProcessTemplate(processId, processName, moduleName, moduleVersion);
		return template;
	}

	public static IBxProcessActivity[] getProcessActivities(GetActivitiesOutput listActivitiesOutput) {
		if (listActivitiesOutput == null) {
			return new IBxProcessActivity[0];
		}
		List<Activity> activities = listActivitiesOutput.getActivities().getActivity();
		List<IBxProcessActivity> processActivities = new ArrayList<IBxProcessActivity>();
		for (Activity activity : activities) {
			processActivities.add(getActivity(activity));
		}
		return processActivities.toArray(new HTTPProcessActivity[activities.size()]);
	}

	private static IBxProcessActivity getActivity(Activity activity) {
		HTTPProcessActivity processActivity = new HTTPProcessActivity(activity);
		return processActivity;
	}

	public static ProcessVariable[] getProcessVariables(IVariableHandler variableHandler, ProcessContainer processContainer,
			GetVariablesOutput listVariablesOutput) throws DebugException {
		if (listVariablesOutput == null) {
			return new ProcessVariable[0];
		}
		List<Variable> variables = listVariablesOutput.getVariables().getVariable();
		List<ProcessVariable> processVariables = new ArrayList<ProcessVariable>();
		for (Variable var : variables) {
			processVariables.add(getProcessVariable(variableHandler, processContainer, var));
		}
		return processVariables.toArray(new ProcessVariable[processVariables.size()]);
	}

	private static ProcessVariable getProcessVariable(IVariableHandler variableHandler, ProcessContainer processContainer, Variable variable)
			throws DebugException {
		String instanceId = variable.getVariableId();
		String name = variable.getVariableName();
		String nameSpace = variable.getNamespace();
		String parentInstanceId = variable.getParentInstanceId();
		String typeName = variable.getType();
		ProcessVariable processVariable = null;
		ProcessInstance processInstance = (processContainer instanceof ProcessInstance) ? (ProcessInstance) processContainer : processContainer
				.getProcessInstance();
		try {
			if (parentInstanceId.equals(processInstance.getInstanceId())) {
				processVariable = new ProcessVariable(instanceId, name, processInstance);
				processVariable.setType(variableHandler.getGlobalVariableType(processInstance.getIndex(), name, nameSpace, typeName));
			} else {
				processVariable = new ProcessVariable(instanceId, name, processContainer);
				processVariable.setType(variableHandler.getLocalVariableType(processInstance.getIndex(), processContainer.getIndex(), name,
						nameSpace, typeName));
			}
			processVariable.setNamespace(variable.getNamespace());
			processVariable.setValue(variableHandler.getValue(processVariable.getType(), variable.getValue()));
		} catch (CoreException e) {
			// TODO
			throw new DebugException(HTTPActivator.newStatus(e, ""));
		}
		return processVariable;
	}

	private static void printRow(String displayName, Map<String, String> data, String key) {
		if (data.containsKey(key)) {
			System.out.print(String.format(" %s=%s", new Object[] { displayName, data.get(key) }));
		}
	}

	private static void printlnRow(String displayName, Map<String, String> data, String key) {
		if (data.containsKey(key)) {
			System.out.println(String.format(" %s=%s", new Object[] { displayName, data.get(key) }));
		}
	}

	public static void printLifeCycleNotificationData(String eventType, Map<String, String> data, String message) {
		System.out.print("event type=" + eventType);
		printRow("Instance_Id", data, DebuggerMBean.PROCESS_INSTANCE_ID);
		printRow("Name", data, DebuggerMBean.PROCESS_NAME);
		printRow("Status", data, DebuggerMBean.STATUS);
		System.out.println(" Message=" + message);
	}

	public static void printBreakpointNotificationData(String eventType, Map<String, String> data) {
		System.out.print("event type=" + eventType);
		printRow("Process_Id", data, DebuggerMBean.PROCESS_INSTANCE_ID);
		printRow("Instance_Id", data, DebuggerMBean.INSTANCE_ID);
		printRow("Activity_Name", data, DebuggerMBean.ACTIVITY_NAME);
		printRow("Break When", data, DebuggerMBean.BREAK_WHEN);
		printRow("Activity_Id", data, DebuggerMBean.ACTIVITY_ID);
		printRow("Activity_Token", data, DebuggerMBean.ACTIVITY_TOKEN);
		printlnRow("Skipped", data, DebuggerMBean.SKIPPED);
	}

	public static void printLinkNotificationData(String eventType, Map<String, String> data) {
		System.out.print("event type=" + eventType);
		printRow("Link name", data, DebuggerMBean.LINK_NAME);
		printRow("Id(XPDL)", data, DebuggerMBean.LINK_ID);
		printRow("Id", data, DebuggerMBean.INSTANCE_ID);
		printRow("Token", data, DebuggerMBean.LINK_TOKEN);
		printRow("From_Id", data, DebuggerMBean.LINK_FROM_ID);
		printRow("From_Name", data, DebuggerMBean.LINK_FROM_TASK_NAME);
		printRow("To_Id", data, DebuggerMBean.LINK_TO_ID);
		printRow("To_Name", data, DebuggerMBean.LINK_TO_TASK_NAME);
		printlnRow("Process_Instance_Id", data, DebuggerMBean.PROCESS_INSTANCE_ID);
	}

	public static void printActivityNotificationData(String eventType, Map<String, String> data) {
		System.out.print("event type=" + eventType);
		printRow("Instance_Id", data, DebuggerMBean.INSTANCE_ID);
		printRow("Activity_Name", data, DebuggerMBean.ACTIVITY_NAME);
		printRow("type", data, DebuggerMBean.ACTIVITY_TYPE);
		printRow("Status", data, DebuggerMBean.STATUS);
		printRow("State", data, DebuggerMBean.ACTIVITY_STATE);
		printRow("Activity_Id", data, DebuggerMBean.ACTIVITY_ID);
		printRow("Activity_Token", data, DebuggerMBean.ACTIVITY_TOKEN);
		printRow("isSkipped", data, DebuggerMBean.SKIPPED);
		printlnRow("Parent_Instance_Id", data, DebuggerMBean.PARENT_INSTANCE_ID);
	}

	public static void printTemplateNotificationData(String eventType, Map<String, String> data) {
		System.out.print("event type=" + eventType);
		printRow("Template_Id", data, DebuggerMBean.PROCESS_ID);
		printRow("Template_Name", data, DebuggerMBean.PROCESS_NAME);
		printRow("Module_Name", data, DebuggerMBean.MODULE_NAME);
		printRow("Module_Version", data, DebuggerMBean.MODULE_VERSION);
		printlnRow("Status", data, DebuggerMBean.STATUS);
	}

	public static void printUnsupportedEvent(String eventType) {
		System.out.println(" unknown event: event type =" + eventType);
	}
}

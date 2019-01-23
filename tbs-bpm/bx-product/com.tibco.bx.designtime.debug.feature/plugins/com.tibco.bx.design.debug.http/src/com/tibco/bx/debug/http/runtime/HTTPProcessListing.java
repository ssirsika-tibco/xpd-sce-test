package com.tibco.bx.debug.http.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.common.model.IBxProcessActivity;
import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.Tracing;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.runtime.IConnection;
import com.tibco.bx.debug.http.HTTPActivator;
import com.tibco.bx.debug.http.Messages;
import com.tibco.bx.debug.http.util.HTTPDataManager;
import com.tibco.bx.debug.operation.ILauncherEventHandler;
import com.tibco.bx.debug.operation.StarterOperation;
import com.tibco.bx.debug.operation.StarterOperationParameter;
import com.tibco.n2.pfe.services.pageflow.PageFlowManagement;
import com.tibco.n2.pfe.services.pageflow.PageflowTransport;
import com.tibco.n2.pfe.services.pageflow.SendPageflowMessageJob;
import com.tibco.www.bx._2009.management.process.ProcessManagement;
import com.tibco.www.bx._2009.management.processManagerType.CreateProcessInstanceInput;
import com.tibco.www.bx._2009.management.processManagerType.NameValuePair;
import com.tibco.www.bx._2009.management.processManagerType.OperationInfo;
import com.tibco.www.bx._2009.management.processManagerType.QualifiedProcessName;
import com.tibco.www.bx._2009.management.processManagerType.TemplateAttribute;
import com.tibco.www.bx._2010.debugging.debugger.Debugger;
import com.tibco.www.bx._2010.debugging.debuggerType.EmptyInput;
import com.tibco.www.bx._2010.debugging.debuggerType.GetActivitiesOutput;
import com.tibco.www.bx._2010.debugging.debuggerType.GetProcessTemplatesOutput;

public class HTTPProcessListing implements IBxProcessListing {

	private final Debugger proxy;
	private final ProcessManagement manager;
	private final PageFlowManagement pfManager;
	private Map<String, ProcessInstance> instanceMap = new HashMap<String, ProcessInstance>();
	private List<ProcessTemplate> templateList = new LinkedList<ProcessTemplate>();
	private IConnection connection;

	public HTTPProcessListing(Debugger proxy, ProcessManagement manager, PageFlowManagement pfManager, IConnection connection) throws CoreException {
		this.proxy = proxy;
		this.manager = manager;
		this.pfManager = pfManager;
		this.connection = connection;
	}

	@Override
	public ProcessTemplate[] getProcessTemplates() throws CoreException {
		templateList.clear();
		if (templateList.isEmpty()) {
			GetProcessTemplatesOutput listProcessTemplatesOutput;
			try {
				if (Tracing.ENABLED)
					Tracing.trace("Calling debugger.getProcessTemplates()"); //$NON-NLS-1$
				listProcessTemplatesOutput = proxy.getProcessTemplates(new EmptyInput());
			} catch (Throwable e) {
				HTTPActivator.log(e);
				throw createCoreException(e, Messages.getString("HTTPProcessListing.refreshProcessTemplates_error_message"));//$NON-NLS-1$
			}
			// adds the ProcessTemplates that loaded after connection being
			// created.
			List<ProcessTemplate> templates = HTTPDataManager.getProcessTemplates(listProcessTemplatesOutput);
			templateList.addAll(templates);
		} else {
			if (Tracing.ENABLED)
				Tracing.trace("Get ProcessTemplates from cache "); //$NON-NLS-1$
		}
		return templateList.toArray(new ProcessTemplate[0]);
	}

	@Override
	public ProcessTemplate[] getProcessTemplates(String processId) {
		List<ProcessTemplate> result = new LinkedList<ProcessTemplate>();
		for (ProcessTemplate template : templateList) {
			if (template.getProcessId().equals(processId)) {
				result.add(template);
			}
		}
		return result.toArray(new ProcessTemplate[0]);
	}

	@Override
	public void addProcessInstance(ProcessInstance instance) {
		if (!instanceMap.containsKey(instance.getInstanceId())) {
			instanceMap.put(instance.getInstanceId(), instance);
		}
	}

	@Override
	public ProcessInstance getProcessInstance(String instanceId) throws CoreException {
		ProcessInstance currentInstance = instanceMap.get(instanceId);
		return currentInstance;
	}

	@Override
	public IBxProcessActivity[] getActivities(String processInstanceId) throws CoreException {
		try {
			GetActivitiesOutput getActivitiesOutput = proxy.getActivities(processInstanceId);
			return HTTPDataManager.getProcessActivities(getActivitiesOutput);
		} catch (Throwable e) {
			HTTPActivator.log(e);
			throw createCoreException(e,
					String.format(Messages.getString("HTTPProcessListing.getActivities_error_message"), new Object[] { processInstanceId }));//$NON-NLS-1$
		}
	}

	@Override
	public StarterOperation[] listStarterOperations(ProcessTemplate processTemplate) throws CoreException {
		List<StarterOperation> starterOperations = new LinkedList<StarterOperation>();
		if (manager == null)
			throw createCoreException(null, Messages.getString("HTTPProcessListing.processManagerValid_error_message"));//$NON-NLS-1$
		try {
			QualifiedProcessName qualifiedProcessName = new QualifiedProcessName();
			qualifiedProcessName.setModuleName(processTemplate.getModuleName());
			qualifiedProcessName.setProcessName(processTemplate.getProcessName());
			qualifiedProcessName.setVersion(processTemplate.getModuleVersion());
			List<com.tibco.www.bx._2009.management.processManagerType.StarterOperation> operations = manager.listStarterOperations(
					qualifiedProcessName).getStarterOperation();
			for (com.tibco.www.bx._2009.management.processManagerType.StarterOperation op : operations) {
				String processName = op.getProcessQName().getProcessName();
				String operationName = op.getOperation();
				String moduleName = op.getProcessQName().getModuleName();
				if (!processTemplate.getProcessName().equals(processName)) {
					continue;
				}

				StarterOperationParameter[] parameters = getStarterOperationParameters(moduleName, processName, operationName,
						processTemplate.getModuleVersion());
				StarterOperation starterOp = new StarterOperation(processTemplate, operationName, parameters);
				starterOperations.add(starterOp);
			}
		} catch (Throwable e) {
			HTTPActivator.log(e);
			throw createCoreException(e, String.format(
					Messages.getString("HTTPProcessListing.listStarterOperations_error_message"), new Object[] { processTemplate.getModuleName() }));//$NON-NLS-1$
		}
		return starterOperations.toArray(new StarterOperation[0]);
	}

	private StarterOperationParameter[] getStarterOperationParameters(String moduleName, String processName, String operationName,
			String moduleVersion) throws Throwable {
		List<StarterOperationParameter> params = new ArrayList<StarterOperationParameter>();
		if (manager == null) {
			throw createCoreException(null, Messages.getString("HTTPProcessListing.processManagerValid_error_message"));//$NON-NLS-1$
		}

		QualifiedProcessName qualifiedProcessName = new QualifiedProcessName();
		qualifiedProcessName.setModuleName(moduleName);
		qualifiedProcessName.setProcessName(processName);
		qualifiedProcessName.setVersion(moduleVersion);

		com.tibco.www.bx._2009.management.processManagerType.StarterOperation startOperation = new com.tibco.www.bx._2009.management.processManagerType.StarterOperation();
		startOperation.setProcessQName(qualifiedProcessName);
		startOperation.setOperation(operationName);

		OperationInfo starterOperationInfo = manager.getStarterOperationInfo(startOperation);
		List<TemplateAttribute> attrs = starterOperationInfo.getParameters().getTemplateAttribute();
		for (TemplateAttribute attr : attrs) {
			String paramName = attr.getName();
			String paramType = attr.getType();
			if ("".equals(paramName)) {
				continue;
			}
			StarterOperationParameter para = new StarterOperationParameter(paramName, paramType);
			params.add(para);
		}
		return params.toArray(new StarterOperationParameter[0]);
	}

	public String createPageflowInstance(ProcessTemplate processTemplate, Map<String, String> parametersMap, ILauncherEventHandler handler)
			throws CoreException {
		if (pfManager == null) {
			throw createCoreException(null, Messages.getString("HTTPProcessListing.pageflowManagerValid_error_message")); //$NON-NLS-1$
		}
		PageflowTransport pfTransport = new PageflowTransport(getConnection());
		if (handler != null) {
			pfTransport.addSelectionChangedListener(handler);
		}
		if (processTemplate != null) {
			SendPageflowMessageJob job = pfTransport.send(processTemplate, parametersMap, handler, pfManager);
			if (handler != null) {
				job.setLauncherEventHandler(handler);
			}
		}
		return null;
	}

	@Override
	public String createProcessInstance(ProcessTemplate processTemplate, String starterOperationName, Map<String, String> parametersMap,
			ILauncherEventHandler handler) throws CoreException {
		try {
//			NameValuePair[] nameValuePairs = new NameValuePair[parametersMap.keySet().size()];
			
			CreateProcessInstanceInput.ParameterMap paraMap = new CreateProcessInstanceInput.ParameterMap();
			for (String key : parametersMap.keySet()) {
				String type = parametersMap.get(key);
				NameValuePair nvp = new NameValuePair();
				nvp.setName(key);
				nvp.setValue(type);
				paraMap.getParameter().add(nvp);
			}
			if (manager == null) {
				throw createCoreException(null, Messages.getString("HTTPProcessListing.processManagerValid_error_message"));//$NON-NLS-1$
			}
			
			QualifiedProcessName qualifiedProcessName = new QualifiedProcessName();
			qualifiedProcessName.setModuleName(processTemplate.getModuleName());
			qualifiedProcessName.setProcessName(processTemplate.getProcessName());
			qualifiedProcessName.setVersion(processTemplate.getModuleVersion());
			
			CreateProcessInstanceInput createProcessInstanceInput = new CreateProcessInstanceInput();
			createProcessInstanceInput.setProcessQName(qualifiedProcessName);
			createProcessInstanceInput.setOperationName(starterOperationName);
			createProcessInstanceInput.setParameterMap(paraMap);
			
			return manager.createProcessInstance(createProcessInstanceInput);
		} catch (Throwable e) {
			HTTPActivator.log(e);
			throw createCoreException(e, String.format(
					Messages.getString("HTTPProcessListing.createProcessInstance_error_message"), new Object[] { processTemplate.getModuleName() }));//$NON-NLS-1$
		}

	}

	private CoreException createCoreException(Throwable e, String message) {
		return (CoreException) new CoreException(HTTPActivator.newStatus(e, message)).initCause(e);
	}

	@Override
	public IConnection getConnection() {
		return connection;
	}

	@Override
	public boolean isProcessManagerValid() {
		return manager != null;
	}

	@Override
	public void registerProcessTemplate(ProcessTemplate processTemplate) {
		templateList.add(processTemplate);
	}

}

package com.tibco.www.bx._2009.management.process;

import java.util.List;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import com.tibco.bx.debug.core.ws.wss.util.WSConstants;
import com.tibco.www.bx._2010.debugging.debugger.SOAPHeaderHandler;

public class ProcessManagerServiceLocator {

	private ProcessManagerService managerService;

	private String username;
	private String password;
	private String soapVersion;

	private String ProcessManagerPort11_address = "http://localhost:8080/bxEngine/node/processManagement/";
	private String ProcessManagerPort12_address = "http://localhost:8080/bxEngine/node/processManagement/";

	public ProcessManagerServiceLocator(String username, String password, String soapVersion) {
		managerService = new ProcessManagerService();
		this.username = username;
		this.password = password;
		this.soapVersion = soapVersion;
	}

	public void setProcessManagerPortAddress(String protocol, String host, int port) {
		if (port == 80) {
			ProcessManagerPort11_address = String.format(protocol + "://%s/bxEngine/node/processManagement/", new Object[] { host });
			ProcessManagerPort12_address = String.format(protocol + "://%s/bxEngine/node/processManagement/", new Object[] { host });
		} else {
			ProcessManagerPort11_address = String.format(protocol + "://%s:%d/bxEngine/node/processManagement/", new Object[] { host, port });
			ProcessManagerPort12_address = String.format(protocol + "://%s:%d/bxEngine/node/processManagement/", new Object[] { host, port });
		}
	}

	public String getProcessManagerPort11Address() {
		return ProcessManagerPort11_address;
	}

	public String getProcessManagerPort12Address() {
		return ProcessManagerPort12_address;
	}

	public ProcessManagement getProcessManagement() throws javax.xml.rpc.ServiceException {
		ProcessManagement manager = null;
		String endpointAddr = ProcessManagerPort11_address;
		if (WSConstants.SOAP11.equals(soapVersion)) {
			manager = managerService.getProcessManagerPort();
		} else {
			manager = managerService.getProcessManagerPort12();
			endpointAddr = ProcessManagerPort12_address;
		}

		BindingProvider bp = (BindingProvider) manager;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddr);

		addAuth(bp);

		return manager;
	}

	public String getProcessManagerPortAddress() {
		if (WSConstants.SOAP11.equals(soapVersion)) {
			return getProcessManagerPort11Address();
		}
		return getProcessManagerPort12Address();
	}

	protected void addAuth(BindingProvider bindingProvider) {
		Binding binding = bindingProvider.getBinding();
		List<Handler> handlerList = binding.getHandlerChain();
		handlerList.add(new SOAPHeaderHandler(username, password));
		bindingProvider.getBinding().setHandlerChain(handlerList);
	}

}

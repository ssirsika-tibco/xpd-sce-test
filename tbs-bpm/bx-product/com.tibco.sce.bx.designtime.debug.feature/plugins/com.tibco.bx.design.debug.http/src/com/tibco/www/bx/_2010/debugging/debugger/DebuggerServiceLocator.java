package com.tibco.www.bx._2010.debugging.debugger;

import java.util.List;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import com.tibco.bx.debug.core.ws.wss.util.WSConstants;

public class DebuggerServiceLocator {

	protected DebuggerService debuggerService;

	protected String username;
	protected String password;
	protected String soapVersion;

	private String DebuggerPort11_address = "http://localhost:8080/bxEngine/node/ProcessDebugger/"; //$NON-NLS-1$
	private String DebuggerPort12_address = "http://localhost:8080/bxEngine/node/ProcessDebugger/"; //$NON-NLS-1$
	private String TesterPort11_address = "http://localhost:8080/bxEngine/node/ProcessTester/"; //$NON-NLS-1$
	private String TesterPort12_address = "http://localhost:8080/bxEngine/node/ProcessTester/"; //$NON-NLS-1$

	private static final String DEFAULT_DEBUGGER_ENDPOINT_URI = "/bxEngine/node/ProcessDebugger/"; //$NON-NLS-1$
	private static final String DEFAULT_TESTER_ENDPOINT_URI = "/bxEngine/node/ProcessTester/"; //$NON-NLS-1$

	public DebuggerServiceLocator(String username, String password, String soapVersion) {
		debuggerService = new DebuggerService();
		this.username = username;
		this.password = password;
		this.soapVersion = soapVersion;
	}

	public void setDebuggerPortAddress(String protocol, String host, int port, String endpointURI) {
		if (port == 80) {
			DebuggerPort11_address = String.format("%s://%s%s", new Object[] { protocol, host, endpointURI }); //$NON-NLS-1$
			TesterPort11_address = String.format("%s://%s%s", new Object[] { protocol, host, formatTesterEndpoint(endpointURI) }); //$NON-NLS-1$

			DebuggerPort12_address = String.format("%s://%s%s", new Object[] { protocol, host, endpointURI }); //$NON-NLS-1$
			TesterPort12_address = String.format("%s://%s%s", new Object[] { protocol, host, formatTesterEndpoint(endpointURI) }); //$NON-NLS-1$
		} else {
			DebuggerPort11_address = String.format("%s://%s:%d%s", new Object[] { protocol, host, port, endpointURI }); //$NON-NLS-1$
			TesterPort11_address = String.format("%s://%s:%d%s", new Object[] { protocol, host, port, formatTesterEndpoint(endpointURI) }); //$NON-NLS-1$

			DebuggerPort12_address = String.format("%s://%s:%d%s", new Object[] { protocol, host, port, endpointURI }); //$NON-NLS-1$
			TesterPort12_address = String.format("%s://%s:%d%s", new Object[] { protocol, host, port, formatTesterEndpoint(endpointURI) }); //$NON-NLS-1$
		}

	}

	public String getTesterPort11Address() {
		return TesterPort11_address;
	}

	public String getTesterPort12Address() {
		return TesterPort12_address;
	}

	public String getDebuggerPort11Address() {
		return DebuggerPort11_address;
	}

	public String getDebuggerPort12Address() {
		return DebuggerPort12_address;
	}

	public Debugger getDebugger() throws javax.xml.rpc.ServiceException {
		Debugger debugger = null;
		String endpointAddr = DebuggerPort11_address;
		if (WSConstants.SOAP11.equals(soapVersion)) {
			debugger = debuggerService.getDebuggerPort();
		} else {
			debugger = debuggerService.getDebuggerPort12();
			endpointAddr = DebuggerPort12_address;
		}

		BindingProvider bp = (BindingProvider) debugger;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddr);

		addAuth(bp);

		return debugger;
	}

	public String getDebuggerPortAddress() {
		if (WSConstants.SOAP11.equals(soapVersion)) {
			return getDebuggerPort11Address();
		}
		return getDebuggerPort12Address();
	}

	public Tester getTester() throws javax.xml.rpc.ServiceException {
		Tester tester = null;
		String endpointAddr = TesterPort11_address;
		if (WSConstants.SOAP11.equals(soapVersion)) {
			tester = debuggerService.getTesterPort();
		} else {
			tester = debuggerService.getTesterPort12();
			endpointAddr = TesterPort12_address;
		}

		BindingProvider bp = (BindingProvider) tester;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddr);

		addAuth(bp);

		return tester;
	}

	public String getTesterPortAddress() {
		if (WSConstants.SOAP11.equals(soapVersion)) {
			return getTesterPort11Address();
		}
		return getTesterPort12Address();
	}

	protected void addAuth(BindingProvider bindingProvider) {
		Binding binding = bindingProvider.getBinding();
		List<Handler> handlerList = binding.getHandlerChain();
		handlerList.add(new SOAPHeaderHandler(username, password));
		bindingProvider.getBinding().setHandlerChain(handlerList);
	}
	
	protected String formatTesterEndpoint(String endpointURI) {
		String testerEndpoint = "";
		int index = endpointURI.indexOf(DEFAULT_DEBUGGER_ENDPOINT_URI);
		if (index < 0) {
			testerEndpoint = DEFAULT_TESTER_ENDPOINT_URI;
		} else {
			String sub = endpointURI.substring(0, index);
			testerEndpoint = sub + DEFAULT_TESTER_ENDPOINT_URI;
		}
		return testerEndpoint;
	}

}

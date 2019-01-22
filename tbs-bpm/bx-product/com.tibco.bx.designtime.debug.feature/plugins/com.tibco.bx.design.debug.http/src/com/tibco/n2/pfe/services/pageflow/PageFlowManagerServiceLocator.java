/**
 * PageFlowService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tibco.n2.pfe.services.pageflow;

import javax.xml.parsers.ParserConfigurationException;

import com.tibco.bx.debug.core.ws.wss.header.WssHeaderBuilder;
import com.tibco.bx.debug.core.ws.wss.util.WSConstants;

public class PageFlowManagerServiceLocator extends org.apache.axis.client.Service implements
		com.tibco.n2.pfe.services.pageflow.PageFlowManagerService {

	private static final long serialVersionUID = 1362717474488992543L;
	
	private String username;
	private String password;
	private String soapVersion;

	public PageFlowManagerServiceLocator(String username, String password, String soapVersion) {
		this.username = username;
		this.password = password;
		this.soapVersion = soapVersion;
	}

	public void setPageFlowManagerPortAddress(String protocol, String host, int port) {
		if (port == 80) {
			PageFlowService_address = String.format(protocol + "://%s/amxbpm/PageFlowService", new Object[] { host });
			PageFlowService12_address = String.format(protocol + "://%s/amxbpm/PageFlowService", new Object[] { host });
		} else {
			PageFlowService_address = String.format(protocol + "://%s:%d/amxbpm/PageFlowService", new Object[] { host, port });
			PageFlowService12_address = String.format(protocol + "://%s:%d/amxbpm/PageFlowService", new Object[] { host, port });
		}
	}

	public PageFlowManagerServiceLocator() {
	}

	public PageFlowManagerServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public PageFlowManagerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for PageFlowService12
	private java.lang.String PageFlowService12_address = "http://localhost:8080/amxbpm/PageFlowService";

	// public java.lang.String getPageFlowService12Address() {
	// return PageFlowService12_address;
	// }

	// The WSDD service name defaults to the port name.
	private java.lang.String PageFlowService12WSDDServiceName = "PageFlowService12";

	public java.lang.String getPageFlowService12WSDDServiceName() {
		return PageFlowService12WSDDServiceName;
	}

	public void setPageFlowService12WSDDServiceName(java.lang.String name) {
		PageFlowService12WSDDServiceName = name;
	}

	public com.tibco.n2.pfe.services.pageflow.PageFlowManagement getPageFlowPort12() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(PageFlowService12_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getPageFlowPort12(endpoint);
	}

	public com.tibco.n2.pfe.services.pageflow.PageFlowManagement getPageFlowPort12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			com.tibco.n2.pfe.services.pageflow.PageFlowManagerBinding12Stub _stub = new com.tibco.n2.pfe.services.pageflow.PageFlowManagerBinding12Stub(
					portAddress, this);
			_stub.setPortName(getPageFlowService12WSDDServiceName());
			if (username != null && !"".equals(username.trim())
					&& (portAddress.getProtocol().equalsIgnoreCase("https") || portAddress.getProtocol().equalsIgnoreCase("http"))) {
				_stub.setHeader(WssHeaderBuilder.instances.getWssHeaderForSoap(username, password));
			}
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		} catch (ParserConfigurationException e) {
			return null;
		}
	}

	public void setPageFlowService12EndpointAddress(java.lang.String address) {
		PageFlowService12_address = address;
	}

	// Use to get a proxy class for PageFlowService
	private java.lang.String PageFlowService_address = "http://localhost:8080/amxbpm/PageFlowService";

	// public java.lang.String getPageFlowServiceAddress() {
	// return PageFlowService_address;
	// }

	// The WSDD service name defaults to the port name.
	private java.lang.String PageFlowServiceWSDDServiceName = "PageFlowService";

	public java.lang.String getPageFlowServiceWSDDServiceName() {
		return PageFlowServiceWSDDServiceName;
	}

	public void setPageFlowServiceWSDDServiceName(java.lang.String name) {
		PageFlowServiceWSDDServiceName = name;
	}

	public com.tibco.n2.pfe.services.pageflow.PageFlowManagement getPageFlowPort() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(PageFlowService_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getPageFlowPort(endpoint);
	}

	public com.tibco.n2.pfe.services.pageflow.PageFlowManagement getPageFlowPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			com.tibco.n2.pfe.services.pageflow.PageFlowManagerBindingStub _stub = new com.tibco.n2.pfe.services.pageflow.PageFlowManagerBindingStub(
					portAddress, this);
			_stub.setPortName(getPageFlowServiceWSDDServiceName());
			if (username != null && !"".equals(username.trim())
					&& (portAddress.getProtocol().equalsIgnoreCase("https") || portAddress.getProtocol().equalsIgnoreCase("http"))) {
				_stub.setHeader(WssHeaderBuilder.instances.getWssHeaderForSoap(username, password));
			}
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		} catch (ParserConfigurationException e) {
			return null;
		}
	}

	public void setPageFlowServiceEndpointAddress(java.lang.String address) {
		PageFlowService_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown. This
	 * service has multiple ports for a given interface; the proxy
	 * implementation returned may be indeterminate.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (com.tibco.n2.pfe.services.pageflow.PageFlowManagement.class.isAssignableFrom(serviceEndpointInterface)) {
				com.tibco.n2.pfe.services.pageflow.PageFlowManagerBinding12Stub _stub = new com.tibco.n2.pfe.services.pageflow.PageFlowManagerBinding12Stub(
						new java.net.URL(PageFlowService12_address), this);
				_stub.setPortName(getPageFlowService12WSDDServiceName());
				return _stub;
			}
			if (com.tibco.n2.pfe.services.pageflow.PageFlowManagement.class.isAssignableFrom(serviceEndpointInterface)) {
				com.tibco.n2.pfe.services.pageflow.PageFlowManagerBindingStub _stub = new com.tibco.n2.pfe.services.pageflow.PageFlowManagerBindingStub(
						new java.net.URL(PageFlowService_address), this);
				_stub.setPortName(getPageFlowServiceWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
				+ (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("PageFlowService12".equals(inputPortName)) {
			return getPageFlowPort12();
		} else if ("PageFlowService".equals(inputPortName)) {
			return getPageFlowPort();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://services.pfe.n2.tibco.com", "PageFlowService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName("http://services.pfe.n2.tibco.com", "PageFlowService12"));
			ports.add(new javax.xml.namespace.QName("http://services.pfe.n2.tibco.com", "PageFlowService"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
		if ("PageFlowService12".equals(portName)) {
			setPageFlowService12EndpointAddress(address);
		} else if ("PageFlowService".equals(portName)) {
			setPageFlowServiceEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

	@Override
	public String getPageFlowPortAddress() {
		return PageFlowService_address;
	}

	@Override
	public String getPageFlowPort12Address() {
		return PageFlowService12_address;
	}

	public String getPageFlowManagementAddress() {
		if (WSConstants.SOAP11.equals(soapVersion)) {
			return getPageFlowPortAddress();
		}
		return getPageFlowPort12Address();
	}

	public com.tibco.n2.pfe.services.pageflow.PageFlowManagement getPageFlowManagement() throws javax.xml.rpc.ServiceException {
		if (WSConstants.SOAP11.equals(soapVersion)) {
			return getPageFlowPort();
		}
		return getPageFlowPort12();
	}

}

package com.tibco.bx.composite.core.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.Service;
import org.eclipse.wst.wsdl.binding.http.HTTPAddress;
import org.eclipse.wst.wsdl.binding.soap.SOAPAddress;
import org.eclipse.wst.wsdl.binding.soap.SOAPBinding;
import org.eclipse.wst.wsdl.binding.soap.SOAPBody;

public class WSDLUtils {

	/**
	 * this method used to read the initialize uri from giving portType
	 * 
	 * @param portType
	 */
	public static String getBindingLocation(PortType portType) {		
		Binding binding = getBinding(portType);
		if (binding != null) {
			Collection<Service> services = portType.getEnclosingDefinition().getServices().values();
			for (Service service : services) {
				Port port = null;
				Collection<Port> ports = service.getPorts().values();
				for (Port p : ports) {
					if (p.getBinding() == binding) {
						port = p;
						break;
					}
				}
				if (port != null) {
					List addresses = port.getExtensibilityElements();
					for (Object address : addresses) {
						if (address instanceof SOAPAddress) {
							return ((SOAPAddress) address).getLocationURI();
						} else if (address instanceof HTTPAddress) {
							return ((HTTPAddress) address).getLocationURI();
						}
					}
					break;
				}
			}
		}
		
		return ""; //$NON-NLS-1$
	}

	/**
	 * this method used to read the initialize binding style from giving portType
	 * 
	 * @param portType
	 */
	public static String getBindingStyle(PortType portType){
		String bindingStyle = ""; //$NON-NLS-1$
		Binding binding = getBinding(portType);
		if (binding != null) {
			EList<ExtensibilityElement> extensibilityElements = binding
					.getEExtensibilityElements();
			for (ExtensibilityElement ee : extensibilityElements) {
				if (ee instanceof SOAPBinding) {
					bindingStyle = ((SOAPBinding) ee).getStyle();
					break;
				}
			}

		}
		return bindingStyle;
	}
	
	public static String getOperationEncoding(Operation operation) {
		PortType portType = (PortType) operation.eContainer();
		Binding binding = getBinding(portType);
		if (binding != null) {
			List<BindingOperation> bindingOperations = binding
					.getBindingOperations();
			for (BindingOperation o : bindingOperations) {
				if (o.getOperation() == operation) {
					List<ExtensibilityElement> extensibilityElements = 
						o.getEBindingInput().getEExtensibilityElements();
					for (ExtensibilityElement ee : extensibilityElements) {
						if (ee instanceof SOAPBody) {
							return ((SOAPBody) ee).getUse();
						}
					}
				}
			}

		}
		
		return null;
	}
	
	public static String getBindingTransport(PortType portType){
		String transportURI = ""; //$NON-NLS-1$
		Binding binding = getBinding(portType);
		if (binding != null) {
			EList<ExtensibilityElement> extensibilityElements = binding
					.getEExtensibilityElements();
			for (ExtensibilityElement ee : extensibilityElements) {
				if (ee instanceof SOAPBinding) {
					transportURI = ((SOAPBinding) ee).getTransportURI();
					break;
				}
			}

		}
		if(!transportURI.equals("")){
			int lastDash = transportURI.lastIndexOf("/");
			transportURI = transportURI.substring(lastDash+1);
		}
		return transportURI;
	}
	
	public static Binding getBinding(PortType portType){
		Definition definition = portType.getEnclosingDefinition();
		Map bindings = definition.getBindings();
		Binding binding = null;
		Iterator bindingIterator = bindings.values().iterator();
		while (bindingIterator.hasNext()) {
			binding = (Binding) bindingIterator.next();
			if (binding.getPortType() == portType) {
				break;
			}
		}
		return binding;
	}
}

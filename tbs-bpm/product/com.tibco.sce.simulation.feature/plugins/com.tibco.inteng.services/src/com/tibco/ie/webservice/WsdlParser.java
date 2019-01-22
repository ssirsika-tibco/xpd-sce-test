package com.tibco.ie.webservice;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Binding;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOperation;
import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;

public class WsdlParser {
	private String wsdlFileUri;

	private String serviceName;

	private String portName;

	private String operationName;

	private String inputMessageName;

	private String outputMessageName;

	public static final String SOAP_SCHEMA_URL = "http://schemas.xmlsoap.org/wsdl/soap/";

	private static final String SOAP_SERVER_ADDRESS_ELEMENT = "address";

	private static final String SOAP_BINDING_ELEMENT = "binding";

	private static final String SOAP_OPERATION_ELEMENT = "operation";

	public static final String RPC_MESSAGING_STYLE = "rpc";

	public static final String DOCUMENT_MESSAGING_STYLE = "document";

	/*
	 * This variable specifies the messaging style of Web service whether rpc or
	 * document.
	 */
	private String defaultMessagingStyle;

	/*
	 * This variable specifies the style of Operation whether rpc or document.
	 * As it is optional, so might not be there in the WSDL file.
	 */
	private String oprMessagingStyle;

	/*
	 * This variable specifies the URI which needs to be specified as a
	 * SOAPAction header field of the HTTP request message.
	 */
	private String soapActionURI;

	/*
	 * This variable specifies the URI for the SOAP server to which we need to
	 * send the SOAP message
	 */
	private String soapServerURI;

	private String targetNameSpace;

	private Message inputMessage;

	private Message outputMessage;

	private Map faultsMap;

	/**
	 * 
	 * @param wsdlFileUri
	 * @param serviceName
	 * @param portName
	 * @param operationName
	 * @param inputMessageName
	 * @param outputMessageName
	 */
	public WsdlParser(String wsdlFileUri, String serviceName, String portName,
			String operationName, String inputMessageName,
			String outputMessageName) {
		this.wsdlFileUri = wsdlFileUri;
		this.serviceName = serviceName;
		this.portName = portName;
		this.operationName = operationName;
		this.inputMessageName = inputMessageName;
		this.outputMessageName = outputMessageName;
	}

	/**
	 * @param wsdlFileUri
	 * @param serviceName
	 * @param portName
	 * @param operationName
	 */
	public WsdlParser(String wsdlFileUri, String serviceName, String portName,
			String operationName) {
		this(wsdlFileUri, serviceName, portName, operationName, null, null);
	}

	/**
	 * 
	 * @param wsdlFileUri
	 */
	public WsdlParser(String wsdlFileUri) {
		this(wsdlFileUri, null, null, null, null, null);
	}

	/**
	 * 
	 * 
	 * @return
	 * @throws WSDLException 
	 */
	public void parseWSDL() throws WSDLException {

		WSDLFactory factory = WSDLFactory.newInstance();
		WSDLReader reader = factory.newWSDLReader();
		reader.setFeature("javax.wsdl.verbose", true);
		reader.setFeature("javax.wsdl.importDocuments", true);
		Definition def = reader.readWSDL(null, wsdlFileUri);
		Service service = getService(def);
		Port port = getPort(service);
		QName soapServerQN = new QName(WsdlParser.SOAP_SCHEMA_URL,
				WsdlParser.SOAP_SERVER_ADDRESS_ELEMENT);
		soapServerURI = getSOAPServerURI(port, soapServerQN);
		Binding binding = port.getBinding();
		/*
		 * get the value of style and transport attributes of soap:binding
		 * element
		 */
		QName soapBindingQN = new QName(WsdlParser.SOAP_SCHEMA_URL,
				WsdlParser.SOAP_BINDING_ELEMENT);
		defaultMessagingStyle = getWebServiceStyle(binding, soapBindingQN);
		String transportURI = getTransportURI(binding, soapBindingQN);
		BindingOperation bindingOperation = getOperation(binding);
		soapActionURI = getSOAPActionURI(bindingOperation);
		oprMessagingStyle = getOprMessagingStyle(bindingOperation);
		PortType portType = binding.getPortType();
		Operation operation = getOperation(portType);
		setOperationProperties(operation);
		if (oprMessagingStyle != null && oprMessagingStyle.length() > 0) {
			targetNameSpace = getTargetNamespace(def, bindingOperation,
					oprMessagingStyle);
		} else {
			targetNameSpace = getTargetNamespace(def, bindingOperation,
					defaultMessagingStyle);
		}
		// String tnsPrefix = def.getPrefix(targetNameSpace);
	}

	/**
	 * 
	 * @param operation
	 */
	private void setOperationProperties(Operation operation) {
		inputMessage = operation.getInput().getMessage();
		outputMessage = operation.getOutput().getMessage();
		faultsMap = operation.getFaults();
		if (inputMessageName == null || inputMessageName.length() < 1) {
			inputMessageName = operation.getInput().getMessage().getQName()
					.getLocalPart();
		}
		if (outputMessageName == null || outputMessageName.length() < 1) {
			outputMessageName = operation.getOutput().getMessage().getQName()
					.getLocalPart();
		}
	}

	/**
	 * This method will return Service element from the definition element.
	 * 
	 * @param definition
	 * @return
	 */
	private Service getService(Definition definition) {
		Service service = null;
		if (serviceName == null || serviceName.length() < 1) {
			Map servicesMap = definition.getServices();
			Set set = servicesMap.entrySet();
			for (Iterator iter = set.iterator(); iter.hasNext();) {
				Map.Entry element = (Map.Entry) iter.next();
				service = (Service) element.getValue();
				serviceName = service.getQName().getLocalPart();
				break;
			}
		} else {
			QName serviceQN = new QName(targetNameSpace, serviceName);
			service = definition.getService(serviceQN);
		}
		return service;
	}

	/**
	 * This method will return Port element from the service element.
	 * 
	 * @param service
	 * @return
	 */
	private Port getPort(Service service) {
		Port port = null;
		if (portName == null || portName.length() < 1) {
			Map portsMap = service.getPorts();
			Set set = portsMap.entrySet();
			for (Iterator iter = set.iterator(); iter.hasNext();) {
				Map.Entry element = (Map.Entry) iter.next();
				port = (Port) element.getValue();
				portName = port.getName();
				break;
			}
		} else {
			port = service.getPort(portName);
		}
		return port;
	}

	/**
	 * This method works on the Port element and returns the SOAP server URL
	 * 
	 * @param port
	 * @param soapServerQN
	 * @return
	 */
	private String getSOAPServerURI(Port port, QName soapServerQN) {
		String locationURI = "";
		ExtensibilityElement element = getExtensibilityElement(port,
				soapServerQN);
		if (element != null) {
			locationURI = ((SOAPAddress) element).getLocationURI();
		}
		return locationURI;
	}

	/**
	 * 
	 * @param binding
	 * @param soapBindingQN
	 * @return
	 */
	private String getWebServiceStyle(Binding binding, QName soapBindingQN) {
		String style = "";
		ExtensibilityElement element = getExtensibilityElement(binding,
				soapBindingQN);
		if (element != null) {
			style = ((SOAPBinding) element).getStyle();
		}
		return style;
	}

	/**
	 * 
	 * @param binding
	 * @param soapBindingQN
	 * @return
	 */
	private String getTransportURI(Binding binding, QName soapBindingQN) {
		String transportURI = "";
		ExtensibilityElement element = getExtensibilityElement(binding,
				soapBindingQN);
		if (element != null) {
			transportURI = ((SOAPBinding) element).getTransportURI();
		}
		return transportURI;
	}

	/**
	 * This method would return the SOAPActionURI which needs to be specified in
	 * the HTTP header.
	 * 
	 * @param bindingOperation
	 * @return the SOAP Action URI
	 */
	private String getSOAPActionURI(BindingOperation bindingOperation) {
		String soapActionURI = "";
		QName soapOperationQN = new QName(WsdlParser.SOAP_SCHEMA_URL,
				WsdlParser.SOAP_OPERATION_ELEMENT);
		ExtensibilityElement element = getExtensibilityElement(
				bindingOperation, soapOperationQN);
		if (element != null) {
			soapActionURI = ((SOAPOperation) element).getSoapActionURI();
		}
		return soapActionURI;
	}

	/**
	 * This method will identify the operation on the basis of its name and
	 * return it.
	 * 
	 * @param binding
	 * @return
	 */
	private BindingOperation getOperation(Binding binding) {
		BindingOperation operation = null;
		if (operationName != null && operationName.length() > 0
				&& inputMessageName != null && inputMessageName.length() > 0
				&& outputMessageName != null && outputMessageName.length() > 0) {
			operation = binding.getBindingOperation(operationName,
					inputMessageName, outputMessageName);
		} else if (operationName != null && operationName.length() > 0) {
			List oprList = binding.getBindingOperations();
			for (Iterator iter = oprList.iterator(); iter.hasNext();) {
				BindingOperation element = (BindingOperation) iter.next();
				if (element.getName().equalsIgnoreCase(operationName)) {
					operation = element;
					break;
				}
			}
		} else {
			List oprList = binding.getBindingOperations();
			for (Iterator iter = oprList.iterator(); iter.hasNext();) {
				operation = (BindingOperation) iter.next();
				operationName = operation.getName();
				break;
			}
		}
		return operation;
	}

	/**
	 * This method would return the messaging style of the Operation.
	 * 
	 * @param bindingOperation
	 * @return the SOAP Action URI
	 */
	private String getOprMessagingStyle(BindingOperation bindingOperation) {
		String messagingStyle = "";
		QName soapOperationQN = new QName(WsdlParser.SOAP_SCHEMA_URL,
				WsdlParser.SOAP_OPERATION_ELEMENT);
		ExtensibilityElement element = getExtensibilityElement(
				bindingOperation, soapOperationQN);
		if (element != null && element instanceof SOAPOperation) {
			messagingStyle = ((SOAPOperation) element).getStyle();
		}
		return messagingStyle;
	}

	/**
	 * If operationName, inputMessageName and outputMessageName are specified
	 * then it will return that operation If operationName is only specified the
	 * it will be returned. If none have been specified then it is assumed that
	 * there is only one operation in the port element, so the first operation
	 * is returned.
	 * 
	 * @param portType
	 * @param oprName
	 * @return instance of Operation element.
	 */
	private Operation getOperation(PortType portType) {
		Operation operation = null;
		if (operationName != null && operationName.length() > 0
				&& inputMessageName != null && inputMessageName.length() > 0
				&& outputMessageName != null && outputMessageName.length() > 0) {
			operation = portType.getOperation(operationName, inputMessageName,
					outputMessageName);
		} else if (operationName != null && operationName.length() > 0) {
			List oprList = portType.getOperations();
			for (Iterator iter = oprList.iterator(); iter.hasNext();) {
				Operation element = (Operation) iter.next();
				if (element.getName().equalsIgnoreCase(operationName)) {
					operation = element;
					break;
				}
			}
		} else {
			List oprList = portType.getOperations();
			for (Iterator iter = oprList.iterator(); iter.hasNext();) {
				operation = (Operation) iter.next();
				operationName = operation.getName();
				break;
			}
		}
		return operation;
	}	
	
	/**
     * 
     * @param element
     * @param qName
     * @return
     */
    private ExtensibilityElement getExtensibilityElement(
            Binding binding, QName qName) {        
        List extensibleElements = binding.getExtensibilityElements();
        return getExtensibilityElement(extensibleElements,qName);
    }
    
    /**
     * 
     * @param element
     * @param qName
     * @return
     */
    private ExtensibilityElement getExtensibilityElement(
            Port port, QName qName) {
        List extensibleElements = port.getExtensibilityElements();        
        return getExtensibilityElement(extensibleElements,qName);
    }
    
    /**
     * 
     * @param element
     * @param qName
     * @return
     */
    private ExtensibilityElement getExtensibilityElement(
            BindingOperation bindingOperation, QName qName) {
        List extensibleElements = bindingOperation.getExtensibilityElements();        
        return getExtensibilityElement(extensibleElements,qName);
    }
    
    /**
     * @param extensibleElements
     * @param qName
     * @return
     */
    private ExtensibilityElement getExtensibilityElement(List extensibleElements,QName qName){
        ExtensibilityElement toReturn = null;
        for (Iterator iter = extensibleElements.iterator(); iter.hasNext();) {
            ExtensibilityElement eachElement = (ExtensibilityElement) iter
                    .next();
            if (eachElement.getElementType().equals(qName)) {
                toReturn = eachElement;
                break;
            }
        }
        return toReturn;
    }

	/**
	 * 
	 * @param defintion
	 * @return
	 */
	private String getTargetNamespace(Definition defintion,
			BindingOperation bindingOperation, String operationMessagingStyle) {
		String targetNameSpace = defintion.getTargetNamespace();
		if (bindingOperation != null
				&& RPC_MESSAGING_STYLE
						.equalsIgnoreCase(operationMessagingStyle)) {
			BindingInput input = bindingOperation.getBindingInput();
			List extensibilityElements = input.getExtensibilityElements();
			for (Iterator iter = extensibilityElements.iterator(); iter
					.hasNext();) {
				Object element = iter.next();
				if (element instanceof javax.wsdl.extensions.soap.SOAPBody) {
					javax.wsdl.extensions.soap.SOAPBody soapBody = (javax.wsdl.extensions.soap.SOAPBody) element;
					String nameSpace = soapBody.getNamespaceURI();
					if (nameSpace != null && nameSpace.length() > 0) {
						targetNameSpace = nameSpace;
					}
				}
			}
		}
		return targetNameSpace;

	}

	/**
	 * @return Returns the serviceName.
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @return Returns the portName.
	 */
	public String getPortName() {
		return portName;
	}

	/**
	 * @return Returns the defaultMessagingStyle.
	 */
	public String getDefaultMessagingStyle() {
		return defaultMessagingStyle;
	}

	/**
	 * @return Returns the faultsMap.
	 */
	public Map getFaultsMap() {
		return faultsMap;
	}

	/**
	 * @return Returns the inputMessage.
	 */
	public Message getInputMessage() {
		return inputMessage;
	}

	/**
	 * @return Returns the operationName.
	 */
	public String getOperationName() {
		return operationName;
	}

	/**
	 * @return Returns the oprMessagingStyle.
	 */
	public String getOprMessagingStyle() {
		return oprMessagingStyle;
	}

	/**
	 * @return Returns the outputMessage.
	 */
	public Message getOutputMessage() {
		return outputMessage;
	}

	/**
	 * @return Returns the outputMessageName.
	 */
	public String getOutputMessageName() {
		return outputMessageName;
	}

	/**
	 * @return Returns the soapActionURI.
	 */
	public String getSoapActionURI() {
		return soapActionURI;
	}

	/**
	 * @return Returns the soapServerURI.
	 */
	public String getSoapServerURI() {
		return soapServerURI;
	}

	/**
	 * @return Returns the targetNameSpace.
	 */
	public String getTargetNameSpace() {
		return targetNameSpace;
	}

	/**
	 * @return Returns the wsdlFileUri.
	 */
	public String getWsdlFileUri() {
		return wsdlFileUri;
	}

}

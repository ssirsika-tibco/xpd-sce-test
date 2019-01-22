package com.tibco.bx.debug.core.invoke.datamodel;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingInput;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.Input;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Output;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.WSDLElement;
import org.eclipse.wst.wsdl.binding.soap.SOAPBinding;
import org.eclipse.wst.wsdl.binding.soap.SOAPBody;
import org.eclipse.wst.wsdl.binding.soap.SOAPHeader;
import org.eclipse.wst.wsdl.binding.soap.SOAPOperation;
import org.eclipse.xsd.XSDNamedComponent;

import com.tibco.bx.debug.core.invoke.constants.BindingTypes;
import com.tibco.bx.debug.core.ws.util.WsdlUtil;

public class LaunchWsdlOperationElement {
	
	public static final int OPERATION_TYPE_SOAP = 0;
	public static final int OPERATION_TYPE_HTTP_GET = 1;
	public static final int OPERATION_TYPE_HTTP_POST = 2;

	private int operationType;

	private boolean isDocumentStyle;
	private boolean isUseLiteral ;
	private String soapAction;

	//private String encodingStyle;
	//private String encodingNamespace;
	
	Operation operation;
	
	public String getSoapAction() {
		return soapAction;
	}

	private WSDLPartsToXSDTypeMapper wsdlPartsToXsdTypeMapper;
	
	LaunchWsdlBindingElement bindingElement;
	
	Definition definition;
	
	public LaunchWsdlOperationElement(Operation operation){
		this.operation = operation;
		initOperation();
	}

	private void gatherSoapInformation(
			LaunchWsdlBindingElement bindingElement,
			SOAPBinding soapBinding) {
		  // Initialize defaults.
	    isDocumentStyle = true;
	    soapAction = ""; //$NON-NLS-1$
	    isUseLiteral = true;
	   // encodingStyle = null;

	    if (soapBinding != null)
	      isDocumentStyle = "document".equals(soapBinding.getStyle()); //$NON-NLS-1$
	    BindingOperation bindingOperation = getBindingOperation(bindingElement);
	    SOAPOperation soapOperation = null;
	    
	    for (Iterator i = bindingOperation.getExtensibilityElements().iterator();i.hasNext();)
	    {
	      ExtensibilityElement e = (ExtensibilityElement)i.next();
	      if (e instanceof SOAPOperation)
	      {
	        soapOperation = (SOAPOperation)e;
	        soapAction = soapOperation.getSoapActionURI();
	        if(soapAction == null) soapAction = ""; //$NON-NLS-1$
	        
	        String style = soapOperation.getStyle();
	        if (style != null)
	          isDocumentStyle = style.equals("document"); //$NON-NLS-1$
	        break;
	      }
	    }

	    BindingInput bindingInput = (BindingInput) bindingOperation.getBindingInput();
	    SOAPBody soapBody = null;
	    for (Iterator i = bindingInput.getExtensibilityElements().iterator();i.hasNext();)
	    {
	      ExtensibilityElement e = (ExtensibilityElement)i.next();
	      if (e instanceof SOAPBody)
	      {
	        soapBody = (SOAPBody)e;
	        isUseLiteral = "literal".equals(soapBody.getUse()); //$NON-NLS-1$
	        if (!isUseLiteral)
	        {
	          // Encoded.
	          for (Iterator j = soapBody.getEncodingStyles().iterator();j.hasNext();)
	          {
	            /*encodingStyle = (String)j.next();
	            encodingNamespace = soapBody.getNamespaceURI();*/
	            break;
	          }
	        }
	        break;
	      }
	    }
	}

	
	public XSDNamedComponent getSchema(Part part){
		
		if (wsdlPartsToXsdTypeMapper == null) {
			wsdlPartsToXsdTypeMapper = new WSDLPartsToXSDTypeMapper();
			wsdlPartsToXsdTypeMapper.addSchemas(bindingElement.getWsdlElement().getSchemaList());
		}
		return wsdlPartsToXsdTypeMapper.getXSDTypeFromSchema(part);
		
	}
	
	public LaunchWsdlBindingElement getBindingElement() {
		return bindingElement;
	}

	private BindingOperation getBindingOperation(
			LaunchWsdlBindingElement bindingElement) {
		Binding binding = (Binding) bindingElement.getBinding();
		String operationInputName = null;
		String operationOutputName = null;
		Input operationInput = (Input) operation.getInput();
		Output operationOutput = (Output) operation.getOutput();
		if (operationInput != null)
			operationInputName = operationInput.getName();
		if (operationOutput != null)
			operationOutputName = operationOutput.getName();
		BindingOperation bindingOperation = (BindingOperation) binding.getBindingOperation(
				operation.getName(), operationInputName, operationOutputName);
		if (bindingOperation == null)
			bindingOperation = (BindingOperation) binding.getBindingOperation(
					operation.getName(), null, null);
		return bindingOperation;
	}

	public Definition getDefinition() {
		return definition;
	}
/*
	public String getEncodingStyle() {
		return encodingStyle;
	}

	public String getEncodingNamespace(){
		return encodingNamespace;
	}
	*/
	public void getInfoFromBinding(LaunchWsdlBindingElement bindingOperation) {
//		setDocumentation(operation.getDocumentationElement());

		operationType = bindingOperation.getBindingType();
		ExtensibilityElement bindingExtensibilityElement =  (ExtensibilityElement) bindingOperation.getBindingExtensibilityElement();
		switch (operationType) {
		case BindingTypes.SOAP:
			gatherSoapInformation(bindingOperation,(SOAPBinding)bindingExtensibilityElement);
		case BindingTypes.HTTP_GET:
		case BindingTypes.HTTP_POST:
		default:
			break;
		}
	}
	
	
	public Operation getOperation() {
		return operation;
	}

	private void initOperation() {

		if(operation != null){
			definition = WsdlUtil.getDefinition((WSDLElement) operation);
			BindingOperation bindingOperation = (BindingOperation) WsdlUtil.getBinding(operation,definition);
			if(bindingOperation != null){
				bindingElement = new LaunchWsdlBindingElement(bindingOperation);
				getInfoFromBinding(bindingElement);
			}
		}
	}

	public boolean isDocumentStyle() {
		return isDocumentStyle;
	}
	
	public boolean isUseLiteral() {
		return isUseLiteral;
	}
	
	public List getSOAPHeaders() {
		return getSOAPHeaders(true);
	}


	public List getSOAPHeaders(boolean isInput) {
		List headers = new Vector();

		BindingOperation bindingOperation = (BindingOperation) getBindingElement().getBindingOperation();
		List extensibilityElements = isInput ? bindingOperation
				.getBindingInput().getExtensibilityElements()
				: bindingOperation.getBindingOutput()
						.getExtensibilityElements();

		for (Iterator it = extensibilityElements.iterator(); it.hasNext();) {

			ExtensibilityElement e = (ExtensibilityElement) it.next();

			if (e instanceof SOAPHeader && !headers.contains(e))
				headers.add(e);
		}

		return headers;
	}

	@SuppressWarnings("unchecked")
	public List getOrderedBodyParts() {
		Input input = (Input) operation.getInput();
		Message message = (Message) input.getMessage();
		List parameters = operation.getParameterOrdering();
		List parts = new Vector(message.getOrderedParts(parameters));

		BindingOperation bindingOperation = (BindingOperation) getBindingElement().getBindingOperation();

		BindingInput bindingInput = (BindingInput) bindingOperation.getBindingInput();

		for (Iterator it = bindingInput.getExtensibilityElements().iterator(); it
				.hasNext();) {
			ExtensibilityElement e = (ExtensibilityElement) it.next();
			if (e instanceof SOAPBody) {
				SOAPBody soapBody = (SOAPBody) e;
				List bodyParts = soapBody.getParts();
				if (bodyParts != null) {
					for (int i = 0; i < parts.size(); i++) {
						Part part = (Part) parts.get(i);
						if (!bodyParts.contains(part)
								&& !bodyParts.contains(part.getName())) {
							parts.remove(i);
							i--;
						}
					}
				}
				break;
			}
		}
		return parts;
	}
	
	public String getName(){
		return getOperation().getName();
	}
}

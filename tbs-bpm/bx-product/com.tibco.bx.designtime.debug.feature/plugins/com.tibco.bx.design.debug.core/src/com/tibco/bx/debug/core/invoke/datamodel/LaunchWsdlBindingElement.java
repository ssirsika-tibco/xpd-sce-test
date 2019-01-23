package com.tibco.bx.debug.core.invoke.datamodel;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.WSDLElement;
import org.eclipse.wst.wsdl.binding.http.HTTPBinding;
import org.eclipse.wst.wsdl.binding.soap.SOAPBinding;

import com.tibco.bx.debug.core.invoke.constants.BindingTypes;
import com.tibco.bx.debug.core.ws.util.WsdlUtil;


public class LaunchWsdlBindingElement {

	private int bindingType;
	
	private Binding binding;
	
	private BindingOperation bindingOperation;
	
	private ExtensibilityElement bindingExtensibilityElement;
	
	private LaunchWsdlElement wsdlElement;

	public LaunchWsdlBindingElement(BindingOperation bindingOper) {
		
		bindingOperation = bindingOper;
		setBinding((Binding) ((EObject)bindingOper).eContainer());
		wsdlElement = new LaunchWsdlElement(WsdlUtil.getDefinition((WSDLElement) bindingOperation));
		wsdlElement.buildModel();
	}

	public LaunchWsdlElement getWsdlElement() {
		return wsdlElement;
	}
	
	public Binding getBinding() {
		return binding;
	}

	public ExtensibilityElement getBindingExtensibilityElement() {
		return bindingExtensibilityElement;
	}

	public int getBindingType() {
		return bindingType;
	}

	private void setBinding(Binding binding) {
		this.binding = binding;
		// setDocumentation(binding.getDocumentationElement());
		setBindingExtensibilityElement();
	}

	public BindingOperation getBindingOperation() {
		return bindingOperation;
	}

	private final void setBindingExtensibilityElement() {
		bindingExtensibilityElement = null;
		bindingType = BindingTypes.UNSUPPORTED;
		List extensibilityElements = binding.getExtensibilityElements();
		for (Iterator i = extensibilityElements.iterator(); i.hasNext();) {
			ExtensibilityElement e = (ExtensibilityElement) i.next();
			if (e instanceof HTTPBinding) {
				HTTPBinding binding = (HTTPBinding) e;
				String verb = binding.getVerb();
				if (BindingTypes.HTTP_VERB_GET.equals(verb))
					bindingType = BindingTypes.HTTP_GET;
				else if (BindingTypes.HTTP_VERB_POST.equals(verb))
					bindingType = BindingTypes.HTTP_POST;
			} else if (e instanceof SOAPBinding)
				bindingType = BindingTypes.SOAP;
			if (bindingType != BindingTypes.UNSUPPORTED) {
				bindingExtensibilityElement = e;
				break;
			}
		}
	}
}

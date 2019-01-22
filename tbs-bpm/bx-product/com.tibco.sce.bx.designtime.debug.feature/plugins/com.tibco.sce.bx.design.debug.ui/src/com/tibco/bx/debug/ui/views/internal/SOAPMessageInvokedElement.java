package com.tibco.bx.debug.ui.views.internal;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.invoke.datamodel.ISOAPMessage;
import com.tibco.bx.emulation.core.common.IInOutElement;

public class SOAPMessageInvokedElement {

	private EObject startActivity;
	private IInOutElement inoutElement;
	private ISOAPMessage soapMessage;
	private String url;
	
	public SOAPMessageInvokedElement(EObject startActivity, IInOutElement inoutElement, ISOAPMessage soapMessage, String url){
		this.startActivity = startActivity;
		this.inoutElement = inoutElement;
		this.soapMessage = soapMessage;
		this.url = url;
	}

	public EObject getStartActivity() {
		return startActivity;
	}

	public IInOutElement getInoutElement() {
		return inoutElement;
	}

	public ISOAPMessage getSoapMessage() {
		return soapMessage;
	}

	public String getUrl() {
		return url;
	}
	
}

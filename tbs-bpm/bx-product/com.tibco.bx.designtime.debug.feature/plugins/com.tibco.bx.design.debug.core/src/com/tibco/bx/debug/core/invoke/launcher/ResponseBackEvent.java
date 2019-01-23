package com.tibco.bx.debug.core.invoke.launcher;

import javax.xml.soap.SOAPFault;

import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.Element;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.ws.util.XMLUtils;

public class ResponseBackEvent {
	
	//private String response;
	private SOAPFault fault;
	private EObject startActivity;
	
	private Element responseElement;
	

	public ResponseBackEvent(String response, EObject startActivity) {
		super();
		//this.response = response;
		this.startActivity = startActivity;
		responseElement = buildDocument(response);
	}
	
	private Element buildDocument(String responseXml){
		  org.w3c.dom.Element requestDoc = null;
		  
		try {
			requestDoc = XMLUtils.byteArrayToElement(responseXml.getBytes(XMLUtils.UTF8_ENCODING),false);
			
		//TODO handle exception
		} catch (Exception e) {
			DebugCoreActivator.log(e);
		} 
		return requestDoc;
	}
	public String toXml(){
		String xmlString = null;
		if(responseElement != null){
			xmlString = XMLUtils.serialize(responseElement,true);
		}
		return xmlString == null ? "" : xmlString; //$NON-NLS-1$
	}
	
	
	public boolean hasError(){
		return fault != null;
	}
	
}

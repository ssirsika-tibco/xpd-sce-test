package com.tibco.bx.debug.core.invoke.datamodel;

import java.util.Map;
import org.w3c.dom.Element;

public interface ISOAPMessage {
	
	//final static String PROP_SOAP_ACTION = "prop_soap_action";
	final static int ENVELOPE = 0;
	final static int HEADER_CONTENT = 1;
	final static int BODY_CONTENT = 2;
	
	public LaunchWsdlOperationElement getOperation() ;
	
	public void setProperty(String key, Object value);
	
	public Object getProperty(String key);
	
	public void setNamespaceTable(Map<String, String> namespaceTable);
	
	public Map<String, String> getNamespaceTable();
	
	public void setEnvelope(Element envelope);
	
	public Element getEnvelope(boolean deep);
	
	public void setHeader(Element header);
	
	public Element getHeader(boolean deep);
	
	public void setHeaderContent(Element[] headerContent);
	
	public Element[] getHeaderContent();

	public Element getBody(boolean deep);
	
	public void setBody(Element body);
	
	public void setBodyContent(Element[] bodyContent);
	
	public Element[] getBodyContent();
	
	public void setFault(Element fault);
	
	public Element getFault();
	
	public String toXML();
	
	
	
}

package com.tibco.bx.debug.core.invoke.datamodel;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.bx.debug.core.invoke.transport.ISerializer;

public class SOAPMessage implements ISOAPMessage {
	
	private ISerializer serializer;
	
	private LaunchWsdlOperationElement operation;
	
	private Map properties = new Hashtable();	
	
	private Element envelope ;
	private Element header ;
	private Element[] headerContent ;
	private Element body ;
	private Element[] bodyContent ;	
	private Element fault;
		
	private String xml ;

	public SOAPMessage(LaunchWsdlOperationElement operation,ISerializer serializer) {
		super();
		this.operation = operation;
		this.serializer = serializer;
	}
	
	@Override
	public LaunchWsdlOperationElement getOperation() {
		return operation;
	}

	@Override
	public Element getBody(boolean deep) {
		if (!deep)
			return body;
		synchronized (this) {
			if (body == null)
				return null;
			// copy the rpc wrapper element as well, if any
			Element clonedBody = (Element) body.cloneNode(true);		
			
			if (fault != null)
				appendNode(clonedBody, fault);
			else {
				Element target = clonedBody;
				
				// check for rpc wrapper
				if (clonedBody.getFirstChild() != null)
					target = (Element) clonedBody.getFirstChild();
				
				appendChildren(target, bodyContent);
			}
			return clonedBody;		
		}
	}

	@Override
	public Element[] getBodyContent() {
		return bodyContent;
	}

	@Override
	public Element getEnvelope(boolean deep) {
		if (!deep)
			return envelope;
		//TODO for deep-copy, headerContent and body should be copied deeply
		synchronized (this) {
			if (envelope == null)
				return null;
			
			Element clonedEnvelope = (Element) envelope.cloneNode(false);
			
			if (headerContent != null && headerContent.length > 0)
				appendNode(clonedEnvelope, getHeader(true));
				
			    appendNode(clonedEnvelope, getBody(true));
			
			return clonedEnvelope;	
		}
	}

	private void appendNode(Node parent, Node child) {
		if (parent == null || child == null)
			return;
		
		Document owner = parent.getOwnerDocument();
		
		if (!owner.equals(child.getOwnerDocument()))
			child = owner.importNode(child, true);
		
		parent.appendChild(child);
	}
	
	@Override
	public Element getFault() {
		return null;
	}

	@Override
	public Element getHeader(boolean deep) {
		if (!deep)
			return header;
		
		synchronized (this) {
			if (header == null)
				return null;
			
			Element clonedHeader = (Element) header.cloneNode(false);
			appendChildren(clonedHeader, headerContent);				
			return clonedHeader;
		}			
	}

	private void appendChildren(Element parent, Node[] children) {
		if (parent == null || children == null)
			return;
		
		for (int i = 0; i < children.length; i++) {
			if (children[i] == null)
				continue;
			appendNode(parent, children[i]);
		}
	}		

	@Override
	public Element[] getHeaderContent() {
		return headerContent;
	}

	@Override
	public Map getNamespaceTable() {
		Hashtable namespaceTable = new Hashtable();
		if (envelope != null) {
			NamedNodeMap attributes = envelope.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				Attr attribute = (Attr) attributes.item(i);
				String prefix = getNSPrefix(attribute);
				if (prefix != null)
					namespaceTable.put(attribute.getValue(), prefix);
			}			
		}		
		return namespaceTable;
		
	}
	
	private String getNSPrefix(Attr attribute) {
		String name = attribute.getName();
		if (name.startsWith("xmlns:"))	//$NON-NLS-1$
			return name.substring(6);
		return null;
	}

	@Override
	public Object getProperty(String key) {
		return properties.get(key);
	}

	@Override
	public void setProperty(String key, Object value) {
		properties.put(key, value);
	}
	
	public synchronized void setBodyContent(Element[] bodyContent) {
		this.bodyContent = bodyContent;
		if (bodyContent != null)
			fault = null;
		xml = null;
	}
	
	public synchronized void setBody(Element body) {		
		if (body == null)
			this.body = null;
		else {
			this.body = (Element) body.cloneNode(false);
			
			if (!operation.isDocumentStyle() && fault == null) {
				NodeList childElements = body.getElementsByTagName("*");//$NON-NLS-1$
				if (childElements.getLength() > 0)
					this.body.appendChild(childElements.item(0).cloneNode(false));
			}
		}		
		xml = null;
	}
	

	@Override
	public void setEnvelope(Element envelope) {
		this.envelope  = envelope != null ? (Element)envelope.cloneNode(false) : null;
		xml = null;
	}

	@Override
	public void setFault(Element fault) {
		this.fault = fault;
	}

	@Override
	public void setHeader(Element header) {
	    if (header == null)
            this.header = null;         
        else
            this.header = (Element) header.cloneNode(false);
        xml = null;
	}

	@Override
	public void setHeaderContent(Element[] headerContent) {
	    this.headerContent = headerContent;
        xml = null;
	}

	@Override
	public void setNamespaceTable(Map namespaceTable) {
		if (namespaceTable == null || envelope == null)
			return;
	
		Iterator iter = namespaceTable.entrySet().iterator();
		
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String name = "xmlns:" + entry.getValue();		//$NON-NLS-1$	
			envelope.setAttribute(name, entry.getKey().toString());			
		}
		xml = null;

	}

	@Override
	public String toXML() {
		if (xml == null)
			xml = serializer.serialize(ENVELOPE, this);
		return xml;		
	}

	

}

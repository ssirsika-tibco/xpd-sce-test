package com.tibco.bx.xpdl2bpel.extensions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.eclipse.uml2.uml.Type;

public class ExtensionVariable {
	
	private String name;
	private Type type;
	private boolean isArray;
	private Map<QName, String> extensionAttributes;
	
	public ExtensionVariable(String name, Type type, boolean isArray) {
		this.name = name;
		this.type = type;
		this.isArray = isArray;
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType() {
		return type;
	}
	
	public boolean isArray() {
		return isArray;
	}
	
	public void setExtensionAttribute(QName attrName, String attrValue) {
		if (extensionAttributes == null) {
			extensionAttributes = new HashMap<QName, String>();
		}
		extensionAttributes.put(attrName, attrValue);
	}
	
	public Map<QName, String> getExtensionAttributes() {
		if (extensionAttributes == null) {
			return null;
		}
		return Collections.unmodifiableMap(extensionAttributes);
	}
	
}

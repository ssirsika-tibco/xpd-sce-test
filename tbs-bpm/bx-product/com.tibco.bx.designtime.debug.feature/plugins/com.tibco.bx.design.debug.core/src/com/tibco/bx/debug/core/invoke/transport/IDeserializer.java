package com.tibco.bx.debug.core.invoke.transport;

import com.tibco.bx.debug.core.invoke.datamodel.ISOAPMessage;


public interface IDeserializer {

	public void deserialize(int part, String xml, ISOAPMessage message) ;
	
}

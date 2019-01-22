package com.tibco.bx.debug.core.invoke.transport;

import com.tibco.bx.debug.core.invoke.datamodel.ISOAPMessage;


public interface ISerializer {

	public String serialize(int part, ISOAPMessage message);
}

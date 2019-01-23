package com.tibco.bx.debug.http.runtime;


public class HTTPSConnectionFactory extends HTTPConnectionFactory {

	@Override
	public String getConnectionType() {
		return "https"; //$NON-NLS-1$
	}

}

package com.tibco.bx.debug.http.runtime;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.launching.IBxLaunchAttribute;
import com.tibco.bx.debug.core.runtime.IConnection;
import com.tibco.bx.debug.core.runtime.IConnectionFactory;
import com.tibco.bx.debug.http.HTTPActivator;

public class HTTPConnectionFactory implements IConnectionFactory {

	@Override
	public IConnection createConnection(Map<String, Object> connectionParameters) throws CoreException {
		final String hostName = (String) connectionParameters.get(IBxLaunchAttribute.ENGINE_ADDRESS);
		if (hostName == null || hostName.length() == 0) {
			throw new CoreException(HTTPActivator.newStatus(null, Messages.getString("BPELLaunchDelegate.errorNoHost"))); //$NON-NLS-1$
		}
		String port = (String) connectionParameters.get(IBxLaunchAttribute.ENGINE_PORT);
		String modelType = (String) connectionParameters.get(IBxLaunchAttribute.MODEL_TYPE);
		String endpointURI = (String) connectionParameters.get(IBxLaunchAttribute.ENDPOINT_URI);
		String username = (String) connectionParameters.get(IBxLaunchAttribute.USER_NAME);
		String password = (String) connectionParameters.get(IBxLaunchAttribute.PASSWORD);
		String soapVersion = (String) connectionParameters.get(IBxLaunchAttribute.SOAP_VERSION);

		IConnection connection = new HTTPConnection(getConnectionType(), hostName, new Integer(port).intValue(), modelType, endpointURI, username,
				password, soapVersion);
		return connection;
	}

	@Override
	public String getConnectionType() {
		return "http"; //$NON-NLS-1$
	}

}

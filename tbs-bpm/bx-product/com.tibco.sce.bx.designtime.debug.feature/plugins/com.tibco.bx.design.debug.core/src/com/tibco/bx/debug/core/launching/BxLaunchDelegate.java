package com.tibco.bx.debug.core.launching;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.runtime.IConnection;
import com.tibco.bx.debug.core.runtime.IConnectionFactory;
import com.tibco.bx.debug.core.runtime.events.IBxRuntimeEventFactory;
import com.tibco.bx.debug.core.runtime.internal.ConnectionFactoryManager;
import com.tibco.bx.debug.core.runtime.internal.RuntimeEventFactoryManager;

/**
 * Launch in debug model
 */
public class BxLaunchDelegate extends LaunchConfigurationDelegate {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.
	 * eclipse.debug.core.ILaunchConfiguration, java.lang.String,
	 * org.eclipse.debug.core.ILaunch,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		int engineNum = configuration.getAttribute(IBxLaunchAttribute.ENGINE_SIZE, new Integer(0));
		if (engineNum <= 0) {
			abort("No engine exists, please check. Configuration must specify at least one engine.", null); //$NON-NLS-1$
		}
		for (int i = 0; i < engineNum; i++) {
			String connectionType = configuration.getAttribute(IBxLaunchAttribute.CONNECTIONFACTORY_TYPE + i, ""); //$NON-NLS-1$
			//			String modelType = configuration.getAttribute(IBxLaunchAttribute.MODEL_TYPE + i, ""); //$NON-NLS-1$
			if (connectionType == null || "".equals(connectionType)) { //$NON-NLS-1$
				continue;
			}
			IConnectionFactory connectionFactory = ConnectionFactoryManager.getConnectionFactory(connectionType);
			if (connectionFactory == null) {
				abort("Invalid connection factory: " + connectionType, null); //$NON-NLS-1$
			}

			IBxRuntimeEventFactory runtimeEventFactory = RuntimeEventFactoryManager.getRuntimeEventFactory(connectionType);
			if (runtimeEventFactory == null) {
				abort("Invalid runtime event factory: " + connectionType, null); //$NON-NLS-1$
			}

			monitor.beginTask(Messages.getString("BxLaunchDelegate.connectingToServer"), 18); //$NON-NLS-1$
			IConnection connection = null;
			try {
				Map<String, Object> connectionParameters = getConnectionParameters(configuration, i);
				connection = connectionFactory.createConnection(connectionParameters);
				BxDebugTarget debugTarget = new BxDebugTarget(launch, connection, runtimeEventFactory);
				launch.addDebugTarget(debugTarget);
			} catch (Exception e) {
				if (connection != null) {
					connection.disconnect();
				}
				abort(Messages.getString("BxLaunchDelegate.cannotConnect"), e); //$NON-NLS-1$
			}
		}
		monitor.done();
	}

	/**
	 * Throws an exception with a new status containing the given message and
	 * optional exception.
	 * 
	 * @param message
	 *            error message
	 * @param e
	 *            underlying exception
	 * @throws CoreException
	 */
	private void abort(final String message, Throwable e) throws CoreException {
		throw new CoreException(DebugCoreActivator.newStatus(e, message));
	}

	private Map<String, Object> getConnectionParameters(ILaunchConfiguration configuration, int index) {
		Map<String, Object> connectionParameters = new HashMap<String, Object>();
		try {
			String modelType = configuration.getAttribute(IBxLaunchAttribute.MODEL_TYPE + index, ""); //$NON-NLS-1$
			String hostName = configuration.getAttribute(IBxLaunchAttribute.ENGINE_ADDRESS + index, ""); //$NON-NLS-1$
			String port = configuration.getAttribute(IBxLaunchAttribute.ENGINE_PORT + index, ""); //$NON-NLS-1$
			String endpointURI = configuration.getAttribute(IBxLaunchAttribute.ENDPOINT_URI + index, ""); //$NON-NLS-1$
			String username = configuration.getAttribute(IBxLaunchAttribute.USER_NAME + index, ""); //$NON-NLS-1$
			String password = configuration.getAttribute(IBxLaunchAttribute.PASSWORD + index, ""); //$NON-NLS-1$
			String soapVersion = configuration.getAttribute(IBxLaunchAttribute.SOAP_VERSION + index, ""); //$NON-NLS-1$
			connectionParameters.put(IBxLaunchAttribute.MODEL_TYPE, modelType);
			connectionParameters.put(IBxLaunchAttribute.ENGINE_ADDRESS, hostName);
			connectionParameters.put(IBxLaunchAttribute.ENGINE_PORT, port);
			connectionParameters.put(IBxLaunchAttribute.ENDPOINT_URI, endpointURI);
			connectionParameters.put(IBxLaunchAttribute.USER_NAME, username);
			connectionParameters.put(IBxLaunchAttribute.PASSWORD, password);
			connectionParameters.put(IBxLaunchAttribute.SOAP_VERSION, soapVersion);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return connectionParameters;
	}

}
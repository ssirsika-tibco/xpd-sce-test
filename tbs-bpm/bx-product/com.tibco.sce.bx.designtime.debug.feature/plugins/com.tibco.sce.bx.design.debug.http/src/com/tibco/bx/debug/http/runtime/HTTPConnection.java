package com.tibco.bx.debug.http.runtime;

import org.apache.axis.utils.ClassUtils;
import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.core.runtime.AbstractBxConnection;
import com.tibco.bx.debug.core.runtime.IBxDebugger;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.runtime.IBxTester;
import com.tibco.bx.debug.core.util.NetUtil;
import com.tibco.bx.debug.http.HTTPActivator;
import com.tibco.n2.pfe.services.pageflow.PageFlowManagement;
import com.tibco.n2.pfe.services.pageflow.PageFlowManagerServiceLocator;
import com.tibco.www.bx._2009.management.process.ProcessManagement;
import com.tibco.www.bx._2009.management.process.ProcessManagerServiceLocator;
import com.tibco.www.bx._2010.debugging.debugger.Debugger;
import com.tibco.www.bx._2010.debugging.debugger.DebuggerServiceLocator;
import com.tibco.www.bx._2010.debugging.debugger.Tester;
import com.tibco.www.bx._2010.debugging.debuggerType.Notification;

public class HTTPConnection extends AbstractBxConnection {

	private HTTPDebugger httpDebugger;
	private String username;
	private String password;
	private String soapVersion;
	private IBxProcessListing processListing;
	private DebuggerServiceLocator debuggerServiceLocator;

	private HTTPNotificationListener httpNotificationListener = new HTTPNotificationListener() {
		@Override
		public void handleNotification(Notification notification) {
			HTTPConnection.this.notifyEvent(notification);
		}
	};

	public HTTPConnection(String protocol, String host, int port, String modelType, String endpointURI) {
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.modelType = modelType;
		this.endpointURI = endpointURI;
		fixAxisIssue();
	}

	public HTTPConnection(String protocol, String hostName, int intValue, String modelType, String endpointURI, String username, String password,
			String soapVersion) {
		this(protocol, hostName, intValue, modelType, endpointURI);
		this.username = username;
		this.password = password;
		this.soapVersion = soapVersion;
	}

	private void fixAxisIssue() {
		ClassUtils.setClassLoader("org.apache.axis.attachments.AttachmentsImpl", getClass().getClassLoader()); //$NON-NLS-1$
		ClassUtils.setClassLoader("org.apache.axis.transport.http.HTTPSender", getClass().getClassLoader()); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return debuggerServiceLocator.getDebuggerPortAddress();
	}

	@Override
	public boolean isValid() throws CoreException {
		DebuggerServiceLocator locator = new DebuggerServiceLocator(username, password, soapVersion);
		locator.setDebuggerPortAddress(protocol, host, port, endpointURI);
		try {
			return NetUtil.validateURLViaNet(locator.getDebuggerPortAddress());
		} catch (Exception e) {
			throw (CoreException) new CoreException(HTTPActivator.newStatus(e, e.getMessage())).initCause(e);
		}
	}

	@Override
	protected void doConnect() throws CoreException {
		try {
			debuggerServiceLocator = new DebuggerServiceLocator(username, password, soapVersion);
			debuggerServiceLocator.setDebuggerPortAddress(protocol, host, port, endpointURI);

			ProcessManagerServiceLocator managerServiceLocator = new ProcessManagerServiceLocator(username, password, soapVersion);
			managerServiceLocator.setProcessManagerPortAddress(protocol, host, port);

			PageFlowManagerServiceLocator pfManagerServiceLocator = new PageFlowManagerServiceLocator(username, password, soapVersion);
			pfManagerServiceLocator.setPageFlowManagerPortAddress(protocol, host, port);

			Debugger debugger = debuggerServiceLocator.getDebugger();
			Tester tester = debuggerServiceLocator.getTester();

			if (!NetUtil.validateURLViaNet(debuggerServiceLocator.getTesterPortAddress())) {
				tester = null;
			}

			ProcessManagement management = managerServiceLocator.getProcessManagement();
			if (!NetUtil.validateURLViaNet(managerServiceLocator.getProcessManagerPortAddress())) {
				management = null;
			}

			PageFlowManagement pfManagement = pfManagerServiceLocator.getPageFlowManagement();
			if (!NetUtil.validateURLViaNet(pfManagerServiceLocator.getPageFlowManagementAddress())) {
				pfManagement = null;
			}

			processListing = new HTTPProcessListing(debugger, management, pfManagement, this);
			httpDebugger = new HTTPDebugger(this, debugger, tester);
			httpDebugger.addNotificationListener(httpNotificationListener);
			sessionId = httpDebugger.connect(getModelType());
		} catch (Throwable e) {
			if (e instanceof CoreException) {
				throw (CoreException) e;
			} else {
				throw (CoreException) new CoreException(HTTPActivator.newStatus(e, e.getMessage())).initCause(e);
			}
		}
	}

	@Override
	protected void doDisconnect() throws CoreException {
		httpDebugger.disconnect();
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (connected && adapter == IBxDebugger.class || adapter == IBxTester.class) {
			return httpDebugger;
		}
		if (connected && adapter == IBxProcessListing.class) {
			return processListing;
		}

		return null;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String getSoapVersion() {
		return soapVersion;
	}

}

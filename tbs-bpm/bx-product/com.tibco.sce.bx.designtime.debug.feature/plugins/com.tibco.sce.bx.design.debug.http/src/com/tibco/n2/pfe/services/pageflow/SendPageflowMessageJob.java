package com.tibco.n2.pfe.services.pageflow;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.methods.PostMethod;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.runtime.IConnection;
import com.tibco.bx.debug.http.util.HTTPDataUtil;
import com.tibco.bx.debug.operation.ILauncherEventHandler;
import com.tibco.n2.pfe.services.pageflowType.DataModel;
import com.tibco.n2.pfe.services.pageflowType.DataPayload;
import com.tibco.n2.pfe.services.pageflowType.InternalServiceFaultException;
import com.tibco.n2.pfe.services.pageflowType.InvalidArgumentFault;
import com.tibco.n2.pfe.services.pageflowType.PFETemplate;
import com.tibco.n2.pfe.services.pageflowType.PageFlowExecutionFault;
import com.tibco.n2.pfe.services.pageflowType.PageResponse;
import com.tibco.n2.pfe.services.pageflowType.PayloadModeType;

public class SendPageflowMessageJob extends Job {

	protected Thread sendPageflowThread;
	protected IStatus resultStatus = Status.OK_STATUS;
	protected boolean isFinished = false;

	private PostMethod post;
	private ILauncherEventHandler launcherEventHandler;

	protected ProcessTemplate processTemplate;
	protected Map<String, String> parametersMap;
	protected ILauncherEventHandler handler;

	protected PageResponse response;
	protected PageFlowManagement pfManager;
	protected IConnection connection;

	public SendPageflowMessageJob(String name, ProcessTemplate processTemplate, Map<String, String> parametersMap, ILauncherEventHandler handler,
			PageFlowManagement pfManager, IConnection connection) {
		super(name);
		this.processTemplate = processTemplate;
		this.parametersMap = parametersMap;
		this.handler = handler;
		this.pfManager = pfManager;
		this.connection = connection;
		post = new PostMethod(getPageflowServiceURL(connection));
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			while (!isFinished) {
				if (sendPageflowThread == null) {
					sendPageflowThread = new Thread() {
						@Override
						public void run() {
							try {
								PFETemplate pft = HTTPDataUtil.createPFETemplate(processTemplate);
								DataModel dataModel = HTTPDataUtil.createDataModel(processTemplate, parametersMap, handler, getConnection());
								DataPayload dataPayload = HTTPDataUtil.createDataPayload(dataModel);
								response = pfManager.startPageFlow(pft, dataPayload, PayloadModeType.XML, null, handler);
							} catch (IOException e) {
								resultStatus = new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, e.getMessage(), e);
								return;
							} catch (NumberFormatException e) {
								resultStatus = new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, e.getMessage(), e);
								return;
							} catch (InternalServiceFaultException e) {
								resultStatus = new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, e.getMessage(), e);
								return;
							} catch (InvalidArgumentFault e) {
								resultStatus = new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, e.getMessage(), e);
								return;
							} catch (PageFlowExecutionFault e) {
								resultStatus = new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, e.getMessage(), e);
								return;
							} finally {
								isFinished = true;
							}
						}

					};
					sendPageflowThread.start();
				} else if (monitor.isCanceled()) {
					post.abort();
				} else {
					if (getLauncherEventHandler() != null) {
						isFinished = isFinished || getLauncherEventHandler().isFinished();
						if (isFinished) {
							if (sendPageflowThread.isAlive()) {
								sendPageflowThread = null;
							}
						}
					}
				}
			}
		} finally {
			post.releaseConnection();
			monitor.done();
		}
		return resultStatus;
	}

	public ILauncherEventHandler getLauncherEventHandler() {
		return launcherEventHandler;
	}

	public void setLauncherEventHandler(ILauncherEventHandler launcherEventHandler) {
		this.launcherEventHandler = launcherEventHandler;
	}

	public PageResponse getResponse() {
		return response;
	}

	public IConnection getConnection() {
		return connection;
	}

	private String getPageflowServiceURL(IConnection connection) {
		String username = connection.getUsername();
		String password = connection.getPassword();
		String protocol = connection.getProtocol();
		String host = connection.getHost();
		int port = connection.getPort();
		String soapVersion = connection.getSoapVersion();
		PageFlowManagerServiceLocator pfManagerServiceLocator = new PageFlowManagerServiceLocator(username, password, soapVersion);
		pfManagerServiceLocator.setPageFlowManagerPortAddress(protocol, host, port);
		return pfManagerServiceLocator.getPageFlowPortAddress();
	}

}

package com.tibco.bx.debug.core.invoke.transport;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis.AxisFault;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.invoke.datamodel.ISOAPMessage;
import com.tibco.bx.debug.core.util.URLUtils;
import com.tibco.bx.debug.operation.ILauncherEventHandler;

public class SendSOAPMessageJob extends Job {

	private final String HTTP_METHOD = "POST";//$NON-NLS-1$
	private final String HTTP_CONNECT = "CONNECT";//$NON-NLS-1$
	private final String HTTP_VERSION_1_0 = "HTTP/1.0";//$NON-NLS-1$
	private final String HTTP_VERSION = "HTTP/1.1";//$NON-NLS-1$
	private final String HTTP_HEADER_SOAP_ACTION = "SOAPAction";//$NON-NLS-1$
	private final String HTTP_HEADER_HOST = "Host";//$NON-NLS-1$
	private final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";//$NON-NLS-1$
	private final String HTTP_HEADER_CONTENT_TYPE_VALUE = "text/xml;charset=UTF-8";//$NON-NLS-1$
	public static final String HTTP_HEADER_CONTENT_LENGTH = "Content-Length";//$NON-NLS-1$
	private final String HTTP_HEADER_ACCEPT = "Accept";//$NON-NLS-1$
	private final String ACCEPT_VALUE = "application/soap+xml, application/dime, multipart/related, text/*";//$NON-NLS-1$
	private final String HTTP_HEADER_USER_AGENT = "User-Agent";//$NON-NLS-1$
	private final String HTTP_HEADER_USER_AGENT_VALUE = "HttpClient";//$NON-NLS-1$
	private final String DEFAULT_CODE = "UTF-8";//$NON-NLS-1$

	private final static int OK_HTTP_STATUS = 200;
	private final static int CREATE_HTTP_STATUS = 202;
	private final static int NOCONTENT_HTTP_STATUS = 204;
	private final static int INTERNAL_SERVER_ERROR_HTTP_STATUS = 500;

	// private transient ListenerList listeners = null;

	private PostMethod post;

	private HttpClient client = new HttpClient();

	private byte[] response;

	private ILauncherEventHandler launcherEventHandler;

	IStatus resultStatus = Status.OK_STATUS;
	private int status = -1;
	private Thread sendSOAPThread;
	boolean isFinished = false;

	public SendSOAPMessageJob(String name, String endPointUrl, ISOAPMessage message) {
		super(name);
		post = buildPostMethod(endPointUrl, message.toXML(), message.getOperation().getName());
		// listeners = new ListenerList();
	}

	public SendSOAPMessageJob(String name, String endPointUrl, String soapMessage, String operationName) {
		super(name);
		post = buildPostMethod(endPointUrl, soapMessage, operationName);
		// listeners = new ListenerList();
	}

	private PostMethod buildPostMethod(String endPointUrl, String message, String operationName) {

		PostMethod postMethod = new PostMethod(endPointUrl);
		try {
			byte[] requestBody = message.getBytes(DEFAULT_CODE);
			// host
			postMethod.setRequestHeader(HTTP_HEADER_HOST, getHost(endPointUrl));
			// ContentType
			postMethod.setRequestHeader(HTTP_HEADER_CONTENT_TYPE, HTTP_HEADER_CONTENT_TYPE_VALUE);
			//
			postMethod.setRequestHeader(HTTP_HEADER_ACCEPT, ACCEPT_VALUE);
			// user agent
			postMethod.setRequestHeader(HTTP_HEADER_USER_AGENT, HTTP_HEADER_USER_AGENT_VALUE);
			// soap action
			postMethod.setRequestHeader(HTTP_HEADER_SOAP_ACTION, operationName); // message.getOperation().getName());

			postMethod.setRequestHeader(HTTP_HEADER_CONTENT_LENGTH, String.valueOf(requestBody.length));
			postMethod.setRequestEntity(new ByteArrayRequestEntity(requestBody));

		} catch (Exception e) {
			DebugCoreActivator.log(e);
		}

		return postMethod;
	}

	private String getHost(String endPointUrl) throws Exception {
		if (URLUtils.isURL(endPointUrl)) {
			URI uri = new URI(endPointUrl);
			return uri.getHost();
		} else {
			throw new Exception(Messages.getString("SendSOAPMessageJob.exception.message")); //$NON-NLS-1$
		}

	}

	public String getResponse() throws CoreException {
		String responseStr = null;
		try {
			responseStr = new String(response == null ? new byte[0] : response, DEFAULT_CODE);
		} catch (UnsupportedEncodingException e) {
			throw new CoreException(new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, IStatus.ERROR, "", e));//$NON-NLS-1$
		}
		return responseStr;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			while (!isFinished) {
				if (sendSOAPThread == null) {
					sendSOAPThread = new Thread() {
						@Override
						public void run() {
							try {
								status = client.executeMethod(post);
								response = post.getResponseBody();
								switch (status) {
								case OK_HTTP_STATUS:
								case CREATE_HTTP_STATUS:
									resultStatus = Status.OK_STATUS;
									return;
								case INTERNAL_SERVER_ERROR_HTTP_STATUS:
									AxisFault fault = buildFault(getResponse());
									resultStatus = new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, fault
											.getMessage(), fault);
								}
							} catch (HttpException e) {
								resultStatus = new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, e.getMessage(),
										e);
								return;
							} catch (IOException e) {
								resultStatus = new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, e.getMessage(),
										e);
								return;
							} catch (CoreException e) {
								resultStatus = new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, e.getMessage(),
										e);
								return;
							} finally {
								isFinished = true;
							}
						}

					};
					sendSOAPThread.start();
				} else if (monitor.isCanceled()) {
					post.abort();
				} else {
					if (getLauncherEventHandler() != null) {
						isFinished = isFinished || getLauncherEventHandler().isFinished();
						if (isFinished) {
							if (sendSOAPThread.isAlive()) {
								sendSOAPThread = null;
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

	private AxisFault buildFault(String response) {
		AxisFault fault = new AxisFault();
		Pattern codeExpress = Pattern
				.compile(
						"<[-\\w]+:Code>\\s+<[-\\w]+:Value>(.*)</[-\\w]+:Value>\\s+</[-\\w]+:Code>", Pattern.DOTALL | Pattern.MULTILINE);//$NON-NLS-1$
		Pattern reasonExpress = Pattern
				.compile(
						"<[-\\w]+:Reason>\\s+<[-\\w]+:Text.*>(.*)</[-\\w]+:Text>\\s+</[-\\w]+:Reason>", Pattern.DOTALL | Pattern.MULTILINE);//$NON-NLS-1$
		Pattern detailExpress = Pattern
				.compile(
						"<[-\\w]+:Detail>\\s+<[-\\w]+:myFaultDetail.*>(.*)</[-\\w]+:myFaultDetail>\\s+</[-\\w]+:Detail>", Pattern.DOTALL | Pattern.MULTILINE);//$NON-NLS-1$

		Matcher matcher = codeExpress.matcher(response);
		if (matcher.find() && matcher.groupCount() > 1) {
			fault.setFaultCode(matcher.group(1));
		}
		matcher = reasonExpress.matcher(response);
		if (matcher.find() && matcher.groupCount() > 1) {
			fault.setFaultReason(matcher.group(1));
		}
		matcher = detailExpress.matcher(response);
		if (matcher.find() && matcher.groupCount() > 1) {
			fault.setFaultDetailString(matcher.group(1));
		}
		return fault;
	}

	public ILauncherEventHandler getLauncherEventHandler() {
		return launcherEventHandler;
	}

	public void setLauncherEventHandler(ILauncherEventHandler launcherEventHandler) {
		this.launcherEventHandler = launcherEventHandler;
	}

}

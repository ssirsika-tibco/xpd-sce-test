package com.tibco.bx.debug.core.launching;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.invoke.datamodel.ISOAPMessage;
import com.tibco.bx.debug.core.invoke.datamodel.LaunchWsdlOperationElement;
import com.tibco.bx.debug.core.invoke.launcher.ILauncherService;
import com.tibco.bx.debug.core.invoke.transport.SOAPMessageBuilder;
import com.tibco.bx.debug.core.invoke.transport.SOAPTransport;
import com.tibco.bx.debug.core.invoke.transport.SendSOAPMessageJob;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.util.NetUtil;
import com.tibco.bx.debug.core.ws.util.WsdlUtil;
import com.tibco.bx.debug.operation.ILauncherEventHandler;

public class SOAPProcessLauncher extends AbstractProcessLauncher {

	private ISOAPMessage lastSoapRequestMsg;

	private ISOAPMessage lastSoapResponseMsg;

	private String soapRequestMessage;

	private LaunchWsdlOperationElement wsdlElement;

	private ProcessTemplate processTemplate;

	public void setSoapRequestMessage(String soapRequestMessage) {
		this.soapRequestMessage = soapRequestMessage;
	}

	public SOAPProcessLauncher(EObject startActivity, IBxProcessListing processListing, ProcessTemplate processTemplate, String endpointUrl)
			throws CoreException {
		super(startActivity, processListing);
		this.processTemplate = processTemplate;
		wsdlElement = new LaunchWsdlOperationElement(WsdlUtil.getWSDLOperation(startActivity, processListing, endpointUrl));
	}

	/**
	 * this function will do some thing as follow: <li>
	 * parser the activity,get operation information. <li>
	 * parser process parameters and mapping to soap input parameters. <li>
	 * build a SOAPMessage,send the SOAPMessage to WS endpoint. <li>
	 * handle the respond of WS
	 * <p>
	 * 
	 */
	@Override
	public Object launch(ILauncherEventHandler handle) {
		lastSoapResponseMsg = callWs(handle);
		return lastSoapResponseMsg;
	}

	private String getEndpoint(LaunchWsdlOperationElement wsdlElement) {
		String endpointUrl = WsdlUtil.getEndpointUrlFromOperationElement(wsdlElement);
		try {
			String[] versions = getProcessTemplate().getModuleVersion().split("\\.");//$NON-NLS-1$
			String version = versions[versions.length - 1];
			if (endpointUrl != null && endpointUrl.matches("^.*/qualifier")) { //$NON-NLS-1$
				endpointUrl = endpointUrl.substring(0, endpointUrl.length() - 9) + version; //$NON-NLS-1$
			}
			if (NetUtil.validateURLViaNet(endpointUrl)) {
				return endpointUrl;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> endPoint = WsdlUtil.getEndpiont(wsdlElement.getDefinition(), wsdlElement.getBindingElement().getBinding(),processListing.getConnection().getHost(), String.valueOf(processListing.getConnection().getPort()),  processListing
				.getConnection().getProtocol());

		// validate endpoint
		endpointUrl = endPoint.size() > 0 ? (String) endPoint.get(0) : ""; //$NON-NLS-1$

		try {
			if (NetUtil.validateURLViaNet(endpointUrl)) {
				return endpointUrl;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ILauncherService service = DebugCoreActivator.createILaunchService();
		if(service !=null ){
			endpointUrl = service.getEndPointDialog();
		}
		// network unreachable, get it from ui dialog.
		

		return endpointUrl;
	}

	private ISOAPMessage callWs(ILauncherEventHandler handler) {
		final SOAPTransport soaptransport = new SOAPTransport();
		final ISOAPMessage soapRequestMsg = getLastSoapRequestMsg();
		String serviceUrl = getEndpoint(wsdlElement);
		ILauncherService service = DebugCoreActivator.createILaunchService();
		if(service !=null&& service.isEmulationProcessInstanceController(handler)){
			serviceUrl = WsdlUtil.getOperationEndpoint(soapRequestMsg);
		}
		if (handler != null) {
			soaptransport.addSelectionChangedListener(handler);
		}

		if (this.soapRequestMessage != null) {
			SendSOAPMessageJob job = soaptransport.send(serviceUrl, soapRequestMessage, soapRequestMsg.getOperation().getSoapAction());
			if (handler != null) {
				job.setLauncherEventHandler(handler);
			}
			soapRequestMessage = null;
		} else {
			soaptransport.send(serviceUrl, soapRequestMsg);
		}

		return lastSoapResponseMsg;
	}

	private ISOAPMessage getLastSoapRequestMsg() {
		try {
			if (lastSoapRequestMsg == null) {
				lastSoapRequestMsg = createSoapRequestMsg();
			}
		} catch (ParserConfigurationException e) {
			DebugCoreActivator.log(e);
		}

		return lastSoapRequestMsg;
	}

	/*
	 * private String getLastSoapResponseMsg() {
	 * 
	 * if( responseBuff.length() < 1){ return null; } return
	 * XMLUtils.serialize(responseBuff.toString(), true); }
	 */

	private ISOAPMessage createSoapRequestMsg() throws ParserConfigurationException {
		String username = processListing.getConnection().getUsername();
		String password = processListing.getConnection().getPassword();
		ISOAPMessage msg = SOAPMessageBuilder.instance.buildSoapMessage(wsdlElement, username, password);
		return msg;
	}

	@Override
	public boolean isSOAPMessage() {
		return true;
	}

	/*
	 * public String getSOAPMessage(SOAPMessageType type) {
	 * 
	 * if(SOAPMessageType.Request == type){ return
	 * getLastSoapRequestMsg().toXML(); }else if(SOAPMessageType.Response ==
	 * type){ return getLastSoapResponseMsg(); } return null; }
	 */

	@Override
	public void clean() {
		// paramValue.clear();
	}

	@Override
	public ProcessTemplate getProcessTemplate() {
		return processTemplate;
	}

}

package com.tibco.bx.debug.core.ws.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Binding;
import org.eclipse.wst.wsdl.BindingOperation;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.Service;
import org.eclipse.wst.wsdl.UnknownExtensibilityElement;
import org.eclipse.wst.wsdl.WSDLElement;
import org.eclipse.wst.wsdl.binding.soap.SOAPAddress;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.invoke.constants.ProcessConstants;
import com.tibco.bx.debug.core.invoke.datamodel.ISOAPMessage;
import com.tibco.bx.debug.core.invoke.datamodel.LaunchWsdlOperationElement;
import com.tibco.bx.debug.core.invoke.launcher.ILauncherService;
import com.tibco.bx.debug.core.invoke.util.ProcessTemplateManager;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.core.util.URLUtils;
import com.tibco.bx.debug.core.ws.finder.FinderFactory;

public class WsdlUtil {

	private static final String LOCATION = "location"; //$NON-NLS-1$
	public static final QName SOAP_ADDRESS = new QName("http://schemas.xmlsoap.org/wsdl/soap/", "address"); //$NON-NLS-1$ //$NON-NLS-2$
	public static final String CREATE_DATE = "create_date"; //$NON-NLS-1$
	private static Operation operation;

	public static List<String> getEndpiont(Definition definition, Binding binding, String host, String portNum, String protocol) {
		List<String> endPoint = new ArrayList<String>();
		// first i must get the port
		Map services = definition.getServices();
		Port port = null;
		for (Object service : services.values().toArray()) {
			if (service instanceof Service) {
				port = getPort((Service) service, binding);
				if (port != null) {
					break;
				}
			}
		}

		List<ExtensibilityElement> extenElement = port.getExtensibilityElements();

		for (ExtensibilityElement extensibilityElement : extenElement) {
			String endPointUrl = null;
			if (extensibilityElement instanceof SOAPAddress) {
				SOAPAddress address = (SOAPAddress) extensibilityElement;
				endPointUrl = address.getLocationURI();

			} else if (extensibilityElement instanceof UnknownExtensibilityElement) {
				UnknownExtensibilityElement extendElement = (UnknownExtensibilityElement) extensibilityElement;
				if (extendElement.getElementType() == SOAP_ADDRESS) {
					Element addressNode = extendElement.getElement();
					endPointUrl = addressNode.getAttribute(LOCATION);
				}

			}

			endPoint.add(getEndpointUrl(protocol, host, portNum, endPointUrl));
		}

		return endPoint;
	}

	public static String getEndpointUrl(String protocol, String host, String port, String url) {

		if (url == null) {
			return ""; //$NON-NLS-1$
		}

		if (URLUtils.isURL(url)) {
			return url.replaceFirst("(?<=https?://)([\\w\\d\\\\.]+(?::\\d+)?)", host + ":" + port); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			return protocol + "://" + host + ":" + port + "/" + url.replaceFirst("/+", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}

	}

	private static Port getPort(Service service, Binding binding) {

		Map ports = service.getPorts();
		for (Iterator iterator = ports.values().iterator(); iterator.hasNext();) {
			Port port = (Port) iterator.next();
			if (port.getBinding() == binding) {
				return port;
			}

		}
		return null;
	}

	public static Document getWsdlDocument(WSDLElement wsdlElement) {
		Definition definition = getDefinition(wsdlElement);
		if (definition != null) {
			return definition.getDocument();
		}
		return null;
	}

	public static Definition getDefinition(WSDLElement wsdlElement) {
		EObject definition = null;
		if (wsdlElement instanceof Definition) {
			definition = (Definition) wsdlElement;
		} else {
			definition = wsdlElement.eContainer();
			while (!(definition instanceof Definition) && definition != null) {
				definition = definition.eContainer();
			}
		}
		return (Definition) definition;
	}

	public static BindingOperation getBinding(Operation operation, Definition definition) {
		Map bindingMap = definition.getBindings();
		for (Iterator iterator = bindingMap.values().iterator(); iterator.hasNext();) {
			Binding binding = (Binding) iterator.next();
			for (Iterator bindOpIter = binding.getBindingOperations().iterator(); bindOpIter.hasNext();) {
				BindingOperation bindingOperator = (BindingOperation) bindOpIter.next();
				if (bindingOperator.getOperation() == operation) {
					return bindingOperator;
				}

			}
		}
		return null;
	}

	public static Operation getWSDLOperation(EObject startActivity, IBxProcessListing processListing, String url) throws CoreException {
		assert (processListing != null);
		Operation operation = null;
		Map<String, String> paramters = new HashMap<String, String>();
		if (url != null) {
			operation = FinderFactory.instance.findWSDLOperation(startActivity, url);
		} else {
			ProcessTemplate template = ProcessTemplateManager.instance.getProcessTemplate(ProcessUtil.getProcess(startActivity), processListing);
			if (template != null) {
				String version = template.getModuleVersion();
				String[] versions = version.split("\\."); //$NON-NLS-1$
				paramters.put(CREATE_DATE, versions[versions.length - 1]);
			}

			paramters.put(ProcessConstants.HTTP_HOST, processListing.getConnection().getHost());
			paramters.put(ProcessConstants.HTTP_PORT, String.valueOf(processListing.getConnection().getPort()));
			paramters.put(ProcessConstants.HTTP_PROTOCOL, processListing.getConnection().getProtocol());
			ILauncherService service = DebugCoreActivator.createILaunchService();
			if (template != null || service == null) {
				operation = FinderFactory.instance.findWSDLOperation(startActivity, processListing.getConnection().getModelType(), paramters);
			}
			if (operation == null) {
				operation = getOperationFromUrl(startActivity);
			}
			if (operation == null) {
				IStatus error = new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, Messages.getString("WsdlUtil.status.message")); //$NON-NLS-1$
				throw new CoreException(error);
			}
		}
		return operation;
	}

	private static Operation getOperationFromUrl(final EObject startActivity) throws CoreException {
		// network unreachable, get it from ui dialog.
		ILauncherService service = DebugCoreActivator.createILaunchService();
		if (service != null) {
			operation = service.getOperationFromUrl(startActivity);
		}
		return operation;
	}

	public static String getOperationEndpoint(ISOAPMessage message) {
		String endpoint = null;
		if (message != null) {
			LaunchWsdlOperationElement operation = (LaunchWsdlOperationElement) message.getOperation();
			Definition definition = (Definition) operation.getDefinition();
			endpoint = XMLUtils.getWSLocation(definition.getLocation());
		}
		return endpoint;
	}

	public static String getEndpointUrlFromOperationElement(LaunchWsdlOperationElement wsdlElement) {
		String endpoint = null;
		Definition definition = (Definition) wsdlElement.getDefinition();
		if (definition != null) {
			endpoint = definition.getLocation();
			endpoint = endpoint.replace("?wsdl", ""); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return endpoint;
	}

}

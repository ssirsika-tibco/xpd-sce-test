package com.tibco.bx.composite.core.it.internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.OnMessage;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;

import com.tibco.bx.composite.core.util.WSDLUtils;

public class ComponentInterfacesModel {

	private Map<IFile, List<ServiceReferenceModel>> servicesPortTypes = new HashMap<IFile, List<ServiceReferenceModel>>();
	private Map<IFile, List<ServiceReferenceModel>> referencesPortTypes = new HashMap<IFile, List<ServiceReferenceModel>>();
	private List<ServiceReferenceModel> services = new ArrayList<ServiceReferenceModel>();
	private List<ServiceReferenceModel> references = new ArrayList<ServiceReferenceModel>();
	private BxImplementationBuilder bxImplementationBuilder;

	public ComponentInterfacesModel(BxImplementationBuilder bxImplementationBuilder) {
		this.bxImplementationBuilder = bxImplementationBuilder;
	}

	public void addServiceForOnEvent(IFile bpelFile, OnEvent onEvent,String xpdlId) {
		// String endpointUri = getEndpointUriFromActivity(bpelFile, onEvent);
		Role myRole = onEvent.getPartnerLink().getMyRole();
		PortType portType = myRole==null?onEvent.getPortType():(PortType)myRole.getPortType();
		addService(bpelFile, portType, onEvent.getOperation(),
				onEvent.getPartnerLink().getName(), xpdlId);
	}

	public void addServiceForReceive(IFile bpelFile, Receive receive,String xpdlId) {
		// String endpointUri = getEndpointUriFromActivity(bpelFile, receive);
		Role myRole = receive.getPartnerLink().getMyRole();
		PortType portType = myRole==null?receive.getPortType():(PortType)myRole.getPortType();
		addService(bpelFile, portType, receive.getOperation(),
				receive.getPartnerLink().getName(), xpdlId);
	}

	public void addServiceForOnMessage(IFile bpelFile, OnMessage onMessage,String xpdlId) {
		// String endpointUri = getEndpointUriFromActivity(bpelFile, onMessage);
		Role myRole = onMessage.getPartnerLink().getMyRole();
		PortType portType = myRole==null?onMessage.getPortType():(PortType)myRole.getPortType();
		addService(bpelFile, portType, onMessage.getOperation(),
				onMessage.getPartnerLink().getName(), xpdlId);
	}

	private void addService(IFile bpelFile, PortType portType,
			Operation operation, String serviceName,String xpdlId) {
//		PortType portType = (PortType) partnerLink.getMyRole().getPortType();
		String endpointUri = bxImplementationBuilder.getEndpointUri(xpdlId);
		if (endpointUri == null) {
			endpointUri = WSDLUtils.getBindingLocation(portType);
		}
		// only use the path of the URL, without the host and port
		try {
			URL url = new URL(endpointUri);
			endpointUri = url.getPath();
		} catch (MalformedURLException e) {
		}
		
		try {
			bpelFile.setPersistentProperty(new QualifiedName(portType.getQName()
					.toString(), serviceName), endpointUri);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServiceReferenceModel service = getService(portType, serviceName,
				endpointUri,xpdlId);
		service.addOperation(bpelFile, operation);

		List<ServiceReferenceModel> list = servicesPortTypes.get(bpelFile);
		if (list == null) {
			list = new ArrayList<ServiceReferenceModel>();
			list.add(service);
			servicesPortTypes.put(bpelFile, list);
		} else {
			for (ServiceReferenceModel im : list) {
				if (im.equals(service)) {
					im.addOperation(bpelFile, operation);
					return;
				}
			}
			list.add(service);
		}
	}

	public void addReference(IFile bpelFile, Invoke invoke,String xpdlId) {
		PortType portType = (invoke.getPartnerLink() == null ? null
				: (PortType) invoke.getPartnerLink().getPartnerRole()
						.getPortType());
		if(portType==null){
			portType = invoke.getPortType();
		}
		String referenceName = invoke.getPartnerLink().getName();
		String endpointUri = bxImplementationBuilder.getEndpointUri(xpdlId);
		if (endpointUri == null) {
			endpointUri = WSDLUtils.getBindingLocation(portType);
		}
		// only use the path of the URL, without the host and port
		try {
			URL url = new URL(endpointUri);
			endpointUri = url.getPath();
		} catch (MalformedURLException e) {
		}

		String httpclientJNDIName = bxImplementationBuilder.getHttpclientJNDIName(xpdlId);
		if (httpclientJNDIName == null) {
			httpclientJNDIName = referenceName;
		}

		try {
			bpelFile.setPersistentProperty(
					new QualifiedName(portType.getQName().toString(), referenceName), endpointUri);
			bpelFile.setPersistentProperty(
					new QualifiedName(portType.getQName().toString(), referenceName+"JNDI"), httpclientJNDIName); //$NON-NLS-1$
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ServiceReferenceModel reference = getReference(portType,
				referenceName, endpointUri,xpdlId);
		Operation op = invoke.getOperation();
		reference.addOperation(bpelFile, op);
		List<ServiceReferenceModel> list = referencesPortTypes.get(bpelFile);
		if (list == null) {
			list = new ArrayList<ServiceReferenceModel>();
			list.add(reference);
			referencesPortTypes.put(bpelFile, list);
		} else {
			for (ServiceReferenceModel im : list) {
				if (im.equals(reference)) {
					im.addOperation(bpelFile, op);
					return;
				}
			}
			list.add(reference);
		}
	}

	public List<ServiceReferenceModel> getServices(IFile bpelFile) {
		return servicesPortTypes.get(bpelFile) == null ? new ArrayList<ServiceReferenceModel>()
				: servicesPortTypes.get(bpelFile);
	}

	public List<ServiceReferenceModel> getReferences(IFile bpelFile) {
		return referencesPortTypes.get(bpelFile) == null ? new ArrayList<ServiceReferenceModel>()
				: referencesPortTypes.get(bpelFile);
	}

	public List<ServiceReferenceModel> getServices() {
		return services;
	}

	public List<ServiceReferenceModel> getReferences() {
		return references;
	}

	private void fixResourceUriEncoding(PortType portType) {
		Resource resource = portType.eResource();
		if (resource != null) {
			URI uri = resource.getURI();
			if (uri != null) {
				uri = URI.createURI(uri.toString(), true);
				resource.setURI(uri);
			}
		}
	}

	private ServiceReferenceModel getService(PortType portType, String name,
			String endpointUri,String xpdlId) {
		for (ServiceReferenceModel im : services) {
			if (im.getPortType().getQName().equals(portType.getQName())
					&& im.getName().equals(name)
					&& im.getEndpointUri().equals(endpointUri)) {
				return im;
			}
		}
			
		fixResourceUriEncoding(portType);
		ServiceReferenceModel service = new ServiceReferenceModel(portType,
				name, endpointUri,xpdlId);
		services.add(service);
		return service;
	}

	private ServiceReferenceModel getReference(PortType portType, String name,
			String endpointUri, String xpdlId) {
		for (ServiceReferenceModel im : references) {
			if (im.getPortType().getQName().equals(portType.getQName())
					&& im.getName().equals(name)
					&& im.getEndpointUri().equals(endpointUri)) {
				return im;
			}
		}
		
		fixResourceUriEncoding(portType);
		ServiceReferenceModel service = new ServiceReferenceModel(portType,
				name, endpointUri,xpdlId);
		references.add(service);
		return service;
	}
}
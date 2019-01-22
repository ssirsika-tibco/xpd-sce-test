package com.tibco.bx.composite.core.it.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;

import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.Reference;
import com.tibco.amf.sca.model.componenttype.Service;
import com.tibco.amf.sca.model.extensionpoints.SCAExtensionsFactory;
import com.tibco.amf.sca.model.extensionpoints.Wsdl11Interface;
import com.tibco.bx.model.service.ComponentType;
import com.tibco.bx.model.service.Interface;
import com.tibco.bx.model.service.ServiceFactory;

public class ServiceReferenceModel {

	private PortType portType;
	private String name = ""; //$NON-NLS-1$
	private String endpointUri;

	private Map<IFile, List<Operation>> processOperationMap = new HashMap<IFile, List<Operation>>();
	private List<Operation> operations = new ArrayList<Operation>();
	private String xpdlId;

	public ServiceReferenceModel(PortType portType, String name,
			String endpointUri, String xpdlId) {
		this.name = name;
		this.portType = portType;
		this.endpointUri = endpointUri;
		this.xpdlId = xpdlId;
	}

	public void addOperation(IFile bpelFile, Operation oper) {
		addOperation(oper);
		List<Operation> list = processOperationMap.get(bpelFile);
		if (list == null) {
			list = new ArrayList<Operation>();
			list.add(oper);
			processOperationMap.put(bpelFile, list);
		} else {
			for (Operation o : list) {
				if (o.getName().equals(oper.getName())) {
					return;
				}
			}
			list.add(oper);
		}
	}

	public String getXpdlId() {
		return xpdlId;
	}
	
	public Operation[] getOperations(IFile bpelFile) {
		return processOperationMap.get(bpelFile) == null ? new Operation[0]
				: processOperationMap.get(bpelFile).toArray(new Operation[0]);
	}

	private void addOperation(Operation oper) {
		for (Operation o : operations) {
			if (o.getName().equals(oper.getName())) {
				return;
			}
		}
		operations.add(oper);
	}

	public boolean contains(IFile bpelFile, PortType portType, Operation operation) {
		if (this.portType!=portType) {
			return false;
		}
		
		Operation[] operationsForFile = getOperations(bpelFile);
		for (Operation o : operationsForFile) {
			if (o.getName().equals(operation.getName())) {
				return true;
			}
		}
		return false;
	}

	public Operation[] getOperations() {
		return operations.toArray(new Operation[0]);
	}

	public String getName() {
		return name;
	}

	public PortType getPortType() {
		return portType;
	}

	public String getEndpointUri() {
		return endpointUri;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof ServiceReferenceModel)) {
			return false;
		}

		ServiceReferenceModel other = (ServiceReferenceModel) obj;
		return getPortType().getQName().equals(other.getPortType().getQName());
	}

	public ComponentType createComponentType() {
		ComponentType componentType = ServiceFactory.eINSTANCE
				.createComponentType();
		componentType.setName(name);
		componentType.setPortType(portType);
		return componentType;
	}

	/**
	 * @param portType
	 * @return
	 */
	private Wsdl11Interface createWSDLInterface() {
		Wsdl11Interface wsdlPortType = SCAExtensionsFactory.INSTANCE
				.createWsdl11Interface();
		wsdlPortType.setPortType(portType);
		return wsdlPortType;
	}

	public Service createService() {
		Service service = ComponentTypeFactory.eINSTANCE.createService();
		service.setName(name);
		Wsdl11Interface wsdl11Interface = this.createWSDLInterface();
		service.setInterface(wsdl11Interface);
		return service;
	}

	public Reference createReference() {
		Reference reference = ComponentTypeFactory.eINSTANCE.createReference();
		reference.setName(name);
		Wsdl11Interface wsdl11Interface = this.createWSDLInterface();
		reference.setInterface(wsdl11Interface);
		return reference;
	}

	public Interface createInterface() {
		Interface newInterface = ServiceFactory.eINSTANCE.createInterface();
		newInterface.setName(name);
		newInterface.setPortType(portType);
		return newInterface;
	}

}
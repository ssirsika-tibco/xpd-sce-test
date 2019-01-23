package com.tibco.bx.composite.core.it.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.model.Process;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;

import com.tibco.amf.sca.model.componenttype.ComponentType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.Reference;
import com.tibco.amf.sca.model.componenttype.Service;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.amx.model.service.N2PEServiceImplementation;
import com.tibco.bx.composite.core.extensions.ComponentTypeContributorUtil;
import com.tibco.bx.composite.core.it.BxImplementationTypeProvider;
import com.tibco.bx.composite.core.util.BxCompositeCoreUtil;
import com.tibco.bx.core.model.ModelFactory;
import com.tibco.bx.model.service.Interface;
import com.tibco.bx.model.service.ProcessImplementation;
import com.tibco.bx.model.service.ServiceFactory;
import com.tibco.bx.model.service.ServiceImplementation;

public class BxImplementationBuilder {

	private final IFile[] bpelFiles;
	private ComponentInterfacesModel interfacesModel;
	private String moduleName;
	private BxServiceImplementation implementation;
	private IFile implementationFile;
	private BxImplementationTypeProvider bxImplementationProvider;

	public BxImplementationBuilder(IFile[] bpelFiles, URI compositeLocation,
			IFile implementationFile, BxServiceImplementation implementation,
			BxImplementationTypeProvider bxImplementationTypeProvider) {
		this.bpelFiles = bpelFiles;
		this.moduleName = implementationFile.getFullPath().toString();
		this.implementationFile = implementationFile;
		this.implementation = implementation;
		this.bxImplementationProvider = bxImplementationTypeProvider;
	}

	public void buildImplementation() throws CoreException, IOException {
		interfacesModel = new ComponentInterfacesModel(this);
		ServiceInterfaceFinder serviceInterfaceFinder = new ServiceInterfaceFinder(
				interfacesModel,implementation instanceof N2PEServiceImplementation);
		for (IFile bpelFile : bpelFiles) {
			Process process = BxCompositeCoreUtil.getProcess(bpelFile);
			serviceInterfaceFinder.findInterfaces(bpelFile, process);
		}
		
		ComponentType componentType = createComponentType();
		implementation.setServiceType(componentType);

		ServiceImplementation serviceImplementation = createServiceImplementation(bpelFiles);
		implementation.setServiceModel(serviceImplementation);
		
		ComponentTypeContributorUtil.contributeToComponentType(
				componentType, implementation, bxImplementationProvider.getImplementationTypeId());
	}

	String getEndpointUri(String xpdlId){
		return bxImplementationProvider.getEndpointUri(implementationFile, xpdlId);
	}
	
	String getHttpclientJNDIName(String xpdlId) {
		return bxImplementationProvider.getHttpclientJNDIName(implementationFile, xpdlId);
	}
	
	private ServiceImplementation createServiceImplementation(IFile[] bpelFiles) {
		ServiceImplementation serviceImplementation = ServiceFactory.eINSTANCE
				.createServiceImplementation();
		serviceImplementation.setModuleName(moduleName);
		serviceImplementation.setModuleVersion(1);
		serviceImplementation.setName(BxCompositeCoreUtil.getFileName(URI
				.createPlatformResourceURI(moduleName, false)));

		EList<Interface> interfaces = serviceImplementation.getInterfaces();
		List<ServiceReferenceModel> srms = new ArrayList<ServiceReferenceModel>();
		srms.addAll(interfacesModel.getServices());
		srms.addAll(interfacesModel.getReferences());
		for (ServiceReferenceModel s : srms) {
			Interface newInterface = s.createInterface();

			createOperations(newInterface, bpelFiles, s);

			interfaces.add(newInterface);

			if (serviceImplementation.getNamespace() == null
					|| serviceImplementation.getNamespace().equals("")) {
				String targetNamespace = s.getPortType().getQName()
						.getNamespaceURI();
				serviceImplementation.setNamespace(targetNamespace);
			}
		}

		EList<com.tibco.bx.core.model.Process> processes = serviceImplementation.getProcesses();
		for (IResource p : bpelFiles) {
			IFile bpelFile = (IFile) p;
			com.tibco.bx.core.model.Process newProcess = ModelFactory.eINSTANCE
					.createProcess();
			newProcess.setProcessFileName(bpelFile.getFullPath().toString());
			newProcess
					.setProcessName(BxCompositeCoreUtil.getFileName(bpelFile));
			processes.add(newProcess);
		}
		
		IFile scriptDescriptorFile = BxCompositeCoreUtil.getScriptDescriptorFile(implementationFile.getProject());
		if (scriptDescriptorFile != null && scriptDescriptorFile.exists()) {
			serviceImplementation.setScriptDescriptorFileName(scriptDescriptorFile.getFullPath().toString());
		}

		return serviceImplementation;
	}

	private void createOperations(Interface newInterface, IFile[] bpelFiles,
			ServiceReferenceModel s) {
		EList<com.tibco.bx.model.service.Operation> operations = newInterface
				.getOperations();
		PortType portType = s.getPortType();
		for (Operation operation : s.getOperations()) {
			com.tibco.bx.model.service.Operation newOperation = ServiceFactory.eINSTANCE
					.createOperation();
			newOperation.setComponentServiceName(s.getName());
			newOperation.setOperationName(operation.getName());

			createOperationImplementations(newOperation, portType,operation, bpelFiles);
			operations.add(newOperation);
		}
	}

	private void createOperationImplementations(
			com.tibco.bx.model.service.Operation newOperation,
			PortType portType, Operation operation, IFile[] bpelFiles) {
		EList<ProcessImplementation> operationImplementations = newOperation
				.getOperationImplementations();

		for (IFile f : bpelFiles) {
			boolean found = false;
			for (ServiceReferenceModel so : interfacesModel.getServices(f)) {
				if (!so.contains(f, portType,operation)) {
					continue;
				}
				ProcessImplementation processImplementation = getProcessImplementationFrom(
						f, operationImplementations);

				createComponentServices(processImplementation, so);
				found = true;
			}

			if (!found) {
				continue;
			}

			for (ServiceReferenceModel ro : interfacesModel.getReferences(f)) {
				ProcessImplementation processImplementation = getProcessImplementationFrom(
						f, operationImplementations);

				createComponentReferences(processImplementation, ro);
			}
		}

	}

	private ProcessImplementation getProcessImplementationFrom(IFile f,
			EList<ProcessImplementation> operationImplementations) {
		String filePath = f.getFullPath().toString();
		for (ProcessImplementation implementation : operationImplementations) {
			if (implementation.getProcessFileName().equals(filePath)) {
				return implementation;
			}
		}
		ProcessImplementation processImplementation = ServiceFactory.eINSTANCE
				.createProcessImplementation();
		processImplementation.setProcessFileName(filePath);
		processImplementation
				.setProcessName(BxCompositeCoreUtil.getFileName(f));
		operationImplementations.add(processImplementation);
		return processImplementation;
	}

	private void createComponentReferences(
			ProcessImplementation processImplementation,
			ServiceReferenceModel referenceModel) {
		EList<com.tibco.bx.model.service.ComponentType> componentReferences = processImplementation
				.getComponentReferences();
		for (com.tibco.bx.model.service.ComponentType c : componentReferences) {
			if (c.getPortType().getQName().equals(
					referenceModel.getPortType().getQName())) {
				return;
			}
		}

		componentReferences.add(referenceModel.createComponentType());
	}

	private void createComponentServices(
			ProcessImplementation processImplementation,
			ServiceReferenceModel serviceModel) {
		EList<com.tibco.bx.model.service.ComponentType> componentServices = processImplementation
				.getComponentServices();
		for (com.tibco.bx.model.service.ComponentType c : componentServices) {
			if (c.getPortType().getQName().equals(
					serviceModel.getPortType().getQName())) {
				return;
			}
		}

		componentServices.add(serviceModel.createComponentType());
	}

	private ComponentType createComponentType() {
		ComponentType componentType = ComponentTypeFactory.eINSTANCE.createComponentType();

		for (ServiceReferenceModel intf : interfacesModel.getServices()) {
			Service service = intf.createService();
			service.setName(intf.getName());
			componentType.getServices().add(service);
		}

		for (ServiceReferenceModel intf : interfacesModel.getReferences()) {
			Reference reference = intf.createReference();
			reference.setName(intf.getName());
			componentType.getReferences().add(reference);
		}
		
		return componentType;
	}

}

package com.tibco.bx.composite.core.it;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.implementation.BaseComponentTypeResolver;
import com.tibco.amf.sca.model.componenttype.ComponentType;
import com.tibco.amf.sca.model.componenttype.Reference;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.componenttype.Service;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.amf.sca.model.extensionpoints.Interface;
import com.tibco.amf.sca.model.extensionpoints.Property;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.extensions.RequirementsResolverUtil;

public abstract class BxComponentTypeResolver extends BaseComponentTypeResolver {

	protected List<Property> getProperties(Implementation impl) {
		BxServiceImplementation bxImplementation = (BxServiceImplementation) impl;
		ComponentType serviceType = bxImplementation.getServiceType();
		if (serviceType == null) {
			return new ArrayList<Property>();
		}
		EList<Property> properties = serviceType.getProperties();
		List<Property> r = new ArrayList<Property>();
		for (Property s : properties) {
			r.add((Property) EcoreUtil.copy((EObject) s));
		}
		return r;
	}

	protected List<Reference> getReferences(Implementation impl) {
		BxServiceImplementation bxImplementation = (BxServiceImplementation) impl;
		ComponentType serviceType = bxImplementation.getServiceType();
		if (serviceType == null) {
			return new ArrayList<Reference>();
		}
		EList<Reference> references = serviceType.getReferences();
		List<Reference> copiedReferences = new ArrayList<Reference>();
		for (Reference ref : references) {
			Reference copiedRef = (Reference) EcoreUtil.copy(ref);
			Interface copiedIntf = ComponentTypeActivator.getDefault().getInterfaceTypeService().copyInterface(ref.getInterface());
			copiedRef.setInterface(copiedIntf);
			copiedReferences.add(copiedRef);
		}
		return copiedReferences;
	}


	protected List<Service> getServices(Implementation impl) {
		BxServiceImplementation bxImplementation = (BxServiceImplementation) impl;
		ComponentType serviceType = bxImplementation.getServiceType();
		if (serviceType == null) {
			return new ArrayList<Service>();
		}
		EList<Service> services = serviceType.getServices();
		List<Service> copiedServices = new ArrayList<Service>();
		for (Service service : services) {
			Service copiedService = (Service) EcoreUtil.copy(service);
			Interface copiedIntf = ComponentTypeActivator.getDefault().getInterfaceTypeService().copyInterface(service.getInterface());
			copiedService.setInterface(copiedIntf);
			copiedServices.add(copiedService);
		}
		return copiedServices;
	}

	protected boolean isAvailable(Implementation impl) {
		if (impl instanceof BxServiceImplementation) {
			BxServiceImplementation bxImplementation = (BxServiceImplementation) impl;
			return bxImplementation.getServiceModel() != null;
		}
		return false;
	}

	@Override
	public Requirements getComponentRequirements(Implementation impl) {
		IResource[] resources = getImplementationResources(impl);
		if (resources.length == 0) {
			return super.getComponentRequirements(impl);
		}
		return RequirementsResolverUtil.resolveRequirements(impl, getImplementationTypeId());
	}

	public abstract String getImplementationTypeId();

	@Override
	public IResource[] getImplementationResources(Implementation impl) {
		if (!isAvailable(impl)) {
			return super.getImplementationResources(impl);
		}

		BxServiceImplementation bxImplementation = (BxServiceImplementation) impl;
		String bpelFilePath = bxImplementation.getServiceModel()
				.getModuleName();
		if (bpelFilePath == null || bpelFilePath.equals("")) {
			return super.getImplementationResources(impl);
		}
		IFile bpelFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(bpelFilePath));
		if (bpelFile.exists()) {
			return new IResource[] { bpelFile };
		}
		return super.getImplementationResources(impl);
	}
}

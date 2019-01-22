package com.tibco.bx.composite.xpdl.core.extensions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Version;

import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.ProvidedCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.extensions.IRequirementsResolver;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Package;


public class ProcessPackageRequirementsResolver implements IRequirementsResolver {

	public void addImplementationRequirements(Requirements requirements, Implementation implementation) {
		BxServiceImplementation bxImplementation = (BxServiceImplementation) implementation;
		ServiceImplementation serviceModel = bxImplementation.getServiceModel();
		if (serviceModel == null) {
			return;
		}
		String moduleName = serviceModel.getModuleName();
		if (moduleName == null || moduleName.equals("")) {
			return;
		}
		IFile xpdlFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(moduleName));
		WorkingCopy workingCopy = XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);
		if (workingCopy == null || !(workingCopy.getRootElement() instanceof Package)) {
			return;
		}
				
		IProject project = xpdlFile.getProject();
		Package pkg = (Package) workingCopy.getRootElement();
		String capabilityId = XPDLUtils.getPackageCapabilityId(project, pkg);
		Version version = new Version(pkg.getRedefinableHeader().getVersion());
		ProvidedCapability pc = createProvidedCapability(capabilityId, version);
		requirements.getProvidedCapabilities().add(pc);		
	}

	private ProvidedCapability createProvidedCapability(String id, Version version) {
		ProvidedCapability pc = ComponentTypeFactory.eINSTANCE.createProvidedCapability();
		pc.setId(id);
		pc.setType(CapabilityType.CUSTOM);
		String versionStr = String.format("%d.%d.%d", new Object[] {version.getMajor(), version.getMinor(), version.getMicro()}); //$NON-NLS-1$
		pc.setVersion(versionStr);
		return pc;
	}

}

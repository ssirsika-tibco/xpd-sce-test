package com.tibco.bx.composite.xpdl.core.extensions;

import java.util.List;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxGlobalSignalImplementation;
import com.tibco.bx.composite.core.extensions.IRequirementsResolver;
import com.tibco.bx.composite.core.extensions.RequirementsResolverUtil;

public class GlobalSignalRequirementsResolver implements IRequirementsResolver{

	public void addImplementationRequirements(Requirements requirements,
			Implementation impl) {
		if (!(impl instanceof BxGlobalSignalImplementation)) {
			return;
		}
		addPackageImports(requirements.getPackageImports());
	}

	protected void addPackageImports(List<ImportPackage> imports) {
		imports.add(RequirementsResolverUtil.createAMXModelImportPackage());
	}
}

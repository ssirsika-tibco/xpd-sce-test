package com.tibco.bx.composite.xpdl.core.it;

import java.util.List;

import com.tibco.amf.sca.componenttype.implementation.BaseComponentTypeResolver;
import com.tibco.amf.sca.model.componenttype.Reference;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.componenttype.Service;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.amf.sca.model.extensionpoints.Property;
import com.tibco.bx.amx.model.service.BxGlobalSignalImplementation;
import com.tibco.bx.composite.core.extensions.RequirementsResolverUtil;
import com.tibco.bx.composite.core.util.BxCompositeCoreConstants;

public class BxGlobalSignalComponentTypeResolver extends BaseComponentTypeResolver {

	public String getImplementationTypeId() {
		return BxCompositeCoreConstants.GLOBAL_SIGNAL_SCA_IMPLEMENTATION_TYPE;
	}

	@Override
	protected boolean isAvailable(Implementation paramImplementation) {
		return paramImplementation instanceof BxGlobalSignalImplementation;
	}

	@Override
	public Requirements getComponentRequirements(Implementation impl) {
		return RequirementsResolverUtil.resolveRequirements(impl, getImplementationTypeId());
	}

	@Override
	protected List<Service> getServices(Implementation paramImplementation) {
		return null;
	}

	@Override
	protected List<Reference> getReferences(Implementation paramImplementation) {
		return null;
	}

	@Override
	protected List<Property> getProperties(Implementation paramImplementation) {
		return null;
	}
}
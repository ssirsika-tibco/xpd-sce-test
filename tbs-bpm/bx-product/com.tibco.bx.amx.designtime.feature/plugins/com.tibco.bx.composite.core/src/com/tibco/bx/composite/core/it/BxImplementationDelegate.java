package com.tibco.bx.composite.core.it;

import com.tibco.amf.sca.componenttype.implementation.ImplementationDelegate;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.ServiceFactory;

public class BxImplementationDelegate extends ImplementationDelegate {

	public BxImplementationDelegate() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.amf.sca.componenttype.implementation.ImplementationDelegate
	 * #createDefaultImplementation()
	 */
	@Override
	public Implementation createDefaultImplementation() {
		return ServiceFactory.eINSTANCE.createBxServiceImplementation();
	}
}

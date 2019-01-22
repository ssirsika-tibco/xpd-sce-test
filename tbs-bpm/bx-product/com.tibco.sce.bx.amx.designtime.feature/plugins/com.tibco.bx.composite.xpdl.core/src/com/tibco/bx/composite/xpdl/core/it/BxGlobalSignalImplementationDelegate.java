package com.tibco.bx.composite.xpdl.core.it;

import com.tibco.amf.sca.componenttype.implementation.ImplementationDelegate;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.ServiceFactory;

public class BxGlobalSignalImplementationDelegate extends ImplementationDelegate {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.amf.sca.componenttype.implementation.ImplementationDelegate
	 * #createDefaultImplementation()
	 */
	@Override
	public Implementation createDefaultImplementation() {
		return ServiceFactory.eINSTANCE.createBxGlobalSignalImplementation();
	}

}

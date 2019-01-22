package com.tibco.bx.composite.xpdl.core.it;

import com.tibco.bx.composite.core.it.BxComponentTypeResolver;
import com.tibco.bx.composite.core.util.BxCompositeCoreConstants;

public class N2PEComponentTypeResolver extends BxComponentTypeResolver {

	public String getImplementationTypeId() {
		return BxCompositeCoreConstants.N2PE_SCA_IMPLEMENTATION_TYPE;
	}
}

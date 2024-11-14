package com.tibco.xpd.implementer.resources.xpdl2.properties;

import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter.RsoType;

/**
 * Selects only Catch Error events where the error is thrown from a REST Service
 * invocation activity.
 * 
 * @author nwilson
 */
public class RestCatchInMappingFilter extends AbstractRestCatchInMappingFilter {

	@Override
	protected RsoType getSupportedRSOType() {
		return RsoType.RSD;
	}

}
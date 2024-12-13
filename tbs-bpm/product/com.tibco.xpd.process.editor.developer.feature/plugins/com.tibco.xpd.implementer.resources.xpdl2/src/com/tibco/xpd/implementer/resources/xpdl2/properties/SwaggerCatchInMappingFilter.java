/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter.RsoType;

/**
 * ACE-8866: Selects only Catch Error events where the error is thrown from a Swagger Service
 * invocation activity.
 * 
 * @author ssirsika
 * @since 24 Oct 2024
 */
public class SwaggerCatchInMappingFilter extends AbstractRestCatchInMappingFilter {

	@Override
	protected RsoType getSupportedRSOType() {
		return RsoType.SWAGGER;
	}
}
/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.rest.swagger;

import io.swagger.v3.oas.models.PathItem.HttpMethod;

/**
 * Class that handles the method ID that we use for referencing Swagger operations from indexer and processes.
 * 
 * Method reference id's are in the form "<HttpMethod>::<ResourcePath>"
 * 
 * e.g. "put::/pets/{id}"
 *
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerOperationReference
{
	private String		resourcePath	= null;

	private HttpMethod	httpMethod		= null;

	/**
	 * Construct a swagger operation reference from the original operation resource path and HTTP method type
	 * 
	 * @param resourcePath
	 * @param httpMethod
	 */
	public SwaggerOperationReference(String resourcePath, HttpMethod httpMethod)
	{
		this.resourcePath = resourcePath;
		this.httpMethod = httpMethod;
	}

	/**
	 * Reconstruct a swagger operation reference from the referenceID string
	 * 
	 * @param operationRef
	 */
	public SwaggerOperationReference(String operationRef)
	{
		int i = operationRef.indexOf("::"); //$NON-NLS-1$
		
		if (i > 0)
		{
			String m = operationRef.substring(0, i);

			httpMethod = HttpMethod.valueOf(m);

			resourcePath = operationRef.substring(i + 2);
		}
	}

	/**
	 * Get the swagger operation reference as a string. A {@link SwaggerOperationReference} can be reconstituted from
	 * this at a later time.
	 * 
	 * @return Swagger operation reference as a string, in the form "<HttpMethod>::<ResourcePath>"
	 */
	public String getSwaggerOperationRef()
	{
		return httpMethod.name() + "::" + resourcePath;
	}

	/**
	 * @return the resource path for the swagger operation reference
	 */
	public String getResourcePath()
	{
		return resourcePath;
	}

	/**
	 * @return the HTTP method for the swagger operation reference
	 */
	public HttpMethod getHttpMethod()
	{
		return httpMethod;
	}

	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@Override
	public String toString()
	{
		return getSwaggerOperationRef();
	}

}

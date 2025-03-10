/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.rest.swagger.internal;

/**
 * List of images registered with Image Registry.
 *
 * @author nkelkar
 * @since Aug 7, 2024
 */
public enum SwaggerImage
{
	SWAGGER_FILE_ICON("icons/SwaggerFileIcon.png"), //$NON-NLS-1$
	METHOD("icons/Method.png"), //$NON-NLS-1$
	METHOD_GET("icons/MethodGet.png"), //$NON-NLS-1$
	METHOD_POST("icons/MethodPost.png"), //$NON-NLS-1$
	METHOD_PUT("icons/MethodPut.png"), //$NON-NLS-1$
	METHOD_DELETE("icons/MethodDelete.png"); //$NON-NLS-1$

	private String id;

	SwaggerImage(String id)
	{
        this.id = id;
    }

	public String getId()
	{
		return id;
	}
}

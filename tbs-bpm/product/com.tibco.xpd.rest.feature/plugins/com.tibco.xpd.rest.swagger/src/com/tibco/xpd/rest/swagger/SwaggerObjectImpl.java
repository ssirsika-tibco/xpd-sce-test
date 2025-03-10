/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.rest.swagger;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import io.swagger.v3.oas.models.OpenAPI;

/**
 * <!-- begin-user-doc -->
 * 
 * An implementation of a dummy EMF model object for a Swagger Model.
 * 
 * This is not a standard EObject but is a dummy that contains the actual OpenAPI model details and resource(IResource)
 * of a Swagger file. This was created because AbstractWorkingCopy supports only EMF models. Swagger model is not EMF
 * but needs to be loaded using a AbstractWorkingCopy implementation.
 * 
 * <!-- end-user-doc -->
 * <p>
 * The following methods are implemented:
 * </p>
 * <ul>
 * <li>{@link com.tibco.xpd.rest.swagger.SwaggerObjectImpl#getModel <em>OpenAPI</em>}</li>
 * <li>{@link com.tibco.xpd.rest.swagger.SwaggerObjectImpl#getResourcePath <em>String</em>}</li>
 * </ul>
 * 
 * @author nkelkar
 * @since Jul 4, 2024
 */
public class SwaggerObjectImpl extends EObjectImpl
{
	/**
	 * The workspace analog (Eclipse resource) of the Swagger file in the local file system
	 */
	private IResource	resource;

	/**
	 * The OpenAPI class instance which is a POJO version of the OpenAPI document.
	 */
	private OpenAPI	model;

	/**
	 * 
	 * @param aResource
	 * @param aModel
	 */
	public SwaggerObjectImpl(IResource aResource, OpenAPI aModel)
	{
		this.resource = aResource;
		this.model = aModel;
	}

	/**
	 * Returns the resource for the Swagger file in the local file system
	 * 
	 * @return resourcePath
	 */
	public IResource getResource()
	{
		return resource;
	}

	/**
	 * Returns the OpenAPI model, i.e., a OpenAPI class instance which is a POJO version of the OpenAPI document
	 * 
	 * @return model
	 */
	public OpenAPI getModel()
	{
		return model;
	}
}

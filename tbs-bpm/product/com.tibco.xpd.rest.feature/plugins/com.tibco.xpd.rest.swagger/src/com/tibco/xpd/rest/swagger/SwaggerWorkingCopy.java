/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.rest.swagger;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.resources.wc.InvalidFileException;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

/**
 * Working copy implementation for the Swagger/OAS file. This will return a dummy UML EMF model that will contain the
 * actual OpenAPI model details based on the 'io.swagger.v3.parser' API, which will load either Swagger 2.0 or OpenAPI
 * 3.0
 * 
 * @author nkelkar
 * @since Jul 2, 2024
 */
public class SwaggerWorkingCopy extends AbstractWorkingCopy
{
	/**
	 * @param resources
	 */
	public SwaggerWorkingCopy(List<IResource> resources)
	{
		super(resources);
	}

	/**
	 * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#createEditingDomain()
	 *
	 * @return
	 */
	@Override
	protected EditingDomain createEditingDomain()
	{
		return new AdapterFactoryEditingDomain(getAdapterFactory(), new BasicCommandStack());
	}

	/**
	 * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#createAdapterFactory()
	 *
	 * @return
	 */
	@Override
	protected AdapterFactory createAdapterFactory()
	{
		return new AdapterFactoryImpl();
	}

	/**
	 * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#doSave()
	 *
	 * @throws IOException
	 */
	@Override
	protected void doSave() throws IOException
	{
		// Do nothing here for now
	}

	/**
	 * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#doLoadModel()
	 *
	 * @return
	 * @throws InvalidFileException
	 */
	@Override
	protected EObject doLoadModel() throws InvalidFileException
	{
		// Load working copy models
		IResource resource = getFirstResource();
		SwaggerObjectImpl result = null;

		if (resource != null && resource.isAccessible())
		{
			OpenAPIParser openAPIParser = new OpenAPIParser();
			String filePath = resource.getLocation().toPortableString();

			// Validate the Swagger/OAS File
			SwaggerParseResult swaggerResult = openAPIParser.readLocation(filePath, null, null);
			OpenAPI openAPI = swaggerResult.getOpenAPI();
			
			// Get the validation errors and warnings during the parsing
			List<String> errorMessages = swaggerResult.getMessages();

			if ((errorMessages != null && errorMessages.size() > 0) || openAPI == null)
			{
				throw new InvalidFileException(String.join(", ", errorMessages)); //$NON-NLS-1$
			}

			// Create the dummy EMF Object and return
			result = new SwaggerObjectImpl(resource, openAPI);
		}

		return result;
	}
	/**
	 * @see com.tibco.xpd.resources.WorkingCopy#getWorkingCopyEPackage()
	 *
	 * @return
	 */
	@Override
	public EPackage getWorkingCopyEPackage()
	{
		return null;
	}
}

/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter.RsoType;
import com.tibco.xpd.implementer.script.RestActivityAdapterFactory;
import com.tibco.xpd.implementer.script.RestActivityMessageProvider;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Selects only REST Service activities that can have applied out mappings and have an associated Rest Service Operation
 * of type 'Swagger'
 *
 * @author nkelkar
 * @since Aug 30, 2024
 */
public class SwaggerActivityOutMappingFilter implements IFilter
{

	private RestServiceTaskAdapter rsta;

	public SwaggerActivityOutMappingFilter()
	{
		rsta = new RestServiceTaskAdapter();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean select(Object toTest)
	{
		EObject eo = null;
		if (toTest instanceof EObject)
		{
			eo = (EObject) toTest;
		}
		else if (toTest instanceof IAdaptable)
		{
			eo = ((IAdaptable) toTest).getAdapter(EObject.class);
		}
		if (eo instanceof Activity)
		{
			Activity activity = (Activity) eo;

			/*
			 * Sid - only show section IF a swagger service is actually selected. Otherwise, when REST service task was
			 * last thing selected and mappings section tab was last thing selected, then if a new REST sercie task is
			 * created the General tab will be switched to the Mapping tab even before the service has been selected.
			 * This would be confusing for users.
			 */
			if (rsta.isRestServiceImplementation(activity) && (rsta.getRsoType(activity) == RsoType.SWAGGER)
					&& (rsta.getServiceRefKey(activity) != null))
			{
				RestActivityMessageProvider messageAdapter = RestActivityAdapterFactory.getInstance()
						.getMessageProvider((Activity) eo);

				return messageAdapter != null && messageAdapter.hasMappingOut((Activity) eo);
			}
		}
		return false;
	}
}
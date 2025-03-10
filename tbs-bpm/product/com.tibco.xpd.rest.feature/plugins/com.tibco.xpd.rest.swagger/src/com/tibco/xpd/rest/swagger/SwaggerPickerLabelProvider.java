/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rest.swagger;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.ui.picker.BasePickerLabelProvider;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.rest.swagger.internal.SwaggerActivator;
import com.tibco.xpd.rest.swagger.internal.SwaggerImage;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem.HttpMethod;

/**
 * Provides labels and images for Swagger picker items.
 *
 * @author nkelkar
 * @since Aug 7, 2024
 */
public class SwaggerPickerLabelProvider extends BasePickerLabelProvider {

	private static final String SWAGGER_METHOD_TYPE = Operation.class.getName();

	/**
	 * {@inheritDoc}
	 */
    @Override
    public Image getImage(PickerItem pickerItem) {
		String type = pickerItem.getType();

		if (SWAGGER_METHOD_TYPE.equals(type))
		{
			HttpMethod httpMethod = HttpMethod.valueOf(pickerItem.get(SwaggerIndexProvider.HTTP_METHOD));
			switch (httpMethod)
			{
				case GET:
					return SwaggerActivator.getDefault().getImage(SwaggerImage.METHOD_GET);
				case POST:
					return SwaggerActivator.getDefault().getImage(SwaggerImage.METHOD_POST);
				case PUT:
					return SwaggerActivator.getDefault().getImage(SwaggerImage.METHOD_PUT);
				case DELETE:
					return SwaggerActivator.getDefault().getImage(SwaggerImage.METHOD_DELETE);
				default:
					break;
			}
			return SwaggerActivator.getDefault().getImage(SwaggerImage.METHOD);
		}

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText(PickerItem pickerItem) {
        String type = pickerItem.getType();
		if (SWAGGER_METHOD_TYPE.equals(type))
		{
			String httpMethod = pickerItem.get(SwaggerIndexProvider.HTTP_METHOD);
			StringBuilder sb = new StringBuilder(pickerItem.getName());
			sb.append(" (").append(httpMethod).append(")"); //$NON-NLS-1$ //$NON-NLS-2$
			return sb.toString();
		}
        return super.getText(pickerItem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String decorateSelectedItemText(String text, PickerItem element) {
        String type = element.getType();
		if (SWAGGER_METHOD_TYPE.equals(type))
		{
			String serviceName = element.get(SwaggerIndexProvider.SERVICE_NAME);
			String resourceName = element.get(SwaggerIndexProvider.RESOURCE_NAME);
			StringBuilder sb = new StringBuilder(text);
			sb.append(" - ").append(serviceName).append(": ").append(resourceName); //$NON-NLS-1$ //$NON-NLS-2$
			return sb.toString();
		}
        return super.decorateSelectedItemText(text, element);
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getStatusImage(PickerItem item)
	{
		if (item.getURI() == null)
		{
			return null;
		}
		URI uri = URI.createURI(item.getURI());
		if (uri != null)
		{
			return SwaggerActivator.getDefault().getImage(SwaggerImage.SWAGGER_FILE_ICON);
		}
		return null;
	}
}

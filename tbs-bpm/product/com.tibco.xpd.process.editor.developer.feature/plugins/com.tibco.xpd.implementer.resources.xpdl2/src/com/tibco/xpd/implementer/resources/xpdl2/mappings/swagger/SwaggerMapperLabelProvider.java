/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.ImageConstants;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaImage;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;

/**
 * Label provider for Swagger Data Mapper tree items.
 *
 * @author nkelkar
 * @since Sep 5, 2024
 */
public class SwaggerMapperLabelProvider extends LabelProvider
{
	/**
	 * Constructor.
	 */
	public SwaggerMapperLabelProvider()
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(Object element)
	{
		String text = ""; //$NON-NLS-1$
		if (element instanceof SwaggerMapperTreeItem)
		{
			return ((SwaggerMapperTreeItem) element).getLabel();
		}
		else
		{
			text = super.getText(element);
		}
		return text;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getImage(Object element)
	{
		ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
		if (element instanceof SwaggerPayloadContainerTreeItem || element instanceof SwaggerParamContainerTreeItem
				|| element instanceof SwaggerResponseContainerTreeItem)
		{
			return imageRegistry.get(ImageConstants.PART);
		}
		else if (element instanceof SwaggerTypedTreeItem)
		{
			/* Sid ACE-8889 show same type dependent image as we do for RSD / JSD content. */
			SwaggerPropertyType type = ((SwaggerTypedTreeItem) element).getType();
			boolean isArray = ((SwaggerTypedTreeItem) element).isArray();

			RestSchemaImage imageId = null;

			if (SwaggerPropertyType.BOOLEAN.equals(type))
			{
				imageId = isArray ? RestSchemaImage.JSON_BOOLEAN_ARRAY_PROPERTY : RestSchemaImage.JSON_BOOLEAN_PROPERTY;
			}
			else if (SwaggerPropertyType.DATE.equals(type))
			{
				imageId = isArray ? RestSchemaImage.JSON_DATE_TIME_ARRAY_PROPERTY
						: RestSchemaImage.JSON_DATE_TIME_PROPERTY;
			}
			else if (SwaggerPropertyType.DATETIME.equals(type))
			{
				imageId = isArray ? RestSchemaImage.JSON_DATE_TIME_ARRAY_PROPERTY
						: RestSchemaImage.JSON_DATE_TIME_PROPERTY;
			}
			else if (SwaggerPropertyType.INTEGER.equals(type))
			{
				imageId = isArray ? RestSchemaImage.JSON_INTEGER_ARRAY_PROPERTY : RestSchemaImage.JSON_INTEGER_PROPERTY;
			}
			else if (SwaggerPropertyType.NUMBER.equals(type))
			{
				imageId = isArray ? RestSchemaImage.JSON_NUMBER_ARRAY_PROPERTY : RestSchemaImage.JSON_NUMBER_PROPERTY;
			}
			else if (SwaggerPropertyType.OBJECT.equals(type))
			{
				if (element instanceof SwaggerPayloadRootTreeItem)
				{
					imageId = RestSchemaImage.JSON_ROOT_CLASS_OBJECT;
				}
				else
				{
					imageId = isArray ? RestSchemaImage.JSON_CLASS_ARRAY_PROPERTY : RestSchemaImage.JSON_CLASS_PROPERTY;
				}
			}
			else if (SwaggerPropertyType.STRING.equals(type))
			{
				imageId = isArray ? RestSchemaImage.JSON_STRING_ARRAY_PROPERTY : RestSchemaImage.JSON_STRING_PROPERTY;
			}
			else
			{
				imageId = RestSchemaImage.JSON_BASE_PROPERTY;
			}

			return RestSchemaUiPlugin.getDefault().getImage(imageId);

		}
		/* Sid ACE-8885 Support image on Unsupported items so that they can be decorated */
		else if (element instanceof UnsupportedSwaggerTreeItem)
		{
			return imageRegistry.get(ImageConstants.PART);
		}
		return super.getImage(element);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose()
	{
		super.dispose();
	}
}
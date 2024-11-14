/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import java.util.Objects;

import com.tibco.xpd.xpdl2.Activity;

import io.swagger.v3.oas.models.media.Schema;

/**
 * Represents the root of the payload data.
 *
 * @author aallway
 * @since 4 Oct 2024
 */
@SuppressWarnings("nls")
public class SwaggerPayloadRootTreeItem extends SwaggerPayloadTreeItem
{
	private String mediaType;

	/**
	 * Construct the root payload tree item.
	 * 
	 * @param parent
	 * @param activity
	 * @param path
	 * @param name
	 * @param mediaType
	 * @param propertySchema
	 * @param isRequired
	 */
	public SwaggerPayloadRootTreeItem(SwaggerMapperTreeItem parent, Activity activity, String path, String name,
			String mediaType, Schema propertySchema, boolean isRequired)
	{
		super(parent, activity, path, name, propertySchema, isRequired);

		this.mediaType = mediaType;
	}

	/**
	 * @return the mediaType
	 */
	public String getMediaType()
	{
		return mediaType;
	}

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerTypedTreeItem#getLabel()
	 *
	 * @return
	 */
	@Override
	public String getLabel()
	{
		/* The is the name of the type directly defined in the property. */
		String typeName = getTypeNameEvenIfUnsupported(getPropertySchema());

		/**
		 * Sid ACE-8885 If the root payload is a $ref to a type definition that's just an indirection to an array of
		 * other things, then getTypeName() will return the name of the underlying type or the array.
		 * 
		 * If that's the case it is better to show the referenced type name and the actual type after that. So check if
		 * ref'd schema name is different from the underlying type name and if so, show both).
		 */

		SwaggerPropertyType type = getType(); // This is the actual type that the payload resolves to thru all
												// indirections.

		if (type != null)
		{
			if (SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE.equals(type))
			{
				/*
				 * For mixed type schemas, show 'Mixed Type (unsupported) : <actual mixed type construct>"
				 */
				Schema composedSchema = getPropertySchema();

				if ("array".equals(composedSchema.getType()))
				{
					composedSchema = composedSchema.getItems();
				}

				if (composedSchema != null)
				{
					if (composedSchema.getOneOf() != null)
					{
						return typeName + " : oneOf" + (isArray() ? "[]" : "");
					}
					else if (composedSchema.getAnyOf() != null)
					{
						return typeName + " : anyOf" + (isArray() ? "[]" : "");
					}
					else if (composedSchema.getAllOf() != null)
					{
						return typeName + " : allOf" + (isArray() ? "[]" : "");
					}
					else if (composedSchema.getNot() != null)
					{
						return typeName + " : not" + (isArray() ? "[]" : "");
					}
				}
			}

			if (SwaggerPropertyType.UNSUPPORTED_ARRAY_OF_ARRAYS.equals(type))
			{
				/*
				 * Use suffix of []..[] on arrays of arrays, as we only know there are least one level of array-arrays
				 */
				String unsupportedTypeName = getUnsupportedTypeName();

				if (unsupportedTypeName != null && !unsupportedTypeName.isEmpty()
						&& !Objects.equals(typeName, unsupportedTypeName))
				{
					return typeName + " : " + unsupportedTypeName + "[]..[]";
				}
				else
				{
					return typeName + "[]..[]";
				}
			}

			if (SwaggerPropertyType.OBJECT.equals(type))
			{
				String finalReferencedTypeName = getFinalReferencedTypeName();

				if (finalReferencedTypeName != null && !finalReferencedTypeName.isEmpty()
						&& !Objects.equals(typeName, finalReferencedTypeName))
				{
					return typeName + " : " + finalReferencedTypeName + (isArray() ? "[]" : "");
				}
			}
			else if (!type.getLabel().equals(typeName))
			{
				return typeName + " : " + type.getLabel() + (isArray() ? "[]" : "");
			}
		}
		return typeName + (isArray() ? "[]" : "");
	}

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerTypedTreeItem#getLabelWithoutType()
	 *
	 * @return
	 */
	@Override
	public String getLabelWithoutType()
	{
		String typeName = getTypeName();

		SwaggerPropertyType type = getType(); // This is the actual type that the payload resolves to thru all
												// indirections.
		if (type != null)
		{
			if (!type.getLabel().equals(typeName))
			{
				return typeName + (isArray() ? "[]" : "");
			}
		}

		return typeName + (isArray() ? "[]" : "");
	}
}

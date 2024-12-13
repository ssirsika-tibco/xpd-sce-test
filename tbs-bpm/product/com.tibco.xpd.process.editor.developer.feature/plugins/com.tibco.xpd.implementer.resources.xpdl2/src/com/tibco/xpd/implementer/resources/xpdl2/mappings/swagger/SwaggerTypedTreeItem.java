/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import java.util.Map;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.xpdl2.Activity;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.Schema;

/**
 * A base class for any Swagger service input/output tree item that has a data type.
 *
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public abstract class SwaggerTypedTreeItem extends SwaggerMapperTreeItem
{
	/**
	 * The schema of the payload or parameter definition.
	 * 
	 * This is not necessarily the actual type schema (although maybe) because the schema property may have the type
	 * directly in it, or it may be an indirection ($ref) to another type.
	 */
	private Schema				propertySchema			= null;

	/**
	 * The actual type of the property (calculated during construction).
	 * 
	 * This is the _actual_ type of the property thru all indirections (i.e. if payload is $ref to Type1 and Type1 is
	 * defined as "type: string", then actualTypeSchema will be 'string' (well StringSchema actually)
	 */
	private Schema				actualTypeSchema		= null;

	/**
	 * If there is a $ref to another type, then this will be the type name of the LAST $ref. This will be different to
	 * the result of {@link #getReferencedTypeName()} IF the property references a type definition via $ref AND the
	 * referenced type is simply a redirection to another type. If this is the case then this field is set to the simple
	 * type name of the last redirection.
	 */
	private String				finalReferencedTypeName	= null;

	/**
	 * The {@link SwaggerPropertyType} of the tree item (calculated during construction)
	 */
	private SwaggerPropertyType	type;

	/**
	 * Whether this is an array tree item (calculated during construction)
	 */
	private boolean				isArray					= false;

	/**
	 * If for some reason we do not support the type definition (such as an array of arrays), then this is the reason
	 * for not supporting it. Will be null if property type is supported.
	 */
	private String				unsupportedTypeReason	= null;

	/**
	 * If for some reason we do not support the type definition (such as an array of arrays) and we require a different
	 * type-name for display / errors, then this is the type name used by {@link #getTypeName()}. Will be null if
	 * property type is supported.
	 */
	private String				unsupportedTypeName	= null;

	/**
	 * The activity with which this Swagger service tree item is associated
	 */
	private Activity			activity;

	/**
	 * The name of the property related to this tree item
	 */
	private String				propertyName;

	/**
	 * 
	 * @param propertySchema
	 */
	public SwaggerTypedTreeItem(Activity activity, Schema propertySchema, String propertyName)
	{
		/*
		 * Sid ACE-8885 Handling of unsupported constructs moved into SwaggerTypedTreeItem, because they could be hidden
		 * in nested type indirections.
		 * 
		 * So now we require the property schema schema to us, then once-only get figure out (a) what the acutal schema
		 * type is and (b) whether it is an array and (c) whether it is unsupported type or external reference
		 * (even it that is buried under  thru multiple levels of type indirection)
		 */
		super();
		this.propertySchema = propertySchema;
		this.activity = activity;
		this.propertyName = propertyName;

		/*
		 * Get the actual schema type - it may be buried indirectly under $ref references to type definitions.
		 * 
		 * This call will also set isArray and unsupportedTypeReason fields as appropriate
		 */
		actualTypeSchema = recursiveGetTypeSchema(propertySchema);

		if (actualTypeSchema == null)
		{
			/*
			 * If we couldn't figure out the type for some reason AND it wasn't for a known unsupported reason then add
			 * a reason now.
			 */
			if (unsupportedTypeReason == null)
			{
				type = SwaggerPropertyType.UNSUPPORTED_UNDETERMINED_TYPE;
				unsupportedTypeReason = String.format(Messages.SwaggerTypedTreeItem_CannotDeterminePropertyType_label,
						getLabel());
			}
		}
		else if (type == null)
		{
			/*
			 * Save our own SwaggerProprtyType idea of the actual schema type, if not already set during recursive
			 * schema discovery
			 */
			type = getSwaggerPropertyType(actualTypeSchema);

			if (SwaggerPropertyType.UNSUPPORTED_UNDETERMINED_TYPE.equals(type))
			{
				unsupportedTypeReason = String.format(Messages.SwaggerTypedTreeItem_CannotDeterminePropertyType_label,
						getLabel());
			}
			else if (SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE.equals(type))
			{
				unsupportedTypeReason = String.format(Messages.SwaggerPayloadTreeItem_CompositionKeywordNotSupported,
						getLabel());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Activity getActivity()
	{
		return activity;
	}

	/**
	 * This is the schema of the property NOT necessarily the data type it refers to. In some cases, like primitive
	 * types, they are the same thing. In other cases, like complex type refs, they property schema and the type-schema
	 * are different.
	 * 
	 * @return schema
	 */
	public Schema getPropertySchema()
	{
		return propertySchema;
	}

	/**
	 * Get the Type Schema for the property.
	 * 
	 * This isn't necessarily the schema of the property itself. In some cases, like primitive types, they are the same
	 * thing. In other cases, like complex type refs, they property schema and the type-schema are different.
	 *
	 * @return The schema for the property type (this is the FINAL type schema that may be indirectly referenced via
	 *         intermediate types that use $ref to simply refer to yet another type)
	 */
	public Schema getTypeSchema()
	{
		return actualTypeSchema;
	}

	/**
	 * Sid ACE-8885 Need to be recursive about reso.ving down to the final type of schema.
	 * 
	 * Get the actual schema type - it may be buried indirectly under $ref references to type definitions.
	 * 
	 * This call will also set isArray and unsupportedTypeReason and unsupportedTypeNameLabel fields as appropriate
	 * 
	 * A payload or property can be a $ref to a type that itself is JUST an indirection to another type or an array of
	 * another type, e.g.
	 * 
	 * <pre>
	 * body:
	 *   $ref: Type1
	 *   
	 * definitions:
	 *   Type1:
	 *      $ref: Type2
	 *   
	 *   Type2: 
	 *      type: array
	 *      items:
	 *  		$ref: Type3
	 *  
	 *   Type3:
	 *     type: string
	 * </pre>
	 * 
	 * So we must recurse to find the actual type at the bottom of all these indirections, ignoring the intermediate
	 * indirections. BUT we must also keep a track of whether ANY of the levels declare the type as an array AND IF we
	 * detect that more than one level of the indirections declares array THEN REJECT the entry. This is because this
	 * effectively declares AN ARRAY OF ARRAYS, which is not a data type we can currently support in Studio mapping (you
	 * can have an array of complex types that have array properties, but arrays of arrays cannot be supported because
	 * BPM process / bom data does not support this either, so there would be nothing you could map from).
	 * 
	 * IF something about the schema is unsupported, the the return schema will be <code>null</code> and the
	 * {@link #type} field will be set appropriately
	 * 
	 * @param schema
	 * @return
	 */
	private Schema recursiveGetTypeSchema(Schema schema)
	{
		Schema returnSchema = null;

		if (schema instanceof ComposedSchema || schema.getNot() != null)
		{
			/*
			 * Disallow if it's composed schema ('oneOf', 'anyOf', 'allOf', or 'not')
			 */
			type = SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE;
			unsupportedTypeReason = String.format(Messages.SwaggerPayloadTreeItem_CompositionKeywordNotSupported,
					getLabel());
		}
		else if (schema.get$ref() != null)
		{
			/*
			 * Handle $ref type configuration
			 */
			String $refType = schema.get$ref();

			if (!$refType.startsWith("#")) //$NON-NLS-1$
			{
				/*
				 * Disallow external type references, if it's an indirection thru other type (i.e. proeprty refs local
				 * type that refs external type) then include the remote type as well for clarity (otherwise it looks
				 * like we're complaining that the local type is an external ref if we only include the local ref'd type
				 * name).
				 */
				type = SwaggerPropertyType.UNSUPPORTED_EXTERNALREF;
				if (!schema.equals(propertySchema))
				{
					unsupportedTypeReason = String.format(
							Messages.SwaggerTypedTreeItem_IndirectExtRefNotSupported_label, getLabel(),
							$refType);
				}
				else
				{
					unsupportedTypeReason = String.format(Messages.SwaggerTypedTreeItem_ExtRefNotSupported_label,
							getLabel());
				}
			}
			else
			{
				/*
				 * It's a reference to another type definition within same swagger
				 */
				String refSchemaName = getReferencedTypeNameFromSchema(schema);

				finalReferencedTypeName = refSchemaName;

				Map<String, Schema> swaggerDefinitions = new RestServiceTaskAdapter()
						.getSwaggerDefinitions(getActivity());

				returnSchema = swaggerDefinitions.get(refSchemaName);
			}
		}
		else if (isArray(schema))
		{
			/*
			 * If it's an array schema, then use the items type
			 */
			if (isArray)
			{
				/*
				 * We have more than one level of the type declaration (thru indirect type defs) that declare that it is
				 * an array - that means it's an array-of-arrays and we do not support that.
				 */
				type = SwaggerPropertyType.UNSUPPORTED_ARRAY_OF_ARRAYS;

				unsupportedTypeName = getTypeNameEvenIfUnsupported(schema);

				unsupportedTypeReason = String.format(Messages.SwaggerTypedTreeItem_ArraysOfArrayTypeNotSupported_label,
						getLabel());
			}
			else
			{
				isArray = true;
				returnSchema = schema.getItems();
			}
		}
		/*
		 * Sid ACE-8885 I think there was misunderstanding over the use of 'additionalProperties' in the original
		 * implementation and you can't _really_ have a property schema defined as 'additionalProperties' because that
		 * really means 'additional unknown properties' whcih in any case we could not support with a rigid mapper UI.
		 * 
		 * So for now commenting this out - and should just ignore it.
		 */
		// else if (additionaProperties instanceof Schema)
		// {
		// typeSchema = (Schema< ? >) additionaProperties;
		// }
		else
		{
			/*
			 * Must be a single instance primitive type param/property, so the so the type schema is the property schema
			 * itself.
			 */
			returnSchema = schema;
		}

		if (returnSchema != null)
		{
			/*
			 * Check if the schema we found is just a ref or array ref to another schema (an indirection to another
			 * type) and recurs until we find the _actual_ underlying type (i.e. a primitive type OR a complex type with
			 * property declarations.
			 */
			if (returnSchema != schema) // prevent infinite recursion, just in case
			{
				String returnSchema$RefType = get$RefType(returnSchema);

				if (returnSchema instanceof ArraySchema)
				{
					if (isArray)
					{
						/*
						 * We have more than one level of the type declaration (thru indirect type defs) that declare
						 * that it is an array - that means it's an array-of-arrays and we do not support that.
						 */
						type = SwaggerPropertyType.UNSUPPORTED_ARRAY_OF_ARRAYS;

						unsupportedTypeName = getTypeNameEvenIfUnsupported(returnSchema);

						unsupportedTypeReason = String
								.format(Messages.SwaggerTypedTreeItem_ArraysOfArrayTypeNotSupported_label,
										getLabel());
					}
					else
					{
						/* Recurs thru this array indirection to another type. */
						return recursiveGetTypeSchema(returnSchema);
					}
				}
				else if (returnSchema instanceof ComposedSchema)
				{
					/* Recurs thru this array indirection a ComposedSchema. */
					return recursiveGetTypeSchema(returnSchema);

				}
				else if (returnSchema$RefType != null && !returnSchema$RefType.isEmpty())
				{
					/* Recurs thru the $ref indirection to another type. */
					return recursiveGetTypeSchema(returnSchema);
				}
			}
		}

		return returnSchema;
	}

	/**
	 * This function will try to discover the type name even if it is unsupported (possibly via multiple levels of array
	 * indirection for example).
	 * 
	 * @param schema
	 * 
	 * @return The type name to use for the given schema even if it is unsupported
	 */
	public String getTypeNameEvenIfUnsupported(Schema schema)
	{
		if (schema != null)
		{
			boolean isArray = "array".equals(schema.getType());
			if (isArray)
			{
				if (!schema.equals(schema.getItems()))
				{
					return getTypeNameEvenIfUnsupported(schema.getItems());
				}
			}

			String typeName = getReferencedTypeNameFromSchema(schema);
			if (typeName != null)
			{
				/* Array of arrays of $ref'd schema - use the schema name */
				return typeName;
			}
		}
		/* Array of arrays of primitive ttype or inline object - use the SwaggerPropertyType label */
		SwaggerPropertyType propertyType = getSwaggerPropertyType(schema);
		return propertyType.getLabel();
	}

	/**
	 * Get the type of the tree item in quantative terms (calculated during construction)
	 * 
	 * This is not the _actual_ Swagger primitive/schema type, just an enumeration that indicates the type in simple
	 * terms.
	 * 
	 * If the type is {@link SwaggerPropertyType#OBJECT}, then {@link #getTypeSchema()} can be used to ascertain the
	 * specific complex type.
	 * 
	 * @return type or null if cannot be ascertained
	 */
	public SwaggerPropertyType getType()
	{
		return type;
	}

	/**
	 * Get the type of the tree item in quantative terms for given schema
	 * 
	 * This is not the _actual_ Swagger primitive/schema type, just an enumeration that indicates the type in simple
	 * terms.
	 * 
	 * If the type is {@link SwaggerPropertyType#OBJECT}, then {@link #getTypeSchema()} can be used to ascertain the
	 * specific complex type.
	 * 
	 * @return type or null if cannot be ascertained
	 */
	private SwaggerPropertyType getSwaggerPropertyType(Schema schema)
	{

		if (schema != null)
		{
			/* If it's oneOf, anyOf, allOf or not construct then return unsupported type */
			if (schema instanceof ComposedSchema || schema.getNot() != null)
			{
				return SwaggerPropertyType.UNSUPPORTED_MIXED_TYPE;
			}

			String paramType = schema.getType();
			String paramFormat = schema.getFormat();

			if (paramType != null)
			{
				if (paramType.equals("string")) //$NON-NLS-1$
				{
					if (paramFormat != null && (paramFormat.equals("date") || paramFormat.equals("date-time"))) //$NON-NLS-1$ //$NON-NLS-2$
					{
						paramType = paramFormat;
					}
				}
				switch (paramType)
				{
					case "string": //$NON-NLS-1$
						return SwaggerPropertyType.STRING;

					case "integer": //$NON-NLS-1$
						return SwaggerPropertyType.INTEGER;

					case "number": //$NON-NLS-1$
						return SwaggerPropertyType.NUMBER;

					case "boolean": //$NON-NLS-1$
						return SwaggerPropertyType.BOOLEAN;

					case "date": //$NON-NLS-1$
						return SwaggerPropertyType.DATE;

					case "date-time": //$NON-NLS-1$
						return SwaggerPropertyType.DATETIME;

					case "object": //$NON-NLS-1$
						return SwaggerPropertyType.OBJECT;
				}
			}
			else
			{
				/*
				 * Sid ACE-8885 Thru unit testing discovered that apache API *does not default getType() to 'object'
				 * when there is just a 'properties' but no explicit 'type: object'.
				 * 
				 * So deal with that separately here...
				 */
				if (schema.getProperties() != null && !schema.getProperties().isEmpty())
				{
					return SwaggerPropertyType.OBJECT;
				}

			}
		}

		/* Sid ACE-8885 If we can't work out the type, then it's definitely unsupported. */
		return SwaggerPropertyType.UNSUPPORTED_UNDETERMINED_TYPE;
	}

	/**
	 * @param objectInTree
	 * 
	 * @return <code>true</code> if the type is known and it is a simple type
	 */
	public boolean isSimpleType()
	{
		SwaggerPropertyType type = getType();

		return (type != null && type.isSimpleType());
	}

	/**
	 * Returns the Schema Type Name referenced by the property if it uses a $ref type declaration
	 * 
	 * For locally defined types, JUST the type name is returned e.g. for $ref: "#/components/schemas/User", "User" will
	 * be returned.
	 * 
	 * For external references the whole $ref string is returned.
	 * 
	 * If it's not a property with $ref type declaration, then null is returned.
	 * 
	 * @return Local schema type name or (complete $ref for external ref) if this property has a schema reference or
	 *         <code>null</code> if not.
	 */
	public String getReferencedTypeName()
	{
		Schema propertySchema = getPropertySchema();

		return getReferencedTypeNameFromSchema(propertySchema);
	}

	/**
	 * If there is a $ref to another type, then this will be the type name of the LAST $ref. This will be different to
	 * the result of {@link #getReferencedTypeName()} IF the property references a type definition via $ref AND the
	 * referenced type is simply a redirection to another type. If this is the case then this field is set to the simple
	 * type name of the last redirection.
	 * 
	 * @return the referenced type name if the property type is defined as a $ref.
	 */
	public String getFinalReferencedTypeName()
	{
		return finalReferencedTypeName;
	}

	/**
	 * Returns the Schema Type Name referenced by the property if it uses a $ref type declaration
	 * 
	 * For locally defined types, JUST the type name is returned e.g. for $ref: "#/components/schemas/User", "User" will
	 * be returned.
	 * 
	 * For external references the whole $ref string is returned.
	 * 
	 * If it's not a property with $ref type declaration, then null is returned.
	 * 
	 * @return Local schema type name or (complete $ref for external ref) if this property has a schema reference or
	 *         <code>null</code> if not.
	 */
	private String getReferencedTypeNameFromSchema(Schema propertySchema)
	{
		Schema schema = propertySchema;

		if (schema != null)
		{

			String ref = get$RefType(schema);

			String refPrefix = "#/components/schemas/"; //$NON-NLS-1$

			if (ref != null)
			{
				return ref.startsWith(refPrefix) ? ref.substring(refPrefix.length()) : ref;
			}
			
		}
		return null;
	}

	/**
	 * Get the reference schema name (as is defined in swagger definition) if the schema is a reference to another type
	 * schema
	 * 
	 * @param schema
	 * 
	 * @return The referenced schema name if the schema's type is a $ref (or array of $ref items) or <code>null</code>
	 *         if not a $ref reference
	 */
	private String get$RefType(Schema schema)
	{
		if (schema != null)
		{
			String ref = null;
			if ("array".equals(schema.getType())) //$NON-NLS-1$
			{
				if (schema.getItems() != null)
				{
					ref = schema.getItems().get$ref();
				}
			}
			else
			{
				ref = schema.get$ref();
			}

			return ref;
		}
		return null;
	}

	/**
	 * 
	 * @return <code>true</code> if the parameter is an array type
	 */
	public boolean isArray()
	{
		return isArray; // Calculated on construction
	}

	/**
	 * Check whether a particular prop[erty or type schema is an array type
	 * 
	 * @param paramSchema
	 * 
	 * @return <code>true</code> if the schema is an array type
	 */
	private boolean isArray(Schema paramSchema)
	{
		if (paramSchema != null)
		{
			if ("array".equals(paramSchema.getType())) //$NON-NLS-1$
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Converts a Swagger type name.
	 * 
	 * @param paramType
	 *            The RSD dataType.
	 * @return The matching BOM type name.
	 */
	@SuppressWarnings("nls")
	public String getTypeName()
	{
		SwaggerPropertyType type = getType();

		if (type != null)
		{
			/*
			 * Sid ACE-8885 If it's a referenced type (even if SwaggerProeprtyType.UNSUPPORTED) then use the referenced
			 * type name if there is one.
			 */
			String referenceTypeName = getReferencedTypeName();

			if (referenceTypeName != null && !referenceTypeName.isEmpty()) {
				return referenceTypeName;
			} else {
				return type.getLabel();
			}
			
		}
		return "???"; //$NON-NLS-1$
	}

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem#getText()
	 *
	 * @return
	 */
	@Override
	public String getText()
	{
		return propertyName;
	}

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerMapperTreeItem#getLabel()
	 *
	 * @return
	 */
	@Override
	public String getLabel()
	{
		/* The is the name of the type directly defined in the property. */
		String typeName = getTypeNameEvenIfUnsupported(getPropertySchema());

		String text = getText();

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
					typeName = typeName + " : oneOf";
				}
				else if (composedSchema.getAnyOf() != null)
				{
					typeName = typeName + " : anyOf";
				}
				else if (composedSchema.getAllOf() != null)
				{
					typeName = typeName + " : allOf";
				}
				else if (composedSchema.getNot() != null)
				{
					typeName = typeName + " : not";
				}
			}
		}
		else if (SwaggerPropertyType.UNSUPPORTED_ARRAY_OF_ARRAYS.equals(getType()))
		{
			/* Use suffix of []..[] on arrays of arrays, as we only know there are least one level of array-arrays */
			typeName = typeName + "[]..";
		}

		if (text == null || text.isEmpty())
		{
			return typeName + (isArray() ? "[]" : ""); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else
		{
			return getText() + " : " + typeName + (isArray() ? "[]" : ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}

	
	/**
	 * Get the item label without a type suffix (as is done by getLabel() above
	 * 
	 * @return the item label without a type suffix
	 */
	public String getLabelWithoutType()
	{
		String text = getText();

		if (text == null || text.isEmpty())
		{
			return getTypeName() + (isArray() ? "[]" : "");
		}

		return getText() + (isArray() ? "[]" : "");
	}

	/**
	 * Return the max length from schema.
	 * 
	 * @return {@link Integer} max length
	 */
	public Integer getMaxLength() {
		if (getTypeSchema() != null)
		{
			return getTypeSchema().getMaxLength();
		}

		return null;
	}

	/**
	 * IF the type referenced by the property is unsupported for some reason, return the textual description of the
	 * reason
	 * 
	 * @return the unsupportedTypeReason or <code>null</code> if the property type is supported.
	 */
	public String getUnsupportedTypeReason()
	{
		return unsupportedTypeReason;
	}

	/**
	 * @return the unsupportedTypeName
	 */
	public String getUnsupportedTypeName()
	{
		return unsupportedTypeName;
	}
}

/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.bds.designtime.api.validation;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bpm.cdm.libs.dql.model.DataFieldProvider;
import com.tibco.bpm.cdm.libs.dql.model.ModelAbstractType;
import com.tibco.bpm.cdm.libs.dql.model.ModelAttribute;
import com.tibco.bpm.cdm.libs.dql.model.ModelBaseType;
import com.tibco.bpm.da.dm.api.Constraint;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * DQL parser {@link ModelAttribute} wrapper for BOM Property
 *
 * @author aallway
 * @since 5 Aug 2024
 */
@SuppressWarnings("nls")
public class DQLPropertyWrapper implements ModelAttribute
{
	private Property				property;

	private DQLClassPropertyWrapper	parentClass;

	private DataFieldProvider		dataFieldProvider;

	/**
	 * Create a DQL parser {@link ModelAttribute} wrapper for BOM Property
	 * 
	 * @param property
	 *            The BOM property to wrap
	 * @param parentClass
	 *            The {@link DQLClassPropertyWrapper} for the BOM class for the given property
	 * @param dataFieldProvider
	 */
	public DQLPropertyWrapper(Property property, DQLClassPropertyWrapper parentClass,
			DataFieldProvider dataFieldProvider)
	{
		super();
		this.property = property;
		this.parentClass = parentClass;
		this.dataFieldProvider = dataFieldProvider;
	}

	/**
	 * @see com.tibco.bpm.cdm.libs.dql.model.ModelAttribute#getConstraint(java.lang.String)
	 *
	 * @param constraintName
	 * @return
	 */
	@Override
	public String getConstraint(String constraintName)
	{
		if (property.getType() instanceof PrimitiveType)
		{
			PrimitiveType primitiveType = (PrimitiveType) property.getType();

			if (Constraint.NAME_LENGTH.equals(constraintName))
			{
				Object length = PrimitivesUtil.getFacetPropertyValue(primitiveType,
						PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH, property, true);
				if (length instanceof Integer)
				{
					return length.toString();
				}
			}
			else if (Constraint.NAME_DECIMAL_PLACES.equals(constraintName))
			{
				Object decimalPlaces = PrimitivesUtil.getFacetPropertyValue(primitiveType,
						PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES, property, true);
				if (decimalPlaces instanceof Integer)
				{
					decimalPlaces.toString();
				}
			}
		}
		return null;
	}

	/**
	 * @see com.tibco.bpm.cdm.libs.dql.model.ModelAttribute#getName()
	 *
	 * @return
	 */
	@Override
	public String getName()
	{
		return property.getName();
	}

	/**
	 * @see com.tibco.bpm.cdm.libs.dql.model.ModelAttribute#getQualifiedName()
	 *
	 * @return
	 */
	@Override
	public String getQualifiedName()
	{
		String parentPath = null;

		if (parentClass != null)
		{
			parentPath = parentClass.getPropertyPath();
		}

		if (parentPath != null)
		{
			return parentPath + "." + getName();
		}

		return getName();
	}

	/**
	 * @see com.tibco.bpm.cdm.libs.dql.model.ModelAttribute#getType()
	 *
	 * @return
	 */
	@Override
	public ModelAbstractType getType()
	{
		Type type = property.getType();

		if (type instanceof PrimitiveType)
		{
			PrimitiveType baseType = PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);

			switch (baseType.getName())
			{
				case PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME:
					return ModelBaseType.TEXT;

				case PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME:
					if (PrimitivesUtil.isFixedPointDecimal(property))
					{
						return ModelBaseType.FIXED_POINT_NUMBER;
					}
					else
					{
						return ModelBaseType.NUMBER;
					}

				case PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME:
					return ModelBaseType.FIXED_POINT_NUMBER;

				case PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME:
					return ModelBaseType.BOOLEAN;

				case PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME:
					return ModelBaseType.DATE;

				case PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME:
					return ModelBaseType.TIME;

				case PrimitivesUtil.BOM_PRIMITIVE_ENUMERATION_NAME:
					return ModelBaseType.TEXT;

				case PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME:
					return ModelBaseType.DATE_TIME_TZ;

				case PrimitivesUtil.BOM_PRIMITIVE_URI_NAME:
					return ModelBaseType.URI;

				default:
					return ModelBaseType.TEXT;
			}

		}
		else if (type instanceof Class)
		{
			return new DQLClassPropertyWrapper((Class) type, getQualifiedName(), dataFieldProvider);
		}
		else if (type instanceof Enumeration)
		{
			return ModelBaseType.TEXT;
		}

		return null;
	}

	/**
	 * @see com.tibco.bpm.cdm.libs.dql.model.ModelAttribute#isArray()
	 *
	 * @return
	 */
	@Override
	public boolean isArray()
	{
		return PrimitivesUtil.isArray(property);
	}

	/**
	 * @see com.tibco.bpm.cdm.libs.dql.model.ModelAttribute#isSearchable()
	 *
	 * @return
	 */
	@Override
	public boolean isSearchable()
	{
		return BOMGlobalDataUtils.isSearchable(property);
	}

	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@Override
	public String toString()
	{
		return getQualifiedName() + (isArray() ? "[]" : "") + "::" + getType().getName();
	}
}

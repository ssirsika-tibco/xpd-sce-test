/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.bx.validation.internal.validator;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.bpm.cdm.libs.dql.model.DataFieldProvider;
import com.tibco.bpm.cdm.libs.dql.model.ModelAbstractType;
import com.tibco.bpm.cdm.libs.dql.model.ModelAttribute;
import com.tibco.bpm.cdm.libs.dql.model.ModelBaseType;
import com.tibco.bpm.da.dm.api.Constraint;
import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Scale;

/**
 * Data Field Provider for DQL Query parsing.
 * 
 * Is used to resolve the data field/property path defined in DQL Query statements in ${data.field.property} tokens.
 *
 * @author aallway
 * @since 12 Sep 2024
 */
public class DQLDataFieldProvider implements DataFieldProvider
{
	private Process process;

	/**
	 * @param process
	 */
	public DQLDataFieldProvider(Process process)
	{
		super();
		this.process = process;
	}

	/**
	 * @see com.tibco.bpm.cdm.libs.dql.model.DataFieldProvider#getDataField(java.lang.String)
	 *
	 * @param var1
	 * @return
	 */
	@Override
	public ModelAttribute getDataField(String parameterPath)
	{
		String dataWrapperPrefix = ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + "."; //$NON-NLS-1$

		if (parameterPath != null && parameterPath.startsWith(dataWrapperPrefix))
		{
			ConceptPath conceptPath = ConceptUtil.resolveConceptPath(process,
					parameterPath.substring(dataWrapperPrefix.length()));

			if (conceptPath != null)
			{
				return conceptPathToDQLModelAttribute(conceptPath);
			}
		}
		return null;
	}

	/**
	 * Convert the given field/attribute to a concept path
	 * 
	 * @param conceptPath
	 * 
	 * @return ModelAttribute for the given field/attribute
	 */
	private ModelAttribute conceptPathToDQLModelAttribute(ConceptPath conceptPath)
	{
		return new ConceptPathDQLModelAttribute(conceptPath);
	}

	/**
	 * Provide a DQL {@link ModelAttribute} implementation based on data fields / BOM properties.
	 *
	 *
	 * @author aallway
	 * @since 13 Sep 2024
	 */
	private static class ConceptPathDQLModelAttribute implements ModelAttribute
	{
		private ConceptPath conceptPath;

		private String		referenceName;

		/**
		 * @param conceptPath
		 */
		public ConceptPathDQLModelAttribute(ConceptPath conceptPath)
		{
			super();
			this.conceptPath = conceptPath;
		}

		/**
		 * @see com.tibco.bpm.cdm.libs.dql.model.ModelAttribute#isSearchable()
		 *
		 * @return
		 */
		@Override
		public boolean isSearchable()
		{
			return false;
		}

		/**
		 * @see com.tibco.bpm.cdm.libs.dql.model.ModelAttribute#isArray()
		 *
		 * @return
		 */
		@Override
		public boolean isArray()
		{
			return conceptPath.isArray();
		}

		/**
		 * @see com.tibco.bpm.cdm.libs.dql.model.ModelAttribute#getType()
		 *
		 * @return
		 */
		@Override
		public ModelAbstractType getType()
		{
			/*
			 * Convert from ConceptPath Item (DataField or BOM Property to DQL Parser's ModelAttribute type scheme.
			 * 
			 * We only bother with primitive types because complex types can't be used for anything useful in DQL Query
			 * comparisons etc
			 */
			Object conceptItem = conceptPath.getItem();

			BasicType basicType = BasicTypeConverterFactory.INSTANCE.getBasicType(conceptItem);

			if (basicType != null)
			{
				BasicTypeType type = basicType.getType();

				if (BasicTypeType.STRING_LITERAL.equals(type))
				{
					return ModelBaseType.TEXT;
				}
				else if (BasicTypeType.BOOLEAN_LITERAL.equals(type))
				{
					return ModelBaseType.BOOLEAN;
				}
				else if (BasicTypeType.DATE_LITERAL.equals(type))
				{
					return ModelBaseType.DATE;
				}
				else if (BasicTypeType.DATETIME_LITERAL.equals(type))
				{
					return ModelBaseType.DATE_TIME_TZ;
				}
				else if (BasicTypeType.TIME_LITERAL.equals(type))
				{
					return ModelBaseType.TIME;
				}
				else if (BasicTypeType.INTEGER_LITERAL.equals(type))
				{
					return ModelBaseType.FIXED_POINT_NUMBER;
				}
				/* Need to distinguish between floating and fixed point numbers as DQL type check is fussy */
				else if (BasicTypeType.FLOAT_LITERAL.equals(type))
				{
					if (conceptItem instanceof Property)
					{
						if (PrimitivesUtil.isFixedPointDecimal((Property) conceptItem))
						{
							return ModelBaseType.FIXED_POINT_NUMBER;
						}
						else
						{
							return ModelBaseType.NUMBER;
						}

					}
					else if (conceptItem instanceof ProcessRelevantData)
					{
						Scale scale = basicType.getScale();
						if (scale != null)
						{
							return ModelBaseType.FIXED_POINT_NUMBER;
						}
						else
						{
							return ModelBaseType.NUMBER;
						}
					}
				}
			}

			/* Special case for URI attributes as we don't have equivalent basic type. */
			if (conceptItem instanceof Property)
			{
				Property conceptProperty = (Property) conceptItem;

				Classifier type = conceptPath.getType();

				if (type instanceof PrimitiveType)
				{
					PrimitiveType baseType = PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);

					if (PrimitivesUtil.BOM_PRIMITIVE_URI_NAME.equals(baseType.getName()))
					{
						return ModelBaseType.URI;
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
			return conceptPath.getName();
		}

		/**
		 * @see com.tibco.bpm.cdm.libs.dql.model.ModelAttribute#getQualifiedName()
		 *
		 * @return
		 */
		@Override
		public String getQualifiedName()
		{
			return conceptPath.getPath();
		}

		/**
		 * @see com.tibco.bpm.cdm.libs.dql.model.ModelAttribute#getConstraint(java.lang.String)
		 *
		 * @param var1
		 * @return
		 */
		@Override
		public String getConstraint(String constraintName)
		{
			if (Constraint.NAME_DECIMAL_PLACES.equals(constraintName))
			{
				if (ModelBaseType.FIXED_POINT_NUMBER.equals(getType()))
				{
					Object conceptItem = conceptPath.getItem();

					BasicType basicType = BasicTypeConverterFactory.INSTANCE.getBasicType(conceptItem);

					if (basicType != null)
					{
						Scale scale = basicType.getScale();

						if (scale != null)
						{
							short decimalPlaces = scale.getValue();

							return Integer.toString(decimalPlaces);
						}
					}
				}
			}
			return null;
		}

		/**
		 * @return The referenceName that the consumer previous set using {@link #setReferenceName(String)}, or
		 *         <code>null</code> if that was not done.
		 */
		@Override
		public String getReferenceName()
		{
			return referenceName;
		}

		/**
		 * This function takes and stores any reference name that the consumer wishes to set on the property. it can be
		 * retrieved again later using {@link #getReferenceName()}
		 *
		 * @param referenceName
		 */
		@Override
		public void setReferenceName(String referenceName)
		{
			this.referenceName = referenceName;
		}
	}
}

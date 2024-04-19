/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.config;

import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.xpdExtension.FieldFormat;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Enum to represent the allowed function parameter type categories.
 *
 * @author ssirsika
 * @since 11-Apr-2024
 */
public enum ProcessScriptFunctionParamCategories
{
	TEXT("Text"), //$NON-NLS-1$
	FIXED_POINT_NUMBER("Fixed_Point_Number"), //$NON-NLS-1$
	FLOATING_POINT_NUMBER("Floating_Point_Number"), //$NON-NLS-1$
	INTEGER("Integer"), //$NON-NLS-1$
	BOOLEAN("Boolean"), //$NON-NLS-1$
	DATE("Date"), //$NON-NLS-1$
	TIME("Time"), //$NON-NLS-1$
	DATE_TIME_TIMEZONE("Date_Time_and_Timezone"), //$NON-NLS-1$
	PERFORMER("Performer"), //$NON-NLS-1$
	URI("URI"), //$NON-NLS-1$
	BOM_CLASS("Class"), //$NON-NLS-1$
	BOM_CASE_REF("Case_Ref"), //$NON-NLS-1$
	UNRESOVED_REFERENCE("Unresolved_reference"); //$NON-NLS-1$

	private String constantStr;

	/**
	 * @param aConstantStr
	 */
	ProcessScriptFunctionParamCategories(String aConstantStr)
	{
		this.constantStr = aConstantStr;
	}

	/**
	 * Looks up an enum constant by its string constant. This method finds the corresponding enum constant for a
	 * supplied string.
	 *
	 * @param aConstantStr
	 *            Constant string internally representing the enum.
	 * @return The enum constant associated with the given string, or null if no match is found.
	 */
	public static ProcessScriptFunctionParamCategories valueOfConstant(String aConstantStr)
	{
		for (ProcessScriptFunctionParamCategories c : values())
		{
			if (c.toConstantString().equals(aConstantStr))
			{
				return c;
			}
		}
		return null;
	}

	/**
	 * Return the internal constant string representing the enum.
	 */
	public String toConstantString()
	{
		return constantStr;
	}

	/**
	 * Determines the corresponding {@link ProcessScriptFunctionParamCategories} category based on the provided data
	 *
	 *
	 * @param aDataType
	 *            The data type of the field
	 * @param aBaseType
	 *            The base object type
	 * @return The matched {@link ProcessScriptFunctionParamCategories} enumeration value that represents the type of
	 *         the field. Returns {@link ProcessScriptFunctionParamCategories#UNRESOVED_REFERENCE} if no specific match
	 *         is found.
	 *
	 */
	public static ProcessScriptFunctionParamCategories fromType(DataType aDataType, Object aBaseType)
	{
		if (aBaseType instanceof BasicType)
		{
			BasicType basicType = (BasicType) aBaseType;
			BasicTypeType basicTypeType = basicType.getType();
			switch (basicTypeType)
			{
				case STRING_LITERAL:
					// Check field format for URI type
					Object fieldFormat = Xpdl2ModelUtil.getOtherAttribute(basicType,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_FieldFormat());
					
					if (FieldFormat.URI.equals(fieldFormat))
					{
						return URI;
					}
					return TEXT;

				case FLOAT_LITERAL:
					if (basicType.getScale() != null)
					{
						return FIXED_POINT_NUMBER;
					}
					else
					{
						return FLOATING_POINT_NUMBER;
					}
				case INTEGER_LITERAL:
					return INTEGER;
				case DATE_LITERAL:
					return DATE;
				case TIME_LITERAL:
					return TIME;
				case DATETIME_LITERAL:
					return TIME;
				case BOOLEAN_LITERAL:
					return BOOLEAN;
				case PERFORMER_LITERAL:
					return PERFORMER;
				default:
					return UNRESOVED_REFERENCE;
			}
		}
		// BOM class reference
		else if (aBaseType instanceof Classifier)
		{
			if (aDataType instanceof ExternalReference)
			{
				return BOM_CLASS;
			}
			else if (aDataType instanceof RecordType)
			{
				return BOM_CASE_REF;
			}
		}
		return UNRESOVED_REFERENCE;
	}
}

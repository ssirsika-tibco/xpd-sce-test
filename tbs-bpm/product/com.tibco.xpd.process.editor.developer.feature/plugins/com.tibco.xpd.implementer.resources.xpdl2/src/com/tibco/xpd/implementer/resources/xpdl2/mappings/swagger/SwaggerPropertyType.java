/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.rsd.ui.components.columns.ParamDataTypeColumn.RestDataTypeUtil;

/**
 * Enumeration of the types for Swagger Parameters (path/query/header) and properties
 *
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public enum SwaggerPropertyType
{
	STRING(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME, true),
	INTEGER(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME, true),
	NUMBER(RestDataTypeUtil.REST_NUMBER_DATA_TYPE_LABEL, true),
	DATE(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME, true),
	DATETIME(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME, true),
	BOOLEAN(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME, true),
	OBJECT("Object", false),
	/**
	 * Sid ACE-8885 Handling of unsupported constructs moved into SwaggerTypedTreeItem, because they could be hidden in
	 * nested type indirections.
	 * 
	 * Reserved for data types we completely do not support under any circumstance (array of arrays for e.g.)
	 */
	UNSUPPORTED_ARRAY_OF_ARRAYS("Array of arrays (unsupported)", false, true),
	UNSUPPORTED_EXTERNALREF("External type (unsupported)", false, true),
	UNSUPPORTED_MIXED_TYPE("Mixed type (unsupported)", false, true),
	UNSUPPORTED_UNDETERMINED_TYPE("Undetermined type", false, true);
	
	private final String	label;
	
	private final boolean	isSimpleType;

	private boolean			isUnsupported;
	
	private SwaggerPropertyType(String label, boolean isSimpleType)
	{
		this(label, isSimpleType, false);
	}

	private SwaggerPropertyType(String label, boolean isSimpleType, boolean isUnsupported)
	{
		this.label = label;
		this.isSimpleType = isSimpleType;
		this.isUnsupported = isUnsupported;
	}

	/**
	 * @return the label
	 */
	public String getLabel()
	{
		return label;
	}
	
	/**
	 * @return <code>true</code> if the data type is a simple 'primitive' type (like text, integer etc).
	 */
	public boolean isSimpleType()
	{
		return isSimpleType;
	}

	/**
	 * @return the isUnsupported
	 */
	public boolean isUnsupported()
	{
		return isUnsupported;
	}

}

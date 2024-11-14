/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import java.util.Collections;
import java.util.List;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.resources.util.XpdUtil;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.xpdl2.Activity;

import io.swagger.v3.oas.models.media.Schema;

/**
 * Mapper tree item representing Swagger parameters used in REST service call.
 *
 * @author nkelkar
 * @since Sep 4, 2024
 */
public abstract class SwaggerParamTreeItem extends SwaggerTypedTreeItem
{

	private final SwaggerMapperTreeItem	parent;

	private final MappingDirection		direction;

	private ParameterStyle				paramStyle;

	/**
	 * Creates mapper tree item for a Swagger parameter.
	 * 
	 * @param parent
	 * @param param
	 * @param name
	 * @param activity
	 * @param direction
	 * @param paramStyle
	 *            The type of parameter (path/header/query)
	 */
	SwaggerParamTreeItem(SwaggerMapperTreeItem parent, String name, Activity activity, MappingDirection direction,
			ParameterStyle paramStyle, Schema paramSchema)
	{
		super(activity, paramSchema, name);

		this.parent = parent;
		/* Sid ACE-8885 'name' pulled up into SwaggerTypedTreeItem (so that it can be used during construction). */
		this.direction = direction;
		this.paramStyle = paramStyle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SwaggerMapperTreeItem getParent()
	{
		return parent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SwaggerMapperTreeItem> createChildren()
	{
		return Collections.emptyList();
	}

	/**
	 *  @return The parameter path prefix (the leading text before the original parameter name from swagger).
	 */
	public abstract String getParamPrefix();

	/**
	 * Returns the parameter type (path/query/header)
	 * 
	 * @return The REST parameter type
	 */
	public ParameterStyle getParamStyle()
	{
		return paramStyle;
	}

	/**
	 * 
	 * @return <code>true</code> if the parameter is an array type
	 */
	public abstract boolean isRequired();


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
		result = prime * result + ((paramStyle == null) ? 0 : paramStyle.hashCode());
		result = prime * result + ((getParent() == null) ? 0 : getParent().hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		SwaggerParamTreeItem other = (SwaggerParamTreeItem) obj;

		if (!XpdUtil.safeEquals(direction, other.direction)) return false;
		if (!XpdUtil.safeEquals(getText(), other.getText())) return false;
		if (!XpdUtil.safeEquals(paramStyle, other.paramStyle)) return false;
		if (!XpdUtil.safeEquals(getParent(), other.getParent())) return false;
		return true;
	}


}
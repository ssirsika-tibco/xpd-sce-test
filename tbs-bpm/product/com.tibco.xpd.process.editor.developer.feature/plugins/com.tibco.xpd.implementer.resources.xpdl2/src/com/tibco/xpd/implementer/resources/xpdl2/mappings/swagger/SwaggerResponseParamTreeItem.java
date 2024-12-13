/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMappingPrefix;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdl2.Activity;

import io.swagger.v3.oas.models.headers.Header;

/**
 * Object that represents (and wraps) a Swagger service output parameter, in other words a Response Header parameter.
 *
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public final class SwaggerResponseParamTreeItem extends SwaggerParamTreeItem
{
	private final Header	header;

	private String			headerName;

	private String			statusCode;

	/**
	 * Construct wraper for a Swagger response header parameter
	 * 
	 * @param parent
	 *            Parent container
	 * @param header
	 *            The apache Swagger API Header object.
	 * @param headerName
	 *            The name of the parameter
	 * @param activity
	 * @param direction
	 * @param paramStyle
	 */
	public SwaggerResponseParamTreeItem(SwaggerMapperTreeItem parent, Header header, String headerName,
			Activity activity, String statusCode)
	{
		super(parent, headerName, activity, MappingDirection.OUT, ParameterStyle.HEADER, header.getSchema());

		assert header != null;
		this.header = header;

		this.headerName = headerName;
		this.statusCode = statusCode;

	}

	/**
	 * @return the underlying Swagger API param object
	 */
	public Header getHeader()
	{
		return header;
	}

	/**
	 * 
	 * @return <code>true</code> if the parameter is an array type
	 */
	@Override
	public boolean isRequired()
	{
		return false; /* Output params can't really be required. */
	}

	/**
	 * 
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerParamTreeItem#getPath()
	 *
	 * @return
	 */
	@Override
	public String getPath()
	{
		String path = getParamPrefix();
		path += headerName;

		/**
		 * JS script variable names params need to have special characters removed
		 * 
		 * Also, just replacing hyphen with underscore is a little weak, so we will do that (as we always used to for
		 * header params) AND then remove anything that is not alphanumeric or underscore - that should cleanse
		 * everything else that will be an issue for JS script variables.
		 */
		path = path.replace('-', '_');

		path = NameUtil.getInternalName(path, true);

		return path;
	}

	/**
	 * 
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerParamTreeItem#getParamPrefix()
	 *
	 * @return
	 */
	@Override
	public String getParamPrefix()
	{
		return RestMappingPrefix.HEADER_PARAM.addPrefix(statusCode + "_");
	}

}

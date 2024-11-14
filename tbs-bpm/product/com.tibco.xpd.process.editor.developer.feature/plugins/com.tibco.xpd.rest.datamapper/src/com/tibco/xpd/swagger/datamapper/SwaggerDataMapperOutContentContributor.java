/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.swagger.datamapper;

import com.tibco.xpd.mapper.MappingDirection;

/**
 * Data Mapper Content contributor for Swagger Service items.
 *
 * @author nkelkar
 * @since Sep 2, 2024
 */
public class SwaggerDataMapperOutContentContributor extends AbstractSwaggerDataMapperContentContributor
{

	/**
	 * @param direction
	 */
	public SwaggerDataMapperOutContentContributor()
	{
		super(MappingDirection.OUT);
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
	 * 
	 * @return
	 */
	@Override
	public String getContributorId()
	{
		return "SwaggerToProcess.DataMapperContent"; //$NON-NLS-1$
	}

}

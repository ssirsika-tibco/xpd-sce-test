/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.swagger.datamapper;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerMapperLabelProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.SwaggerTaskItemProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptableLabelProvider;

/**
 * Data Mapper Content contributor for Swagger items.
 *
 * @author nkelkar
 * @since Sep 3, 2024
 */
public abstract class AbstractSwaggerDataMapperContentContributor extends AbstractDataMapperContentContributor
{
	private MappingDirection direction;

	/**
	 * 
	 * @param direction
	 */
	public AbstractSwaggerDataMapperContentContributor(MappingDirection direction)
	{
		this.direction = direction;
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#createInfoProvider()
	 * 
	 * @return
	 */
	@Override
	public AbstractDataMapperInfoProvider createInfoProvider()
	{
		ITreeContentProvider contentProvider = new SwaggerTaskItemProvider(direction);
		ILabelProvider labelProvider = new ScriptableLabelProvider(new SwaggerMapperLabelProvider());
		return new SwaggerDataMapperInfoProvider(direction, contentProvider, labelProvider, false);
	}

}

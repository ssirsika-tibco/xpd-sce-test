/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.swagger.datamapper;

/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rest.datamapper.AbstractRestDataMapperSection;
import com.tibco.xpd.rest.datamapper.RestDataMapperConstants;
import com.tibco.xpd.rest.datamapper.internal.Messages;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;

/**
 * Data Mapper section for mappings from Swagger to process data.
 * 
 * @author nkelkar
 * @since Aug 30, 2024
 */
public class SwaggerDataMapperOutSection extends AbstractRestDataMapperSection
{

    /**
     * @param direction
     */
    public SwaggerDataMapperOutSection() {
        super(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
		return RestDataMapperConstants.SWAGGER_TO_PROCESS;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
		return Messages.SwaggerDataMapperOutSection_SwaggerOutputMappingTitle_message;
    }

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getScriptSection()
	 * 
	 * @return a new RestMapperScriptProperties.
	 */
	@Override
	protected BaseScriptSection getScriptSection()
	{
		return new SwaggerMapperScriptProperties(getDirection(), getScriptDataMapperProvider());
	}

}

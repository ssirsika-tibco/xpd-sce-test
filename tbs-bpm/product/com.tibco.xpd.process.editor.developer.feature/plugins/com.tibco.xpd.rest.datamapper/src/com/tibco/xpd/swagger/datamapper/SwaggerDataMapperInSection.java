/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */
package com.tibco.xpd.swagger.datamapper;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rest.datamapper.AbstractRestDataMapperSection;
import com.tibco.xpd.rest.datamapper.RestDataMapperConstants;
import com.tibco.xpd.rest.datamapper.internal.Messages;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;

/**
 * Data Mapper section for mappings from process data to Swagger
 * 
 * @author nkelkar
 * @since Aug 30, 2024
 */
public class SwaggerDataMapperInSection extends AbstractRestDataMapperSection
{

    /**
     * @param direction
     */
    public SwaggerDataMapperInSection() {
        super(MappingDirection.IN);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
		return RestDataMapperConstants.PROCESS_TO_SWAGGER;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
		return Messages.SwaggerDataMapperInSection_SwaggerInputMappingTitle_message;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#isCreatingTargetData()
     *
     * @return
     */
    @Override
    protected boolean isCreatingTargetData() {
        /* REST input data is brand new and created from scratch */
        return true;
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

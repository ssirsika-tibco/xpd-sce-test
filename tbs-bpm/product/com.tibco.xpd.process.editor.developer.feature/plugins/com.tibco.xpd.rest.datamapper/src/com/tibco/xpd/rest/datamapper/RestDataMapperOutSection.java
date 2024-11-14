/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rest.datamapper.internal.Messages;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;

/**
 * Data Mapper section for mappings from a REST Service to process data.
 * 
 * @author nwilson
 * @since 30 Apr 2015
 */
public class RestDataMapperOutSection extends AbstractRestDataMapperSection {

    /**
     * @param direction
     */
    public RestDataMapperOutSection() {
        super(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return RestDataMapperConstants.REST_SERVICE_TO_PROCESS;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.RestDataMapperOutSection_RestOutputMappingTitle_message;
    }

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getScriptSection()
	 * 
	 * @return a new RestMapperScriptProperties.
	 */
	@Override
	protected BaseScriptSection getScriptSection()
	{
		return new RestMapperScriptProperties(getDirection());
	}
}

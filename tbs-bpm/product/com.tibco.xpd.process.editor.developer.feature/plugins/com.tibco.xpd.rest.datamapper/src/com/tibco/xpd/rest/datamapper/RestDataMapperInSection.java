/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rest.datamapper.internal.Messages;

/**
 * Data Mapper section for mappings from process data to a REST Service.
 * 
 * @author nwilson
 * @since 30 Apr 2015
 */
public class RestDataMapperInSection extends AbstractRestDataMapperSection {

    /**
     * @param direction
     */
    public RestDataMapperInSection() {
        super(MappingDirection.IN);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return RestDataMapperConstants.PROCESS_TO_REST_SERVICE;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.RestDataMapperInSection_RestInputMappingTitle_message;
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
}

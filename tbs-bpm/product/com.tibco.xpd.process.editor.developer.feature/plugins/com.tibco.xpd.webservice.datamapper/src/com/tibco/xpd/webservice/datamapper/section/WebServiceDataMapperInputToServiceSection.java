/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.webservice.datamapper.WebServiceDataMapperConstants;
import com.tibco.xpd.webservice.datamapper.internal.Messages;

/**
 * Data mapper section for "Input to Service" mappings of Web-Service
 * activities.
 * 
 * @author ssirsika
 * @since 20-Jan-2016
 */
public class WebServiceDataMapperInputToServiceSection extends
        AbstractWebServiceDataMapperSection {

    /**
     * @param direction
     */
    public WebServiceDataMapperInputToServiceSection() {
        super(MappingDirection.IN);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return WebServiceDataMapperConstants.INPUT_TO_SERVICE;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.WSDataMapperInSection_InputMappingTitle_message;
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section;

import com.tibco.xpd.webservice.datamapper.WebServiceDataMapperConstants;

/**
 * Data mapper section for "Input to Process" mappings of Web-Service
 * activities.
 * 
 * @author sajain
 * @since Jan 27, 2016
 */
public class WebServiceDataMapperInputToProcessSection extends
        WebServiceDataMapperOutputFromServiceSection {

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return WebServiceDataMapperConstants.INPUT_TO_PROCESS;
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section;

import com.tibco.xpd.webservice.datamapper.WebServiceDataMapperConstants;

/**
 * Data mapper section for "Output from Process" mappings of Web-Service
 * activities.
 * 
 * @author sajain
 * @since Jan 27, 2016
 */
public class WebServiceDataMapperOutputFromProcessSection extends
        WebServiceDataMapperInputToServiceSection {

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return WebServiceDataMapperConstants.OUTPUT_FROM_PROCESS;
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section;

import com.tibco.xpd.webservice.datamapper.WebServiceDataMapperConstants;
import com.tibco.xpd.webservice.datamapper.internal.Messages;

/**
 * 
 * Data mapper section for "Output Fault from Process" mappings of Web-Service
 * activities.
 * 
 * @author ssirsika
 * @since 03-Feb-2016
 */
public class WebServiceDataMapperOutputFaultFromProcessSection extends
        WebServiceDataMapperInputToServiceSection {

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return WebServiceDataMapperConstants.OUTPUT_FAULT_FROM_PROCESS;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.WebServiceDataMapperOutputFaultFromProcessSection_WSDataMapperInSection_ProcessDataToWSFaultSectionTitle_message;
    }

}

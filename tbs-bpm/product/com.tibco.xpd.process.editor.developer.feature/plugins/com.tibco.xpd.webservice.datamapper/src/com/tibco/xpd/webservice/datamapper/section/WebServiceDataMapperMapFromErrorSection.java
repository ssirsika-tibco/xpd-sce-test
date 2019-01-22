/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section;

import com.tibco.xpd.catcherror.datamapper.CatchErrorScriptDataMapperProvider;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.webservice.datamapper.WebServiceDataMapperConstants;
import com.tibco.xpd.webservice.datamapper.internal.Messages;

/**
 * 'Map From Error' section to map the Web Service Fault with process data.
 * 
 * @author ssirsika
 * @since 08-Mar-2016
 */
public class WebServiceDataMapperMapFromErrorSection extends
        AbstractWebServiceDataMapperSection {

    /**
     * @param direction
     */
    public WebServiceDataMapperMapFromErrorSection() {
        super(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return WebServiceDataMapperConstants.WEB_SERVICE_FAULT_CATCH;
    }

    /**
     * @see com.tibco.xpd.webservice.datamapper.section.AbstractWebServiceDataMapperSection#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected AbstractScriptDataMapperEditorProvider getScriptDataMapperProvider() {
        return new CatchErrorScriptDataMapperProvider(getDataMapperContext());
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.WebServiceDataMapperMapFromErrorSection_MapFromErrorSection_Label;
    }

}

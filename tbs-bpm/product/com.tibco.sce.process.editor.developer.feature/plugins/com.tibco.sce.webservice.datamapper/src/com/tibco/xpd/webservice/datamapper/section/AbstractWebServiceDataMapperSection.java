/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper.section;

import com.tibco.xpd.datamapper.api.AbstractSelectableGrammarDataMapperSection;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.webservice.datamapper.WebServiceScriptDataMapperProvider;

/**
 * This section provides the base data mapper to be used for webservice
 * input-output.
 * 
 * @author ssirsika
 * @since 20-Jan-2016
 */
public abstract class AbstractWebServiceDataMapperSection extends
        AbstractSelectableGrammarDataMapperSection {

    /**
     * @param direction
     */
    public AbstractWebServiceDataMapperSection(MappingDirection direction) {
        super(direction);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected AbstractScriptDataMapperEditorProvider getScriptDataMapperProvider() {
        return new WebServiceScriptDataMapperProvider(getDataMapperContext(),
                getDirection());
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getScriptSection()
     * 
     * @return
     */
    @Override
    protected BaseScriptSection getScriptSection() {
        return new WebServiceScriptProperties(getDataMapperContext(),
                getDirection());
    }
}

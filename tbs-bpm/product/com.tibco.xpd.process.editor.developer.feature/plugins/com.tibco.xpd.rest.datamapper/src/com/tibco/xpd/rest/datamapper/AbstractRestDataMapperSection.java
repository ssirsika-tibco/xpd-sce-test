/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper;

import com.tibco.xpd.datamapper.api.AbstractDataMapperSection;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;

/**
 * This section provides the base Data Mapper for use in REST Invocation
 * activities.
 * 
 * @author nwilson
 * @since 29 Apr 2015
 */
public abstract class AbstractRestDataMapperSection extends
        AbstractDataMapperSection {

    /**
     * @param direction
     *            The mapping direction for this section.
     */
    public AbstractRestDataMapperSection(MappingDirection direction) {
        super(direction);
    }

    /**
     * 
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected AbstractScriptDataMapperEditorProvider getScriptDataMapperProvider() {
        return new RestScriptDataMapperProvider(getDirection());
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getScriptSection()
     * 
     * @return a new RestMapperScriptProperties.
     */
    @Override
    protected BaseScriptSection getScriptSection() {
        return new RestMapperScriptProperties(getDirection());
    }

}

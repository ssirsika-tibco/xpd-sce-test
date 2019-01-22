/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.globaldataservice.datamapper;

import com.tibco.xpd.catcherror.datamapper.CatchErrorScriptDataMapperProvider;
import com.tibco.xpd.catcherror.datamapper.CatchErrorScriptProperties;
import com.tibco.xpd.datamapper.api.AbstractSelectableGrammarDataMapperSection;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.webservice.datamapper.internal.Messages;

/**
 * Mapper Section for Global Data Operation
 * 
 * @author ssirsika
 * @since 11-Mar-2016
 */
public class GlobalDataErrorDataMapperMapFromErrorSection extends
        AbstractSelectableGrammarDataMapperSection {

    /**
     * @param direction
     */
    public GlobalDataErrorDataMapperMapFromErrorSection() {
        super(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return GlobalDataMapperConstants.GLOBAL_DATA_FAULT_CATCH;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected AbstractScriptDataMapperEditorProvider getScriptDataMapperProvider() {
        return new CatchErrorScriptDataMapperProvider(getDataMapperContext());
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getScriptSection()
     * 
     * @return
     */
    @Override
    protected BaseScriptSection getScriptSection() {
        return new CatchErrorScriptProperties(getDirection(),
                getDataMapperContext());
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.GlobalDataErrorDataMapperMapFromErrorSection_SectionTitle;
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.catcherror.datamapper.section;

import com.tibco.xpd.catcherror.datamapper.CatchErrorScriptDataMapperProvider;
import com.tibco.xpd.catcherror.datamapper.CatchErrorScriptProperties;
import com.tibco.xpd.datamapper.api.AbstractSelectableGrammarDataMapperSection;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Abstract Section for Catch Error related UI.
 * 
 * @author sajain
 * @since Feb 26, 2016
 */
public abstract class AbstractCatchErrorDataMapperSection extends
        AbstractSelectableGrammarDataMapperSection {

    /**
     * @param direction
     */
    public AbstractCatchErrorDataMapperSection(MappingDirection direction) {
        super(direction);
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
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#showScriptEditor()
     * 
     * @return
     */
    @Override
    protected boolean showScriptEditor() {
        /* If in studio for analyst don`t show */
        return CapabilityUtil.isDeveloperActivityEnabled();
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractSelectableGrammarDataMapperSection#showGrammarSelectionCombo()
     * 
     * @return
     */
    @Override
    protected boolean showGrammarSelectionCombo() {
        /* If in studio for analyst don`t show */
        return CapabilityUtil.isDeveloperActivityEnabled();
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper;

import com.tibco.xpd.datamapper.api.AbstractSelectableGrammarDataMapperSection;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * This section provides the base data mapper to be used for call-subprocess
 * input.
 * 
 * @author sajain
 * @since Oct 20, 2015
 */
public abstract class AbstractSubProcessDataMapperSection extends
        AbstractSelectableGrammarDataMapperSection {

    /**
     * @param direction
     *            The mapping direction for this section.
     */
    public AbstractSubProcessDataMapperSection(MappingDirection direction) {
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
        return new SubProcessScriptDataMapperProvider(getDirection());
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getScriptSection()
     * 
     * @return
     */
    @Override
    protected BaseScriptSection getScriptSection() {
        return new SubProcessScriptProperties(getDirection());
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
        /*
         * We only support Data Mapper, so no need to show.
         */
        return false;
    }
}

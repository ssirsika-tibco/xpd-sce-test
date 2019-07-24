/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.datamapper.sections;

import com.tibco.xpd.datamapper.api.AbstractSelectableGrammarDataMapperSection;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.datamapper.GlobalSignalScriptDataMapperProvider;
import com.tibco.xpd.n2.process.globalsignal.datamapper.GlobalSignalScriptProperties;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * This section provides the base data mapper to be used for global signal
 * catch/throw.
 * 
 * @author sajain
 * @since Apr 26, 2016
 */
public abstract class AbstractGlobalSignalDataMapperSection extends
        AbstractSelectableGrammarDataMapperSection {

    /**
     * @param direction
     *            The mapping direction for this section.
     */
    public AbstractGlobalSignalDataMapperSection(MappingDirection direction) {
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
        return new GlobalSignalScriptDataMapperProvider(getDirection());
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getScriptSection()
     * 
     * @return
     */
    @Override
    protected BaseScriptSection getScriptSection() {
        return new GlobalSignalScriptProperties(getDirection());
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#showScriptEditor()
     * 
     * @return
     */
    @Override
    protected boolean showScriptEditor() {
        /* If in studio for analyst don't show */
        return CapabilityUtil.isDeveloperActivityEnabled();
    }
}

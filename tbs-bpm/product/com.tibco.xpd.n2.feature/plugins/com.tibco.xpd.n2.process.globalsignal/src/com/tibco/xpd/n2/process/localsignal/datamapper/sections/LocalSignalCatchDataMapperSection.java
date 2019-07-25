/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper.sections;

import com.tibco.xpd.datamapper.api.AbstractSelectableGrammarDataMapperSection;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.n2.process.localsignal.datamapper.LocalSignalScriptDataMapperProvider;
import com.tibco.xpd.n2.process.localsignal.datamapper.LocalSignalScriptProperties;
import com.tibco.xpd.process.datamapper.signal.util.SignalDataMapperConstants;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * UI mapper section implementation to facilitate Local Signal Catch Data Mapper
 * mappings.
 *
 * @author sajain
 * @since Jul 15, 2019
 */
public class LocalSignalCatchDataMapperSection extends AbstractSelectableGrammarDataMapperSection {

    /**
     * @param direction
     */
    public LocalSignalCatchDataMapperSection() {
        super(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return SignalDataMapperConstants.LOCAL_SIGNAL_CATCH;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.LocalSignalCatchDataMapperSection_Title;
    }

    /**
     * 
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected AbstractScriptDataMapperEditorProvider getScriptDataMapperProvider() {
        return new LocalSignalScriptDataMapperProvider(getDirection());
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getScriptSection()
     * 
     * @return
     */
    @Override
    protected BaseScriptSection getScriptSection() {
        return new LocalSignalScriptProperties(getDirection());
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

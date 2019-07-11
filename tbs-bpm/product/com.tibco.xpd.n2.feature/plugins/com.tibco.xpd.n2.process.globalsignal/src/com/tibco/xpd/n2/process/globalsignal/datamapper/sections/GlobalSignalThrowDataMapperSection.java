/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.datamapper.sections;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.datamapper.GlobalSignalDataMapperConstants;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;

/**
 * Data Mapper section for Global Signal Throw.
 * 
 * @author sajain
 * @since Apr 27, 2016
 */
public class GlobalSignalThrowDataMapperSection extends
        AbstractGlobalSignalDataMapperSection {

    /**
     * @param direction
     */
    public GlobalSignalThrowDataMapperSection() {
        super(MappingDirection.IN);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return GlobalSignalDataMapperConstants.GLOBAL_SIGNAL_THROW;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.GlobalSignalThrowDataMapperSection_Title;
    }
}

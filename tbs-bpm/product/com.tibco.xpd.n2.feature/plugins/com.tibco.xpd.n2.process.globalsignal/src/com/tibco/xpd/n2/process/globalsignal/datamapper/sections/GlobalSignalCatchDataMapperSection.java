/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.datamapper.sections;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.process.datamapper.signal.util.SignalDataMapperConstants;

/**
 * Data Mapper section for Global Signal Catch.
 * 
 * @author sajain
 * @since Apr 27, 2016
 */
public class GlobalSignalCatchDataMapperSection extends
        AbstractGlobalSignalDataMapperSection {

    /**
     * @param direction
     */
    public GlobalSignalCatchDataMapperSection() {
        super(MappingDirection.OUT);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return SignalDataMapperConstants.GLOBAL_SIGNAL_CATCH;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.GlobalSignalCatchDataMapperSection_Title;
    }
}

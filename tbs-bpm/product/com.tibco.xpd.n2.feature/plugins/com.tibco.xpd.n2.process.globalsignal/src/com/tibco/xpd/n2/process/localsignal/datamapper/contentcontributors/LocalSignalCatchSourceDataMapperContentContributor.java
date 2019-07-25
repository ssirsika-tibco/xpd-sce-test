/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper.contentcontributors;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.n2.process.localsignal.datamapper.LocalSignalCatchSourceDataMapperInfoProvider;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.process.datamapper.signal.util.SignalDataMapperConstants;

/**
 * Content contributor implementation for Local Signal CATCH Data Mapper
 * (source).
 *
 * @author sajain
 * @since Jul 16, 2019
 */
public class LocalSignalCatchSourceDataMapperContentContributor extends AbstractProcessDataMapperContentContributor {

    /**
     * Content contributor implementation for Local Signal CATCH Data Mapper
     * (source).
     * 
     * @param direction
     */
    public LocalSignalCatchSourceDataMapperContentContributor() {
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return SignalDataMapperConstants.LOCAL_SIGNAL_CATCH_SOURCE_CONTRIBUTOR_ID;
    }

    /**
     * 
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        return new LocalSignalCatchSourceDataMapperContentProvider();
    }

    /**
     * 
     * @param contentProvider
     * @param labelProvider
     * @return
     */
    @Override
    protected ProcessDataMapperInfoProvider createProcessDataInfoProvider(ITreeContentProvider contentProvider,
            ILabelProvider labelProvider) {
        return new LocalSignalCatchSourceDataMapperInfoProvider(contentProvider, labelProvider);
    }
}
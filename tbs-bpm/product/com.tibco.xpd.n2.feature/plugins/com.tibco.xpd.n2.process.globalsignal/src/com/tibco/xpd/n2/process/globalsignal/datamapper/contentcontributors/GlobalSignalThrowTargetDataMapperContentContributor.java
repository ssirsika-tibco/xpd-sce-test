/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.datamapper.contentcontributors;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.datamapper.PayloadDataMapperInfoProvider;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadDataMapperContentLabelProvider;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadDataMapperContentProvider;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;

/**
 * Target Content contributor for Global Signal Throw.
 * 
 * @author sajain
 * @since Apr 27, 2016
 */
public class GlobalSignalThrowTargetDataMapperContentContributor extends
        AbstractProcessDataMapperContentContributor {

    /**
     * @param direction
     */
    public GlobalSignalThrowTargetDataMapperContentContributor() {
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "MapFromGlobalSignal.DataMapperContent"; //$NON-NLS-1$
    }

    /**
     * 
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        return new PayloadDataMapperContentProvider(false, false,
                MappingDirection.IN);
    }

    /**
     * @see com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor#createLabelProvider()
     * 
     * @return
     */
    @Override
    protected ILabelProvider createLabelProvider() {
        return new PayloadDataMapperContentLabelProvider();
    }

    /**
     * 
     * @param contentProvider
     * @param labelProvider
     * @return
     */
    @Override
    protected ProcessDataMapperInfoProvider createProcessDataInfoProvider(
            ITreeContentProvider contentProvider, ILabelProvider labelProvider) {
        return new PayloadDataMapperInfoProvider(contentProvider,
                labelProvider);

    }
}
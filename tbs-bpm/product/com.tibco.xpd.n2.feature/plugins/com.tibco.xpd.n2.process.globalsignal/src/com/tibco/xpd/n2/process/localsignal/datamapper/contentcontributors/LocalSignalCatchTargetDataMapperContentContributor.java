/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper.contentcontributors;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;

/**
 * Content contributor implementation for Local Signal CATCH Data Mapper
 * (target).
 *
 * @author sajain
 * @since Jul 16, 2019
 */
public class LocalSignalCatchTargetDataMapperContentContributor extends AbstractProcessDataMapperContentContributor {

    /**
     * Content contributor implementation for Local Signal CATCH Data Mapper
     * (target).
     * 
     * @param direction
     */
    public LocalSignalCatchTargetDataMapperContentContributor() {
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "MapToLocalSignalTarget.DataMapperContent"; //$NON-NLS-1$
    }

    /**
     * 
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        return new LocalSignalCatchTargetDataMapperContentProvider();
    }

    /**
     * @see com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor#createLabelProvider()
     * 
     * @return
     */
    @Override
    protected ILabelProvider createLabelProvider() {
        return new ParameterLabelProvider();
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
        return new ProcessDataMapperInfoProvider(contentProvider, labelProvider);
    }
}
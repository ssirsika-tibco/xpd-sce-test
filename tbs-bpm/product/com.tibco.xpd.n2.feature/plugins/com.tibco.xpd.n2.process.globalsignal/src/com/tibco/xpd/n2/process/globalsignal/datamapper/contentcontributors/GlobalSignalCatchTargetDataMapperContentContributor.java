/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.datamapper.contentcontributors;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider;
import com.tibco.xpd.n2.process.globalsignal.mapping.CatchGlobalSignalMapperTargetContentProvider;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.process.datamapper.signal.util.SignalDataMapperConstants;

/**
 * Target Content contributor for Global Signal Catch.
 * 
 * @author sajain
 * @since Apr 27, 2016
 */
public class GlobalSignalCatchTargetDataMapperContentContributor extends
    AbstractProcessDataMapperContentContributor {

    /**
     * @param direction
     */
    public GlobalSignalCatchTargetDataMapperContentContributor() {
    }
    
    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return SignalDataMapperConstants.GLOBAL_SIGNAL_CATCH_TARGET_CONTRIBUTOR_ID; // $NON-NLS-1$
    }

    /**
     * 
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        return new CatchGlobalSignalMapperTargetContentProvider();
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
    protected ProcessDataMapperInfoProvider createProcessDataInfoProvider(
            ITreeContentProvider contentProvider, ILabelProvider labelProvider) {
        return new ProcessDataMapperInfoProvider(contentProvider, labelProvider);
    }
}
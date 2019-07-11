/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.datamapper.contentcontributors;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.n2.process.globalsignal.datamapper.GlobalSignalDataMapperConstants;
import com.tibco.xpd.n2.process.globalsignal.datamapper.PayloadDataMapperInfoProvider;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadDataMapperContentLabelProvider;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadDataMapperContentProvider;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.CorrelationDataFolder;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Correlation content contributor for Global Signal Throw Target.
 * 
 * @author sajain
 * @since Apr 29, 2016
 */
public class GlobalSignalCorrelationDataContentContributor extends
        AbstractProcessDataMapperContentContributor {

    /**
     * @param direction
     */
    public GlobalSignalCorrelationDataContentContributor() {
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return GlobalSignalDataMapperConstants.GS_THROW_CORRELATION_DATAMAPPER_CONTENT_CONTRIBUTOR_ID;
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
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {

        return new PayloadDataMapperContentProvider() {

            /**
             * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
             *      .lang.Object)
             */
            @Override
            public Object[] getElements(Object inputElement) {

                if (inputElement instanceof Activity) {

                    setSelectedActivity((Activity) inputElement);

                    List<Object> treeElements = new LinkedList<Object>();

                    /*
                     * add dummy Correlation Folder which will be the parent
                     * root..
                     */
                    treeElements.add(new CorrelationDataFolder(null));
                    return treeElements.toArray();
                }

                return null;
            }

        };
    }

    /**
     * 
     * @see com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor#createProcessDataInfoProvider(org.eclipse.jface.viewers.ITreeContentProvider, org.eclipse.jface.viewers.ILabelProvider)
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
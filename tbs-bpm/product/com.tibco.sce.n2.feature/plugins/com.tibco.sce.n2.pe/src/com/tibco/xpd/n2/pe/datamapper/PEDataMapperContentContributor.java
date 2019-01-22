/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.datamapper;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.datamapper.staticcontent.StaticContentDataMapperLabelProvider;
import com.tibco.xpd.process.datamapper.common.StaticContentDataMapperInfoProvider;

/**
 * Contributes the data mapper content that represents the "Process" JavaScript
 * class that is available in process manager scripts.
 * 
 * 
 * @author Ali / Sid
 * @since 18 Feb 2015
 */
public class PEDataMapperContentContributor extends
        AbstractDataMapperContentContributor {

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#createInfoProvider()
     * 
     * @return
     */
    @Override
    public AbstractDataMapperInfoProvider createInfoProvider() {
        ITreeContentProvider contentProvider =
                new PEDataMapperContentProvider();

        ILabelProvider labelProvider =
                new StaticContentDataMapperLabelProvider();

        AbstractDataMapperInfoProvider infoProvider =
                new StaticContentDataMapperInfoProvider(contentProvider,
                        labelProvider);

        return infoProvider;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "ProcessManager.DataMapperContent"; //$NON-NLS-1$

    }

}

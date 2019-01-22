/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.n2.ut.resources.processdatamapper;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.process.datamapper.common.StaticContentDataMapperInfoProvider;

/**
 * The Data mapper Content Contributor that supports the use of the content of
 * the JavaScript class "WorkManagerFactory" within User Task Work manager
 * Scripts.
 * <p>
 * Contributes the "Work Item" folder and it's content.
 * 
 * 
 * @author Ali
 * @since 18 Feb 2015
 */
public class WMDataMapperContentContributor extends
        AbstractDataMapperContentContributor {

    public WMDataMapperContentContributor() {
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#createInfoProvider()
     * 
     * @return
     */
    @Override
    public AbstractDataMapperInfoProvider createInfoProvider() {
        ITreeContentProvider contentProvider =
                new WMDataMapperContentProvider();

        ILabelProvider labelProvider = new WMDataMapperLabelProvider();

        StaticContentDataMapperInfoProvider infoProvider =
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
        return "DataMapperWMScripts"; //$NON-NLS-1$
    }

}

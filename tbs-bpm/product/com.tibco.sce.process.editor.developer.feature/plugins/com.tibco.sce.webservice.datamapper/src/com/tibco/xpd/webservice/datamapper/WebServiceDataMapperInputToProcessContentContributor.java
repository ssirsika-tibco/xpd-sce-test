/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptBomContentProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperContentContributor;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperInfoProvider;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * Content contributor for Web-Service mappings which are INPUT to Process.
 * 
 * @author sajain
 * @since Jan 27, 2016
 */

public class WebServiceDataMapperInputToProcessContentContributor extends
        AbstractProcessDataMapperContentContributor {

    /**
     * @param direction
     */
    public WebServiceDataMapperInputToProcessContentContributor() {
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "InputToProcess.DataMapperContent"; //$NON-NLS-1$
    }

    /**
     * 
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        return new JavaScriptBomContentProvider(MappingDirection.OUT,
                DirectionType.OUT_LITERAL, WsdlDirection.IN);
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
        return new WebServiceContentDataMapperInfoProvider(contentProvider,
                labelProvider, WsdlDirection.IN);

    }
}
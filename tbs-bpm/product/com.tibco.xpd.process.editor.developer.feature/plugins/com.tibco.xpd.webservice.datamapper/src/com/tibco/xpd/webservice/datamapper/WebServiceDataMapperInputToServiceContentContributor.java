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
 * Content contributor for INPUT to Web-Service mappings.
 * 
 * @author ssirsika
 * @since 20-Jan-2016
 */
public class WebServiceDataMapperInputToServiceContentContributor extends
        AbstractProcessDataMapperContentContributor {

    /**
     * 
     */
    public WebServiceDataMapperInputToServiceContentContributor() {
    }

    /**
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createItemProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createItemProvider() {
        return new JavaScriptBomContentProvider(MappingDirection.IN,
                DirectionType.IN_LITERAL, WsdlDirection.IN);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "InputToService.DataMapperContent"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.n2.pe.datamapper.AbstractProcessDataMapperContentContributor#createProcessDataInfoProvider(org.eclipse.jface.viewers.ITreeContentProvider,
     *      org.eclipse.jface.viewers.ILabelProvider)
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

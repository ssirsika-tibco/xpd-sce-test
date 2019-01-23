/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper;

import com.tibco.xpd.mapper.MappingDirection;

/**
 * Data Mapper Content contributor for REST Service items.
 * 
 * @author nwilson
 * @since 1 May 2015
 */
public class RestDataMapperInContentContributor extends
        AbstractRestDataMapperContentContributor {

    /**
     * @param direction
     */
    public RestDataMapperInContentContributor() {
        super(MappingDirection.IN);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "ProcessToRestService.DataMapperContent"; //$NON-NLS-1$
    }

}

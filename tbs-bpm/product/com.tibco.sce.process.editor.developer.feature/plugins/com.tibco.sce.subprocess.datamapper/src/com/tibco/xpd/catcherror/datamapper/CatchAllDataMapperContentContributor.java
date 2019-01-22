/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.catcherror.datamapper;


/**
 * Content contributor for Catch All Error
 * 
 * @author sajain
 * @since Feb 26, 2016
 */
public class CatchAllDataMapperContentContributor extends
        AbstractCatchErrorDataMapperContentContributor {

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "CatchAll.DataMapperContent"; //$NON-NLS-1$
    }

}

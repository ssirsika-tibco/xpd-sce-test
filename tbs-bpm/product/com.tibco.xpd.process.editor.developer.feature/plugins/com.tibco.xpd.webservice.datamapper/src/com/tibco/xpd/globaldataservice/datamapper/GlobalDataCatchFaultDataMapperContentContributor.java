/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.globaldataservice.datamapper;

import com.tibco.xpd.catcherror.datamapper.AbstractCatchErrorDataMapperContentContributor;

/**
 * Content Contributor for Global Data Operations
 * 
 * @author ssirsika
 * @since 11-Mar-2016
 */
public class GlobalDataCatchFaultDataMapperContentContributor extends
        AbstractCatchErrorDataMapperContentContributor {

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "GDFaultCatch.DataMapperContent"; //$NON-NLS-1$
    }

}

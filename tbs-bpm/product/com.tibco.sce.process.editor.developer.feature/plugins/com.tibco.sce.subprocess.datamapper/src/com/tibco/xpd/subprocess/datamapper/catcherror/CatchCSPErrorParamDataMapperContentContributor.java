/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper.catcherror;

import com.tibco.xpd.catcherror.datamapper.AbstractCatchErrorDataMapperContentContributor;

/**
 * Content Contributor for Catch error for SubProcess
 * 
 * @author ssirsika
 * @since 04-Mar-2016
 */
public class CatchCSPErrorParamDataMapperContentContributor extends
        AbstractCatchErrorDataMapperContentContributor {

    /**
     * @see com.tibco.xpd.n2.pe.catcherror.datamapper.AbstractCatchErrorDataMapperContentContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        return "CatchSubProcessError.DataMapperContent";
    }

}

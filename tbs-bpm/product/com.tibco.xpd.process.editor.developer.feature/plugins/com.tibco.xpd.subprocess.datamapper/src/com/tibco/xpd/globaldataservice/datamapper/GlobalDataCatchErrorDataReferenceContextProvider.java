/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.globaldataservice.datamapper;

import com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider;
import com.tibco.xpd.subprocess.datamapper.internal.Messages;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

/**
 * Sid ACE-3074 Data reference resolver context provider for catch specific case data task error events.
 * 
 * @author aallway
 * @since 19 Sept 2019
 */
public class GlobalDataCatchErrorDataReferenceContextProvider implements
        IDataReferenceContextProvider {

    private static DataReferenceContext CONTEXT_MAP_FROM_GLOBALDATA_ERROR =
            new DataReferenceContext.MappingInReferenceContext(
                    Messages.CatchErrorDataReferenceContextProvider_MapFromError_label);

    /**
     * @see com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider#getDataReferenceContext(java.lang.String,
     *      com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider.ContextSide)
     * 
     * @param context
     * @param side
     * @return
     */
    @Override
    public DataReferenceContext getDataReferenceContext(String context,
            ContextSide side) {
        if (GlobalDataMapperConstants.GLOBAL_DATA_FAULT_CATCH.equals(context)) {

            return CONTEXT_MAP_FROM_GLOBALDATA_ERROR;
        }
        return DataReferenceContext.CONTEXT_UNKNOWN;
    }

}

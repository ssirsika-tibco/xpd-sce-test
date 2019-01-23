/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.catcherror.datamapper;

import com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider;
import com.tibco.xpd.subprocess.datamapper.internal.Messages;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

/**
 * Returns catch error specific {@link DataReferenceContext}.
 * 
 * @author ssirsika
 * @since 07-Apr-2016
 */
public class CatchErrorDataReferenceContextProvider implements
        IDataReferenceContextProvider {

    private static DataReferenceContext CONTEXT_MAP_FROM_ERROR =
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
        if (CatchErrorDataMapperConstants.CATCH_ALL.equals(context)) {

            return CONTEXT_MAP_FROM_ERROR;
        }
        return DataReferenceContext.CONTEXT_UNKNOWN;
    }

}

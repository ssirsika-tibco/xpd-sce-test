/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.datamapper;

import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

/**
 * Data Reference Context Provider for Global Signal Catch/Throw.
 * 
 * @author sajain
 * @since Apr 28, 2016
 */
public class GlobalSignalDataReferenceContextProvider implements
        IDataReferenceContextProvider {

    private static DataReferenceContext CONTEXT_CATCH_GLOBALSIGNAL =
            new DataReferenceContext.MappingInReferenceContext(
                    Messages.CatchGlobalSignalMappingScriptFieldResolver_CatchGlobalSignalReferenceContext_label);

    private static DataReferenceContext CONTEXT_THROW_GLOBAL_SIGNAL =
            new DataReferenceContext.MappingOutReferenceContext(
                    Messages.ThrowGlobalSignalMappingScriptFieldResolver_MappingReferenceContext_label);

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
        if (GlobalSignalDataMapperConstants.GLOBAL_SIGNAL_CATCH.equals(context)) {

            return CONTEXT_CATCH_GLOBALSIGNAL;

        } else if (GlobalSignalDataMapperConstants.GLOBAL_SIGNAL_THROW
                .equals(context)) {

            return CONTEXT_THROW_GLOBAL_SIGNAL;

        }
        return DataReferenceContext.CONTEXT_UNKNOWN;
    }

}

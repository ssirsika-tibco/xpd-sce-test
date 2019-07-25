/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper;

import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider;
import com.tibco.xpd.process.datamapper.signal.util.SignalDataMapperConstants;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

/**
 * Data Reference Context Provider implementation for Local Signal Catch.
 *
 * @author sajain
 * @since Jul 24, 2019
 */
public class LocalSignalDataReferenceContextProvider implements IDataReferenceContextProvider {

    private static DataReferenceContext CONTEXT_CATCH_LOCALSIGNAL = new DataReferenceContext.MappingInReferenceContext(
            Messages.CatchGlobalSignalMappingScriptFieldResolver_CatchGlobalSignalReferenceContext_label);

    /**
     * @see com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider#getDataReferenceContext(java.lang.String,
     *      com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider.ContextSide)
     * 
     * @param context
     * @param side
     * @return
     */
    @Override
    public DataReferenceContext getDataReferenceContext(String context, ContextSide side) {
        if (SignalDataMapperConstants.LOCAL_SIGNAL_CATCH.equals(context)) {

            return CONTEXT_CATCH_LOCALSIGNAL;

        }
        return DataReferenceContext.CONTEXT_UNKNOWN;
    }

}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper;

import com.tibco.xpd.catcherror.datamapper.CatchErrorDataMapperConstants;
import com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider;
import com.tibco.xpd.subprocess.datamapper.internal.Messages;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

/**
 * Returns SubProcess specific {@link DataReferenceContext}.
 * 
 * @author ssirsika
 * @since 07-Apr-2016
 */
public class SubProcessDataReferenceContextProvider implements
        IDataReferenceContextProvider {

    private static DataReferenceContext CONTEXT_PROCESS_TO_SUBPROCESS =
            new DataReferenceContext.MappingInReferenceContext(
                    Messages.SubProcessDataReferenceContextProvider_MapToSubProc_label);

    private static DataReferenceContext CONTEXT_SUBPROCESS_TO_PROCESS =
            new DataReferenceContext.MappingOutReferenceContext(
                    Messages.SubProcessDataReferenceContextProvider_MapFromSubProc_label);

    private static DataReferenceContext CONTEXT_MAP_FROM_ERROR =
            new DataReferenceContext.MappingInReferenceContext(
                    Messages.SubProcessDataReferenceContextProvider_MapFromError_Label);

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
        if (SubProcessDataMapperConstants.PROCESS_TO_SUBPROCESS.equals(context)) {
            return CONTEXT_PROCESS_TO_SUBPROCESS;

        } else if (SubProcessDataMapperConstants.SUBPROCESS_TO_PROCESS
                .equals(context)) {

            /*
             * For Sub-Process output mapping, process data references are only
             * there on target side - so if asked to return only if process data
             * is on source side specifically then the answer is
             * "no this isn't process data related"
             */

            if (ContextSide.SOURCE.equals(side)) {
                return null;
            }

            return CONTEXT_SUBPROCESS_TO_PROCESS;

        } else if (CatchErrorDataMapperConstants.CATCH_SUBPROCESS_ERROR
                .equals(context)) {

            return CONTEXT_MAP_FROM_ERROR;
        }

        return DataReferenceContext.CONTEXT_UNKNOWN;
    }

}

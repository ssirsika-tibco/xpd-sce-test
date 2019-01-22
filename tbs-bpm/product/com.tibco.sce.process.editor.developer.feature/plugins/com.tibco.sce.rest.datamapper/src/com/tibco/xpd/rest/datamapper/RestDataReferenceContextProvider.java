/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper;

import com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider;
import com.tibco.xpd.rest.datamapper.internal.Messages;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

/**
 * Returns REST specific {@link DataReferenceContext}.
 * 
 * @author ssirsika
 * @since 07-Apr-2016
 */
public class RestDataReferenceContextProvider implements
        IDataReferenceContextProvider {

    private static DataReferenceContext CONTEXT_INPUT_TO_SERVICE =
            new DataReferenceContext.MappingInReferenceContext(
                    Messages.RestDataReferenceContextProvider_InputToService_Label);

    private static DataReferenceContext CONTEXT_OUTPUT_FROM_SERVICE =
            new DataReferenceContext.MappingOutReferenceContext(
                    Messages.RestDataReferenceContextProvider_OutputFromService_Label);

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
        if (RestDataMapperConstants.PROCESS_TO_REST_SERVICE.equals(context)) {
            return CONTEXT_INPUT_TO_SERVICE;

        } else if (RestDataMapperConstants.REST_SERVICE_TO_PROCESS
                .equals(context)) {

            /*
             * For REST Service output mapping, process data references are only
             * there on target side - so if asked to return only if process data
             * is on source side specifically then the answer is
             * "no this isn't process data related"
             */
            if (ContextSide.SOURCE.equals(side)) {
                return null;
            }

            return CONTEXT_OUTPUT_FROM_SERVICE;
        }
        return DataReferenceContext.CONTEXT_UNKNOWN;
    }

}

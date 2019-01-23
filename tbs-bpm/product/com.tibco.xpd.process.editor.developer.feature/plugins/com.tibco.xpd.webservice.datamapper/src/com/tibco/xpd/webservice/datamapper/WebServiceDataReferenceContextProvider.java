/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper;

import com.tibco.xpd.globaldataservice.datamapper.GlobalDataMapperConstants;
import com.tibco.xpd.process.datamapper.refactor.IDataReferenceContextProvider;
import com.tibco.xpd.webservice.datamapper.internal.Messages;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

/**
 * 
 * Returns Webservice specific {@link DataReferenceContext}.
 * 
 * @author ssirsika
 * @since 07-Apr-2016
 */
public class WebServiceDataReferenceContextProvider implements
        IDataReferenceContextProvider {

    private static DataReferenceContext CONTEXT_INPUT_TO_SERVICE =
            new DataReferenceContext.MappingInReferenceContext(
                    Messages.WebServiceDataReferenceContextProvider_InputToService_label);

    private static DataReferenceContext CONTEXT_OUTPUT_FROM_SERVICE =
            new DataReferenceContext.MappingOutReferenceContext(
                    Messages.WebServiceDataReferenceContextProvider_OutputFromService_label);

    private static DataReferenceContext CONTEXT_INPUT_TO_PROCESS =
            new DataReferenceContext.MappingOutReferenceContext(
                    Messages.WebServiceDataReferenceContextProvider_InputToProcess_label);

    private static DataReferenceContext CONTEXT_OUTPUT_FROM_PROCESS =
            new DataReferenceContext.MappingInReferenceContext(
                    Messages.WebServiceDataReferenceContextProvider_OutputFromProcess_label);

    private static DataReferenceContext CONTEXT_OUTPUT_FAULT_FROM_PROCESS =
            new DataReferenceContext.MappingInReferenceContext(
                    Messages.WebServiceDataReferenceContextProvider_OutputFalutFromProcess_Label);

    private static DataReferenceContext CONTEXT_MAP_FROM_ERROR =
            new DataReferenceContext.MappingInReferenceContext(Messages.WebServiceDataReferenceContextProvider_MapFromError_label);

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
        if (WebServiceDataMapperConstants.INPUT_TO_SERVICE.equals(context)) {

            return CONTEXT_INPUT_TO_SERVICE;

        } else if (WebServiceDataMapperConstants.OUTPUT_FROM_SERVICE
                .equals(context)) {

            return CONTEXT_OUTPUT_FROM_SERVICE;
        } else if (WebServiceDataMapperConstants.INPUT_TO_PROCESS
                .equals(context)) {

            return CONTEXT_INPUT_TO_PROCESS;
        } else if (WebServiceDataMapperConstants.OUTPUT_FROM_PROCESS
                .equals(context)) {

            return CONTEXT_OUTPUT_FROM_PROCESS;
        } else if (WebServiceDataMapperConstants.OUTPUT_FAULT_FROM_PROCESS
                .equals(context)) {

            return CONTEXT_OUTPUT_FAULT_FROM_PROCESS;
        } else if (WebServiceDataMapperConstants.WEB_SERVICE_FAULT_CATCH
                .equals(context)) {

            return CONTEXT_MAP_FROM_ERROR;
        } else if (GlobalDataMapperConstants.GLOBAL_DATA_FAULT_CATCH
                .equals(context)) {

            return CONTEXT_MAP_FROM_ERROR;
        }
        return DataReferenceContext.CONTEXT_UNKNOWN;
    }

}

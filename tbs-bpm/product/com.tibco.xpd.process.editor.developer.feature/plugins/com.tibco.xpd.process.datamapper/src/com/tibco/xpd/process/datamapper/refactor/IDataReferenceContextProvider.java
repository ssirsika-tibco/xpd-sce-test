/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.refactor;

import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;

/**
 * 
 * Implementors of this interface should override the method to return
 * DataReferenceContext for passed context and side.
 * 
 * @author ssirsika
 * @since 07-Apr-2016
 */
public interface IDataReferenceContextProvider {
    enum ContextSide {
        SOURCE, DESTINATION, ANY
    }

    /**
     * Return the {@link DataReferenceContext} for passed 'context' attribute
     * and {@link ContextSide}. If no matching {@link DataReferenceContext}
     * found then return DataReferenceContext.CONTEXT_UNKNOWN
     * 
     * @param context
     *            Context key
     * @param side
     *            {@link ContextSide}
     * @return return {@link DataReferenceContext} and if no matching
     *         {@link DataReferenceContext} found then return
     *         DataReferenceContext.CONTEXT_UNKNOWN
     */
    DataReferenceContext getDataReferenceContext(String context,
            ContextSide side);
}

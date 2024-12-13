/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper;

import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.mapper.IMappingTransferValidator;

/**
 * Transfer validator for process data mapper, which decides whether a source
 * item could be mapped to a target so as to draw the mapping.
 * 
 * @author Ali
 * @since 16 Jan 2015
 */
public class DataMapperMappingTransferValidator implements
        IMappingTransferValidator {

    private AbstractDataMapperInfoProvider targetInfoProvider;

    private AbstractDataMapperInfoProvider sourceInfoProvider;

    public DataMapperMappingTransferValidator(
            AbstractDataMapperInfoProvider sourceInfoProvider,
            AbstractDataMapperInfoProvider targetInfoProvider) {
        this.sourceInfoProvider = sourceInfoProvider;
        this.targetInfoProvider = targetInfoProvider;
    }

    /**
     * @see com.tibco.xpd.mapper.IMappingTransferValidator#isValidTransfer(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param source
     * @param target
     * @return
     */
    @Override
    public boolean isValidTransfer(Object source, Object target) {
        boolean isValid = true;

		/* Sid ACE-8885 We should also prevent actual mapping of items tagged as not valid for mapping. */
        if (sourceInfoProvider.isArtificialObject(source)
				|| targetInfoProvider.isArtificialObject(target) || !sourceInfoProvider.isValidForMapping(source)
				|| !targetInfoProvider.isValidForMapping(target))
		{
            isValid = false;
        }

        return isValid;
    }

    /**
     * @see com.tibco.xpd.mapper.IMappingTransferValidator#canAcceptMultipleInputs(java.lang.Object)
     * 
     * @param target
     * @return
     * @deprecated
     */
    @Deprecated
    @Override
    public boolean canAcceptMultipleInputs(Object target) {
        return false;
    }

}

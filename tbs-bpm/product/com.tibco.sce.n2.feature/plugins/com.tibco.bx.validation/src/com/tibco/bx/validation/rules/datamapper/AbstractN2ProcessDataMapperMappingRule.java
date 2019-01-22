/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

/**
 * Base class for Process manager Process-Data-mapper mapping scenarios (e.g.
 * scenarios where data mapper is used as an activity script).
 * 
 * @author aallway
 * @since 4 Aug 2015
 */
public abstract class AbstractN2ProcessDataMapperMappingRule extends
        AbstractN2DataMapperMappingRule {

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#isPartialMappingCompletionSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree) {
        /*
         * Because process data mapper uses a 'create or merge-into-existing'
         * approach, then it is inappropriate to treat unmapped mandatory child
         * content as an error.
         * 
         * therefore we will treat Unmapped mandatory content as a warning.
         */
        return true;

    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#performAdditionalMappingsValidation(java.lang.Object,
     *      java.util.Collection)
     * 
     * @param objectToValidate
     * @param mappings
     */
    @Override
    protected void performAdditionalMappingsValidation(Object objectToValidate,
            Collection<Object> mappings) {
        super.performAdditionalMappingsValidation(objectToValidate, mappings);
        if (mappings.size() == 0 && objectToValidate instanceof EObject) {
            addIssue("bx.processDataMapper.noMappings", //$NON-NLS-1$
                    (EObject) objectToValidate,
                    createMessageList(getDataMapperContext()));
        }
    }

}

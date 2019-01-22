/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.RestServiceTaskUtil;
import com.tibco.xpd.rest.datamapper.RestDataMapperConstants;
import com.tibco.xpd.rest.datamapper.RestScriptDataMapperProvider;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Process to REST service mapping rule.
 * 
 * @author jarciuch
 * @since 16 Apr 2015
 */
public class ProcessDataToRestServiceJsRule extends
        AbstractRestDataMapperMappingRule {

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#validateObject(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     */
    @Override
    protected void validateObject(EObject objectToValidate) {
        /*
         * XPD-7922: Added filtering so that only Rest Activities are validated
         * by this rule.
         */
        if (objectToValidate instanceof Activity
                && RestServiceTaskUtil
                        .isRESTServiceActivity((Activity) objectToValidate)) {

            super.validateObject(objectToValidate);

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.DATA_MAPPER_PE_MAPPING_SCRIPTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.IN;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected IScriptDataMapperProvider getScriptDataMapperProvider() {
        return new RestScriptDataMapperProvider(getMappingDirection());
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return RestDataMapperConstants.PROCESS_TO_REST_SERVICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.ProcessDataToRestServiceJsRule_Mapping_desc;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#isPartialMappingCompletionSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree) {
        /* Mandatory target child content mapping is required. */
        return false;
    }

}

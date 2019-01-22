/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.RestServiceTaskUtil;
import com.tibco.xpd.rest.datamapper.RestDataMapperConstants;
import com.tibco.xpd.rest.datamapper.RestScriptDataMapperProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;

/**
 * REST service to process data mapping rule.
 * 
 * @author jarciuch
 * @since 16 Apr 2015
 */
public class RestServiceToProcessDataJsRule extends
        AbstractRestDataMapperMappingRule {

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#validateObject(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     */
    @Override
    protected void validateObject(EObject objectToValidate) {
        /*
         * XPD-7922: Added filtering so that only Rest Activities AND error
         * events attached to Rest service activities are are validated by this
         * rule.
         */
        if (objectToValidate instanceof Activity) {
            Activity activity = (Activity) objectToValidate;
            if (RestServiceTaskUtil.isRESTServiceActivity(activity)) {

                super.validateObject(objectToValidate);

            } else if (activity.getEvent() instanceof IntermediateEvent
                    && EventObjectUtil.isAttachedToTask(activity)) {

                IntermediateEvent ie = (IntermediateEvent) activity.getEvent();

                if (ie.getResultError() != null) {

                    Activity taskAttachedTo =
                            EventObjectUtil.getTaskAttachedTo(activity);

                    if (taskAttachedTo != null
                            && RestServiceTaskUtil
                                    .isRESTServiceActivity(taskAttachedTo)) {

                        super.validateObject(objectToValidate);

                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.DATA_MAPPER_REST_MAPPING_SCRIPTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.OUT;
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
        return RestDataMapperConstants.REST_SERVICE_TO_PROCESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.RestServiceToProcessDataJsRule_Mapping_desc;
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
        /*
         * Mandatory target child content mapping is optional. On output from
         * service we may be merging into existing process data content
         */
        return true;
    }
}

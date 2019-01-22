/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.webservice.datamapper.WebServiceDataMapperConstants;
import com.tibco.xpd.webservice.datamapper.WebServiceScriptDataMapperProvider;
import com.tibco.xpd.webservice.datamapper.section.filter.WebServiceDataMapperOutputFaultFromProcessSectionFilter;
import com.tibco.xpd.xpdl2.Activity;

/**
 * DataMapper validation rule for WebService OutputFaultFromProcess
 * 
 * @author ssirsika
 * @since 01-Feb-2016
 */
public class WebServiceOutputFaultFromProcessDataMapperMappingRule extends
        AbstractProcessToWebSvcDataMapperRule {
    /**
     * Class level field to track the activity being validated.
     */
    Activity act;

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#objectIsApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param eo
     * @return
     */
    @Override
    protected boolean objectIsApplicable(EObject eo) {

        act = null;

        if (eo instanceof Activity) {
            act = (Activity) eo;

            WebServiceDataMapperOutputFaultFromProcessSectionFilter filter =
                    new WebServiceDataMapperOutputFaultFromProcessSectionFilter();

            return filter.select(act) && super.objectIsApplicable(eo);
        }

        return false;
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
        return new WebServiceScriptDataMapperProvider(getDataMapperContext(),
                getMappingDirection());
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return WebServiceDataMapperConstants.OUTPUT_FAULT_FROM_PROCESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.WebServiceOutputFaultFromProcessDataMapperMappingRule_MappingTypeDescription;
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

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#isMultiToSingleSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param multiInstanceSource
     * @param singleInstanceTarget
     * @return
     */
    @Override
    protected boolean isMultiToSingleSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        return super.isMultiToSingleSupported(multiInstanceSource,
                singleInstanceTarget);
    }
}

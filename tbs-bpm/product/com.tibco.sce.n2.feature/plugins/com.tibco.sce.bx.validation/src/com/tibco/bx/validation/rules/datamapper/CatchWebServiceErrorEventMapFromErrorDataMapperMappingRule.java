/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.catcherror.datamapper.CatchErrorScriptDataMapperProvider;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.webservice.datamapper.WebServiceDataMapperConstants;
import com.tibco.xpd.webservice.datamapper.section.filter.WebServiceDataMapperMapFromErrorSectionFilter;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Rules for Catch error in case of 'Map from error' section of attached Web
 * Service activity.
 * 
 * @author ssirsika
 * @since 07-Mar-2016
 */
public class CatchWebServiceErrorEventMapFromErrorDataMapperMappingRule extends
        AbstractWebSvcToProcessDataMapperRule {

    private Activity activity;

    /**
     * @see com.tibco.bx.validation.rules.datamapper.AbstractWebSvcToProcessDataMapperRule#objectIsApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param eo
     * @return
     */
    @Override
    protected boolean objectIsApplicable(EObject eo) {
        activity = null;

        if (eo instanceof Activity) {
            activity = (Activity) eo;
            WebServiceDataMapperMapFromErrorSectionFilter filter =
                    new WebServiceDataMapperMapFromErrorSectionFilter();
            return filter.select(activity) && super.objectIsApplicable(eo);
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected IScriptDataMapperProvider getScriptDataMapperProvider() {
        return new CatchErrorScriptDataMapperProvider(getDataMapperContext());
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return WebServiceDataMapperConstants.WEB_SERVICE_FAULT_CATCH;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingDirection()
     * 
     * @return
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.OUT;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.DATA_MAPPER_PE_MAPPING_SCRIPTS;
    }

}

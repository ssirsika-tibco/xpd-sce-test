/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.catcherror.datamapper.CatchErrorDataMapperConstants;
import com.tibco.xpd.catcherror.datamapper.CatchErrorScriptDataMapperProvider;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * Rules for Catch All error in case of 'Map from error' section.
 * 
 * @author ssirsika
 * @since 07-Mar-2016
 */
public class CatchAllErrorEventMapFromErrorDataMapperMappingRule extends
        AbstractN2DataMapperMappingRule {

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
            if (ScriptGrammarFactory.DATAMAPPER.equals(ScriptGrammarFactory
                    .getGrammarToUse(activity, DirectionType.OUT_LITERAL))) {
                ErrorCatchType catchType =
                        BpmnCatchableErrorUtil.getCatchTypeStrict(activity);
                return ErrorCatchType.CATCH_ALL.equals(catchType)
                        && super.objectIsApplicable(eo);
            }
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
        return CatchErrorDataMapperConstants.CATCH_ALL;
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

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#isSingleToMultiSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param singleInstanceSource
     * @param multiInstanceTarget
     * @return
     */
    @Override
    protected boolean isSingleToMultiSupported(Object singleInstanceSource,
            Object multiInstanceTarget) {
        return true;
    }
}

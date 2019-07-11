/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.datamapper.GlobalSignalDataMapperConstants;
import com.tibco.xpd.n2.process.globalsignal.datamapper.GlobalSignalScriptDataMapperProvider;
import com.tibco.xpd.n2.process.globalsignal.datamapper.GlobalSignalThrowDataMapperFilter;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapping rules for throw global signal events data mapper.
 * 
 * @author ssirsika
 * @since May 3, 2016
 */
public class ThrowGlobalSignalEventDataMapperMappingRule extends
    AbstractN2DataMapperMappingRule {
    private Activity activity;

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingDirection()
     * 
     * @return
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.IN;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#objectIsApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param eo
     * @return
     */
    @Override
    protected boolean objectIsApplicable(EObject eo) {

        activity = null;

        if (eo instanceof Activity) {
            activity = (Activity) eo;

            IFilter filter = new GlobalSignalThrowDataMapperFilter();

            return filter.select(activity) && super.objectIsApplicable(eo);
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {

        return ProcessScriptContextConstants.GLOBAL_THROW_SIGNAL_EVENTMAPPING;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getScriptGrammar(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected String getScriptGrammar(EObject objectToValidate) {
        return ScriptGrammarFactory.JAVASCRIPT;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#checkJavaScriptTypeCompatibility(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return
     */
    @Override
    protected JavaScriptTypeCompatibilityResult checkJavaScriptTypeCompatibility(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {

        Object unwrappedSource = sourceObjectInTree;
        Object unwrappedTarget = targetObjectInTree;
        if (sourceObjectInTree instanceof WrappedContributedContent) {
            unwrappedSource =
                    ((WrappedContributedContent) sourceObjectInTree)
                            .getContributedObject();
        }
        if (targetObjectInTree instanceof WrappedContributedContent) {
            unwrappedTarget =
                    ((WrappedContributedContent) targetObjectInTree)
                            .getContributedObject();
        }

        if (unwrappedTarget instanceof PayloadConceptPath) {

            PayloadConceptPath payloadConceptPath =
                    (PayloadConceptPath) unwrappedTarget;

            PayloadDataField payloadDataField =
                    payloadConceptPath.getPayloadDataField();

            if (payloadDataField != null) {
                /*
                 * converting payload data field data field to concept path ,
                 * verified this and there are no side effects of doing this.
                 */
                JavaScriptTypeCompatibilityResult checkJavaScriptTypeCompatibility =
                        super.checkJavaScriptTypeCompatibility(unwrappedSource,
                                ConceptUtil.getConceptPath(payloadDataField),
                                mapping);

                return checkJavaScriptTypeCompatibility;
            }
        }
        return JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getScriptDataMapperProvider()
     * 
     * @return
     */
    @Override
    protected IScriptDataMapperProvider getScriptDataMapperProvider() {
        return new GlobalSignalScriptDataMapperProvider(getMappingDirection());
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#getDataMapperContext()
     * 
     * @return
     */
    @Override
    protected String getDataMapperContext() {
        return GlobalSignalDataMapperConstants.GLOBAL_SIGNAL_THROW;
    }
}

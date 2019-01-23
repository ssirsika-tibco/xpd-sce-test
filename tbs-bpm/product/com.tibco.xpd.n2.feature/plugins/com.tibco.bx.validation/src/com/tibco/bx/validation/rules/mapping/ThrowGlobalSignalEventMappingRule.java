/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadConceptPath;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadDataMapperContentProvider;
import com.tibco.xpd.n2.process.globalsignal.mapping.ThrowGlobalSignalMapperSourceContentProvider;
import com.tibco.xpd.n2.process.globalsignal.mapping.ThrowGlobalSignalMappingContentProvider;
import com.tibco.xpd.process.js.parser.util.ScriptMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingTypeCompatibility;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping rules for throw global signal events.
 * 
 * @author kthombar
 * @since Feb 14, 2015
 */
public class ThrowGlobalSignalEventMappingRule extends
        AbstractProcessData2ProcessDataJSMappingRule {

    private ThrowGlobalSignalMapperSourceContentProvider sourceContentProvider;

    private PayloadDataMapperContentProvider targetContentProvider;

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingTypeDescription(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.ThrowGlobalSignalEventMappingRule_ThrowGlobalSignalMAppingType_desc;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createSourceInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected MappingRuleContentInfoProvider createSourceInfoProvider(
            EObject objectToValidate) {
        sourceContentProvider =
                new ThrowGlobalSignalMapperSourceContentProvider(true);

        return new ProcessDataMappingRuleInfoProvider(sourceContentProvider);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createTargetInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected MappingRuleContentInfoProvider createTargetInfoProvider(
            EObject objectToValidate) {
        targetContentProvider =
                new PayloadDataMapperContentProvider(false, MappingDirection.IN);

        return new PayloadDataMappingRuleInfoProvider(targetContentProvider);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createMappingsContentProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected IStructuredContentProvider createMappingsContentProvider(
            EObject objectToValidate) {
        if (sourceContentProvider == null) {
            throw new RuntimeException(
                    "Source content provider and Mapping content provider creation request in unexpected order."); //$NON-NLS-1$
        }

        return new ThrowGlobalSignalMappingContentProvider();
    }

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
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isConcatenationMappingSupported()
     * 
     * @return
     */
    @Override
    protected boolean isConcatenationMappingSupported() {
        /*
         * no concatenation allowed
         */
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isConcatenationMappingSupportedForTarget(java.lang.Object)
     * 
     * @param targetObject
     * @return
     */
    @Override
    protected boolean isConcatenationMappingSupportedForTarget(
            Object targetObject) {
        /*
         * no concatenation allowed
         */
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingSupported()
     * 
     * @return
     */
    @Override
    protected boolean isScriptMappingSupported() {
        /*
         * script mappings are supported
         */
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isScriptMappingSupportedForTarget(java.lang.Object)
     * 
     * @param targetObject
     * @return
     */
    @Override
    protected boolean isScriptMappingSupportedForTarget(Object targetObject) {
        /*
         * script mappings are supported for all target payloads
         */
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMappingFromSourceLevelSupported(java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @return
     */
    @Override
    protected boolean isMappingFromSourceLevelSupported(
            Object sourceObjectInTree) {
        /*
         * Mappings fro all source levels are supported
         */
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMappingToTargetLevelSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        /*
         * Mappings to any target levels are supported
         */
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isSingleToMultiSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param singleInstanceSource
     * @param multiInstanceTarget
     * @return
     */
    @Override
    protected boolean isSingleToMultiSupported(Object singleInstanceSource,
            Object multiInstanceTarget) {
        /*
         * not supported
         */
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMultiToSingleSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param multiInstanceSource
     * @param singleInstanceTarget
     * @return
     */
    @Override
    protected boolean isMultiToSingleSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        /*
         * not supported
         */
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMultiToMultiSupported(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param multiInstanceSource
     * @param singleInstanceTarget
     * @return
     */
    @Override
    protected boolean isMultiToMultiSupported(Object multiInstanceSource,
            Object singleInstanceTarget) {
        /*
         * multi to multi mappings supported.
         */
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isPartialMappingCompletionSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree) {
        /*
         * partial mapping not supported.
         */
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#parseScript(java.lang.Object)
     * 
     * @param mapping
     * @return
     */
    @Override
    protected ScriptInformationParsed parseScript(Object mapping) {
        /*
         * parse the script
         */
        if (mapping instanceof Mapping
                && ((Mapping) mapping).getMappingModel() instanceof DataMapping) {
            DataMapping dataMapping =
                    (DataMapping) ((Mapping) mapping).getMappingModel();
            String strScript =
                    ProcessScriptUtil.getDataMappingScript(dataMapping);

            Activity activity = Xpdl2ModelUtil.getParentActivity(dataMapping);

            ScriptInformation scriptInformation =
                    ProcessScriptUtil
                            .getScriptInformationFromDataMapping(dataMapping);

            if (strScript == null || strScript.trim().length() < 1) {
                return new ScriptInformationParsed(scriptInformation, null);
            }

            Process process = Xpdl2ModelUtil.getProcess(activity);
            if (activity != null && scriptInformation != null
                    && process != null) {

                return ScriptMappingUtil
                        .parseScript(mapping,
                                getValidationStrategyList(process, activity),
                                getProcessDestinationList(process),
                                getScriptRelevantDataTypeMap(process,
                                        scriptInformation),
                                getScriptType());
            }
        }
        return null;
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
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getDefaultScriptDestination()
     * 
     * @return
     */
    @Override
    protected String getDefaultScriptDestination() {
        return JsConsts.JSCRIPT_DESTINATION;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#doActivityIsApplicable(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean doActivityIsApplicable(Activity activity) {
        /*
         * Rules applicable only when the Activity is an Throw Global signal
         * event.
         */
        if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL.equals(EventObjectUtil
                .getEventTriggerType(activity))) {

            return GlobalSignalUtil.isGlobalSignalEvent(activity);
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#doActivityValidationDone(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void doActivityValidationDone(Activity activity) {
        /*
         * do nothing here
         */
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#isScriptTypeToProcessDataCompatible(com.tibco.xpd.script.model.client.IScriptRelevantData,
     *      com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath)
     * 
     * @param scriptType
     * @param target
     * @return
     */
    @Override
    protected MappingTypeCompatibility isScriptTypeToProcessDataCompatible(
            IScriptRelevantData scriptType, ConceptPath target) {
        /*
         * perform script compatibility check
         */
        return N2ProcessDataMappingCompatibilityUtil
                .checkTypeCompatibility(scriptType, target);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#doCreateMappingSourceItemContentProvider(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected ITreeContentProvider doCreateMappingSourceItemContentProvider(
            Activity activity) {
        throw new RuntimeException(
                "Sould not get here because of overirde createSourceInfoProvider()"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingJavaScriptRule#isUntypedScriptListToArrayMappingAllowed(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return
     */
    @Override
    protected boolean isUntypedScriptListToArrayMappingAllowed(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {

        return true;
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

        if (targetObjectInTree instanceof PayloadConceptPath) {

            PayloadConceptPath payloadConceptPath =
                    (PayloadConceptPath) targetObjectInTree;

            PayloadDataField payloadDataField =
                    payloadConceptPath.getPayloadDataField();

            if (payloadDataField != null) {
                /*
                 * converting payload data field data field to concept path ,
                 * verified this and there are no side effects of doing this.
                 */
                JavaScriptTypeCompatibilityResult checkJavaScriptTypeCompatibility =
                        super.checkJavaScriptTypeCompatibility(sourceObjectInTree,
                                ConceptUtil.getConceptPath(payloadDataField),
                                mapping);

                return checkJavaScriptTypeCompatibility;
            }
        }
        return JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO;
    }
}

/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.mapping.CatchGlobalSignalMapperTargetContentProvider;
import com.tibco.xpd.n2.process.globalsignal.mapping.CatchGlobalSignalMappingContentProvider;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadConceptPath;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadDataMapperContentProvider;
import com.tibco.xpd.process.js.parser.util.ScriptMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingTypeCompatibility;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping Rules for Catch Global Signal Events.
 * 
 * @author kthombar
 * @since Feb 16, 2015
 */
public class CatchGlobalSignalEventMappingRule
        extends AbstractProcessData2ProcessDataJSMappingRule {

    /**
     * Correlation data (%1$s) can only be mapped from signal payload
     * correlation parameters.
     */
    private static String ISSUE_ONLY_CORRELATION_PAYLOAD_MAPPING_ALLOWED_TO_TARGET_CORRELATION_DATA =
            "bx.OnlyCorrelationPayloadMappingSupportedToTargetCorrelationData"; //$NON-NLS-1$

    /**
     * Source correlation payload data (%2$s) must be mapped to a correlation
     * data field.
     */
    private static String ISSUE_SOURCE_CORRELATION_PAYLOAD_MUST_BE_MAPPED =
            "bx.SourceCorrelationPayloadMustBeMapped"; //$NON-NLS-1$

    /**
     * Source correlation payload data (%2$s) must be mapped to a correlation
     * data field. This issue additionally provides the remove mapping quick
     * fix.
     */
    private static String ISSUE_SOURCE_CORRELATION_PAYLOAD_MUST_BE_MAPPED_ONLY_TO_TARGET_CORRELATION_FIELD =
            "bx.SourceCorrelationPayloadMustBeMappedOnlyToTargetCorrelationField"; //$NON-NLS-1$

    /**
     * This issue is raised when field in the mapping is correlation field but
     * the mapping is not correlation data mapping.
     */
    private static final String MAPPING_NOT_CORRELATION_DATA_MAPPING =
            "bx.dataIsCorrelationButMappingIsNotCorrelationDataMapping"; //$NON-NLS-1$

    private MappingRuleContentInfoProvider sourceInfoProvider;

    private MappingRuleContentInfoProvider targetInfoProvider;

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#doActivityIsApplicable(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean doActivityIsApplicable(Activity activity) {

        if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                .equals(EventObjectUtil.getEventTriggerType(activity))) {

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
        // Do nothing here.
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
         * check script compatibility
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
            Object sourceObjectInTree, Object targetObjectInTree,
            Object mapping) {

        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingTypeDescription(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        return Messages.CatchGlobalSignalEventMappingRule_CatchGlobalSignalMappingType_desc;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#createSourceInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected MappingRuleContentInfoProvider createSourceInfoProvider(
            EObject objectToValidate) {
        PayloadDataMapperContentProvider sourceContentProvider =
                new PayloadDataMapperContentProvider(true,
                        MappingDirection.OUT);

        sourceInfoProvider =
                new PayloadDataMappingRuleInfoProvider(sourceContentProvider);

        return sourceInfoProvider;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#createTargetInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected MappingRuleContentInfoProvider createTargetInfoProvider(
            EObject objectToValidate) {
        CatchGlobalSignalMapperTargetContentProvider targetContentProvider =
                new CatchGlobalSignalMapperTargetContentProvider();

        targetInfoProvider = new CatchGlobalSignalTargetMappingRuleInfoProvider(
                targetContentProvider);

        return targetInfoProvider;
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

        return new CatchGlobalSignalMappingContentProvider();
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isConcatenationMappingSupported()
     * 
     * @return
     */
    @Override
    protected boolean isConcatenationMappingSupported() {
        /*
         * concatenation not supported
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
         * concatenation not supported
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
         * Script mappings are supported to non-correlation data only, but these
         * validations will be taken care of in
         * performAdditionalMappingValidation method.
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
         * Script mappings are supported to non-correlation data only, but these
         * validations will be taken care of in
         * performAdditionalMappingValidation method.
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

        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isMappingToTargetLevelSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isMappingToTargetLevelSupported(
            Object targetObjectInTree) {

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
         * Not supported
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
         * Not supported
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
         * Multi to Multi supported
         */
        return true;
    }

    /**
     * 
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#getScriptGrammar(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected String getScriptGrammar(EObject objectToValidate) {
        return ScriptGrammarFactory.JAVASCRIPT;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#getScriptType()
     * 
     * @return
     */
    @Override
    protected String getScriptType() {
        return ProcessScriptContextConstants.GLOBAL_CATCH_SIGNAL_EVENTMAPPING;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#isPartialMappingCompletionSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree) {

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
         * Parse the script
         */
        if (mapping instanceof Mapping && ((Mapping) mapping)
                .getMappingModel() instanceof DataMapping) {
            DataMapping dataMapping =
                    (DataMapping) ((Mapping) mapping).getMappingModel();
            String strScript =
                    ProcessScriptUtil.getDataMappingScript(dataMapping);

            Activity activity = Xpdl2ModelUtil.getParentActivity(dataMapping);
            ScriptInformation scriptInformation = ProcessScriptUtil
                    .getScriptInformationFromDataMapping(dataMapping);
            if (strScript == null || strScript.trim().length() < 1) {
                return new ScriptInformationParsed(scriptInformation, null);
            }

            Process process = Xpdl2ModelUtil.getProcess(activity);
            if (activity != null && scriptInformation != null
                    && process != null) {
                return ScriptMappingUtil.parseScript(mapping,
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
            Object sourceObjectInTree, Object targetObjectInTree,
            Object mapping) {
        /*
         * Check compatibility of source and target
         */
        if (sourceObjectInTree instanceof PayloadConceptPath) {

            PayloadConceptPath payloadConceptPath =
                    (PayloadConceptPath) sourceObjectInTree;

            PayloadDataField payloadDataField =
                    payloadConceptPath.getPayloadDataField();

            if (payloadDataField != null) {
                /*
                 * converting payload params data field to concept path ,
                 * verified this and there are no side effects of doing this.
                 */
                JavaScriptTypeCompatibilityResult checkJavaScriptTypeCompatibility =
                        super.checkJavaScriptTypeCompatibility(
                                ConceptUtil.getConceptPath(payloadDataField),
                                targetObjectInTree,
                                mapping);

                return checkJavaScriptTypeCompatibility;
            }
        } else if (sourceObjectInTree instanceof ScriptInformation) {
            return super.checkJavaScriptTypeCompatibility(sourceObjectInTree,
                    targetObjectInTree,
                    mapping);
        }
        return JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#performAdditionalMappingsValidation(java.util.Collection)
     * 
     * @param mappings
     */
    @Override
    protected void performAdditionalMappingsValidation(Object objectToValidate,
            Collection<Object> mappings) {
        /*
         * perform other compatibility mappings first.
         */
        super.performAdditionalMappingsValidation(objectToValidate, mappings);

        Activity currentActivity = getCurrentActivity();

        if (currentActivity != null) {

            GlobalSignal referencedGlobalSignal =
                    GlobalSignalUtil.getReferencedGlobalSignal(currentActivity);

            if (referencedGlobalSignal != null) {

                EList<PayloadDataField> payloadDataFields =
                        referencedGlobalSignal.getPayloadDataFields();

                for (PayloadDataField payloadDataField : payloadDataFields) {

                    if (payloadDataField.isCorrelation()) {

                        Set<Mapping> mappingsFromPayloadField =
                                getMappingsFromPayloadField(
                                        payloadDataField.getName(),
                                        mappings);

                        /*
                         * Problem should be raised only on correlation payload
                         * that too only if they are not mapped.
                         */
                        if (mappingsFromPayloadField.isEmpty()) {
                            /*
                             * form the payload concept path.
                             */
                            PayloadConceptPath payloadConceptPath =
                                    new PayloadConceptPath(payloadDataField);
                            /*
                             * raise the issue.
                             */
                            addIssue(
                                    ISSUE_SOURCE_CORRELATION_PAYLOAD_MUST_BE_MAPPED,
                                    currentActivity,
                                    createMessageList(
                                            getMappingTypeDescription(null),
                                            sourceInfoProvider
                                                    .getObjectPathDescription(
                                                            payloadConceptPath)),
                                    createAdditionalInfo(
                                            MapperContentProvider.SOURCE_URI_ISSUEINFO,
                                            sourceInfoProvider.getObjectPath(
                                                    payloadConceptPath)));
                        }
                        /*
                         * XPD-7315: the correlation payload to non correlation
                         * data mapping issue is moved to
                         * performAdditionalMappingValidation(Object mapping,
                         * Object sourceObjectInTree, Object targetObjectInTree)
                         * method below because it specifically deals with
                         * mappings and passes the source and target of mapping
                         * hence we do not need to do any additional checks.
                         */
                    }
                }
            }
        }
    }

    /**
     * Return all of the mappings whose source payloadConceptPath is the given
     * field name.
     * 
     * @param name
     * @param mappings
     * 
     * @return All of the mappings whose source payloadConceptPath is the given
     *         field name.
     */
    private Set<Mapping> getMappingsFromPayloadField(String name,
            Collection<Object> mappings) {

        Set<Mapping> mappingsFromPayloadField = new HashSet<Mapping>();

        for (Object eachMappingObj : mappings) {

            if (eachMappingObj instanceof Mapping) {
                Mapping eachMapping = (Mapping) eachMappingObj;

                if (eachMapping.getSource() instanceof PayloadConceptPath) {
                    PayloadConceptPath pcp =
                            (PayloadConceptPath) (eachMapping.getSource());

                    if (pcp.getPayloadDataField() != null && name
                            .equals(pcp.getPayloadDataField().getName())) {
                        mappingsFromPayloadField.add(eachMapping);
                    }
                }
            }
        }

        return mappingsFromPayloadField;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#performAdditionalMappingValidation(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param mapping
     * @param sourceObjectInTree
     * @param targetObjectInTree
     */
    @Override
    protected void performAdditionalMappingValidation(Object mapping,
            Object sourceObjectInTree, Object targetObjectInTree) {
        /*
         * perform other compatibility mappings first.
         */
        super.performAdditionalMappingValidation(mapping,
                sourceObjectInTree,
                targetObjectInTree);

        if (targetObjectInTree instanceof ConceptPath) {
            Object item = ((ConceptPath) targetObjectInTree).getItem();
            boolean isTargetCorrelationData = false;
            boolean isSourceCorrelationData = false;

            if (item instanceof DataField) {

                isTargetCorrelationData = ((DataField) item).isCorrelation();
            }

            if (sourceObjectInTree instanceof PayloadConceptPath) {

                isSourceCorrelationData =
                        ((PayloadConceptPath) sourceObjectInTree)
                                .isCorrelationPayloadData();
            }

            if (isTargetCorrelationData) {

                DataMapping dataMapping = null;

                Object mappingModel = ((Mapping) mapping).getMappingModel();
                if (mappingModel instanceof DataMapping) {
                    dataMapping = (DataMapping) mappingModel;
                }

                if (!(dataMapping
                        .eContainer() instanceof CorrelationDataMappings)) {
                    /*
                     * XPD-8325: Create issue that the data type is correlation
                     * but mapping is not correlation mapping. It gets into this
                     * state if a data field or parameter is used in mapping
                     * (where a correlation field is expected) and that field or
                     * parameter is later converted to correlation field, but
                     * the mapping is not correlation data mapping!
                     */
                    Map<String, String> additionalInfo =
                            new HashMap<String, String>();

                    additionalInfo.put(
                            MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                            getTargetPath(targetInfoProvider, mapping));

                    addIssue(MAPPING_NOT_CORRELATION_DATA_MAPPING,
                            dataMapping,
                            createMessageList(getMappingTypeDescription(null),
                                    getTargetPathDescription(targetInfoProvider,
                                            mapping)),
                            additionalInfo);
                } else {
                    if (!isSourceCorrelationData) {
                        /*
                         * Non correlation source to correlation target mappings
                         * are not supported.
                         */
                        addIssue(
                                ISSUE_ONLY_CORRELATION_PAYLOAD_MAPPING_ALLOWED_TO_TARGET_CORRELATION_DATA,
                                getModelObjectForMapping(mapping),
                                createMessageList(
                                        getMappingTypeDescription(null),
                                        getTargetPathDescription(
                                                targetInfoProvider,
                                                mapping)),
                                createAdditionalInfo(
                                        MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                        getTargetPath(targetInfoProvider,
                                                mapping)));
                    }
                }
            } else {

                DataMapping dataMapping = null;

                Object mappingModel = ((Mapping) mapping).getMappingModel();
                if (mappingModel instanceof DataMapping) {
                    dataMapping = (DataMapping) mappingModel;
                }

                if (dataMapping
                        .eContainer() instanceof CorrelationDataMappings) {
                    /*
                     * XPD-8325: Create issue that the data type is not
                     * correlation but mapping is correlation mapping. It gets
                     * into this state if a correlation field is used in mapping
                     * and that field is later converted to field or parameter,
                     * but the mapping is still correlation data mapping!
                     */
                    Map<String, String> additionalInfo =
                            new HashMap<String, String>();

                    additionalInfo.put(
                            MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                            getSourcePath(sourceInfoProvider, mapping));

                    additionalInfo.put(
                            MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                            getTargetPath(targetInfoProvider, mapping));

                    addIssue(MAPPING_NOT_CORRELATION_DATA_MAPPING,
                            getModelObjectForMapping(mapping),
                            createMessageList(getMappingTypeDescription(null),
                                    getSourcePathDescription(sourceInfoProvider,
                                            mapping)),
                            additionalInfo);
                } else {

                    if (isSourceCorrelationData) {
                        /*
                         * Correlation Payload to non correlation target
                         * mappings is not allowed.
                         */

                        Map<String, String> aditionalInfo =
                                new HashMap<String, String>();

                        aditionalInfo.put(
                                MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                getSourcePath(sourceInfoProvider, mapping));

                        aditionalInfo.put(
                                MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                getTargetPath(targetInfoProvider, mapping));

                        addIssue(
                                ISSUE_SOURCE_CORRELATION_PAYLOAD_MUST_BE_MAPPED_ONLY_TO_TARGET_CORRELATION_FIELD,
                                getModelObjectForMapping(mapping),
                                createMessageList(
                                        getMappingTypeDescription(null),
                                        getSourcePathDescription(
                                                sourceInfoProvider,
                                                mapping)),
                                aditionalInfo);
                    }
                }
            }
        }
    }
}

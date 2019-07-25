/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.datamapper.infoProviders.ContributableDataMapperInfoProvider;
import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.datamapper.GlobalSignalCatchDataMapperFilter;
import com.tibco.xpd.n2.process.globalsignal.datamapper.GlobalSignalScriptDataMapperProvider;
import com.tibco.xpd.n2.process.globalsignal.mapping.PayloadConceptPath;
import com.tibco.xpd.n2.process.globalsignal.util.GlobalSignalMappingUtil;
import com.tibco.xpd.process.datamapper.signal.util.SignalDataMapperConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;

/**
 * Mapping rule for Catch Global Signal Data Mapper mapping event.
 * 
 * @author ssirsika
 * @since 28-Apr-2016
 */
public class CatchGlobalSignalEventDataMapperMappingRule extends
    AbstractN2DataMapperMappingRule {

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
     * Class level field to track the activity being validated.
     */
    Activity act;

    private ContributableDataMapperInfoProvider targetInfoProvider;

    private ContributableDataMapperInfoProvider sourceInfoProvider;

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

            IFilter filter = new GlobalSignalCatchDataMapperFilter();

            return filter.select(act) && super.objectIsApplicable(eo);
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule#createSourceInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected ContributableDataMapperInfoProvider createSourceInfoProvider(
            EObject objectToValidate) {
        sourceInfoProvider = super.createSourceInfoProvider(objectToValidate);
        return sourceInfoProvider;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractProcessData2ProcessDataJSMappingRule#createTargetInfoProvider(org.eclipse.emf.ecore.EObject)
     * 
     * @param objectToValidate
     * @return
     */
    @Override
    protected ContributableDataMapperInfoProvider createTargetInfoProvider(
            EObject objectToValidate) {
        targetInfoProvider = super.createTargetInfoProvider(objectToValidate);
        return targetInfoProvider;
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
        return SignalDataMapperConstants.GLOBAL_SIGNAL_CATCH;
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
        return ProcessScriptContextConstants.GLOBAL_CATCH_SIGNAL_EVENTMAPPING;
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

        Activity currentActivity = act;

        if (currentActivity != null) {

            GlobalSignal referencedGlobalSignal =
                    GlobalSignalUtil.getReferencedGlobalSignal(currentActivity);

            if (referencedGlobalSignal != null) {

                EList<PayloadDataField> payloadDataFields =
                        referencedGlobalSignal.getPayloadDataFields();

                for (PayloadDataField payloadDataField : payloadDataFields) {

                    if (payloadDataField.isCorrelation()) {

                        Set<Mapping> mappingsFromPayloadField =
                                getMappingsFromPayloadField(payloadDataField.getName(),
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

                            addIssue(ISSUE_SOURCE_CORRELATION_PAYLOAD_MUST_BE_MAPPED,
                                    currentActivity,
                                    createMessageList(getMappingTypeDescription(null),
                                            sourceInfoProvider
                                                    .getObjectPathDescription(payloadConceptPath)),
                                    createAdditionalInfo(MapperContentProvider.SOURCE_URI_ISSUEINFO,
                                            sourceInfoProvider
                                                    .getObjectPath(payloadConceptPath)));
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
        super.performAdditionalMappingsValidation(objectToValidate, mappings);
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
                Object source = eachMapping.getSource();
                if (source instanceof WrappedContributedContent) {
                    source =
                            ((WrappedContributedContent) source)
                                    .getContributedObject();
                }
                if (source instanceof PayloadConceptPath) {
                    PayloadConceptPath pcp = (PayloadConceptPath) source;

                    if (pcp.getPayloadDataField() != null
                            && name.equals(pcp.getPayloadDataField().getName())) {
                        mappingsFromPayloadField.add(eachMapping);
                    }
                }
            }
        }

        return mappingsFromPayloadField;
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
        /*
         * Check compatibility of source and target
         */
        if (unwrappedSource instanceof PayloadConceptPath) {

            PayloadConceptPath payloadConceptPath =
                    (PayloadConceptPath) unwrappedSource;

            PayloadDataField payloadDataField =
                    payloadConceptPath.getPayloadDataField();

            if (payloadDataField != null) {
                /*
                 * converting payload params data field to concept path ,
                 * verified this and there are no side effects of doing this.
                 */
                JavaScriptTypeCompatibilityResult checkJavaScriptTypeCompatibility =
                        super.checkJavaScriptTypeCompatibility(ConceptUtil
                                .getConceptPath(payloadDataField),
                                unwrappedTarget,
                                mapping);

                return checkJavaScriptTypeCompatibility;
            }
        } else if (unwrappedSource instanceof ScriptInformation) {
            return super.checkJavaScriptTypeCompatibility(unwrappedSource,
                    unwrappedTarget,
                    mapping);
        }
        return JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO;
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

        if (unwrappedTarget instanceof ConceptPath) {
            boolean isTargetCorrelationData = false;
            boolean isSourceCorrelationData = false;
            Object dataMapping = ((Mapping) mapping).getMappingModel();
            if (dataMapping instanceof DataMapping) {

                isTargetCorrelationData =
                        GlobalSignalMappingUtil
                                .isMappedToCorrelationData((DataMapping) dataMapping);
            }

            if (unwrappedSource instanceof PayloadConceptPath) {

                isSourceCorrelationData =
                        ((PayloadConceptPath) unwrappedSource)
                                .isCorrelationPayloadData();
            }

            if (isTargetCorrelationData) {

                if (!isSourceCorrelationData) {
                    /*
                     * Non correlation source to correlation target mappings are
                     * not supported.
                     */
                    addIssue(ISSUE_ONLY_CORRELATION_PAYLOAD_MAPPING_ALLOWED_TO_TARGET_CORRELATION_DATA,
                            getModelObjectForMapping(mapping),
                            createMessageList(getMappingTypeDescription(null),
                                    getTargetPathDescription(targetInfoProvider,
                                            mapping)),
                            createAdditionalInfo(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                    getTargetPath(targetInfoProvider, mapping)));
                }
            } else {

                if (isSourceCorrelationData) {
                    /*
                     * Correlation Payload to non correlation target mappings is
                     * not allowed.
                     */

                    Map<String, String> aditionalInfo =
                            new HashMap<String, String>();

                    aditionalInfo
                            .put(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                    getSourcePath(sourceInfoProvider, mapping));

                    aditionalInfo
                            .put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                    getTargetPath(targetInfoProvider, mapping));

                    addIssue(ISSUE_SOURCE_CORRELATION_PAYLOAD_MUST_BE_MAPPED_ONLY_TO_TARGET_CORRELATION_FIELD,
                            getModelObjectForMapping(mapping),
                            createMessageList(getMappingTypeDescription(null),
                                    getSourcePathDescription(sourceInfoProvider,
                                            mapping)),
                            aditionalInfo);

                }
            }
        }
    }
}

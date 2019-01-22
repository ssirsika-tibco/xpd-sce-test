/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.baserules;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlItemProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageWsdlMappingItemProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.implementer.resources.xpdl2.properties.replyimmediate.ReplyImmediateMappingContentProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.replyimmediate.ReplyImmediateMappingSourceContentProvider;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil.MappingStatus;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.xpath.model.ProcessXPathConsts;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProviderWithContributions;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.OutgoingReplyActivityTargetDataProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.CompositeTreeContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.script.ScriptInformationParsed;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.bpmn.developer.providers.WebSvcXPathMappingRuleInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.AbstractActivityMappingRule;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingIssue;
import com.tibco.xpd.validation.bpmn.rules.baserules.MappingRuleContentInfoProvider;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validate the XPath mappings for process Data to Web Service content.
 * <p>
 * i.e. This validate service invocation input mappings (ServiceTask &
 * Send-one-way SendTask/EndEvent) AND reply activities/throw end error for
 * non-generated request activities.
 * 
 * @author aallway
 * @since 3.3 (22 Jun 2010)
 */
public abstract class AbstractProcessDataToWebSvcRuleForXPath extends
        AbstractActivityMappingRule implements IWsdlXpdlNamespaceForXPathRules {

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#validateObject(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     */
    @Override
    protected void validateObject(EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        if (!ReplyActivityUtil.isStartMessageWithReplyImmediate(activity)) {
            MappingStatus mappingStatus =
                    NamespacePrefixMapUtil.getMappingStatus(activity);

            if (MappingStatus.INCONSISTENT == mappingStatus) {
                addIssue(IWsdlXpdlNamespaceForXPathRules.ISSUE_WSDL_XPDL_NAMESPACE_DISCREPANCY,
                        activity,
                        Arrays.asList(new String[] { activity.getName() }));
                return;
            } else if (MappingStatus.WSDL_INACCESSIBLE == mappingStatus) {
                return;
            }

            switch (mappingStatus) {
            case NONE_EXISTS: {
                addIssue(IWsdlXpdlNamespaceForXPathRules.ISSUE_MISSING_WSDL_XPDL_NAMESPACE_MAP,
                        activity);
                break;
            }
            case SAME_PREFIX_FOR_DIFFERENT_NAMESPACES: {
                addIssue(IWsdlXpdlNamespaceForXPathRules.ISSUE_SAME_PREFIX_DIFFERENT_NS,
                        activity,
                        Arrays.asList(new String[] { mappingStatus.getMessage() }));
                break;
            }
            case DIFFERENT_PREFIXES_FOR_SAME_NAMESPACE: {
                addIssue(IWsdlXpdlNamespaceForXPathRules.ISSUE_DIFFERENT_PREFIX_SAME_NS,
                        activity,
                        Arrays.asList(new String[] { mappingStatus.getMessage() }));
                break;
            }
            case ADDITIONAL_XSD_NS_PREFIXES_REQUIRED: {
                addIssue(IWsdlXpdlNamespaceForXPathRules.ISSUE_REQUIRE_ADDITIONAL_XSD_NS,
                        activity,
                        Arrays.asList(new String[] { mappingStatus.getMessage() }));
                break;
            }
            case NS_MAPPING_DISBALED_BUT_REQUIRED: {
                addIssue(IWsdlXpdlNamespaceForXPathRules.ISSUE_WSDL_XPDL_NS_MAP_DISABLED_BUT_REQUIRED,
                        activity,
                        Arrays.asList(new String[] { mappingStatus.getMessage() }));
                break;
            }
            }
        }

        super.validateObject(activity);

    }

    /**
     * @param activity
     * @return The mapping grammar that the sub-class is interested in.
     */
    protected String getApplicableGrammar(Activity activity) {
        return ScriptGrammarFactory.XPATH;
    }

    /**
     * @param contentProvider
     * @return The mapping rule info provider for the web service side of the
     *         data.
     */
    protected MappingRuleContentInfoProvider createWebServiceDataInfoProvider(
            ActivityMessageWsdlItemProvider contentProvider) {
        return new WebSvcXPathMappingRuleInfoProvider(contentProvider);
    }

    @Override
    protected String getDefaultScriptDestination() {
        return ProcessXPathConsts.XPATH_DESTINATION;
    }

    @Override
    protected boolean isScriptMappingTypeResolved(Object mapping,
            EObject objectToValidate) {
        // THis is handled by AbstractXPathExpressionValidator
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#parseScript(java.lang.Object)
     * 
     * @param mapping
     * @return
     */
    @Override
    protected ScriptInformationParsed parseScript(Object mapping) {
        /* Validation of XPath script mapping scriptss is done elsewhere. */
        return null;
    }

    @Override
    protected boolean objectIsApplicable(EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        if (WebServiceOperationUtil.isWebServiceImplementationType(activity)
                || WebServiceOperationUtil
                        .isInvokeBusinessProcessImplementationType(activity)) {
            if (isApplicableGrammar(activity)) {
                if (TaskType.SERVICE_LITERAL.equals(TaskObjectUtil
                        .getTaskType(activity))
                        || Xpdl2ModelUtil.isSendOneWayRequest(activity)) {
                    /*
                     * Service invocation activities have process data -> web
                     * svc input mappings
                     */
                    return true;

                } else if (ReplyActivityUtil.isReplyActivity(activity)) {
                    /*
                     * Reply activities have process data -> web service output
                     * mappings.
                     * 
                     * But don't want to validate ones that are generating their
                     * mappings.
                     */
                    Activity requestActivityForReplyActivity =
                            ReplyActivityUtil
                                    .getRequestActivityForReplyActivity(activity);

                    if (requestActivityForReplyActivity != null
                            && !Xpdl2ModelUtil
                                    .isGeneratedRequestActivity(requestActivityForReplyActivity)) {
                        return true;
                    }

                } else if (ThrowErrorEventUtil
                        .isThrowFaultMessageErrorEvent(activity)) {
                    /*
                     * Throw error (reply with fault) have process data -> web
                     * service output mappings.
                     * 
                     * But don't want to validate ones that are generating their
                     * mappings.
                     */
                    if (!ThrowErrorEventUtil
                            .shouldGenerateMappingsForErrorEvent(activity)) {
                        return true;
                    }
                } else if (ReplyActivityUtil
                        .isStartMessageWithReplyImmediate(activity)
                        && !Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                    return true;
                }

            }
        }

        return false;
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

    @Override
    protected IStructuredContentProvider createMappingsContentProvider(
            EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        IStructuredContentProvider contentProvider = null;
        if (ReplyActivityUtil.isStartMessageWithReplyImmediate(activity)) {
            contentProvider = new ReplyImmediateMappingContentProvider();
        } else {
            /*
             * If the activity is a reply activity then for process data -> svc
             * data needs the wsdl OUTPUT part data. If the activity is not a
             * reply activity then the svc data should be the wsdl input part
             * data.
             */
            boolean isWebSvcInputPart =
                    !ReplyActivityUtil.isReplyActivity(activity);

            /*
             * For process data -> web svc data MappingDirection.IN always
             * applies because mapping direction is always from perspective of
             * the WSDL (hence input to service = MappingDirection.IN to WSDL
             * Input part :: Output From Process = MappingsDirection.IN to WSDL
             * output part).
             */
            contentProvider =
                    new ActivityMessageWsdlMappingItemProvider(
                            MappingDirection.IN, isWebSvcInputPart);

            /* Make sure we set the input. */
            contentProvider.inputChanged(null, null, activity);

            return contentProvider;
        }
        contentProvider.inputChanged(null, null, activity);
        return contentProvider;
    }

    @Override
    protected MappingRuleContentInfoProvider createSourceInfoProvider(
            EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        ITreeContentProvider contentProvider = null;
        if (ReplyActivityUtil.isStartMessageWithReplyImmediate(activity)) {
            contentProvider = new ReplyImmediateMappingSourceContentProvider();

        } else if (ReplyActivityUtil.isReplyActivity(activity)
                || ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(activity)) {
            // XPD-6043 Ensure only API data fields are used for reply and throw
            // error activities.
            contentProvider =
                    new CompositeTreeContentProvider(
                            new OutgoingReplyActivityTargetDataProvider(
                                    MappingDirection.IN),
                            new ActivityScriptContentProvider(
                                    MappingDirection.IN));

        } else {
            contentProvider =
                    new CompositeTreeContentProvider(
                            new ActivityDataFieldItemProviderWithContributions(
                                    MappingDirection.IN),
                            new ActivityScriptContentProvider(
                                    MappingDirection.IN));

        }
        ProcessDataMappingRuleInfoProvider sourceInfoProvider =
                new ProcessDataMappingRuleInfoProvider(contentProvider);

        sourceInfoProvider.getContentProvider().inputChanged(null,
                null,
                activity);

        return sourceInfoProvider;
    }

    @Override
    protected final MappingRuleContentInfoProvider createTargetInfoProvider(
            EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        ActivityMessageWsdlItemProvider contentProvider;

        if (ReplyActivityUtil.isReplyActivity(activity)
                || ReplyActivityUtil.isStartMessageWithReplyImmediate(activity)) {
            /*
             * For reply activities "Output From Process".
             */
            contentProvider =
                    new ActivityMessageWsdlItemProvider(MappingDirection.OUT,
                            DirectionType.IN_LITERAL, WsdlDirection.OUT);
        } else {
            /*
             * For Service invocation activities "Input To Service"
             */
            contentProvider =
                    new ActivityMessageWsdlItemProvider(MappingDirection.IN,
                            DirectionType.IN_LITERAL, WsdlDirection.IN);

        }

        MappingRuleContentInfoProvider targetInfoProvider =
                createWebServiceDataInfoProvider(contentProvider);

        targetInfoProvider.getContentProvider().inputChanged(null,
                null,
                activity);

        return targetInfoProvider;
    }

    /**
     * @param activity
     * @return The mapping grammar that the sub-class is interested in.
     */
    private boolean isApplicableGrammar(Activity activity) {
        /*
         * ProcessData To WebSvcData is always direction IN because it is always
         * from the perspective of WSDL data. i.e. Service task invoke input
         * data is obviosuly DirectionType.IN and ReplyActivity is
         * DirectionTYpe.IN as well! (just means
         * "IN to the output part of WSDL")
         */
        String grammar =
                ScriptGrammarFactory.getScriptGrammar(activity,
                        DirectionType.IN_LITERAL);
        if (grammar == null) {
            /*
             * Script grammar for reply-immediate has to be forced to be Xpath
             * (as it is in the property section)
             */
            if (ReplyActivityUtil.isStartMessageWithReplyImmediate(activity)) {
                grammar = ScriptGrammarFactory.XPATH;
            } else {
                grammar =
                        ScriptGrammarFactory.getDefaultScriptGrammar(activity);
            }
        }

        String applicableGrammar = getApplicableGrammar(activity);
        if (applicableGrammar != null) {
            if (applicableGrammar.equals(grammar)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected String getMappingTypeDescription(EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        if (ReplyActivityUtil.isReplyActivity(activity)) {
            return Messages.MappingRule_OutputFromProcess_label;
        } else if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(activity)) {
            return Messages.AbstractProcessDataToWebSvcMappingRule_MappingRule_OutputFaultFromProcess_label;
        }
        if (ReplyActivityUtil.isStartMessageWithReplyImmediate(activity)) {
            return Messages.MappingRule_OutputProcessId;
        }
        return Messages.MappingRule_InputToService_label;

    }

    @Override
    protected boolean isMappingFromSourceLevelSupported(
            Object sourceObjectInTree) {
        /*
         * In general the mapping command provider will have prevented illegal
         * source/target selections for mappings.
         */
        return true;
    }

    @Override
    protected boolean isMappingToTargetLevelSupported(Object targetObjectInTree) {
        /*
         * In general the mapping command provider will have prevented illegal
         * source/target selections for mappings.
         */
        return true;
    }

    @Override
    protected void performAdditionalMappingValidation(Object mapping,
            Object sourceObjectInTree, Object targetObjectInTree) {
        return;
    }

    @Override
    protected void performAdditionalMappingsValidation(Object objectToValidate,
            Collection<Object> mappings) {
        if (mappings != null && !mappings.isEmpty()) {
            Set<ConceptPath> sourceObjectWithChildrenMapped =
                    new HashSet<ConceptPath>();
            Set<ConceptPath> targetObjectWithChildrenMapped =
                    new HashSet<ConceptPath>();
            Set<Mapping> mappingErrorCandidates = new HashSet<Mapping>();
            Activity activity = null;
            for (Object obj : mappings) {
                if (obj instanceof Mapping) {
                    Mapping mapping = (Mapping) obj;
                    if (activity == null
                            && mapping.getMappingModel() instanceof DataMapping) {
                        DataMapping dataMapping =
                                (DataMapping) mapping.getMappingModel();
                        activity =
                                Xpdl2ModelUtil.getParentActivity(dataMapping);
                    }
                    Object sourceObject = getSourceObject(mapping);
                    Object targetObject = getTargetObject(mapping);
                    if (sourceObject instanceof ConceptPath) {
                        sourceObjectWithChildrenMapped
                                .add(((ConceptPath) sourceObject).getRoot());
                        mappingErrorCandidates.add(mapping);
                    }
                    if (targetObject instanceof ConceptPath
                            && !ConceptUtil.isRoot((ConceptPath) targetObject)) {
                        targetObjectWithChildrenMapped
                                .add(((ConceptPath) targetObject).getRoot());
                        mappingErrorCandidates.add(mapping);
                    }
                }
            }
            if (activity != null) {
                for (ConceptPath sourceObjectRoot : sourceObjectWithChildrenMapped) {
                    for (ConceptPath targetObjectRoot : targetObjectWithChildrenMapped) {
                        if (sourceObjectRoot.equals(targetObjectRoot)) {
                            for (Mapping mapping : mappingErrorCandidates) {
                                Object sourceObject = getSourceObject(mapping);
                                Object targetObject = getTargetObject(mapping);
                                if (sourceObject instanceof ConceptPath
                                        && targetObject instanceof ConceptPath) {
                                    if (((ConceptPath) sourceObject).getRoot() != null
                                            && ((ConceptPath) targetObject)
                                                    .getRoot() != null
                                            && (((ConceptPath) sourceObject)
                                                    .getRoot()
                                                    .equals(sourceObjectRoot) || ((ConceptPath) targetObject)
                                                    .getRoot()
                                                    .equals(targetObjectRoot))) {
                                        // Wsdl Part and Process Relevant data
                                        // collide
                                        addIssue(MappingIssue.WSDLPART_AND_PROCESSDATA_NAME_COLLISISON
                                                .getIssueId(),
                                                getModelObjectForMapping(mapping),
                                                Collections
                                                        .singletonList(sourceObjectRoot
                                                                .getName()),
                                                createAdditionalInfo(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                                        ((ConceptPath) sourceObject)
                                                                .getPath(),
                                                        MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                                        ((ConceptPath) targetObject)
                                                                .getPath()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return;
    }

    @Override
    protected boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree) {
        /*
         * When mapping from process data to WSDL data then we should always
         * create complete data to pass in WSDL message part
         */
        return false;
    }

    @Override
    protected String getScriptGrammar(EObject objectToValidate) {
        /**
         * It's ok to cast to activity as that is the only thing we return from
         * #getObjectsToValidates()
         */
        Activity activity = (Activity) objectToValidate;

        return getApplicableGrammar(activity);
    }

    @Override
    protected String getScriptType() {
        return ProcessJsConsts.WEBSERVICE_TASK;
    }

    /*
     * Sid: Moved parseScript() up to JavaScript specific sub-classes because
     * this implementation is javaScript specific!
     */

}
